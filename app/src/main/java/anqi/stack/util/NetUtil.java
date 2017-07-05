package anqi.stack.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装网络请求，get,post方法
 * Created by niuanqi on 2017/7/4.
 */

public class NetUtil {
    private static OkHttpClient client;
    private static OkHttpClient.Builder builder;
    private static ReentrantLock lock = new ReentrantLock();
    private static long ConnecTimeOut = 10;//10秒链接超时
    private static long ReadTimeOut = 10;//10秒读超时
    private static long WriteTimeOut = 10;//10秒写超时
    private static JSONObject resJson;


    /**
     * 初始化Client,设置相应超时参数
     */
    private static void initClient() {


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
    public static JSONObject doGetSync(String url, Map params) {
        initClient();

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
        try {
            Response response = client.newCall(request)
                    .execute();
            String res = response.body().string();
            resJson = new JSONObject(res);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resJson;
    }

    /**
     * 同步post请求
     *
     * @param rul
     * @param params
     * @return
     */
    public static JSONObject doPostSyc(String rul, Map params) {
        initClient();
        return resJson;
    }

    /**
     * 异步get
     *
     * @param url
     * @return
     */
    public static JSONObject doGetAsync(String url, Map params) {
        initClient();
        return resJson;
    }

    /**
     * 异步post
     *
     * @param url
     * @param params
     * @return
     */
    public static JSONObject doPostAsync(String url, Map params) {
        initClient();
        return resJson;
    }

}
