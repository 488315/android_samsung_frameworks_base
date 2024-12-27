package com.android.server.timezonedetector.location;

import android.app.ActivityManagerInternal;
import android.app.time.LocationTimeZoneAlgorithmStatus;
import android.os.SystemClock;
import android.service.timezone.TimeZoneProviderEvent;
import android.service.timezone.TimeZoneProviderStatus;
import android.service.timezone.TimeZoneProviderSuggestion;
import android.util.IndentingPrintWriter;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalServices;
import com.android.server.timedetector.ServerFlags;
import com.android.server.timezonedetector.ConfigurationInternal;
import com.android.server.timezonedetector.Dumpable;
import com.android.server.timezonedetector.GeolocationTimeZoneSuggestion;
import com.android.server.timezonedetector.LocationAlgorithmEvent;
import com.android.server.timezonedetector.ReferenceWithHistory;
import com.android.server.timezonedetector.ServiceConfigAccessorImpl;
import com.android.server.timezonedetector.TimeZoneDetectorInternalImpl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;

public final class LocationTimeZoneProviderController implements Dumpable {
    public LocationTimeZoneProviderControllerCallbackImpl mCallback;
    public ConfigurationInternal mCurrentUserConfiguration;
    public LocationTimeZoneProviderControllerEnvironmentImpl mEnvironment;
    public LocationAlgorithmEvent mLastEvent;
    public final RealControllerMetricsLogger mMetricsLogger;
    public final LocationTimeZoneProvider mPrimaryProvider;
    public final boolean mRecordStateChanges;
    public final ArrayList mRecordedStates = new ArrayList(0);
    public final LocationTimeZoneProvider mSecondaryProvider;
    public final Object mSharedLock;
    public final ReferenceWithHistory mState;
    public final HandlerThreadingDomain mThreadingDomain;
    public final ThreadingDomain$SingleRunnableQueue mUncertaintyTimeoutQueue;

    public LocationTimeZoneProviderController(
            HandlerThreadingDomain handlerThreadingDomain,
            RealControllerMetricsLogger realControllerMetricsLogger,
            LocationTimeZoneProvider locationTimeZoneProvider,
            LocationTimeZoneProvider locationTimeZoneProvider2,
            boolean z) {
        ReferenceWithHistory referenceWithHistory = new ReferenceWithHistory(10);
        this.mState = referenceWithHistory;
        Objects.requireNonNull(handlerThreadingDomain);
        this.mThreadingDomain = handlerThreadingDomain;
        Object obj = handlerThreadingDomain.mLockObject;
        this.mSharedLock = obj;
        this.mUncertaintyTimeoutQueue =
                new ThreadingDomain$SingleRunnableQueue(handlerThreadingDomain);
        this.mMetricsLogger = realControllerMetricsLogger;
        this.mPrimaryProvider = locationTimeZoneProvider;
        this.mSecondaryProvider = locationTimeZoneProvider2;
        this.mRecordStateChanges = z;
        synchronized (obj) {
            referenceWithHistory.set("UNKNOWN");
        }
    }

