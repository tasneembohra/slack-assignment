package com.tasneembohra.slackassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tasneembohra.slackassignment.R
import com.tasneembohra.slackassignment.databinding.FragmentHomeBinding
import com.tasneembohra.slackassignment.repo.model.ErrorCode
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.di.homeModule
import com.tasneembohra.slackassignment.ui.home.model.UserUi
import com.tasneembohra.slackassignment.util.launch
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: HomeViewModel by viewModel()
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
        launch { viewModel.data.collectLatest(::onSearchResult) }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun onSearchResult(result: Resource<List<UserUi>>) {
        binding.progressBar.isVisible = result is Resource.Loading
        binding.errorMessage.isVisible = result is Resource.Error
        if (result is Resource.Error) {
            // TODO improve this
            binding.errorMessage.text = when (result.errorCode) {
                ErrorCode.NOT_FOUND -> getString(R.string.user_not_found_error_message)
                else -> getString(R.string.generic_error_message)
            }
        }
    }
}