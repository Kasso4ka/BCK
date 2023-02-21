package com.example.bckapp

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject

class DataBasePreferences(var context: Context) {
    private var prefs: SharedPreferences
    private val prefsSetting = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor
    private val editorSettings = prefsSetting.edit()
    private var indexNow = 0

            init {
                prefs = context.getSharedPreferences(getSizeInt().toString(), Context.MODE_PRIVATE)
                editor = prefs.edit()
            }

    fun getSizeInt(): Int {
        return prefsSetting.getInt("size",0)
    }

    private fun sizeUp(){
        editorSettings.putInt("size", getSizeInt()+1).apply()
    }

    private fun sizeDown(){
        editorSettings.putInt("size",getSizeInt()-1).apply()
    }

    private fun updatePrefs(index: Int){
        if (indexNow != index) {
            indexNow = index
            prefs = context.getSharedPreferences(index.toString(), Context.MODE_PRIVATE)
            editor = prefs.edit()
        }
    }

    fun addUid(_input: String){
        sizeUp()
        updatePrefs(getSizeInt())
        setUid(_input)
    }

    private fun setUid(_input: String){
            editor.putString("uid",_input).apply()
    }

    fun getUid(): String? {
        return prefs.getString("uid","").toString()
    }

}