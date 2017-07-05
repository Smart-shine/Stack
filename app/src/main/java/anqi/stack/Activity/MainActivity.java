package anqi.stack.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import anqi.stack.R;
import anqi.stack.util.NetUtil;

public class MainActivity extends AppCompatActivity {

//    private final OkHttpClient client = new OkHttpClient();
    private TextView text;
    private final String basePath = "http://9.112.239.137:8080/p4-web-pg-debug";

    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
    }

    public void click(View view) {

        Map<String,String> params = new HashMap<>();
        params.put("username","p4admin");
        params.put("password","aq1sw2de");
        JSONObject result = NetUtil.doPostAsync(basePath+"/api/auth/login",params);
        try {
            text.setText(result.getString("token"));
            token = result.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        final Request request = new Request.Builder().url("http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll").build();
    }

    public void click_json(View view) {

//        Gson json = new Gson();
//        String ss = json.fromJson("hsoghoang", String.class);
//        String you = json.toJson(false);
//
//        Log.d("json", ss + you);


    }

    public void get(View view){
        String test = "http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll";
        String url = basePath+"/app/nls/ZH_CN.json";
        Map params = new HashMap();
        params.put("token",token);
        JSONObject res = NetUtil.doGetSync(test,params);

        try {
            Log.d("get test",res.getString("message"));
            text.setText(res.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
