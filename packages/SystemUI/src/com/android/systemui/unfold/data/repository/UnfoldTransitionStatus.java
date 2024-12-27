package com.android.systemui.unfold.data.repository;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class UnfoldTransitionStatus {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TransitionFinished extends UnfoldTransitionStatus {
        public static final TransitionFinished INSTANCE = new TransitionFinished();

        private TransitionFinished() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TransitionFinished);
        }

        public final int hashCode() {
            return 1524641535;
        }

        public final String toString() {
            return "TransitionFinished";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TransitionInProgress extends UnfoldTransitionStatus {
        public final float progress;

        public TransitionInProgress(float f) {
            super(null);
            this.progress = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof TransitionInProgress) && Float.compare(this.progress, ((TransitionInProgress) obj).progress) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.progress);
        }

        public final String toString() {
            return "TransitionInProgress(progress=" + this.progress + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TransitionStarted extends UnfoldTransitionStatus {
        public static final TransitionStarted INSTANCE = new TransitionStarted();

        private TransitionStarted() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TransitionStarted);
        }

        public final int hashCode() {
            return -1826272172;
        }

        public final String toString() {
            return "TransitionStarted";
        }
    }

    private UnfoldTransitionStatus() {
    }

    public /* synthetic */ UnfoldTransitionStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
