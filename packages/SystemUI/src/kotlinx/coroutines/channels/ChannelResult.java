package kotlinx.coroutines.channels;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ChannelResult {
    public static final Companion Companion = new Companion(null);
    public static final Failed failed = new Failed();
    public final Object holder;

    public final class Closed extends Failed {
        public final Throwable cause;

        public Closed(Throwable th) {
            this.cause = th;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof Closed) && Intrinsics.areEqual(this.cause, ((Closed) obj).cause);
        }

        public final int hashCode() {
            Throwable th = this.cause;
            if (th != null) {
                return th.hashCode();
            }
            return 0;
        }

        @Override // kotlinx.coroutines.channels.ChannelResult.Failed
        public final String toString() {
            return "Closed(" + this.cause + ")";
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: closed-JP2dKIU, reason: not valid java name */
        public static Closed m3479closedJP2dKIU(Throwable th) {
            Closed closed = new Closed(th);
            Companion companion = ChannelResult.Companion;
            return closed;
        }

        private Companion() {
        }
    }

    public class Failed {
        public String toString() {
            return "Failed";
        }
    }

    private /* synthetic */ ChannelResult(Object obj) {
        this.holder = obj;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ChannelResult m3476boximpl(Object obj) {
        return new ChannelResult(obj);
    }

    /* renamed from: exceptionOrNull-impl, reason: not valid java name */
    public static final Throwable m3477exceptionOrNullimpl(Failed failed2) {
        Closed closed = failed2 instanceof Closed ? (Closed) failed2 : null;
        if (closed != null) {
            return closed.cause;
        }
        return null;
    }

    /* renamed from: getOrNull-impl, reason: not valid java name */
    public static final Object m3478getOrNullimpl(Object obj) {
        if (obj instanceof Failed) {
            return null;
        }
        return obj;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ChannelResult) {
            return Intrinsics.areEqual(this.holder, ((ChannelResult) obj).holder);
        }
        return false;
    }

    public final int hashCode() {
        Object obj = this.holder;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public final String toString() {
        Object obj = this.holder;
        if (obj instanceof Closed) {
            return ((Closed) obj).toString();
        }
        return "Value(" + obj + ")";
    }
}
