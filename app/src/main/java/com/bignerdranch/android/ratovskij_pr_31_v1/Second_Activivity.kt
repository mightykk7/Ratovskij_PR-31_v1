package com.bignerdranch.android.ratovskij_pr_31_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class Second_Activivity : AppCompatActivity() {
    private lateinit var btnCount: Button
    private lateinit var shapes: Spinner
    private lateinit var v: EditText
    private lateinit var pic: ImageView
    private lateinit var rect: ImageView
    private lateinit var rectDop: ImageView
    private lateinit var b: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second_activivity)

        btnCount = findViewById(R.id.btnCount)
        shapes = findViewById(R.id.shape)
        v = findViewById(R.id.editTextValue)
        b = findViewById(R.id.inputB)
        pic = findViewById(R.id.formula)
        rect = findViewById(R.id.rect4)
        rectDop = findViewById(R.id.rectR)

        val adapter = ArrayAdapter.createFromResource(this, R.array.shapes, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapes.adapter = adapter
        shapes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selShape = parent.getItemAtPosition(position).toString()
                ViewPic(selShape.toString())
                val density = resources.displayMetrics.density

                val paramsRect = rect.layoutParams
                val paramsEditTextV = v.layoutParams
                if (selShape.toString() == "Круг") {
                    paramsRect.width = (300 * density).toInt()
                    rectDop.visibility = View.INVISIBLE
                    b.visibility = View.INVISIBLE
                    paramsEditTextV.width = (280 * density).toInt()
                    v.hint = getString(R.string.data)
                }
                else {
                    paramsRect.width = (140 * density).toInt()
                    rectDop.visibility = View.VISIBLE
                    b.visibility = View.VISIBLE
                    paramsEditTextV.width = (120 * density).toInt()
                    v.hint = getString(R.string.inputA)
                }
                rect.layoutParams = paramsRect
                view.requestLayout()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                pic.setImageDrawable(null)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //показ картинки с формулой
    private fun ViewPic(shape: String){
        val shapeID = when(shape){
            "Круг" -> R.drawable.circle
            "Треугольник" -> R.drawable.triangle
            else -> null
        }
        if (shapeID != null)
            pic.setImageResource(shapeID)
        else
            pic.setImageDrawable(null)
    }

    //проверка корректности значений
    private fun ValidValue():Boolean {
        val value1 = v.text.toString()
        val value2 = b.text.toString()
        if (value1 == "." || value2 == "."){
            MainActivity.AlertUtils.ShowAlert("Ошибка", "Неверный ввод", this)
            return false
        }
        if (value1.isEmpty() || (b.isVisible && value2.isEmpty())) {
            MainActivity.AlertUtils.ShowAlert("Ошибка", "Введите значение", this)
            return false
        }
        if (value1.isNotEmpty() && value2.isNotEmpty() &&
            !(2*value1.toDouble() > value2.toDouble() && value1.toDouble() + value2.toDouble() > value1.toDouble())){
            MainActivity.AlertUtils.ShowAlert("Ошибка", "Такого треугольника не существует", this)
            return false
        }
        return true
    }

    //расчет
    fun Count(view: View){
        if (!ValidValue())
            return
        else
        {
            val selShape = shapes.selectedItem.toString()
            var result: Double = 0.0
            val a = v.text.toString().toDouble()
            when(selShape){
                "Круг" -> {
                    result = a/(2*Math.PI)
                }
                "Треугольник" -> {
                    val b = b.text.toString().toDouble()
                    result = 2*a + b
                }
                else -> MainActivity.AlertUtils.ShowAlert("Ошибка", "Выберите фигуру", this)
            }
            val intent = Intent(this, Fierd_Activivity::class.java)
            intent.putExtra("selShape", selShape)
            intent.putExtra("result", result)
            startActivity(intent)
            finish()
        }
    }
}