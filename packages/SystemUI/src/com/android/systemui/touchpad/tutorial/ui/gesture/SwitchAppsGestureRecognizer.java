package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.util.MathUtils;
import android.view.MotionEvent;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SwitchAppsGestureRecognizer implements GestureRecognizer {
    public final int gestureDistanceThresholdPx;
    public final DistanceTracker distanceTracker = new DistanceTracker(0.0f, 0.0f, 3, null);
    public Function1 gestureStateChangedCallback = new SwitchAppsGestureRecognizer$$ExternalSyntheticLambda0(0);

    public SwitchAppsGestureRecognizer(int i) {
        this.gestureDistanceThresholdPx = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        if (GestureRecognizerKt.isMultifingerTouchpadSwipe(motionEvent)) {
            if (!GestureRecognizerKt.isNFingerTouchpadSwipe(motionEvent, 4)) {
                if (motionEvent.getActionMasked() == 1) {
                    this.gestureStateChangedCallback.mo779invoke(GestureState.Error.INSTANCE);
                    return;
                }
                return;
            }
            DistanceGestureState processEvent = this.distanceTracker.processEvent(motionEvent);
            Function1 function1 = this.gestureStateChangedCallback;
            if (processEvent instanceof Finished) {
                if (((Finished) processEvent).deltaX >= this.gestureDistanceThresholdPx) {
                    function1.mo779invoke(GestureState.Finished.INSTANCE);
                    return;
                } else {
                    function1.mo779invoke(GestureState.Error.INSTANCE);
                    return;
                }
            }
            if (processEvent instanceof Moving) {
                function1.mo779invoke(new GestureState.InProgress(MathUtils.saturate(((Moving) processEvent).deltaX / this.gestureDistanceThresholdPx), null, 2, null));
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
        this.gestureStateChangedCallback = new SwitchAppsGestureRecognizer$$ExternalSyntheticLambda0(1);
    }
}
