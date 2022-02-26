package com.example.meliapp.app.search.list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meliapp.R
import com.example.meliapp.app.search.list.viewmodel.ItemsListViewModel
import com.example.meliapp.app.search.list.viewmodel.ItemsListViewModelFactory
import com.example.meliapp.core.search.actions.SearchItemsByQuery
import com.example.meliapp.core.search.domain.ItemsResponse
import com.example.meliapp.core.search.infrastructure.ItemsAPI
import com.example.meliapp.core.search.infrastructure.RetrofitItemsRepository
import com.example.meliapp.databinding.FragmentResultListBinding
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.mercadolibre.com/"
class ResultListFragment : Fragment() {
    private var _binding: FragmentResultListBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ItemsListViewModel
    lateinit var viewModelFactory: ItemsListViewModelFactory

    private val retrofitAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ItemsAPI::class.java)

    private val repository = RetrofitItemsRepository(retrofitAPI)
    private val searchItemsByQuery = SearchItemsByQuery(repository)



    val args: ResultListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResultListBinding.inflate(inflater, container, false)

        setupViewModel()

        observeLoader()
        observeItemsResultList()

        makeSearchByQuery(args.query)


        return binding.root
    }

    private fun makeSearchByQuery(query: String) {
        viewModel.searchItemsByQuery(query)
    }

    private fun observeItemsResultList() {
        viewModel.items.observe(this as LifecycleOwner, { items ->
            if (items.getOrNull() != null) {
                setupList(binding.eventsRecyclerView, items.getOrNull()!!)
            } else {
                //TODO
            }

        })
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
                findNavController().navigate(R.id.detailProductFragment)
            }
        }

    }

    private fun observeLoader() {
        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when(loading) {
                true -> binding.loader.root.visibility = View.VISIBLE
                false -> binding.loader.root.visibility = View.GONE
            }
        })
    }

    private fun setupViewModel() {
        viewModelFactory = ItemsListViewModelFactory(searchItemsByQuery)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsListViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}