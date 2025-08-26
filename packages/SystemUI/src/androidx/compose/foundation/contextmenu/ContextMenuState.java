package androidx.compose.foundation.contextmenu;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ContextMenuState {
    public final MutableState status$delegate;

    public abstract class Status {

        public final class Closed extends Status {
            public static final Closed INSTANCE = new Closed();

            private Closed() {
                super(null);
            }

            public final String toString() {
                return "Closed";
            }
        }

        public final class Open extends Status {
            public final long offset;

            public /* synthetic */ Open(long j, DefaultConstructorMarker defaultConstructorMarker) {
                this(j);
            }

            public final boolean equals(Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Open)) {
                    return false;
                }
                return Offset.m398equalsimpl0(this.offset, ((Open) obj).offset);
            }

            public final int hashCode() {
                Offset.Companion companion = Offset.Companion;
                return Long.hashCode(this.offset);
            }

            public final String toString() {
                return "Open(offset=" + ((Object) Offset.m405toStringimpl(this.offset)) + ')';
            }

            private Open(long j) {
                super(null);
                this.offset = j;
                if ((j & 9223372034707292159L) != 9205357640488583168L) {
                    return;
                }
                InlineClassHelperKt.throwIllegalStateException("ContextMenuState.Status should never be open with an unspecified offset. Use ContextMenuState.Status.Closed instead.");
            }
        }

        public /* synthetic */ Status(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Status() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContextMenuState() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ContextMenuState) {
            return Intrinsics.areEqual((Status) ((SnapshotMutableStateImpl) ((ContextMenuState) obj).status$delegate).getValue(), (Status) ((SnapshotMutableStateImpl) this.status$delegate).getValue());
        }
        return false;
    }

    public final int hashCode() {
        return ((Status) ((SnapshotMutableStateImpl) this.status$delegate).getValue()).hashCode();
    }

    public final String toString() {
        return "ContextMenuState(status=" + ((Status) ((SnapshotMutableStateImpl) this.status$delegate).getValue()) + ')';
    }

    public ContextMenuState(Status status) {
        this.status$delegate = SnapshotStateKt.mutableStateOf$default(status);
    }

    public /* synthetic */ ContextMenuState(Status status, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Status.Closed.INSTANCE : status);
    }
}
