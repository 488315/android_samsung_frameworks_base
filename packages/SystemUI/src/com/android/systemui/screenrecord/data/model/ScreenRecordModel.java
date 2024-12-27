package com.android.systemui.screenrecord.data.model;

public interface ScreenRecordModel {

    public final class DoingNothing implements ScreenRecordModel {
        public static final DoingNothing INSTANCE = new DoingNothing();

        private DoingNothing() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DoingNothing);
        }

        public final int hashCode() {
            return -2100932977;
        }

        public final String toString() {
            return "DoingNothing";
        }
    }

    public final class Recording implements ScreenRecordModel {
        public static final Recording INSTANCE = new Recording();

        private Recording() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Recording);
        }

        public final int hashCode() {
            return -1632877992;
        }

        public final String toString() {
            return "Recording";
        }
    }

    public final class Starting implements ScreenRecordModel {
        public final long millisUntilStarted;

        public Starting(long j) {
            this.millisUntilStarted = j;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Starting) && this.millisUntilStarted == ((Starting) obj).millisUntilStarted;
        }

        public final int hashCode() {
            return Long.hashCode(this.millisUntilStarted);
        }

        public final String toString() {
            return "Starting(millisUntilStarted=" + this.millisUntilStarted + ")";
        }
    }
}
