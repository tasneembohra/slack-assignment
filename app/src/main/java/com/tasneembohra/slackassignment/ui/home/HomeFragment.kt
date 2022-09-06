package com.tasneembohra.slackassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.databinding.FragmentHomeBinding
import com.tasneembohra.slackassignment.repo.model.ErrorCode
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.di.homeModule
import com.tasneembohra.slackassignment.util.base.adapters.BaseListAdapter
import com.tasneembohra.slackassignment.util.extensions.launch
import com.tasneembohra.slackassignment.util.model.AvatarUi
import com.tasneembohra.slackassignment.util.model.BaseItemUi
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModel()
    private val searchAdapter: BaseListAdapter by inject()

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).d("onCreate(%s)", savedInstanceState)
        super.onCreate(savedInstanceState)
        // Loading feature module
        loadKoinModules(homeModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.setOnQueryTextListener(this)
        binding.recyclerView.adapter = searchAdapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL,
            )
        )
        launch { viewModel.data.collectLatest(::onSearchResult) }
    }

    override fun onDestroyView() {
        binding.recyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.search(newText)
        return true
    }

    private fun onSearchResult(result: Resource<List<BaseItemUi>>) {
        binding.progressBar.isVisible = result is Resource.Loading
        binding.recyclerView.isVisible = !result.data.isNullOrEmpty()
        searchAdapter.setData(result)
    }
}