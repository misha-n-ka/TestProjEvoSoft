package com.testprojevosoft.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.testprojevosoft.Navigator
import com.testprojevosoft.R
import com.testprojevosoft.databinding.FragmentEnterPhoneNumberBinding
import com.testprojevosoft.viewModels.PhoneNumberFragmentViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

private const val TAG = "NumberInputFragment"

class PhoneNumberInputFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mBinding: FragmentEnterPhoneNumberBinding
    private lateinit var mPhoneNumber: String
    private var isButtonEnabled: Boolean = false
    private val mPhoneNumberViewModel: PhoneNumberFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()

        if (mBinding.etPhoneNumber.text.isNullOrEmpty()) {
            mBinding.btnGetSmsCode.isEnabled = false
        }
        updateButtonColor(isButtonEnabled)
        resetUI()

        val phoneNumberWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged not realised")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mBinding.btnGetSmsCode.isEnabled = !s.isNullOrEmpty()
                val validPhoneNumber: String? =
                    Regex("^[+]?[0-9]{10}$").find(s.toString().trim())?.value
                if (validPhoneNumber == null) {
                    isButtonEnabled = false
                    updateButtonColor(isButtonEnabled)
                    mBinding.etPhoneNumber.error = "Enter valid phone number"
                } else {
                    isButtonEnabled = true
                    updateButtonColor(isButtonEnabled)
                    mPhoneNumber = "+7$validPhoneNumber"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged not realised")
            }
        }

        mBinding.etPhoneNumber.apply {
            addTextChangedListener(phoneNumberWatcher)
        }

        mBinding.btnGetSmsCode.setOnClickListener {
            if (mBinding.etPhoneNumber.error == null) {
                it.visibility = View.INVISIBLE
                mBinding.progressBar.visibility = View.VISIBLE
            }
            if (mPhoneNumberViewModel.isNumberInBase(mPhoneNumber)) {
                GlobalScope.launch(Dispatchers.Main) {
                    mPhoneNumberViewModel.requestVerificationCode()
                    (activity as Navigator).goToSmsVerification(mPhoneNumber)
                }
            } else {
                resetUI()
                Toast.makeText(context, R.string.no_number_in_base, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateButtonColor(isEnabled: Boolean) {
        when(isEnabled) {
            true -> mBinding.btnGetSmsCode.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.purple_500)
            false -> mBinding.btnGetSmsCode.backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.gray)
        }
    }

    private fun resetUI() {
        mBinding.btnGetSmsCode.visibility = View.VISIBLE
        mBinding.progressBar.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance(): PhoneNumberInputFragment {
            return PhoneNumberInputFragment()
        }
    }
}