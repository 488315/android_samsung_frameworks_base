package com.android.systemui.touchpad.tutorial.ui.gesture;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface GestureState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Error implements GestureState {
        public static final Error INSTANCE = new Error();

        private Error() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Error);
        }

        public final int hashCode() {
            return -1685839892;
        }

        public final String toString() {
            return "Error";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Finished implements GestureState {
        public static final Finished INSTANCE = new Finished();

        private Finished() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Finished);
        }

        public final int hashCode() {
            return 421656590;
        }

        public final String toString() {
            return "Finished";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InProgress implements GestureState {
        public final GestureDirection direction;
        public final float progress;

        public InProgress() {
            this(0.0f, null, 3, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InProgress)) {
                return false;
            }
            InProgress inProgress = (InProgress) obj;
            return Float.compare(this.progress, inProgress.progress) == 0 && this.direction == inProgress.direction;
        }

        public final int hashCode() {
            int hashCode = Float.hashCode(this.progress) * 31;
            GestureDirection gestureDirection = this.direction;
            return hashCode + (gestureDirection == null ? 0 : gestureDirection.hashCode());
        }

        public final String toString() {
            return "InProgress(progress=" + this.progress + ", direction=" + this.direction + ")";
        }

        public InProgress(float f, GestureDirection gestureDirection) {
            this.progress = f;
            this.direction = gestureDirection;
        }

        public /* synthetic */ InProgress(float f, GestureDirection gestureDirection, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? null : gestureDirection);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotStarted implements GestureState {
        public static final NotStarted INSTANCE = new NotStarted();

        private NotStarted() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotStarted);
        }

        public final int hashCode() {
            return 1497410250;
        }

        public final String toString() {
            return "NotStarted";
        }
    }
}
