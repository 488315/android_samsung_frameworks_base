package com.android.server.timedetector;

import android.app.ActivityManagerInternal;
import android.app.time.ExternalTimeSuggestion;
import android.app.time.TimeCapabilities;
import android.app.time.TimeConfiguration;
import android.app.time.TimeState;
import android.app.time.UnixEpochTime;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TelephonyTimeSuggestion;
import android.os.RemoteException;
import android.os.SystemClock;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemClockTime;
import com.android.server.timezonedetector.ArrayMapWithHistory;
import com.android.server.timezonedetector.ReferenceWithHistory;
import com.android.server.timezonedetector.StateChangeListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeDetectorStrategyImpl implements TimeDetectorStrategy {
    static final long MAX_SUGGESTION_TIME_AGE_MILLIS = 86400000;
    static final int TELEPHONY_BUCKET_SIZE_MILLIS = 3600000;
    public ConfigurationInternal mCurrentConfigurationInternal;
    public final Environment mEnvironment;
    public UnixEpochTime mLastAutoSystemClockTimeSet;
    public final ServiceConfigAccessor mServiceConfigAccessor;
    public final List mStateChangeListeners = new ArrayList();
    public final ArrayMapWithHistory mSuggestionBySlotIndex = new ArrayMapWithHistory();
    public final ReferenceWithHistory mLastNetworkSuggestion = new ReferenceWithHistory(10);
    public final ReferenceWithHistory mLastGnssSuggestion = new ReferenceWithHistory(10);
    public final ReferenceWithHistory mLastExternalSuggestion = new ReferenceWithHistory(10);
    public final ArraySet mNetworkTimeUpdateListeners = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Environment {}

    public TimeDetectorStrategyImpl(
            Environment environment, ServiceConfigAccessor serviceConfigAccessor) {
        Objects.requireNonNull(environment);
        this.mEnvironment = environment;
        Objects.requireNonNull(serviceConfigAccessor);
        this.mServiceConfigAccessor = serviceConfigAccessor;
        synchronized (this) {
            try {
                StateChangeListener stateChangeListener =
                        new StateChangeListener() { // from class:
                                                    // com.android.server.timedetector.TimeDetectorStrategyImpl$$ExternalSyntheticLambda1
                            @Override // com.android.server.timezonedetector.StateChangeListener
                            public final void onChange() {
                                TimeDetectorStrategyImpl timeDetectorStrategyImpl =
                                        TimeDetectorStrategyImpl.this;
                                synchronized (timeDetectorStrategyImpl) {
                                    timeDetectorStrategyImpl
                                            .updateCurrentConfigurationInternalIfRequired(
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
                updateCurrentConfigurationInternalIfRequired("TimeDetectorStrategyImpl:");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean isNtpSetByMdm() {
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

    public static boolean validateSuggestionAgainstLowerBound(
            UnixEpochTime unixEpochTime, Object obj, Instant instant) {
        if (instant.toEpochMilli() <= unixEpochTime.getUnixEpochTimeMillis()) {
            return true;
        }
        Slog.w(
                "time_detector",
                "Suggestion points to time before lower bound, skipping it. suggestion="
                        + obj
                        + ", lower bound="
                        + instant);
        return false;
    }

    public static boolean validateSuggestionUnixEpochTime(long j, UnixEpochTime unixEpochTime) {
        long elapsedRealtimeMillis = unixEpochTime.getElapsedRealtimeMillis();
        return elapsedRealtimeMillis <= j && j - elapsedRealtimeMillis <= 86400000;
    }

    public final synchronized void clearLatestNetworkSuggestion() {
        this.mLastNetworkSuggestion.set(null);
        notifyNetworkTimeUpdateListenersAsynchronously();
        doAutoTimeDetection("Network time cleared");
    }

    public final synchronized boolean confirmTime(UnixEpochTime unixEpochTime) {
        boolean z;
        int systemClockConfidence;
        Objects.requireNonNull(unixEpochTime);
        ((EnvironmentImpl) this.mEnvironment).acquireWakeLock();
        try {
            ((EnvironmentImpl) this.mEnvironment).getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            ((EnvironmentImpl) this.mEnvironment).getClass();
            long currentTimeMillis = System.currentTimeMillis();
            z =
                    Math.abs(
                                    unixEpochTime.at(elapsedRealtime).getUnixEpochTimeMillis()
                                            - currentTimeMillis)
                            <= ((long)
                                    this.mCurrentConfigurationInternal
                                            .mSystemClockConfidenceThresholdMillis);
            if (z
                    && (systemClockConfidence =
                                    ((EnvironmentImpl) this.mEnvironment).systemClockConfidence())
                            < 100) {
                String str =
                        "Confirm system clock time. confirmationTime="
                                + unixEpochTime
                                + " newTimeConfidence=100 currentElapsedRealtimeMillis="
                                + elapsedRealtime
                                + " currentSystemClockMillis="
                                + currentTimeMillis
                                + " (old) currentTimeConfidence="
                                + systemClockConfidence;
                Slog.d("time_detector", str);
                ((EnvironmentImpl) this.mEnvironment).setSystemClockConfidence(100, str);
            }
        } finally {
            ((EnvironmentImpl) this.mEnvironment).releaseWakeLock();
        }
        return z;
    }

    public final void doAutoTimeDetection(String str) {
        String str2;
        int[] iArr = this.mCurrentConfigurationInternal.mOriginPriorities;
        for (int i : iArr) {
            UnixEpochTime unixEpochTime = null;
            if (i == 1) {
                TelephonyTimeSuggestion findBestTelephonySuggestion = findBestTelephonySuggestion();
                if (findBestTelephonySuggestion != null) {
                    unixEpochTime = findBestTelephonySuggestion.getUnixEpochTime();
                    str2 =
                            "Found good telephony suggestion., bestTelephonySuggestion="
                                    + findBestTelephonySuggestion
                                    + ", detectionReason="
                                    + str;
                }
                str2 = null;
            } else if (i == 3) {
                NetworkTimeSuggestion findLatestValidNetworkSuggestion =
                        findLatestValidNetworkSuggestion();
                if (findLatestValidNetworkSuggestion != null) {
                    unixEpochTime = findLatestValidNetworkSuggestion.mUnixEpochTime;
                    str2 =
                            "Found good network suggestion., networkSuggestion="
                                    + findLatestValidNetworkSuggestion
                                    + ", detectionReason="
                                    + str;
                }
                str2 = null;
            } else if (i == 4) {
                GnssTimeSuggestion findLatestValidGnssSuggestion = findLatestValidGnssSuggestion();
                if (findLatestValidGnssSuggestion != null) {
                    unixEpochTime =
                            findLatestValidGnssSuggestion.mTimeSuggestionHelper.getUnixEpochTime();
                    str2 =
                            "Found good gnss suggestion., gnssSuggestion="
                                    + findLatestValidGnssSuggestion
                                    + ", detectionReason="
                                    + str;
                }
                str2 = null;
            } else {
                if (i == 5) {
                    ExternalTimeSuggestion findLatestValidExternalSuggestion =
                            findLatestValidExternalSuggestion();
                    if (findLatestValidExternalSuggestion != null) {
                        unixEpochTime = findLatestValidExternalSuggestion.getUnixEpochTime();
                        str2 =
                                "Found good external suggestion., externalSuggestion="
                                        + findLatestValidExternalSuggestion
                                        + ", detectionReason="
                                        + str;
                    }
                } else {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "Unknown or unsupported origin=", " in ");
                    m.append(Arrays.toString(iArr));
                    m.append(": Skipping");
                    Slog.w("time_detector", m.toString());
                }
                str2 = null;
            }
            if (unixEpochTime != null) {
                if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()
                        || isNtpSetByMdm()) {
                    setSystemClockAndConfidenceIfRequired(i, unixEpochTime, str2);
                    return;
                }
                Environment environment = this.mEnvironment;
                EnvironmentImpl environmentImpl = (EnvironmentImpl) environment;
                int systemClockConfidence = environmentImpl.systemClockConfidence();
                if (systemClockConfidence < 100) {
                    environmentImpl.acquireWakeLock();
                    try {
                        ((EnvironmentImpl) environment).getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        ((EnvironmentImpl) environment).getClass();
                        long currentTimeMillis = System.currentTimeMillis();
                        UnixEpochTime unixEpochTime2 = unixEpochTime;
                        if (Math.abs(
                                        unixEpochTime.at(elapsedRealtime).getUnixEpochTimeMillis()
                                                - currentTimeMillis)
                                <= this.mCurrentConfigurationInternal
                                        .mSystemClockConfidenceThresholdMillis) {
                            String str3 =
                                    "Upgrade system clock confidence. autoDetectedUnixEpochTime="
                                            + unixEpochTime2
                                            + " newTimeConfidence=100 cause="
                                            + str2
                                            + " currentElapsedRealtimeMillis="
                                            + elapsedRealtime
                                            + " currentSystemClockMillis="
                                            + currentTimeMillis
                                            + " currentTimeConfidence="
                                            + systemClockConfidence;
                            Slog.d("time_detector", str3);
                            ((EnvironmentImpl) environment).setSystemClockConfidence(100, str3);
                        }
                        return;
                    } finally {
                        environmentImpl.releaseWakeLock();
                    }
                }
                return;
            }
        }
        Slog.d(
                "time_detector",
                "Could not determine time: No suggestion found in originPriorities="
                        + Arrays.toString(iArr)
                        + ", detectionReason="
                        + str);
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final synchronized void dump(
            IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        indentingPrintWriter.println("TimeDetectorStrategy:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(
                "mLastAutoSystemClockTimeSet=" + this.mLastAutoSystemClockTimeSet);
        indentingPrintWriter.println(
                "mCurrentConfigurationInternal=" + this.mCurrentConfigurationInternal);
        indentingPrintWriter.println(
                "[Capabilities="
                        + this.mCurrentConfigurationInternal.createCapabilitiesAndConfig()
                        + "]");
        indentingPrintWriter.println("mEnvironment:");
        indentingPrintWriter.increaseIndent();
        ((EnvironmentImpl) this.mEnvironment).dumpDebugLog(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Time change log:");
        indentingPrintWriter.increaseIndent();
        SystemClockTime.sTimeDebugLog.dump(indentingPrintWriter);
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.timedetector.TelephonyTimeSuggestion findBestTelephonySuggestion() {
        /*
            r12 = this;
            com.android.server.timedetector.TimeDetectorStrategyImpl$Environment r0 = r12.mEnvironment
            com.android.server.timedetector.EnvironmentImpl r0 = (com.android.server.timedetector.EnvironmentImpl) r0
            r0.getClass()
            long r0 = android.os.SystemClock.elapsedRealtime()
            r2 = 0
            r3 = -1
            r4 = 0
            r5 = r2
            r6 = r3
        L10:
            com.android.server.timezonedetector.ArrayMapWithHistory r7 = r12.mSuggestionBySlotIndex
            android.util.ArrayMap r8 = r7.mMap
            if (r8 != 0) goto L18
            r8 = r2
            goto L1c
        L18:
            int r8 = r8.size()
        L1c:
            if (r5 >= r8) goto Lb7
            android.util.ArrayMap r8 = r7.mMap
            if (r8 == 0) goto Lb1
            java.lang.Object r8 = r8.keyAt(r5)
            java.lang.Integer r8 = (java.lang.Integer) r8
            java.lang.Object r7 = r7.valueAt(r5)
            android.app.timedetector.TelephonyTimeSuggestion r7 = (android.app.timedetector.TelephonyTimeSuggestion) r7
            java.lang.String r9 = "time_detector"
            if (r7 != 0) goto L46
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r10 = "Latest suggestion unexpectedly null for slotIndex. slotIndex="
            r7.<init>(r10)
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Slog.w(r9, r7)
            goto Lad
        L46:
            android.app.time.UnixEpochTime r8 = r7.getUnixEpochTime()
            if (r8 != 0) goto L5e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r10 = "Latest suggestion unexpectedly empty.  candidateSuggestion="
            r8.<init>(r10)
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Slog.w(r9, r7)
            goto Lad
        L5e:
            android.app.time.UnixEpochTime r8 = r7.getUnixEpochTime()
            boolean r10 = validateSuggestionUnixEpochTime(r0, r8)
            if (r10 != 0) goto L83
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r10 = "Existing suggestion found to be invalid elapsedRealtimeMillis="
            r8.<init>(r10)
            r8.append(r0)
            java.lang.String r10 = ", suggestion="
            r8.append(r10)
            r8.append(r7)
            java.lang.String r8 = r8.toString()
            android.util.Slog.w(r9, r8)
        L81:
            r8 = r3
            goto L95
        L83:
            long r8 = r8.getElapsedRealtimeMillis()
            long r8 = r0 - r8
            r10 = 3600000(0x36ee80, double:1.7786363E-317)
            long r8 = r8 / r10
            int r8 = (int) r8
            r9 = 24
            if (r8 < r9) goto L93
            goto L81
        L93:
            int r8 = 24 - r8
        L95:
            if (r8 != r3) goto L98
            goto Lad
        L98:
            if (r4 == 0) goto Lab
            if (r6 >= r8) goto L9d
            goto Lab
        L9d:
            if (r6 != r8) goto Lad
            int r8 = r7.getSlotIndex()
            int r9 = r4.getSlotIndex()
            if (r8 >= r9) goto Lad
            r4 = r7
            goto Lad
        Lab:
            r4 = r7
            r6 = r8
        Lad:
            int r5 = r5 + 1
            goto L10
        Lb1:
            java.lang.ArrayIndexOutOfBoundsException r12 = new java.lang.ArrayIndexOutOfBoundsException
            r12.<init>(r5)
            throw r12
        Lb7:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.timedetector.TimeDetectorStrategyImpl.findBestTelephonySuggestion():android.app.timedetector.TelephonyTimeSuggestion");
    }

    public synchronized TelephonyTimeSuggestion findBestTelephonySuggestionForTests() {
        return findBestTelephonySuggestion();
    }

    public final ExternalTimeSuggestion findLatestValidExternalSuggestion() {
        ExternalTimeSuggestion externalTimeSuggestion =
                (ExternalTimeSuggestion) this.mLastExternalSuggestion.get();
        if (externalTimeSuggestion == null) {
            return null;
        }
        UnixEpochTime unixEpochTime = externalTimeSuggestion.getUnixEpochTime();
        ((EnvironmentImpl) this.mEnvironment).getClass();
        if (validateSuggestionUnixEpochTime(SystemClock.elapsedRealtime(), unixEpochTime)) {
            return externalTimeSuggestion;
        }
        return null;
    }

    public synchronized ExternalTimeSuggestion findLatestValidExternalSuggestionForTests() {
        return findLatestValidExternalSuggestion();
    }

    public final GnssTimeSuggestion findLatestValidGnssSuggestion() {
        GnssTimeSuggestion gnssTimeSuggestion = (GnssTimeSuggestion) this.mLastGnssSuggestion.get();
        if (gnssTimeSuggestion == null) {
            return null;
        }
        UnixEpochTime unixEpochTime = gnssTimeSuggestion.mTimeSuggestionHelper.getUnixEpochTime();
        ((EnvironmentImpl) this.mEnvironment).getClass();
        if (validateSuggestionUnixEpochTime(SystemClock.elapsedRealtime(), unixEpochTime)) {
            return gnssTimeSuggestion;
        }
        return null;
    }

    public synchronized GnssTimeSuggestion findLatestValidGnssSuggestionForTests() {
        return findLatestValidGnssSuggestion();
    }

    public final NetworkTimeSuggestion findLatestValidNetworkSuggestion() {
        NetworkTimeSuggestion networkTimeSuggestion =
                (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
        if (networkTimeSuggestion == null) {
            return null;
        }
        UnixEpochTime unixEpochTime = networkTimeSuggestion.mUnixEpochTime;
        ((EnvironmentImpl) this.mEnvironment).getClass();
        if (validateSuggestionUnixEpochTime(SystemClock.elapsedRealtime(), unixEpochTime)) {
            return networkTimeSuggestion;
        }
        return null;
    }

    public synchronized NetworkTimeSuggestion findLatestValidNetworkSuggestionForTests() {
        return findLatestValidNetworkSuggestion();
    }

    public synchronized ConfigurationInternal getCachedCapabilitiesAndConfigForTests() {
        return this.mCurrentConfigurationInternal;
    }

    public synchronized ExternalTimeSuggestion getLatestExternalSuggestion() {
        return (ExternalTimeSuggestion) this.mLastExternalSuggestion.get();
    }

    public synchronized GnssTimeSuggestion getLatestGnssSuggestion() {
        return (GnssTimeSuggestion) this.mLastGnssSuggestion.get();
    }

    public final synchronized NetworkTimeSuggestion getLatestNetworkSuggestion() {
        return (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
    }

    public synchronized TelephonyTimeSuggestion getLatestTelephonySuggestion(int i) {
        return (TelephonyTimeSuggestion) this.mSuggestionBySlotIndex.get(Integer.valueOf(i));
    }

    public final synchronized TimeState getTimeState() {
        boolean z;
        long elapsedRealtime;
        z = ((EnvironmentImpl) this.mEnvironment).systemClockConfidence() < 100;
        ((EnvironmentImpl) this.mEnvironment).getClass();
        elapsedRealtime = SystemClock.elapsedRealtime();
        ((EnvironmentImpl) this.mEnvironment).getClass();
        return new TimeState(new UnixEpochTime(elapsedRealtime, System.currentTimeMillis()), z);
    }

    public final void notifyNetworkTimeUpdateListenersAsynchronously() {
        Iterator it = this.mNetworkTimeUpdateListeners.iterator();
        while (it.hasNext()) {
            StateChangeListener stateChangeListener = (StateChangeListener) it.next();
            Objects.requireNonNull(stateChangeListener);
            ((EnvironmentImpl) this.mEnvironment)
                    .mHandler.post(
                            new TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(
                                    stateChangeListener));
        }
    }

    public final boolean setSystemClockAndConfidenceIfRequired(
            int i, UnixEpochTime unixEpochTime, String str) {
        if (i != 2) {
            if (!this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()
                    && !isNtpSetByMdm()) {
                StringBuilder sb =
                        new StringBuilder(
                                "Auto time detection is not enabled / no confidence update is"
                                    + " needed. origin=");
                sb.append(TimeDetectorStrategy.originToString(i));
                sb.append(", time=");
                sb.append(unixEpochTime);
                sb.append(", cause=");
                BootReceiver$$ExternalSyntheticOutline0.m(sb, str, "time_detector");
                return false;
            }
        } else if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()
                && !isNtpSetByMdm()) {
            StringBuilder sb2 = new StringBuilder("Auto time detection is enabled. origin=");
            sb2.append(TimeDetectorStrategy.originToString(i));
            sb2.append(", time=");
            sb2.append(unixEpochTime);
            sb2.append(", cause=");
            BootReceiver$$ExternalSyntheticOutline0.m(sb2, str, "time_detector");
            return false;
        }
        EnvironmentImpl environmentImpl = (EnvironmentImpl) this.mEnvironment;
        environmentImpl.acquireWakeLock();
        try {
            setSystemClockAndConfidenceUnderWakeLock(i, unixEpochTime, 100, str);
            return true;
        } finally {
            environmentImpl.releaseWakeLock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setSystemClockAndConfidenceUnderWakeLock(
            int r32, android.app.time.UnixEpochTime r33, int r34, java.lang.String r35) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.timedetector.TimeDetectorStrategyImpl.setSystemClockAndConfidenceUnderWakeLock(int,"
                    + " android.app.time.UnixEpochTime, int, java.lang.String):void");
    }

    public final synchronized void setTimeState(TimeState timeState) {
        Objects.requireNonNull(timeState);
        int i = timeState.getUserShouldConfirmTime() ? 0 : 100;
        ((EnvironmentImpl) this.mEnvironment).acquireWakeLock();
        try {
            setSystemClockAndConfidenceUnderWakeLock(
                    2, timeState.getUnixEpochTime(), i, "setTimeState()");
        } finally {
            ((EnvironmentImpl) this.mEnvironment).releaseWakeLock();
        }
    }

    public final boolean storeTelephonySuggestion(TelephonyTimeSuggestion telephonyTimeSuggestion) {
        UnixEpochTime unixEpochTime = telephonyTimeSuggestion.getUnixEpochTime();
        int slotIndex = telephonyTimeSuggestion.getSlotIndex();
        Integer valueOf = Integer.valueOf(slotIndex);
        ArrayMapWithHistory arrayMapWithHistory = this.mSuggestionBySlotIndex;
        TelephonyTimeSuggestion telephonyTimeSuggestion2 =
                (TelephonyTimeSuggestion) arrayMapWithHistory.get(valueOf);
        if (telephonyTimeSuggestion2 != null) {
            if (telephonyTimeSuggestion2.getUnixEpochTime() == null) {
                Slog.w(
                        "time_detector",
                        "Previous suggestion is null or has a null time. previousSuggestion="
                                + telephonyTimeSuggestion2
                                + ", suggestion="
                                + telephonyTimeSuggestion);
                return false;
            }
            long elapsedRealtimeDifference =
                    UnixEpochTime.elapsedRealtimeDifference(
                            unixEpochTime, telephonyTimeSuggestion2.getUnixEpochTime());
            if (elapsedRealtimeDifference < 0) {
                Slog.w(
                        "time_detector",
                        "Out of order telephony suggestion received. referenceTimeDifference="
                                + elapsedRealtimeDifference
                                + " previousSuggestion="
                                + telephonyTimeSuggestion2
                                + " suggestion="
                                + telephonyTimeSuggestion);
                return false;
            }
        }
        arrayMapWithHistory.put(Integer.valueOf(slotIndex), telephonyTimeSuggestion);
        return true;
    }

    public final synchronized void suggestGnssTime(GnssTimeSuggestion gnssTimeSuggestion) {
        Slog.d(
                "time_detector",
                "GNSS suggestion received. currentUserConfig="
                        + this.mCurrentConfigurationInternal
                        + " suggestion="
                        + gnssTimeSuggestion);
        Objects.requireNonNull(gnssTimeSuggestion);
        if (validateAutoSuggestionTime(
                gnssTimeSuggestion.mTimeSuggestionHelper.getUnixEpochTime(), gnssTimeSuggestion)) {
            this.mLastGnssSuggestion.set(gnssTimeSuggestion);
            doAutoTimeDetection("GNSS time suggestion received: suggestion=" + gnssTimeSuggestion);
        }
    }

    public final synchronized boolean suggestManualTime(
            int i, ManualTimeSuggestion manualTimeSuggestion) {
        ConfigurationInternal configurationInternal = this.mCurrentConfigurationInternal;
        if (configurationInternal.mUserId != i) {
            Slog.w(
                    "time_detector",
                    "Manual suggestion received but user != current user, userId="
                            + i
                            + " suggestion="
                            + manualTimeSuggestion);
            return false;
        }
        Objects.requireNonNull(manualTimeSuggestion);
        String str = "Manual time suggestion received: suggestion=" + manualTimeSuggestion;
        TimeCapabilities capabilities =
                configurationInternal.createCapabilitiesAndConfig().getCapabilities();
        if (capabilities.getSetManualTimeCapability() == 40) {
            UnixEpochTime unixEpochTime = manualTimeSuggestion.getUnixEpochTime();
            Instant instant = this.mCurrentConfigurationInternal.mManualSuggestionLowerBound;
            if (!validateSuggestionCommon(unixEpochTime, manualTimeSuggestion)
                    || !validateSuggestionAgainstLowerBound(
                            unixEpochTime, manualTimeSuggestion, instant)) {
                return false;
            }
            return setSystemClockAndConfidenceIfRequired(2, unixEpochTime, str);
        }
        Slog.i(
                "time_detector",
                "User does not have the capability needed to set the time manually: capabilities="
                        + capabilities
                        + ", suggestion="
                        + manualTimeSuggestion
                        + ", cause="
                        + str);
        return false;
    }

    public final synchronized void suggestNetworkTime(NetworkTimeSuggestion networkTimeSuggestion) {
        Slog.d(
                "time_detector",
                "Network suggestion received. currentUserConfig="
                        + this.mCurrentConfigurationInternal
                        + " suggestion="
                        + networkTimeSuggestion);
        Objects.requireNonNull(networkTimeSuggestion);
        if (validateAutoSuggestionTime(networkTimeSuggestion.mUnixEpochTime, networkTimeSuggestion)
                || isNtpSetByMdm()) {
            NetworkTimeSuggestion networkTimeSuggestion2 =
                    (NetworkTimeSuggestion) this.mLastNetworkSuggestion.get();
            if (networkTimeSuggestion2 == null
                    || !networkTimeSuggestion2.equals(networkTimeSuggestion)) {
                this.mLastNetworkSuggestion.set(networkTimeSuggestion);
                notifyNetworkTimeUpdateListenersAsynchronously();
            }
            doAutoTimeDetection("New network time suggested. suggestion=" + networkTimeSuggestion);
        }
    }

    public final synchronized boolean updateConfiguration(
            int i, TimeConfiguration timeConfiguration) {
        boolean updateConfiguration;
        updateConfiguration =
                ((ServiceConfigAccessorImpl) this.mServiceConfigAccessor)
                        .updateConfiguration(i, timeConfiguration);
        if (updateConfiguration) {
            updateCurrentConfigurationInternalIfRequired(
                    "updateConfiguration: userId="
                            + i
                            + ", configuration="
                            + timeConfiguration
                            + ", bypassUserPolicyChecks=false");
        }
        return updateConfiguration;
    }

    public final void updateCurrentConfigurationInternalIfRequired(String str) {
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
        Slog.d("time_detector", str2);
        ((EnvironmentImpl) this.mEnvironment).getClass();
        SystemClockTime.sTimeDebugLog.log(str2);
        Iterator it = ((ArrayList) this.mStateChangeListeners).iterator();
        while (it.hasNext()) {
            StateChangeListener stateChangeListener = (StateChangeListener) it.next();
            Objects.requireNonNull(stateChangeListener);
            ((EnvironmentImpl) this.mEnvironment)
                    .mHandler.post(
                            new TimeDetectorStrategyImpl$$ExternalSyntheticLambda0(
                                    stateChangeListener));
        }
        if (this.mCurrentConfigurationInternal.getAutoDetectionEnabledBehavior()) {
            doAutoTimeDetection("Auto time detection config changed.");
        } else {
            this.mLastAutoSystemClockTimeSet = null;
        }
    }

    public final boolean validateAutoSuggestionTime(UnixEpochTime unixEpochTime, Object obj) {
        return validateSuggestionCommon(unixEpochTime, obj)
                && validateSuggestionAgainstLowerBound(
                        unixEpochTime,
                        obj,
                        this.mCurrentConfigurationInternal.mAutoSuggestionLowerBound);
    }

    public final boolean validateSuggestionCommon(UnixEpochTime unixEpochTime, Object obj) {
        ((EnvironmentImpl) this.mEnvironment).getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime < unixEpochTime.getElapsedRealtimeMillis()) {
            Slog.w(
                    "time_detector",
                    "New elapsed realtime is in the future? Ignoring. elapsedRealtimeMillis="
                            + elapsedRealtime
                            + ", suggestion="
                            + obj);
            return false;
        }
        if (unixEpochTime.getUnixEpochTimeMillis()
                <= this.mCurrentConfigurationInternal.mSuggestionUpperBound.toEpochMilli()) {
            return true;
        }
        Slog.w(
                "time_detector",
                "Suggested value is above max time supported by this device. suggestion=" + obj);
        return false;
    }
}
