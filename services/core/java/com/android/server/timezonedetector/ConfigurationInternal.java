package com.android.server.timezonedetector;

import android.app.time.TimeZoneCapabilities;
import android.os.UserHandle;

import java.util.Objects;

public final class ConfigurationInternal {
    public final boolean mAutoDetectionEnabledSetting;
    public final boolean mEnhancedMetricsCollectionEnabled;
    public final boolean mGeoDetectionEnabledSetting;
    public final boolean mGeoDetectionRunInBackgroundEnabled;
    public final boolean mGeoDetectionSupported;
    public final boolean mGeoLocationFbEnabledSetting;
    public final boolean mLocationEnabledSetting;
    public final boolean mTelephonyDetectionSupported;
    public final boolean mTelephonyFallbackSupported;
    public final boolean mUserConfigAllowed;
    public final int mUserId;

    public final class Builder {
        public boolean mAutoDetectionEnabledSetting;
        public boolean mEnhancedMetricsCollectionEnabled;
        public boolean mGeoDetectionEnabledSetting;
        public boolean mGeoDetectionRunInBackgroundEnabled;
        public boolean mGeoDetectionSupported;
        public boolean mGeoLocationFbEnabledSetting;
        public boolean mLocationEnabledSetting;
        public boolean mTelephonyDetectionSupported;
        public boolean mTelephonyFallbackSupported;
        public boolean mUserConfigAllowed;
        public Integer mUserId;
    }

    public ConfigurationInternal(Builder builder) {
        this.mTelephonyDetectionSupported = builder.mTelephonyDetectionSupported;
        this.mGeoDetectionSupported = builder.mGeoDetectionSupported;
        this.mTelephonyFallbackSupported = builder.mTelephonyFallbackSupported;
        this.mGeoDetectionRunInBackgroundEnabled = builder.mGeoDetectionRunInBackgroundEnabled;
        this.mEnhancedMetricsCollectionEnabled = builder.mEnhancedMetricsCollectionEnabled;
        this.mAutoDetectionEnabledSetting = builder.mAutoDetectionEnabledSetting;
        Integer num = builder.mUserId;
        Objects.requireNonNull(num, "userId must be set");
        this.mUserId = num.intValue();
        this.mUserConfigAllowed = builder.mUserConfigAllowed;
        this.mLocationEnabledSetting = builder.mLocationEnabledSetting;
        this.mGeoDetectionEnabledSetting = builder.mGeoDetectionEnabledSetting;
        this.mGeoLocationFbEnabledSetting = builder.mGeoLocationFbEnabledSetting;
    }

