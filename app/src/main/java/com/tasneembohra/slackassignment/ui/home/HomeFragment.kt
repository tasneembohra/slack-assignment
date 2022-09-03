package com.tasneembohra.slackassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.tasneembohra.slackassignment.databinding.FragmentHomeBinding
import com.tasneembohra.slackassignment.repo.model.Resource
import com.tasneembohra.slackassignment.ui.home.di.homeModule
import com.tasneembohra.slackassignment.ui.util.search.SearchBarConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(javaClass.simpleName).d("onCreate(%s)", savedInstanceState)
        super.onCreate(savedInstanceState)
        // Loading feature module
        loadKoinModules(homeModule)
        viewModel
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

        launch {
            viewModel.data.collectLatest {
                Timber.d("data collected ${it.data}")
                binding.progressBar.isVisible = it is Resource.Loading
                binding.errorMessage.isVisible = it is Resource.Error
            }
        }
    }
}

fun Fragment.launch(block: suspend CoroutineScope.() -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch(block = block)