package com.timeless.paybuddy.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.timeless.paybuddy.R
import com.timeless.paybuddy.databinding.FragmentHomeBinding
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.presentation.fragment.buyData.BuyDataAdapter
import com.timeless.paybuddy.presentation.fragment.purchaseHistory.PurchaseHistoryPagingDataAdapter
import com.timeless.paybuddy.presentation.shared.FlowCollector.collectLatestLifecycleFlow
import com.timeless.paybuddy.presentation.shared.NetworkEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var buyDataAdapter : BuyDataAdapter
    private lateinit var purchaseHistoryAdapter : PurchaseHistoryPagingDataAdapter

    private val viewModel : HomeFragmentViewModel by viewModels()
    private lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.apply {

            //TODO: Change caret color
            userProfileImage.startAnimation(AnimationUtils
                .loadAnimation(requireContext(), R.anim.top_to_down_anim)
            )

            userNameGreeting.startAnimation(AnimationUtils
                .loadAnimation(requireContext(), R.anim.top_to_down_anim)
            )

            buyDataAdapter = BuyDataAdapter()
            buyDataRecyclerView.adapter = buyDataAdapter
            buyDataRecyclerView.layoutManager = GridLayoutManager(
                requireContext(), 3, GridLayoutManager.VERTICAL, false
            )

            purchaseHistoryAdapter = PurchaseHistoryPagingDataAdapter()
            purchaseHistoryRecyclerView.adapter = purchaseHistoryAdapter
            purchaseHistoryRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.reloadAllData()
            binding.swipeToRefresh.isRefreshing = false
        }

        collectLatestLifecycleFlow(viewModel.networkEventFlow) {
            when (it) {
                is NetworkEvent.Available -> {
                    println("Network is fire")
                }
                is NetworkEvent.Unavailable -> {
                    Snackbar.make(
                        requireActivity().findViewById(R.id.main_activity_view),
                        it.errorMessage,
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }

                else -> {}
            }
        }

        collectLatestLifecycleFlow(viewModel.isLoading) {
            if (it) {
                showProgressBar()
            } else {
                hideProgressBar()
            }
        }

        collectLatestLifecycleFlow(viewModel.isUserBalanceLoading) {
            if (it) {
                showBalanceProgressBar()
            } else {
                hideBalanceProgressBar()
            }
        }

        collectLatestLifecycleFlow(viewModel.userBalance) {
            binding.apply {
                userBalance.setText(it.toString())
            }
        }

        collectLatestLifecycleFlow(viewModel.userDetails) {
            user = it
            binding.apply {
                userNameGreeting.text = "Hi ${it.username}"
            }
        }

        collectLatestLifecycleFlow(viewModel.purchaseHistoryFlow) {
            purchaseHistoryAdapter.submitData(it)
            // TODO: Save purchase history to local database
        }

        collectLatestLifecycleFlow(purchaseHistoryAdapter.loadStateFlow) {
            when(it.refresh) {
                LoadState.Loading -> showPurchaseHistoryShimmer()
                else -> {
                    hidePurchaseHistoryShimmer()
                }
            }
        }

        binding.apply {
//            userNameGreeting.text = "Hi ${viewModel.getUserData().username}"
//            TODO: Use viewmodel.getUserData() instead
            userProfileImage.setOnClickListener {
                //TODO: Pass in user from userDetails in viewmodel as args
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(
                        user
                    )
                )
            }

            fundAccountButton.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToFundAccountFragment(
                        user
                    )
                )
            }

            referFriendsButton.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(
                        user
                    )
                )
            }

            closeReferCardBtn.setOnClickListener {
                referFriendsCardview.visibility = View.GONE
            }

            buyDataAdapter.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToBuyDataFragment(
                        user,
                        it
                    )
                )
            }

            purchaseHistoryAdapter.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPurchaseHistoryFragment(
                        it
                    )
                )
            }
        }

    }

    private fun showProgressBar() {
        binding.apply {
            userNameGreeting.visibility = View.GONE
            usernameProgressBar.visibility = View.VISIBLE
            homeFragmentProgressBar.visibility = View.VISIBLE
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            homeFragmentProgressBar.visibility = View.GONE
            usernameProgressBar.visibility = View.GONE
            userNameGreeting.visibility = View.VISIBLE
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun showBalanceProgressBar() {
        binding.userBalanceProgressBar.visibility = View.VISIBLE
        binding.userBalanceTxl.visibility = View.GONE
    }

    private fun hideBalanceProgressBar() {
        binding.userBalanceProgressBar.visibility = View.GONE
        binding.userBalanceTxl.visibility = View.VISIBLE
    }

    // TOD0: Pagination for purchase history shimmer
    private fun showPurchaseHistoryShimmer() {
        binding.apply {
            purchaseHistoryShimmer.startShimmer()
            purchaseHistoryShimmer.visibility = View.VISIBLE
        }
    }
    private fun hidePurchaseHistoryShimmer() {
        binding.apply {
            purchaseHistoryShimmer.stopShimmer()
            purchaseHistoryShimmer.visibility = View.GONE
        }
    }
}