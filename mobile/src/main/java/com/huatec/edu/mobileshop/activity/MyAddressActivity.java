package com.huatec.edu.mobileshop.activity;

import android.widget.TextView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAddressActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_Title;
    @Override
    public int getContentViewId(){
        return R.layout.activity_my_address;
    }
    @Override
    protected void initView(){
        super.initView();
        tv_Title.setText("我的地址");
    }
    @OnClick(R.id.iv_back)
    void close(){
        finish();
    }

}