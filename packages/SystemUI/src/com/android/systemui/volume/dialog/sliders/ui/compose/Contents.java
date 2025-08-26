package com.android.systemui.volume.dialog.sliders.ui.compose;

/* loaded from: classes3.dex */
public interface Contents {

    public interface Active extends Contents {

        public final class TrackEndIcon implements Active {
            public static final TrackEndIcon INSTANCE = new TrackEndIcon();

            private TrackEndIcon() {
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final int calculatePosition(float f, int i, int i2, int i3) {
                return (int) (((i2 * f) - i) - i3);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TrackEndIcon);
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final Contents getMirrored() {
                return Inactive.TrackStartIcon.INSTANCE;
            }

            public final int hashCode() {
                return 2049270406;
            }

            public final String toString() {
                return "TrackEndIcon";
            }
        }

        public final class TrackStartIcon implements Active {
            public static final TrackStartIcon INSTANCE = new TrackStartIcon();

            private TrackStartIcon() {
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final int calculatePosition(float f, int i, int i2, int i3) {
                return 0;
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TrackStartIcon);
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final Contents getMirrored() {
                return Inactive.TrackEndIcon.INSTANCE;
            }

            public final int hashCode() {
                return -1182915059;
            }

            public final String toString() {
                return "TrackStartIcon";
            }
        }

        @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
        default boolean isVisible(float f, int i, int i2, int i3) {
            return ((int) ((((float) i2) * f) - ((float) i3))) > i;
        }
    }

    public interface Inactive extends Contents {

        public final class TrackEndIcon implements Inactive {
            public static final TrackEndIcon INSTANCE = new TrackEndIcon();

            private TrackEndIcon() {
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final int calculatePosition(float f, int i, int i2, int i3) {
                return i2 - i;
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TrackEndIcon);
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final Contents getMirrored() {
                return Active.TrackStartIcon.INSTANCE;
            }

            public final int hashCode() {
                return -1715769343;
            }

            public final String toString() {
                return "TrackEndIcon";
            }
        }

        public final class TrackStartIcon implements Inactive {
            public static final TrackStartIcon INSTANCE = new TrackStartIcon();

            private TrackStartIcon() {
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final int calculatePosition(float f, int i, int i2, int i3) {
                return (int) ((i2 * f) + i3);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TrackStartIcon);
            }

            @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
            public final Contents getMirrored() {
                return Active.TrackEndIcon.INSTANCE;
            }

            public final int hashCode() {
                return 1271316680;
            }

            public final String toString() {
                return "TrackStartIcon";
            }
        }

        @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
        default boolean isVisible(float f, int i, int i2, int i3) {
            float f2 = i2;
            return f2 - ((f * f2) + ((float) i3)) > ((float) i);
        }
    }

    public final class Track implements Contents {
        public static final Track INSTANCE = new Track();

        private Track() {
        }

        @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
        public final int calculatePosition(float f, int i, int i2, int i3) {
            return 0;
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Track);
        }

        @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
        public final Contents getMirrored() {
            throw new IllegalStateException("unsupported for Track");
        }

        public final int hashCode() {
            return -486981024;
        }

        @Override // com.android.systemui.volume.dialog.sliders.ui.compose.Contents
        public final boolean isVisible(float f, int i, int i2, int i3) {
            return true;
        }

        public final String toString() {
            return "Track";
        }
    }

    int calculatePosition(float f, int i, int i2, int i3);

    Contents getMirrored();

    boolean isVisible(float f, int i, int i2, int i3);
}
