package com.android.server.timezonedetector.location;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.service.timezone.TimeZoneProviderEvent;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.timezonedetector.ConfigurationInternal;
import com.android.server.timezonedetector.Dumpable;
import com.android.server.timezonedetector.ReferenceWithHistory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class LocationTimeZoneProvider implements Dumpable {
    public final ThreadingDomain$SingleRunnableQueue mInitializationTimeoutQueue;
    public LocationTimeZoneProviderController$$ExternalSyntheticLambda0 mProviderListener;
    public final RealProviderMetricsLogger mProviderMetricsLogger;
    public final String mProviderName;
    public final boolean mRecordStateChanges;
    public final Object mSharedLock;
    public final HandlerThreadingDomain mThreadingDomain;
    public final TimeZoneProviderEventPreProcessor mTimeZoneProviderEventPreProcessor;
    public final ArrayList mRecordedStates = new ArrayList(0);
    public final ReferenceWithHistory mCurrentState = new ReferenceWithHistory(10);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProviderState {
        public final ConfigurationInternal currentUserConfiguration;
        public final TimeZoneProviderEvent event;
        public final String mDebugInfo;
        public final long mStateEntryTimeMillis;
        public final LocationTimeZoneProvider provider;
        public final int stateEnum;

        public ProviderState(LocationTimeZoneProvider locationTimeZoneProvider, int i, TimeZoneProviderEvent timeZoneProviderEvent, ConfigurationInternal configurationInternal, String str) {
            Objects.requireNonNull(locationTimeZoneProvider);
            this.provider = locationTimeZoneProvider;
            this.stateEnum = i;
            this.event = timeZoneProviderEvent;
            this.currentUserConfiguration = configurationInternal;
            this.mStateEntryTimeMillis = SystemClock.elapsedRealtime();
            this.mDebugInfo = str;
        }

        public static String prettyPrintStateEnum(int i) {
            switch (i) {
                case 1:
                    return "Started initializing (1)";
                case 2:
                    return "Started certain (2)";
                case 3:
                    return "Started uncertain (3)";
                case 4:
                    return "Stopped (4)";
                case 5:
                    return "Perm failure (5)";
                case 6:
                    return "Destroyed (6)";
                default:
                    return BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "Unknown (", ")");
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ProviderState.class != obj.getClass()) {
                return false;
            }
            ProviderState providerState = (ProviderState) obj;
            return this.stateEnum == providerState.stateEnum && Objects.equals(this.event, providerState.event) && Objects.equals(this.currentUserConfiguration, providerState.currentUserConfiguration);
        }

        public final int getProviderStatus() {
            int i = this.stateEnum;
            switch (i) {
                case 1:
                    return 2;
                case 2:
                    return 3;
                case 3:
                    return 4;
                case 4:
                case 6:
                    return 2;
                case 5:
                    return 1;
                default:
                    throw new IllegalStateException("Unknown state enum:" + prettyPrintStateEnum(i));
            }
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.stateEnum), this.event, this.currentUserConfiguration);
        }

        public final boolean isStarted() {
            int i = this.stateEnum;
            return i == 1 || i == 2 || i == 3;
        }

        public final boolean isTerminated() {
            int i = this.stateEnum;
            return i == 5 || i == 6;
        }

        public final ProviderState newState(int i, TimeZoneProviderEvent timeZoneProviderEvent, ConfigurationInternal configurationInternal, String str) {
            int i2 = this.stateEnum;
            switch (i2) {
                case 0:
                    if (i != 4) {
                        throw new IllegalArgumentException("Must transition from " + prettyPrintStateEnum(0) + " to " + prettyPrintStateEnum(4));
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    break;
                case 5:
                case 6:
                    throw new IllegalArgumentException("Illegal transition out of " + prettyPrintStateEnum(i2));
                default:
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Invalid this.stateEnum="));
            }
            switch (i) {
                case 0:
                    throw new IllegalArgumentException("Cannot transition to " + prettyPrintStateEnum(0));
                case 1:
                case 2:
                case 3:
                    if (configurationInternal == null) {
                        throw new IllegalArgumentException("Started state: currentUserConfig must not be null");
                    }
                    break;
                case 4:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new IllegalArgumentException("Stopped state: event and currentUserConfig must be null, event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                case 5:
                case 6:
                    if (timeZoneProviderEvent != null || configurationInternal != null) {
                        throw new IllegalArgumentException("Terminal state: event and currentUserConfig must be null, newStateEnum=" + i + ", event=" + timeZoneProviderEvent + ", currentUserConfig=" + configurationInternal);
                    }
                default:
                    throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown newStateEnum="));
            }
            return new ProviderState(this.provider, i, timeZoneProviderEvent, configurationInternal, str);
        }

        public final String toString() {
            return "ProviderState{stateEnum=" + prettyPrintStateEnum(this.stateEnum) + ", event=" + this.event + ", currentUserConfiguration=" + this.currentUserConfiguration + ", mStateEntryTimeMillis=" + this.mStateEntryTimeMillis + ", mDebugInfo=" + this.mDebugInfo + '}';
        }
    }

    public LocationTimeZoneProvider(RealProviderMetricsLogger realProviderMetricsLogger, HandlerThreadingDomain handlerThreadingDomain, String str, TimeZoneProviderEventPreProcessor timeZoneProviderEventPreProcessor, boolean z) {
        Objects.requireNonNull(handlerThreadingDomain);
        this.mThreadingDomain = handlerThreadingDomain;
        this.mProviderMetricsLogger = realProviderMetricsLogger;
        this.mInitializationTimeoutQueue = new ThreadingDomain$SingleRunnableQueue(handlerThreadingDomain);
        this.mSharedLock = handlerThreadingDomain.mLockObject;
        Objects.requireNonNull(str);
        this.mProviderName = str;
        this.mTimeZoneProviderEventPreProcessor = timeZoneProviderEventPreProcessor;
        this.mRecordStateChanges = z;
    }

    public final void cancelInitializationTimeoutIfSet() {
        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue = this.mInitializationTimeoutQueue;
        threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
        if (threadingDomain$SingleRunnableQueue.mIsQueued) {
            threadingDomain$SingleRunnableQueue.cancel();
        }
    }

    public final void clearRecordedStates() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mRecordedStates.clear();
            this.mRecordedStates.trimToSize();
        }
    }

    public final void destroy() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                ProviderState providerState = (ProviderState) this.mCurrentState.get();
                if (!providerState.isTerminated()) {
                    setCurrentState(providerState.newState(6, null, null, "destroy"), false);
                    onDestroy();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ProviderState getCurrentState() {
        ProviderState providerState;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            providerState = (ProviderState) this.mCurrentState.get();
        }
        return providerState;
    }

    public Duration getInitializationTimeoutDelay() {
        Duration ofMillis;
        synchronized (this.mSharedLock) {
            try {
                ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue = this.mInitializationTimeoutQueue;
                threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
                if (!threadingDomain$SingleRunnableQueue.mIsQueued) {
                    throw new IllegalStateException("No item queued");
                }
                ofMillis = Duration.ofMillis(threadingDomain$SingleRunnableQueue.mDelayMillis);
            } catch (Throwable th) {
                throw th;
            }
        }
        return ofMillis;
    }

    public final List getRecordedStates() {
        ArrayList arrayList;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            arrayList = new ArrayList(this.mRecordedStates);
        }
        return arrayList;
    }

    public final void initialize(LocationTimeZoneProviderController$$ExternalSyntheticLambda0 locationTimeZoneProviderController$$ExternalSyntheticLambda0) {
        String str;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            if (this.mProviderListener != null) {
                throw new IllegalStateException("initialize already called");
            }
            this.mProviderListener = locationTimeZoneProviderController$$ExternalSyntheticLambda0;
            ProviderState newState = new ProviderState(this, 0, null, null, "Initial state").newState(4, null, null, "initialize");
            boolean z = false;
            setCurrentState(newState, false);
            try {
                str = "onInitialize() returned false";
                z = onInitialize();
            } catch (RuntimeException e) {
                LocationTimeZoneManagerService.warnLog("Unable to initialize the provider due to exception", e);
                str = "onInitialize() threw exception:" + e.getMessage();
            }
            if (!z) {
                setCurrentState(newState.newState(5, null, null, "Failed to initialize: " + str), true);
            }
        }
    }

    public boolean isInitializationTimeoutSet() {
        boolean z;
        synchronized (this.mSharedLock) {
            ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue = this.mInitializationTimeoutQueue;
            threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
            z = threadingDomain$SingleRunnableQueue.mIsQueued;
        }
        return z;
    }

    public abstract void onDestroy();

    public abstract boolean onInitialize();

    public abstract void onStartUpdates(Duration duration, Duration duration2);

    public abstract void onStopUpdates();

    public final void setCurrentState(ProviderState providerState, boolean z) {
        int i;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                ProviderState providerState2 = (ProviderState) this.mCurrentState.get();
                this.mCurrentState.set(providerState);
                if (!providerState.equals(providerState2)) {
                    RealProviderMetricsLogger realProviderMetricsLogger = this.mProviderMetricsLogger;
                    int i2 = providerState.stateEnum;
                    realProviderMetricsLogger.getClass();
                    switch (i2) {
                        case 1:
                            i = 1;
                            break;
                        case 2:
                            i = 2;
                            break;
                        case 3:
                            i = 3;
                            break;
                        case 4:
                            i = 4;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        default:
                            i = 0;
                            break;
                    }
                    FrameworkStatsLog.write(FrameworkStatsLog.LOCATION_TIME_ZONE_PROVIDER_STATE_CHANGED, realProviderMetricsLogger.mProviderIndex, i);
                    if (this.mRecordStateChanges) {
                        this.mRecordedStates.add(providerState);
                    }
                    if (z) {
                        this.mProviderListener.onProviderStateChange(providerState);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
