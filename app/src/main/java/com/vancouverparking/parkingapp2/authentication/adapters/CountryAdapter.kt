package com.vancouverparking.parkingapp2.authentication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.vancouverparking.parkingapp2.core.domain.Country
import com.vancouverparking.parkingapp2.databinding.ItemCountrySpinnerBinding

class CountryAdapter(
        private val context: Context,
        private val countries: List<Country> = emptyList()
) :
    ArrayAdapter<Country>(context, 0, countries)
{

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup): View
    {
        return createView(position, convertView, parent)

    }

    override fun getDropDownView(position: Int,
                                 convertView: View?,
                                 parent: ViewGroup): View
    {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val country = getItem(position)
        val binding: ItemCountrySpinnerBinding

        if (convertView == null) {
            binding = ItemCountrySpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
            binding.root.tag = ViewHolder(binding)
        } else {
            binding = ItemCountrySpinnerBinding.bind(convertView)
        }

        country?.let { it ->
            Glide.with(context)
                .load(it.flagImage)
                .into(binding.flagImageView)
        }
        binding.countryCodeTextView.text = country?.countryCode

        return binding.root
    }

    private class ViewHolder(val binding: ItemCountrySpinnerBinding)


}