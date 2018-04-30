package com.aim.atlas.ui.splash

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aim.atlas.R


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        DisplaySplash().execute()
    }

    private inner class DisplaySplash : AsyncTask<Void, Int, Int>() {
        override fun doInBackground(vararg params: Void): Int? {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return 0
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            setResult(RESULT_OK)
            finish()
        }
    }
}