package com.gideondev.stackcandyspace.test_common

import com.gideondev.stackcandyspace.dataApiCall.remote.responses.SearchUserResponse
import com.gideondev.stackcandyspace.model.BadgeCounts
import com.gideondev.stackcandyspace.model.User

class MockTestUtil {
    companion object {
        fun createSearchResponse(count : Int): SearchUserResponse {
            return SearchUserResponse(
                hasMore = true,
                quotaMax = 300,
                quotaRemaining = 258,
                items = createUser(count)
                )
        }

        fun createUser(count: Int): List<User> {
            return (0 until count).map {
                User(
                    accountId = 53121,
                    isEmployee = true,
                    displayName = "Gideon Brian",
                    acceptRate = 100,
                    userType = "registered",
                    userId = 158779,
                    badgeCounts = createUserBadgeCount(),
                )
            }
        }

        fun createUserBadgeCount(): BadgeCounts {
            return BadgeCounts(
                silver = 99,
                bronze = 146,
                gold = 12
            )
        }

    }
}
