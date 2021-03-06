package com.fullpagedeveloper.githubuserapp.ui.users.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fullpagedeveloper.githubuserapp.data.Constant.Companion.AUTHKEY
import com.fullpagedeveloper.githubuserapp.data.ServiceGenerator
import com.fullpagedeveloper.githubuserapp.data.model.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel : ViewModel() {

    private val users = MutableLiveData<Users>()
    private val serviceGenerator = ServiceGenerator()
    val _users : MutableLiveData<Users> get() = users

    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun users(username: String) {
        loading.value = true
        serviceGenerator.getDetail(AUTHKEY, username).enqueue(
            object : Callback<Users> {
                override fun onResponse(call: Call<Users>, response: Response<Users>) {
                    if (response.isSuccessful) {
                        try {
                            users.value = response.body()
                            loading.value = false
                            error.value = false
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    } else {
                        if (response.code() in 400..511){
                            loading.value = false
                            error.value = true
                        }
                    }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    t.printStackTrace()
                    loading.value = false
                    error.value = true
                }

            })
    }
}