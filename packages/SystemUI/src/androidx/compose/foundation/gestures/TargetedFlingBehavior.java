package androidx.compose.foundation.gestures;

import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes.dex */
public interface TargetedFlingBehavior extends FlingBehavior {
    @Override // androidx.compose.foundation.gestures.FlingBehavior
    default Object performFling(ScrollScope scrollScope, float f, ContinuationImpl continuationImpl) {
        return ((SnapFlingBehavior) this).performFling(scrollScope, f, TargetedFlingBehaviorKt.NoOnReport, continuationImpl);
    }
}
