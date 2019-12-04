package com.huatec.edu.mobileshop.activity;




import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.BaseActivity;
import com.huatec.edu.mobileshop.common.Constants;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.entity.MemberEntity;
import com.huatec.edu.mobileshop.http.presenter.MemberPresenter;
import com.huatec.edu.mobileshop.utils.SystemConfig;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;


    @Override
    public int getContentViewId(){return R.layout.activity_login;}
    @OnClick(R.id.iv_back)
    void close(){finish();}
    @OnClick(R.id.bt_login)
    void login(){
        String userName=et_username.getText().toString().trim();
        String pwd=et_pwd.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            toastShort("请输入用户名");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            toastShort("请输入密码");
        return;
        }
        MemberPresenter.login2(new ProgressDialogSubscriber<MemberEntity>(this) {
        @Override
            public void onNext(MemberEntity memberEntity){
            SystemConfig.setLogin(true);
            toastShort("登录成功");
            SystemConfig.setLoginUserName(memberEntity.uname);
            SystemConfig.setLoginUserEmail(memberEntity.email);
            SystemConfig.setLoginUserHead(memberEntity.image);
            sendLoginBroadcast();
            setResult(RESULT_OK);
            finish();
        }
        },userName,pwd);

    }
    private void sendLoginBroadcast(){
        Intent intent=new Intent();
        intent.setAction(Constants.ACTION_LOGIN);
        intent.putExtra("my_data","这是数据");
        sendBroadcast(intent);
    }

}