    public static void stopProviderIfStarted(LocationTimeZoneProvider locationTimeZoneProvider) {
        if (locationTimeZoneProvider.getCurrentState().isStarted()) {
            switch (locationTimeZoneProvider.getCurrentState().stateEnum) {
                case 1:
                case 2:
                case 3:
                    LocationTimeZoneManagerService.debugLog("Stopping " + locationTimeZoneProvider);
                    locationTimeZoneProvider.mThreadingDomain.assertCurrentThread();
                    synchronized (locationTimeZoneProvider.mSharedLock) {
                        try {
                            LocationTimeZoneProvider.ProviderState providerState =
                                    (LocationTimeZoneProvider.ProviderState)
                                            locationTimeZoneProvider.mCurrentState.get();
                            if (!providerState.isStarted()) {
                                throw new IllegalStateException(
                                        "Required a started state, but was " + providerState);
                            }
                            locationTimeZoneProvider.setCurrentState(
                                    ((LocationTimeZoneProvider.ProviderState)
                                                    locationTimeZoneProvider.mCurrentState.get())
                                            .newState(4, null, null, "stopUpdates"),
                                    false);
                            locationTimeZoneProvider.cancelInitializationTimeoutIfSet();
                            locationTimeZoneProvider.onStopUpdates();
                        } finally {
                        }
                    }
                    return;
                case 4:
                    LocationTimeZoneManagerService.debugLog(
                            "No need to stop " + locationTimeZoneProvider + ": already stopped");
                    return;
                case 5:
                case 6:
                    LocationTimeZoneManagerService.debugLog(
                            "Unable to stop " + locationTimeZoneProvider + ": it is terminated.");
                    return;
                default:
                    LocationTimeZoneManagerService.warnLog(
                            "Unknown provider state: " + locationTimeZoneProvider, null);
                    return;
            }
        }
    }

    public final void alterProvidersStartedStateIfRequired(
            ConfigurationInternal configurationInternal,
            ConfigurationInternal configurationInternal2) {
        boolean z =
                configurationInternal != null
                        && configurationInternal.isGeoDetectionExecutionEnabled();
        boolean isGeoDetectionExecutionEnabled =
                configurationInternal2.isGeoDetectionExecutionEnabled();
        if (z == isGeoDetectionExecutionEnabled) {
            return;
        }
        if (!isGeoDetectionExecutionEnabled) {
            stopProviders("Geo detection behavior disabled");
            return;
        }
        setStateAndReportStatusOnlyEvent("INITIALIZING", "initializing()");
        LocationTimeZoneProvider locationTimeZoneProvider = this.mPrimaryProvider;
        tryStartProvider(locationTimeZoneProvider, configurationInternal2);
        if (locationTimeZoneProvider.getCurrentState().isStarted()) {
            return;
        }
        LocationTimeZoneProvider locationTimeZoneProvider2 = this.mSecondaryProvider;
        tryStartProvider(locationTimeZoneProvider2, configurationInternal2);
        if (locationTimeZoneProvider2.getCurrentState().isStarted()) {
            return;
        }
        setStateAndReportStatusOnlyEvent(
                "FAILED",
                "Providers are failed: primary="
                        + locationTimeZoneProvider.getCurrentState()
                        + " secondary="
                        + locationTimeZoneProvider.getCurrentState());
    }

