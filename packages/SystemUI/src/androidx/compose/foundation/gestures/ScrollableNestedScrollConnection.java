package androidx.compose.foundation.gestures;

import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.unit.Velocity;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;

/* loaded from: classes.dex */
final class ScrollableNestedScrollConnection implements NestedScrollConnection {
    public boolean enabled;
    public final ScrollingLogic scrollingLogic;

    public ScrollableNestedScrollConnection(ScrollingLogic scrollingLogic, boolean z) {
        this.scrollingLogic = scrollingLogic;
        this.enabled = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005b, code lost:
    
        if (r7 == r10) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006b, code lost:
    
        if (r7 == r10) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostFling-RZ2iAVY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo78onPostFlingRZ2iAVY(long j, long j2, Continuation continuation) throws Throwable {
        ScrollableNestedScrollConnection$onPostFling$1 scrollableNestedScrollConnection$onPostFling$1;
        long jM882minusAH228Gc;
        if (continuation instanceof ScrollableNestedScrollConnection$onPostFling$1) {
            scrollableNestedScrollConnection$onPostFling$1 = (ScrollableNestedScrollConnection$onPostFling$1) continuation;
            int i = scrollableNestedScrollConnection$onPostFling$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                scrollableNestedScrollConnection$onPostFling$1.label = i - Integer.MIN_VALUE;
            } else {
                scrollableNestedScrollConnection$onPostFling$1 = new ScrollableNestedScrollConnection$onPostFling$1(this, continuation);
            }
        }
        Object objM82doFlingAnimationQWom1Mo = scrollableNestedScrollConnection$onPostFling$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = scrollableNestedScrollConnection$onPostFling$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objM82doFlingAnimationQWom1Mo);
            jM882minusAH228Gc = 0;
            if (!this.enabled) {
                Velocity.Companion.getClass();
                return Velocity.m878boximpl(jM882minusAH228Gc);
            }
            boolean z = ComposeFoundationFlags.NewNestedFlingPropagationEnabled;
            ScrollingLogic scrollingLogic = this.scrollingLogic;
            if (!z) {
                scrollableNestedScrollConnection$onPostFling$1.J$0 = j2;
                scrollableNestedScrollConnection$onPostFling$1.label = 2;
                objM82doFlingAnimationQWom1Mo = scrollingLogic.m82doFlingAnimationQWom1Mo(j2, scrollableNestedScrollConnection$onPostFling$1);
            } else if (scrollingLogic.isFlinging) {
                Velocity.Companion.getClass();
            } else {
                scrollableNestedScrollConnection$onPostFling$1.J$0 = j2;
                scrollableNestedScrollConnection$onPostFling$1.label = 1;
                objM82doFlingAnimationQWom1Mo = scrollingLogic.m82doFlingAnimationQWom1Mo(j2, scrollableNestedScrollConnection$onPostFling$1);
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            j2 = scrollableNestedScrollConnection$onPostFling$1.J$0;
            ResultKt.throwOnFailure(objM82doFlingAnimationQWom1Mo);
            jM882minusAH228Gc = ((Velocity) objM82doFlingAnimationQWom1Mo).packedValue;
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j2 = scrollableNestedScrollConnection$onPostFling$1.J$0;
            ResultKt.throwOnFailure(objM82doFlingAnimationQWom1Mo);
            jM882minusAH228Gc = ((Velocity) objM82doFlingAnimationQWom1Mo).packedValue;
        }
        jM882minusAH228Gc = Velocity.m882minusAH228Gc(j2, jM882minusAH228Gc);
        return Velocity.m878boximpl(jM882minusAH228Gc);
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M, reason: not valid java name */
    public final long mo79onPostScrollDzOQY0M(int i, long j, long j2) {
        if (!this.enabled) {
            Offset.Companion.getClass();
            return 0L;
        }
        ScrollingLogic scrollingLogic = this.scrollingLogic;
        if (!scrollingLogic.scrollableState.isScrollInProgress()) {
            return scrollingLogic.m86toOffsettuRUvjQ(scrollingLogic.reverseIfNeeded(scrollingLogic.scrollableState.dispatchRawDelta(scrollingLogic.reverseIfNeeded(scrollingLogic.m85toFloatk4lQ0M(j2)))));
        }
        Offset.Companion.getClass();
        return 0L;
    }
}
