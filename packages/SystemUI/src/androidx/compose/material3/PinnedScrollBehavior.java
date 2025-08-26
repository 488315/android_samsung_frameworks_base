package androidx.compose.material3;

import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.unit.Velocity;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class PinnedScrollBehavior implements TopAppBarScrollBehavior {
    public final Function0 canScroll;
    public final boolean isPinned;
    public final PinnedScrollBehavior$nestedScrollConnection$1 nestedScrollConnection;
    public final TopAppBarState state;

    /* JADX WARN: Type inference failed for: r1v2, types: [androidx.compose.material3.PinnedScrollBehavior$nestedScrollConnection$1] */
    public PinnedScrollBehavior(TopAppBarState topAppBarState, Function0 function0) {
        this.state = topAppBarState;
        this.canScroll = function0;
        this.isPinned = true;
        this.nestedScrollConnection = new NestedScrollConnection() { // from class: androidx.compose.material3.PinnedScrollBehavior$nestedScrollConnection$1
            @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
            /* renamed from: onPostFling-RZ2iAVY */
            public final Object mo78onPostFlingRZ2iAVY(long j, long j2, Continuation continuation) {
                if (Velocity.m881getYimpl(j2) > 0.0f) {
                    ((SnapshotMutableFloatStateImpl) this.this$0.state.contentOffset$delegate).setFloatValue(0.0f);
                }
                return super.mo78onPostFlingRZ2iAVY(j, j2, continuation);
            }

            @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
            /* renamed from: onPostScroll-DzOQY0M */
            public final long mo79onPostScrollDzOQY0M(int i, long j, long j2) {
                PinnedScrollBehavior pinnedScrollBehavior = this.this$0;
                if (!((Boolean) pinnedScrollBehavior.canScroll.invoke()).booleanValue()) {
                    Offset.Companion.getClass();
                    return 0L;
                }
                TopAppBarState topAppBarState2 = pinnedScrollBehavior.state;
                ((SnapshotMutableFloatStateImpl) topAppBarState2.contentOffset$delegate).setFloatValue(Offset.m401getYimpl(j) + ((SnapshotMutableFloatStateImpl) topAppBarState2.contentOffset$delegate).getFloatValue());
                Offset.Companion.getClass();
                return 0L;
            }
        };
    }

    @Override // androidx.compose.material3.TopAppBarScrollBehavior
    public final PinnedScrollBehavior$nestedScrollConnection$1 getNestedScrollConnection() {
        return this.nestedScrollConnection;
    }

    public /* synthetic */ PinnedScrollBehavior(TopAppBarState topAppBarState, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(topAppBarState, (i & 2) != 0 ? new Function0() { // from class: androidx.compose.material3.PinnedScrollBehavior.1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Boolean.TRUE;
            }
        } : function0);
    }
}
