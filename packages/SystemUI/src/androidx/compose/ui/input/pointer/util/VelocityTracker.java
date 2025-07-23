package androidx.compose.ui.input.pointer.util;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.util.VelocityTracker1D;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VelocityTracker {
    public long lastMoveEventTimeStamp;
    public final VelocityTracker1D xVelocityTracker;
    public final VelocityTracker1D yVelocityTracker;

    public VelocityTracker() {
        VelocityTracker1D.Strategy strategy = VelocityTracker1D.Strategy.Lsq2;
        this.xVelocityTracker = new VelocityTracker1D(false, strategy, 1, null);
        this.yVelocityTracker = new VelocityTracker1D(false, strategy, 1, null);
        Offset.Companion.getClass();
    }

    /* renamed from: addPosition-Uv8p0NA, reason: not valid java name */
    public final void m599addPositionUv8p0NA(long j, long j2) {
        this.xVelocityTracker.addDataPoint(Float.intBitsToFloat((int) (j2 >> 32)), j);
        this.yVelocityTracker.addDataPoint(Float.intBitsToFloat((int) (j2 & 4294967295L)), j);
    }

    /* renamed from: calculateVelocity-AH228Gc, reason: not valid java name */
    public final long m600calculateVelocityAH228Gc(long j) {
        if (!(Velocity.m878getXimpl(j) > 0.0f && Velocity.m879getYimpl(j) > 0.0f)) {
            InlineClassHelperKt.throwIllegalStateException("maximumVelocity should be a positive value. You specified=" + ((Object) Velocity.m883toStringimpl(j)));
        }
        return VelocityKt.Velocity(this.xVelocityTracker.calculateVelocity(Velocity.m878getXimpl(j)), this.yVelocityTracker.calculateVelocity(Velocity.m879getYimpl(j)));
    }

    public final void resetTracking() {
        VelocityTracker1D velocityTracker1D = this.xVelocityTracker;
        DataPointAtTime[] dataPointAtTimeArr = velocityTracker1D.samples;
        Arrays.fill(dataPointAtTimeArr, 0, dataPointAtTimeArr.length, (Object) null);
        velocityTracker1D.index = 0;
        VelocityTracker1D velocityTracker1D2 = this.yVelocityTracker;
        DataPointAtTime[] dataPointAtTimeArr2 = velocityTracker1D2.samples;
        Arrays.fill(dataPointAtTimeArr2, 0, dataPointAtTimeArr2.length, (Object) null);
        velocityTracker1D2.index = 0;
        this.lastMoveEventTimeStamp = 0L;
    }
}
