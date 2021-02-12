package com.app.infyexerciseone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RawRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var countrylist: ArrayList<CountryModel> = arrayListOf()
    lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        fectingData()
        setupUIadapter()
    }

    private fun checkNull(countryModel: CountryModel): Boolean = if(countryModel.title.toString() != "null") true else false

    fun setupView() {
        recyclerView = findViewById(R.id.country_recyclerview)
    }

    fun setUpData() :String?{
        var json: String

        json =applicationContext.assets.open("api.txt").bufferedReader().use {
            it.readText()
        }
        return json
    }
    fun fectingData(){

        var jsonObject : JSONObject= JSONObject(setUpData())
        var jsonArray : JSONArray = jsonObject.getJSONArray("rows")

        for (i in 0..jsonArray.length()-1) {
            var jsonObject: JSONObject = jsonArray.getJSONObject(i)
            var country_title: String = jsonObject.getString("title")
            var country_description: String = jsonObject.getString("description")
            var image: String = jsonObject.getString("imageHref")

            val country_model: CountryModel = CountryModel(country_title, country_description, image)

            if (checkNull(country_model)) countrylist.add(country_model)

        }
    }
    private fun setupUIadapter() {

        recyclerView.layoutManager = LinearLayoutManager(this)
        countryAdapter =
                CountryAdapter(countrylist)
        recyclerView.addItemDecoration(
                DividerItemDecoration(
                        recyclerView.context,
                        (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView.adapter = countryAdapter
    }
}
