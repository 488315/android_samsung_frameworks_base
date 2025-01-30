package com.android.systemui.p016qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.p016qs.bar.BarController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class BarItemImpl {
    public View mBarRootView;
    public BarController.C20874 mCallback;
    public Context mContext;
    public final String TAG = getClass().getSimpleName();
    public boolean mListening = true;
    public boolean mShowing = true;
    public boolean mIsOnCollapsedState = false;
    public boolean mIsUnderneathQqs = false;

    public BarItemImpl(Context context) {
        this.mContext = context;
    }

    public void destroy() {
        this.mCallback = null;
    }

    public int getBarHeight() {
        if (this.mShowing) {
            return this.mBarRootView.getMeasuredHeight();
        }
        return 0;
    }

    public abstract int getBarLayout();

    public void inflateViews(ViewGroup viewGroup) {
        this.mBarRootView = (ViewGroup) LayoutInflater.from(this.mContext).inflate(getBarLayout(), viewGroup, false);
        onFinishInflate();
    }

    public boolean isAvailable() {
        return true;
    }

    public void setCallback(BarController.C20874 c20874) {
        this.mCallback = c20874;
    }

    public void setListening(boolean z) {
        this.mListening = z;
    }

    public void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
    }

    public void showBar(boolean z) {
        if (this.mBarRootView == null) {
            return;
        }
        Log.i(this.TAG, AbstractC0866xb1ce8deb.m86m("showBar : ", z));
        this.mShowing = z;
        this.mBarRootView.setVisibility(z ? 0 : 8);
        BarController.C20874 c20874 = this.mCallback;
        if (c20874 != null) {
            BarController barController = BarController.this;
            BarController.C20863 c20863 = barController.mBarListener;
            if (c20863 != null) {
                c20863.val$containerRunner.run();
                c20863.val$animatorRunner.run();
            }
            barController.updateBarUnderneathQqs();
        }
    }

    public void onConfigChanged(Configuration configuration) {
    }

    public void setExpanded(boolean z) {
    }

    public void setPosition(float f) {
    }

    public void setQsFullyExpanded(boolean z) {
    }

    public void onFinishInflate() {
    }

    public void onKnoxPolicyChanged() {
    }

    public void onUiModeChanged() {
    }

    public void updateHeightMargins() {
    }
}
