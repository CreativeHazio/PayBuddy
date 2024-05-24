package com.timeless.paybuddy.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import com.timeless.paybuddy.data.local.database.user.model.PurchaseHistoryEntity
import com.timeless.paybuddy.data.remote.firebase.model.FirebasePurchaseHistoryDto
import com.timeless.paybuddy.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PurchaseHistoryMapper {

    companion object {

        fun fromFirebasePurchaseHistoryDtoListToPurchaseHistoryList(
            firebasePurchaseHistoryDtoList: MutableList<FirebasePurchaseHistoryDto>
        ) : MutableList<PurchaseHistory> {

            return firebasePurchaseHistoryDtoList.map {
                PurchaseHistory(
                    it.transactionID!!,
                    it.networkID!!,
                    it.plan!!,
                    it.amount!!,
                    it.date!!,
                    it.phoneNumber!!,
                    it.status!!
                )
            }.toMutableList()
        }

        fun mapFromPurchaseHistoryEntityList(from: List<PurchaseHistoryEntity>): List<PurchaseHistory> {

            return from.map {
                PurchaseHistory(
                    it.transactionID,
                    it.networkID,
                    it.plan,
                    it.amount,
                    it.date,
                    it.phoneNumber,
                    it.status
                )
            }
        }

        fun mapFromPurchaseHistoryEntityPagingData (
            from : Flow<PagingData<PurchaseHistoryEntity>>
        ) : Flow<PagingData<PurchaseHistory>> {

            return from.map { pagingData ->
                pagingData.map {
                    PurchaseHistory(
                        it.transactionID,
                        it.networkID,
                        it.plan,
                        it.amount,
                        it.date,
                        it.phoneNumber,
                        it.status
                    )
                }
            }

        }

    }

}