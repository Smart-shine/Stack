package anqi.stack.Activity;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import anqi.stack.R;
import anqi.stack.util.NetUtil;

import static anqi.stack.util.NetUtil.showWaitingDialog;

public class MainActivity extends AppCompatActivity implements TestInterface{

    //    private final OkHttpClient client = new OkHttpClient();
    private static TextView text;
    private final String basePath = "http://9.112.239.137:8080/p4-web-pg-debug";
    private static String token;
    public static Handler tool = new Handler();
    //    public static Handler tool0 = new Handler();
    private static JSONObject _json;
    static Dialog waiting;
    static Dialog waiting0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        SQLiteDatabase db = this.openOrCreateDatabase("stack.db", Context.MODE_PRIVATE,null);
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.AsyncPost:
                waiting = showWaitingDialog(this);
                Map<String, String> params = new HashMap<>();
                params.put("username", "p4admin");
                params.put("password", "aq1sw2de");
                NetUtil.doPostAsync(basePath + "/api/auth/login", params,this,waiting);
                break;
            case R.id.AsyncGet:
                waiting0 = showWaitingDialog(this);
                String test = "http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll";
                String url = " http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499308548133";
                Map AsyncGetParams = new HashMap();
                AsyncGetParams.put("token", token);
                NetUtil.doGetAsync(url, AsyncGetParams,this,waiting0);
                break;
            case R.id.SyncGet:
                Dialog waiting0 = showWaitingDialog(this);
//                String tests = "http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll";
                String SyncGeturl = " http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499308548133";
                Map SyncGetParams = new HashMap();
                SyncGetParams.put("token", token);
                NetUtil.doGetSync(SyncGeturl, SyncGetParams,this,waiting0);
                break;
            case R.id.SyncPost:
                Dialog dialog = showWaitingDialog(this);
                Map<String, String> SyncPostParam = new HashMap<>();
                SyncPostParam.put("username", "p4admin");
                SyncPostParam.put("password", "aq1sw2de");
                NetUtil.doPostSyc(basePath + "/api/auth/login", SyncPostParam,this,dialog);
                break;

        }


    }

    public void click_json(View view) {
//        Gson json = new Gson();
//        String ss = json.fromJson("hsoghoang", String.class);
//        String you = json.toJson(false);
//
//        Log.d("json", ss + you);

    }

    public static void initjson(JSONObject json) {
        _json = json;
        try {
            text.setText(_json.getString("id"));
            token = _json.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        waiting.dismiss();

    }

    public static void initjsonArray(JSONArray json) {
        JSONArray _json;
        _json = json;
        try {
            text.setText(_json.get(0).toString());
//            token = _json.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void json(final String result, final Dialog dialog) {
        Log.d("999999999999","333333333333");
        String flag = result.substring(0,1);
        if(flag.equals("{")){
            try {
                JSONObject jsonObject = new JSONObject(result);
                token = jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(flag.equals("["))
            try {
                JSONArray jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    text.setText(result);
                    dialog.dismiss();
            }
        });

    }


//    @Override
//    public void json(JSONArray jsonObject) {
//        JSONArray jsonArray = jsonObject;
//        try {
//            text.setText(jsonArray.get(0).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
