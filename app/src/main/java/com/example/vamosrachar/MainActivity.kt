package com.example.vamosrachar

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener {
    var pessoas: Int = 0
    var valor: Float = 0.0f
    var res: Float = 0.0f

    var TTS: TextToSpeech? = null;

    fun calcular(){
        if (valor >= 0.00f  && pessoas > 0){
            res = valor/pessoas
        } else{
            res = 0.00f
        }
        val tvResultado : TextView = findViewById(R.id.texto_resultado)
        val df = DecimalFormat("#.00")
        tvResultado.text = "R$ " + df.format(res).toString()
        Log.v("PDM", "Calculando 2")
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
        val TTSBtn : FloatingActionButton = findViewById(R.id.soundButton)
        val SHAREBtn : FloatingActionButton = findViewById(R.id.shareButton)

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
            override fun afterTextChanged(str: Editable?) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int){}
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
            override fun afterTextChanged(str: Editable){}
            override fun beforeTextChanged(str: CharSequence, ini: Int, i: Int, fim: Int){}
        })
        TTSBtn.setOnClickListener{
            val ttsText: String = res.toString() + " for each motherfucking person";
            speak(ttsText.toString());
        }
        SHAREBtn.setOnClickListener{
            share(res.toString() + " " + getString(R.string.para_cada));
        }
    }
    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = TTS?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){}
        }
    }
    fun speak(content : String){TTS?.speak(content, TextToSpeech.QUEUE_FLUSH,null,"")}

    fun share(content : String){
        val intent : Intent = Intent().apply{
            action = Intent.ACTION_SEND;
            putExtra(Intent.EXTRA_TEXT, content);
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,null);
        startActivity(shareIntent);
    }
    override fun onDestroy(){
        if (TTS == null) {return}
        TTS?.stop();
        TTS?.shutdown();
        super.onDestroy();
    }
}
