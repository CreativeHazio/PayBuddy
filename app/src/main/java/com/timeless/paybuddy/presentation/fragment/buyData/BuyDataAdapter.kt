package com.timeless.paybuddy.presentation.fragment.buyData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.timeless.paybuddy.R
import com.timeless.paybuddy.domain.model.NetworkID

class BuyDataAdapter : RecyclerView.Adapter<BuyDataAdapter.BuyDataViewHolder>() {

    private val networkIdList = listOf(
        NetworkID("AIRTEL", R.drawable.ic_airtel, R.drawable.ic_airtel_colored),
        NetworkID("MTN", R.drawable.ic_mtn, R.drawable.ic_mtn_colored),
        NetworkID("GLO", R.drawable.ic_glo, R.drawable.ic_glo_colored),
        NetworkID("9MOBILE", R.drawable.ic_9mobile, R.drawable.ic_9mobile_colored)
    )

    inner class BuyDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyDataViewHolder {
        return BuyDataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.buy_data_items, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return networkIdList.size
    }

    override fun onBindViewHolder(holder: BuyDataViewHolder, position: Int) {
        val currentNetworkID = networkIdList[position]

        holder.itemView.apply {
            Glide.with(context).load(currentNetworkID.image).into(
                this.findViewById(R.id.networkIDimage)
            )

            setOnClickListener {
                onItemClickListener?.let {
                    it(currentNetworkID)
                }
            }
        }
    }

    private var onItemClickListener : ((NetworkID) -> Unit)? = null
    fun setOnClickListener( listener: (NetworkID) -> Unit) {
        onItemClickListener = listener
    }
}