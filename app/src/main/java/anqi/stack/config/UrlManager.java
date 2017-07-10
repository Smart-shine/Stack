package anqi.stack.config;

/**
 *
 * 配置的网络请求地址
 * Created by niuanqi on 2017/7/4.
 */

public  class UrlManager {

    public final static String BASEPATH = "http://9.112.239.137:8080/p4-web-pg-debug";
    public final static String Login = "http://9.112.239.137:8080/p4-web-pg-debug"+"/api/auth/login";

    private final static String LOGIN = "http://";
    private final static String PAGW = "http://";
    private final static String basePath = "http://";

    public final static String testGetUrl = "http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499308548133";

    public final static String AllRequests = "http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499674399340";
    public final static String MyRequests = "http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499676266582&Type=submitted";
    public final static String MyApproved = "http://9.112.239.179:8080/p4-web-pg-debug/api/requests?noCache=1499676431902&Type=approved";

    public final static String baiduTestUrl = "http://api.map.baidu.com/location/ip?ak=请输入您的AK&coor=bd09ll";
}
