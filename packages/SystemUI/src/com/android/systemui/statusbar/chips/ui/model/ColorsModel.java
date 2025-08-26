package com.android.systemui.statusbar.chips.ui.model;

/* loaded from: classes3.dex */
public interface ColorsModel {

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
