package com.timeless.paybuddy.presentation.fragment.fundAccount

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.timeless.paybuddy.R
import com.timeless.paybuddy.databinding.FragmentFundAccountBinding

class FundAccountFragment : Fragment() {
    private var _binding : FragmentFundAccountBinding? = null
    private val binding get() = _binding!!

    private val args : FundAccountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFundAccountBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            userAccountNumber.text = args.user.wallet?.nuban
            userAccountName.text = args.user.wallet?.accountName
            userBankName.text = args.user.wallet?.bankName

            copyUserAccountNumberBtn.setOnClickListener {
                val clipboard =
                    requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(
                    getString(R.string.datahaven_account_number_txt),
                    userAccountNumber.text.toString()
                )
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, getString(R.string.account_number_has_been_copied_txt),
                    Toast.LENGTH_LONG).show()
            }

            payNowBtn.setOnClickListener {
                //TODO: Flutterwave Android UI to pay with card and Bank transfer
            }

        }
    }

}