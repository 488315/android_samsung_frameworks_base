package com.android.systemui.dreams.complication;

import android.graphics.Region;
import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.complication.Complication;
import com.android.systemui.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HideComplicationTouchHandler implements DreamTouchHandler {
    public static final boolean DEBUG = Log.isLoggable("HideComplicationHandler", 3);
    public final DelayableExecutor mExecutor;
    public final int mFadeOutDelay;
    public Runnable mHiddenCallback;
    public final DreamOverlayStateController mOverlayStateController;
    public final int mRestoreTimeout;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final TouchInsetManager mTouchInsetManager;
    public final Complication.VisibilityController mVisibilityController;
    public boolean mHidden = false;
    public final ArrayDeque mCancelCallbacks = new ArrayDeque();
    public final RunnableC12801 mRestoreComplications = new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.1
        @Override // java.lang.Runnable
        public final void run() {
            CrossFadeHelper.fadeIn(((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, r0.mFadeInDuration, 0);
            HideComplicationTouchHandler.this.mHidden = false;
        }
    };
    public final RunnableC12812 mHideComplications = new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler.2
        @Override // java.lang.Runnable
        public final void run() {
            if (HideComplicationTouchHandler.this.mOverlayStateController.containsState(8)) {
                return;
            }
            CrossFadeHelper.fadeOut(((ComplicationLayoutEngine) HideComplicationTouchHandler.this.mVisibilityController).mLayout, r0.mFadeOutDuration, (Runnable) null);
            HideComplicationTouchHandler hideComplicationTouchHandler = HideComplicationTouchHandler.this;
            hideComplicationTouchHandler.mHidden = true;
            Runnable runnable = hideComplicationTouchHandler.mHiddenCallback;
            if (runnable != null) {
                runnable.run();
                HideComplicationTouchHandler.this.mHiddenCallback = null;
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.dreams.complication.HideComplicationTouchHandler$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.dreams.complication.HideComplicationTouchHandler$2] */
    public HideComplicationTouchHandler(Complication.VisibilityController visibilityController, int i, int i2, TouchInsetManager touchInsetManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DelayableExecutor delayableExecutor, DreamOverlayStateController dreamOverlayStateController) {
        this.mVisibilityController = visibilityController;
        this.mRestoreTimeout = i;
        this.mFadeOutDelay = i2;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mTouchInsetManager = touchInsetManager;
        this.mExecutor = delayableExecutor;
        this.mOverlayStateController = dreamOverlayStateController;
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public final void onSessionStart(DreamTouchHandler.TouchSession touchSession) {
        boolean z = DEBUG;
        if (z) {
            Log.d("HideComplicationHandler", "onSessionStart");
        }
        boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
        final DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) touchSession;
        if (touchSessionImpl.mTouchMonitor.mActiveTouchSessions.size() <= 1 && !isBouncerShowing && !this.mOverlayStateController.containsState(8)) {
            touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler$$ExternalSyntheticLambda0
                /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.dreams.complication.HideComplicationTouchHandler$$ExternalSyntheticLambda2] */
                @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                public final void onInputEvent(InputEvent inputEvent) {
                    final HideComplicationTouchHandler hideComplicationTouchHandler = HideComplicationTouchHandler.this;
                    hideComplicationTouchHandler.getClass();
                    if (inputEvent instanceof MotionEvent) {
                        MotionEvent motionEvent = (MotionEvent) inputEvent;
                        int action = motionEvent.getAction();
                        final DreamTouchHandler.TouchSession touchSession2 = touchSessionImpl;
                        DelayableExecutor delayableExecutor = hideComplicationTouchHandler.mExecutor;
                        if (action != 0) {
                            if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                                ((DreamOverlayTouchMonitor.TouchSessionImpl) touchSession2).pop();
                                final ?? r5 = new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler$$ExternalSyntheticLambda2
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        HideComplicationTouchHandler hideComplicationTouchHandler2 = HideComplicationTouchHandler.this;
                                        hideComplicationTouchHandler2.mCancelCallbacks.add(hideComplicationTouchHandler2.mExecutor.executeDelayed(hideComplicationTouchHandler2.mRestoreTimeout, hideComplicationTouchHandler2.mRestoreComplications));
                                    }
                                };
                                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        HideComplicationTouchHandler hideComplicationTouchHandler2 = HideComplicationTouchHandler.this;
                                        Runnable runnable = r5;
                                        if (hideComplicationTouchHandler2.mHidden) {
                                            runnable.run();
                                        } else {
                                            hideComplicationTouchHandler2.mHiddenCallback = runnable;
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        }
                        if (HideComplicationTouchHandler.DEBUG) {
                            Log.d("HideComplicationHandler", "ACTION_DOWN received");
                        }
                        final int round = Math.round(motionEvent.getX());
                        final int round2 = Math.round(motionEvent.getY());
                        final TouchInsetManager touchInsetManager = hideComplicationTouchHandler.mTouchInsetManager;
                        touchInsetManager.getClass();
                        final CallbackToFutureAdapter.SafeFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda0
                            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                            public final Object attachCompleter(final CallbackToFutureAdapter.Completer completer) {
                                final TouchInsetManager touchInsetManager2 = TouchInsetManager.this;
                                touchInsetManager2.getClass();
                                final int i = round;
                                final int i2 = round2;
                                touchInsetManager2.mExecutor.execute(new Runnable() { // from class: com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TouchInsetManager touchInsetManager3 = touchInsetManager2;
                                        CallbackToFutureAdapter.Completer completer2 = completer;
                                        final int i3 = i;
                                        final int i4 = i2;
                                        completer2.set(Boolean.valueOf(touchInsetManager3.mLastAffectedSurfaces.values().stream().anyMatch(new Predicate() { // from class: com.android.systemui.touch.TouchInsetManager$$ExternalSyntheticLambda6
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return ((Region) obj).contains(i3, i4);
                                            }
                                        })));
                                    }
                                });
                                return "DreamOverlayTouchMonitor::checkWithinTouchRegion";
                            }
                        });
                        future.delegate.addListener(new Runnable() { // from class: com.android.systemui.dreams.complication.HideComplicationTouchHandler$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                HideComplicationTouchHandler hideComplicationTouchHandler2 = HideComplicationTouchHandler.this;
                                ListenableFuture listenableFuture = future;
                                DreamTouchHandler.TouchSession touchSession3 = touchSession2;
                                hideComplicationTouchHandler2.getClass();
                                try {
                                    if (((Boolean) listenableFuture.get()).booleanValue()) {
                                        ((DreamOverlayTouchMonitor.TouchSessionImpl) touchSession3).pop();
                                        return;
                                    }
                                    while (true) {
                                        ArrayDeque arrayDeque = hideComplicationTouchHandler2.mCancelCallbacks;
                                        if (arrayDeque.isEmpty()) {
                                            arrayDeque.add(hideComplicationTouchHandler2.mExecutor.executeDelayed(hideComplicationTouchHandler2.mFadeOutDelay, hideComplicationTouchHandler2.mHideComplications));
                                            return;
                                        }
                                        ((Runnable) arrayDeque.pop()).run();
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    EmergencyButton$$ExternalSyntheticOutline0.m58m("could not check TouchInsetManager:", e, "HideComplicationHandler");
                                }
                            }
                        }, delayableExecutor);
                    }
                }
            });
            return;
        }
        if (z) {
            Log.d("HideComplicationHandler", "not fading. Active session count: " + touchSessionImpl.mTouchMonitor.mActiveTouchSessions.size() + ". Bouncer showing: " + isBouncerShowing);
        }
        touchSessionImpl.pop();
    }
}
