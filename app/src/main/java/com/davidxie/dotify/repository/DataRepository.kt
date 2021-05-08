package edu.uw.echee.httppulldowntoupdate_sp21.repository

import com.davidxie.dotify.model.UserInfo
import com.davidxie.dotify.model.SongList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * https://raw.githubusercontent.com/echeeUW/codesnippets/master/email.json
 *
 * https://raw.githubusercontent.com/echeeUW/codesnippets/master/emails.json
 */

class DataRepository {

    private val songService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DotifyService::class.java)


    suspend fun getUser(): UserInfo = songService.getUser()
    suspend fun getSongList(): SongList = songService.getSongList()

}

interface DotifyService {

    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getUser(): UserInfo

    @GET("echeeUW/codesnippets/master/musiclibrary.json")
    suspend fun getSongList(): SongList


/*
    @GET("echeeUW/codesnippets/master/emails.json")
    suspend fun getUser(
        @Header("apiKey") apiKey: String,
        @Query("userId") userId: String,
    ): Inbox
*/


}
