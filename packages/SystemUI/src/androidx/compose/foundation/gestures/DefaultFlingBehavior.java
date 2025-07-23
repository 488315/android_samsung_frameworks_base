package androidx.compose.foundation.gestures;

import androidx.compose.animation.core.DecayAnimationSpec;
import androidx.compose.ui.MotionDurationScale;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultFlingBehavior implements FlingBehavior {
    public DecayAnimationSpec flingDecay;
    public final MotionDurationScale motionDurationScale;

    public DefaultFlingBehavior(DecayAnimationSpec<Float> decayAnimationSpec, MotionDurationScale motionDurationScale) {
        this.flingDecay = decayAnimationSpec;
        this.motionDurationScale = motionDurationScale;
    }

    @Override // androidx.compose.foundation.gestures.FlingBehavior
    public final Object performFling(ScrollScope scrollScope, float f, ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.motionDurationScale, new DefaultFlingBehavior$performFling$2(f, this, scrollScope, null), continuationImpl);
    }

    public DefaultFlingBehavior(DecayAnimationSpec decayAnimationSpec, MotionDurationScale motionDurationScale, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(decayAnimationSpec, (i & 2) != 0 ? ScrollableKt.DefaultScrollMotionDurationScale : motionDurationScale);
    }
}
