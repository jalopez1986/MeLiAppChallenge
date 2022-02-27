package com.example.meliapp.app.search.detail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meliapp.R
import com.example.meliapp.app.search.detail.viewmodel.ItemDetailViewModel
import com.example.meliapp.app.search.detail.viewmodel.ItemDetailViewModelFactory
import com.example.meliapp.core.search.domain.ItemInfo
import com.example.meliapp.databinding.FragmentItemDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import javax.inject.Inject

@AndroidEntryPoint
class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var viewModelFactory: ItemDetailViewModelFactory

    private val viewModel: ItemDetailViewModel by viewModels { viewModelFactory }

    val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        observeLoader()

        observeItemDetail()

        searchItemDescription()

        return binding.root

    }

    private fun observeItemDetail() {
        viewModel.itemDetail.observe(this as LifecycleOwner, { itemDetail ->
            if (itemDetail.getOrNull() != null) {
                renderUI(itemDetail.getOrNull()!!)
            }
        })
    }

    private fun renderUI(itemDetail: ItemInfo) {
        binding.itemDetailConditionTextView.text = itemDetail.item.condition
        binding.itemDetailTitleTextView.text = itemDetail.item.title
        binding.itemDetailPriceTextView.text = resolvePriceText(itemDetail.item.price)
        binding.itemDetailShippingTextView.text = resolveShipmentText(itemDetail.item.shipping.free_shipping)
        binding.itemDetailDescriptionTextView.text = itemDetail.description.plain_text

        Glide.with(binding.itemDetailImageView)
            .load(itemDetail.item.pictures[0].url)
            .into(binding.itemDetailImageView)
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when(loading) {
                true -> binding.loader.root.visibility = View.VISIBLE
                false -> binding.loader.root.visibility = View.GONE
            }
        })

    }

    private fun searchItemDescription() {
        viewModel.searchItemDescription(args.itemId)
    }

    private fun resolvePriceText(price: Double): String {
        val format = NumberFormat.getCurrencyInstance()
        return format.format(price)
    }

    private fun resolveShipmentText(freeshipping: Boolean): String {
        return if (freeshipping) requireContext().getString(R.string.item_result_freeshipping) else ""
    }
}