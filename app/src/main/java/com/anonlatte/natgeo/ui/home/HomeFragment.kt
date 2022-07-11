package com.anonlatte.natgeo.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anonlatte.natgeo.R
import com.anonlatte.natgeo.databinding.FragmentHomeBinding
import com.anonlatte.natgeo.ui.home.state.NewsUiState
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModel
import com.anonlatte.natgeo.ui.home.viewmodel.HomeViewModelImpl
import com.anonlatte.natgeo.utils.collectOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModel<HomeViewModelImpl>()
    private val newsAdapter = ArticlesAdapter {
        val actionHomeToArticle = HomeFragmentDirections.actionHomeToArticle(it)
        findNavController().navigate(actionHomeToArticle)
    }
    private lateinit var searchView: SearchView

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        initViews()
        viewModel.uiState.collectOnLifecycle(this) {
            when (it) {
                is NewsUiState.Success -> renderSuccess(it)
                is NewsUiState.Error -> {}
                NewsUiState.Loading -> {}
                NewsUiState.Empty -> {}
            }
        }
        viewModel.getNews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.rvNews.adapter = newsAdapter
    }

    private fun renderSuccess(state: NewsUiState.Success) {
        newsAdapter.submitList(state.news)
    }

    private fun initMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val menuItem = menu.findItem(R.id.action_search)
                searchView = (menuItem.actionView as SearchView)
                searchView.queryHint = getString(R.string.placeholder_news_search)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }
}