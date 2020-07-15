package com.example.swfgame

import com.google.gson.annotations.SerializedName

class Event {

    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("address")
    private var address: List<Address>? = null
    @SerializedName("date_start")
    private var date_start: String? = null
    @SerializedName("date_end")
    private var date_end: String? = null
    @SerializedName("descriptions")
    private var descriptions: List<Description>? = null
    @SerializedName("experience")
    private var experience: Int? = null

    constructor(id: Int, address: List<Address>, date_start: String, date_end: String, descriptions: List<Description>, experience: Int){
        this.id = id
        this.address = address
        this.date_start = date_start
        this.date_end = date_end
        this.descriptions = descriptions
        this.experience = experience
    }

    fun getId(): Int? {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }

    fun getAddress(): List<Address>? {
        return address
    }
    fun setAddress(address: List<Address>){
        this.address = address
    }

    fun getDateStart(): String? {
        return date_start
    }
    fun setDateStart(date_start: String){
        this.date_start = date_start
    }

    fun getDateEnd(): String? {
        return date_end
    }
    fun setDateEnd(date_end: String){
        this.date_end = date_end
    }

    fun getDescriptions(): List<Description>? {
        return descriptions
    }
    fun setDescriptions(descriptions: List<Description>){
        this.descriptions = descriptions
    }

    fun getExperience(): Int? {
        return experience
    }
    fun setExperience(experience: Int){
        this.experience = experience
    }
}