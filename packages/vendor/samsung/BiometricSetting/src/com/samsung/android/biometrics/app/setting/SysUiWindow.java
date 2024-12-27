package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public abstract class SysUiWindow {
    public static final boolean DEBUG = Utils.DEBUG;
    public View mBaseView;
    public final Context mContext;
    public final Handler mH;
    public boolean mHasPendingRemove;
    public boolean mIsAlreadyAdded;
    public final WindowManager mWindowManager;

    /* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiWindow$1, reason: invalid class name */
    public final class AnonymousClass1 implements View.OnAttachStateChangeListener {
        public AnonymousClass1() {}

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            SysUiWindow sysUiWindow = SysUiWindow.this;
            if (sysUiWindow.mHasPendingRemove) {
                sysUiWindow.mH.post(
                        new Runnable() { // from class:
                                         // com.samsung.android.biometrics.app.setting.SysUiWindow$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SysUiWindow.AnonymousClass1 anonymousClass1 =
                                        SysUiWindow.AnonymousClass1.this;
                                anonymousClass1.getClass();
                                if (SysUiWindow.DEBUG) {
                                    Log.d(SysUiWindow.this.getLogTag(), "removeView: pending");
                                }
                                SysUiWindow.this.removeView();
                            }
                        });
            } else {
                sysUiWindow.onAttachedToWindow(view);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            SysUiWindow.this.getClass();
        }
    }

    public SysUiWindow(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mH = createHandler(context.getMainLooper());
    }

    public void addView() {
        if (this.mBaseView == null) {
            return;
        }
        if (DEBUG) {
            Log.d(
                    getLogTag(),
                    "addView: " + this.mIsAlreadyAdded + ", " + this.mBaseView.getWindowToken());
        }
        try {
            if (this.mIsAlreadyAdded) {
                Log.d(getLogTag(), "addView: Already added!");
                return;
            }
            this.mBaseView.addOnAttachStateChangeListener(new AnonymousClass1());
            this.mWindowManager.addView(this.mBaseView, getLayoutParams());
            this.mIsAlreadyAdded = true;
        } catch (Exception e) {
            Log.d(getLogTag(), "addView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Handler createHandler(Looper looper) {
        return new Handler(looper);
    }

    public void destroyHbmProvider() {
        removeView();
    }

    public abstract WindowManager.LayoutParams getLayoutParams();

    public abstract String getLogTag();

    public boolean isEnabledHbm() {
        return isVisible();
    }

    public final boolean isVisible() {
        View view = this.mBaseView;
        return view != null && view.getVisibility() == 0;
    }

    public void onConfigurationChanged(Configuration configuration) {
        updateViewLayout();
    }

    public void removeView() {
        if (this.mBaseView == null) {
            return;
        }
        if (DEBUG) {
            Log.d(
                    getLogTag(),
                    "removeView: " + this.mIsAlreadyAdded + ", " + this.mBaseView.getWindowToken());
        }
        try {
            if (this.mIsAlreadyAdded) {
                if (this.mBaseView.getWindowToken() == null) {
                    this.mHasPendingRemove = true;
                    return;
                }
                this.mIsAlreadyAdded = false;
                this.mWindowManager.removeViewImmediate(this.mBaseView);
                this.mBaseView = null;
                this.mHasPendingRemove = false;
            }
        } catch (Exception e) {
            Log.w(getLogTag(), "removeView: " + e);
            e.printStackTrace();
        }
    }

    public final void updateViewLayout() {
        View view = this.mBaseView;
        if (view == null || view.getWindowToken() == null) {
            return;
        }
        this.mWindowManager.updateViewLayout(this.mBaseView, getLayoutParams());
    }

    public void onAttachedToWindow(View view) {}

    public void onRotationInfoChanged(int i) {}
}
