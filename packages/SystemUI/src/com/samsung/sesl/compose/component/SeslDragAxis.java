package com.samsung.sesl.compose.component;

/* loaded from: classes4.dex */
public interface SeslDragAxis {

    public final class Vertical implements SeslDragAxis {
        public static final Vertical INSTANCE = new Vertical();

        private Vertical() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Vertical);
        }

        public final int hashCode() {
            return -1492193323;
        }

        public final String toString() {
            return "Vertical";
        }
    }
}
