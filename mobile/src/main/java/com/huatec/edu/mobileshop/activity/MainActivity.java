package com.huatec.edu.mobileshop.activity;


import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.BaseActivity;
import com.huatec.edu.mobileshop.fragment.NavigationFragment;

public class MainActivity extends BaseActivity {

    @Override
 public @LayoutRes
    int getContentViewId(){
        return R.layout.activity_main;
    }
    protected void initView(){
        super.initView();
        FragmentManager manager =getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.f1_main,new NavigationFragment());
        transaction.commit();
    }
}