    public final TimeZoneCapabilities asCapabilities() {
        TimeZoneCapabilities.Builder builder =
                new TimeZoneCapabilities.Builder(UserHandle.of(this.mUserId));
        boolean isAutoDetectionSupported = isAutoDetectionSupported();
        boolean z = this.mUserConfigAllowed;
        int i = 10;
        builder.setConfigureAutoDetectionEnabledCapability(
                !isAutoDetectionSupported ? 10 : !z ? 20 : 40);
        boolean z2 = this.mLocationEnabledSetting;
        builder.setUseLocationEnabled(z2);
        boolean z3 = this.mGeoDetectionSupported;
        boolean z4 = this.mAutoDetectionEnabledSetting;
        if (z3 && this.mTelephonyDetectionSupported) {
            i = (z4 && z2) ? 40 : 30;
        }
        builder.setConfigureGeoDetectionEnabledCapability(i);
        builder.setSetManualTimeZoneCapability(
                z ? (isAutoDetectionSupported() && z4) ? 30 : 40 : 20);
        return builder.build();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ConfigurationInternal.class != obj.getClass()) {
            return false;
        }
        ConfigurationInternal configurationInternal = (ConfigurationInternal) obj;
        return this.mUserId == configurationInternal.mUserId
                && this.mUserConfigAllowed == configurationInternal.mUserConfigAllowed
                && this.mTelephonyDetectionSupported
                        == configurationInternal.mTelephonyDetectionSupported
                && this.mGeoDetectionSupported == configurationInternal.mGeoDetectionSupported
                && this.mTelephonyFallbackSupported
                        == configurationInternal.mTelephonyFallbackSupported
                && this.mGeoDetectionRunInBackgroundEnabled
                        == configurationInternal.mGeoDetectionRunInBackgroundEnabled
                && this.mEnhancedMetricsCollectionEnabled
                        == configurationInternal.mEnhancedMetricsCollectionEnabled
                && this.mAutoDetectionEnabledSetting
                        == configurationInternal.mAutoDetectionEnabledSetting
                && this.mLocationEnabledSetting == configurationInternal.mLocationEnabledSetting
                && this.mGeoLocationFbEnabledSetting
                        == configurationInternal.mGeoLocationFbEnabledSetting
                && this.mGeoDetectionEnabledSetting
                        == configurationInternal.mGeoDetectionEnabledSetting;
    }

    public final int getDetectionMode() {
        boolean z = true;
        if (!isAutoDetectionSupported() || !this.mAutoDetectionEnabledSetting) {
            return 1;
        }
        boolean z2 = this.mGeoDetectionSupported;
        boolean z3 = this.mTelephonyDetectionSupported;
        if (!z2 || !this.mLocationEnabledSetting) {
            z = false;
        } else if (z3) {
            z = this.mGeoDetectionEnabledSetting;
        }
        return z ? (z3 && this.mGeoLocationFbEnabledSetting) ? 3 : 2 : z3 ? 3 : 0;
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mUserId),
                Boolean.valueOf(this.mUserConfigAllowed),
                Boolean.valueOf(this.mTelephonyDetectionSupported),
                Boolean.valueOf(this.mGeoDetectionSupported),
                Boolean.valueOf(this.mTelephonyFallbackSupported),
                Boolean.valueOf(this.mGeoDetectionRunInBackgroundEnabled),
                Boolean.valueOf(this.mEnhancedMetricsCollectionEnabled),
                Boolean.valueOf(this.mGeoLocationFbEnabledSetting),
                Boolean.valueOf(this.mAutoDetectionEnabledSetting),
                Boolean.valueOf(this.mLocationEnabledSetting),
                Boolean.valueOf(this.mGeoDetectionEnabledSetting));
    }

    public final boolean isAutoDetectionSupported() {
        return this.mTelephonyDetectionSupported || this.mGeoDetectionSupported;
    }

    public final boolean isGeoDetectionExecutionEnabled() {
        return getDetectionMode() == 2
                || (this.mGeoDetectionSupported
                        && this.mLocationEnabledSetting
                        && this.mAutoDetectionEnabledSetting
                        && this.mGeoDetectionRunInBackgroundEnabled);
    }

    public final String toString() {
        return "ConfigurationInternal{mUserId="
                + this.mUserId
                + ", mUserConfigAllowed="
                + this.mUserConfigAllowed
                + ", mTelephonyDetectionSupported="
                + this.mTelephonyDetectionSupported
                + ", mGeoDetectionSupported="
                + this.mGeoDetectionSupported
                + ", mTelephonyFallbackSupported="
                + this.mTelephonyFallbackSupported
                + ", mGeoDetectionRunInBackgroundEnabled="
                + this.mGeoDetectionRunInBackgroundEnabled
                + ", mEnhancedMetricsCollectionEnabled="
                + this.mEnhancedMetricsCollectionEnabled
                + ", mAutoDetectionEnabledSetting="
                + this.mAutoDetectionEnabledSetting
                + ", mLocationEnabledSetting="
                + this.mLocationEnabledSetting
                + ", mGeoDetectionEnabledSetting="
                + this.mGeoDetectionEnabledSetting
                + ", mGeoLocationFbEnabledSetting="
                + this.mGeoLocationFbEnabledSetting
                + '}';
    }
}
