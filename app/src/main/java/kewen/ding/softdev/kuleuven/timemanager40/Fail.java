package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Fail extends AppCompatActivity {
    private Button button61;
    private ImageView imageView61;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);
        imageView61=findViewById(R.id.imageView61);
        imageView61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Fail.this, "The egg is cracked", Toast.LENGTH_SHORT).show();
            }
        });

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
