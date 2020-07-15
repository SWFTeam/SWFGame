package com.example.swfgame

import com.google.gson.annotations.SerializedName

class Advice {

    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("descriptions")
    private var descriptions: List<Description>? = null

    constructor(id: Int, descriptions: List<Description>){
        this.id = id
        this.descriptions = descriptions
    }

    fun getId(): Int? {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }

    fun getDescriptions(): List<Description>? {
        return descriptions
    }
    fun setDescriptions(descriptions: List<Description>){
        this.descriptions = descriptions
    }
}