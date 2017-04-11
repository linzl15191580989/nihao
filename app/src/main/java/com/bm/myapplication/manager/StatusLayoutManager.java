package com.bm.myapplication.manager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

/**
 * @ Created by
 * @ function :
 * @ version:1.0
 */
public class StatusLayoutManager {

     final Context context;
     final ViewStub netWorkErrorVs;
     final int netWorkErrorRetryViewId;
     final ViewStub emptyDataVs;
     final int emptyDataRetryViewId;
     final ViewStub errorVs;
     final int errorRetryViewId;
     final int loadingLayoutResId;
     final int contentLayoutResId;
     final int retryViewId;

     final private RootFrameLayout rootFrameLayout;
     final OnShowHideViewListener onShowHideViewListener;
     final OnRetryListener onRetryListener;

     private StatusLayoutManager(Builder builder) {
        this.context = builder.context;
        this.loadingLayoutResId = builder.loadingLayoutResId;
        this.netWorkErrorVs = builder.netWorkErrorVs;
        this.netWorkErrorRetryViewId = builder.netWorkErrorRetryViewId;
        this.emptyDataVs = builder.emptyDataVs;
        this.emptyDataRetryViewId = builder.emptyDataRetryViewId;
        this.errorVs = builder.errorVs;
        this.errorRetryViewId = builder.errorRetryViewId;
        this.contentLayoutResId = builder.contentLayoutResId;
        this.onShowHideViewListener = builder.onShowHideViewListener;
        this.retryViewId = builder.retryViewId;
        this.onRetryListener = builder.onRetryListener;

        rootFrameLayout = new RootFrameLayout(this.context);
        rootFrameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        rootFrameLayout.setStatusLayoutManager(this);
    }


    /**
     *  显示loading
     */
    public void showLoading() {
        rootFrameLayout.showLoading();
    }

    /**
     *  显示内容
     */
    public void showContent() {
        rootFrameLayout.showContent();
    }

// --Commented out by Inspection START (2017/4/10 10:29):
//    /**
//     *  显示空数据
//     */
//    public void showEmptyData() {
//        rootFrameLayout.showEmptyData();
//    }
// --Commented out by Inspection STOP (2017/4/10 10:29)

// --Commented out by Inspection START (2017/4/10 10:30):
//    /**
//     *  显示网络异常
//     */
//    public void showNetWorkError() {
//        rootFrameLayout.showNetWorkError();
//    }
// --Commented out by Inspection STOP (2017/4/10 10:30)

// --Commented out by Inspection START (2017/4/10 10:30):
//    /**
//     *  显示异常
//     */
//    public void showError() {
//        rootFrameLayout.showError();
//    }
// --Commented out by Inspection STOP (2017/4/10 10:30)

    /**
     *  得到root 布局
     */
    public View getRootLayout() {
        return rootFrameLayout;
    }


    public static final class Builder {

        private final Context context;
        private int loadingLayoutResId;
        private int contentLayoutResId;
        private ViewStub netWorkErrorVs;
        private int netWorkErrorRetryViewId;
        private ViewStub emptyDataVs;
        private int emptyDataRetryViewId;
        private ViewStub errorVs;
        private int errorRetryViewId;
        private int retryViewId;
        private OnShowHideViewListener onShowHideViewListener;
        private OnRetryListener onRetryListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder loadingView(@LayoutRes int loadingLayoutResId) {
            this.loadingLayoutResId = loadingLayoutResId;
            return this;
        }

        public Builder netWorkErrorView(@LayoutRes int newWorkErrorId) {
            netWorkErrorVs = new ViewStub(context);
            netWorkErrorVs.setLayoutResource(newWorkErrorId);
            return this;
        }

        public Builder emptyDataView(@LayoutRes int noDataViewId) {
            emptyDataVs = new ViewStub(context);
            emptyDataVs.setLayoutResource(noDataViewId);
            return this;
        }

        public Builder errorView(@LayoutRes int errorViewId) {
            errorVs = new ViewStub(context);
            errorVs.setLayoutResource(errorViewId);
            return this;
        }

        public Builder contentView(@LayoutRes int contentLayoutResId) {
            this.contentLayoutResId = contentLayoutResId;
            return this;
        }

// --Commented out by Inspection START (2017/4/10 10:30):
//        public Builder netWorkErrorRetryViewId(int netWorkErrorRetryViewId) {
//            this.netWorkErrorRetryViewId = netWorkErrorRetryViewId;
//            return this;
//        }
// --Commented out by Inspection STOP (2017/4/10 10:30)

// --Commented out by Inspection START (2017/4/10 10:30):
//        public Builder emptyDataRetryViewId(int emptyDataRetryViewId) {
//            this.emptyDataRetryViewId = emptyDataRetryViewId;
//            return this;
//        }
// --Commented out by Inspection STOP (2017/4/10 10:30)

// --Commented out by Inspection START (2017/4/10 10:30):
//        public Builder errorRetryViewId(int errorRetryViewId) {
//            this.errorRetryViewId = errorRetryViewId;
//            return this;
//        }
// --Commented out by Inspection STOP (2017/4/10 10:30)

        public Builder retryViewId(int retryViewId) {
            this.retryViewId = retryViewId;
            return this;
        }

        public Builder onShowHideViewListener(OnShowHideViewListener onShowHideViewListener) {
            this.onShowHideViewListener = onShowHideViewListener;
            return this;
        }

        public Builder onRetryListener(OnRetryListener onRetryListener) {
            this.onRetryListener = onRetryListener;
            return this;
        }

        public StatusLayoutManager build() {
            return new StatusLayoutManager(this);
        }
    }

    public static Builder newBuilder(Context context) {
       return new Builder(context);
    }

}
