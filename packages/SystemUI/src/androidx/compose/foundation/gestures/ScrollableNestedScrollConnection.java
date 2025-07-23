package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ScrollableNestedScrollConnection implements NestedScrollConnection {
    public boolean enabled;
    public final ScrollingLogic scrollingLogic;

    public ScrollableNestedScrollConnection(ScrollingLogic scrollingLogic, boolean z) {
        this.scrollingLogic = scrollingLogic;
        this.enabled = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x005b, code lost:
    
        if (r7 == r10) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006b, code lost:
    
        if (r7 == r10) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostFling-RZ2iAVY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object mo77onPostFlingRZ2iAVY(long r6, long r8, kotlin.coroutines.Continuation r10) {
        /*
            r5 = this;
            boolean r6 = r10 instanceof androidx.compose.foundation.gestures.ScrollableNestedScrollConnection$onPostFling$1
            if (r6 == 0) goto L13
            r6 = r10
            androidx.compose.foundation.gestures.ScrollableNestedScrollConnection$onPostFling$1 r6 = (androidx.compose.foundation.gestures.ScrollableNestedScrollConnection$onPostFling$1) r6
            int r7 = r6.label
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r7 & r0
            if (r1 == 0) goto L13
            int r7 = r7 - r0
            r6.label = r7
            goto L18
        L13:
            androidx.compose.foundation.gestures.ScrollableNestedScrollConnection$onPostFling$1 r6 = new androidx.compose.foundation.gestures.ScrollableNestedScrollConnection$onPostFling$1
            r6.<init>(r5, r10)
        L18:
            java.lang.Object r7 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r10 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r6.label
            r1 = 2
            r2 = 1
            if (r0 == 0) goto L3a
            if (r0 == r2) goto L34
            if (r0 != r1) goto L2c
            long r8 = r6.J$0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L6e
        L2c:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L34:
            long r8 = r6.J$0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5e
        L3a:
            kotlin.ResultKt.throwOnFailure(r7)
            boolean r7 = r5.enabled
            r3 = 0
            if (r7 == 0) goto L77
            boolean r7 = androidx.compose.foundation.ComposeFoundationFlags.NewNestedFlingPropagationEnabled
            androidx.compose.foundation.gestures.ScrollingLogic r5 = r5.scrollingLogic
            if (r7 == 0) goto L63
            boolean r7 = r5.isFlinging
            if (r7 == 0) goto L53
            androidx.compose.ui.unit.Velocity$Companion r5 = androidx.compose.ui.unit.Velocity.Companion
            r5.getClass()
            goto L72
        L53:
            r6.J$0 = r8
            r6.label = r2
            java.lang.Object r7 = r5.m81doFlingAnimationQWom1Mo(r8, r6)
            if (r7 != r10) goto L5e
            goto L6d
        L5e:
            androidx.compose.ui.unit.Velocity r7 = (androidx.compose.ui.unit.Velocity) r7
            long r3 = r7.packedValue
            goto L72
        L63:
            r6.J$0 = r8
            r6.label = r1
            java.lang.Object r7 = r5.m81doFlingAnimationQWom1Mo(r8, r6)
            if (r7 != r10) goto L6e
        L6d:
            return r10
        L6e:
            androidx.compose.ui.unit.Velocity r7 = (androidx.compose.ui.unit.Velocity) r7
            long r3 = r7.packedValue
        L72:
            long r3 = androidx.compose.ui.unit.Velocity.m880minusAH228Gc(r8, r3)
            goto L7c
        L77:
            androidx.compose.ui.unit.Velocity$Companion r5 = androidx.compose.ui.unit.Velocity.Companion
            r5.getClass()
        L7c:
            androidx.compose.ui.unit.Velocity r5 = androidx.compose.ui.unit.Velocity.m876boximpl(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.ScrollableNestedScrollConnection.mo77onPostFlingRZ2iAVY(long, long, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M, reason: not valid java name */
    public final long mo78onPostScrollDzOQY0M(int i, long j, long j2) {
        if (!this.enabled) {
            Offset.Companion.getClass();
            return 0L;
        }
        ScrollingLogic scrollingLogic = this.scrollingLogic;
        if (!scrollingLogic.scrollableState.isScrollInProgress()) {
            return scrollingLogic.m85toOffsettuRUvjQ(scrollingLogic.reverseIfNeeded(scrollingLogic.scrollableState.dispatchRawDelta(scrollingLogic.reverseIfNeeded(scrollingLogic.m84toFloatk4lQ0M(j2)))));
        }
        Offset.Companion.getClass();
        return 0L;
    }
}
