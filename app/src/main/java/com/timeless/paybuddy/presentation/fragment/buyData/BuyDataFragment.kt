package com.timeless.paybuddy.presentation.fragment.buyData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.timeless.paybuddy.databinding.FragmentBuyDataBinding

class BuyDataFragment : Fragment() {
    private var _binding : FragmentBuyDataBinding? = null
    private val binding get() = _binding!!

    private val args : BuyDataFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuyDataBinding.inflate(layoutInflater, container, false)

        val networkID = args.networkID
        binding.apply {
            Glide.with(requireContext()).load(networkID.imageColored).into(networkIDimage)
            networkProviderEdt.setText(networkID.name)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            // TODO: Load User's default phone number when box is checked
            forMeCheckBox.addOnCheckedStateChangedListener { checkBox, state ->
                phoneNumberEdt.isEnabled = state != 1
                if (state == 1) {
                    phoneNumberEdt.setText(args.user.mobileNumber)
                } else {
                    phoneNumberEdt.setText("")
                }
            }

        }

    }

}