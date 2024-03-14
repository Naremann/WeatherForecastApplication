package com.example.weatherforecastapplication.ui.fav

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapplication.base.BaseFragment
import com.example.weatherforecastapplication.databinding.FragmentFavoriteBinding
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.data.api.ApiService
import com.example.weatherforecastapplication.data.db.LocationDao
import com.example.weatherforecastapplication.data.model.WeatherDataEntity
import com.example.weatherforecastapplication.data.repo.WeatherRepo
import com.example.weatherforecastapplication.data.repo.local.WeatherLocalSource
import com.example.weatherforecastapplication.data.repo.remote.WeatherRemoteSource
import com.example.weatherforecastapplication.ui.ResultState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment :BaseFragment<FragmentFavoriteBinding,FavoriteViewModel>(),Navigator{
    @Inject
    lateinit var locationDao: LocationDao
    private var favLocationAdapter=FavLocationAdapter()

    @Inject
    lateinit var apiService: ApiService
  /*  private val favoriteViewModel:FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(
            WeatherRepo.WeatherRepoImp(
                WeatherRemoteSource.WeatherRemoteSourceImp(apiService),
            WeatherLocalSource.WeatherLocalSourceImp(locationDao)))
    }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      viewDataBinding.lifecycleOwner=this
        viewDataBinding.viewModel=viewModel
        viewModel.navigator=this
        observeToViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        favLocationAdapter.onItemClickListener=object :FavLocationAdapter.OnItemClickListener{
            override fun onDeleteClick(weatherDataEntity: WeatherDataEntity) {
                viewModel.deleteFavLocation(weatherDataEntity)
            }

            override fun onItemClick(weatherDataEntity: WeatherDataEntity) {
                navigateToFavDetailsFragment(weatherDataEntity)
            }

        }
        viewDataBinding.favRecyclerView.adapter=favLocationAdapter
    }

    private fun navigateToFavDetailsFragment(weatherDataEntity: WeatherDataEntity) {
        val action=FavoriteFragmentDirections.actionFavoriteFragmentToFavDetailsFragment(weatherDataEntity)
        findNavController().navigate(action)
    }

    private fun observeToViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.favLocation.collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                     //   binding.progressBar.visibility=View.VISIBLE
                    }
                    is ResultState.Success -> {
                       // binding.progressBar.visibility = View.GONE
                        favLocationAdapter.submitList(state.locationModel)
                    }
                    is ResultState.Error -> {
                        //binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                    }


                    else -> {}
                }
            }
        }
    }

    override fun getFragmentView(): View {
        return viewDataBinding.root
    }

    override fun initViewModel(): FavoriteViewModel {
        return ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite
    }

    override fun navigateToMapSelectionFragment() {
        val action=FavoriteFragmentDirections.actionFavoriteFragmentToMapSelectionFragment(true)
        findNavController().navigate(action)
    }

}

