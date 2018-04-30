package com.aim.atlas.ui.blockedAccount

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aim.atlas.R
import kotlinx.android.synthetic.main.activity_blocked_account.buttonBlockedAccount
import kotlinx.android.synthetic.main.activity_blocked_account.textViewChangeAccount
import kotlinx.android.synthetic.main.activity_blocked_account.textViewChangeAccountTitle

class BlockedAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocked_account)

        setListeners()
        setView()
    }

    private fun setView() {
        textViewChangeAccountTitle.text = getString(
                R.string.blocked_account_change_account_header,
                "edgardo.flores.osben@gmail.com"
        )
    }

    private fun setListeners() {
        buttonBlockedAccount.setOnClickListener {
            //Todo: Call unlock account action
        }

        textViewChangeAccount.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}