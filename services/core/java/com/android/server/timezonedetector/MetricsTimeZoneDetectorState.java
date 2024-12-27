package com.android.server.timezonedetector;

import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.app.timezonedetector.TelephonyTimeZoneSuggestion;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class MetricsTimeZoneDetectorState {
    public final ConfigurationInternal mConfigurationInternal;
    public final String mDeviceTimeZoneId;
    public final int mDeviceTimeZoneIdOrdinal;
    public final MetricsTimeZoneSuggestion mLatestGeolocationSuggestion;
    public final MetricsTimeZoneSuggestion mLatestManualSuggestion;
    public final MetricsTimeZoneSuggestion mLatestTelephonySuggestion;

    public final class MetricsTimeZoneSuggestion {
        public final int[] mZoneIdOrdinals;
        public final String[] mZoneIds;

        public MetricsTimeZoneSuggestion(int[] iArr, String[] strArr) {
            this.mZoneIds = strArr;
            this.mZoneIdOrdinals = iArr;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || MetricsTimeZoneSuggestion.class != obj.getClass()) {
                return false;
            }
            MetricsTimeZoneSuggestion metricsTimeZoneSuggestion = (MetricsTimeZoneSuggestion) obj;
            return Arrays.equals(this.mZoneIdOrdinals, metricsTimeZoneSuggestion.mZoneIdOrdinals)
                    && Arrays.equals(this.mZoneIds, metricsTimeZoneSuggestion.mZoneIds);
        }

        public final int hashCode() {
            return Arrays.hashCode(this.mZoneIdOrdinals) + (Arrays.hashCode(this.mZoneIds) * 31);
        }

        public final String toString() {
            return "MetricsTimeZoneSuggestion{mZoneIdOrdinals="
                    + Arrays.toString(this.mZoneIdOrdinals)
                    + ", mZoneIds="
                    + Arrays.toString(this.mZoneIds)
                    + '}';
        }
    }

    public MetricsTimeZoneDetectorState(
            ConfigurationInternal configurationInternal,
            int i,
            String str,
            MetricsTimeZoneSuggestion metricsTimeZoneSuggestion,
            MetricsTimeZoneSuggestion metricsTimeZoneSuggestion2,
            MetricsTimeZoneSuggestion metricsTimeZoneSuggestion3) {
        Objects.requireNonNull(configurationInternal);
        this.mConfigurationInternal = configurationInternal;
        this.mDeviceTimeZoneIdOrdinal = i;
        this.mDeviceTimeZoneId = str;
        this.mLatestManualSuggestion = metricsTimeZoneSuggestion;
        this.mLatestTelephonySuggestion = metricsTimeZoneSuggestion2;
        this.mLatestGeolocationSuggestion = metricsTimeZoneSuggestion3;
    }

    public static MetricsTimeZoneDetectorState create(
            OrdinalGenerator ordinalGenerator,
            ConfigurationInternal configurationInternal,
            String str,
            ManualTimeZoneSuggestion manualTimeZoneSuggestion,
            TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion,
            LocationAlgorithmEvent locationAlgorithmEvent) {
        MetricsTimeZoneSuggestion metricsTimeZoneSuggestion;
        MetricsTimeZoneSuggestion metricsTimeZoneSuggestion2;
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion;
        String[] strArr;
        boolean z = configurationInternal.mEnhancedMetricsCollectionEnabled;
        String str2 = z ? str : null;
        Objects.requireNonNull(str);
        int ordinal = ordinalGenerator.ordinal(str);
        if (manualTimeZoneSuggestion == null) {
            metricsTimeZoneSuggestion = null;
        } else {
            String zoneId = manualTimeZoneSuggestion.getZoneId();
            metricsTimeZoneSuggestion =
                    new MetricsTimeZoneSuggestion(
                            new int[] {ordinalGenerator.ordinal(zoneId)},
                            z ? new String[] {zoneId} : null);
        }
        if (telephonyTimeZoneSuggestion == null) {
            metricsTimeZoneSuggestion2 = null;
        } else {
            String zoneId2 = telephonyTimeZoneSuggestion.getZoneId();
            metricsTimeZoneSuggestion2 =
                    zoneId2 == null
                            ? new MetricsTimeZoneSuggestion(null, null)
                            : new MetricsTimeZoneSuggestion(
                                    new int[] {ordinalGenerator.ordinal(zoneId2)},
                                    z ? new String[] {zoneId2} : null);
        }
        if (locationAlgorithmEvent != null
                && (geolocationTimeZoneSuggestion = locationAlgorithmEvent.mSuggestion) != null) {
            List list = geolocationTimeZoneSuggestion.mZoneIds;
            if (list == null) {
                strArr = new MetricsTimeZoneSuggestion(null, null);
            } else {
                r1 = z ? (String[]) list.toArray(new String[0]) : null;
                int size = list.size();
                int[] iArr = new int[size];
                for (int i = 0; i < size; i++) {
                    iArr[i] = ordinalGenerator.ordinal(list.get(i));
                }
                strArr = new MetricsTimeZoneSuggestion(iArr, r1);
            }
            r1 = strArr;
        }
        return new MetricsTimeZoneDetectorState(
                configurationInternal,
                ordinal,
                str2,
                metricsTimeZoneSuggestion,
                metricsTimeZoneSuggestion2,
                r1);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MetricsTimeZoneDetectorState.class != obj.getClass()) {
            return false;
        }
        MetricsTimeZoneDetectorState metricsTimeZoneDetectorState =
                (MetricsTimeZoneDetectorState) obj;
        return this.mDeviceTimeZoneIdOrdinal
                        == metricsTimeZoneDetectorState.mDeviceTimeZoneIdOrdinal
                && Objects.equals(
                        this.mDeviceTimeZoneId, metricsTimeZoneDetectorState.mDeviceTimeZoneId)
                && this.mConfigurationInternal.equals(
                        metricsTimeZoneDetectorState.mConfigurationInternal)
                && Objects.equals(
                        this.mLatestManualSuggestion,
                        metricsTimeZoneDetectorState.mLatestManualSuggestion)
                && Objects.equals(
                        this.mLatestTelephonySuggestion,
                        metricsTimeZoneDetectorState.mLatestTelephonySuggestion)
                && Objects.equals(
                        this.mLatestGeolocationSuggestion,
                        metricsTimeZoneDetectorState.mLatestGeolocationSuggestion);
    }

    public final int hashCode() {
        return Objects.hash(
                this.mConfigurationInternal,
                Integer.valueOf(this.mDeviceTimeZoneIdOrdinal),
                this.mDeviceTimeZoneId,
                this.mLatestManualSuggestion,
                this.mLatestTelephonySuggestion,
                this.mLatestGeolocationSuggestion);
    }

    public final String toString() {
        return "MetricsTimeZoneDetectorState{mConfigurationInternal="
                + this.mConfigurationInternal
                + ", mDeviceTimeZoneIdOrdinal="
                + this.mDeviceTimeZoneIdOrdinal
                + ", mDeviceTimeZoneId="
                + this.mDeviceTimeZoneId
                + ", mLatestManualSuggestion="
                + this.mLatestManualSuggestion
                + ", mLatestTelephonySuggestion="
                + this.mLatestTelephonySuggestion
                + ", mLatestGeolocationSuggestion="
                + this.mLatestGeolocationSuggestion
                + '}';
    }
}
