package com.example.meliapp.app.search.home.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.meliapp.R
import com.example.meliapp.app.search.home.viewmodel.SearchItemViewModel
import com.example.meliapp.databinding.FragmentSearchItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchItemFragment : Fragment() {

    private var _binding: FragmentSearchItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchItemBinding.inflate(inflater, container, false)

        observeIsValidSearchQuery()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            viewModel.tryToMakeSearch(binding.searchEditText.text.toString())
        }
    }

    private fun observeIsValidSearchQuery() {
        viewModel.isValidSearchQuery.observe(this as LifecycleOwner, { eventResult ->
            eventResult.getContentIfNotHandled()?.let { isValid ->
                when (isValid) {
                    true -> goToItemsResultScreen()
                    false -> Toast.makeText(
                        context,
                        getString(R.string.search_empty_query_string_message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun goToItemsResultScreen() {
        val action =
            SearchItemFragmentDirections.actionSearchHomeFragmentToResultListFragment(binding.searchEditText.text.toString())
        findNavController().navigate(action)
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = requireActivity().currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}