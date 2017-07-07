package anqi.stack.Activity;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

/**
 * Created by niuanqi on 2017/7/4.
 */

public class JsonTest  implements TestInterface{
    private Context context;

    public JsonTest(Context con){
        context = con;
    }


    @Override
    public void json(String result, Dialog dialog) {
//        Toast.makeText(context, "接口", Toast.LENGTH_SHORT).show();
        Log.d("--------------","-----------");
    }
}
