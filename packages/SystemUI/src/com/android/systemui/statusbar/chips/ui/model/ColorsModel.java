package com.android.systemui.statusbar.chips.ui.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ColorsModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AccentThemed implements ColorsModel {
        public static final AccentThemed INSTANCE = new AccentThemed();

        private AccentThemed() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof AccentThemed);
        }

        public final int hashCode() {
            return 204961030;
        }

        public final String toString() {
            return "AccentThemed";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Red implements ColorsModel {
        public static final Red INSTANCE = new Red();

        private Red() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Red);
        }

        public final int hashCode() {
            return 1272928144;
        }

        public final String toString() {
            return "Red";
        }
    }
}
