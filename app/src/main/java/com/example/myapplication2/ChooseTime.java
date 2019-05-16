package com.example.myapplication2;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;

public class ChooseTime extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    public int beginHour=0;
    public int beginMin=0;
    public int endHour=0;
    public int endMin=0;
    public int studyHour=0;
    public int studyMin=0;
    public LocalTime beginTime;
    public LocalTime endTime;
    public Duration duration;
    private Button button21;
    public TextView textView2;
    public TextView textView21;
    public TextView textView23;
    private Button button23;
    private TextView textView25;
    private TextView textView27;
    private TextView textView29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        textView2=(TextView)findViewById(R.id.textView2);
        textView2.setText("Pick your time");
        textView21=(TextView)findViewById(R.id.textView21);
        textView23=(TextView)findViewById(R.id.textView23);
        textView25=(TextView)findViewById(R.id.textView25);
        textView27=(TextView)findViewById(R.id.textView27);
        textView29=(TextView)findViewById(R.id.textView29);

        button21=(Button) findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                int hour=c.get(Calendar.HOUR_OF_DAY);
                int minute=c.get(Calendar.MINUTE);
                beginMin=minute;
                beginHour=hour;
                textView21.setText(" "+beginHour);
                textView23.setText(" "+beginMin);
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        Button button23=findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(duration!=null) {
                    Intent intent = new Intent(ChooseTime.this, StudyTime.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ChooseTime.this,"You haven't chosen your time",Toast.LENGTH_SHORT).show();
                }
            }
        });








    }// onCreate method


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        endHour=hourOfDay;
        endMin=minute;
        textView25.setText(" "+hourOfDay);
        textView27.setText(" "+minute);
        beginTime=LocalTime.of(beginHour,beginMin);
        endTime=LocalTime.of(hourOfDay,minute);
        duration=Duration.between(beginTime,endTime);
        studyHour= (int) duration.toHours();
        studyMin=(int)duration.toMinutes()-studyHour*60;
        textView29.setText("Duration: "+studyHour+"Hour, "+studyMin+"Min  ");
    }
}//definition of the class

