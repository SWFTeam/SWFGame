package com.example.swfgame

import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("firstname")
    private var firstname: String? = null
    @SerializedName("lastname")
    private var lastname: String? = null
    @SerializedName("isAdmin")
    private var isAdmin: Int? = null
    @SerializedName("last_login_at")
    private var last_login_at: String? = null
    @SerializedName("created_at")
    private var created_at: String? = null
    @SerializedName("experience")
    private var experience: Int? = null

    constructor(firstname: String, lastname: String, isAdmin: Int, last_login_at: String, created_at: String, experience: Int){
        this.firstname = firstname
        this.lastname = lastname
        this.isAdmin = isAdmin
        this.last_login_at = last_login_at
        this.created_at = created_at
        this.experience = experience
    }

    fun getFirstname(): String? {
        return firstname
    }
    fun setFirstname(firstname: String){
        this.firstname = firstname
    }

    fun getLastname(): String? {
        return lastname
    }
    fun setLastname(lastname: String){
        this.lastname = lastname
    }

    fun getIsAdmin(): Int? {
        return isAdmin
    }
    fun setFirstname(isAdmin: Int){
        this.isAdmin = isAdmin
    }

    fun getLastLoginAt(): String? {
        return last_login_at
    }
    fun setLastLoginAt(last_login_at: String){
        this.last_login_at = last_login_at
    }

    fun getLastCreatedAt(): String? {
        return created_at
    }
    fun setLastCreatedAt(created_at: String){
        this.created_at = created_at
    }

    fun getExperience(): Int? {
        return experience
    }
    fun setExperience(experience: Int){
        this.experience = experience
    }
}