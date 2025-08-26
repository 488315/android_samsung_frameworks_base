package com.android.systemui.screenrecord.data.model;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
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
        public static final Companion Companion = new Companion(null);
        public final long countdownSeconds;
        public final long millisUntilStarted;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Starting(long j) {
            this.millisUntilStarted = j;
            Companion.getClass();
            this.countdownSeconds = Math.floorDiv(j + 500, 1000);
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
            return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.millisUntilStarted, ")", new StringBuilder("Starting(millisUntilStarted="));
        }
    }
}
