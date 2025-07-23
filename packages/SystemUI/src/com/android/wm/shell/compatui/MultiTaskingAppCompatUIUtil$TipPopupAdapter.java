package com.android.wm.shell.compatui;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.samsung.android.widget.SemTipPopup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public enum MultiTaskingAppCompatUIUtil$TipPopupAdapter {
    INSTANCE;

    public static final String TAG = MultiTaskingAppCompatUIUtil$TipPopupAdapter.class.getSimpleName();
    private View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUIUtil$TipPopupAdapter.1
        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewAttachedToWindow(View view) {
            String str = MultiTaskingAppCompatUIUtil$TipPopupAdapter.TAG;
            Log.d(str, "onViewAttachedToWindow: v=" + view);
            if (view.equals(MultiTaskingAppCompatUIUtil$TipPopupAdapter.this.mViewHost)) {
                MultiTaskingAppCompatUIUtil$TipPopupAdapter.m3226$$Nest$mshowTipPopup(MultiTaskingAppCompatUIUtil$TipPopupAdapter.this);
                return;
            }
            Log.e(str, "HostView is not matched with the view attached, hostView=" + MultiTaskingAppCompatUIUtil$TipPopupAdapter.this.mViewHost);
            MultiTaskingAppCompatUIUtil$TipPopupAdapter.this.dismissTipPopup();
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public final void onViewDetachedFromWindow(View view) {
            Log.d(MultiTaskingAppCompatUIUtil$TipPopupAdapter.TAG, "onViewDetachedFromWindow: v=" + view);
        }
    };
    private CharSequence mMessage;
    private MultiTaskingAppCompatUILayout mMultiTaskingAppCompatUILayout;
    private SemTipPopup mSemTipPopup;
    private View mViewHost;
    private WindowManager mWindowManager;

    /* renamed from: -$$Nest$mbuild, reason: not valid java name */
    public static MultiTaskingAppCompatUIUtil$TipPopupAdapter m3225$$Nest$mbuild(MultiTaskingAppCompatUIUtil$TipPopupAdapter multiTaskingAppCompatUIUtil$TipPopupAdapter, MultiTaskingAppCompatUIUtil$TipPopupBuilder multiTaskingAppCompatUIUtil$TipPopupBuilder) {
        multiTaskingAppCompatUIUtil$TipPopupAdapter.getClass();
        String str = TAG;
        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = multiTaskingAppCompatUIUtil$TipPopupBuilder.mMultiTaskingAppCompatUILayout;
        MultiTaskingAppCompatUIUtil$TipPopupAdapter multiTaskingAppCompatUIUtil$TipPopupAdapter2 = INSTANCE;
        if (multiTaskingAppCompatUILayout == null) {
            Log.e(str, "build: no layout, something went wrong.");
            return multiTaskingAppCompatUIUtil$TipPopupAdapter2;
        }
        if (multiTaskingAppCompatUIUtil$TipPopupAdapter.mWindowManager == null) {
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mWindowManager = (WindowManager) multiTaskingAppCompatUILayout.getContext().getSystemService("window");
        }
        if (!multiTaskingAppCompatUILayout.equals(multiTaskingAppCompatUIUtil$TipPopupAdapter.mMultiTaskingAppCompatUILayout)) {
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mMultiTaskingAppCompatUILayout = multiTaskingAppCompatUILayout;
        }
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mMessage = multiTaskingAppCompatUIUtil$TipPopupBuilder.mMessage;
        View view = multiTaskingAppCompatUIUtil$TipPopupAdapter.mViewHost;
        if (view != null && view.isAttachedToWindow()) {
            Log.d(str, "build: removed remained host, " + multiTaskingAppCompatUIUtil$TipPopupAdapter.mViewHost);
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mWindowManager.removeViewImmediate(multiTaskingAppCompatUIUtil$TipPopupAdapter.mViewHost);
        }
        View inflate = LayoutInflater.from(multiTaskingAppCompatUIUtil$TipPopupAdapter.mMultiTaskingAppCompatUILayout.getContext()).inflate(R.layout.mt_app_compat_ui_tip, (ViewGroup) null);
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mViewHost = inflate;
        inflate.addOnAttachStateChangeListener(multiTaskingAppCompatUIUtil$TipPopupAdapter.mAttachStateChangeListener);
        return multiTaskingAppCompatUIUtil$TipPopupAdapter2;
    }

    /* renamed from: -$$Nest$mshowTipPopup, reason: not valid java name */
    public static void m3226$$Nest$mshowTipPopup(final MultiTaskingAppCompatUIUtil$TipPopupAdapter multiTaskingAppCompatUIUtil$TipPopupAdapter) {
        MultiTaskingAppCompatUILayout multiTaskingAppCompatUILayout = multiTaskingAppCompatUIUtil$TipPopupAdapter.mMultiTaskingAppCompatUILayout;
        FrameLayout frameLayout = multiTaskingAppCompatUILayout != null ? multiTaskingAppCompatUILayout.mSwitchableButtonContainer : null;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) (frameLayout != null ? frameLayout.getLayoutParams() : null);
        if (frameLayout == null || layoutParams == null) {
            Log.e(TAG, "No target button with layoutParams to show guide tip.");
            multiTaskingAppCompatUIUtil$TipPopupAdapter.dismissTipPopup();
            return;
        }
        SemTipPopup semTipPopup = new SemTipPopup(multiTaskingAppCompatUIUtil$TipPopupAdapter.mViewHost);
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup = semTipPopup;
        semTipPopup.setMessage(multiTaskingAppCompatUIUtil$TipPopupAdapter.mMessage);
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setExpanded(true);
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setOutsideTouchEnabled(true);
        multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setOnStateChangeListener(new SemTipPopup.OnStateChangeListener() { // from class: com.android.wm.shell.compatui.MultiTaskingAppCompatUIUtil$TipPopupAdapter$$ExternalSyntheticLambda0
            public final void onStateChanged(int i) {
                MultiTaskingAppCompatUIUtil$TipPopupAdapter multiTaskingAppCompatUIUtil$TipPopupAdapter2 = MultiTaskingAppCompatUIUtil$TipPopupAdapter.this;
                MultiTaskingAppCompatUIUtil$TipPopupAdapter multiTaskingAppCompatUIUtil$TipPopupAdapter3 = MultiTaskingAppCompatUIUtil$TipPopupAdapter.INSTANCE;
                if (i == 0) {
                    multiTaskingAppCompatUIUtil$TipPopupAdapter2.dismissTipPopup();
                } else {
                    multiTaskingAppCompatUIUtil$TipPopupAdapter2.getClass();
                }
            }
        });
        int[] locationOnScreen = frameLayout.getLocationOnScreen();
        int i = layoutParams.gravity;
        if (i == 53) {
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0], locationOnScreen[1] + (layoutParams.height >> 1));
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(2);
        } else if (i != 83) {
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0], locationOnScreen[1] - (layoutParams.height >> 1));
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(0);
        } else {
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.setTargetPosition(locationOnScreen[0] + layoutParams.width, locationOnScreen[1] - (layoutParams.height >> 1));
            multiTaskingAppCompatUIUtil$TipPopupAdapter.mSemTipPopup.show(1);
        }
    }

    MultiTaskingAppCompatUIUtil$TipPopupAdapter() {
    }

    public final void dismissTipPopup() {
        View view = this.mViewHost;
        String str = TAG;
        if (view != null) {
            Log.d(str, "dismissTipPopup: mViewHost=" + this.mViewHost + ", callers=" + Debug.getCallers(6));
        }
        SemTipPopup semTipPopup = this.mSemTipPopup;
        if (semTipPopup != null && semTipPopup.isShowing()) {
            Log.d(str, "dismissTipPopup: dismiss TipPopup");
            this.mSemTipPopup.setOnStateChangeListener((SemTipPopup.OnStateChangeListener) null);
            this.mSemTipPopup.dismiss(false);
            this.mSemTipPopup = null;
        }
        View view2 = this.mViewHost;
        if (view2 != null) {
            view2.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            this.mWindowManager.removeViewImmediate(this.mViewHost);
            this.mViewHost = null;
        }
    }

    public final void release() {
        dismissTipPopup();
        this.mWindowManager = null;
        this.mMultiTaskingAppCompatUILayout = null;
        this.mMessage = null;
        this.mViewHost = null;
        this.mSemTipPopup = null;
    }

    public final void show() {
        View view = this.mViewHost;
        String str = TAG;
        if (view == null) {
            Log.e(str, "show: host is null, something went wrong.");
            return;
        }
        Rect rect = new Rect();
        this.mMultiTaskingAppCompatUILayout.getWindowDisplayFrame(rect);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(rect.width(), rect.height(), 2038, 40, -3);
        layoutParams.token = new Binder();
        layoutParams.setTitle(MultiTaskingAppCompatUIUtil$TipPopupAdapter.class.getSimpleName());
        layoutParams.privateFlags |= 536870976;
        layoutParams.semAddPrivateFlags(16);
        layoutParams.setFitInsetsTypes(0);
        Log.d(str, "show: mViewHost=" + this.mViewHost + ", layoutParams=" + layoutParams);
        this.mWindowManager.addView(this.mViewHost, layoutParams);
    }
}
