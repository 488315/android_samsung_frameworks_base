package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.util.MathUtils;
import android.view.MotionEvent;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RecentAppsGestureRecognizer implements GestureRecognizer {
    public final DistanceTracker distanceTracker;
    public final int gestureDistanceThresholdPx;
    public Function1 gestureStateChangedCallback;
    public final float velocityThresholdPxPerMs;
    public final VelocityTracker velocityTracker;

    public RecentAppsGestureRecognizer(int i, float f, VelocityTracker velocityTracker) {
        this.gestureDistanceThresholdPx = i;
        this.velocityThresholdPxPerMs = f;
        this.velocityTracker = velocityTracker;
        this.distanceTracker = new DistanceTracker(0.0f, 0.0f, 3, null);
        this.gestureStateChangedCallback = new RecentAppsGestureRecognizer$$ExternalSyntheticLambda0(0);
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        if (GestureRecognizerKt.isMultifingerTouchpadSwipe(motionEvent)) {
            if (!GestureRecognizerKt.isNFingerTouchpadSwipe(motionEvent, 3)) {
                if (motionEvent.getActionMasked() == 1) {
                    this.gestureStateChangedCallback.mo779invoke(GestureState.Error.INSTANCE);
                    return;
                }
                return;
            }
            DistanceGestureState processEvent = this.distanceTracker.processEvent(motionEvent);
            this.velocityTracker.accept(motionEvent);
            Function1 function1 = this.gestureStateChangedCallback;
            if (processEvent instanceof Finished) {
                if ((-((Finished) processEvent).deltaY) < this.gestureDistanceThresholdPx || Math.abs(((VerticalVelocityTracker) this.velocityTracker).velocityTracker.calculateVelocity() / 1000) > this.velocityThresholdPxPerMs) {
                    function1.mo779invoke(GestureState.Error.INSTANCE);
                    return;
                } else {
                    function1.mo779invoke(GestureState.Finished.INSTANCE);
                    return;
                }
            }
            if (processEvent instanceof Moving) {
                function1.mo779invoke(new GestureState.InProgress(MathUtils.saturate((-((Moving) processEvent).deltaY) / this.gestureDistanceThresholdPx), null, 2, null));
            } else if (processEvent instanceof Started) {
                function1.mo779invoke(new GestureState.InProgress(0.0f, null, 3, null));
            }
        }
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer
    public final void addGestureStateCallback(GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0 gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0) {
        this.gestureStateChangedCallback = gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer
    public final void clearGestureStateCallback() {
        this.gestureStateChangedCallback = new RecentAppsGestureRecognizer$$ExternalSyntheticLambda0(1);
    }

    public /* synthetic */ RecentAppsGestureRecognizer(int i, float f, VelocityTracker velocityTracker, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, f, (i2 & 4) != 0 ? new VerticalVelocityTracker(null, 1, null) : velocityTracker);
    }
}
