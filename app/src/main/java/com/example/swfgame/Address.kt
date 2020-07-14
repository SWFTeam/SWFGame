package com.example.swfgame

import com.google.gson.annotations.SerializedName

class Address {

    @SerializedName("id")
    private var id: Int? = null
    @SerializedName("country")
    private var country: String? = null
    @SerializedName("city")
    private var city: String? = null
    @SerializedName("street")
    private var street: String? = null
    @SerializedName("zip_code")
    private var zip_code: Int? = null
    @SerializedName("nb_house")
    private var nb_house: Int? = null
    @SerializedName("complement")
    private var complement: String? = null

    constructor(id: Int, country: String, city: String, street: String, zip_code: Int, nb_house: Int, complement: String){
        this.id = id
        this.country = country
        this.city = city
        this.street = street
        this.zip_code = zip_code
        this.nb_house = nb_house
        this.complement = complement
    }

    constructor(country: String, city: String, street: String, zip_code: Int, nb_house: Int, complement: String){
        this.country = country
        this.city = city
        this.street = street
        this.zip_code = zip_code
        this.nb_house = nb_house
        this.complement = complement
    }

    fun getId(): Int? {
        return id
    }
    fun setId(id: Int){
        this.id = id
    }

    fun getCountry(): String? {
        return country
    }
    fun setCountry(country: String){
        this.country = country
    }

    fun getCity(): String? {
        return city
    }
    fun setCity(city: String){
        this.city = city
    }

    fun getStreet(): String? {
        return street
    }
    fun setStreet(street: String){
        this.street = street
    }

    fun getZipCode(): Int? {
        return zip_code
    }
    fun setZipCode(zip_code: Int){
        this.zip_code = zip_code
    }

    fun getNbHouse(): Int? {
        return nb_house
    }
    fun setNbHouse(nb_house: Int){
        this.nb_house = nb_house
    }

    fun getComplement(): String? {
        return complement
    }
    fun setComplement(complement: String){
        this.complement = complement
    }
}