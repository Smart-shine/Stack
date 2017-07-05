package anqi.stack.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import anqi.stack.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private TextView text;
    private final String basePath = "http://9.112.239.137:8080/p4-web-pg-debug";
    private final String passWord = "p4admin" + "1qa2ws3ed";
    private Handler h;

    private String token=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
         h = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                text.setText((String)msg.obj);
                return false;
            }
        });
    }

    public void click(View view) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            object.put("username", "p4admin");
            object.put("password", "aq1sw2de");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(object));
//        final Request request = new Request.Builder().url("http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll").build();
        Request request = new Request.Builder().url(basePath + "/api/auth/login").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("tttt", "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                String sss = new String(res_ult.body().bytes());
//                Log.d("json",sss);
                String json = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String s = (String) jsonObject.get("token");
                    token = s;
                    Log.d("success", s);
                    Message msg = Message.obtain();
                    msg.obj = s;
                    h.sendMessage(msg);

                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void click_json(View view) {

        Gson json = new Gson();
        String ss = json.fromJson("hsoghoang", String.class);
        String you = json.toJson(false);

        Log.d("json", ss + you);
    }

//    public void get(View view){
//        String url = basePath+"/app/nls/ZH_CN.json";
//        Map params = new HashMap();
//        params.put("token",token);
//        JSONObject res = NetUtil.doGetSync(url,params);
//
//        try {
//            Log.d("get test",res.getString("p4_title"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


}
