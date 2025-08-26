package com.android.systemui.dextouchpad.activity;

import android.view.View;
import android.view.ViewTreeObserver;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;

/* loaded from: classes2.dex */
public abstract class ViewPositionTracker implements DefaultLifecycleObserver {
    public FragmentActivity mActivity;
    public View mBaseView;
    public ViewPositionTracker$$ExternalSyntheticLambda1 mBaseViewLayoutListener;
    public boolean mSetup = false;
    public boolean mLayoutSetup = false;
    public final ViewPositionTracker$$ExternalSyntheticLambda0 mSetupLayoutRunnable = new Runnable() { // from class: com.android.systemui.dextouchpad.activity.ViewPositionTracker$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ViewPositionTracker viewPositionTracker = this.f$0;
            if (viewPositionTracker.mActivity.lifecycleRegistry.state.isAtLeast(Lifecycle.State.CREATED)) {
                viewPositionTracker.mBaseView.getViewTreeObserver().addOnGlobalLayoutListener(viewPositionTracker.mBaseViewLayoutListener);
                viewPositionTracker.mLayoutSetup = true;
                viewPositionTracker.onLayoutSetup();
            }
        }
    };

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onDestroy$1() {
        tearDown();
    }

    public abstract void onLayoutSetup();

    public abstract void onStartSetup();

    public abstract void onStartTearDown();

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onStop$1() {
        onStopWindow();
    }

    public abstract void onStopWindow();

    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.dextouchpad.activity.ViewPositionTracker$$ExternalSyntheticLambda1] */
    public final void setup(FragmentActivity fragmentActivity, View view) {
        if (this.mSetup) {
            return;
        }
        this.mActivity = fragmentActivity;
        this.mBaseView = view;
        onStartSetup();
        if (this.mBaseViewLayoutListener == null) {
            final FloatingWindow floatingWindow = (FloatingWindow) this;
            this.mBaseViewLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.dextouchpad.activity.ViewPositionTracker$$ExternalSyntheticLambda1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    FloatingWindow floatingWindow2 = floatingWindow;
                    if (floatingWindow2.mActivity.lifecycleRegistry.state.isAtLeast(Lifecycle.State.CREATED) && floatingWindow2.mWindowView.isAttachedToWindow()) {
                        floatingWindow2.mActivity.getWindowManager().updateViewLayout(floatingWindow2.mWindowView, floatingWindow2.createLayoutParams(floatingWindow2.mBaseView));
                    }
                }
            };
        }
        this.mActivity.lifecycleRegistry.addObserver(this);
        this.mBaseView.post(this.mSetupLayoutRunnable);
        this.mSetup = true;
    }

    public final void tearDown() {
        if (this.mSetup) {
            onStartTearDown();
            this.mBaseView.removeCallbacks(this.mSetupLayoutRunnable);
            if (this.mLayoutSetup) {
                this.mBaseView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mBaseViewLayoutListener);
                this.mLayoutSetup = false;
            }
            this.mActivity = null;
            this.mBaseView = null;
            this.mSetup = false;
        }
    }
}
