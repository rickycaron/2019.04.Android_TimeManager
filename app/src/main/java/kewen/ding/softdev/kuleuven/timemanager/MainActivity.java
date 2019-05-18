package kewen.ding.softdev.kuleuven.timemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    private TextView txtText;
    private Button button1;
    private EditText editText;
    private EditText editText12;
    private Button button2;
    private Button button3;
    private Button button14;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(Button) findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        editText=findViewById(R.id.editText);
        editText12=findViewById(R.id.editText12);
        textView2=findViewById(R.id.textView2);

        Intent intentFromRegister=getIntent();

        String username=intentFromRegister.getStringExtra("uesr_userName");
        String password=intentFromRegister.getStringExtra("uesr_password");
        if(username!=null&&password!=null) {
            editText.setText(username);
            editText12.setText(password);
        }

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
                Intent intentRegister=new Intent(MainActivity.this, Register.class);
                startActivity(intentRegister);
            }
        });


        //String text=txtText.getText().toString();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://studev.groept.be/api/a18_sd609/checkUser/";
                url += editText.getText().toString();//username
                url += "/"+editText12.getText().toString();//password

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject isCorrectColumn = (JSONObject) response.get(0);
                                    if (isCorrectColumn.getInt("isCorrect") == 1) {
                                        Toast.makeText(MainActivity.this, "Success in Login", Toast.LENGTH_SHORT).show();
                                        Intent intentIn=new Intent(MainActivity.this,HomePage.class);
                                        startActivity(intentIn);
                                        //success
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Failed in Login", Toast.LENGTH_SHORT).show();
                                        //fail
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                };

//                                String combinedCol = "";
//                                for (int i = 0; i < response.length(); i++) {
//                                    try {
//                                        int id = ((JSONObject) response.get(i)).getInt("id");
//                                        String col = ((JSONObject) response.get(i)).getString("column");
//                                        combinedCol += col;
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                txtText.setText(combinedCol);
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



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
                String url="https://studev.groept.be/api/a18_sd609/tests";

                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        int id = ((JSONObject) response.get(i)).getInt("id");//id和number是表格的名字
                                        String col = ((JSONObject) response.get(i)).getString("number");//从表格里面得到数据，在可以使用listView或者ArrayList 把它展示出来

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });



    }//onCreate()
}//definition of the class
