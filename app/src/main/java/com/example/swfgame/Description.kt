package com.example.swfgame

import com.google.gson.annotations.SerializedName

class Description {

    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("country_code")
    private var country_code: String? = null
    @SerializedName("title")
    private var title: String? = null
    @SerializedName("name")
    private var name: String? = null
    @SerializedName("description")
    private var description: String? = null
    @SerializedName("type")
    private var type: String? = null
    @SerializedName("foreign_id")
    private var foreign_id: Int? = null

    constructor(id: Int, country_code: String, title: String, name: String, description: String, type: String, foreign_id: Int){
        this.id = id
        this.country_code = country_code
        this.title = title
        this.name = name
        this.description = description
        this.type = type
        this.foreign_id = foreign_id
    }

    fun getId(): Int? {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }

    fun getCountryCode(): String? {
        return country_code
    }
    fun setCountryCode(country_code: String){
        this.country_code = country_code
    }

    fun getTitle(): String? {
        return title
    }
    fun setTitle(title: String){
        this.title = title
    }

    fun getName(): String? {
        return name
    }
    fun setName(name: String){
        this.name = name
    }

    fun getDescription(): String? {
        return description
    }
    fun setDescription(description: String){
        this.description = description
    }

    fun getType(): String? {
        return type
    }
    fun setType(type: String){
        this.type = type
    }

    fun getForeignId(): Int? {
        return foreign_id
    }
    fun setForeignId(foreign_id: Int){
        this.foreign_id = foreign_id
    }
}