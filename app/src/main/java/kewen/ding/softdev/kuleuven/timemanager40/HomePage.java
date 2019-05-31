package kewen.ding.softdev.kuleuven.timemanager40;

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

public class HomePage extends AppCompatActivity {
    private Button button11;
    private String userName;
    private TextView textView11;
    private Button button13;
    private Button button12;
    private EditText editText11;
    public static String UsersName;
    private TextView textView12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        textView11=findViewById(R.id.textView11);
        button13=findViewById(R.id.button13);
        button12=findViewById(R.id.button12);
        editText11=findViewById(R.id.editText11);
        textView12=findViewById(R.id.textView12);

        Intent loginSuccess=getIntent();//捕获intent
        userName=loginSuccess.getStringExtra("This UserName");
        if (userName!=null){
            UsersName=userName;
        }

        textView11.setText("Hello,"+UsersName);


        button11=(Button)findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,ChooseTime.class);
                startActivity(intent);
            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backout=new Intent(HomePage.this, MainActivity.class);
                startActivity(backout);
                finish();
            }
        });

        RequestQueue requestQueue11 = Volley.newRequestQueue(HomePage.this);
        String url = "https://studev.groept.be/api/a18_sd609/getQuote/";
        url += UsersName;//username

        JsonArrayRequest jsonArrayRequest11 = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject myquote = (JSONObject) response.get(0);
                            String extractedQuote=myquote.getString("quote");
                            textView12.setText(extractedQuote);
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
        requestQueue11.add(jsonArrayRequest11);



        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            RequestQueue requestQueue13 = Volley.newRequestQueue(HomePage.this);
                            String url13 = "https://studev.groept.be/api/a18_sd609/checkUserInQueto/";
                            url13 += UsersName;//username
                            JsonArrayRequest jsonArrayRequest13 = new JsonArrayRequest(
                                    Request.Method.GET, url13, null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            try {
                                                JSONObject isChecked = (JSONObject) response.get(0);
                                                if (isChecked.getInt("isCorrect") == 1) {
                                                    RequestQueue requestQueue14 = Volley.newRequestQueue(HomePage.this);
                                                    String url14 = "https://studev.groept.be/api/a18_sd609/deleteQueto/";
                                                    url14 += UsersName;//username
                                                    JsonArrayRequest jsonArrayRequest14 = new JsonArrayRequest(
                                                            Request.Method.GET, url14, null,
                                                            new Response.Listener<JSONArray>() {
                                                                @Override
                                                                public void onResponse(JSONArray response) {

                                                                    RequestQueue requestQueue12 = Volley.newRequestQueue(HomePage.this);
                                                                    String url12 = "https://studev.groept.be/api/a18_sd609/add_quote/";
                                                                    String myQuote = editText11.getText().toString();
                                                                    url12 += UsersName;//username
                                                                    url12 += "/" + myQuote;//quote
                                                                    if (myQuote == null) {
                                                                        Toast.makeText(HomePage.this, "Type your new quote", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        JsonArrayRequest jsonArrayRequest12 = new JsonArrayRequest(
                                                                                Request.Method.GET, url12, null,
                                                                                new Response.Listener<JSONArray>() {
                                                                                    @Override
                                                                                    public void onResponse(JSONArray response) {
                                                                                        RequestQueue requestQueue11 = Volley.newRequestQueue(HomePage.this);
                                                                                        String url = "https://studev.groept.be/api/a18_sd609/getQuote/";
                                                                                        url += UsersName;//username

                                                                                        JsonArrayRequest jsonArrayRequest11 = new JsonArrayRequest(
                                                                                                Request.Method.GET, url, null,
                                                                                                new Response.Listener<JSONArray>() {
                                                                                                    @Override
                                                                                                    public void onResponse(JSONArray response) {
                                                                                                        try {
                                                                                                            JSONObject myquote = (JSONObject) response.get(0);
                                                                                                            String extractedQuote=myquote.getString("quote");
                                                                                                            textView12.setText(extractedQuote);
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
                                                                                        requestQueue11.add(jsonArrayRequest11);
                                                                                    }
                                                                                },
                                                                                new Response.ErrorListener() {
                                                                                    @Override
                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                    }
                                                                                }
                                                                        );
                                                                        requestQueue12.add(jsonArrayRequest12);
                                                                    }

                                                                }
                                                            },
                                                            new Response.ErrorListener() {
                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                }
                                                            });
                                                    requestQueue14.add(jsonArrayRequest14);
                                                }
                                                else if(isChecked.getInt("isCorrect") == 0){
                                                    RequestQueue requestQueue12 = Volley.newRequestQueue(HomePage.this);
                                                    String url12 = "https://studev.groept.be/api/a18_sd609/add_quote/";
                                                    String myQuote = editText11.getText().toString();
                                                    url12 += UsersName;//username
                                                    url12 += "/" + myQuote;//quote
                                                    if (myQuote == null) {
                                                        Toast.makeText(HomePage.this, "Type your new quote", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        JsonArrayRequest jsonArrayRequest12 = new JsonArrayRequest(
                                                                Request.Method.GET, url12, null,
                                                                new Response.Listener<JSONArray>() {
                                                                    @Override
                                                                    public void onResponse(JSONArray response) {
                                                                        RequestQueue requestQueue11 = Volley.newRequestQueue(HomePage.this);
                                                                        String url = "https://studev.groept.be/api/a18_sd609/getQuote/";
                                                                        url += UsersName;//username

                                                                        JsonArrayRequest jsonArrayRequest11 = new JsonArrayRequest(
                                                                                Request.Method.GET, url, null,
                                                                                new Response.Listener<JSONArray>() {
                                                                                    @Override
                                                                                    public void onResponse(JSONArray response) {
                                                                                        try {
                                                                                            JSONObject myquote = (JSONObject) response.get(0);
                                                                                            String extractedQuote=myquote.getString("quote");
                                                                                            textView12.setText(extractedQuote);
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
                                                                        requestQueue11.add(jsonArrayRequest11);
                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {
                                                                    }
                                                                }
                                                        );
                                                        requestQueue12.add(jsonArrayRequest12);
                                                    }







                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }; }},
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }
                            );
                            requestQueue13.add(jsonArrayRequest13);



                    }//onClick()
        });

    }//onCreate()
}//definition of the class
