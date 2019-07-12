package com.birikorang_kelvin_proj.alc4phase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initMyProfile()
    }

    private fun initMyProfile() {
        @Suppress("SpellCheckingInspection")
        val myProfile = arrayListOf(
            "Android",
            "Ghana",
            "kelvinbirikorang@rocketmail.com",
            "+233548977159",
            "@Bik_Krlvn",
            "Kelvin Birikorang"
        )
        tv_track?.text = myProfile[0]
        tv_country?.text = myProfile[1]
        tv_email?.text = myProfile[2]
        tv_contact?.text = myProfile[3]
        tv_username?.text = myProfile[4]
        tv_name?.text = myProfile[5]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
