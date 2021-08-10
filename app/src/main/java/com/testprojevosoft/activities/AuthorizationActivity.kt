package com.testprojevosoft.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testprojevosoft.Navigator
import com.testprojevosoft.databinding.ActivityMainBinding
import com.testprojevosoft.fragments.PhoneNumberInputFragment
import com.testprojevosoft.fragments.VerificationFragment

class AuthorizationActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        val currentFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        if (currentFragment == null) {
            goToPhoneNumberInput()
        }
    }

    override fun goToPhoneNumberInput() {
        val phoneNumberFragment = PhoneNumberInputFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, phoneNumberFragment)
            .commit()
    }

    override fun goToSmsVerification(phoneNumber: String) {
        val verificationFragment = VerificationFragment.newInstance(phoneNumber)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, verificationFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val PHONE_NUMBER_KEY = "phoneNumber"
    }
}