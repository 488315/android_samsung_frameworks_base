package com.android.server.timedetector;

import android.app.time.ExternalTimeSuggestion;
import android.app.time.TimeCapabilities;
import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.app.time.TimeState;
import android.app.time.UnixEpochTime;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TelephonyTimeSuggestion;
import android.content.Context;
import android.os.Handler;
import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.server.SystemClockTime;
import com.android.server.clipboard.ClipboardService;
import com.android.server.timezonedetector.ArrayMapWithHistory;
import com.android.server.timezonedetector.ReferenceWithHistory;
import com.android.server.timezonedetector.StateChangeListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class TimeDetectorStrategyImpl implements TimeDetectorStrategy {
    static final long MAX_SUGGESTION_TIME_AGE_MILLIS = 86400000;
    static final int TELEPHONY_BUCKET_SIZE_MILLIS = 3600000;
    public ConfigurationInternal mCurrentConfigurationInternal;
    public final Environment mEnvironment;
    public UnixEpochTime mLastAutoSystemClockTimeSet;
    public final ServiceConfigAccessor mServiceConfigAccessor;
    public final List mStateChangeListeners = new ArrayList();
    public final ArrayMapWithHistory mSuggestionBySlotIndex = new ArrayMapWithHistory(10);
    public final ReferenceWithHistory mLastNetworkSuggestion = new ReferenceWithHistory(10);
    public final ReferenceWithHistory mLastGnssSuggestion = new ReferenceWithHistory(10);
    public final ReferenceWithHistory mLastExternalSuggestion = new ReferenceWithHistory(10);
    public final ArraySet mNetworkTimeUpdateListeners = new ArraySet();

    public interface Environment {
        void acquireWakeLock();

        void addDebugLogEntry(String str);

        void dumpDebugLog(IndentingPrintWriter indentingPrintWriter);

        long elapsedRealtimeMillis();

        void releaseWakeLock();

        void runAsync(Runnable runnable);

        void setSystemClock(long j, int i, String str);

        void setSystemClockConfidence(int i, String str);

        int systemClockConfidence();

        long systemClockMillis();
    }

    public static boolean isOriginAutomatic(int i) {
        return i != 2;
    }

    public static TimeDetectorStrategy create(Context context, Handler handler, ServiceConfigAccessor serviceConfigAccessor) {
        return new TimeDetectorStrategyImpl(new EnvironmentImpl(context, handler), serviceConfigAccessor);
    }

    public TimeDetectorStrategyImpl(Environment environment, ServiceConfigAccessor serviceConfigAccessor) {
        Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        Objects.requireNonNull(serviceConfigAccessor);
        ServiceConfigAccessor serviceConfigAccessor2 = serviceConfigAccessor;
        this.mServiceConfigAccessor = serviceConfigAccessor2;
        synchronized (this) {
            serviceConfigAccessor2.addConfigurationInternalChangeListener(new StateChangeListener() { // from class: com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda1
                @Override // com.android.server.timezonedetector.StateChangeListener
                public final void onChange() {
                    TimeDetectorStrategyImpl.this.handleConfigurationInternalMaybeChanged();
                }
            });
            updateCurrentConfigurationInternalIfRequired("TimeDetectorStrategyImpl:");
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) {
        Slog.d("time_detector", "External suggestion received. currentUserConfig=" + this.mCurrentConfigurationInternal + " suggestion=" + externalTimeSuggestion);
        Objects.requireNonNull(externalTimeSuggestion);
        if (validateAutoSuggestionTime(externalTimeSuggestion.getUnixEpochTime(), externalTimeSuggestion)) {
            this.mLastExternalSuggestion.set(externalTimeSuggestion);
            doAutoTimeDetection("External time suggestion received: suggestion=" + externalTimeSuggestion);
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestGnssTime(GnssTimeSuggestion gnssTimeSuggestion) {
        Slog.d("time_detector", "GNSS suggestion received. currentUserConfig=" + this.mCurrentConfigurationInternal + " suggestion=" + gnssTimeSuggestion);
        Objects.requireNonNull(gnssTimeSuggestion);
        if (validateAutoSuggestionTime(gnssTimeSuggestion.getUnixEpochTime(), gnssTimeSuggestion)) {
            this.mLastGnssSuggestion.set(gnssTimeSuggestion);
            doAutoTimeDetection("GNSS time suggestion received: suggestion=" + gnssTimeSuggestion);
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean suggestManualTime(int i, ManualTimeSuggestion manualTimeSuggestion, boolean z) {
        ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (configurationInternal.getUserId() != i) {
            Slog.w("time_detector", "Manual suggestion received but user != current user, userId=" + i + " suggestion=" + manualTimeSuggestion);
            return false;
        }
        Objects.requireNonNull(manualTimeSuggestion);
        String str = "Manual time suggestion received: suggestion=" + manualTimeSuggestion;
        TimeCapabilities capabilities = configurationInternal.createCapabilitiesAndConfig(z).getCapabilities();
        if (capabilities.getSetManualTimeCapability() != 40) {
            Slog.i("time_detector", "User does not have the capability needed to set the time manually: capabilities=" + capabilities + ", suggestion=" + manualTimeSuggestion + ", cause=" + str);
            return false;
        }
        UnixEpochTime unixEpochTime = manualTimeSuggestion.getUnixEpochTime();
        if (!validateManualSuggestionTime(unixEpochTime, manualTimeSuggestion)) {
            return false;
        }
        return setSystemClockAndConfidenceIfRequired(2, unixEpochTime, str);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestNetworkTime(NetworkTimeSuggestion networkTimeSuggestion) {
        Slog.d("time_detector", "Network suggestion received. currentUserConfig=" + this.mCurrentConfigurationInternal + " suggestion=" + networkTimeSuggestion);
        Objects.requireNonNull(networkTimeSuggestion);
        if (validateAutoSuggestionTime(networkTimeSuggestion.getUnixEpochTime(), networkTimeSuggestion) || isNtpSetByMdm()) {
            NetworkTimeSuggestion networkTimeSuggestion2 = (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
            if (networkTimeSuggestion2 == null || !networkTimeSuggestion2.equals(networkTimeSuggestion)) {
                this.mLastNetworkSuggestion.set(networkTimeSuggestion);
                notifyNetworkTimeUpdateListenersAsynchronously();
            }
            doAutoTimeDetection("New network time suggested. suggestion=" + networkTimeSuggestion);
        }
    }

    public final void notifyNetworkTimeUpdateListenersAsynchronously() {
        Iterator it = this.mNetworkTimeUpdateListeners.iterator();
        while (it.hasNext()) {
            StateChangeListener stateChangeListener = (StateChangeListener) it.next();
            Environment environment = this.mEnvironment;
            Objects.requireNonNull(stateChangeListener);
            environment.runAsync(new TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(stateChangeListener));
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized NetworkTimeSuggestion getLatestNetworkSuggestion() {
        return (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void clearLatestNetworkSuggestion() {
        this.mLastNetworkSuggestion.set(null);
        notifyNetworkTimeUpdateListenersAsynchronously();
        doAutoTimeDetection("Network time cleared");
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized TimeState getTimeState() {
        return new TimeState(new UnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), this.mEnvironment.systemClockMillis()), this.mEnvironment.systemClockConfidence() < 100);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void setTimeState(TimeState timeState) {
        Objects.requireNonNull(timeState);
        int i = timeState.getUserShouldConfirmTime() ? 0 : 100;
        this.mEnvironment.acquireWakeLock();
        try {
            setSystemClockAndConfidenceUnderWakeLock(2, timeState.getUnixEpochTime(), i, "setTimeZoneState()");
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean confirmTime(UnixEpochTime unixEpochTime) {
        boolean isTimeWithinConfidenceThreshold;
        Objects.requireNonNull(unixEpochTime);
        this.mEnvironment.acquireWakeLock();
        try {
            long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
            long systemClockMillis = this.mEnvironment.systemClockMillis();
            isTimeWithinConfidenceThreshold = isTimeWithinConfidenceThreshold(unixEpochTime, elapsedRealtimeMillis, systemClockMillis);
            if (isTimeWithinConfidenceThreshold) {
                int systemClockConfidence = this.mEnvironment.systemClockConfidence();
                if (systemClockConfidence < 100) {
                    String str = "Confirm system clock time. confirmationTime=" + unixEpochTime + " newTimeConfidence=100 currentElapsedRealtimeMillis=" + elapsedRealtimeMillis + " currentSystemClockMillis=" + systemClockMillis + " (old) currentTimeConfidence=" + systemClockConfidence;
                    Slog.d("time_detector", str);
                    this.mEnvironment.setSystemClockConfidence(100, str);
                }
            }
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
        return isTimeWithinConfidenceThreshold;
    }

    public final void notifyStateChangeListenersAsynchronously() {
        for (StateChangeListener stateChangeListener : this.mStateChangeListeners) {
            Environment environment = this.mEnvironment;
            Objects.requireNonNull(stateChangeListener);
            environment.runAsync(new TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(stateChangeListener));
        }
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void addChangeListener(StateChangeListener stateChangeListener) {
        this.mStateChangeListeners.add(stateChangeListener);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized TimeCapabilitiesAndConfig getCapabilitiesAndConfig(int i, boolean z) {
        ConfigurationInternal configurationInternal;
        if (this.mCurrentConfigurationInternal.getUserId() == i) {
            configurationInternal = this.mCurrentConfigurationInternal;
        } else {
            configurationInternal = this.mServiceConfigAccessor.getConfigurationInternal(i);
        }
        return configurationInternal.createCapabilitiesAndConfig(z);
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized boolean updateConfiguration(int i, TimeConfiguration timeConfiguration, boolean z) {
        boolean updateConfiguration;
        updateConfiguration = this.mServiceConfigAccessor.updateConfiguration(i, timeConfiguration, z);
        if (updateConfiguration) {
            updateCurrentConfigurationInternalIfRequired("updateConfiguration: userId=" + i + ", configuration=" + timeConfiguration + ", bypassUserPolicyChecks=" + z);
        }
        return updateConfiguration;
    }

    @Override // com.android.server.timedetector.TimeDetectorStrategy
    public synchronized void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) {
        if (telephonyTimeSuggestion.getUnixEpochTime() == null) {
            return;
        }
        if (validateAutoSuggestionTime(telephonyTimeSuggestion.getUnixEpochTime(), telephonyTimeSuggestion)) {
            if (storeTelephonySuggestion(telephonyTimeSuggestion)) {
                doAutoTimeDetection("New telephony time suggested. suggestion=" + telephonyTimeSuggestion);
            }
        }
    }

    public final synchronized void handleConfigurationInternalMaybeChanged() {
        updateCurrentConfigurationInternalIfRequired("handleConfigurationInternalMaybeChanged:");
    }

    public final void updateCurrentConfigurationInternalIfRequired(String str) {
        ConfigurationInternal currentUserConfigurationInternal = this.mServiceConfigAccessor.getCurrentUserConfigurationInternal();
        ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (currentUserConfigurationInternal.equals(configurationInternal)) {
            return;
        }
        this.mCurrentConfigurationInternal = currentUserConfigurationInternal;
        addDebugLogEntry(str + " [oldConfiguration=" + configurationInternal + ", newConfiguration=" + currentUserConfigurationInternal + "]");
        notifyStateChangeListenersAsynchronously();
        if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
            doAutoTimeDetection("Auto time zone detection config changed.");
        } else {
            this.mLastAutoSystemClockTimeSet = null;
        }
    }

    public final void addDebugLogEntry(String str) {
        Slog.d("time_detector", str);
        this.mEnvironment.addDebugLogEntry(str);
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public synchronized void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        indentingPrintWriter.println("TimeDetectorStrategy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mLastAutoSystemClockTimeSet=" + this.mLastAutoSystemClockTimeSet);
        indentingPrintWriter.println("mCurrentConfigurationInternal=" + this.mCurrentConfigurationInternal);
        indentingPrintWriter.println("[Capabilities=" + this.mCurrentConfigurationInternal.createCapabilitiesAndConfig(false) + "]");
        indentingPrintWriter.println("mEnvironment:");
        indentingPrintWriter.increaseIndent();
        this.mEnvironment.dumpDebugLog(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Time change log:");
        indentingPrintWriter.increaseIndent();
        SystemClockTime.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Telephony suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mSuggestionBySlotIndex.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Network suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastNetworkSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Gnss suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastGnssSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("External suggestion history:");
        indentingPrintWriter.increaseIndent();
        this.mLastExternalSuggestion.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public synchronized ConfigurationInternal getCachedCapabilitiesAndConfigForTests() {
        return this.mCurrentConfigurationInternal;
    }

    public final boolean storeTelephonySuggestion(TelephonyTimeSuggestion telephonyTimeSuggestion) {
        UnixEpochTime unixEpochTime = telephonyTimeSuggestion.getUnixEpochTime();
        int slotIndex = telephonyTimeSuggestion.getSlotIndex();
        TelephonyTimeSuggestion telephonyTimeSuggestion2 = (TelephonyTimeSuggestion) this.mSuggestionBySlotIndex.get(Integer.valueOf(slotIndex));
        if (telephonyTimeSuggestion2 != null) {
            if (telephonyTimeSuggestion2.getUnixEpochTime() == null) {
                Slog.w("time_detector", "Previous suggestion is null or has a null time. previousSuggestion=" + telephonyTimeSuggestion2 + ", suggestion=" + telephonyTimeSuggestion);
                return false;
            }
            long elapsedRealtimeDifference = UnixEpochTime.elapsedRealtimeDifference(unixEpochTime, telephonyTimeSuggestion2.getUnixEpochTime());
            if (elapsedRealtimeDifference < 0) {
                Slog.w("time_detector", "Out of order telephony suggestion received. referenceTimeDifference=" + elapsedRealtimeDifference + " previousSuggestion=" + telephonyTimeSuggestion2 + " suggestion=" + telephonyTimeSuggestion);
                return false;
            }
        }
        this.mSuggestionBySlotIndex.put(Integer.valueOf(slotIndex), telephonyTimeSuggestion);
        return true;
    }

    public final boolean isNtpSetByMdm() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isNtpSetByMDM();
            }
            return false;
        } catch (RemoteException e) {
            Slog.d("time_detector", "Remote Exception: " + e);
            return false;
        }
    }

    public final boolean validateSuggestionCommon(UnixEpochTime unixEpochTime, Object obj) {
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        if (elapsedRealtimeMillis < unixEpochTime.getElapsedRealtimeMillis()) {
            Slog.w("time_detector", "New elapsed realtime is in the future? Ignoring. elapsedRealtimeMillis=" + elapsedRealtimeMillis + ", suggestion=" + obj);
            return false;
        }
        if (unixEpochTime.getUnixEpochTimeMillis() <= this.mCurrentConfigurationInternal.getSuggestionUpperBound().toEpochMilli()) {
            return true;
        }
        Slog.w("time_detector", "Suggested value is above max time supported by this device. suggestion=" + obj);
        return false;
    }

    public final boolean validateAutoSuggestionTime(UnixEpochTime unixEpochTime, Object obj) {
        return validateSuggestionCommon(unixEpochTime, obj) && validateSuggestionAgainstLowerBound(unixEpochTime, obj, this.mCurrentConfigurationInternal.getAutoSuggestionLowerBound());
    }

    public final boolean validateManualSuggestionTime(UnixEpochTime unixEpochTime, Object obj) {
        return validateSuggestionCommon(unixEpochTime, obj) && validateSuggestionAgainstLowerBound(unixEpochTime, obj, this.mCurrentConfigurationInternal.getManualSuggestionLowerBound());
    }

    public final boolean validateSuggestionAgainstLowerBound(UnixEpochTime unixEpochTime, Object obj, Instant instant) {
        if (instant.toEpochMilli() <= unixEpochTime.getUnixEpochTimeMillis()) {
            return true;
        }
        Slog.w("time_detector", "Suggestion points to time before lower bound, skipping it. suggestion=" + obj + ", lower bound=" + instant);
        return false;
    }

    public final void doAutoTimeDetection(String str) {
        String str2;
        int[] autoOriginPriorities = this.mCurrentConfigurationInternal.getAutoOriginPriorities();
        for (int i : autoOriginPriorities) {
            UnixEpochTime unixEpochTime = null;
            if (i == 1) {
                TelephonyTimeSuggestion findBestTelephonySuggestion = findBestTelephonySuggestion();
                if (findBestTelephonySuggestion != null) {
                    unixEpochTime = findBestTelephonySuggestion.getUnixEpochTime();
                    str2 = "Found good telephony suggestion., bestTelephonySuggestion=" + findBestTelephonySuggestion + ", detectionReason=" + str;
                }
                str2 = null;
            } else if (i == 3) {
                NetworkTimeSuggestion findLatestValidNetworkSuggestion = findLatestValidNetworkSuggestion();
                if (findLatestValidNetworkSuggestion != null) {
                    unixEpochTime = findLatestValidNetworkSuggestion.getUnixEpochTime();
                    str2 = "Found good network suggestion., networkSuggestion=" + findLatestValidNetworkSuggestion + ", detectionReason=" + str;
                }
                str2 = null;
            } else if (i == 4) {
                GnssTimeSuggestion findLatestValidGnssSuggestion = findLatestValidGnssSuggestion();
                if (findLatestValidGnssSuggestion != null) {
                    unixEpochTime = findLatestValidGnssSuggestion.getUnixEpochTime();
                    str2 = "Found good gnss suggestion., gnssSuggestion=" + findLatestValidGnssSuggestion + ", detectionReason=" + str;
                }
                str2 = null;
            } else {
                if (i == 5) {
                    ExternalTimeSuggestion findLatestValidExternalSuggestion = findLatestValidExternalSuggestion();
                    if (findLatestValidExternalSuggestion != null) {
                        UnixEpochTime unixEpochTime2 = findLatestValidExternalSuggestion.getUnixEpochTime();
                        str2 = "Found good external suggestion., externalSuggestion=" + findLatestValidExternalSuggestion + ", detectionReason=" + str;
                        unixEpochTime = unixEpochTime2;
                    }
                } else {
                    Slog.w("time_detector", "Unknown or unsupported origin=" + i + " in " + Arrays.toString(autoOriginPriorities) + ": Skipping");
                }
                str2 = null;
            }
            if (unixEpochTime != null) {
                if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior() || isNtpSetByMdm()) {
                    setSystemClockAndConfidenceIfRequired(i, unixEpochTime, str2);
                    return;
                } else {
                    upgradeSystemClockConfidenceIfRequired(unixEpochTime, str2);
                    return;
                }
            }
        }
        Slog.d("time_detector", "Could not determine time: No suggestion found in originPriorities=" + Arrays.toString(autoOriginPriorities) + ", detectionReason=" + str);
    }

    public final TelephonyTimeSuggestion findBestTelephonySuggestion() {
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        TelephonyTimeSuggestion telephonyTimeSuggestion = null;
        int i = -1;
        for (int i2 = 0; i2 < this.mSuggestionBySlotIndex.size(); i2++) {
            Integer num = (Integer) this.mSuggestionBySlotIndex.keyAt(i2);
            TelephonyTimeSuggestion telephonyTimeSuggestion2 = (TelephonyTimeSuggestion) this.mSuggestionBySlotIndex.valueAt(i2);
            if (telephonyTimeSuggestion2 == null) {
                Slog.w("time_detector", "Latest suggestion unexpectedly null for slotIndex. slotIndex=" + num);
            } else if (telephonyTimeSuggestion2.getUnixEpochTime() == null) {
                Slog.w("time_detector", "Latest suggestion unexpectedly empty.  candidateSuggestion=" + telephonyTimeSuggestion2);
            } else {
                int scoreTelephonySuggestion = scoreTelephonySuggestion(elapsedRealtimeMillis, telephonyTimeSuggestion2);
                if (scoreTelephonySuggestion != -1) {
                    if (telephonyTimeSuggestion == null || i < scoreTelephonySuggestion) {
                        i = scoreTelephonySuggestion;
                    } else if (i == scoreTelephonySuggestion) {
                        if (telephonyTimeSuggestion2.getSlotIndex() >= telephonyTimeSuggestion.getSlotIndex()) {
                        }
                    }
                    telephonyTimeSuggestion = telephonyTimeSuggestion2;
                }
            }
        }
        return telephonyTimeSuggestion;
    }

    public static int scoreTelephonySuggestion(long j, TelephonyTimeSuggestion telephonyTimeSuggestion) {
        UnixEpochTime unixEpochTime = telephonyTimeSuggestion.getUnixEpochTime();
        if (!validateSuggestionUnixEpochTime(j, unixEpochTime)) {
            Slog.w("time_detector", "Existing suggestion found to be invalid elapsedRealtimeMillis=" + j + ", suggestion=" + telephonyTimeSuggestion);
            return -1;
        }
        int elapsedRealtimeMillis = (int) ((j - unixEpochTime.getElapsedRealtimeMillis()) / ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
        if (elapsedRealtimeMillis >= 24) {
            return -1;
        }
        return 24 - elapsedRealtimeMillis;
    }

    public final NetworkTimeSuggestion findLatestValidNetworkSuggestion() {
        NetworkTimeSuggestion networkTimeSuggestion = (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
        if (networkTimeSuggestion == null) {
            return null;
        }
        if (validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), networkTimeSuggestion.getUnixEpochTime())) {
            return networkTimeSuggestion;
        }
        return null;
    }

    public final GnssTimeSuggestion findLatestValidGnssSuggestion() {
        GnssTimeSuggestion gnssTimeSuggestion = (GnssTimeSuggestion) this.mLastGnssSuggestion.get();
        if (gnssTimeSuggestion == null) {
            return null;
        }
        if (validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), gnssTimeSuggestion.getUnixEpochTime())) {
            return gnssTimeSuggestion;
        }
        return null;
    }

    public final ExternalTimeSuggestion findLatestValidExternalSuggestion() {
        ExternalTimeSuggestion externalTimeSuggestion = (ExternalTimeSuggestion) this.mLastExternalSuggestion.get();
        if (externalTimeSuggestion == null) {
            return null;
        }
        if (validateSuggestionUnixEpochTime(this.mEnvironment.elapsedRealtimeMillis(), externalTimeSuggestion.getUnixEpochTime())) {
            return externalTimeSuggestion;
        }
        return null;
    }

    public final boolean setSystemClockAndConfidenceIfRequired(int i, UnixEpochTime unixEpochTime, String str) {
        if (isOriginAutomatic(i)) {
            if (!this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior() && !isNtpSetByMdm()) {
                Slog.d("time_detector", "Auto time detection is not enabled / no confidence update is needed. origin=" + TimeDetectorStrategy.originToString(i) + ", time=" + unixEpochTime + ", cause=" + str);
                return false;
            }
        } else if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior() && !isNtpSetByMdm()) {
            Slog.d("time_detector", "Auto time detection is enabled. origin=" + TimeDetectorStrategy.originToString(i) + ", time=" + unixEpochTime + ", cause=" + str);
            return false;
        }
        this.mEnvironment.acquireWakeLock();
        try {
            return setSystemClockAndConfidenceUnderWakeLock(i, unixEpochTime, 100, str);
        } finally {
            this.mEnvironment.releaseWakeLock();
        }
    }

    public final void upgradeSystemClockConfidenceIfRequired(UnixEpochTime unixEpochTime, String str) {
        int systemClockConfidence = this.mEnvironment.systemClockConfidence();
        if (systemClockConfidence < 100) {
            this.mEnvironment.acquireWakeLock();
            try {
                long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
                long systemClockMillis = this.mEnvironment.systemClockMillis();
                if (isTimeWithinConfidenceThreshold(unixEpochTime, elapsedRealtimeMillis, systemClockMillis)) {
                    String str2 = "Upgrade system clock confidence. autoDetectedUnixEpochTime=" + unixEpochTime + " newTimeConfidence=100 cause=" + str + " currentElapsedRealtimeMillis=" + elapsedRealtimeMillis + " currentSystemClockMillis=" + systemClockMillis + " currentTimeConfidence=" + systemClockConfidence;
                    Slog.d("time_detector", str2);
                    this.mEnvironment.setSystemClockConfidence(100, str2);
                }
            } finally {
                this.mEnvironment.releaseWakeLock();
            }
        }
    }

    public final boolean isTimeWithinConfidenceThreshold(UnixEpochTime unixEpochTime, long j, long j2) {
        return Math.abs(unixEpochTime.at(j).getUnixEpochTimeMillis() - j2) <= ((long) this.mCurrentConfigurationInternal.getSystemClockConfidenceThresholdMillis());
    }

    public final boolean setSystemClockAndConfidenceUnderWakeLock(int i, UnixEpochTime unixEpochTime, int i2, String str) {
        long j;
        UnixEpochTime unixEpochTime2;
        long elapsedRealtimeMillis = this.mEnvironment.elapsedRealtimeMillis();
        boolean isOriginAutomatic = isOriginAutomatic(i);
        long systemClockMillis = this.mEnvironment.systemClockMillis();
        if (isOriginAutomatic && (unixEpochTime2 = this.mLastAutoSystemClockTimeSet) != null) {
            long unixEpochTimeMillis = unixEpochTime2.at(elapsedRealtimeMillis).getUnixEpochTimeMillis();
            if (Math.abs(unixEpochTimeMillis - systemClockMillis) > 2000) {
                Slog.w("time_detector", "System clock has not tracked elapsed real time clock. A clock may be inaccurate or something unexpectedly set the system clock. origin=" + TimeDetectorStrategy.originToString(i) + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " expectedTimeMillis=" + unixEpochTimeMillis + " actualTimeMillis=" + systemClockMillis + " cause=" + str);
            }
        }
        long unixEpochTimeMillis2 = unixEpochTime.at(elapsedRealtimeMillis).getUnixEpochTimeMillis();
        long abs = Math.abs(unixEpochTimeMillis2 - systemClockMillis);
        long systemClockUpdateThresholdMillis = this.mCurrentConfigurationInternal.getSystemClockUpdateThresholdMillis();
        if (i != 3 || unixEpochTime.equals(this.mLastAutoSystemClockTimeSet)) {
            j = systemClockUpdateThresholdMillis;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("For more accuracy, systemClockUpdateThreshold changed to ");
            j = 500;
            sb.append(500L);
            Slog.d("time_detector", sb.toString());
        }
        boolean z = abs >= j;
        int systemClockConfidence = this.mEnvironment.systemClockConfidence();
        boolean z2 = i2 != systemClockConfidence;
        long j2 = j;
        if (z) {
            String str2 = "Set system clock & confidence. origin=" + TimeDetectorStrategy.originToString(i) + " newTime=" + unixEpochTime + " newTimeConfidence=" + i2 + " cause=" + str + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " (old) actualSystemClockMillis=" + systemClockMillis + " newSystemClockMillis=" + unixEpochTimeMillis2 + " currentTimeConfidence=" + systemClockConfidence;
            this.mEnvironment.setSystemClock(unixEpochTimeMillis2, i2, str2);
            Slog.d("time_detector", str2);
            if (isOriginAutomatic(i)) {
                this.mLastAutoSystemClockTimeSet = unixEpochTime;
            } else {
                this.mLastAutoSystemClockTimeSet = null;
            }
        } else if (z2) {
            String str3 = "Set system clock confidence. origin=" + TimeDetectorStrategy.originToString(i) + " newTime=" + unixEpochTime + " newTimeConfidence=" + i2 + " cause=" + str + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " (old) actualSystemClockMillis=" + systemClockMillis + " newSystemClockMillis=" + unixEpochTimeMillis2 + " currentTimeConfidence=" + systemClockConfidence;
            Slog.d("time_detector", str3);
            this.mEnvironment.setSystemClockConfidence(i2, str3);
        } else {
            Slog.d("time_detector", "Not setting system clock or confidence. origin=" + TimeDetectorStrategy.originToString(i) + " newTime=" + unixEpochTime + " newTimeConfidence=" + i2 + " cause=" + str + " elapsedRealtimeMillis=" + elapsedRealtimeMillis + " systemClockUpdateThreshold=" + j2 + " absTimeDifference=" + abs + " currentTimeConfidence=" + systemClockConfidence);
        }
        return true;
    }

    public synchronized TelephonyTimeSuggestion findBestTelephonySuggestionForTests() {
        return findBestTelephonySuggestion();
    }

    public synchronized NetworkTimeSuggestion findLatestValidNetworkSuggestionForTests() {
        return findLatestValidNetworkSuggestion();
    }

    public synchronized GnssTimeSuggestion findLatestValidGnssSuggestionForTests() {
        return findLatestValidGnssSuggestion();
    }

    public synchronized ExternalTimeSuggestion findLatestValidExternalSuggestionForTests() {
        return findLatestValidExternalSuggestion();
    }

    public synchronized TelephonyTimeSuggestion getLatestTelephonySuggestion(int i) {
        return (TelephonyTimeSuggestion) this.mSuggestionBySlotIndex.get(Integer.valueOf(i));
    }

    public synchronized GnssTimeSuggestion getLatestGnssSuggestion() {
        return (GnssTimeSuggestion) this.mLastGnssSuggestion.get();
    }

    public synchronized ExternalTimeSuggestion getLatestExternalSuggestion() {
        return (ExternalTimeSuggestion) this.mLastExternalSuggestion.get();
    }

    public static boolean validateSuggestionUnixEpochTime(long j, UnixEpochTime unixEpochTime) {
        long elapsedRealtimeMillis = unixEpochTime.getElapsedRealtimeMillis();
        return elapsedRealtimeMillis <= j && j - elapsedRealtimeMillis <= 86400000;
    }
}
