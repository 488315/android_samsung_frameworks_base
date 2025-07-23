package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;
import androidx.compose.ui.input.pointer.util.DataPointAtTime;
import androidx.compose.ui.input.pointer.util.VelocityTracker1D;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VerticalVelocityTracker implements VelocityTracker {
    public final VelocityTracker1D velocityTracker;

    public VerticalVelocityTracker() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        if (motionEvent.getActionMasked() == 0) {
            VelocityTracker1D velocityTracker1D = this.velocityTracker;
            DataPointAtTime[] dataPointAtTimeArr = velocityTracker1D.samples;
            Arrays.fill(dataPointAtTimeArr, 0, dataPointAtTimeArr.length, (Object) null);
            velocityTracker1D.index = 0;
        }
        this.velocityTracker.addDataPoint(motionEvent.getY(), motionEvent.getEventTime());
    }

    public VerticalVelocityTracker(VelocityTracker1D velocityTracker1D) {
        this.velocityTracker = velocityTracker1D;
    }

    public /* synthetic */ VerticalVelocityTracker(VelocityTracker1D velocityTracker1D, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new VelocityTracker1D(false) : velocityTracker1D);
    }
}
