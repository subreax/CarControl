package com.subreax.hackaton.data.retrofit

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class NetworkCarTemplate(
    @SerializedName("unique_id")
    val uniqueId: UUID,
    val name: String,
    @SerializedName("type_key")
    val typeKey: String,
    val parts: List<UUID>
)