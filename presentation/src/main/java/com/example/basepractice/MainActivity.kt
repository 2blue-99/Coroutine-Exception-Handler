package com.example.basepractice

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initObserver()
        initListener()
        initRecycler()
    }

    private fun initListener(){
        binding.searchTxt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val newList = mutableListOf<String>()
                resources.getStringArray(R.array.placeName_array).forEach { if(it.contains("$p0")) newList.add(it) }
                adapter.dataList = newList
            }

        })

        adapter.onClickData = {
            binding.include.dataComponent.visibility = View.INVISIBLE
            lifecycleScope.launch {
                viewModel.getApiData(it)
            }
        }

        binding.searchBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getApiData("${binding.searchTxt.text}")
            }
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
                    when(it){
                        is ResourceState.Success -> {
                            binding.include.root.isVisible = true
                            binding.include.data = it.data
                        }
                        is ResourceState.Error -> {
                            Toast.makeText(applicationContext, it.failure.message, Toast.LENGTH_SHORT).show()
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
        adapter.dataList = resources.getStringArray(R.array.placeName_array).toMutableList()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2500) { finish() }
        Toast.makeText(this, "한번 더 클릭 시 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}