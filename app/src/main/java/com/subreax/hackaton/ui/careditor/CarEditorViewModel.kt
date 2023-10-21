package com.subreax.hackaton.ui.careditor

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.subreax.hackaton.data.CarPart
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarEditorViewModel @Inject constructor(
) : ViewModel() {
    private var _parts = mutableStateListOf<CarPart>()
    val parts: SnapshotStateList<CarPart> = _parts


}