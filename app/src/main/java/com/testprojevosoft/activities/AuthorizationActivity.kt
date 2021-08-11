package com.testprojevosoft.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testprojevosoft.Authorizable
import com.testprojevosoft.Navigator
import com.testprojevosoft.data.User
import com.testprojevosoft.databinding.ActivityMainBinding
import com.testprojevosoft.fragments.PhoneNumberInputFragment
import com.testprojevosoft.fragments.VerificationFragment

class AuthorizationActivity : AppCompatActivity(), Navigator, Authorizable {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        user = User()
        user.isAuthorized = savedInstanceState?.getBoolean(SAVED_STATE_IS_AUTHORIZED) ?: false
        if (user.isAuthorized) {
            goToPicturesList()
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        if (currentFragment == null) {
            goToPhoneNumberInput()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_STATE_IS_AUTHORIZED, user.isAuthorized)
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

    override fun goToPicturesList() {
        val intent = Intent(this, ImagesListActivity::class.java)
        startActivity(intent)
    }

    override fun login(isAuthorized: Boolean) {
        user.isAuthorized = isAuthorized
    }

    companion object {
        const val PHONE_NUMBER_KEY = "phoneNumber"
        const val SAVED_STATE_IS_AUTHORIZED = "isAuthorized"
    }
}