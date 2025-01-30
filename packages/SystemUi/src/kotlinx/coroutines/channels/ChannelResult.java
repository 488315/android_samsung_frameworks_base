package kotlinx.coroutines.channels;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ChannelResult {
    public static final Companion Companion = new Companion(null);
    public static final Failed failed = new Failed();
    public final Object holder;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Closed extends Failed {
        public final Throwable cause;

        public Closed(Throwable th) {
            this.cause = th;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Closed) {
                if (Intrinsics.areEqual(this.cause, ((Closed) obj).cause)) {
                    return true;
                }
            }
            return false;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: closed-JP2dKIU, reason: not valid java name */
        public static Closed m2875closedJP2dKIU(Throwable th) {
            Closed closed = new Closed(th);
            Companion companion = ChannelResult.Companion;
            return closed;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Failed {
        public String toString() {
            return "Failed";
        }
    }

    private /* synthetic */ ChannelResult(Object obj) {
        this.holder = obj;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ChannelResult m2873boximpl(Object obj) {
        return new ChannelResult(obj);
    }

    /* renamed from: getOrThrow-impl, reason: not valid java name */
    public static final void m2874getOrThrowimpl(Object obj) {
        Throwable th;
        if (obj instanceof Failed) {
            if ((obj instanceof Closed) && (th = ((Closed) obj).cause) != null) {
                throw th;
            }
            throw new IllegalStateException(("Trying to call 'getOrThrow' on a failed channel result: " + obj).toString());
        }
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
