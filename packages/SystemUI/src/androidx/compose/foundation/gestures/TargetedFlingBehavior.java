package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface TargetedFlingBehavior extends FlingBehavior {
    @Override // androidx.compose.foundation.gestures.FlingBehavior
    default Object performFling(ScrollScope scrollScope, float f, ContinuationImpl continuationImpl) {
        return ((SnapFlingBehavior) this).performFling(scrollScope, f, TargetedFlingBehaviorKt.NoOnReport, continuationImpl);
    }
}
