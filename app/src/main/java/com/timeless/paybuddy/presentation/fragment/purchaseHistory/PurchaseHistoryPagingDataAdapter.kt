package com.timeless.paybuddy.presentation.fragment.purchaseHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timeless.paybuddy.R
import com.timeless.paybuddy.domain.model.PurchaseHistory

class PurchaseHistoryPagingDataAdapter(

) : PagingDataAdapter<PurchaseHistory, PurchaseHistoryPagingDataAdapter.PurchaseHistoryViewHolder>(
    Companion
) {

    companion object : DiffUtil.ItemCallback<PurchaseHistory>() {
        override fun areItemsTheSame(oldItem: PurchaseHistory, newItem: PurchaseHistory): Boolean {
            return oldItem.transactionID == newItem.transactionID
        }

        override fun areContentsTheSame(
            oldItem: PurchaseHistory,
            newItem: PurchaseHistory
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class PurchaseHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: PurchaseHistoryViewHolder, position: Int) {
        val purchaseHistory = getItem(position) ?: return

        holder.itemView.apply {
            when (purchaseHistory.networkId) {
                "MTN" -> {
                    Glide.with(context).load(R.drawable.ic_mtn_colored).into(
                        this.findViewById(R.id.networkIDimagePurchased)
                    )
                }
                "GLO" -> {
                    Glide.with(context).load(R.drawable.ic_glo_colored).into(
                        this.findViewById(R.id.networkIDimagePurchased)
                    )
                }
                "9MOBILE" -> {
                    Glide.with(context).load(R.drawable.ic_9mobile_colored).into(
                        this.findViewById(R.id.networkIDimagePurchased)
                    )
                }
                "AIRTEL" -> {
                    Glide.with(context).load(R.drawable.ic_airtel_colored).into(
                        this.findViewById(R.id.networkIDimagePurchased)
                    )
                }
            }
            this.findViewById<TextView>(R.id.purchaseNetworkID)
                .text = "${context.getString(R.string.you_topped_up)} ${purchaseHistory.networkId}"
            this.findViewById<TextView>(R.id.purchaseAmount)
                .text = "\u20a6${purchaseHistory.amount}"
            this.findViewById<TextView>(R.id.purchaseDate)
                .text = purchaseHistory.date
            this.findViewById<TextView>(R.id.phoneNumberPurchasedFor)
                .text = purchaseHistory.mobileNumber

            setOnClickListener {
                onItemClickListener?.let {
                    it(purchaseHistory)
                }
            }
        }
    }

    private var onItemClickListener : ((PurchaseHistory) -> Unit)? = null
    fun setOnClickListener( listener: (PurchaseHistory) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseHistoryViewHolder {
        return PurchaseHistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.purchase_history_cardview, parent, false)
        )
    }

}