package com.example.shift_tt

import android.util.Log
import com.example.shift_tt.Retrofit.model.Person
import com.example.shift_tt.db.MainDb
import com.example.shift_tt.db.entity.EntityItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun makeApiCall(call: Call<Person>, db: MainDb) {
    call.clone().enqueue(object : Callback<Person> {
        override fun onResponse(call: Call<Person>, response: Response<Person>) {
            if (response.isSuccessful) {
                val person = response.body()
                val result = person?.results?.get(0)

                val item = EntityItem(
                    name = result?.name?.first ?: "",
                    lastName = result?.name?.last ?: "",
                    picture = result?.picture?.large ?: "",
                    latitude = result?.location?.coordinates!!.latitude,
                    longitude = result.location.coordinates.longitude,
                    country = result.location.country,
                    state = result.location.state,
                    phoneNumber = result.phone
                )

                Thread {
                    db.getDao().insertItem(item)
                }.start()
            }
        }

        override fun onFailure(call: Call<Person>, t: Throwable) {
            Log.e("Error", "Failed to get user data", t)
        }
    })
}
