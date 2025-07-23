package android.app.time;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class TimeZoneConfiguration implements Parcelable {
    public static final Parcelable.Creator<TimeZoneConfiguration> CREATOR = new Parcelable.Creator<TimeZoneConfiguration>() { // from class: android.app.time.TimeZoneConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneConfiguration createFromParcel(Parcel parcel) {
            return TimeZoneConfiguration.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TimeZoneConfiguration[] newArray(int i) {
            return new TimeZoneConfiguration[i];
        }
    };
    private static final String SETTING_AUTO_DETECTION_ENABLED = "autoDetectionEnabled";
    private static final String SETTING_GEO_DETECTION_ENABLED = "geoDetectionEnabled";
    private static final String SETTING_NOTIFICATIONS_ENABLED = "notificationsEnabled";
    private final Bundle mBundle;

    @Retention(RetentionPolicy.SOURCE)
    @interface Setting {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private TimeZoneConfiguration(Builder builder) {
        this.mBundle = (Bundle) Objects.requireNonNull(builder.mBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TimeZoneConfiguration createFromParcel(Parcel parcel) {
        return new Builder().setPropertyBundleInternal(parcel.readBundle()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public boolean isComplete() {
        return hasIsAutoDetectionEnabled() && hasIsGeoDetectionEnabled() && hasIsNotificationsEnabled();
    }

    public boolean isAutoDetectionEnabled() {
        enforceSettingPresent(SETTING_AUTO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean hasIsAutoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean isGeoDetectionEnabled() {
        enforceSettingPresent(SETTING_GEO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_GEO_DETECTION_ENABLED);
    }

    public boolean hasIsGeoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_GEO_DETECTION_ENABLED);
    }

    public boolean areNotificationsEnabled() {
        enforceSettingPresent(SETTING_NOTIFICATIONS_ENABLED);
        return this.mBundle.getBoolean(SETTING_NOTIFICATIONS_ENABLED);
    }

    public boolean hasIsNotificationsEnabled() {
        return this.mBundle.containsKey(SETTING_NOTIFICATIONS_ENABLED);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mBundle.kindofEquals(((TimeZoneConfiguration) obj).mBundle);
    }

    public int hashCode() {
        return Objects.hash(this.mBundle);
    }

    public String toString() {
        return "TimeZoneConfiguration{mBundle=" + this.mBundle + '}';
    }

    private void enforceSettingPresent(String str) {
        if (this.mBundle.containsKey(str)) {
            return;
        }
        throw new IllegalStateException(str + " is not set");
    }

    @SystemApi
    public static final class Builder {
        private final Bundle mBundle = new Bundle();

        public Builder() {
        }

        public Builder(TimeZoneConfiguration timeZoneConfiguration) {
            mergeProperties(timeZoneConfiguration);
        }

        public Builder mergeProperties(TimeZoneConfiguration timeZoneConfiguration) {
            this.mBundle.putAll(timeZoneConfiguration.mBundle);
            return this;
        }

        Builder setPropertyBundleInternal(Bundle bundle) {
            this.mBundle.putAll(bundle);
            return this;
        }

        public Builder setAutoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(TimeZoneConfiguration.SETTING_AUTO_DETECTION_ENABLED, z);
            return this;
        }

        public Builder setGeoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(TimeZoneConfiguration.SETTING_GEO_DETECTION_ENABLED, z);
            return this;
        }

        public Builder setNotificationsEnabled(boolean z) {
            this.mBundle.putBoolean(TimeZoneConfiguration.SETTING_NOTIFICATIONS_ENABLED, z);
            return this;
        }

        public TimeZoneConfiguration build() {
            return new TimeZoneConfiguration(this);
        }
    }
}
