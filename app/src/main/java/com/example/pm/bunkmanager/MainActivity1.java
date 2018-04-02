package com.example.pm.bunkmanager;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private EditText classConducted;
    private EditText classPresent;
    private Button helpmeBunk;
    private RadioGroup radioGroup;
    private TextView resultOutput;
    private int targetPercentage;
    private BunkCalculator bunkCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
    }

    private void initVars() {
        this.classConducted=findViewById(R.id.classes_conducted);
        this.classPresent=findViewById(R.id.classes_present);
        this.radioGroup = findViewById(R.id.inputPercentageGroup);
        this.helpmeBunk = findViewById(R.id.helpmebunk);
        this.resultOutput=findViewById(R.id.outputResult);
        helpmeBunk.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        this.targetPercentage=75;
        this.bunkCalculator=new BunkCalculator();


    }

    @Override
    public void onClick(View view) {
        try{
            if((this.classConducted.getText()!=null)&&(this.classPresent.getText()!=null)&&(inputIsValidx())) {
            int i=Integer.parseInt(this.classConducted.getText().toString());
            int j= Integer.parseInt(this.classPresent.getText().toString());
            this.bunkCalculator.setValues(i,j,this.targetPercentage);
            this.resultOutput.setText(bunkCalculatorToOutput(this.bunkCalculator.calculate()));
            }
          /*  return;*/
            }
            catch(NullPointerException exception){
                exception.printStackTrace();
                /*return;*/
            }
        }

    private String bunkCalculatorToOutput(int calculate) {
        DecimalFormat localDecimalFormat=new DecimalFormat("###.##");
        String str="Your Current Percentage Is" + localDecimalFormat.format(this.bunkCalculator.originalPercentage()) + "%\n\n";
        if(calculate>0){
           return str + "You Can Bunk"+calculate+" classes to keep your attendance at"+this.targetPercentage;
        }
        if(calculate==0){
            return str + "Hold Tight You Cannot Bunk Right Now Try After Few Days";
        }
        int i=calculate* 1;
        return str + "You Need To Attend " + i + "More Classes To get Your Attendance Back To" + this.targetPercentage + "%";
    }

    private boolean inputIsValidx() {
        if(Integer.parseInt(classPresent.getText().toString())>Integer.parseInt(classConducted.getText().toString()))
        {
            AlertDialog alertDialog=new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("please fill correct details");
            alertDialog.show();
            return false;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.radio1:
                this.targetPercentage=60;
                return;
            case R.id.radio2:
                this.targetPercentage=65;
                return;
            case R.id.radio3:
                this.targetPercentage=70;
                return;
            case R.id.radio4:
                this.targetPercentage=75;
                return;
            case R.id.radio5:
                this.targetPercentage=80;
                return;
            default:
                return;

        }


    }
}
