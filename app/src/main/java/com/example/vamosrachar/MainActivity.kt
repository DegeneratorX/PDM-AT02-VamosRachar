package com.example.vamosrachar

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    var pessoas: Int = 0
    var valor: Float = 0.0f
    var resString: String = ""
    var res: Float = 0.0f

    var TTS: TextToSpeech? = null;

    fun calcular(){
        if (valor >= 0.00f  && pessoas > 0){
            res = valor/pessoas
        } else{
            res = 0.00f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TTS =TextToSpeech(this, this)
    }

    override fun onStart(){
        super.onStart()

        val etValor : EditText = findViewById(R.id.valor)
        val etPessoas : EditText = findViewById(R.id.pessoas)
        val tvResultado : TextView = findViewById(R.id.texto_resultado)
        resString = getString(R.string.texto_resultado,"RS", res)
        tvResultado.text = resString
        //val btnShare : FloatingActionButton = findViewById(R.id.fbShare)
        //val btnSpeech : FloatingActionButton = findViewById(R.id.fbSound)

        etValor.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(str: CharSequence?, ini: Int, bfr: Int, i: Int) {
                if (str != null) {
                    if (str.isNotEmpty()){
                        valor = str.toString().toFloat()
                    } else{
                        valor = 0.00f
                    }
                }
                calcular()
            }

            override fun afterTextChanged(str: Editable?) {
                tvResultado.text = getString(R.string.texto_resultado, "R$".toString(), res)
                resString = tvResultado.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){
                //
            }
        })
        etPessoas.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(str: CharSequence, ini: Int, bfr: Int, i: Int){
                if (str.toString().isNotEmpty()){
                    pessoas = str.toString().toInt()
                } else{
                    pessoas = 0
                }
                calcular();
            }
            override fun afterTextChanged(str: Editable){
                tvResultado.text = getString(R.string.texto_resultado,"R$", res);
                resString = tvResultado.text.toString();

            }
            override fun beforeTextChanged(str: CharSequence, ini: Int, i: Int, fim: Int){
                //
            }
        })





    }

    override fun onInit(p0: Int) {
        TODO("Not yet implemented")
    }
}
