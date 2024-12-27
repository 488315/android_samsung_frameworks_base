package com.android.server.timedetector;

import android.app.time.TimeCapabilities;
import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.os.UserHandle;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ConfigurationInternal {
    public final boolean mAutoDetectionEnabledSetting;
    public final boolean mAutoDetectionSupported;
    public final Instant mAutoSuggestionLowerBound;
    public final Instant mManualSuggestionLowerBound;
    public final int[] mOriginPriorities;
    public final Instant mSuggestionUpperBound;
    public final int mSystemClockConfidenceThresholdMillis;
    public final int mSystemClockUpdateThresholdMillis;
    public final boolean mUserConfigAllowed;
    public final int mUserId;

    public final class Builder {
        public boolean mAutoDetectionEnabledSetting;
        public boolean mAutoDetectionSupported;
        public Instant mAutoSuggestionLowerBound;
        public Instant mManualSuggestionLowerBound;
        public int[] mOriginPriorities;
        public Instant mSuggestionUpperBound;
        public int mSystemClockConfidenceThresholdMillis;
        public int mSystemClockUpdateThresholdMillis;
        public boolean mUserConfigAllowed;
        public final int mUserId;

        public Builder(int i) {
            this.mUserId = i;
        }
    }

    public ConfigurationInternal(Builder builder) {
        this.mAutoDetectionSupported = builder.mAutoDetectionSupported;
        this.mSystemClockUpdateThresholdMillis = builder.mSystemClockUpdateThresholdMillis;
        this.mSystemClockConfidenceThresholdMillis = builder.mSystemClockConfidenceThresholdMillis;
        Instant instant = builder.mAutoSuggestionLowerBound;
        Objects.requireNonNull(instant);
        this.mAutoSuggestionLowerBound = instant;
        Instant instant2 = builder.mManualSuggestionLowerBound;
        Objects.requireNonNull(instant2);
        this.mManualSuggestionLowerBound = instant2;
        Instant instant3 = builder.mSuggestionUpperBound;
        Objects.requireNonNull(instant3);
        this.mSuggestionUpperBound = instant3;
        int[] iArr = builder.mOriginPriorities;
        Objects.requireNonNull(iArr);
        this.mOriginPriorities = iArr;
        this.mAutoDetectionEnabledSetting = builder.mAutoDetectionEnabledSetting;
        this.mUserId = builder.mUserId;
        this.mUserConfigAllowed = builder.mUserConfigAllowed;
    }

    public final TimeCapabilitiesAndConfig createCapabilitiesAndConfig() {
        TimeCapabilities.Builder builder =
                new TimeCapabilities.Builder(UserHandle.of(this.mUserId));
        boolean z = this.mUserConfigAllowed;
        int i = 40;
        builder.setConfigureAutoDetectionEnabledCapability(
                !this.mAutoDetectionSupported ? 10 : !z ? 20 : 40);
        if (!z) {
            i = 20;
        } else if (getAutoDetectionEnabledBehavior()) {
            i = 30;
        }
        builder.setSetManualTimeCapability(i);
        return new TimeCapabilitiesAndConfig(
                builder.build(),
                new TimeConfiguration.Builder()
                        .setAutoDetectionEnabled(this.mAutoDetectionEnabledSetting)
                        .build());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConfigurationInternal)) {
            return false;
        }
        ConfigurationInternal configurationInternal = (ConfigurationInternal) obj;
        return this.mAutoDetectionSupported == configurationInternal.mAutoDetectionSupported
                && this.mAutoDetectionEnabledSetting
                        == configurationInternal.mAutoDetectionEnabledSetting
                && this.mUserId == configurationInternal.mUserId
                && this.mUserConfigAllowed == configurationInternal.mUserConfigAllowed
                && this.mSystemClockUpdateThresholdMillis
                        == configurationInternal.mSystemClockUpdateThresholdMillis
                && this.mSystemClockConfidenceThresholdMillis
                        == configurationInternal.mSystemClockConfidenceThresholdMillis
                && this.mAutoSuggestionLowerBound.equals(
                        configurationInternal.mAutoSuggestionLowerBound)
                && this.mManualSuggestionLowerBound.equals(
                        configurationInternal.mManualSuggestionLowerBound)
                && this.mSuggestionUpperBound.equals(configurationInternal.mSuggestionUpperBound)
                && Arrays.equals(this.mOriginPriorities, configurationInternal.mOriginPriorities);
    }

    public final boolean getAutoDetectionEnabledBehavior() {
        return this.mAutoDetectionSupported && this.mAutoDetectionEnabledSetting;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.mOriginPriorities)
                + (Objects.hash(
                                Boolean.valueOf(this.mAutoDetectionSupported),
                                Boolean.valueOf(this.mAutoDetectionEnabledSetting),
                                Integer.valueOf(this.mUserId),
                                Boolean.valueOf(this.mUserConfigAllowed),
                                Integer.valueOf(this.mSystemClockUpdateThresholdMillis),
                                Integer.valueOf(this.mSystemClockConfidenceThresholdMillis),
                                this.mAutoSuggestionLowerBound,
                                this.mManualSuggestionLowerBound,
                                this.mSuggestionUpperBound)
                        * 31);
    }

    public final String toString() {
        return "ConfigurationInternal{mAutoDetectionSupported="
                + this.mAutoDetectionSupported
                + ", mSystemClockUpdateThresholdMillis="
                + this.mSystemClockUpdateThresholdMillis
                + ", mSystemClockConfidenceThresholdMillis="
                + this.mSystemClockConfidenceThresholdMillis
                + ", mAutoSuggestionLowerBound="
                + this.mAutoSuggestionLowerBound
                + "("
                + this.mAutoSuggestionLowerBound.toEpochMilli()
                + "), mManualSuggestionLowerBound="
                + this.mManualSuggestionLowerBound
                + "("
                + this.mManualSuggestionLowerBound.toEpochMilli()
                + "), mSuggestionUpperBound="
                + this.mSuggestionUpperBound
                + "("
                + this.mSuggestionUpperBound.toEpochMilli()
                + "), mOriginPriorities="
                + ((String)
                        Arrays.stream(this.mOriginPriorities)
                                .mapToObj(new ConfigurationInternal$$ExternalSyntheticLambda0())
                                .collect(Collectors.joining(",", "[", "]")))
                + ", mAutoDetectionEnabled="
                + this.mAutoDetectionEnabledSetting
                + ", mUserId="
                + this.mUserId
                + ", mUserConfigAllowed="
                + this.mUserConfigAllowed
                + '}';
    }
}
