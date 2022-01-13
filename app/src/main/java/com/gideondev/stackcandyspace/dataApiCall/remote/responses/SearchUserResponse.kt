package com.gideondev.stackcandyspace.dataApiCall.remote.responses

import com.gideondev.stackcandyspace.model.User
import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

	@field:SerializedName("quota_max")
	val quotaMax: Int? = null,

	@field:SerializedName("quota_remaining")
	val quotaRemaining: Int? = null,

	@field:SerializedName("has_more")
	val hasMore: Boolean? = null,

	@field:SerializedName("items")
	val items: List<User?>? = null
)