    public final void destroy() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            stopProviders("destroy()");
            this.mPrimaryProvider.destroy();
            this.mSecondaryProvider.destroy();
            setStateAndReportStatusOnlyEvent("DESTROYED", "destroy()");
        }
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("LocationTimeZoneProviderController:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(
                    "mCurrentUserConfiguration=" + this.mCurrentUserConfiguration);
            StringBuilder sb = new StringBuilder("providerInitializationTimeout=");
            ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                    (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
            Duration duration = ServiceConfigAccessorImpl.DEFAULT_LTZP_INITIALIZATION_TIMEOUT;
            serviceConfigAccessorImpl.mServerFlags.getClass();
            sb.append(ServerFlags.getDurationFromMillis("ltzp_init_timeout_millis", duration));
            indentingPrintWriter.println(sb.toString());
            StringBuilder sb2 = new StringBuilder("providerInitializationTimeoutFuzz=");
            ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                    (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
            Duration duration2 = ServiceConfigAccessorImpl.DEFAULT_LTZP_INITIALIZATION_TIMEOUT_FUZZ;
            serviceConfigAccessorImpl2.mServerFlags.getClass();
            sb2.append(
                    ServerFlags.getDurationFromMillis("ltzp_init_timeout_fuzz_millis", duration2));
            indentingPrintWriter.println(sb2.toString());
            StringBuilder sb3 = new StringBuilder("uncertaintyDelay=");
            ServiceConfigAccessorImpl serviceConfigAccessorImpl3 =
                    (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
            Duration duration3 = ServiceConfigAccessorImpl.DEFAULT_LTZP_UNCERTAINTY_DELAY;
            serviceConfigAccessorImpl3.mServerFlags.getClass();
            sb3.append(
                    ServerFlags.getDurationFromMillis(
                            "location_time_zone_detection_uncertainty_delay_millis", duration3));
            indentingPrintWriter.println(sb3.toString());
            indentingPrintWriter.println("mState=" + ((String) this.mState.get()));
            indentingPrintWriter.println("mLastEvent=" + this.mLastEvent);
            indentingPrintWriter.println("State history:");
            indentingPrintWriter.increaseIndent();
            this.mState.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Primary Provider:");
            indentingPrintWriter.increaseIndent();
            this.mPrimaryProvider.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Secondary Provider:");
            indentingPrintWriter.increaseIndent();
            this.mSecondaryProvider.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final LocationTimeZoneAlgorithmStatus generateCurrentAlgorithmStatus() {
        char c;
        String str = (String) this.mState.get();
        LocationTimeZoneProvider.ProviderState currentState =
                this.mPrimaryProvider.getCurrentState();
        LocationTimeZoneProvider.ProviderState currentState2 =
                this.mSecondaryProvider.getCurrentState();
        switch (str.hashCode()) {
            case -1166336595:
                if (str.equals("STOPPED")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -468307734:
                if (str.equals("PROVIDERS_INITIALIZING")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 478389753:
                if (str.equals("DESTROYED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 872357833:
                if (str.equals("UNCERTAIN")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1386911874:
                if (str.equals("CERTAIN")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1917201485:
                if (str.equals("INITIALIZING")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 2066319421:
                if (str.equals("FAILED")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        int i = (c == 0 || c == 1 || c == 2 || c == 3) ? 3 : 2;
        int providerStatus = currentState.getProviderStatus();
        int providerStatus2 = currentState2.getProviderStatus();
        TimeZoneProviderEvent timeZoneProviderEvent = currentState.event;
        TimeZoneProviderStatus timeZoneProviderStatus =
                timeZoneProviderEvent == null
                        ? null
                        : timeZoneProviderEvent.getTimeZoneProviderStatus();
        TimeZoneProviderEvent timeZoneProviderEvent2 = currentState2.event;
        return new LocationTimeZoneAlgorithmStatus(
                i,
                providerStatus,
                timeZoneProviderStatus,
                providerStatus2,
                timeZoneProviderEvent2 == null
                        ? null
                        : timeZoneProviderEvent2.getTimeZoneProviderStatus());
    }

    public final LocationTimeZoneManagerServiceState getStateForTests() {
        LocationTimeZoneManagerServiceState locationTimeZoneManagerServiceState;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            LocationTimeZoneManagerServiceState.Builder builder =
                    new LocationTimeZoneManagerServiceState.Builder();
            LocationAlgorithmEvent locationAlgorithmEvent = this.mLastEvent;
            if (locationAlgorithmEvent != null) {
                Objects.requireNonNull(locationAlgorithmEvent);
                builder.mLastEvent = locationAlgorithmEvent;
            }
            builder.mControllerState = (String) this.mState.get();
            builder.mControllerStates = new ArrayList(this.mRecordedStates);
            builder.mPrimaryProviderStates =
                    new ArrayList(this.mPrimaryProvider.getRecordedStates());
            builder.mSecondaryProviderStates =
                    new ArrayList(this.mSecondaryProvider.getRecordedStates());
            locationTimeZoneManagerServiceState = new LocationTimeZoneManagerServiceState(builder);
        }
        return locationTimeZoneManagerServiceState;
    }

    public long getUncertaintyTimeoutDelayMillis() {
        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue =
                this.mUncertaintyTimeoutQueue;
        threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
        if (threadingDomain$SingleRunnableQueue.mIsQueued) {
            return threadingDomain$SingleRunnableQueue.mDelayMillis;
        }
        throw new IllegalStateException("No item queued");
    }

    public final void handleProviderFailedStateChange(
            LocationTimeZoneProvider.ProviderState providerState) {
        LocationTimeZoneProvider locationTimeZoneProvider = this.mPrimaryProvider;
        LocationTimeZoneProvider.ProviderState currentState =
                locationTimeZoneProvider.getCurrentState();
        LocationTimeZoneProvider locationTimeZoneProvider2 = this.mSecondaryProvider;
        LocationTimeZoneProvider.ProviderState currentState2 =
                locationTimeZoneProvider2.getCurrentState();
        LocationTimeZoneProvider locationTimeZoneProvider3 = providerState.provider;
        if (locationTimeZoneProvider3 == locationTimeZoneProvider) {
            if (!currentState2.isTerminated()) {
                tryStartProvider(locationTimeZoneProvider2, this.mCurrentUserConfiguration);
            }
        } else if (locationTimeZoneProvider3 == locationTimeZoneProvider2
                && currentState.stateEnum != 3
                && !currentState.isTerminated()) {
            StringBuilder sb =
                    new StringBuilder(
                            "Secondary provider unexpected reported a failure: failed provider=");
            locationTimeZoneProvider3.mThreadingDomain.assertCurrentThread();
            sb.append(locationTimeZoneProvider3.mProviderName);
            sb.append(", primary provider=");
            sb.append(locationTimeZoneProvider);
            sb.append(", secondary provider=");
            sb.append(locationTimeZoneProvider2);
            LocationTimeZoneManagerService.warnLog(sb.toString(), null);
        }
        if (currentState.isTerminated() && currentState2.isTerminated()) {
            this.mUncertaintyTimeoutQueue.cancel();
            setStateAndReportStatusOnlyEvent(
                    "FAILED",
                    "Both providers are terminated: primary="
                            + currentState.provider
                            + ", secondary="
                            + currentState2.provider);
        }
    }

    public final void handleProviderStartedStateChange(
            LocationTimeZoneProvider.ProviderState providerState) {
        TimeZoneProviderEvent timeZoneProviderEvent = providerState.event;
        LocationTimeZoneProvider locationTimeZoneProvider = providerState.provider;
        if (timeZoneProviderEvent == null) {
            this.mEnvironment.getClass();
            handleProviderUncertainty(
                    locationTimeZoneProvider,
                    SystemClock.elapsedRealtime(),
                    "provider=" + locationTimeZoneProvider + ", implicit uncertainty, event=null");
            return;
        }
        if (!this.mCurrentUserConfiguration.isGeoDetectionExecutionEnabled()) {
            LocationTimeZoneManagerService.warnLog(
                    "Provider="
                            + locationTimeZoneProvider
                            + " is started, but currentUserConfiguration="
                            + this.mCurrentUserConfiguration
                            + " suggests it shouldn't be.",
                    null);
        }
        int type = timeZoneProviderEvent.getType();
        if (type == 1) {
            LocationTimeZoneManagerService.warnLog(
                    "Provider="
                            + locationTimeZoneProvider
                            + " is started, but event suggests it shouldn't be",
                    null);
            return;
        }
        if (type != 2) {
            if (type != 3) {
                LocationTimeZoneManagerService.warnLog(
                        "Unknown eventType=" + timeZoneProviderEvent.getType(), null);
                return;
            } else {
                handleProviderUncertainty(
                        locationTimeZoneProvider,
                        timeZoneProviderEvent.getCreationElapsedMillis(),
                        "provider="
                                + locationTimeZoneProvider
                                + ", explicit uncertainty. event="
                                + timeZoneProviderEvent);
                return;
            }
        }
        this.mUncertaintyTimeoutQueue.cancel();
        if (locationTimeZoneProvider == this.mPrimaryProvider) {
            stopProviderIfStarted(this.mSecondaryProvider);
        }
        TimeZoneProviderSuggestion suggestion = timeZoneProviderEvent.getSuggestion();
        setState("CERTAIN");
        GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion =
                new GeolocationTimeZoneSuggestion(
                        suggestion.getElapsedRealtimeMillis(), suggestion.getTimeZoneIds());
        StringBuilder sb = new StringBuilder("Provider event received: provider=");
        sb.append(locationTimeZoneProvider);
        sb.append(", providerEvent=");
        sb.append(timeZoneProviderEvent);
        sb.append(", suggestionCreationTime=");
        this.mEnvironment.getClass();
        sb.append(SystemClock.elapsedRealtime());
        reportSuggestionEvent(geolocationTimeZoneSuggestion, sb.toString());
    }

    public final void handleProviderUncertainty(
            final LocationTimeZoneProvider locationTimeZoneProvider, final long j, String str) {
        Objects.requireNonNull(locationTimeZoneProvider);
        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue =
                this.mUncertaintyTimeoutQueue;
        threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
        boolean z = threadingDomain$SingleRunnableQueue.mIsQueued;
        LocationTimeZoneProvider locationTimeZoneProvider2 = this.mSecondaryProvider;
        LocationTimeZoneProvider locationTimeZoneProvider3 = this.mPrimaryProvider;
        if (!z) {
            if ("UNCERTAIN".equals(this.mState.get())) {
                GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion =
                        new GeolocationTimeZoneSuggestion(j, null);
                StringBuilder sb = new StringBuilder("Uncertainty received from ");
                locationTimeZoneProvider.mThreadingDomain.assertCurrentThread();
                sb.append(locationTimeZoneProvider.mProviderName);
                sb.append(": primary=");
                sb.append(locationTimeZoneProvider3);
                sb.append(", secondary=");
                sb.append(locationTimeZoneProvider2);
                sb.append(", uncertaintyStarted=");
                sb.append(Duration.ofMillis(j));
                reportSuggestionEvent(geolocationTimeZoneSuggestion, sb.toString());
            } else {
                LocationTimeZoneManagerService.debugLog(
                        "Starting uncertainty timeout: reason=" + str);
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
                Duration duration = ServiceConfigAccessorImpl.DEFAULT_LTZP_UNCERTAINTY_DELAY;
                serviceConfigAccessorImpl.mServerFlags.getClass();
                final Duration durationFromMillis =
                        ServerFlags.getDurationFromMillis(
                                "location_time_zone_detection_uncertainty_delay_millis", duration);
                Runnable runnable =
                        new Runnable() { // from class:
                                         // com.android.server.timezonedetector.location.LocationTimeZoneProviderController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                LocationTimeZoneProviderController
                                        locationTimeZoneProviderController =
                                                LocationTimeZoneProviderController.this;
                                LocationTimeZoneProvider locationTimeZoneProvider4 =
                                        locationTimeZoneProvider;
                                long j2 = j;
                                Duration duration2 = durationFromMillis;
                                locationTimeZoneProviderController.mThreadingDomain
                                        .assertCurrentThread();
                                synchronized (locationTimeZoneProviderController.mSharedLock) {
                                    locationTimeZoneProviderController.mEnvironment.getClass();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    locationTimeZoneProviderController.setState("UNCERTAIN");
                                    GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion2 =
                                            new GeolocationTimeZoneSuggestion(j2, null);
                                    StringBuilder sb2 =
                                            new StringBuilder("Uncertainty timeout triggered for ");
                                    locationTimeZoneProvider4.mThreadingDomain
                                            .assertCurrentThread();
                                    sb2.append(locationTimeZoneProvider4.mProviderName);
                                    sb2.append(": primary=");
                                    sb2.append(locationTimeZoneProviderController.mPrimaryProvider);
                                    sb2.append(", secondary=");
                                    sb2.append(
                                            locationTimeZoneProviderController.mSecondaryProvider);
                                    sb2.append(", uncertaintyStarted=");
                                    sb2.append(Duration.ofMillis(j2));
                                    sb2.append(", afterUncertaintyTimeout=");
                                    sb2.append(Duration.ofMillis(elapsedRealtime));
                                    sb2.append(", uncertaintyDelay=");
                                    sb2.append(duration2);
                                    locationTimeZoneProviderController.reportSuggestionEvent(
                                            geolocationTimeZoneSuggestion2, sb2.toString());
                                }
                            }
                        };
                long millis = durationFromMillis.toMillis();
                threadingDomain$SingleRunnableQueue.cancel();
                threadingDomain$SingleRunnableQueue.mIsQueued = true;
                threadingDomain$SingleRunnableQueue.mDelayMillis = millis;
                threadingDomain$SingleRunnableQueue.this$0.mHandler.postDelayed(
                        new ThreadingDomain$SingleRunnableQueue$$ExternalSyntheticLambda0(
                                threadingDomain$SingleRunnableQueue, runnable),
                        threadingDomain$SingleRunnableQueue,
                        millis);
            }
        }
        if (locationTimeZoneProvider == locationTimeZoneProvider3) {
            tryStartProvider(locationTimeZoneProvider2, this.mCurrentUserConfiguration);
        }
    }

    public final void initialize(
            LocationTimeZoneProviderControllerEnvironmentImpl
                    locationTimeZoneProviderControllerEnvironmentImpl,
            LocationTimeZoneProviderControllerCallbackImpl
                    locationTimeZoneProviderControllerCallbackImpl) {
        ConfigurationInternal configurationInternal;
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            try {
                LocationTimeZoneManagerService.debugLog("initialize()");
                this.mEnvironment = locationTimeZoneProviderControllerEnvironmentImpl;
                this.mCallback = locationTimeZoneProviderControllerCallbackImpl;
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl)
                                locationTimeZoneProviderControllerEnvironmentImpl
                                        .mServiceConfigAccessor;
                synchronized (serviceConfigAccessorImpl) {
                    configurationInternal =
                            serviceConfigAccessorImpl.getConfigurationInternal(
                                    ((ActivityManagerInternal)
                                                    LocalServices.getService(
                                                            ActivityManagerInternal.class))
                                            .getCurrentUserId());
                }
                this.mCurrentUserConfiguration = configurationInternal;
                LocationTimeZoneProviderController$$ExternalSyntheticLambda0
                        locationTimeZoneProviderController$$ExternalSyntheticLambda0 =
                                new LocationTimeZoneProviderController$$ExternalSyntheticLambda0(
                                        this);
                setState("PROVIDERS_INITIALIZING");
                this.mPrimaryProvider.initialize(
                        locationTimeZoneProviderController$$ExternalSyntheticLambda0);
                this.mSecondaryProvider.initialize(
                        locationTimeZoneProviderController$$ExternalSyntheticLambda0);
                setStateAndReportStatusOnlyEvent("STOPPED", "initialize()");
                alterProvidersStartedStateIfRequired(null, this.mCurrentUserConfiguration);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUncertaintyTimeoutSet() {
        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue =
                this.mUncertaintyTimeoutQueue;
        threadingDomain$SingleRunnableQueue.this$0.assertCurrentThread();
        return threadingDomain$SingleRunnableQueue.mIsQueued;
    }

    public final void reportEvent(final LocationAlgorithmEvent locationAlgorithmEvent) {
        LocationTimeZoneManagerService.debugLog(
                "makeSuggestion: suggestion=" + locationAlgorithmEvent);
        this.mCallback.mThreadingDomain.assertCurrentThread();
        final TimeZoneDetectorInternalImpl timeZoneDetectorInternalImpl =
                (TimeZoneDetectorInternalImpl)
                        LocalServices.getService(TimeZoneDetectorInternalImpl.class);
        timeZoneDetectorInternalImpl.getClass();
        timeZoneDetectorInternalImpl.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.timezonedetector.TimeZoneDetectorInternalImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TimeZoneDetectorInternalImpl timeZoneDetectorInternalImpl2 =
                                TimeZoneDetectorInternalImpl.this;
                        ((TimeZoneDetectorStrategyImpl)
                                        timeZoneDetectorInternalImpl2.mTimeZoneDetectorStrategy)
                                .handleLocationAlgorithmEvent(locationAlgorithmEvent);
                    }
                });
        this.mLastEvent = locationAlgorithmEvent;
    }

    public final void reportSuggestionEvent(
            GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion, String str) {
        LocationAlgorithmEvent locationAlgorithmEvent =
                new LocationAlgorithmEvent(
                        generateCurrentAlgorithmStatus(), geolocationTimeZoneSuggestion);
        locationAlgorithmEvent.addDebugInfo(str);
        reportEvent(locationAlgorithmEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void setState(String str) {
        char c;
        ReferenceWithHistory referenceWithHistory = this.mState;
        if (Objects.equals(referenceWithHistory.get(), str)) {
            return;
        }
        referenceWithHistory.set(str);
        if (this.mRecordStateChanges) {
            this.mRecordedStates.add(str);
        }
        this.mMetricsLogger.getClass();
        int i = 1;
        switch (str.hashCode()) {
            case -1166336595:
                if (str.equals("STOPPED")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -468307734:
                if (str.equals("PROVIDERS_INITIALIZING")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 478389753:
                if (str.equals("DESTROYED")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 872357833:
                if (str.equals("UNCERTAIN")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1386911874:
                if (str.equals("CERTAIN")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1917201485:
                if (str.equals("INITIALIZING")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2066319421:
                if (str.equals("FAILED")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                break;
            case 1:
                i = 2;
                break;
            case 2:
                i = 3;
                break;
            case 3:
                i = 4;
                break;
            case 4:
                i = 5;
                break;
            case 5:
                i = 6;
                break;
            case 6:
                i = 7;
                break;
            default:
                i = 0;
                break;
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.LOCATION_TIME_ZONE_PROVIDER_CONTROLLER_STATE_CHANGED, i);
    }

    public final void setStateAndReportStatusOnlyEvent(String str, String str2) {
        setState(str);
        LocationAlgorithmEvent locationAlgorithmEvent =
                new LocationAlgorithmEvent(generateCurrentAlgorithmStatus(), null);
        locationAlgorithmEvent.addDebugInfo(str2);
        reportEvent(locationAlgorithmEvent);
    }

    public final void stopProviders(String str) {
        stopProviderIfStarted(this.mPrimaryProvider);
        stopProviderIfStarted(this.mSecondaryProvider);
        this.mUncertaintyTimeoutQueue.cancel();
        setStateAndReportStatusOnlyEvent("STOPPED", "Providers stopped: " + str);
    }

    public final void tryStartProvider(
            final LocationTimeZoneProvider locationTimeZoneProvider,
            ConfigurationInternal configurationInternal) {
        switch (locationTimeZoneProvider.getCurrentState().stateEnum) {
            case 1:
            case 2:
            case 3:
                LocationTimeZoneManagerService.debugLog(
                        "No need to start " + locationTimeZoneProvider + ": already started");
                return;
            case 4:
                LocationTimeZoneManagerService.debugLog("Enabling " + locationTimeZoneProvider);
                ServiceConfigAccessorImpl serviceConfigAccessorImpl =
                        (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
                Duration duration = ServiceConfigAccessorImpl.DEFAULT_LTZP_INITIALIZATION_TIMEOUT;
                serviceConfigAccessorImpl.mServerFlags.getClass();
                Duration durationFromMillis =
                        ServerFlags.getDurationFromMillis("ltzp_init_timeout_millis", duration);
                ServiceConfigAccessorImpl serviceConfigAccessorImpl2 =
                        (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
                Duration duration2 =
                        ServiceConfigAccessorImpl.DEFAULT_LTZP_INITIALIZATION_TIMEOUT_FUZZ;
                serviceConfigAccessorImpl2.mServerFlags.getClass();
                Duration durationFromMillis2 =
                        ServerFlags.getDurationFromMillis(
                                "ltzp_init_timeout_fuzz_millis", duration2);
                ServiceConfigAccessorImpl serviceConfigAccessorImpl3 =
                        (ServiceConfigAccessorImpl) this.mEnvironment.mServiceConfigAccessor;
                Duration duration3 =
                        ServiceConfigAccessorImpl.DEFAULT_LTZP_EVENT_FILTER_AGE_THRESHOLD;
                serviceConfigAccessorImpl3.mServerFlags.getClass();
                Duration durationFromMillis3 =
                        ServerFlags.getDurationFromMillis(
                                "ltzp_event_filtering_age_threshold_millis", duration3);
                locationTimeZoneProvider.mThreadingDomain.assertCurrentThread();
                synchronized (locationTimeZoneProvider.mSharedLock) {
                    try {
                        LocationTimeZoneProvider.ProviderState providerState =
                                (LocationTimeZoneProvider.ProviderState)
                                        locationTimeZoneProvider.mCurrentState.get();
                        if (providerState.stateEnum != 4) {
                            throw new IllegalStateException(
                                    "Required stateEnum=4, but was " + providerState);
                        }
                        locationTimeZoneProvider.setCurrentState(
                                ((LocationTimeZoneProvider.ProviderState)
                                                locationTimeZoneProvider.mCurrentState.get())
                                        .newState(1, null, configurationInternal, "startUpdates"),
                                false);
                        Duration plus = durationFromMillis.plus(durationFromMillis2);
                        ThreadingDomain$SingleRunnableQueue threadingDomain$SingleRunnableQueue =
                                locationTimeZoneProvider.mInitializationTimeoutQueue;
                        Runnable runnable = new Runnable() { // from class:
                                    // com.android.server.timezonedetector.location.LocationTimeZoneProvider$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LocationTimeZoneProvider locationTimeZoneProvider2 =
                                                LocationTimeZoneProvider.this;
                                        locationTimeZoneProvider2.mThreadingDomain
                                                .assertCurrentThread();
                                        synchronized (locationTimeZoneProvider2.mSharedLock) {
                                            try {
                                                LocationTimeZoneProvider.ProviderState
                                                        providerState2 =
                                                                (LocationTimeZoneProvider
                                                                                .ProviderState)
                                                                        locationTimeZoneProvider2
                                                                                .mCurrentState
                                                                                .get();
                                                if (providerState2.stateEnum == 1) {
                                                    locationTimeZoneProvider2.setCurrentState(
                                                            providerState2.newState(
                                                                    3,
                                                                    null,
                                                                    providerState2
                                                                            .currentUserConfiguration,
                                                                    "handleInitializationTimeout"),
                                                            true);
                                                } else {
                                                    LocationTimeZoneManagerService.warnLog(
                                                            "handleInitializationTimeout:"
                                                                + " Initialization timeout"
                                                                + " triggered when in an unexpected"
                                                                + " state="
                                                                    + providerState2,
                                                            null);
                                                }
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                    }
                                };
                        long millis = plus.toMillis();
                        threadingDomain$SingleRunnableQueue.cancel();
                        threadingDomain$SingleRunnableQueue.mIsQueued = true;
                        threadingDomain$SingleRunnableQueue.mDelayMillis = millis;
                        threadingDomain$SingleRunnableQueue.this$0.mHandler.postDelayed(
                                new ThreadingDomain$SingleRunnableQueue$$ExternalSyntheticLambda0(
                                        threadingDomain$SingleRunnableQueue, runnable),
                                threadingDomain$SingleRunnableQueue,
                                millis);
                        locationTimeZoneProvider.onStartUpdates(
                                durationFromMillis, durationFromMillis3);
                    } finally {
                    }
                }
                return;
            case 5:
            case 6:
                LocationTimeZoneManagerService.debugLog(
                        "Unable to start " + locationTimeZoneProvider + ": it is terminated");
                return;
            default:
                throw new IllegalStateException(
                        "Unknown provider state: provider=" + locationTimeZoneProvider);
        }
    }
}
