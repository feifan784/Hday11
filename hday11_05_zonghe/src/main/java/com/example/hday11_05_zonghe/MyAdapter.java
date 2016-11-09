package com.example.hday11_05_zonghe;

import cn.sharesdk.framework.authorize.AuthorizeAdapter;

/**
 * Created by xu on 2016/11/7.
 */
public class MyAdapter extends AuthorizeAdapter {

    @Override
    public void onCreate() {
        super.onCreate();

        hideShareSDKLogo();
    }
}
