package androidx.window.layout;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface FoldingFeature {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Orientation {
        public static final Orientation HORIZONTAL;
        public static final Orientation VERTICAL;
        public final String description;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            VERTICAL = new Orientation("VERTICAL");
            HORIZONTAL = new Orientation("HORIZONTAL");
        }

        private Orientation(String str) {
            this.description = str;
        }

        public final String toString() {
            return this.description;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class State {
        public static final State FLAT;
        public static final State HALF_OPENED;
        public final String description;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
            FLAT = new State("FLAT");
            HALF_OPENED = new State("HALF_OPENED");
        }

        private State(String str) {
            this.description = str;
        }

        public final String toString() {
            return this.description;
        }
    }
}
