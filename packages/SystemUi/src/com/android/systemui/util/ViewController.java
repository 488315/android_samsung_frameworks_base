package com.android.systemui.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ViewController {
    public boolean mInited;
    public final ViewOnAttachStateChangeListenerC35841 mOnAttachStateListener = new ViewOnAttachStateChangeListenerC35841();
    public View mView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.util.ViewController$1 */
    public final class ViewOnAttachStateChangeListenerC35841 implements View.OnAttachStateChangeListener {
        public ViewOnAttachStateChangeListenerC35841() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            ViewController.this.onViewAttached();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            ViewController.this.onViewDetached();
        }
    }

    public ViewController(View view) {
        this.mView = view;
    }

    public Context getContext() {
        return this.mView.getContext();
    }

    public final Resources getResources() {
        return this.mView.getResources();
    }

    public final void init() {
        if (this.mInited) {
            return;
        }
        onInit();
        this.mInited = true;
        View view = this.mView;
        boolean z = view != null && view.isAttachedToWindow();
        ViewOnAttachStateChangeListenerC35841 viewOnAttachStateChangeListenerC35841 = this.mOnAttachStateListener;
        if (z) {
            viewOnAttachStateChangeListenerC35841.onViewAttachedToWindow(this.mView);
        }
        View view2 = this.mView;
        if (view2 != null) {
            view2.addOnAttachStateChangeListener(viewOnAttachStateChangeListenerC35841);
        }
    }

    public void initView() {
        init();
    }

    public abstract void onViewAttached();

    public abstract void onViewDetached();

    public void start() {
        init();
    }

    public void onInit() {
    }
}
