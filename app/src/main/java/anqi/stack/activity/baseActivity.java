package anqi.stack.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import anqi.stack.R;
import anqi.stack.util.NetUtil;

/**
 * Created by niuanqi on 2017/7/7.
 */

public class baseActivity extends AppCompatActivity {
    //重新构造的根布局,include标签里面的
    private LinearLayout root_layout;

    //每个界面都会有的Title和说明
    private TextView template_title, template_title_annotation;
    private LinearLayout template_linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_root_layout);
        initTempalteView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetUtil.getInstent().dispatcher().cancelAll();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        root_layout = (LinearLayout) findViewById(R.id.template_Tag);
        if (root_layout != null) {
            root_layout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void initTempalteView() {
        root_layout = (LinearLayout) findViewById(R.id.template_Tag);
        template_title = (TextView) findViewById(R.id.template_title);
        template_title_annotation = (TextView) findViewById(R.id.template_title_annotation);
    }

    public void setTile(String title) {
        template_title.setText(title);
    }

    public void setAnnotation(String annotation) {
        template_title_annotation.setText(annotation);
    }
}
