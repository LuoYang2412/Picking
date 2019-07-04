package com.luoyang.picking.ui

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luoyang.picking.R
import com.luoyang.picking.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        if (viewModel.logined) {
            WarehouseOut.goIn(this)
            finish()
        }

        username.afterTextChanged { username ->
            viewModel.loginDataChanged(
                username,
                password.text.toString()
            )
        }
        password.afterTextChanged { password ->
            viewModel.loginDataChanged(
                username.text.toString(),
                password
            )
        }
        login.setOnClickListener {
            loading.isVisible = true
            Handler().postDelayed(
                {
                    loading.isVisible = false
                    WarehouseOut.goIn(this)
                    finish()
                },
                1000
            )
        }

        viewModel.inputSuccess.observe(this, Observer {
            login.isEnabled = it
        })
    }

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