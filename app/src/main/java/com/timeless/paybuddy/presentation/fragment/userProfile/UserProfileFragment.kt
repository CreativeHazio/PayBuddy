package com.timeless.paybuddy.presentation.fragment.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timeless.paybuddy.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment() {

    private var _binding : FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            editProfileBtn.setOnClickListener {
                fullName.requestFocus()
                saveProfileBtn.visibility = View.VISIBLE
                fullName.isEnabled = true
                phoneNumber.isEnabled = true
            }

            saveProfileBtn.setOnClickListener {
                saveProfileBtn.visibility = View.GONE
                fullName.isEnabled = false
                phoneNumber.isEnabled = false
            }

        }
    }


}