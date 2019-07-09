package com.luoyang.picking.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luoyang.picking.PickingApplication
import com.luoyang.picking.R
import com.luoyang.picking.utils.afterTextChanged
import com.luoyang.picking.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

//        MainActivity.goIn(this)
//        finish()

        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        //用户输入监听
        username.afterTextChanged { username ->
            loginViewModel.loginDataChanged(
                username,
                password.text.toString()
            )
        }
        //密码输入监听
        password.afterTextChanged { password ->
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password
            )
        }
        //输入正确
        loginViewModel.inputSuccess.observe(this, Observer {
            login.isEnabled = it
        })
        //登录
        login.setOnClickListener {
            loading.isVisible = true
            loginViewModel.login(username.text.toString(), password.text.toString())
        }

        loginViewModel.resultMsg.observe(this, Observer {
            loading.isVisible = false
            if (it.contains("true")) {
                MainActivity.goIn(this)
                finish()
            } else {
                showToast(it)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exit()
    }

    private fun exit() {
        PickingApplication.application.exit()
    }
}