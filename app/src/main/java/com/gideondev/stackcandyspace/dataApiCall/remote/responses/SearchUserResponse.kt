package com.gideondev.stackcandyspace.dataApiCall.remote.responses

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

	@field:SerializedName("quota_max")
	val quotaMax: Int? = null,

	@field:SerializedName("quota_remaining")
	val quotaRemaining: Int? = null,

	@field:SerializedName("has_more")
	val hasMore: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("reputation_change_quarter")
	val reputationChangeQuarter: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("last_modified_date")
	val lastModifiedDate: Int? = null,

	@field:SerializedName("last_access_date")
	val lastAccessDate: Int? = null,

	@field:SerializedName("reputation")
	val reputation: Int? = null,

	@field:SerializedName("badge_counts")
	val badgeCounts: BadgeCounts? = null,

	@field:SerializedName("creation_date")
	val creationDate: Int? = null,

	@field:SerializedName("display_name")
	val displayName: String? = null,

	@field:SerializedName("reputation_change_year")
	val reputationChangeYear: Int? = null,

	@field:SerializedName("accept_rate")
	val acceptRate: Int? = null,

	@field:SerializedName("is_employee")
	val isEmployee: Boolean? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("account_id")
	val accountId: Int? = null,

	@field:SerializedName("user_type")
	val userType: String? = null,

	@field:SerializedName("website_url")
	val websiteUrl: String? = null,

	@field:SerializedName("reputation_change_week")
	val reputationChangeWeek: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("reputation_change_day")
	val reputationChangeDay: Int? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("reputation_change_month")
	val reputationChangeMonth: Int? = null
)

data class BadgeCounts(

	@field:SerializedName("gold")
	val gold: Int? = null,

	@field:SerializedName("silver")
	val silver: Int? = null,

	@field:SerializedName("bronze")
	val bronze: Int? = null
)
