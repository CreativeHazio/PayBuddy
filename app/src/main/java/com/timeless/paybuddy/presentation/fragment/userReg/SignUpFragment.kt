package com.timeless.paybuddy.presentation.fragment.userReg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.timeless.paybuddy.R
import com.timeless.paybuddy.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel : SignUpFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        binding.apply {

            signupTxt.startAnimation(AnimationUtils
                .loadAnimation(requireContext(), R.anim.top_to_down_anim)
            )

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            signupWithEmailPasswordBtn.setOnClickListener {
                if (verifyEmail() && verifyInputs()) {

                    viewModel.signUpWithEmailAndPassword(
                        emailEdt.text.toString(),
                        usernameEdt.text.toString(),
                        passwordEdt.text.toString(),
                        phoneNumberEdt.text.toString(),
                        providerSpinner.selectedItem.toString()
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

            collectLatestLifecycleFlow(viewModel.isEmailExists) {
                if (it) {
                    //TODO: Display a snackbar to go to login page
                    Snackbar.make(
                        view, getString(R.string.email_already_exists), Snackbar.LENGTH_LONG
                    ).setAction(getString(R.string.ok)) {
                        findNavController().navigate(
                            SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                        )
                    }.show()
                }
            }

            collectLatestLifecycleFlow(viewModel.isEmailVerificationSent) {
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.email_verification_sent),
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                    )
                }
            }

            alreadyAUserBtn.setOnClickListener {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                )
            }

        }

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
            if (usernameEdt.text.toString().isEmpty()){
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
            if (phoneNumberEdt.text.toString().isEmpty()){
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

    private fun showProgressBar(){
        binding.signUpProgressBar.visibility = View.VISIBLE
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
    private fun hideProgressBar(){
        binding.signUpProgressBar.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun <T> Fragment.collectLatestLifecycleFlow(flow : Flow<T>, collect : suspend (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }

}