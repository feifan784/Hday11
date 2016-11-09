package com.example.hday11_05_zonghe;

import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity {


    private XRecyclerView xrv;
    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;
    private Handler handler = new Handler();
    private TextInputLayout til;
    private TextView tv;
    private EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        initData();

        xrv.setLayoutManager(new LinearLayoutManager(this));

        initAdapter();

        til.setHint("情书要一段字符串");
        til.setErrorEnabled(true);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et.getText().toString().contains("奶茶妹妹")){
                    til.setError("无效输入词");

                }else {
                    til.setError("");
                }
            }
        });
        xrv.setPullRefreshEnabled(true);

        xrv.setLoadingMoreEnabled(true);
        xrv.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);


        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//下拉刷新

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,"下拉刷新的新数据"+System.currentTimeMillis());
                        adapter.notifyDataSetChanged();
                        xrv.refreshComplete();
                    }
                },3000);

            }

            @Override
            public void onLoadMore() {//上拉加载

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("上拉加载的新数据"+System.currentTimeMillis());
                        adapter.notifyDataSetChanged();
                        xrv.loadMoreComplete();
                    }
                },3000);
            }
        });
    }

    private void initAdapter() {

        adapter = new MyAdapter();
        xrv.setAdapter(adapter);


    }

    private void initData() {

        for (int i = 0; i <30 ; i++) {
            list.add("item"+i);
        }

    }

    private void initView() {
        xrv = (XRecyclerView) findViewById(R.id.rcv);
        til = (TextInputLayout) findViewById(R.id.til);
        et = (EditText) findViewById(R.id.et);
//        tv = (TextView) findViewById(R.id.tv);
    }



    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false));
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.iv.setImageResource(R.mipmap.ic_launcher);
            holder.tv.setText(list.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showShare(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder{

            TextView tv;
            ImageView iv;

            public MyHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.textView);
                iv = (ImageView) itemView.findViewById(R.id.imageView);
            }
        }
    }


    private void showShare(int position) {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(list.get(position)+"------徐非凡的分享");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
