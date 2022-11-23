package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        if(result != null) {
            txtResult.text = "Última aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        // VERIFICA SE O TEXT VIEW ESTA VAZIO
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }
        // VALIDA SE O TEXT VIEW ESTA ENTRE 6 E 15
        val qtd = text.toInt()
        if (qtd < 6 || qtd > 15) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)
            if (numbers.size == qtd) {
                break
            }
        }

        val sortedNumbers = numbers.sorted()
        txtResult.text = sortedNumbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
    }
}