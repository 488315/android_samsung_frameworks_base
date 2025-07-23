package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.FlingBehavior;
import androidx.compose.foundation.gestures.TargetedFlingBehavior;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PagerWrapperFlingBehavior implements FlingBehavior {
    public final TargetedFlingBehavior originalFlingBehavior;
    public final PagerState pagerState;

    public PagerWrapperFlingBehavior(TargetedFlingBehavior targetedFlingBehavior, PagerState pagerState) {
        this.originalFlingBehavior = targetedFlingBehavior;
        this.pagerState = pagerState;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // androidx.compose.foundation.gestures.FlingBehavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object performFling(final androidx.compose.foundation.gestures.ScrollScope r5, float r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1
            if (r0 == 0) goto L13
            r0 = r7
            androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1 r0 = (androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1 r0 = new androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            androidx.compose.foundation.pager.PagerWrapperFlingBehavior r4 = (androidx.compose.foundation.pager.PagerWrapperFlingBehavior) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4a
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r7)
            androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$resultVelocity$1$1 r7 = new androidx.compose.foundation.pager.PagerWrapperFlingBehavior$performFling$resultVelocity$1$1
            r7.<init>()
            r0.L$0 = r4
            r0.label = r3
            androidx.compose.foundation.gestures.TargetedFlingBehavior r2 = r4.originalFlingBehavior
            androidx.compose.foundation.gestures.snapping.SnapFlingBehavior r2 = (androidx.compose.foundation.gestures.snapping.SnapFlingBehavior) r2
            java.lang.Object r7 = r2.performFling(r5, r6, r7, r0)
            if (r7 != r1) goto L4a
            return r1
        L4a:
            java.lang.Number r7 = (java.lang.Number) r7
            float r5 = r7.floatValue()
            androidx.compose.foundation.pager.PagerState r6 = r4.pagerState
            float r6 = r6.getCurrentPageOffsetFraction()
            r7 = 0
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            androidx.compose.foundation.pager.PagerState r4 = r4.pagerState
            if (r6 != 0) goto L5e
            goto L97
        L5e:
            float r6 = r4.getCurrentPageOffsetFraction()
            float r6 = java.lang.Math.abs(r6)
            double r0 = (double) r6
            r2 = 4562254508917369340(0x3f50624dd2f1a9fc, double:0.001)
            int r6 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r6 >= 0) goto L97
            int r6 = r4.getCurrentPage()
            androidx.compose.foundation.gestures.ScrollableState r0 = r4.scrollableState
            boolean r0 = r0.isScrollInProgress()
            if (r0 == 0) goto L92
            androidx.compose.runtime.MutableState r0 = r4.pagerLayoutInfoState
            androidx.compose.runtime.SnapshotMutableStateImpl r0 = (androidx.compose.runtime.SnapshotMutableStateImpl) r0
            java.lang.Object r0 = r0.getValue()
            androidx.compose.foundation.pager.PagerMeasureResult r0 = (androidx.compose.foundation.pager.PagerMeasureResult) r0
            kotlinx.coroutines.CoroutineScope r0 = r0.coroutineScope
            androidx.compose.foundation.pager.PagerState$requestScrollToPage$1 r1 = new androidx.compose.foundation.pager.PagerState$requestScrollToPage$1
            r2 = 0
            r1.<init>(r4, r2)
            r3 = 3
            kotlinx.coroutines.BuildersKt.launch$default(r0, r2, r2, r1, r3)
        L92:
            r0 = 0
            r4.snapToItem$foundation_release(r7, r0, r6)
            goto L9a
        L97:
            r4.getCurrentPageOffsetFraction()
        L9a:
            java.lang.Float r4 = new java.lang.Float
            r4.<init>(r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.pager.PagerWrapperFlingBehavior.performFling(androidx.compose.foundation.gestures.ScrollScope, float, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
