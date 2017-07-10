package anqi.stack.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import anqi.stack.R;
import anqi.stack.adapter.ListViewAdapter;
import anqi.stack.config.UrlManager;
import anqi.stack.util.NetUtil;

/**
 * Created by niuanqi on 2017/7/10.
 */

public class mainFragment extends Fragment {
    ExpandableListView expandableListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expanded_menu);
        expandableListView.setAdapter(new ListViewAdapter(getActivity()));
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    Toast.makeText(getActivity(), "主页面被点击", Toast.LENGTH_LONG).show();
                    final Dialog dialog = NetUtil.showWaitingDialog(getActivity());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", "p4admin");
                    params.put("password", "aq1sw2de");
                    NetUtil.doPostAsync(UrlManager.BASEPATH + "/api/auth/login", params, getActivity(), dialog);
                    return true;
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), groupPosition + " " + childPosition, Toast.LENGTH_LONG).show();
                final Dialog dialog = NetUtil.showWaitingDialog(getActivity());
                Map<String, String> params = new HashMap<String, String>();
//                String token = (String) getArguments().get("token");
                String token_from_share = getActivity().getSharedPreferences("global_token", Context.MODE_PRIVATE).getString("token",null);
//                if(token==null) Toast.makeText(getActivity(),"token已经超时",Toast.LENGTH_LONG).show();
                params.put("token",token_from_share);
                if(groupPosition==1){
                    switch (childPosition){
                        case 0:
                            NetUtil.doGetAsync(UrlManager.AllRequests,params,getActivity(),dialog);
                            break;
                        case 1:
                            NetUtil.doGetAsync(UrlManager.MyRequests,params,getActivity(),dialog);
                            break;
                        case 2:
                            NetUtil.doGetAsync(UrlManager.MyApproved,params,getActivity(),dialog);
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                }


                NetUtil.doPostAsync(UrlManager.AllRequests,params,getActivity(),dialog);
                return true;
            }
        });

        return view;
    }
}
