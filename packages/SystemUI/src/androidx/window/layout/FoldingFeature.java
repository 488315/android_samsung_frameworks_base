package androidx.window.layout;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public interface FoldingFeature {

    public final class Orientation {
        public static final Orientation HORIZONTAL;
        public static final Orientation VERTICAL;
        public final String description;

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

    public final class State {
        public static final State FLAT;
        public static final State HALF_OPENED;
        public final String description;

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
