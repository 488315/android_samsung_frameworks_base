package com.android.systemui.volume.panel.component.spatial.domain.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface SpatialAudioAvailabilityModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SpatialAudio extends SpatialAudioAvailabilityModel {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion implements SpatialAudio {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
