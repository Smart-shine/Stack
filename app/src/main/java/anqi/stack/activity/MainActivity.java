package anqi.stack.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import anqi.stack.R;
import anqi.stack.config.UrlManager;
import anqi.stack.util.NetUtil;

import static anqi.stack.util.NetUtil.showWaitingDialog;

public class MainActivity extends baseActivity implements TestInterface {

    private static TextView text;
    private final String basePath = "http://9.112.239.137:8080/p4-web-pg-debug";
    private static String token;
    static Dialog waiting;
    static Dialog waiting0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        SQLiteDatabase db = this.openOrCreateDatabase("stack.db", Context.MODE_PRIVATE, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetUtil.getInstent().dispatcher().cancelAll();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.AsyncPost:
                waiting = showWaitingDialog(this);
                Map<String, String> params = new HashMap<>();
                params.put("username", "p4admin");
                params.put("password", "aq1sw2de");
                NetUtil.doPostAsync(basePath + "/api/auth/login", params, this, waiting);
                break;
            case R.id.AsyncGet:
                waiting0 = showWaitingDialog(this);
                String url = UrlManager.testGetUrl;
                Map AsyncGetParams = new HashMap();
                AsyncGetParams.put("token", token);
                NetUtil.doGetAsync(url, AsyncGetParams, this, waiting0);
                break;
            case R.id.SyncGet:
                Dialog waiting0 = showWaitingDialog(this);
                String SyncGeturl = UrlManager.testGetUrl;
                Map SyncGetParams = new HashMap();
                SyncGetParams.put("token", token);
                NetUtil.doGetSync(SyncGeturl, SyncGetParams, this, waiting0);
                break;
            case R.id.SyncPost:
                Dialog dialog = showWaitingDialog(this);
                Map<String, String> SyncPostParam = new HashMap<>();
                SyncPostParam.put("username", "p4admin");
                SyncPostParam.put("password", "aq1sw2de");
                NetUtil.doPostSyc(basePath + "/api/auth/login", SyncPostParam, this, dialog);
                break;
            case R.id.db:
                Intent startDB = new Intent(this,DBdemo.class);
                startActivity(startDB);
                break;

        }

    }
    @Override
    public void json(final String result, final Dialog dialog) {
        Log.d("999999999999", "333333333333");
        String flag = result.substring(0, 1);
        if (flag.equals("{")) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                token = jsonObject.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (flag.equals("["))
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

}
