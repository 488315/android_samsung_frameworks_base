package androidx.compose.foundation.gestures;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class DragEvent {

    public final class DragCancelled extends DragEvent {
        public static final DragCancelled INSTANCE = new DragCancelled();

        private DragCancelled() {
            super(null);
        }
    }

    public final class DragDelta extends DragEvent {
        public final long delta;

        public /* synthetic */ DragDelta(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        private DragDelta(long j) {
            super(null);
            this.delta = j;
        }
    }

    public final class DragStarted extends DragEvent {
        public final long startPoint;

        public /* synthetic */ DragStarted(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        private DragStarted(long j) {
            super(null);
            this.startPoint = j;
        }
    }

    public final class DragStopped extends DragEvent {
        public final long velocity;

        public /* synthetic */ DragStopped(long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(j);
        }

        private DragStopped(long j) {
            super(null);
            this.velocity = j;
        }
    }

    public /* synthetic */ DragEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private DragEvent() {
    }
}
