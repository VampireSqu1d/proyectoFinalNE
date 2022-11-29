package com.ayalabautista.proyectofinalne;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    Context context;
    private TextInputEditText textInputEditText;
    private FloatingActionButton playSound;
    private FloatingActionButton disminuir;
    private FloatingActionButton aumentar;
    private FloatingActionButton micro;
    private TextToSpeech textToSpeech;
    private float ourFontsize = 16f;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
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
        micro = findViewById(R.id.micButton);

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
                    textToSpeech.setLanguage(Locale.getDefault());
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

        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                    textInputEditText.setText("");

                } catch (Exception e) {
                    Toast.makeText(context, "El reconocimiento de voz no está disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT:
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInputEditText.setText(text.get(0));
                }
                break;
        }
    }



}