package com.android.server.timezonedetector.location;

import android.util.IndentingPrintWriter;

import com.android.server.servicewatcher.ServiceWatcherImpl;

import java.time.Duration;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BinderLocationTimeZoneProvider extends LocationTimeZoneProvider {
    public final RealLocationTimeZoneProviderProxy mProxy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.timezonedetector.location.BinderLocationTimeZoneProvider$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}

        public final void onProviderBound() {
            BinderLocationTimeZoneProvider binderLocationTimeZoneProvider =
                    BinderLocationTimeZoneProvider.this;
            binderLocationTimeZoneProvider.mThreadingDomain.assertCurrentThread();
            synchronized (binderLocationTimeZoneProvider.mSharedLock) {
                try {
                    LocationTimeZoneProvider.ProviderState providerState =
                            (LocationTimeZoneProvider.ProviderState)
                                    binderLocationTimeZoneProvider.mCurrentState.get();
                    switch (providerState.stateEnum) {
                        case 1:
                        case 2:
                        case 3:
                            LocationTimeZoneManagerService.debugLog(
                                    "handleOnProviderBound mProviderName="
                                            + binderLocationTimeZoneProvider.mProviderName
                                            + ", currentState="
                                            + providerState
                                            + ": Provider is started.");
                            break;
                        case 4:
                            LocationTimeZoneManagerService.debugLog(
                                    "handleOnProviderBound mProviderName="
                                            + binderLocationTimeZoneProvider.mProviderName
                                            + ", currentState="
                                            + providerState
                                            + ": Provider is stopped.");
                            break;
                        case 5:
                        case 6:
                            LocationTimeZoneManagerService.debugLog(
                                    "handleOnProviderBound, mProviderName="
                                            + binderLocationTimeZoneProvider.mProviderName
                                            + ", currentState="
                                            + providerState
                                            + ": No state change required, provider is"
                                            + " terminated.");
                            break;
                        default:
                            throw new IllegalStateException(
                                    "Unknown currentState=" + providerState);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onProviderUnbound() {
            BinderLocationTimeZoneProvider binderLocationTimeZoneProvider =
                    BinderLocationTimeZoneProvider.this;
            binderLocationTimeZoneProvider.mThreadingDomain.assertCurrentThread();
            synchronized (binderLocationTimeZoneProvider.mSharedLock) {
                try {
                    LocationTimeZoneProvider.ProviderState providerState =
                            (LocationTimeZoneProvider.ProviderState)
                                    binderLocationTimeZoneProvider.mCurrentState.get();
                    switch (providerState.stateEnum) {
                        case 1:
                        case 2:
                        case 3:
                            binderLocationTimeZoneProvider.setCurrentState(
                                    providerState.newState(
                                            3,
                                            null,
                                            providerState.currentUserConfiguration,
                                            "handleTemporaryFailure: reason=onProviderUnbound(),"
                                                + " currentState="
                                                    + LocationTimeZoneProvider.ProviderState
                                                            .prettyPrintStateEnum(
                                                                    providerState.stateEnum)),
                                    true);
                            binderLocationTimeZoneProvider.cancelInitializationTimeoutIfSet();
                            break;
                        case 4:
                            LocationTimeZoneManagerService.debugLog(
                                    "handleProviderLost reason=onProviderUnbound(), mProviderName="
                                            + binderLocationTimeZoneProvider.mProviderName
                                            + ", currentState="
                                            + providerState
                                            + ": No state change required, provider is stopped.");
                            break;
                        case 5:
                        case 6:
                            LocationTimeZoneManagerService.debugLog(
                                    "handleProviderLost reason=onProviderUnbound(), mProviderName="
                                            + binderLocationTimeZoneProvider.mProviderName
                                            + ", currentState="
                                            + providerState
                                            + ": No state change required, provider is"
                                            + " terminated.");
                            break;
                        default:
                            throw new IllegalStateException(
                                    "Unknown currentState=" + providerState);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public BinderLocationTimeZoneProvider(
            RealProviderMetricsLogger realProviderMetricsLogger,
            HandlerThreadingDomain handlerThreadingDomain,
            String str,
            RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy,
            boolean z) {
        super(
                realProviderMetricsLogger,
                handlerThreadingDomain,
                str,
                new ZoneInfoDbTimeZoneProviderEventPreProcessor(),
                z);
        this.mProxy = realLocationTimeZoneProviderProxy;
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{BinderLocationTimeZoneProvider}");
            indentingPrintWriter.println("mProviderName=" + this.mProviderName);
            indentingPrintWriter.println("mCurrentState=" + this.mCurrentState);
            indentingPrintWriter.println("mProxy=" + this.mProxy);
            indentingPrintWriter.println("State history:");
            indentingPrintWriter.increaseIndent();
            this.mCurrentState.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("Proxy details:");
            indentingPrintWriter.increaseIndent();
            this.mProxy.dump(indentingPrintWriter, strArr);
            indentingPrintWriter.decreaseIndent();
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onDestroy() {
        RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy = this.mProxy;
        synchronized (realLocationTimeZoneProviderProxy.mSharedLock) {
            realLocationTimeZoneProviderProxy.mServiceWatcher.unregister();
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final boolean onInitialize() {
        RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy = this.mProxy;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        realLocationTimeZoneProviderProxy.getClass();
        synchronized (realLocationTimeZoneProviderProxy.mSharedLock) {
            try {
                if (realLocationTimeZoneProviderProxy.mListener != null) {
                    throw new IllegalStateException("listener already set");
                }
                realLocationTimeZoneProviderProxy.mListener = anonymousClass1;
                ServiceWatcherImpl serviceWatcherImpl =
                        realLocationTimeZoneProviderProxy.mServiceWatcher;
                boolean checkServiceResolves = serviceWatcherImpl.checkServiceResolves();
                if (checkServiceResolves) {
                    serviceWatcherImpl.register();
                }
                if (!checkServiceResolves) {
                    throw new IllegalStateException("Unable to register binder proxy");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onStartUpdates(Duration duration, Duration duration2) {
        Objects.requireNonNull(duration2);
        TimeZoneProviderRequest timeZoneProviderRequest =
                new TimeZoneProviderRequest(true, duration, duration2);
        RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy = this.mProxy;
        realLocationTimeZoneProviderProxy.mThreadingDomain.assertCurrentThread();
        synchronized (realLocationTimeZoneProviderProxy.mSharedLock) {
            realLocationTimeZoneProviderProxy.mRequest = timeZoneProviderRequest;
            realLocationTimeZoneProviderProxy.mServiceWatcher.runOnBinder(
                    new RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0(
                            timeZoneProviderRequest,
                            realLocationTimeZoneProviderProxy.mManagerProxy));
        }
    }

    @Override // com.android.server.timezonedetector.location.LocationTimeZoneProvider
    public final void onStopUpdates() {
        TimeZoneProviderRequest timeZoneProviderRequest = TimeZoneProviderRequest.STOP_UPDATES;
        RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy = this.mProxy;
        realLocationTimeZoneProviderProxy.mThreadingDomain.assertCurrentThread();
        synchronized (realLocationTimeZoneProviderProxy.mSharedLock) {
            realLocationTimeZoneProviderProxy.mRequest = timeZoneProviderRequest;
            realLocationTimeZoneProviderProxy.mServiceWatcher.runOnBinder(
                    new RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0(
                            timeZoneProviderRequest,
                            realLocationTimeZoneProviderProxy.mManagerProxy));
        }
    }

    public final String toString() {
        String str;
        synchronized (this.mSharedLock) {
            str =
                    "BinderLocationTimeZoneProvider{mProviderName="
                            + this.mProviderName
                            + ", mCurrentState="
                            + this.mCurrentState
                            + ", mProxy="
                            + this.mProxy
                            + '}';
        }
        return str;
    }
}
