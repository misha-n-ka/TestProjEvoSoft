package com.testprojevosoft.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.testprojevosoft.Navigator
import com.testprojevosoft.R
import com.testprojevosoft.databinding.FragmentEnterPhoneNumberBinding
import com.testprojevosoft.viewModels.PhoneNumberFragmentViewModel

private const val TAG = "NumberInputFragment"

class PhoneNumberInputFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mBinding: FragmentEnterPhoneNumberBinding
    private lateinit var mPhoneNumber: String
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

        val phoneNumberWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged not realised")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val validPhoneNumber: String? =
                    Regex("^[+]?[0-9]{10}$").find(s.toString().trim())?.value
                if (validPhoneNumber == null) {
                    mBinding.etPhoneNumber.error = "Enter valid phone number"
                } else {
                    val colorEnabledButton = ContextCompat.getColor(requireContext(), R.color.gray)
                    mBinding.btnGetSmsCode.setBackgroundColor(colorEnabledButton)
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
            if (mBinding.etPhoneNumber.error == null &&
                mPhoneNumberViewModel.isNumberInBase(mPhoneNumber)
            ) {
                mBinding.btnGetSmsCode.visibility = View.INVISIBLE
                mBinding.progressBar.visibility = View.VISIBLE

                mPhoneNumberViewModel.requestVerificationCode()
                (activity as Navigator).goToSmsVerification(mPhoneNumber)
            }
        }
    }

    companion object {
        fun newInstance(): PhoneNumberInputFragment {
            return PhoneNumberInputFragment()
        }
    }
}