package com.android.systemui.navigationbar.gestural;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.statusbar.VibratorHelper;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CornerGestureHandler {
    public boolean allowGesture;
    public final Lazy assistManagerLazy;
    public final Context context;
    public final float degreeEnd;
    public final float degreeStart;
    public final int displayId;
    public float distance;
    public final PointF downPos;
    public long downTime;
    public InputChannelCompat$InputEventReceiver inputEventReceiver;
    public InputMonitor inputMonitor;
    public boolean isAttached;
    public boolean isPilfered;
    public float lastProgress;
    public NavBarHelper navBarHelper;
    public final NavBarStateManager navBarStateManager;
    public final NavBarStore navBarStore;
    public final float progressTouchSlop;
    public boolean startAssistant;
    public float timeFraction;
    public final float touchSlop;
    public final VibratorHelper vibratorHelper;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CornerGestureHandler(Context context, NavBarStore navBarStore, OverviewProxyService overviewProxyService, Lazy lazy) {
        this.context = context;
        this.navBarStore = navBarStore;
        this.assistManagerLazy = lazy;
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        this.navBarStateManager = ((NavBarStoreImpl) navBarStore).getNavStateManager(displayId);
        this.vibratorHelper = (VibratorHelper) Dependency.sDependency.getDependencyInner(VibratorHelper.class);
        this.degreeStart = 110.0f;
        this.degreeEnd = 180.0f;
        this.downPos = new PointF();
        this.touchSlop = context.getResources().getDimension(R.dimen.gestures_assistant_drag_threshold);
        this.timeFraction = 1.0f;
        this.progressTouchSlop = context.getResources().getDimension(android.R.dimen.resolver_empty_state_container_padding_top) * 0.5f;
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        if (this.allowGesture && !this.startAssistant) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(this.lastProgress, 0.0f);
            ofFloat.setDuration(300L);
            ofFloat.setInterpolator(new DecelerateInterpolator(2.0f));
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ((AssistManager) CornerGestureHandler.this.assistManagerLazy.get()).onInvocationProgress(((Float) ofFloat.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.gestural.CornerGestureHandler$cancelGesture$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ((AssistManager) CornerGestureHandler.this.assistManagerLazy.get()).onInvocationProgress(0.0f);
                }
            });
            ofFloat.start();
        }
        this.timeFraction = 1.0f;
        this.allowGesture = false;
        this.startAssistant = false;
        this.isPilfered = false;
        this.lastProgress = 0.0f;
        this.distance = 0.0f;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        obtain.recycle();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateIsEnabled() {
        /*
            r5 = this;
            boolean r0 = r5.isAttached
            r1 = 0
            if (r0 == 0) goto L1a
            com.android.systemui.navigationbar.store.NavBarStateManager r0 = r5.navBarStateManager
            com.android.systemui.navigationbar.store.NavBarStateManagerImpl r0 = (com.android.systemui.navigationbar.store.NavBarStateManagerImpl) r0
            boolean r0 = r0.isGestureMode()
            if (r0 != 0) goto L1a
            com.android.systemui.navigationbar.NavBarHelper r0 = r5.navBarHelper
            if (r0 != 0) goto L14
            r0 = r1
        L14:
            boolean r0 = r0.mLongPressHomeEnabled
            if (r0 == 0) goto L1a
            r0 = 1
            goto L1b
        L1a:
            r0 = 0
        L1b:
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r2 = r5.inputEventReceiver
            if (r2 == 0) goto L22
            r2.dispose()
        L22:
            r5.inputEventReceiver = r1
            android.view.InputMonitor r2 = r5.inputMonitor
            if (r2 == 0) goto L2b
            r2.dispose()
        L2b:
            r5.inputMonitor = r1
            if (r0 == 0) goto L61
            android.content.Context r0 = r5.context
            java.lang.Class<android.hardware.input.InputManager> r2 = android.hardware.input.InputManager.class
            java.lang.Object r0 = r0.getSystemService(r2)
            android.hardware.input.InputManager r0 = (android.hardware.input.InputManager) r0
            if (r0 == 0) goto L44
            java.lang.String r2 = "corner-swipe"
            int r3 = r5.displayId
            android.view.InputMonitor r0 = r0.monitorGestureInput(r2, r3)
            goto L45
        L44:
            r0 = r1
        L45:
            r5.inputMonitor = r0
            com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver r2 = new com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver
            if (r0 == 0) goto L4f
            android.view.InputChannel r1 = r0.getInputChannel()
        L4f:
            android.os.Looper r0 = android.os.Looper.getMainLooper()
            android.view.Choreographer r3 = android.view.Choreographer.getInstance()
            com.android.systemui.navigationbar.gestural.CornerGestureHandler$setInputChannel$1 r4 = new com.android.systemui.navigationbar.gestural.CornerGestureHandler$setInputChannel$1
            r4.<init>()
            r2.<init>(r1, r0, r3, r4)
            r5.inputEventReceiver = r2
        L61:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.CornerGestureHandler.updateIsEnabled():void");
    }
}
