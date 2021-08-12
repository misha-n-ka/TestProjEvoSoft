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

class AuthorizationMainActivity : AppCompatActivity(), Navigator, Authorizable {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        // init user
        user = User()
        // get authorization info from saved state
        user.isAuthorized = savedInstanceState?.getBoolean(SAVED_STATE_IS_AUTHORIZED) ?: false
        // go to pictures list if user is logged in
        if (user.isAuthorized) {
            goToPicturesList()
        }

        val currentFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        // go to phone input fragment if current fragment container is empty
        if (currentFragment == null) {
            goToPhoneNumberInput()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_STATE_IS_AUTHORIZED, user.isAuthorized)
    }

    // launching phone number input fragment
    override fun goToPhoneNumberInput() {
        val phoneNumberFragment = PhoneNumberInputFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(binding.fragmentContainer.id, phoneNumberFragment)
            .commit()
    }

    // launching sms verification fragment
    override fun goToSmsVerification(phoneNumber: String) {
        val verificationFragment = VerificationFragment.newInstance(phoneNumber)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, verificationFragment)
            .addToBackStack(null)
            .commit()
    }

    // launching pictures list activity
    override fun goToPicturesList() {
        val intent = Intent(this, ImagesListActivity::class.java)
        startActivity(intent)
    }

    // set logged in user field
    override fun login(isAuthorized: Boolean) {
        user.isAuthorized = isAuthorized
    }

    companion object {
        const val PHONE_NUMBER_KEY = "phoneNumber"
        const val SAVED_STATE_IS_AUTHORIZED = "isAuthorized"
    }
}