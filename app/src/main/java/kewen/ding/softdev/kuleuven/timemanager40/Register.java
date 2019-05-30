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

public class Register extends AppCompatActivity {

    private EditText editText51;
    private EditText editText52;
    private EditText editText53;
    private Button button51;
    private Button button52;
    private TextView textView52;
    private Boolean checkName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText51=findViewById(R.id.editText51);
        editText52=findViewById(R.id.editText52);
        editText53=findViewById(R.id.editText53);
        button51=findViewById(R.id.button51);
        button52=findViewById(R.id.button52);
        textView52=findViewById(R.id.textView52);
        checkName=false;
        editText52.setVisibility(View.INVISIBLE);
        editText53.setVisibility(View.INVISIBLE);
        button51.setVisibility(View.VISIBLE);
        button52.setVisibility(View.INVISIBLE);

        textView52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentloginnow=new Intent(Register.this,MainActivity.class);
                startActivity(intentloginnow);
            }
        });

        button51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText51.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                String url = "https://studev.groept.be/api/a18_sd609/checkUserName/";
                url=url+name;

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject isCorrectColumn = (JSONObject) response.get(0);
                                    if (isCorrectColumn.getInt("isCorrect") == 1) {
                                        Toast.makeText(Register.this, "UserName Available", Toast.LENGTH_SHORT).show();
                                        checkName=true;
                                        editText52.setVisibility(View.VISIBLE);
                                        editText53.setVisibility(View.VISIBLE);
                                        button51.setVisibility(View.INVISIBLE);
                                        button52.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        Toast.makeText(Register.this, "This name existed", Toast.LENGTH_SHORT).show();
                                        checkName=false;
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
            }//button51 onClick()
        });//button51setOnClickListener

        button52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkName){
                    RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                    String url = "https://studev.groept.be/api/a18_sd609/addUser/";
                    final String name=editText51.getText().toString();
                    final String password1=editText52.getText().toString();
                    String password2=editText53.getText().toString();
                    if(!password1.equals(password2)){
                        Toast.makeText(Register.this, "Your passwords are different", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        url += name;//username
                        url += "/" + password1;//password
                        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(
                                Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        Toast.makeText(Register.this, "Success in Register", Toast.LENGTH_SHORT).show();
                                        Intent intentLogin=new Intent(Register.this,MainActivity.class);
                                        intentLogin.putExtra("uesr_userName", name);
                                        intentLogin.putExtra("uesr_password", password1);
                                        startActivity(intentLogin);

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }
                        );
                        requestQueue.add(jsonArrayRequest2);
                    }}
                else {
                    Toast.makeText(Register.this,"You need to Check First",Toast.LENGTH_SHORT).show();
                }
            }//onClick()
        });//button52()onClickListener()





    }
}
