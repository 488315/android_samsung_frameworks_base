package com.samsung.android.biometrics.app.setting;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.samsung.android.biometrics.app.setting.SysUiWindow;

/* loaded from: classes.dex */
public abstract class SysUiWindow {
    protected static final boolean DEBUG = Utils.DEBUG;
    protected View mBaseView;
    private final Context mContext;

    /* renamed from: mH */
    protected final Handler f22mH;
    private boolean mHasPendingRemove;
    protected boolean mIsAlreadyAdded;
    private final WindowManager mWindowManager;

    /* renamed from: com.samsung.android.biometrics.app.setting.SysUiWindow$1 */
    final class ViewOnAttachStateChangeListenerC02241 implements View.OnAttachStateChangeListener {
        ViewOnAttachStateChangeListenerC02241() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            if (SysUiWindow.this.mHasPendingRemove) {
                SysUiWindow.this.f22mH.post(new Runnable() { // from class: com.samsung.android.biometrics.app.setting.SysUiWindow$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SysUiWindow.ViewOnAttachStateChangeListenerC02241 viewOnAttachStateChangeListenerC02241 = SysUiWindow.ViewOnAttachStateChangeListenerC02241.this;
                        viewOnAttachStateChangeListenerC02241.getClass();
                        if (SysUiWindow.DEBUG) {
                            Log.d(SysUiWindow.this.getLogTag(), "removeView: pending");
                        }
                        SysUiWindow.this.removeView();
                    }
                });
            } else {
                SysUiWindow.this.onAttachedToWindow(view);
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
        this.f22mH = new Handler(context.getMainLooper());
    }

    public void addView() {
        if (this.mBaseView == null) {
            return;
        }
        if (DEBUG) {
            Log.d(getLogTag(), "addView: " + this.mIsAlreadyAdded + ", " + this.mBaseView.getWindowToken());
        }
        try {
            if (this.mIsAlreadyAdded) {
                Log.d(getLogTag(), "addView: Already added!");
                return;
            }
            this.mBaseView.addOnAttachStateChangeListener(new ViewOnAttachStateChangeListenerC02241());
            this.mWindowManager.addView(this.mBaseView, getLayoutParams());
            this.mIsAlreadyAdded = true;
        } catch (Exception e) {
            Log.d(getLogTag(), "addView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void destroyHbmProvider() {
        removeView();
    }

    protected final Context getContext() {
        return this.mContext;
    }

    protected final LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }

    public abstract WindowManager.LayoutParams getLayoutParams();

    public abstract String getLogTag();

    protected final boolean hasPendingRemove() {
        return this.mHasPendingRemove;
    }

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
            Log.d(getLogTag(), "removeView: " + this.mIsAlreadyAdded + ", " + this.mBaseView.getWindowToken());
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

    public void setVisibility(int i) {
        View view = this.mBaseView;
        if (view != null) {
            view.setVisibility(i);
        }
    }

    protected final void updateViewLayout() {
        View view = this.mBaseView;
        if (view == null || view.getWindowToken() == null) {
            return;
        }
        this.mWindowManager.updateViewLayout(this.mBaseView, getLayoutParams());
    }

    protected void onAttachedToWindow(View view) {
    }

    public void onRotationInfoChanged(int i) {
    }
}
