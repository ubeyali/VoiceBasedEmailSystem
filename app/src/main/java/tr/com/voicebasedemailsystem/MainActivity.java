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

    public static int LISTEN=1;
    public static int TOWHOM=2;
    public static int CONTENT=3;
    public static int CONFIRM=4;
    TextView recordedText;
    private ArrayList<String> keywordsArrayList;
    SpeechText speechText;
    String toWhom,content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String [] keywordsList={"create a new message","send","read unread messages","send the message","send message"};

        speechText=new SpeechText(getApplicationContext(),MainActivity.this);

        keywordsArrayList=new ArrayList<String>(Arrays.asList(keywordsList));
        setContentView(R.layout.activity_main);
        Button rcdBtn=(Button)findViewById(R.id.rcdBtn);

        recordedText=(TextView)findViewById(R.id.textView);

        rcdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speechText.listen(LISTEN);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            final ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final String speechInput = result.get(0);
        switch (requestCode) {
            case 1: {
                    recordedText.setText(speechInput);
                    if(keywordsArrayList.contains(speechInput)){
                        switch (speechInput){
                            case "create a new message":
                                break;
                            case "send the message": case "send message":
                                speechText.speak("to whom would you like to send ");
                                speechText.listen(TOWHOM);
                                break;
                        }
                    }
                    break;
                }
            case 2:{
                toWhom=speechInput;
                speechText.speak("what is the content of the message");
                speechText.listen(CONTENT);
                break;
            }
            case 3:{
                content=speechInput;
                String mes="for confirming, an email will be send to "+toWhom+" and the message is: "+content+", Please say yes to confirm and send the message, otherwise say no to cancel!";
                speechText.speak(mes);
                speechText.listen(CONFIRM);
                break;
            }
            case 4:{
                if(speechInput.equals("yes")){
                    speechText.speak("message send successfully");
                }else if(speechInput.equals("no")){
                    speechText.speak("message canceled");
                }
                break;
            }
            }
        }
    }

}
