package com.gideondev.stackcandyspace.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gideondev.stackcandyspace.R
import com.gideondev.stackcandyspace.databinding.ActivityStackExchangeBinding
import com.gideondev.stackcandyspace.model.User
import java.text.SimpleDateFormat
import java.util.*

class StackExchangeActivity : AppCompatActivity() {
    lateinit var user : User
    lateinit var binding: ActivityStackExchangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stack_exchange)
        setSupportActionBar(binding.toolbarMain)

        supportActionBar?.apply {
            title = getString(R.string.details)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        getData()
        setValueToView()
    }

    private fun getData(){
        user = intent.getSerializableExtra("model") as User
    }

    private fun setValueToView(){
          binding.txtLocations.text = user.location
          binding.txtReputation.text = user.reputation.toString()
          binding.txtUserName.text = user.displayName
          binding.txtBadges.text = user.badgeCounts.toString()
          binding.txtCreationDate.text = user.creationDate?.toLong()?.let { getDateTime(it) }
          Glide.with(binding.imgUser .context)
              .load(user.profileImage)
              .centerCrop()
              .into(binding.imgUser)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}