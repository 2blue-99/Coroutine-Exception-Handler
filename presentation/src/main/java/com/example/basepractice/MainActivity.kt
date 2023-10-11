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
import androidx.recyclerview.widget.ListAdapter
import com.example.basepractice.adapter.MyAdapter
import com.example.basepractice.databinding.ActivityMainBinding
import com.example.basepractice.viewModel.MyViewModel
import com.example.domain.model.MyData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MyViewModel by viewModels()
    private val adapter: MyAdapter by lazy { MyAdapter() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        initObserver()
        initListener()
        initRecycler()
    }

    private fun initListener() {
        binding.btn.setOnClickListener {
            // liveData
            viewModel.getApiData()

            //flow
//            viewModel.getFlowApiData()

            // Response

            binding.btn.isClickable = false
        }

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            adapter.dataList.clear()
            viewModel.getApiData()
        }

        binding.socketException.setOnClickListener {
            viewModel.getSocketException()
        }

        binding.timeout.setOnClickListener {
            viewModel.getTimeOutException()
        }
    }

    private fun initObserver() {
        viewModel.liveData.observe(this) {
            val newList = adapter.dataList
            newList.add(it)
            adapter.dataList = newList
        }

        viewModel.loading.observe(this) {
            if (it) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.INVISIBLE
        }

        viewModel.fetchState.observe(this) {
            when (it.first) {
                is SocketException -> {
                    binding.progress.isVisible = false
                    binding.linearLayout2.setBackgroundColor(Color.RED)
                    binding.errTxt.isVisible = true
                    binding.errTxt.text = "SocketException"
                    adapter.dataList = emptyList<MyData>().toMutableList()
                    Toast.makeText(this, "${it.second}", Toast.LENGTH_SHORT).show()
                }

                is TimeoutException -> {
                    binding.progress.isVisible = false
                    binding.linearLayout2.setBackgroundColor(Color.BLUE)
                    binding.errTxt.isVisible = true
                    binding.errTxt.text = "TimeOutException"
                    adapter.dataList = emptyList<MyData>().toMutableList()
                    Toast.makeText(this, "${it.second}", Toast.LENGTH_SHORT).show()
                }

                is UnknownHostException -> {
                    binding.progress.isVisible = false
                    binding.linearLayout2.setBackgroundColor(Color.BLUE)
                    binding.errTxt.isVisible = true
                    binding.errTxt.text = "TimeOutException"
                    adapter.dataList = emptyList<MyData>().toMutableList()
                    Toast.makeText(this, "${it.second}", Toast.LENGTH_SHORT).show()
                }

                is HttpException -> {
                    when((it.first as HttpException).code()){
                        400 -> {}
                        401 -> {}
                        402 -> {}
                        403 -> {}
                        404 -> {}
                        else -> {}
                    }
                }

                else -> {}
            }
        }


//        viewModel.fetchState.observe(this){
//            when(it.first) {
//                is SocketException ->
//                    exceptionControl(true, true, Color.BLUE, "SocketException", "SocketException 에러 발생!")
//
//                is TimeoutException ->
//                    exceptionControl(false, true, Color.RED, "TimeoutException", "TimeoutException 에러 발생!")
//
//                is UnknownHostException ->
//                    exceptionControl(true, false, Color.WHITE, "UnknownHostException", "UnknownHostException 에러 발생!")
//
//                else -> {
//                    exceptionControl(true, false, Color.WHITE, "알수없는 에러 발생!", "알수없는 에러 발생!")
//                }
//            }
//        }





        /// Flow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collectLatest {



                    val newList = adapter.dataList
                    newList.add(it)
                    adapter.dataList = newList
                }
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(application)
        binding.recyclerView.adapter = adapter
    }

    private fun exceptionControl(loading: Boolean, empty: Boolean, color: Int, viewText: String="", toast: String=""){
        binding.progress.isVisible = loading
        binding.errTxt.isVisible = empty
        binding.linearLayout2.setBackgroundColor(color)
        binding.errTxt.text = viewText
        Toast.makeText(this, "$toast", Toast.LENGTH_SHORT).show()
    }
}