package com.example.meliapp.app.search.detail.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meliapp.R
import com.example.meliapp.app.search.detail.viewmodel.ItemDetailViewModel
import com.example.meliapp.app.search.detail.viewmodel.ItemDetailViewModelFactory
import com.example.meliapp.app.util.FormatterHelper
import com.example.meliapp.core.search.domain.ItemInfo
import com.example.meliapp.databinding.FragmentItemDetailBinding
import dagger.hilt.android.AndroidEntryPoint
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
        binding.itemDetailRootView.visibility = View.GONE

        observeLoader()
        observeItemDetail()

        searchItemDescription()

        return binding.root
    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when (loading) {
                true -> binding.loader.root.visibility = View.VISIBLE
                false -> binding.loader.root.visibility = View.GONE
            }
        })
    }

    private fun observeItemDetail() {
        viewModel.itemDetail.observe(this as LifecycleOwner, { itemDetailResult ->
            when {
                itemDetailResult.isSuccess -> {
                    renderUI(itemDetailResult.getOrNull()!!)
                }

                itemDetailResult.isFailure -> {
                    manageError(itemDetailResult)
                }
            }
        })
    }

    private fun searchItemDescription() {
        viewModel.searchItemDescription(args.itemId)
    }

    private fun renderUI(itemDetail: ItemInfo) {
        binding.itemDetailRootView.visibility = View.VISIBLE
        binding.itemDetailConditionTextView.text =
            FormatterHelper.resolveConditionText(itemDetail.item.condition)
        binding.itemDetailTitleTextView.text = itemDetail.item.title
        binding.itemDetailPriceTextView.text =
            FormatterHelper.resolvePriceText(itemDetail.item.price)
        binding.itemDetailShippingTextView.text =
            FormatterHelper.resolveShipmentText(itemDetail.item.shipping.free_shipping)
        binding.itemDetailDescriptionTextView.text = itemDetail.description.plain_text

        Glide.with(binding.itemDetailImageView)
            .load(itemDetail.item.pictures[0].url)
            .into(binding.itemDetailImageView)
    }

    private fun manageError(itemDetailResult: Result<ItemInfo>) {
        val exceptionError = itemDetailResult.exceptionOrNull()?.localizedMessage ?: ""
        Log.d("ItemDetailFragment", exceptionError)
        showAlert(getString(R.string.api_result_error_message))
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { _, _ ->
            val action =
                ItemDetailFragmentDirections.actionDetailProductFragmentToSearchHomeFragment()
            findNavController().navigate(action)
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}