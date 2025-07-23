package com.android.systemui.dreams.complication;

import android.util.Log;
import androidx.emoji2.text.FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class HideComplicationTouchHandler implements TouchHandler {
    public static final boolean DEBUG = Log.isLoggable("HideComplicationHandler", 3);
    public final DelayableExecutor mExecutor;
    public final FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 mHiddenCallback;
    public final AnonymousClass2 mHideComplications;
    public final DreamOverlayStateController mOverlayStateController;
    public final AnonymousClass1 mRestoreComplications;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final Complication.VisibilityController mVisibilityController;

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.dreams.complication.HideComplicationTouchHandler$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.dreams.complication.HideComplicationTouchHandler$2] */
    public HideComplicationTouchHandler(Complication.VisibilityController visibilityController, int i, int i2, TouchInsetManager touchInsetManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DelayableExecutor delayableExecutor, DreamOverlayStateController dreamOverlayStateController) {
        new ArrayDeque();
        this.mRestoreComplications = new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("HideComplicationHandler", "Restoring complications...");
                CrossFadeHelper.fadeIn(((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, r0.mFadeInDuration, 0);
                HideComplicationTouchHandler.this.getClass();
            }
        };
        this.mHideComplications = new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.2
            @Override // java.lang.Runnable
            public final void run() {
                if (HideComplicationTouchHandler.this.mOverlayStateController.containsState(8)) {
                    return;
                }
                Log.d("HideComplicationHandler", "Hiding complications...");
                CrossFadeHelper.fadeOut(r0.mFadeOutDuration, ((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, (Runnable) null);
                HideComplicationTouchHandler hideComplicationTouchHandler = HideComplicationTouchHandler.this;
                hideComplicationTouchHandler.getClass();
                FontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 fontRequestEmojiCompatConfig$FontRequestMetadataLoader$$ExternalSyntheticLambda1 = hideComplicationTouchHandler.mHiddenCallback;
            }
        };
        this.mVisibilityController = visibilityController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mExecutor = delayableExecutor;
        this.mOverlayStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(TouchHandler.TouchSession touchSession) {
        boolean z = DEBUG;
        if (z) {
            Log.d("HideComplicationHandler", "onSessionStart");
        }
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        if (z) {
            Log.d("HideComplicationHandler", "not fading. Active session count: " + ((TouchMonitor.TouchSessionImpl) touchSession).mTouchMonitor.mActiveTouchSessions.size() + ". Bouncer showing: " + isBouncerShowing);
        }
        ((TouchMonitor.TouchSessionImpl) touchSession).pop();
    }
}
