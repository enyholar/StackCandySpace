package com.gideondev.stackcandyspace.ui
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.gideondev.stackcandyspace.R
import com.gideondev.stackcandyspace.databinding.ActivityMainBinding
import com.gideondev.stackcandyspace.model.User
import com.gideondev.stackcandyspace.ui.adapter.UserAdapter
import com.gideondev.stackcandyspace.uiState.ContentState
import com.gideondev.stackcandyspace.uiState.ErrorState
import com.gideondev.stackcandyspace.uiState.LoadingState
import com.gideondev.stackcandyspace.viewModel.SearchUserViewModel
import androidx.lifecycle.ViewModelProvider
import com.gideondev.stackcandyspace.Utils.showSnack
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userList: MutableList<User?> = ArrayList()
    private lateinit var userAdapter: UserAdapter
    private lateinit var  viewModel: SearchUserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[SearchUserViewModel::class.java]
        setUpAdapter()
        initView()
        observeData()

    }

    private fun setUpAdapter() {
       val layoutManager = LinearLayoutManager(this)
        binding.searchUserRecyclerview.layoutManager = layoutManager
        userAdapter = UserAdapter(userList, object  : UserAdapter.ClickListner{

            override fun onItemClick(model: User?, position: Int) {
                val intent = Intent(this@MainActivity, StackExchangeActivity::class.java)
                intent.putExtra("model",model)
                startActivity(intent)
            }

        })
        binding.searchUserRecyclerview.setHasFixedSize(true)
        binding.searchUserRecyclerview.adapter = userAdapter

    }

    private fun initView(){
        binding.toolbarMain.title = getString(R.string.app_name)
        binding.btnSearch.setOnClickListener{
            val query = binding.edtSearch.text.toString()
            if (query.isNotEmpty()){
                performSearch(query)
            }else{
                userAdapter.removeAllData()
            }
        }
    }

    private fun performSearch(query: String) {
        viewModel.searchForUser(query)
    }

    private fun observeData() {
        viewModel.uiStateLiveData.observe(this) { state ->
            when (state) {
                is LoadingState -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is ContentState -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ErrorState -> {
                    binding.progressBar.visibility = View.GONE

                    binding.parentView.showSnack(state.message, getString(R.string.action_retry_str)) {
                        viewModel.retry()
                    }
                }
            }
        }

        viewModel.searchUserResponseLiveData.observe(this) { users ->

            users.items?.let { it ->
                userList = it.toMutableList()
                userList.sortBy { it?.displayName }
                userAdapter.addAllItem(userList)
            }
        }
    }

}