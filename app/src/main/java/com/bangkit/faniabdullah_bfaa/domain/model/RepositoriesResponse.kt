package com.bangkit.faniabdullah_bfaa.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoriesResponse(
    val id: Int,
    val name: String,
    val stargazers_count: Int,
    val description: String,
) : Parcelable
