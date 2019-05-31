package kewen.ding.softdev.kuleuven.timemanager40;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;

public class ChooseTime extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    public static final String EXTRA_MESSAGE = "com.example.myapplication2.STUDY_TIME";
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
    private TextView textView25;
    private TextView textView27;
    private TextView textView29;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        textView2=(TextView)findViewById(R.id.textView2);
        textView21=(TextView)findViewById(R.id.textView21);
        textView23=(TextView)findViewById(R.id.textView23);
        textView25=(TextView)findViewById(R.id.textView25);
        textView27=(TextView)findViewById(R.id.textView27);
        textView29=(TextView)findViewById(R.id.textView29);

        button21=(Button) findViewById(R.id.button21);
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();// get the default time zone and locale
                int hour=c.get(Calendar.HOUR_OF_DAY);
                int minute=c.get(Calendar.MINUTE);
                beginMin=minute;
                beginHour=hour;
                DialogFragment timePicker=new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });
        //reference: https://developer.android.com/guide/topics/ui/controls/pickers#java
        //reference：https://www.youtube.com/watch?v=QMwaNN_aM3U

        Button button23=(Button) findViewById(R.id.button23);
        button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int studyTime=studyHour*60+studyMin;
                if(duration!=null&&studyTime>0) {
                    Intent intent = new Intent(ChooseTime.this, StudyTime.class);
                    intent.putExtra(EXTRA_MESSAGE,studyTime);
                    SharedState.getInstance().setFailed(false);
                    startActivity(intent);

                }
                else if (duration==null){
                    Toast.makeText(ChooseTime.this,"You haven't chosen your time",Toast.LENGTH_SHORT).show();
                }
                else if(studyTime<=0){
                    Toast.makeText(ChooseTime.this,"The duration cannot be bigger than 24h",Toast.LENGTH_SHORT).show();
                }

            }
        });









    }//onCreat

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        endHour=hourOfDay;
        endMin=minute;
        textView25.setText(String.format("%02d" ,hourOfDay));
        textView27.setText(String.format("%02d" ,minute));
        beginTime=LocalTime.of(beginHour,beginMin);
        endTime=LocalTime.of(hourOfDay,minute);
        duration=Duration.between(beginTime,endTime);
        studyHour= (int) duration.toHours();
        studyMin=(int)duration.toMinutes()-studyHour*60;
        textView29.setText("Duration: "+studyHour+"Hour, "+studyMin+"Min  ");
    }




}//class definition
