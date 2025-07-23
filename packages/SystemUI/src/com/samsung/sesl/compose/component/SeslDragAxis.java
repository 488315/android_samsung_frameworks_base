package com.samsung.sesl.compose.component;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SeslDragAxis {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
