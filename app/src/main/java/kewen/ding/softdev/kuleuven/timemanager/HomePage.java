package kewen.ding.softdev.kuleuven.timemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {
    private Button button11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        button11=(Button)findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,ChooseTime.class);
                startActivity(intent);
            }
        });
    }
}
