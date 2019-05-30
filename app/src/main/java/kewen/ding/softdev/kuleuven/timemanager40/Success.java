package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Success extends AppCompatActivity {
    private Button button71;
    private EditText editText71;
    private Button button72;
    public int successfulStudyTime;
    private String username;
    private Boolean userNameChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        button71=findViewById(R.id.button71);
        button72=findViewById(R.id.button72);
        editText71=findViewById(R.id.editText71);
        editText71.setText(HomePage.UsersName);
        editText71.setEnabled(true);
        userNameChecked=false;

        Intent intentFromStudy=getIntent();
        successfulStudyTime=intentFromStudy.getIntExtra("The time counted successfully",0);

        button71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userNameChecked){
                    Toast.makeText(Success.this,"You need to input your username correctly",Toast.LENGTH_SHORT).show();;
                }
                else{
                    RequestQueue requestQueue = Volley.newRequestQueue(Success.this);
                    String url = "https://studev.groept.be/api/a18_sd609/addMark/";
                    if(username==null){
                        Toast.makeText(Success.this, "You need to input your username", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        url += username;//username
                        url += "/" + successfulStudyTime;//minutes

                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                                Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Intent successRaingBoear = new Intent(Success.this, RankingBoard.class);
                                        startActivity(successRaingBoear);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }

                        );
                        requestQueue.add(jsonArrayRequest);

                    }
                }
            }//onClick
        });

        button72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(Success.this);
                username= editText71.getText().toString();
                String url = "https://studev.groept.be/api/a18_sd609/checkUserName/";
                url=url+username;

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject isCorrectColumn = (JSONObject) response.get(0);
                                    if (isCorrectColumn.getInt("isCorrect") == 0) {
                                        Toast.makeText(Success.this, "Valid Username", Toast.LENGTH_SHORT).show();
                                        userNameChecked=true;
                                        editText71.setEnabled(false);
                                    }
                                    else {
                                        Toast.makeText(Success.this, "This name doesn't exist", Toast.LENGTH_SHORT).show();
                                        userNameChecked=false;
                                    }
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
                requestQueue.add(jsonArrayRequest);

            }
        });



    }
}
