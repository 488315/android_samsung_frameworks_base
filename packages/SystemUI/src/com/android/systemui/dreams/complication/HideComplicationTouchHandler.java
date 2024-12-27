package com.android.systemui.dreams.complication;

import android.util.Log;
import com.android.systemui.Flags;
import com.android.systemui.ambient.touch.TouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayDeque;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class HideComplicationTouchHandler implements TouchHandler {
    public static final boolean DEBUG = Log.isLoggable("HideComplicationHandler", 3);
    public final DelayableExecutor mExecutor;
    public Runnable mHiddenCallback;
    public final AnonymousClass2 mHideComplications;
    public final DreamOverlayStateController mOverlayStateController;
    public final AnonymousClass1 mRestoreComplications;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final Complication.VisibilityController mVisibilityController;

    public HideComplicationTouchHandler(Complication.VisibilityController visibilityController, int i, int i2, TouchInsetManager touchInsetManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DelayableExecutor delayableExecutor, DreamOverlayStateController dreamOverlayStateController) {
        new ArrayDeque();
        new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("HideComplicationHandler", "Restoring complications...");
                CrossFadeHelper.fadeIn(((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, r0.mFadeInDuration, 0);
                HideComplicationTouchHandler.this.getClass();
            }
        };
        new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.2
            @Override // java.lang.Runnable
            public final void run() {
                if (HideComplicationTouchHandler.this.mOverlayStateController.containsState(8)) {
                    return;
                }
                Log.d("HideComplicationHandler", "Hiding complications...");
                CrossFadeHelper.fadeOut(r0.mFadeOutDuration, ((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, (Runnable) null);
                HideComplicationTouchHandler hideComplicationTouchHandler = HideComplicationTouchHandler.this;
                hideComplicationTouchHandler.getClass();
                Runnable runnable = hideComplicationTouchHandler.mHiddenCallback;
                if (runnable != null) {
                    runnable.run();
                    HideComplicationTouchHandler.this.mHiddenCallback = null;
                }
            }
        };
        this.mVisibilityController = visibilityController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mExecutor = delayableExecutor;
        this.mOverlayStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(TouchMonitor.TouchSessionImpl touchSessionImpl) {
        boolean z = DEBUG;
        if (z) {
            Log.d("HideComplicationHandler", "onSessionStart");
        }
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        Flags.FEATURE_FLAGS.getClass();
        if (z) {
            Log.d("HideComplicationHandler", "not fading. Active session count: " + touchSessionImpl.mTouchMonitor.mActiveTouchSessions.size() + ". Bouncer showing: " + isBouncerShowing);
        }
        touchSessionImpl.pop();
    }
}
