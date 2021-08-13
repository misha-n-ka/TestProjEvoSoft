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
import com.testprojevosoft.utils.PhoneNumberFormatter
import com.testprojevosoft.viewModels.PhoneNumberFragmentViewModel
import kotlinx.coroutines.*
import java.text.NumberFormat
import kotlin.coroutines.coroutineContext

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

        // valid input phoneNumber
        var isValidPhoneNumber = false

        // reset UI to default state
        resetUI()

        // init text changed listener to EditText
        mBinding.etPhoneNumber.addTextChangedListener(object :  TextWatcher {
            val pattern = "(###)###-##-##"
            var editTextText = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged not realised")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().equals(mBinding.etPhoneNumber.text)) {
                    mBinding.etPhoneNumber.removeTextChangedListener(this)

                    val formattedNumber = PhoneNumberFormatter.format(s.toString(), pattern)
                    val isNumberInputted =
                        formattedNumber.replaceFirst("(", "").isNotEmpty()

                    if (isNumberInputted) {
                        editTextText = formattedNumber
                        isValidPhoneNumber = true
                    } else {
                        isValidPhoneNumber = false
                    }
                    mBinding.etPhoneNumber.setText(editTextText)
                    mBinding.etPhoneNumber.setSelection(editTextText.length)
                    mBinding.etPhoneNumber.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged not realised")
            }
        })

        // set onClickListener for request cmc-code button
        mBinding.btnGetSmsCode.setOnClickListener { button ->
            when (isValidPhoneNumber) {
                false -> {
                    mBinding.etPhoneNumber.error = "Enter valid phone number"
                }
                true -> {
                    // start progress bar if phone number is valid
                    button.visibility = View.INVISIBLE
                    mBinding.progressBar.visibility = View.VISIBLE

                    mPhoneNumber = "+7" +
                            mBinding.etPhoneNumber.text?.filter { it.isDigit() }.toString()
                    // check if phone number is in database
                    if (mPhoneNumberViewModel.isNumberInBase(mPhoneNumber)) {
                        //launching coroutine for request verification code and
                        // then start verification activity
                        GlobalScope.launch(Dispatchers.Main) {
                            mPhoneNumberViewModel.requestVerificationCode()
                            (activity as Navigator)
                                .goToSmsVerification("+7" + mBinding.etPhoneNumber.text.toString())
                        }
                    } else {
                        // if phone number is not in database
                        resetUI()
                        Toast.makeText(context, R.string.no_number_in_base, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    // reset UI to default state
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