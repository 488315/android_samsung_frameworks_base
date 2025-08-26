package com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model;

import com.android.systemui.R;

/* loaded from: classes2.dex */
public interface SensorPrivacyTileResources {

    public final class CameraPrivacyTileResources implements SensorPrivacyTileResources {
        public static final CameraPrivacyTileResources INSTANCE = new CameraPrivacyTileResources();

        private CameraPrivacyTileResources() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof CameraPrivacyTileResources);
        }

        @Override // com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources
        public final int getIconRes(boolean z) {
            return z ? R.drawable.qs_camera_access_icon_off : R.drawable.qs_camera_access_icon_on;
        }

        @Override // com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources
        public final int getTileLabelRes() {
            return R.string.quick_settings_camera_label;
        }

        public final int hashCode() {
            return 1128579314;
        }

        public final String toString() {
            return "CameraPrivacyTileResources";
        }
    }

    public final class MicrophonePrivacyTileResources implements SensorPrivacyTileResources {
        public static final MicrophonePrivacyTileResources INSTANCE = new MicrophonePrivacyTileResources();

        private MicrophonePrivacyTileResources() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof MicrophonePrivacyTileResources);
        }

        @Override // com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources
        public final int getIconRes(boolean z) {
            return z ? R.drawable.qs_mic_access_off : R.drawable.qs_mic_access_on;
        }

        @Override // com.android.systemui.qs.tiles.impl.sensorprivacy.ui.model.SensorPrivacyTileResources
        public final int getTileLabelRes() {
            return R.string.quick_settings_mic_label;
        }

        public final int hashCode() {
            return 1931105143;
        }

        public final String toString() {
            return "MicrophonePrivacyTileResources";
        }
    }

    int getIconRes(boolean z);

    int getTileLabelRes();
}
