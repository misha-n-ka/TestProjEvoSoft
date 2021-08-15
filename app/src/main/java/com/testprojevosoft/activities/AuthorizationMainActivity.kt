package com.testprojevosoft.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.testprojevosoft.Navigator
import com.testprojevosoft.R
import com.testprojevosoft.TestApplication
import com.testprojevosoft.data.User
import com.testprojevosoft.databinding.ActivityMainBinding
import com.testprojevosoft.fragments.PhoneNumberInputFragment
import com.testprojevosoft.fragments.VerificationFragment
import kotlinx.coroutines.runBlocking

class AuthorizationMainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User
    private val settingsManager = TestApplication.getApplicationInstance().settingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // init user
        user = User()
        // get flag "if user pressed log out button in images list activity"
        val isUserLoggedOut = intent.getBooleanExtra(IS_USER_LOGGED_OUT, false)

        runBlocking {
            if(isUserLoggedOut) {
                // if user logged out, save this state to DataStore (settingsManager)
                settingsManager.saveAuthorizationState(false)
            } else {
                // if user didn't log out, get authorization state from DatsStore(settingsManager)
                // and set to user.isAuthorized
                user.isAuthorized = settingsManager.readAuthorizationState()
            }
        }

        // go to pictures list if user is logged in
        if (user.isAuthorized) {
            goToPicturesList()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
            .also { setContentView(it.root) }

        val currentFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainer.id)

        // go to phone input fragment if current fragment container is empty
        if (currentFragment == null) {
            goToPhoneNumberInput()
        }
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

    companion object {
        const val PHONE_NUMBER_KEY = "phoneNumber"
        const val IS_USER_LOGGED_OUT = "isUserLoggedOut"
    }
}