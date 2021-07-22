package ir.moeindeveloper.brainzplaces.places.action

import ir.moeindeveloper.brainzplaces.core.platform.action.BaseAction

sealed class PlaceActions: BaseAction {
    data class Search(val query: String): PlaceActions()
}
