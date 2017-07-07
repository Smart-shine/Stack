package anqi.stack.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import anqi.stack.Activity.TestInterface;
import anqi.stack.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装网络请求，get,post方法
 * Created by niuanqi on 2017/7/4.
 */

public class NetUtil {
    private static OkHttpClient client  = new OkHttpClient();
    private static OkHttpClient.Builder builder;
    private static ReentrantLock lock = new ReentrantLock();
    private static long ConnecTimeOut = 10;//10秒链接超时
    private static long ReadTimeOut = 10;//10秒读超时
    private static long WriteTimeOut = 10;//10秒写超时

    /**
     * 初始化Client,设置相应超时参数
     */
     static  {
        if (builder == null) {
            lock.lock();
            builder = new OkHttpClient.Builder();
            lock.unlock();
        }
        if (client == null) {
            lock.lock();
            client = builder.connectTimeout(ConnecTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(WriteTimeOut, TimeUnit.SECONDS)
                    .readTimeout(ReadTimeOut, TimeUnit.SECONDS)
                    .build();
            lock.unlock();
        }
    }

    /**
     * 同步get请求
     *
     * @param url 地址
     * @return 结果json
     */
    public static void doGetSync(String url, Map params, final Context context, final Dialog dialog) {

        final Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:52.0) Gecko/20100101 Firefox/52.0")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Host","9.112.239.179:8080")
                .addHeader("Referer","http://9.112.239.179:8080/p4-web-pg-debug/")
                .addHeader("IPC_TOKEN", (String) params.get("token"))
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request)
                            .execute();
                    String res = response.body().string();
                    ((TestInterface)context).json(res,dialog);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 同步post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static void doPostSyc(String url, Map params, final Context context, final Dialog dialog) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            Set<String> keySet = params.keySet();
            for (String key:keySet) {
                object.put(key,params.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(object));
        final  Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String Result = response.body().string();
                    ((TestInterface)context).json(Result,dialog);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 异步get
     *
     * @param url
     * @return
     */
    public static void doGetAsync(String url, Map params, final Context context, final Dialog dialog) {
        final Request request = new Request.Builder()
                .url(url)
                .header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:52.0) Gecko/20100101 Firefox/52.0")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Encoding","gzip, deflate")
                .addHeader("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3")
                .addHeader("Host","9.112.239.179:8080")
                .addHeader("Referer","http://9.112.239.179:8080/p4-web-pg-debug/")
                .addHeader("IPC_TOKEN", (String) params.get("token"))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d("失败","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                ((TestInterface)context).json(result,dialog);

            }
        });
    }

    /**
     * 异步post
     *
     * @param url
     * @param params
     * @return
     */
    public static void doPostAsync(String url, Map params, final Context context, final Dialog dialog) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject object = new JSONObject();
        try {
            Set<String> keySet = params.keySet();
            for (String key:keySet) {
                object.put(key,params.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, String.valueOf(object));
        final Request request = new Request.Builder().url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("failed", "failed");
                e.printStackTrace();
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                String result = response.body().string();
                ((TestInterface)context).json(result,dialog);
//                try {
//                    final JSONObject resJson = new JSONObject(json);
//                    String s = (String) resJson.get("token");
//
////                    tool.post(new Runnable() {
////                        @Override
////                        public void run() {
////                            MainActivity.initjson(resJson);
////                        }
////                    });
//                    Log.d("success", s);
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    public static Dialog showWaitingDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_custom_progress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


}
