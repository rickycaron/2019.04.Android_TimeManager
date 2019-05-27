package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Fail extends AppCompatActivity {
    private Button button61;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);


        button61=findViewById(R.id.button61);
        button61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent failRankingBoard=new Intent(Fail.this, RankingBoard.class);
                startActivity(failRankingBoard);
            }
        });
    }
}
