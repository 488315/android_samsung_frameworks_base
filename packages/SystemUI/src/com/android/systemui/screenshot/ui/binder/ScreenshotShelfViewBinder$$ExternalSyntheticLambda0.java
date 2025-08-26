package com.android.systemui.screenshot.ui.binder;

import android.view.MotionEvent;
import com.android.systemui.screenshot.FloatingWindowUtil;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ui.SwipeGestureListener;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class ScreenshotShelfViewBinder$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ScreenshotShelfViewBinder$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                ScreenshotEvent screenshotEvent = ScreenshotEvent.SCREENSHOT_SWIPE_DISMISSED;
                ((ScreenshotShelfViewProxy$$ExternalSyntheticLambda0) this.f$0).invoke(screenshotEvent, (Float) obj);
                return Unit.INSTANCE;
            default:
                MotionEvent motionEvent = (MotionEvent) obj;
                SwipeGestureListener swipeGestureListener = (SwipeGestureListener) this.f$0;
                motionEvent.offsetLocation(swipeGestureListener.view.getTranslationX(), 0.0f);
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked != 0) {
                    z = true;
                    if (actionMasked == 1) {
                        swipeGestureListener.velocityTracker.computeCurrentVelocity(1);
                        float xVelocity = swipeGestureListener.velocityTracker.getXVelocity();
                        float fAbs = Math.abs(xVelocity);
                        float fDpToPx = FloatingWindowUtil.dpToPx(swipeGestureListener.displayMetrics, 0.8f);
                        Function1 function1 = swipeGestureListener.onDismiss;
                        if (fAbs > fDpToPx || Math.abs(swipeGestureListener.view.getTranslationX()) > FloatingWindowUtil.dpToPx(swipeGestureListener.displayMetrics, 80.0f)) {
                            function1.mo781invoke(Float.valueOf(xVelocity));
                            return Boolean.valueOf(z);
                        }
                        swipeGestureListener.onCancel.invoke();
                        swipeGestureListener.velocityTracker.clear();
                    } else if (actionMasked == 2) {
                        swipeGestureListener.velocityTracker.addMovement(motionEvent);
                        swipeGestureListener.view.setTranslationX(motionEvent.getRawX() - swipeGestureListener.startX);
                    }
                } else {
                    swipeGestureListener.velocityTracker.addMovement(motionEvent);
                    swipeGestureListener.startX = motionEvent.getRawX();
                }
                z = false;
                return Boolean.valueOf(z);
        }
    }
}
