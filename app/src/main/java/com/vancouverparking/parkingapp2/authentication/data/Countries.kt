package com.vancouverparking.parkingapp2.authentication.data

import com.vancouverparking.parkingapp2.R

data class CountryCode(
        var countryCode: String,
        val countryPhoneCode: String = "",
        val countryName: String = "",
        val flagResID: Int = 0
)

fun getFlags(countryName: String):Int
{
    return when (countryName)
    {
        "be" -> R.drawable.ic_flag_belgium
        "br" -> R.drawable.ic_flag_brazil
        "co" -> R.drawable.ic_flag_colombia
        "ca" -> R.drawable.ic_flag_canada
        "es" -> R.drawable.ic_flag_spain
        "us" -> R.drawable.ic_flag_united_states
        "cn" -> R.drawable.ic_flag_china
        "in" -> R.drawable.ic_flag_india
        "gb" -> R.drawable.ic_flag_united_kingdom

        else -> R.drawable.ic_flag_transparent
    }
}

fun getListOfCountries(): List<CountryCode>
{
    val countries: MutableList<CountryCode> = ArrayList()
    countries.add(CountryCode("ca", "+1", "Canada"))
    countries.add(CountryCode("us", "+1", "United States"))
    countries.add(CountryCode("be", "+32", "Belgium"))
    countries.add(CountryCode("br", "+55", "Brazil"))
    countries.add(CountryCode("cn", "+86", "China"))
    countries.add(CountryCode("co", "+57", "Colombia"))
    countries.add(CountryCode("gb", "+44", "United Kingdom"))
    countries.add(CountryCode("in", "+91", "India"))
    countries.add(CountryCode("es", "+34", "Spain"))
    return countries
}