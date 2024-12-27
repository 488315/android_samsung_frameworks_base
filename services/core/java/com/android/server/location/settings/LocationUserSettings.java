package com.android.server.location.settings;

import java.util.Objects;

public final class LocationUserSettings {
    public final boolean mAdasGnssLocationEnabled;

    public LocationUserSettings(boolean z) {
        this.mAdasGnssLocationEnabled = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LocationUserSettings)
                && this.mAdasGnssLocationEnabled
                        == ((LocationUserSettings) obj).mAdasGnssLocationEnabled;
    }

    public final int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mAdasGnssLocationEnabled));
    }
}
