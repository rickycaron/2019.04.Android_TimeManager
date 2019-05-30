package kewen.ding.softdev.kuleuven.timemanager40;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankingBoard extends AppCompatActivity {
    private TextView textView81;
    public ArrayList<String> userNameStrings=new ArrayList<String>();
    public List<String> markStrings=new ArrayList<String>();
    private Button button81;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_board);
        textView81=findViewById(R.id.textView81);
        button81=(Button)findViewById(R.id.button81);

            RequestQueue requestQueue = Volley.newRequestQueue(RankingBoard.this);
            String url = "https://studev.groept.be/api/a18_sd609/rankShow";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    String userName = ""+(i+1)+":  "+((JSONObject) response.get(i)).getString("userName");
                                    int wholemark = ((JSONObject) response.get(i)).getInt("wholemark");
                                    userNameStrings.add(userName);
                                    markStrings.add(" "+wholemark);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest);
            int number=userNameStrings.size();
            String [] userNameString =new String[number];
            String[] markString=new String[number];
            for(int i=0;i<number;i++){
                userNameString[i]=userNameStrings.get(i);
                markString[i]=markStrings.get(i);
            }
            ArrayAdapter<String> adapter1=new ArrayAdapter<String>(RankingBoard.this,android.R.layout.simple_list_item_1,userNameString);
            ListView listView81=(ListView)findViewById(R.id.listView81);
            listView81.setAdapter(adapter1);

            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(RankingBoard.this,android.R.layout.simple_list_item_1,markString);
            ListView listView82=(ListView)findViewById(R.id.listView82);
            listView82.setAdapter(adapter2);






        textView81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(RankingBoard.this);
                String url = "https://studev.groept.be/api/a18_sd609/rankShow";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET, url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        String userName = ((JSONObject) response.get(i)).getString("userName");
                                        int wholemark = ((JSONObject) response.get(i)).getInt("wholemark");
                                        userNameStrings.add(userName);
                                        markStrings.add(" "+wholemark);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                );
                requestQueue.add(jsonArrayRequest);
                int number=userNameStrings.size();
                String [] userNameString =new String[number];
                String[] markString=new String[number];
                for(int i=0;i<number;i++){
                    userNameString[i]=userNameStrings.get(i);
                    markString[i]=markStrings.get(i);
                }
                ArrayAdapter<String> adapter1=new ArrayAdapter<String>(RankingBoard.this,android.R.layout.simple_list_item_1,userNameString);
                ListView listView81=(ListView)findViewById(R.id.listView81);
                listView81.setAdapter(adapter1);

                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(RankingBoard.this,android.R.layout.simple_list_item_1,markString);
                ListView listView82=(ListView)findViewById(R.id.listView82);
                listView82.setAdapter(adapter2);
            }
        });

        button81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goHomeintent=new Intent(RankingBoard.this,HomePage.class);
                startActivity(goHomeintent);
                finish();
            }
        });

    }
}
