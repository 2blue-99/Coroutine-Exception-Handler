package com.example.basepractice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basepractice.adapter.MyAdapter
import com.example.basepractice.databinding.ActivityMainBinding
import com.example.basepractice.viewModel.MyViewModel
import com.example.domain.state.ResourceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MyViewModel by viewModels()
    private val adapter: MyAdapter by lazy { MyAdapter() }
    private lateinit var binding: ActivityMainBinding
    private var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initObserver()
        initListener()
        initRecycler()
    }

    private fun initListener(){
        binding.btn.setOnClickListener {
            viewModel.getApiData("김포공항")
        }

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            adapter.dataList.clear()
            viewModel.getApiData("${count++}")
        }
    }

    private fun initObserver() {

        viewModel.loading.observe(this) {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.INVISIBLE
        }

        /// Flow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.myChannel.collectLatest {
                    Log.e("TAG", "initObserver: $it", )
                    when(it){
                        is ResourceState.Success -> {
//                            adapter.dataList.add(it.data) // TODO 이렇게 넣으면 값이 안바뀜
                        }
                        is ResourceState.Error -> {
                            Toast.makeText(applicationContext, "${it.failure}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(application)
        binding.recyclerView.adapter = adapter
    }
}