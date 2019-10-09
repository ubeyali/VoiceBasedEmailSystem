package tr.com.voicebasedemailsystem;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SpeechText implements TextToSpeech.OnInitListener {

    private Activity activity;
    private Context context;
    private TextToSpeech textToSpeech;


    public SpeechText(Context cx,Activity ac) {
        context = cx;
        activity=ac;
        textToSpeech = new TextToSpeech(context,this );
    }
    public void Listen(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        try {
            activity.startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException a) {

        }
    }

    public void Speak(String input){
        textToSpeech.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.US);
        }
    }
}
