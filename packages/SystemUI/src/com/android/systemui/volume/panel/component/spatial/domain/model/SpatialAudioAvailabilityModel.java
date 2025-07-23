package com.android.systemui.volume.panel.component.spatial.domain.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface SpatialAudioAvailabilityModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HeadTracking implements SpatialAudio {
        public static final HeadTracking INSTANCE = new HeadTracking();

        private HeadTracking() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof HeadTracking);
        }

        public final int hashCode() {
            return 614680126;
        }

        public final String toString() {
            return "HeadTracking";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SpatialAudio extends SpatialAudioAvailabilityModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion implements SpatialAudio {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Unavailable implements SpatialAudioAvailabilityModel {
        public static final Unavailable INSTANCE = new Unavailable();

        private Unavailable() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unavailable);
        }

        public final int hashCode() {
            return 1618458089;
        }

        public final String toString() {
            return "Unavailable";
        }
    }
}
