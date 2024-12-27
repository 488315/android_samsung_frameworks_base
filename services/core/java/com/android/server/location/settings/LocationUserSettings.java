package com.android.server.location.settings;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
