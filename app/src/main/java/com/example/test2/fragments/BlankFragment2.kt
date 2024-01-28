package com.example.test2.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.shift_tt.db.MainDb
import com.example.test2.R

class BlankFragment2 : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_blank2, container, false)

        val id = arguments?.getString("selectedItem")
        val db = MainDb.getDb(requireContext())

        val userDao = db.getDao()
        val user = id?.let { userDao.getUserById(it) }


        user!!.observe(viewLifecycleOwner, Observer { userData ->

            Glide.with(this)
                .load(userData.picture)
                .into(view.findViewById(R.id.imageView))

            view.findViewById<TextView>(R.id.textViewName).text = "Name: " + userData.name
            view.findViewById<TextView>(R.id.textViewLastName).text = "LastName: " + userData.lastName
            view.findViewById<TextView>(R.id.textViewCoordinates).text = "Coordinates: " + userData.latitude + " " + userData.longitude
            view.findViewById<TextView>(R.id.textViewCountry).text = "Country: " + userData.country
            view.findViewById<TextView>(R.id.textViewState).text = "State: " + userData.state
            view.findViewById<TextView>(R.id.textViewPhoneNumber).text = "PhoneNumber: " + userData.phoneNumber

            view.findViewById<TextView>(R.id.textViewCoordinates).setOnClickListener {
                val gmmIntentUri = "geo:${userData.latitude},${userData.longitude}?q=${userData.latitude},${userData.longitude}(Label+Name)"
                val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri))
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                    startActivity(mapIntent)
                } else {
                    Toast.makeText(requireContext(), "Google Maps не установлен", Toast.LENGTH_SHORT).show()
                }
            }

            view.findViewById<TextView>(R.id.textViewPhoneNumber).setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userData.phoneNumber))
                startActivity(intent)
            }

        })
        
        return view
    }
}
