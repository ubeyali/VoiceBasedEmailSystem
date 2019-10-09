package tr.com.voicebasedemailsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView recordedText;
    private ArrayList<String> keywordsArrayList;
    SpeechText speechText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String [] keywordsList={"create a new message","send","read unread messages"};

        speechText=new SpeechText(getApplicationContext(),MainActivity.this);

        keywordsArrayList=new ArrayList<String>(Arrays.asList(keywordsList));
        setContentView(R.layout.activity_main);
        Button rcdBtn=(Button)findViewById(R.id.rcdBtn);

        recordedText=(TextView)findViewById(R.id.textView);

        rcdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechText.Listen();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK && data != null) {
                    final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    final String speechInput = result.get(0);

                    recordedText.setText(speechInput);

                    if(keywordsArrayList.contains(speechInput)){
                        switch (speechInput){
                            case "create a new message":

                                break;
                            case "read unread messages":

                                break;
                        }

                        Toast.makeText(MainActivity.this,speechInput,Toast.LENGTH_LONG).show();
                    }
                    speechText.Speak(speechInput);
                    break;
                }
            }
        }
    }

}
