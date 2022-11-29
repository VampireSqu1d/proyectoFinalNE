package com.ayalabautista.proyectofinalne;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private TextInputEditText textInputEditText;
    private FloatingActionButton playSound;
    private FloatingActionButton disminuir;
    private FloatingActionButton aumentar;
    private FloatingActionButton micro;
    private TextToSpeech textToSpeech;
    private float ourFontsize = 16f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // aquí se comienzan a instanciar los elementos
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        textInputEditText = findViewById(R.id.textoParaTraducir);
        playSound = findViewById(R.id.playButton);
        disminuir = findViewById(R.id.botonMenos);
        aumentar = findViewById(R.id.botonMas);
        // aqui se terminan de instanciar los elementos

        // método para borrar el place holder text y cambiar el color del texto
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                textInputEditText.setText("");
                textInputEditText.setTextColor(Color.parseColor("#000000"));
            }
        });

        // en este método se selecciona el lenguaje del TTS
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        // click listener del boton play
        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(textInputEditText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        // en este método se disminuye el tamaño del texto
        disminuir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ourFontsize -= 4f;
                if (ourFontsize > 4f) {
                    textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,ourFontsize);
                }
            }
        });

        // en este método se aumenta el tamaño del texto
        aumentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ourFontsize += 4f;
                if (ourFontsize < 88f) {
                    textInputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,ourFontsize);
                }
            }
        });

    }
}