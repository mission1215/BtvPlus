package com.skb.mytvlibrary.navigator

sealed class Screens(val route: String) {
    data object Home : Screens("Home")
    data object Detail : Screens("Detail")

//    data object Home : Screens("Home")
//    data object Detail : Screens("Detail/{channelId}/{episodeId}") {
//        fun route(channelId: String, episodeId: String) = "Detail/$channelId/$episodeId"
//    }
//
//    data object Story : Screens("Story")
//    data object YoutubeDetail : Screens("YoutubeDetail") {
//        //fun route(youtubeVideoUrl: String) = "YoutubeDetail/$youtubeVideoUrl"
//        fun route() = "YoutubeDetail"
//    }
//
//    data object TestView : Screens("TestView/{testString}") {
//        fun route(testString: String) = "TestView/$testString"
//    }
}