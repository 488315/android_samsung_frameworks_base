package androidx.compose.foundation.contextmenu;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContextMenuState {
    public final MutableState status$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Status {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Closed extends Status {
            public static final Closed INSTANCE = new Closed();

            private Closed() {
                super(null);
            }

            public final String toString() {
                return "Closed";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return Offset.m396equalsimpl0(this.offset, ((Open) obj).offset);
            }

            public final int hashCode() {
                Offset.Companion companion = Offset.Companion;
                return Long.hashCode(this.offset);
            }

            public final String toString() {
                return "Open(offset=" + ((Object) Offset.m403toStringimpl(this.offset)) + ')';
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
