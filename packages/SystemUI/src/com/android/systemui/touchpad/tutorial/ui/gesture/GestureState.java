package com.android.systemui.touchpad.tutorial.ui.gesture;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public interface GestureState {

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

    public final class InProgress implements GestureState {
        public final GestureDirection direction;
        public final float progress;

        /* JADX WARN: Multi-variable type inference failed */
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
            int iHashCode = Float.hashCode(this.progress) * 31;
            GestureDirection gestureDirection = this.direction;
            return iHashCode + (gestureDirection == null ? 0 : gestureDirection.hashCode());
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
