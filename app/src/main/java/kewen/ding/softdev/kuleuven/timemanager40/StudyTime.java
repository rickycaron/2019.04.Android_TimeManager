package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class StudyTime extends AppCompatActivity implements View.OnClickListener {
    private long timeCountInMilliSeconds = 1 * 60000;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private enum MusicPlaying{
        PLAYING,
        STOPING
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private MusicPlaying musicPlayingState=MusicPlaying.STOPING;

    MediaPlayer player;

    private ProgressBar progressBarCircle;
    private TextView editTextMinute;
    private TextView textViewTime;
    private ImageView imageViewStartStop;
    private CountDownTimer countDownTimer;
    private TextView textView31;
    private int studyTime;
    private ImageView imageViewMusic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);
        // method call to initialize the views
        initViews();
        // method call to initialize the listeners
        imageViewStartStop.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        SharedState.getInstance().setFailed(true);
        this.countDownTimer.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SharedState.getInstance().getFailState()){
            Intent failedStudyTime=new Intent(StudyTime.this,Fail.class);
            startActivity(failedStudyTime);
        }
    }

    /**
     * method to initialize the views
     */
    private void initViews() {
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        editTextMinute = (TextView) findViewById(R.id.editTextMinute);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        imageViewStartStop = (ImageView) findViewById(R.id.imageViewStartStop);
        textView31=(TextView)findViewById(R.id.textView31);

        Intent intent=getIntent();//获得用户选择的时间
        int studytime=intent.getIntExtra(ChooseTime.EXTRA_MESSAGE,0);
        studyTime=studytime;
        editTextMinute.setText(""+studytime);
        imageViewMusic=findViewById(R.id.music51);

        imageViewMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicPlayingState==MusicPlaying.STOPING){play(v); }
                else{ pause(v);}
            }
        });

        RequestQueue requestQueue31 = Volley.newRequestQueue(StudyTime.this);
        String url = "https://studev.groept.be/api/a18_sd609/getQuote/";
        url += HomePage.UsersName;//username
        JsonArrayRequest jsonArrayRequest31 = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject myquote = (JSONObject) response.get(0);
                            String extractedQuote=myquote.getString("quote");
                            textView31.setText(extractedQuote);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        };
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue31.add(jsonArrayRequest31);
    }

    /**
     * implemented method to listen clicks
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewStartStop:
                startStop();
                break;
        }
    }
    /**
     * method to start and stop count down timer
     */
    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {
            // call to initialize the timer values
            setTimerValues();
            // call to initialize the progress bar values
            setProgressBarValues();
            // changing play icon to stop icon
            imageViewStartStop.setVisibility(View.INVISIBLE);
            // making edit text not editable
            //editTextMinute.setEnabled(false);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;
            // call to start the count down timer
            startCountDownTimer();
        }

    }

    /**
     * method to initialize the values for count down timer
     */
    private void setTimerValues() {
        int time = 0;
       time=studyTime;
        timeCountInMilliSeconds = time * 60 * 1000;
    }

    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));
                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                stopPlayer();
                // changing stop icon to start icon
                imageViewStartStop.setVisibility(View.GONE);
                // making edit text editable
                //editTextMinute.setEnabled(true);
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED;

                Intent finishCounting=new Intent(StudyTime.this,Success.class);
                finishCounting.putExtra("The time counted successfully",studyTime);
                startActivity(finishCounting);
                StudyTime.this.finish();
            }
        }.start();
        countDownTimer.start();
    }

    /**
     * method to set circular progress bar values
     */
    private void setProgressBarValues() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;

    }


    public void play(View v){
        musicPlayingState=MusicPlaying.PLAYING;
        if(player==null){
            player=MediaPlayer.create(this, R.raw.song);
        }
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                musicPlayingState=MusicPlaying.STOPING;
                stopPlayer();
            }
        });
    }

    public void pause(View v){
        if(player!=null){
            musicPlayingState=MusicPlaying.STOPING;
            player.pause();
        }
    }

    public void stop(View v){
        stopPlayer();
    }
    private void stopPlayer(){
        if(player!=null){
            musicPlayingState=MusicPlaying.STOPING;
            player.release();
            player=null;
            Toast.makeText(this,"MediaPlayer released",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
