package ir.moeindeveloper.brainzplaces


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import ir.moeindeveloper.brainzplaces.core.ext.appLog
import ir.moeindeveloper.brainzplaces.core.ext.longToast
import ir.moeindeveloper.brainzplaces.core.ext.shortToast
import ir.moeindeveloper.brainzplaces.core.ext.textChanges
import ir.moeindeveloper.brainzplaces.core.platform.fragment.BaseFragment
import ir.moeindeveloper.brainzplaces.core.state.UiStatus
import ir.moeindeveloper.brainzplaces.databinding.FragmentMapsBinding
import ir.moeindeveloper.brainzplaces.places.action.PlaceActions
import ir.moeindeveloper.brainzplaces.places.disappearAfterLifeSpan
import ir.moeindeveloper.brainzplaces.places.getAverageCoordinates
import ir.moeindeveloper.brainzplaces.places.markerTag
import ir.moeindeveloper.brainzplaces.places.toMarkerOptions
import ir.moeindeveloper.brainzplaces.places.viewModel.PlaceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MapsFragment : BaseFragment<FragmentMapsBinding>(R.layout.fragment_maps) {

    private val placeVM by viewModels<PlaceViewModel>()
    private var googleMap: GoogleMap? = null

    @ExperimentalTime
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = placeVM
        }

        lifecycleScope.launch {
            placeVM.places.collect { state ->
                appLog { "State ------> $state" }
                googleMap?.clear()
                when(state?.status) {
                    UiStatus.LOADING -> {
                        shortToast("Searching...")
                    }

                    UiStatus.Failure -> {
                        longToast(state.errorMessage ?: "Failed with an unknown error")
                    }

                    UiStatus.SUCCESS -> {
                        shortToast("Success")
                        state.data.whatIfNotNull { places ->
                            appLog { "places size -> ${places.size}" }
                            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(places.getAverageCoordinates()))

                            places.forEach { place ->
                                place.toMarkerOptions().whatIfNotNull { options ->
                                    val marker = googleMap?.addMarker(options)
                                    marker?.tag = place.markerTag()
                                    place.disappearAfterLifeSpan {
                                        activity?.runOnUiThread {
                                            marker?.remove()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @FlowPreview
    @ExperimentalTime
    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()
        binding.searchQuery.textChanges()
            .filterNot { it.isNullOrBlank() }
            .debounce(1000)
            .onEach { charSequence ->
                search(charSequence.toString())
            }
            .launchIn(lifecycleScope)
    }


    private fun search(query: String) {
        appLog { "query ------> $query" }
        placeVM.doAction(PlaceActions.Search(query = query))
    }
}