package anqi.stack.activity;

import android.app.Dialog;
import android.app.FragmentTransaction;
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
import anqi.stack.fragment.mainFragment;
import anqi.stack.util.NetUtil;

import static anqi.stack.util.NetUtil.showWaitingDialog;

public class MainActivity extends baseActivity implements TestInterface {

    private static TextView text;
    public static String token;
    static Dialog waiting;
    static Dialog waiting0;
    int flag = 0;
    mainFragment fragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new mainFragment();
        text = (TextView) findViewById(R.id.text);
//        SQLiteDatabase db = this.openOrCreateDatabase("stack.db", Context.MODE_PRIVATE, null);
    }

    public void click(View view) {

        switch (view.getId()) {
            case R.id.AsyncPost:
                waiting = showWaitingDialog(this);
                Map<String, String> params = new HashMap<>();
                params.put("username", "p4admin");
                params.put("password", "aq1sw2de");
                NetUtil.doPostAsync(UrlManager.BASEPATH + "/api/auth/login", params, this, waiting);
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
                NetUtil.doPostSyc(UrlManager.BASEPATH + "/api/auth/login", SyncPostParam, this, dialog);
                break;
            case R.id.slide:
                if(flag%2==0) {
                    flag++;
                    if(fragment!=null){
                        Bundle bundle = new Bundle();
                        bundle.putCharSequence("token",token);
                        fragment.setArguments(bundle);
                    }
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, null)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }else {
                    flag--;
                    getFragmentManager().beginTransaction()
                            .remove(fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }
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
                getSharedPreferences("global_token",MODE_PRIVATE).edit().putString("token",token).apply();

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
