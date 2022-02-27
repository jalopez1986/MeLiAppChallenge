package com.example.meliapp.app.search.list.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.app.search.list.viewmodel.ItemsListViewModel
import com.example.meliapp.app.search.list.viewmodel.ItemsListViewModelFactory
import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.databinding.FragmentItemsListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemstListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ItemsListViewModelFactory

    private val viewModel: ItemsListViewModel by viewModels { viewModelFactory }

    val args: ItemstListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemsListBinding.inflate(inflater, container, false)

        observeLoader()
        observeItemsResultList()

        makeSearchByQuery(args.query)

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

    private fun observeItemsResultList() {
        viewModel.items.observe(this as LifecycleOwner, { itemsResult ->
            when {
                itemsResult.isSuccess -> {
                    setupList(binding.eventsRecyclerView, itemsResult.getOrNull()!!)
                }
                itemsResult.isFailure -> {
                    manageError(itemsResult)
                }
            }
        })
    }

    private fun makeSearchByQuery(query: String) {
        viewModel.searchItemsByQuery(query)
    }

    private fun setupList(view: View, itemsResponse: ItemsResponse) {
        if (itemsResponse.results.isEmpty()) {
            view.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            view.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE
        }

        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemsAdapter(itemsResponse.results) { item ->
                val action =
                    ItemstListFragmentDirections.actionResultListFragmentToDetailProductFragment(
                        item.id ?: ""
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun manageError(itemsResult: Result<ItemsResponse>) {
        val exceptionError = itemsResult.exceptionOrNull()?.localizedMessage ?: ""
        Log.d("ItemsListFragment", exceptionError)
        showAlert(getString(R.string.api_result_error_message))
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar") { _, _ ->
            findNavController().popBackStack()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}