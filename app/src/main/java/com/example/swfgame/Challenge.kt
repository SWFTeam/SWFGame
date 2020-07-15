package com.example.swfgame

import com.google.gson.annotations.SerializedName

class Challenge {

    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("description")
    private var description: List<Description>? = null
    @SerializedName("experience")
    private var experience: Int? = null

    constructor(id: Int, descriptions: List<List<Description>>, experience: Int){
        this.id = id
        this.description = description
        this.experience = experience
    }

    fun getId(): Int? {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }

    fun getDescription(): List<Description>? {
        return description
    }
    fun setDescription(description: List<Description>){
        this.description = description
    }

    fun getExperience(): Int? {
        return experience
    }
    fun setExperience(experience: Int){
        this.experience = experience
    }
}