package com.timeless.paybuddy.presentation.fragment.userReg

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.timeless.paybuddy.R
import com.timeless.paybuddy.databinding.FragmentLoginBinding
import com.timeless.paybuddy.presentation.shared.FlowCollector.collectLatestLifecycleFlow

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var dialog: AlertDialog

    private val viewModel : LoginFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        binding.apply {
            loginTxt.startAnimation(
                AnimationUtils
                    .loadAnimation(requireContext(), R.anim.top_to_down_anim)
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            loginWithEmailPasswordBtn.setOnClickListener {
                if (verifyEmail() && verifyInputs()) {
                    viewModel.logInWithEmailAndPassword(
                        emailEdt.text.toString(),
                        passwordEdt.text.toString()
                    )
                }
            }

            collectLatestLifecycleFlow(viewModel.isLoading) {
                if (it) {
                    showProgressBar()
                } else {
                    hideProgressBar()
                }
            }

            collectLatestLifecycleFlow(viewModel.isUserExist) {
                if (!it) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.incorrect_email_or_password),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            collectLatestLifecycleFlow(viewModel.isEmailVerified) {
                if (!it) {
                    showVerifyEmailDialog()
                }
            }

            collectLatestLifecycleFlow(viewModel.isWalletCreated) {
                if (it) {
                    findNavController().navigate(
                        LoginFragmentDirections.actionLoginFragmentToMainActivity()
                    )
                } else {
                    //TODO: Prompt user with a snackbar to retry creating an account and redo viewmodel.login
                }
            }

            forgotPasswordBtn.setOnClickListener {
                //TODO: Send password reset email and change user password field to new password
            }

            newUserBtn.setOnClickListener {
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                )
            }

        }
    }

    private fun showVerifyEmailDialog() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.verify_email_dialog, null)

        val okBtn  = dialogView.findViewById<MaterialButton>(R.id.okBtn)
        okBtn.setOnClickListener {
            dialog.dismiss()
        }

        val resendVerificationBtn  = dialogView.findViewById<MaterialButton>(R.id.resendVerificationBtn)
        resendVerificationBtn.setOnClickListener {

            if (viewModel.sendEmailVerification()) {
                Toast.makeText(
                    requireContext(), getString(R.string.email_verification_sent), Toast.LENGTH_LONG
                ).show()
                dialog.dismiss()
            } else {
                Toast.makeText(
                    requireContext(), getString(R.string.email_verification_not_sent), Toast.LENGTH_LONG
                ).show()
                dialog.dismiss()
            }

        }

        builder.setView(dialogView)
        dialog = builder.create()
        dialog.show()
    }

    private fun showProgressBar(){
        binding.logInProgressBar.visibility = View.VISIBLE
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
         
    }
    private fun hideProgressBar(){
        binding.logInProgressBar.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun verifyEmail() : Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        //TODO: Add email regex validator
        if (!(binding.emailEdt.text.toString().matches(emailRegex.toRegex()))) {
            //TODO: Show invalid email toast
            Toast.makeText(
                requireContext(),
                getString(R.string.invalid_email),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun verifyInputs() : Boolean {
        binding.apply {

            if (emailEdt.text.toString().isEmpty()){
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_input),
                    Toast.LENGTH_LONG
                ).show()
                return false
            }
            if (passwordEdt.text.toString().isEmpty()){
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_input),
                    Toast.LENGTH_LONG
                ).show()
                return false
            }

        }

        return true
    }

}