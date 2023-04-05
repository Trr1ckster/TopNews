package com.trr1ckster.topnews.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.trr1ckster.topnews.databinding.FragmentMainBinding
import com.trr1ckster.topnews.ui.adapters.NewsAdapter
import com.trr1ckster.topnews.utils.Resource

class NewsFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding!!

    private val newsAdapter = NewsAdapter()
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = newsAdapter

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.progressBar.isVisible
                    response.data?.let {
                        newsAdapter.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.isInvisible
                    response.message?.let { message ->
                        Log.e("TAG", "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isGone
                }
            }

            binding.swipeRefresh.setOnRefreshListener {
                viewModel.getNews()
                binding.swipeRefresh.isRefreshing = false
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}