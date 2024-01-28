package com.example.test2.fragments

import CustomAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shift_tt.Retrofit.api.RandomUserApi
import com.example.shift_tt.Retrofit.model.Person
import com.example.shift_tt.Retrofit.util.Constants
import com.example.shift_tt.db.MainDb
import com.example.shift_tt.makeApiCall
import com.example.test2.R
import com.example.test2.databinding.FragmentBlankBinding
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    private lateinit var customAdapter: CustomAdapter
    private lateinit var recyclerView: RecyclerView
    private var call: Call<Person>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        val view = binding.root
        val db = MainDb.getDb(requireContext())

        recyclerView = binding.myRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(RandomUserApi::class.java)
        call = service.getRandomUser()

        customAdapter = CustomAdapter { item ->
            val pattern = "\\[(.*?)\\]".toRegex()
            val matchResult = pattern.find(item)
            val extractedValue = matchResult?.groupValues?.get(1)
            if (extractedValue != null) {
                val bundle = bundleOf("selectedItem" to extractedValue)
                view.findNavController().navigate(R.id.action_blankFragment_to_blankFragment2, bundle)
            }
        }


        binding.getAndsaveB.setOnClickListener {
            makeApiCall(call!!, db)
        }
        recyclerView.adapter = customAdapter

        binding.UpdateB.setOnClickListener {
            val updatedData = db.getDao().getAllItemNames()
            customAdapter.setData(updatedData)
            customAdapter.notifyDataSetChanged()
        }

        val dataFromDb = db.getDao().getAllItemNames()
        customAdapter.setData(dataFromDb)

        return view
    }

    override fun onDestroy() {
        call?.cancel()
        super.onDestroy()
    }

}