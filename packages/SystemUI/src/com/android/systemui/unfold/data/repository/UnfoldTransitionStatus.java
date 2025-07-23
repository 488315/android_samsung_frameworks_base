package com.android.systemui.unfold.data.repository;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class UnfoldTransitionStatus {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public /* synthetic */ UnfoldTransitionStatus(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private UnfoldTransitionStatus() {
    }
}
