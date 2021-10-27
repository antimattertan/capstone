package com.example.test

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(position: LatLng, title: String) : ClusterItem {
    override fun getTitle() : String {
        return title
    }

    override fun getSnippet(): String? {
        TODO("Not yet implemented")
    }

    override fun getPosition(): LatLng {
        return position
    }

    override fun equals(other: Any?): Boolean {
        if(other is MyItem) {
            return (other.position.latitude == position.latitude && other.position.longitude == position.longitude
                    && other.title == title)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = position.latitude.hashCode() * 31
        hash = hash * 31 + position.longitude.hashCode()
        hash = hash * 31 + title.hashCode()

        return hash
    }
}