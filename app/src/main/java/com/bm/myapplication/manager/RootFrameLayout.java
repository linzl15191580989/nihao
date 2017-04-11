package com.bm.myapplication.manager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by
 */
public class RootFrameLayout extends FrameLayout {

    /**
     *  loading 加载id
     */
    private static final int LAYOUT_LOADING_ID = 1;

    /**
     *  内容id
     */
    private static final int LAYOUT_CONTENT_ID = 2;

    /**
     *  异常id
     */
    private static final int LAYOUT_ERROR_ID = 3;

    /**
     *  网络异常id
     */
    private static final int LAYOUT_NETWORK_ERROR_ID = 4;

    /**
     *  空数据id
     */
    private static final int LAYOUT_EMPTY_DATA_ID = 5;

    /**
     *  存放布局集合
     */
    private final SparseArray<View> layoutSparseArray = new SparseArray<>();

    /**
     *  布局管理器
     */
    private StatusLayoutManager mStatusLayoutManager;


    public RootFrameLayout(Context context) {
        super(context);
    }

    public RootFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

// --Commented out by Inspection START (2017/4/10 10:28):
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public RootFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
// --Commented out by Inspection STOP (2017/4/10 10:28)


    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager) {
        mStatusLayoutManager = statusLayoutManager;

        addAllLayoutToLayout();
    }

    private void addAllLayoutToLayout() {
        if(mStatusLayoutManager.contentLayoutResId != 0) addLayoutResId(mStatusLayoutManager.contentLayoutResId, RootFrameLayout.LAYOUT_CONTENT_ID);
        if(mStatusLayoutManager.loadingLayoutResId != 0) addLayoutResId(mStatusLayoutManager.loadingLayoutResId, RootFrameLayout.LAYOUT_LOADING_ID);

        if(mStatusLayoutManager.emptyDataVs != null) addView(mStatusLayoutManager.emptyDataVs);
        if(mStatusLayoutManager.errorVs != null) addView(mStatusLayoutManager.errorVs);
        if(mStatusLayoutManager.netWorkErrorVs != null) addView(mStatusLayoutManager.netWorkErrorVs);
    }

    private void addLayoutResId(@LayoutRes int layoutResId, int id) {
        View resView = LayoutInflater.from(mStatusLayoutManager.context).inflate(layoutResId, null);
        layoutSparseArray.put(id, resView);
        addView(resView);
    }

    /**
     *  显示loading
     */
    public void showLoading() {
        if(layoutSparseArray.get(LAYOUT_LOADING_ID) != null)
            showHideViewById(LAYOUT_LOADING_ID);
    }

    /**
     *  显示内容
     */
    public void showContent() {
        if(layoutSparseArray.get(LAYOUT_CONTENT_ID) != null)
            showHideViewById(LAYOUT_CONTENT_ID);
    }

// --Commented out by Inspection START (2017/4/10 15:20):
//    /**
//     *  显示空数据
//     */
//    public void showEmptyData() {
//        if(inflateLayout(LAYOUT_EMPTY_DATA_ID))
//            showHideViewById(LAYOUT_EMPTY_DATA_ID);
//    }
// --Commented out by Inspection STOP (2017/4/10 15:20)

// --Commented out by Inspection START (2017/4/10 15:20):
//    /**
//     *  显示网络异常
//     */
//    public void showNetWorkError() {
//        if(inflateLayout(LAYOUT_NETWORK_ERROR_ID))
//            showHideViewById(LAYOUT_NETWORK_ERROR_ID);
//    }
// --Commented out by Inspection STOP (2017/4/10 15:20)

    /**
     *  显示异常
     */
//    public void showError() {
//        if(inflateLayout(LAYOUT_ERROR_ID))
//            showHideViewById(LAYOUT_ERROR_ID);
//    }

    /**
     *  根据ID显示隐藏布局
     * @param id
     */
    @SuppressWarnings("JavaDoc")
    private void showHideViewById(int id) {
        for(int i = 0; i < layoutSparseArray.size(); i++) {
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);
            //显示该view
            if(key == id) {
                valueView.setVisibility(View.VISIBLE);
                if(mStatusLayoutManager.onShowHideViewListener != null) mStatusLayoutManager.onShowHideViewListener.onShowView(valueView, key);
            } else {
                if(valueView.getVisibility() != View.GONE) {
                    valueView.setVisibility(View.GONE);
                    if(mStatusLayoutManager.onShowHideViewListener != null) mStatusLayoutManager.onShowHideViewListener.onHideView(valueView, key);
                }
            }
        }
    }

    private boolean inflateLayout(int id) {
        boolean isShow = true;
        if(layoutSparseArray.get(id) != null) return true;
        switch (id) {
            case LAYOUT_NETWORK_ERROR_ID:
                if(mStatusLayoutManager.netWorkErrorVs != null) {
                    View view = mStatusLayoutManager.netWorkErrorVs.inflate();
                    retryLoad(view, mStatusLayoutManager.netWorkErrorRetryViewId);
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_ERROR_ID:
                if(mStatusLayoutManager.errorVs != null) {
                    View view = mStatusLayoutManager.errorVs.inflate();
                    retryLoad(view, mStatusLayoutManager.errorRetryViewId);
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
            case LAYOUT_EMPTY_DATA_ID:
                if(mStatusLayoutManager.emptyDataVs != null) {
                    View view = mStatusLayoutManager.emptyDataVs.inflate();
                    retryLoad(view, mStatusLayoutManager.emptyDataRetryViewId);
                    layoutSparseArray.put(id, view);
                    isShow = true;
                } else {
                    isShow = false;
                }
                break;
        }
        return isShow;
    }

    /**
     *  重试加载
     */
    private void retryLoad(View view, int id) {
        View retryView = view.findViewById(mStatusLayoutManager.retryViewId != 0 ? mStatusLayoutManager.retryViewId : id);
        if(retryView == null || mStatusLayoutManager.onRetryListener == null) return;
        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayoutManager.onRetryListener.onRetry();
            }
        });
    }
}

