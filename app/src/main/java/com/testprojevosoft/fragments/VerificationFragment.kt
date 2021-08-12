package com.testprojevosoft.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.testprojevosoft.Authorizable
import com.testprojevosoft.Navigator
import com.testprojevosoft.R
import com.testprojevosoft.activities.AuthorizationMainActivity
import com.testprojevosoft.anim.ShakeError
import com.testprojevosoft.databinding.FragmentVerificationBinding
import com.testprojevosoft.viewModels.VerificationViewModel
import kotlinx.coroutines.*


private const val TAG = "Verification"

class VerificationFragment : Fragment() {

    private lateinit var mBinding: FragmentVerificationBinding
    private lateinit var mPhoneNumber: String
    private val mVerificationViewModel: VerificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get phone number from arguments bundle
        mPhoneNumber = arguments?.getString(AuthorizationMainActivity.PHONE_NUMBER_KEY) as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentVerificationBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set observer to LiveData timer remaining value
        mVerificationViewModel.remainingSecondsLiveData.observe(viewLifecycleOwner,
            Observer { remainingSeconds ->
                mBinding.tvTimer.text = getString(R.string.timerText, remainingSeconds.toString())
            })

        // set observer to LiveData when timer elapsed
        mVerificationViewModel.isTimerElapsed.observe(viewLifecycleOwner,
            Observer { isTimerElapsed ->
                if (isTimerElapsed) {
                    mBinding.btnRetry.setTextColor(R.attr.colorPrimary)
                    mBinding.btnRetry.isEnabled = true
                }
            })

        // set onClickListener for retry button to retry requestValidation code
        mBinding.btnRetry.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                mVerificationViewModel.retryVerificationCode()
            }
            // reset timer
            mVerificationViewModel.startTimer(60, 1f)
        }

        // start timer when fragment view created
        mVerificationViewModel.startTimer(60, 1f)
        // set formatted phone number to TextView
        val formattedPhoneNumber = formatPhoneNumber(mPhoneNumber)
        mBinding.tvPhoneNumber.text = formattedPhoneNumber
        // setting up EditText's watchers
        setupCodeInputs()
    }

    private fun formatPhoneNumber(number: String): String {
        number.split("").toMutableList().apply {
            add(2, "(")
            add(6, ")")
            add(10, "-")
            add(13, "-")
            joinToString()
        }
        return number
    }

    // setting up EditText's watchers
    private fun setupCodeInputs() {
        mBinding.etInputCode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    mBinding.etInputCode2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        mBinding.etInputCode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    mBinding.etInputCode3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        mBinding.etInputCode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    mBinding.etInputCode4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        mBinding.etInputCode4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    // get input code
                    val inputCode: String =
                        "${mBinding.etInputCode1.text}${mBinding.etInputCode2.text}" +
                                "${mBinding.etInputCode3.text}${mBinding.etInputCode4.text}"

                    // start coroutine for validation sms-code and go to ImagesListActivity
                    GlobalScope.launch(Dispatchers.IO) {
                        val isValid =
                            withContext(Dispatchers.IO) {
                                mVerificationViewModel.isValidCode(inputCode)
                            }
                        if (isValid) {
                            (activity as Authorizable).login(true)
                            (activity as Navigator).goToPicturesList()
                            requireActivity().finish()
                        } else {
                            // if validation sms-code failed
                            mBinding.etInputCode1.requestFocus()
                            startShakeError(mBinding)
                        }

                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mVerificationViewModel.cancelTimer()
    }

    private fun startShakeError(binding: FragmentVerificationBinding) {
        val context = activity?.applicationContext as Context

        //reset text of all code placeholders
        binding.apply {
            etInputCode1.text.clear()
            etInputCode2.text.clear()
            etInputCode3.text.clear()
            etInputCode4.text.clear()
        }
        // change color of code placeholders
        binding.apply {
            etInputCode1.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_error))
            etInputCode2.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_error))
            etInputCode3.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_error))
            etInputCode4.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_error))
        }

        //starting the error animation
        binding.apply {
            etInputCode1.startAnimation(ShakeError.shakeError())
            etInputCode2.startAnimation(ShakeError.shakeError())
            etInputCode3.startAnimation(ShakeError.shakeError())
            etInputCode4.startAnimation(ShakeError.shakeError())
        }
    }

    companion object {
        fun newInstance(phoneNumber: String): VerificationFragment {
            val args = Bundle().apply {
                putString(AuthorizationMainActivity.PHONE_NUMBER_KEY, phoneNumber)
            }

            return VerificationFragment().apply {
                arguments = args
            }
        }
    }
}