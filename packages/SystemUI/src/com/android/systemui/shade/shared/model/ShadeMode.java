package com.android.systemui.shade.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ShadeMode {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
