package com.example.test

import android.content.Context

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class ClusterRenderer(context: Context?, map: GoogleMap?, clusterManager: ClusterManager<MyItem>?)
    : DefaultClusterRenderer<MyItem>(context, map, clusterManager) {

    init {
        clusterManager?.renderer = this
    }

}