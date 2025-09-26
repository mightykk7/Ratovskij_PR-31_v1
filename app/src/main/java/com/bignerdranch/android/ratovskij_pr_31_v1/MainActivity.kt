package com.bignerdranch.android.ratovskij_pr_31_v1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var login: EditText
    lateinit var pass: EditText
    lateinit var sharedPreferences: SharedPreferences
    private val KEY_LOGIN = "login"
    private val KEY_PASSWORD = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fierst_activivity_main)
        login = findViewById(R.id.login)
        pass = findViewById(R.id.pass)
        sharedPreferences = getPreferences(MODE_PRIVATE)
    }
    //регистрация
    fun enter(view: View){
        val login: String = login.text.toString()
        val password: String = pass.text.toString()

        if (login.isNotEmpty() && password.isNotEmpty()){
            if (password.length < 8){
                val alert = AlertUtils.ShowAlert("Ошибка", "Пароль должен содержать минимум 8 символов", this)
            }
            else{
                if(password == "ects2023" && login == "ects") {
                    SaveLogPas(login, password)
                    val intent = Intent(this, Second_Activivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    val alert = AlertUtils.ShowAlert("Ошибка", "Неверный логин или пароль", this)
                }
            }
        }
        else{
            val alert = AlertUtils.ShowAlert("Ошибка", "Введите логин и пароль", this)
        }
    }
    //показ диалогового окна
    object AlertUtils {
        fun ShowAlert(title: String, message: String, context: Context) {
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ок", null)
                .create()
                .show()
        }
    }
    //сохранение логина и пароля
    private fun SaveLogPas(login: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LOGIN, login)
        editor.putString(KEY_PASSWORD, password)
        editor.apply()
    }
}