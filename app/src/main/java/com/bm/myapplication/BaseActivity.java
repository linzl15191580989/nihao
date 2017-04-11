package com.bm.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bm.myapplication.manager.OnRetryListener;
import com.bm.myapplication.manager.OnShowHideViewListener;
import com.bm.myapplication.manager.StatusLayoutManager;


/**
 * @ Created by linzl on 2017/4/7.
 * @ function :
 * @ version:1.0
 */
public abstract  class BaseActivity extends AppCompatActivity {


    private StatusLayoutManager statusLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        stateLayout();
    }





    /**
     * 多状态视图（网络错误，无数据，加载框,数据异常显示页面）
     * @ return
     */
    private void stateLayout(){
        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_rl);
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(getContentView())
                .emptyDataView(R.layout.activity_empty_data)
                .errorView(R.layout.activity_error)
                .loadingView(R.layout.activity_loading)
                .netWorkErrorView(R.layout.activity_network_error)
                .retryViewId(R.id.button_try)
                .onShowHideViewListener(new OnShowHideViewListener() {
                    @Override
                    public void onShowView(View view, int id) {
                    }

                    @Override
                    public void onHideView(View view, int id) {
                    }
                }).onRetryListener(new OnRetryListener() {
                    @Override
                    public void onRetry() {
                        statusLayoutManager.showLoading();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        statusLayoutManager.showContent();
                                    }
                                });
                            }
                        }).start();

                    }
                }).build();

        assert mainLinearLayout != null;
        mainLinearLayout.addView(statusLayoutManager.getRootLayout(), 1);
        statusLayoutManager.showLoading();
    }
    protected abstract int getContentView();
    //******************多状态视图结束*********************
}
