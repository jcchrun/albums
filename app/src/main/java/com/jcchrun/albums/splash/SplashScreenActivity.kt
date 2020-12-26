package com.jcchrun.albums.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jcchrun.albums.R
import com.jcchrun.albums.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity()  {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        observeApplicationInitialization()
        splashViewModel.init()
    }

    private fun observeApplicationInitialization() {
        splashViewModel.initLiveData.observe(this, {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}