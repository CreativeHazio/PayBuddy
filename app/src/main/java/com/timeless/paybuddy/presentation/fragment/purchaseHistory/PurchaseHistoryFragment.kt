package com.timeless.paybuddy.presentation.fragment.purchaseHistory

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.timeless.paybuddy.R
import com.timeless.paybuddy.databinding.FragmentPurchaseHistoryBinding

class PurchaseHistoryFragment : Fragment() {

    private var _binding : FragmentPurchaseHistoryBinding? = null
    private val binding get() = _binding!!

    private val args : PurchaseHistoryFragmentArgs by navArgs()

    var pageHeight = 1120
    var pageWidth = 792

    lateinit var bmp: Bitmap
    lateinit var scaledbmp: Bitmap

    var PERMISSION_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPurchaseHistoryBinding.inflate(layoutInflater, container, false)

        val purchaseHistory = args.purchaseHistory
        binding.apply {
            when (purchaseHistory.networkId) {
                "MTN" -> {
                    Glide.with(requireContext()).load(R.drawable.ic_mtn_colored).into(
                        networkIDimage
                    )
                }
                "GLO" -> {
                    Glide.with(requireContext()).load(R.drawable.ic_glo_colored).into(
                        networkIDimage
                    )
                }
                "9MOBILE" -> {
                    Glide.with(requireContext()).load(R.drawable.ic_9mobile_colored).into(
                        networkIDimage
                    )
                }
                "AIRTEL" -> {
                    Glide.with(requireContext()).load(R.drawable.ic_airtel_colored).into(
                        networkIDimage
                    )
                }
            }
            networkProvider.text = purchaseHistory.networkId
            plan.text = purchaseHistory.plan
            amount.text = purchaseHistory.amount
            phone.text = purchaseHistory.mobileNumber
            transactionId.text = purchaseHistory.transactionID
            status.text = purchaseHistory.status
            date.text = purchaseHistory.date
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}