package com.luoyang.picking.ui

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luoyang.picking.PickingApplication
import com.luoyang.picking.R
import com.luoyang.picking.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        //已经登录
        if (loginViewModel.logined) {
            MainActivity.goIn(this)
            finish()
        }

        setContentView(R.layout.activity_login)

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

        //登录
        login.setOnClickListener {
            loading.isVisible = true
            Handler().postDelayed(
                {
                    loading.isVisible = false
                    MainActivity.goIn(this)
                    finish()
                },
                1000
            )
        }

        loginViewModel.inputSuccess.observe(this, Observer {
            login.isEnabled = it
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            exit()
        }
        return true
    }

    private fun exit() {
        PickingApplication.application.exit()
    }

    //扩展输入框方法
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }
}