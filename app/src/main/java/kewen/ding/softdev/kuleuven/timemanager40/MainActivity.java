package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.util.prefs.PreferenceChangeEvent;

public class MainActivity extends AppCompatActivity {
    private TextView txtText;
    private Button button1;
    private EditText editText;
    private EditText editText12;
    private Button button2;
    private TextView textView2;
    public String userNameThis;
    private CheckBox checkBox11;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        editText=findViewById(R.id.editText);
        editText12=findViewById(R.id.editText12);
        textView2=findViewById(R.id.textView2);
        userNameThis=null;

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        checkBox11=(CheckBox)findViewById(R.id.checkbox1);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            editText.setText(account);
            editText12.setText(password);
            checkBox11.setChecked(true);
        }

        Intent intentFromRegister=getIntent();
        String username=intentFromRegister.getStringExtra("uesr_userName");
        String password=intentFromRegister.getStringExtra("uesr_password");
        if(username!=null&&password!=null) {
            editText.setText(username);
            editText12.setText(password);
            userNameThis=username;
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
                final String usernameInput=editText.getText().toString();
                final String passwordInput=editText12.getText().toString();
                url += usernameInput;//username
                url += "/"+passwordInput;//password

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject isCorrectColumn = (JSONObject) response.get(0);
                                    if (isCorrectColumn.getInt("isCorrect") == 1) {
                                        editor=pref.edit();
                                        if(checkBox11.isChecked()){
                                            editor.putBoolean("remember_password",true);
                                            editor.putString("account",usernameInput);
                                            editor.putString("password",passwordInput);
                                        }else{editor.clear();}
                                        editor.apply();

                                        userNameThis=usernameInput;
                                        Toast.makeText(MainActivity.this, "Success in Login", Toast.LENGTH_SHORT).show();
                                        Intent intentIn=new Intent(MainActivity.this,HomePage.class);
                                        intentIn.putExtra("This UserName",userNameThis);
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
            }//onClick()
        });



        button2.setVisibility(View.INVISIBLE);
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





    }
}
