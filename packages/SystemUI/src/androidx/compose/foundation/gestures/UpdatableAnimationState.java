package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationVector1D;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.animation.core.VectorizedAnimationSpec;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UpdatableAnimationState {
    public static final Companion Companion = new Companion(null);
    public static final AnimationVector1D ZeroVector = new AnimationVector1D(0.0f);
    public boolean isRunning;
    public long lastFrameTime;
    public AnimationVector1D lastVelocity;
    public float value;
    public final VectorizedAnimationSpec vectorizedSpec;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public UpdatableAnimationState(AnimationSpec<Float> animationSpec) {
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        this.vectorizedSpec = animationSpec.vectorize(VectorConvertersKt.FloatToVector);
        this.lastFrameTime = Long.MIN_VALUE;
        this.lastVelocity = ZeroVector;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00bc, code lost:
    
        if (r15 != 0.0f) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e4, code lost:
    
        if (androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r0.getContext()).withFrameNanos(r15, r0) == r1) goto L47;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0028  */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v18 */
    /* JADX WARN: Type inference failed for: r13v19 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00b4 -> B:24:0x00b7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object animateToZero(kotlin.jvm.functions.Function1 r13, kotlin.jvm.functions.Function0 r14, kotlin.coroutines.jvm.internal.ContinuationImpl r15) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.UpdatableAnimationState.animateToZero(kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
