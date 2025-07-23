package com.android.wm.shell.common;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DismissViewManager extends FrameLayout {
    public static final String[] DISMISS_TYPE_STRINGS = {"Type_None", "Type_DnD", "Type_Pip", "Type_Freeform", "Type_NS"};
    public boolean mAddWindowRequested;
    public final int mDismissType;
    public boolean mNeedToRemoveWindow;
    public String mTitle;
    public final Rect mTmpRect;
    public DismissView mView;
    public final WindowManager mWindowManager;
    public final int mWindowType;

    public DismissViewManager(Context context, int i) {
        this(context, i, 2607);
    }

    public final void cleanUpDismissTarget() {
        Log.i("DismissViewManager", "cleanUpDismissTarget  isAttachedToWindow=" + isAttachedToWindow() + "  addWindowRequested=" + this.mAddWindowRequested);
        clearAnimation();
        this.mNeedToRemoveWindow = false;
        DismissView dismissView = this.mView;
        if (dismissView != null && dismissView.mVisible) {
            dismissView.mVisible = false;
            dismissView.clearAnimation();
            dismissView.setVisibility(4);
        }
        if (isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(this);
            this.mAddWindowRequested = false;
        } else if (this.mAddWindowRequested) {
            this.mNeedToRemoveWindow = true;
        }
    }

    public final void createDismissView() {
        if (this.mView != null) {
            cleanUpDismissTarget();
            removeView(this.mView);
            this.mView = null;
        }
        DismissView dismissView = (DismissView) LayoutInflater.from(getContext()).inflate(R.layout.dismiss_view, (ViewGroup) this, false);
        this.mView = dismissView;
        dismissView.updateMarginBottom();
        this.mView.setDismissType(this.mDismissType);
        addView(this.mView);
    }

    public final void createOrUpdateWrapper() {
        Log.i("DismissViewManager", "createOrUpdateWrapper  isAttachedToWindow=" + isAttachedToWindow());
        if (isAttachedToWindow() || this.mAddWindowRequested) {
            this.mView.updateMarginBottom();
            this.mWindowManager.updateViewLayout(this, generateWrapperLayoutParams());
            return;
        }
        setVisibility(4);
        this.mNeedToRemoveWindow = false;
        try {
            this.mWindowManager.addView(this, generateWrapperLayoutParams());
            this.mAddWindowRequested = true;
        } catch (IllegalStateException e) {
            Log.e("DismissViewManager", "createOrUpdateWrapper: failed to addView, " + this + ", e=" + e);
            this.mWindowManager.updateViewLayout(this, generateWrapperLayoutParams());
        }
    }

    public final WindowManager.LayoutParams generateWrapperLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, this.mWindowType, 16778040, -2);
        layoutParams.setTitle(this.mTitle + DISMISS_TYPE_STRINGS[this.mDismissType]);
        layoutParams.privateFlags = layoutParams.privateFlags | 80;
        layoutParams.samsungFlags = layoutParams.samsungFlags | 131072;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.y = 0;
        layoutParams.x = 0;
        Point point = new Point();
        getContext().getDisplay().getRealSize(point);
        layoutParams.width = point.x;
        layoutParams.height = point.y;
        layoutParams.gravity = 8388659;
        return layoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onAttachedToWindow  mNeedToRemoveWindow="), this.mNeedToRemoveWindow, "DismissViewManager");
        if (this.mNeedToRemoveWindow) {
            cleanUpDismissTarget();
        }
    }

    public final void show() {
        setVisibility(0);
        DismissView dismissView = this.mView;
        if (dismissView.mVisible) {
            return;
        }
        dismissView.mVisible = true;
        Log.i("DismissView", "show");
        dismissView.startAnimation(dismissView.mEnterAnimation);
    }

    public final void updateDismissTargetView(PointF pointF) {
        int i = (int) pointF.x;
        int i2 = (int) pointF.y;
        this.mTmpRect.set(i - 3, i2 - 3, i + 3, i2 + 3);
        this.mView.updateView(this.mTmpRect);
    }

    public DismissViewManager(Context context, int i, int i2) {
        super(context);
        this.mTmpRect = new Rect();
        this.mTitle = "dismiss-button-overlay-";
        this.mDismissType = i;
        this.mWindowType = i2;
        this.mWindowManager = (WindowManager) getContext().getSystemService("window");
    }
}
