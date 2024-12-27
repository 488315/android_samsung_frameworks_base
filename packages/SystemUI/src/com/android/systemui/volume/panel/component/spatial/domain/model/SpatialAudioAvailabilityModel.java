package com.android.systemui.volume.panel.component.spatial.domain.model;

public interface SpatialAudioAvailabilityModel {

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

    public interface SpatialAudio extends SpatialAudioAvailabilityModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        public final class Companion implements SpatialAudio {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

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
