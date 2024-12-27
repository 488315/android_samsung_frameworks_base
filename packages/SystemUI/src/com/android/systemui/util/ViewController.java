package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

public abstract class ViewController<T extends View> {
    private boolean mInited;
    private View.OnAttachStateChangeListener mOnAttachStateListener = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.util.ViewController.1
        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
            ViewController.this.onViewAttached();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            ViewController.this.onViewDetached();
        }
    };
    protected T mView;

    public ViewController(T t) {
        this.mView = t;
    }

    public void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        T t = this.mView;
        if (t != null) {
            t.addOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    public void destroy() {
        this.mView.removeOnAttachStateChangeListener(this.mOnAttachStateListener);
    }

    public Context getContext() {
        return this.mView.getContext();
    }

    public Resources getResources() {
        return this.mView.getResources();
    }

    public void init() {
        if (this.mInited) {
            return;
        }
        onInit();
        this.mInited = true;
        if (isAttachedToWindow()) {
            this.mOnAttachStateListener.onViewAttachedToWindow(this.mView);
        }
        addOnAttachStateChangeListener(this.mOnAttachStateListener);
    }

    public void initView() {
        init();
    }

    public boolean isAttachedToWindow() {
        T t = this.mView;
        return t != null && t.isAttachedToWindow();
    }

    public abstract void onViewAttached();

    public abstract void onViewDetached();

    public void start() {
        init();
    }

    public void onInit() {
    }
}
