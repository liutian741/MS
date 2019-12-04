package com.huatec.edu.mobileshop.activity;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.common.BaseActivity;
import com.huatec.edu.mobileshop.http.ProgressDialogSubscriber;
import com.huatec.edu.mobileshop.http.entity.GoodsDetailEntity;
import com.huatec.edu.mobileshop.http.presenter.GoodsPresenter;
import com.huatec.edu.mobileshop.utils.SystemConfig;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class GoodsDetailActivity  extends BaseActivity {
    private static final String TAG="goodsDetailActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.image_paper)
    ViewPager viewPager;
    @BindView(R.id.image_def)
    ImageView imageDef;
    @BindView(R.id.image_paper_index)
    TextView imagePaperIndex;

    private int goods_id;
    private String goods_name;
    private List<GoodsDetailEntity.GisBean> datas;
    private MyPagerAdapter pagerAdapter;
    @Override
    public int getContentViewId(){
        return R.layout.acticity_goods_detail;
    }

    @OnClick(R.id.favorite_layout)
    void favorite(){
        if(SystemConfig.isLogin()){
            toastShort("收藏");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1001);
        }
    }
    @OnClick(R.id.cat_layout)
    void cat(){
        if(SystemConfig.isLogin()){
            toastShort("购物车");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1002);
        }
    }
    @OnClick(R.id.add_to_cart)
    void add_to_cart(){
        if(SystemConfig.isLogin()){
            toastShort("添加到购物车");
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),1003);
        }
    }
    @Override
    protected void initView(){
        super.initView();
        goods_id=getIntent().getIntExtra("goods_id",0);
        goods_name=getIntent().getStringExtra("goods_name");
        tvTitle.setMaxEms(9);
        tvTitle.setLines(1);
        if(TextUtils.isEmpty(goods_name)){
            tvTitle.setText("商品详情");
        }else {
            tvTitle.setText(goods_name);
        }
        datas=new ArrayList<>();
        pagerAdapter=new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                imagePaperIndex.setText(String.format("%d/%d",position+1,datas.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }







    @Override
    protected void initData(){
        super.initData();
        GoodsPresenter.goodsDetail(new ProgressDialogSubscriber<GoodsDetailEntity>(GoodsDetailActivity.this) {
            @Override
            public void onNext(GoodsDetailEntity goodsDetailEntity) {
                datas.clear();
                List<GoodsDetailEntity.GisBean>gis=goodsDetailEntity.getGis();
                if(gis!=null&&gis.size()!=0){
                    imageDef.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                    datas.addAll(gis);
                    imagePaperIndex.setText(String.format("%d/%d",1,datas.size()));
                    pagerAdapter.notifyDataSetChanged();
                }else {
                    imageDef.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.GONE);
                }
            }
        },goods_id);
    }
    @OnClick(R.id.iv_back)
    void close(){
        finish();
    }
    public  class  MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount(){
            return datas.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1){
            return arg0==arg1;
        }
        @Override
        public void destroyItem(ViewGroup view,int position,Object object){
            view.removeView((View)object);
        }
        @Override
        public Object instantiateItem(ViewGroup view, final int position){
            GoodsDetailEntity.GisBean gisBean=datas.get(position);
            ImageView imageView =new ImageView(GoodsDetailActivity.this);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toastShort("onClick position="+position);
                }
            });
            ImageLoader.getInstance().displayImage(gisBean.getThumbnail(), imageView);
            view.addView(imageView);
            return imageView;
        }
    }
}
