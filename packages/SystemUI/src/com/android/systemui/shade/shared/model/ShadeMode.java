package com.android.systemui.shade.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;

public interface ShadeMode {

    public final class Dual implements ShadeMode {
        public static final Dual INSTANCE = null;

        static {
            new Dual();
        }

        private Dual() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Dual);
        }

        public final int hashCode() {
            return 1185394120;
        }

        public final String toString() {
            return "Dual";
        }
    }

    public final class Single implements ShadeMode {
        public static final Single INSTANCE = new Single();

        private Single() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Single);
        }

        public final int hashCode() {
            return 1416156820;
        }

        public final String toString() {
            return "Single";
        }
    }

    public final class Split implements ShadeMode {
        public static final Split INSTANCE = new Split();

        private Split() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Split);
        }

        public final int hashCode() {
            return -1893773490;
        }

        public final String toString() {
            return ActionResults.RESULT_SUPPORT_SPLIT;
        }
    }
}
