package com.example.winsome.funquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.funquiz.MESSAGE";
    private Questions myQuestionLib = new Questions();

    private int myScore = 0;
    private int questions = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_main);

        for(int i=0; i<questions; i++){
            String currentQ = "question"+i;
            int resID = getResources().getIdentifier(currentQ, "id", getPackageName());
            TextView myQuestion = (TextView)findViewById(resID);
            myQuestion.setText(myQuestionLib.getQuestion(i));

            if(i == 2){
                for(int j=0; j<3; j++){
                    String currentRadio = "radio"+j;
                    int radID = getResources().getIdentifier(currentRadio, "id", getPackageName());
                    RadioButton myRadio = (RadioButton) findViewById(radID);
                    if(j==0){myRadio.setText(myQuestionLib.getChoice1(i));}
                    else if(j==1){myRadio.setText(myQuestionLib.getChoice2(i));}
                    else {myRadio.setText(myQuestionLib.getChoice3(i));}
                }
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.answer2);
                ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
            } else if(i ==5){
                for(int j=0; j<3; j++) {
                    String currentCheck = "checkbox" + j;
                    int radID = getResources().getIdentifier(currentCheck, "id", getPackageName());
                    CheckBox myCheck = (CheckBox) findViewById(radID);
                    if (j == 0) {myCheck.setText(myQuestionLib.getChoice1(i));
                    } else if (j == 1) {myCheck.setText(myQuestionLib.getChoice2(i));
                    } else {myCheck.setText(myQuestionLib.getChoice3(i));}
                }
            }
        }
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkbox1:
                if (checked){myScore+=1;}
                else{myScore-=1;}
                break;
            case R.id.checkbox2:
                if (checked){myScore+=1;}
                else{myScore-=1;}
                break;
        }
    }

    public void checkResult(){
        for(int i=0; i<questions; i++){
            if(i == 2){
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.answer2);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String answer = (String) radioButton.getText();
                if (answer.equals(myQuestionLib.getCorrectanswer(i))) {myScore++;}
            } else if( i==5) {}
            else{
                String currentQ = "answer" + i;
                int resID = getResources().getIdentifier(currentQ, "id", getPackageName());
                EditText myQuestion = (EditText) findViewById(resID);
                String answer = String.valueOf(myQuestion.getText());
                answer = answer.toLowerCase();
                if (answer.equals(myQuestionLib.getCorrectanswer(i))) {myScore++;}
            }
        }
    }

    /** Called when the user taps the Result button */
    public void sendMessage(View view) {
        checkResult();
        Toast.makeText(MainActivity.this, "Final Score", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        String message = Integer.toString(myScore);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
