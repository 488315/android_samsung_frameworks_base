package com.android.server.location.eventlog;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.location.GnssMeasurementRequest;
import android.location.LocationRequest;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.TimeUtils;

import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class LocationEventLog extends LocalEventLog {
    public static final LocationEventLog EVENT_LOG = new LocationEventLog();
    public final ArrayMap mAggregateStats;
    public final ArrayMap mGnssMeasAggregateStats;
    public final LocationsEventLog mLocationsLog;

    public final class AggregateStats {
        public int mActiveRequestCount;
        public long mActiveTimeLastUpdateRealtimeMs;
        public long mActiveTimeTotalMs;
        public int mAddedRequestCount;
        public long mAddedTimeLastUpdateRealtimeMs;
        public long mAddedTimeTotalMs;
        public int mDeliveredLocationCount;
        public long mFastestIntervalMs;
        public int mForegroundRequestCount;
        public long mForegroundTimeLastUpdateRealtimeMs;
        public long mForegroundTimeTotalMs;
        public long mSlowestIntervalMs;

        public static String intervalToString(long j) {
            if (j == Long.MAX_VALUE) {
                return "passive";
            }
            return TimeUnit.MILLISECONDS.toSeconds(j) + "s";
        }

        public final synchronized String toString() {
            return "min/max interval = "
                    + intervalToString(this.mFastestIntervalMs)
                    + "/"
                    + intervalToString(this.mSlowestIntervalMs)
                    + ", total/active/foreground duration = "
                    + TimeUtils.formatDuration(this.mAddedTimeTotalMs)
                    + "/"
                    + TimeUtils.formatDuration(this.mActiveTimeTotalMs)
                    + "/"
                    + TimeUtils.formatDuration(this.mForegroundTimeTotalMs)
                    + ", locations = "
                    + this.mDeliveredLocationCount;
        }

        public final synchronized void updateTotals() {
            try {
                if (this.mAddedRequestCount > 0) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    this.mAddedTimeTotalMs =
                            (elapsedRealtime - this.mAddedTimeLastUpdateRealtimeMs)
                                    + this.mAddedTimeTotalMs;
                    this.mAddedTimeLastUpdateRealtimeMs = elapsedRealtime;
                }
                if (this.mActiveRequestCount > 0) {
                    long elapsedRealtime2 = SystemClock.elapsedRealtime();
                    this.mActiveTimeTotalMs =
                            (elapsedRealtime2 - this.mActiveTimeLastUpdateRealtimeMs)
                                    + this.mActiveTimeTotalMs;
                    this.mActiveTimeLastUpdateRealtimeMs = elapsedRealtime2;
                }
                if (this.mForegroundRequestCount > 0) {
                    long elapsedRealtime3 = SystemClock.elapsedRealtime();
                    this.mForegroundTimeTotalMs =
                            (elapsedRealtime3 - this.mForegroundTimeLastUpdateRealtimeMs)
                                    + this.mForegroundTimeTotalMs;
                    this.mForegroundTimeLastUpdateRealtimeMs = elapsedRealtime3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final class GnssMeasurementAggregateStats {
        public int mAddedRequestCount;
        public long mAddedTimeLastUpdateRealtimeMs;
        public long mAddedTimeTotalMs;
        public long mFastestIntervalMs;
        public boolean mHasDutyCycling;
        public boolean mHasFullTracking;
        public int mReceivedMeasurementEventCount;
        public long mSlowestIntervalMs;

        public static String intervalToString(long j) {
            if (j == 2147483647L) {
                return "passive";
            }
            return TimeUnit.MILLISECONDS.toSeconds(j) + "s";
        }

        public final synchronized String toString() {
            StringBuilder sb;
            sb = new StringBuilder("min/max interval = ");
            sb.append(intervalToString(this.mFastestIntervalMs));
            sb.append("/");
            sb.append(intervalToString(this.mSlowestIntervalMs));
            sb.append(", total duration = ");
            sb.append(TimeUtils.formatDuration(this.mAddedTimeTotalMs));
            sb.append(", tracking mode = ");
            boolean z = this.mHasFullTracking;
            sb.append(
                    (z && this.mHasDutyCycling)
                            ? "mixed tracking mode"
                            : z ? "always full-tracking" : "always duty-cycling");
            sb.append(", GNSS measurement events = ");
            sb.append(this.mReceivedMeasurementEventCount);
            return sb.toString();
        }

        public final synchronized void updateTotals() {
            if (this.mAddedRequestCount > 0) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.mAddedTimeTotalMs =
                        (elapsedRealtime - this.mAddedTimeLastUpdateRealtimeMs)
                                + this.mAddedTimeTotalMs;
                this.mAddedTimeLastUpdateRealtimeMs = elapsedRealtime;
            }
        }
    }

    public final class GnssMeasurementClientRegisterEvent {
        public final GnssMeasurementRequest mGnssMeasurementRequest;
        public final CallerIdentity mIdentity;
        public final boolean mRegistered;

        public GnssMeasurementClientRegisterEvent(
                boolean z,
                CallerIdentity callerIdentity,
                GnssMeasurementRequest gnssMeasurementRequest) {
            this.mRegistered = z;
            this.mIdentity = callerIdentity;
            this.mGnssMeasurementRequest = gnssMeasurementRequest;
        }

        public final String toString() {
            if (!this.mRegistered) {
                return "gnss measurements -registration " + this.mIdentity;
            }
            return "gnss measurements +registration "
                    + this.mIdentity
                    + " -> "
                    + this.mGnssMeasurementRequest;
        }
    }

    public final class GnssMeasurementDeliverEvent {
        public final CallerIdentity mIdentity;
        public final int mNumGnssMeasurements;

        public GnssMeasurementDeliverEvent(int i, CallerIdentity callerIdentity) {
            this.mNumGnssMeasurements = i;
            this.mIdentity = callerIdentity;
        }

        public final String toString() {
            return "gnss measurements delivered GnssMeasurements["
                    + this.mNumGnssMeasurements
                    + "] to "
                    + this.mIdentity;
        }
    }

    public final class LocationEnabledEvent {
        public final /* synthetic */ int $r8$classId;
        public final boolean mEnabled;
        public final int mUserId;

        public /* synthetic */ LocationEnabledEvent(int i, int i2, boolean z) {
            this.$r8$classId = i2;
            this.mUserId = i;
            this.mEnabled = z;
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    StringBuilder sb = new StringBuilder("location [u");
                    sb.append(this.mUserId);
                    sb.append("] ");
                    sb.append(this.mEnabled ? "enabled" : "disabled");
                    return sb.toString();
                case 1:
                    StringBuilder sb2 = new StringBuilder("adas location [u");
                    sb2.append(this.mUserId);
                    sb2.append("] ");
                    sb2.append(this.mEnabled ? "enabled" : "disabled");
                    return sb2.toString();
                default:
                    StringBuilder sb3 = new StringBuilder("[u");
                    sb3.append(this.mUserId);
                    sb3.append("] ");
                    sb3.append(this.mEnabled ? "visible" : "invisible");
                    return sb3.toString();
            }
        }
    }

    public final class LocationPowerSaveModeEvent {
        public final int mLocationPowerSaveMode;

        public LocationPowerSaveModeEvent(int i) {
            this.mLocationPowerSaveMode = i;
        }

        public final String toString() {
            int i = this.mLocationPowerSaveMode;
            return "location power save mode changed to "
                    .concat(
                            i != 0
                                    ? i != 1
                                            ? i != 2
                                                    ? i != 3
                                                            ? i != 4
                                                                    ? "UNKNOWN"
                                                                    : "THROTTLE_REQUESTS_WHEN_SCREEN_OFF"
                                                            : "FOREGROUND_ONLY"
                                                    : "ALL_DISABLED_WHEN_SCREEN_OFF"
                                            : "GPS_DISABLED_WHEN_SCREEN_OFF"
                                    : "NO_CHANGE");
        }
    }

    public final class LocationsEventLog extends LocalEventLog {}

    public final class ProviderClientPermittedEvent extends ProviderEvent {
        public final /* synthetic */ int $r8$classId;
        public final Object mIdentity;
        public final boolean mPermitted;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ ProviderClientPermittedEvent(
                String str, boolean z, Object obj, int i) {
            super(str);
            this.$r8$classId = i;
            this.mPermitted = z;
            this.mIdentity = obj;
        }

        public final String toString() {
            switch (this.$r8$classId) {
                case 0:
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mProvider);
                    sb.append(" provider client ");
                    sb.append((CallerIdentity) this.mIdentity);
                    sb.append(" -> ");
                    sb.append(this.mPermitted ? "permitted" : "unpermitted");
                    return sb.toString();
                case 1:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(this.mProvider);
                    sb2.append(" provider client ");
                    sb2.append((CallerIdentity) this.mIdentity);
                    sb2.append(" -> ");
                    sb2.append(this.mPermitted ? "foreground" : "background");
                    return sb2.toString();
                default:
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.mProvider);
                    sb3.append(" provider stationary/idle ");
                    sb3.append(this.mPermitted ? "throttled" : "unthrottled");
                    sb3.append(", request = ");
                    sb3.append((ProviderRequest) this.mIdentity);
                    return sb3.toString();
            }
        }
    }

    public final class ProviderClientRegisterEvent extends ProviderEvent {
        public final CallerIdentity mIdentity;
        public final LocationRequest mLocationRequest;
        public final boolean mRegistered;

        public ProviderClientRegisterEvent(
                String str,
                boolean z,
                CallerIdentity callerIdentity,
                LocationRequest locationRequest) {
            super(str);
            this.mRegistered = z;
            this.mIdentity = callerIdentity;
            this.mLocationRequest = locationRequest;
        }

        public final String toString() {
            boolean z = this.mRegistered;
            String str = this.mProvider;
            if (!z) {
                StringBuilder m =
                        Preconditions$$ExternalSyntheticOutline0.m(str, " provider -registration ");
                m.append(this.mIdentity);
                return m.toString();
            }
            StringBuilder m2 =
                    Preconditions$$ExternalSyntheticOutline0.m(str, " provider +registration ");
            m2.append(this.mIdentity);
            m2.append(" -> ");
            m2.append(this.mLocationRequest);
            return m2.toString();
        }
    }

    public final class ProviderDeliverLocationEvent extends ProviderEvent {
        public final CallerIdentity mIdentity;
        public final int mNumLocations;

        public ProviderDeliverLocationEvent(String str, int i, CallerIdentity callerIdentity) {
            super(str);
            this.mNumLocations = i;
            this.mIdentity = callerIdentity;
        }

        public final String toString() {
            return this.mProvider
                    + " provider delivered location["
                    + this.mNumLocations
                    + "] to "
                    + this.mIdentity;
        }
    }

    public final class ProviderEnabledEvent extends ProviderEvent {
        public final boolean mEnabled;
        public final int mUserId;

        public ProviderEnabledEvent(int i, String str, boolean z) {
            super(str);
            this.mUserId = i;
            this.mEnabled = z;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider [u");
            sb.append(this.mUserId);
            sb.append("] ");
            sb.append(this.mEnabled ? "enabled" : "disabled");
            return sb.toString();
        }
    }

    public abstract class ProviderEvent {
        public final String mProvider;

        public ProviderEvent(String str) {
            this.mProvider = str;
        }
    }

    public final class ProviderMockedEvent extends ProviderEvent {
        public final boolean mMocked;

        public ProviderMockedEvent(String str, boolean z) {
            super(str);
            this.mMocked = z;
        }

        public final String toString() {
            boolean z = this.mMocked;
            String str = this.mProvider;
            return z
                    ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " provider added mock provider override")
                    : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, " provider removed mock provider override");
        }
    }

    public final class ProviderReceiveLocationEvent extends ProviderEvent {
        public final int mNumLocations;

        public ProviderReceiveLocationEvent(String str, int i) {
            super(str);
            this.mNumLocations = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mProvider);
            sb.append(" provider received location[");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mNumLocations, sb, "]");
        }
    }

    public final class ProviderUpdateEvent extends ProviderEvent {
        public final ProviderRequest mRequest;

        public ProviderUpdateEvent(String str, ProviderRequest providerRequest) {
            super(str);
            this.mRequest = providerRequest;
        }

        public final String toString() {
            return this.mProvider + " provider request = " + this.mRequest;
        }
    }

    public final class UserSwitchedEvent {
        public final int mUserIdFrom;
        public final int mUserIdTo;

        public UserSwitchedEvent(int i, int i2) {
            this.mUserIdFrom = i;
            this.mUserIdTo = i2;
        }

        public final String toString() {
            return "current user switched from u" + this.mUserIdFrom + " to u" + this.mUserIdTo;
        }
    }

    public LocationEventLog() {
        super(600);
        this.mAggregateStats = new ArrayMap(4);
        this.mGnssMeasAggregateStats = new ArrayMap();
        this.mLocationsLog = new LocationsEventLog(400);
    }

    public final void addLog$1(Object obj) {
        addLog(SystemClock.elapsedRealtime(), obj);
    }

    public final AggregateStats getAggregateStats(String str, CallerIdentity callerIdentity) {
        AggregateStats aggregateStats;
        synchronized (this.mAggregateStats) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mAggregateStats.get(str);
                if (arrayMap == null) {
                    arrayMap = new ArrayMap(2);
                    this.mAggregateStats.put(str, arrayMap);
                }
                CallerIdentity forAggregation = CallerIdentity.forAggregation(callerIdentity);
                aggregateStats = (AggregateStats) arrayMap.get(forAggregation);
                if (aggregateStats == null) {
                    aggregateStats = new AggregateStats();
                    aggregateStats.mFastestIntervalMs = Long.MAX_VALUE;
                    aggregateStats.mSlowestIntervalMs = 0L;
                    arrayMap.put(forAggregation, aggregateStats);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return aggregateStats;
    }

    public final GnssMeasurementAggregateStats getGnssMeasurementAggregateStats(
            CallerIdentity callerIdentity) {
        GnssMeasurementAggregateStats gnssMeasurementAggregateStats;
        synchronized (this.mGnssMeasAggregateStats) {
            try {
                CallerIdentity forAggregation = CallerIdentity.forAggregation(callerIdentity);
                gnssMeasurementAggregateStats =
                        (GnssMeasurementAggregateStats)
                                this.mGnssMeasAggregateStats.get(forAggregation);
                if (gnssMeasurementAggregateStats == null) {
                    gnssMeasurementAggregateStats = new GnssMeasurementAggregateStats();
                    gnssMeasurementAggregateStats.mFastestIntervalMs = Long.MAX_VALUE;
                    gnssMeasurementAggregateStats.mSlowestIntervalMs = 0L;
                    this.mGnssMeasAggregateStats.put(forAggregation, gnssMeasurementAggregateStats);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return gnssMeasurementAggregateStats;
    }

    public final void iterate(String str, Consumer consumer) {
        LocationEventLog$$ExternalSyntheticLambda0 locationEventLog$$ExternalSyntheticLambda0 =
                new LocationEventLog$$ExternalSyntheticLambda0(
                        str,
                        new StringBuilder(),
                        System.currentTimeMillis() - SystemClock.elapsedRealtime(),
                        consumer);
        synchronized (this) {
            LocalEventLog.iterate(
                    locationEventLog$$ExternalSyntheticLambda0, this, this.mLocationsLog);
        }
    }

    public final void logProviderClientForeground(String str, CallerIdentity callerIdentity) {
        boolean z = true;
        addLog$1(new ProviderClientPermittedEvent(str, z, callerIdentity, 1));
        AggregateStats aggregateStats = getAggregateStats(str, callerIdentity);
        synchronized (aggregateStats) {
            if (aggregateStats.mAddedRequestCount <= 0) {
                z = false;
            }
            Preconditions.checkState(z);
            int i = aggregateStats.mForegroundRequestCount;
            aggregateStats.mForegroundRequestCount = i + 1;
            if (i == 0) {
                aggregateStats.mForegroundTimeLastUpdateRealtimeMs = SystemClock.elapsedRealtime();
            }
        }
    }

    public final void logProviderDeliveredLocations(
            String str, int i, CallerIdentity callerIdentity) {
        synchronized (this) {
            LocationsEventLog locationsEventLog = this.mLocationsLog;
            locationsEventLog.getClass();
            locationsEventLog.addLog(
                    SystemClock.elapsedRealtime(),
                    new ProviderDeliverLocationEvent(str, i, callerIdentity));
        }
        AggregateStats aggregateStats = getAggregateStats(str, callerIdentity);
        synchronized (aggregateStats) {
            aggregateStats.mDeliveredLocationCount++;
        }
    }
}
