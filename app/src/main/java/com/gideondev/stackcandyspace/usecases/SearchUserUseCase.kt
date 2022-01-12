package com.gideondev.stackcandyspace.usecases
import com.gideondev.stackcandyspace.repository.StackExchangeCandySpaceRepository
import javax.inject.Inject

/**
 * A use-case to search for user from Stack Exchange API.
 * @author Gideon Oyediran
 */
class SearchUserUseCase @Inject constructor(private val repository: StackExchangeCandySpaceRepository) {
    suspend operator fun invoke(
        query: String,
        siteToVisit: String = "stackoverflow",
        sortBy: String = "reputation",
        orderBy: String = "desc"
    ) = repository.searchUser(
        order = orderBy,
        site = siteToVisit,
        inName = query,
        sort = sortBy
    )
}
