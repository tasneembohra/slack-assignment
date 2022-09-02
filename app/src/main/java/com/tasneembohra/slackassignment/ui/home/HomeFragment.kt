package com.tasneembohra.slackassignment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tasneembohra.slackassignment.databinding.FragmentHomeBinding
import com.tasneembohra.slackassignment.ui.home.di.homeModule
import com.tasneembohra.slackassignment.ui.util.search.SearchBarConfig
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var _binding: FragmentHomeBinding

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun bindSearchView() {

    }


}