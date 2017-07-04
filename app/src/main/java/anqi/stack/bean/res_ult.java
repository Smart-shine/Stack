package anqi.stack.bean;

/**
 * 对网络请求结果的解析
 * Created by niuanqi on 2017/7/4.
 */

public class res_ult {


    public static String getMenuList() {
        return menuList;
    }

    public static void setMenuList(String menuList) {
        res_ult.menuList = menuList;
    }

    public static String getPermissionList() {
        return permissionList;
    }

    public static void setPermissionList(String permissionList) {
        res_ult.permissionList = permissionList;
    }

    public static String getRoleList() {
        return roleList;
    }

    public static void setRoleList(String roleList) {
        res_ult.roleList = roleList;
    }

    public  String getTonken() {
        return tonken;
    }

    public  void setTonken(String tonken) {
    }

    private static String menuList = "menuList";
    private static String permissionList = "permissionList";
    private static String roleList = "roleList";
    private  String tonken = "token";



}
