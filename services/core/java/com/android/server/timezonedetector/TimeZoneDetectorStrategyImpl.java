package com.android.server.timezonedetector;

import android.app.ActivityManagerInternal;
import android.app.time.LocationTimeZoneAlgorithmStatus;
import android.app.time.TelephonyTimeZoneAlgorithmStatus;
import android.app.time.TimeZoneCapabilities;
import android.app.time.TimeZoneConfiguration;
import android.app.time.TimeZoneDetectorStatus;
import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.app.timezonedetector.TelephonyTimeZoneSuggestion;
import android.os.TimestampedValue;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.TimeUtils;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda0;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeZoneDetectorStrategyImpl implements TimeZoneDetectorStrategy {
    public static final int TELEPHONY_SCORE_HIGH = 3;
    public static final int TELEPHONY_SCORE_HIGHEST = 4;
    public static final int TELEPHONY_SCORE_LOW = 1;
    public static final int TELEPHONY_SCORE_MEDIUM = 2;
    public static final int TELEPHONY_SCORE_NONE = 0;
    public static final int TELEPHONY_SCORE_USAGE_THRESHOLD = 2;
    public ConfigurationInternal mCurrentConfigurationInternal;
    public TimeZoneDetectorStatus mDetectorStatus;
    public final Environment mEnvironment;
    public String mLastTelephonyTimezoneIso;
    public final ServiceConfigAccessor mServiceConfigAccessor;
    public TimestampedValue mTelephonyTimeZoneFallbackEnabled;
    public final ArrayMapWithHistory mTelephonySuggestionsBySlotIndex = new ArrayMapWithHistory();
    public final ReferenceWithHistory mLatestLocationAlgorithmEvent = new ReferenceWithHistory(10);
    public final ReferenceWithHistory mLatestManualSuggestion = new ReferenceWithHistory(10);
    public final List mStateChangeListeners = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Environment {
        void addDebugLogEntry(String str);

        void dumpDebugLog(PrintWriter printWriter);

        long elapsedRealtimeMillis();

        String getDeviceTimeZone();

        int getDeviceTimeZoneConfidence();

        void runAsync(
                TimeDetectorStrategyImpl$$ExternalSyntheticLambda0
                        timeDetectorStrategyImpl$$ExternalSyntheticLambda0);

        void setDeviceTimeZoneAndConfidence(int i, String str, String str2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class QualifiedTelephonyTimeZoneSuggestion {
        public final int score;
        public final TelephonyTimeZoneSuggestion suggestion;

        public QualifiedTelephonyTimeZoneSuggestion(
                TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion, int i) {
            this.suggestion = telephonyTimeZoneSuggestion;
            this.score = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || QualifiedTelephonyTimeZoneSuggestion.class != obj.getClass()) {
                return false;
            }
            QualifiedTelephonyTimeZoneSuggestion qualifiedTelephonyTimeZoneSuggestion =
                    (QualifiedTelephonyTimeZoneSuggestion) obj;
            return this.score == qualifiedTelephonyTimeZoneSuggestion.score
                    && this.suggestion.equals(qualifiedTelephonyTimeZoneSuggestion.suggestion);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.score), this.suggestion);
        }

        public final String toString() {
            StringBuilder sb =
                    new StringBuilder("QualifiedTelephonyTimeZoneSuggestion{suggestion=");
            sb.append(this.suggestion);
            sb.append(", score=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.score, '}');
        }
    }

    public TimeZoneDetectorStrategyImpl(
            ServiceConfigAccessor serviceConfigAccessor, Environment environment) {
        Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        this.mTelephonyTimeZoneFallbackEnabled =
                new TimestampedValue(environment.elapsedRealtimeMillis(), Boolean.TRUE);
        synchronized (this) {
            try {
                StateChangeListener stateChangeListener =
                        new StateChangeListener() { // from class:
                                                    // com.android.server.timezonedetector.TimeZoneDetectorStrategyImpl$$ExternalSyntheticLambda0
                            @Override // com.android.server.timezonedetector.StateChangeListener
                            public final void onChange() {
                                TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl =
                                        TimeZoneDetectorStrategyImpl.this;
                                synchronized (timeZoneDetectorStrategyImpl) {
                                    timeZoneDetectorStrategyImpl
                                            .updateCurrentConfigurationInternalIfRequired$1(
                                                    "handleConfigurationInternalMaybeChanged:");
                                }
                            }
                        };
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl) serviceConfigAccessor;
                synchronized (serviceConfigAccessorImpl) {
                    ((ArrayList) serviceConfigAccessorImpl.mConfigurationInternalListeners)
                            .add(stateChangeListener);
                }
                this.mLastTelephonyTimezoneIso = "";
                updateCurrentConfigurationInternalIfRequired$1("TimeZoneDetectorStrategyImpl:");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized boolean confirmTimeZone(String str) {
        Objects.requireNonNull(str);
        String deviceTimeZone = this.mEnvironment.getDeviceTimeZone();
        if (!deviceTimeZone.equals(str)) {
            return false;
        }
        if (this.mEnvironment.getDeviceTimeZoneConfidence() < 100) {
            this.mEnvironment.setDeviceTimeZoneAndConfidence(
                    100, deviceTimeZone, "confirmTimeZone: timeZoneId=".concat(str));
        }
        return true;
    }

    public final void disableTelephonyFallbackIfNeeded() {
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion;
        LocationAlgorithmEvent locationAlgorithmEvent =
                (LocationAlgorithmEvent) this.mLatestLocationAlgorithmEvent.get();
        if (locationAlgorithmEvent == null
                || (geolocationTimeZoneSuggestion = locationAlgorithmEvent.mSuggestion) == null
                || geolocationTimeZoneSuggestion.mZoneIds == null
                || !((Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue()) {
            return;
        }
        if (geolocationTimeZoneSuggestion.mEffectiveFromElapsedMillis
                > this.mTelephonyTimeZoneFallbackEnabled.getReferenceTimeMillis()) {
            Environment environment = this.mEnvironment;
            this.mTelephonyTimeZoneFallbackEnabled =
                    new TimestampedValue(environment.elapsedRealtimeMillis(), Boolean.FALSE);
            environment.addDebugLogEntry(
                    "disableTelephonyFallbackIfNeeded: mTelephonyTimeZoneFallbackEnabled="
                            + this.mTelephonyTimeZoneFallbackEnabled);
        }
    }

    public final void doAutoTimeZoneDetection(
            ConfigurationInternal configurationInternal, String str) {
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion;
        List list;
        int detectionMode = configurationInternal.getDetectionMode();
        if (detectionMode == 0) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    detectionMode,
                    "Unknown detection mode: ",
                    ", is location off?",
                    "time_zone_detector");
            return;
        }
        if (detectionMode != 1) {
            if (detectionMode == 2) {
                if (!doGeolocationTimeZoneDetection(str)
                        && ((Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue())
                                .booleanValue()
                        && configurationInternal.mTelephonyFallbackSupported) {
                    doTelephonyTimeZoneDetection(str + ", telephony fallback mode");
                    return;
                }
                return;
            }
            if (detectionMode != 3) {
                Slog.wtf("time_zone_detector", "Unknown detection mode: " + detectionMode);
                return;
            }
            if (configurationInternal.mGeoLocationFbEnabledSetting) {
                QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion =
                        findBestTelephonySuggestion();
                if (findBestTelephonySuggestion == null
                        || findBestTelephonySuggestion.score < 2
                        || findBestTelephonySuggestion.suggestion.getZoneId() == null) {
                    Slog.d(
                            "time_zone_detector",
                            "isTelephonyTimeZoneCertain - telephony uncertain");
                    QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion2 =
                            findBestTelephonySuggestion();
                    String str2 = null;
                    String str3 =
                            findBestTelephonySuggestion2 != null
                                    ? findBestTelephonySuggestion2.suggestion.mCountryIso
                                    : null;
                    StringBuilder m =
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "useGeoLocationTimezoneFallbackIfNeeded - currentIso:",
                                    str3,
                                    " ,mLastTelephonyTimezoneIso:");
                    m.append(this.mLastTelephonyTimezoneIso);
                    Slog.d("time_zone_detector", m.toString());
                    if (!TextUtils.isEmpty(str3)
                            && !TextUtils.equals(str3, this.mLastTelephonyTimezoneIso)) {
                        LocationAlgorithmEvent locationAlgorithmEvent =
                                (LocationAlgorithmEvent) this.mLatestLocationAlgorithmEvent.get();
                        if (locationAlgorithmEvent != null
                                && (geolocationTimeZoneSuggestion =
                                                locationAlgorithmEvent.mSuggestion)
                                        != null
                                && (list = geolocationTimeZoneSuggestion.mZoneIds) != null
                                && !list.isEmpty()) {
                            str2 = this.mEnvironment.getDeviceTimeZone();
                            if (!list.contains(str2)) {
                                str2 = (String) list.get(0);
                            }
                        }
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                "useGeoLocationTimezoneFallbackIfNeeded - geoTimeZoneId:",
                                str2,
                                "time_zone_detector");
                        if (str2 != null) {
                            List timeZoneIdsForCountryCode =
                                    TimeUtils.getTimeZoneIdsForCountryCode(str3);
                            if (timeZoneIdsForCountryCode == null) {
                                timeZoneIdsForCountryCode = new ArrayList();
                            }
                            Slog.d(
                                    "time_zone_detector",
                                    "getCountryTimeZoneList - timeZoneList:"
                                            + timeZoneIdsForCountryCode);
                            if (timeZoneIdsForCountryCode.contains(str2)) {
                                Slog.d(
                                        "time_zone_detector",
                                        "doAutoTimeZoneDetection - use GEO case, not Telephony");
                                if (doGeolocationTimeZoneDetection(
                                        str + ", geolocation fallback mode")) {
                                    return;
                                }
                                Slog.d(
                                        "time_zone_detector",
                                        "doAutoTimeZoneDetection - geolocation fallback failed");
                                return;
                            }
                        }
                    }
                } else {
                    Slog.d(
                            "time_zone_detector",
                            "useGeoLocationTimezoneFallbackIfNeeded - TelephonyTimeZoneCertain,"
                                + " return false");
                }
            }
            doTelephonyTimeZoneDetection(str);
        }
    }

    public final boolean doGeolocationTimeZoneDetection(String str) {
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion;
        List list;
        LocationAlgorithmEvent locationAlgorithmEvent =
                (LocationAlgorithmEvent) this.mLatestLocationAlgorithmEvent.get();
        if (locationAlgorithmEvent == null
                || (geolocationTimeZoneSuggestion = locationAlgorithmEvent.mSuggestion) == null
                || (list = geolocationTimeZoneSuggestion.mZoneIds) == null) {
            return false;
        }
        if (list.isEmpty()) {
            return true;
        }
        String deviceTimeZone = this.mEnvironment.getDeviceTimeZone();
        if (!list.contains(deviceTimeZone)) {
            deviceTimeZone = (String) list.get(0);
        }
        setDeviceTimeZoneIfRequired(deviceTimeZone, str);
        return true;
    }

    public final void doTelephonyTimeZoneDetection(String str) {
        QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion =
                findBestTelephonySuggestion();
        if (findBestTelephonySuggestion != null && findBestTelephonySuggestion.score >= 2) {
            String zoneId = findBestTelephonySuggestion.suggestion.getZoneId();
            if (zoneId == null) {
                Slog.w(
                        "time_zone_detector",
                        "Empty zone suggestion scored higher than expected. This is an error:"
                            + " bestTelephonySuggestion="
                                + findBestTelephonySuggestion
                                + ", detectionReason="
                                + str);
                return;
            }
            setDeviceTimeZoneIfRequired(
                    zoneId,
                    "Found good suggestion: bestTelephonySuggestion="
                            + findBestTelephonySuggestion
                            + ", detectionReason="
                            + str);
            this.mLastTelephonyTimezoneIso = findBestTelephonySuggestion.suggestion.mCountryIso;
        }
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final synchronized void dump(
            IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        indentingPrintWriter.println("TimeZoneDetectorStrategy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(
                "mCurrentConfigurationInternal=" + this.mCurrentConfigurationInternal);
        indentingPrintWriter.println("mDetectorStatus=" + this.mDetectorStatus);
        indentingPrintWriter.println(
                "[Capabilities=" + this.mCurrentConfigurationInternal.asCapabilities() + "]");
        StringBuilder sb = new StringBuilder("mEnvironment.getDeviceTimeZone()=");
        sb.append(this.mEnvironment.getDeviceTimeZone());
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println(
                "mEnvironment.getDeviceTimeZoneConfidence()="
                        + this.mEnvironment.getDeviceTimeZoneConfidence());
        indentingPrintWriter.println("Misc state:");
        indentingPrintWriter.increaseIndent();
        StringBuilder sb2 = new StringBuilder("mTelephonyTimeZoneFallbackEnabled=");
        TimestampedValue timestampedValue = this.mTelephonyTimeZoneFallbackEnabled;
        sb2.append(
                timestampedValue.getValue()
                        + " @ "
                        + Duration.ofMillis(timestampedValue.getReferenceTimeMillis()));
        indentingPrintWriter.println(sb2.toString());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Time zone debug log:");
        indentingPrintWriter.increaseIndent();
        this.mEnvironment.dumpDebugLog(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Manual suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLatestManualSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Location algorithm event history:");
        indentingPrintWriter.increaseIndent();
        this.mLatestLocationAlgorithmEvent.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Telephony suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mTelephonySuggestionsBySlotIndex.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Geolocation fallback state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mLastTelephonyTimezoneIso=" + this.mLastTelephonyTimezoneIso);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final synchronized void enableTelephonyTimeZoneFallback(String str) {
        if (!((Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue()) {
            ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
            this.mTelephonyTimeZoneFallbackEnabled =
                    new TimestampedValue(this.mEnvironment.elapsedRealtimeMillis(), Boolean.TRUE);
            this.mEnvironment.addDebugLogEntry(
                    "enableTelephonyTimeZoneFallback:  reason="
                            + str
                            + ", currentUserConfig="
                            + configurationInternal
                            + ", mTelephonyTimeZoneFallbackEnabled="
                            + this.mTelephonyTimeZoneFallbackEnabled);
            disableTelephonyFallbackIfNeeded();
            if (configurationInternal.mTelephonyFallbackSupported) {
                doAutoTimeZoneDetection(configurationInternal, str);
            }
        }
    }

    public final QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion() {
        int i;
        int i2;
        QualifiedTelephonyTimeZoneSuggestion qualifiedTelephonyTimeZoneSuggestion = null;
        int i3 = 0;
        while (true) {
            ArrayMapWithHistory arrayMapWithHistory = this.mTelephonySuggestionsBySlotIndex;
            ArrayMap arrayMap = arrayMapWithHistory.mMap;
            if (i3 >= (arrayMap == null ? 0 : arrayMap.size())) {
                return qualifiedTelephonyTimeZoneSuggestion;
            }
            QualifiedTelephonyTimeZoneSuggestion qualifiedTelephonyTimeZoneSuggestion2 =
                    (QualifiedTelephonyTimeZoneSuggestion) arrayMapWithHistory.valueAt(i3);
            if (qualifiedTelephonyTimeZoneSuggestion2 != null
                    && (qualifiedTelephonyTimeZoneSuggestion == null
                            || (i = qualifiedTelephonyTimeZoneSuggestion2.score)
                                    > (i2 = qualifiedTelephonyTimeZoneSuggestion.score)
                            || (i == i2
                                    && qualifiedTelephonyTimeZoneSuggestion2.suggestion
                                                    .getSlotIndex()
                                            < qualifiedTelephonyTimeZoneSuggestion.suggestion
                                                    .getSlotIndex()))) {
                qualifiedTelephonyTimeZoneSuggestion = qualifiedTelephonyTimeZoneSuggestion2;
            }
            i3++;
        }
    }

    public synchronized QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestionForTests() {
        return findBestTelephonySuggestion();
    }

    public final synchronized MetricsTimeZoneDetectorState generateMetricsState() {
        QualifiedTelephonyTimeZoneSuggestion findBestTelephonySuggestion;
        findBestTelephonySuggestion = findBestTelephonySuggestion();
        return MetricsTimeZoneDetectorState.create(
                new OrdinalGenerator(new TimeZoneCanonicalizer()),
                this.mCurrentConfigurationInternal,
                this.mEnvironment.getDeviceTimeZone(),
                getLatestManualSuggestion(),
                findBestTelephonySuggestion == null ? null : findBestTelephonySuggestion.suggestion,
                getLatestLocationAlgorithmEvent());
    }

    public synchronized ConfigurationInternal getCachedCapabilitiesAndConfigForTests() {
        return this.mCurrentConfigurationInternal;
    }

    public synchronized TimeZoneDetectorStatus getCachedDetectorStatusForTests() {
        return this.mDetectorStatus;
    }

    public synchronized LocationAlgorithmEvent getLatestLocationAlgorithmEvent() {
        return (LocationAlgorithmEvent) this.mLatestLocationAlgorithmEvent.get();
    }

    public synchronized ManualTimeZoneSuggestion getLatestManualSuggestion() {
        return (ManualTimeZoneSuggestion) this.mLatestManualSuggestion.get();
    }

    public synchronized QualifiedTelephonyTimeZoneSuggestion getLatestTelephonySuggestion(int i) {
        return (QualifiedTelephonyTimeZoneSuggestion)
                this.mTelephonySuggestionsBySlotIndex.get(Integer.valueOf(i));
    }

    public final synchronized void handleLocationAlgorithmEvent(
            LocationAlgorithmEvent locationAlgorithmEvent) {
        try {
            ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
            Objects.requireNonNull(locationAlgorithmEvent);
            this.mLatestLocationAlgorithmEvent.set(locationAlgorithmEvent);
            if (updateDetectorStatus()) {
                notifyStateChangeListenersAsynchronously$1();
            }
            if (locationAlgorithmEvent.mAlgorithmStatus.couldEnableTelephonyFallback()) {
                enableTelephonyTimeZoneFallback(
                        "handleLocationAlgorithmEvent(), event=" + locationAlgorithmEvent);
            } else {
                disableTelephonyFallbackIfNeeded();
            }
            doAutoTimeZoneDetection(
                    configurationInternal,
                    "New location algorithm event received. event=" + locationAlgorithmEvent);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized boolean isTelephonyFallbackEnabledForTests() {
        return ((Boolean) this.mTelephonyTimeZoneFallbackEnabled.getValue()).booleanValue();
    }

    public final void notifyStateChangeListenersAsynchronously$1() {
        Iterator it = ((ArrayList) this.mStateChangeListeners).iterator();
        while (it.hasNext()) {
            StateChangeListener stateChangeListener = (StateChangeListener) it.next();
            Objects.requireNonNull(stateChangeListener);
            this.mEnvironment.runAsync(
                    new TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(stateChangeListener));
        }
    }

    public final void setDeviceTimeZoneIfRequired(String str, String str2) {
        Environment environment = this.mEnvironment;
        String deviceTimeZone = environment.getDeviceTimeZone();
        int deviceTimeZoneConfidence = environment.getDeviceTimeZoneConfidence();
        if (!str.equals(deviceTimeZone) || 100 > deviceTimeZoneConfidence) {
            environment.setDeviceTimeZoneAndConfidence(
                    100,
                    str,
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Set device time zone or higher confidence: newZoneId=",
                            str,
                            ", cause=",
                            str2,
                            ", newConfidence=100"));
        }
    }

    public final synchronized boolean suggestManualTimeZone(
            int i, ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (configurationInternal.mUserId != i) {
            Slog.w(
                    "time_zone_detector",
                    "Manual suggestion received but user != current user, userId="
                            + i
                            + " suggestion="
                            + manualTimeZoneSuggestion);
            return false;
        }
        Objects.requireNonNull(manualTimeZoneSuggestion);
        String zoneId = manualTimeZoneSuggestion.getZoneId();
        String str = "Manual time suggestion received: suggestion=" + manualTimeZoneSuggestion;
        TimeZoneCapabilities asCapabilities = configurationInternal.asCapabilities();
        if (asCapabilities.getSetManualTimeZoneCapability() == 40) {
            this.mLatestManualSuggestion.set(manualTimeZoneSuggestion);
            setDeviceTimeZoneIfRequired(zoneId, str);
            return true;
        }
        Slog.i(
                "time_zone_detector",
                "User does not have the capability needed to set the time zone manually:"
                    + " capabilities="
                        + asCapabilities
                        + ", timeZoneId="
                        + zoneId
                        + ", cause="
                        + str);
        return false;
    }

    public final synchronized boolean updateConfiguration(
            int i, TimeZoneConfiguration timeZoneConfiguration) {
        boolean z;
        ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                (ServiceConfigAccessorImpl) this.mServiceConfigAccessor;
        synchronized (serviceConfigAccessorImpl) {
            Objects.requireNonNull(timeZoneConfiguration);
            ConfigurationInternal configurationInternal =
                    serviceConfigAccessorImpl.getConfigurationInternal(i);
            TimeZoneConfiguration tryApplyConfigChanges =
                    configurationInternal
                            .asCapabilities()
                            .tryApplyConfigChanges(
                                    new TimeZoneConfiguration.Builder()
                                            .setAutoDetectionEnabled(
                                                    configurationInternal
                                                            .mAutoDetectionEnabledSetting)
                                            .setGeoDetectionEnabled(
                                                    configurationInternal
                                                            .mGeoDetectionEnabledSetting)
                                            .build(),
                                    timeZoneConfiguration);
            if (tryApplyConfigChanges == null) {
                z = false;
            } else {
                serviceConfigAccessorImpl.storeConfiguration(
                        i, timeZoneConfiguration, tryApplyConfigChanges);
                z = true;
            }
        }
        if (z) {
            updateCurrentConfigurationInternalIfRequired$1(
                    "updateConfiguration: userId="
                            + i
                            + ", configuration="
                            + timeZoneConfiguration
                            + ", bypassUserPolicyChecks=false");
        }
        return z;
    }

    public final void updateCurrentConfigurationInternalIfRequired$1(String str) {
        ConfigurationInternal configurationInternal;
        ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                (ServiceConfigAccessorImpl) this.mServiceConfigAccessor;
        synchronized (serviceConfigAccessorImpl) {
            configurationInternal =
                    serviceConfigAccessorImpl.getConfigurationInternal(
                            ((ActivityManagerInternal)
                                            LocalServices.getService(ActivityManagerInternal.class))
                                    .getCurrentUserId());
        }
        ConfigurationInternal configurationInternal2 = this.mCurrentConfigurationInternal;
        if (configurationInternal.equals(configurationInternal2)) {
            return;
        }
        this.mCurrentConfigurationInternal = configurationInternal;
        String str2 =
                str
                        + " [oldConfiguration="
                        + configurationInternal2
                        + ", newConfiguration="
                        + configurationInternal
                        + "]";
        this.mEnvironment.addDebugLogEntry(str2);
        updateDetectorStatus();
        notifyStateChangeListenersAsynchronously$1();
        doAutoTimeZoneDetection(this.mCurrentConfigurationInternal, str2);
    }

    public final boolean updateDetectorStatus() {
        ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        LocationAlgorithmEvent locationAlgorithmEvent =
                (LocationAlgorithmEvent) this.mLatestLocationAlgorithmEvent.get();
        TimeZoneDetectorStatus timeZoneDetectorStatus =
                new TimeZoneDetectorStatus(
                        !configurationInternal.isAutoDetectionSupported()
                                ? 1
                                : (configurationInternal.isAutoDetectionSupported()
                                                && configurationInternal
                                                        .mAutoDetectionEnabledSetting)
                                        ? 3
                                        : 2,
                        new TelephonyTimeZoneAlgorithmStatus(
                                configurationInternal.mTelephonyDetectionSupported ? 3 : 1),
                        locationAlgorithmEvent != null
                                ? locationAlgorithmEvent.mAlgorithmStatus
                                : !configurationInternal.mGeoDetectionSupported
                                        ? LocationTimeZoneAlgorithmStatus.NOT_SUPPORTED
                                        : configurationInternal.isGeoDetectionExecutionEnabled()
                                                ? LocationTimeZoneAlgorithmStatus
                                                        .RUNNING_NOT_REPORTED
                                                : LocationTimeZoneAlgorithmStatus.NOT_RUNNING);
        boolean z = !timeZoneDetectorStatus.equals(this.mDetectorStatus);
        if (z) {
            this.mDetectorStatus = timeZoneDetectorStatus;
        }
        return z;
    }
}
