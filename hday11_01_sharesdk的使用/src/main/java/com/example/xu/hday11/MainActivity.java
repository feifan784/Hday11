package com.example.xu.hday11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShareSDK.initSDK(this);
    }

    public void click(View view) {

        switch(view.getId()){
            case R.id.but_share:
            showShare();


            break;
            case R.id.but_sina_share:

                SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                sp.setText("测试分享的文本");
                sp.setImagePath("/mnt/sdcard/xffpic/1477923766747.png");
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.i("xff", "onComplete: "+"分享成功");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                        Log.i("xff", "onError: "+throwable.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                weibo.share(sp);

            break;
            case R.id.but_power1:
                Platform weiboq = ShareSDK.getPlatform(QQ.NAME);
                weiboq.SSOSetting(false);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weiboq.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        Log.i("xff", "onError: "+arg2.getMessage());
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        arg0.getDb().exportData();
                        Log.i("xff", "onComplete: "+arg0.getDb().exportData());
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
//authorize与showUser单独调用一个即可
                weiboq.authorize();//单独授权,OnComplete返回的hashmap是空的
//                weibo.showUser(null);//授权并获取用户信息
//移除授权
//weibo.removeAccount(true);





                break;
            case R.id.but_power2:

                Platform weiboqq = ShareSDK.getPlatform(QQ.NAME);
                weiboqq.SSOSetting(false);
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                weiboqq.setPlatformActionListener(new PlatformActionListener() {

                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                        Log.i("xff", "onError: "+arg2.getMessage());
                    }

                    @Override
                    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        arg0.getDb().exportData();
                        Log.i("xff", "onComplete: "+arg0.getDb().exportData());
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub

                    }
                });
//authorize与showUser单独调用一个即可
//                weiboqq.authorize();//单独授权,OnComplete返回的hashmap是空的
                weiboqq.showUser(null);//授权并获取用户信息
//移除授权
//weibo.removeAccount(true);
                break;

        }
    }
    private void showShare() {
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
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
////         comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite("ShareSDK");
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
//    private void share(SinaWeibo.ShareParams sp){
//
//
//    }
}
