package com.davidxie.dotify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val username: String,
    val firstName: String,
    val lastName: String,
    val hasNose: Boolean,
    val platform: Float,
    val profilePicURL: String,
): Parcelable