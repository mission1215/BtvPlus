package com.skb.bourbon_network.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.Response

class GeneralPagingSource<T : Any>(
    private val apiCall: suspend (page: Int, size: Int) -> Response<List<T>>,
    private val pageSize: Int = 20
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            val response = apiCall(page, params.loadSize)
            if (response.isSuccessful) {
                val data = response.body() ?: emptyList()
                LoadResult.Page(
                    data = data,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}