package android.service.timezone;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.service.timezone.ITimeZoneProvider;
import android.service.timezone.TimeZoneProviderService;
import android.util.Log;
import com.android.internal.os.BackgroundThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public abstract class TimeZoneProviderService extends Service {
    public static final String PRIMARY_LOCATION_TIME_ZONE_PROVIDER_SERVICE_INTERFACE = "android.service.timezone.PrimaryLocationTimeZoneProviderService";
    public static final String SECONDARY_LOCATION_TIME_ZONE_PROVIDER_SERVICE_INTERFACE = "android.service.timezone.SecondaryLocationTimeZoneProviderService";
    private static final String TAG = "TimeZoneProviderService";
    public static final String TEST_COMMAND_RESULT_ERROR_KEY = "ERROR";
    public static final String TEST_COMMAND_RESULT_SUCCESS_KEY = "SUCCESS";
    private long mEventFilteringAgeThresholdMillis;
    private TimeZoneProviderEvent mLastEventSent;
    private ITimeZoneProviderManager mManager;
    private final TimeZoneProviderServiceWrapper mWrapper = new TimeZoneProviderServiceWrapper();
    private final Object mLock = new Object();
    private final Handler mHandler = BackgroundThread.getHandler();

    public abstract void onStartUpdates(long j);

    public abstract void onStopUpdates();

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    public final void reportSuggestion(TimeZoneProviderSuggestion timeZoneProviderSuggestion) {
        reportSuggestionInternal(timeZoneProviderSuggestion, null);
    }

    public final void reportSuggestion(TimeZoneProviderSuggestion timeZoneProviderSuggestion, TimeZoneProviderStatus timeZoneProviderStatus) {
        Objects.requireNonNull(timeZoneProviderStatus);
        reportSuggestionInternal(timeZoneProviderSuggestion, timeZoneProviderStatus);
    }

    private void reportSuggestionInternal(final TimeZoneProviderSuggestion timeZoneProviderSuggestion, final TimeZoneProviderStatus timeZoneProviderStatus) {
        Objects.requireNonNull(timeZoneProviderSuggestion);
        this.mHandler.post(new Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TimeZoneProviderService.this.lambda$reportSuggestionInternal$0(timeZoneProviderSuggestion, timeZoneProviderStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportSuggestionInternal$0(TimeZoneProviderSuggestion timeZoneProviderSuggestion, TimeZoneProviderStatus timeZoneProviderStatus) {
        synchronized (this.mLock) {
            ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    TimeZoneProviderEvent createSuggestionEvent = TimeZoneProviderEvent.createSuggestionEvent(SystemClock.elapsedRealtime(), timeZoneProviderSuggestion, timeZoneProviderStatus);
                    if (shouldSendEvent(createSuggestionEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createSuggestionEvent);
                        this.mLastEventSent = createSuggestionEvent;
                    }
                } catch (RemoteException | RuntimeException e) {
                    Log.w(TAG, e);
                }
            }
        }
    }

    public final void reportUncertain() {
        reportUncertainInternal(null);
    }

    public final void reportUncertain(TimeZoneProviderStatus timeZoneProviderStatus) {
        Objects.requireNonNull(timeZoneProviderStatus);
        reportUncertainInternal(timeZoneProviderStatus);
    }

    private void reportUncertainInternal(final TimeZoneProviderStatus timeZoneProviderStatus) {
        this.mHandler.post(new Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TimeZoneProviderService.this.lambda$reportUncertainInternal$1(timeZoneProviderStatus);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportUncertainInternal$1(TimeZoneProviderStatus timeZoneProviderStatus) {
        synchronized (this.mLock) {
            ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    TimeZoneProviderEvent createUncertainEvent = TimeZoneProviderEvent.createUncertainEvent(SystemClock.elapsedRealtime(), timeZoneProviderStatus);
                    if (shouldSendEvent(createUncertainEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createUncertainEvent);
                        this.mLastEventSent = createUncertainEvent;
                    }
                } catch (RemoteException | RuntimeException e) {
                    Log.w(TAG, e);
                }
            }
        }
    }

    public final void reportPermanentFailure(final Throwable th) {
        Objects.requireNonNull(th);
        this.mHandler.post(new Runnable() { // from class: android.service.timezone.TimeZoneProviderService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TimeZoneProviderService.this.lambda$reportPermanentFailure$2(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportPermanentFailure$2(Throwable th) {
        synchronized (this.mLock) {
            ITimeZoneProviderManager iTimeZoneProviderManager = this.mManager;
            if (iTimeZoneProviderManager != null) {
                try {
                    TimeZoneProviderEvent createPermanentFailureEvent = TimeZoneProviderEvent.createPermanentFailureEvent(SystemClock.elapsedRealtime(), th.getMessage());
                    if (shouldSendEvent(createPermanentFailureEvent)) {
                        iTimeZoneProviderManager.onTimeZoneProviderEvent(createPermanentFailureEvent);
                        this.mLastEventSent = createPermanentFailureEvent;
                    }
                } catch (RemoteException | RuntimeException e) {
                    Log.w(TAG, e);
                }
            }
        }
    }

    private boolean shouldSendEvent(TimeZoneProviderEvent timeZoneProviderEvent) {
        return !timeZoneProviderEvent.isEquivalentTo(this.mLastEventSent) || timeZoneProviderEvent.getCreationElapsedMillis() - this.mLastEventSent.getCreationElapsedMillis() > this.mEventFilteringAgeThresholdMillis;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStartUpdatesInternal(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) {
        synchronized (this.mLock) {
            this.mManager = iTimeZoneProviderManager;
            this.mEventFilteringAgeThresholdMillis = j2;
            this.mLastEventSent = null;
            onStartUpdates(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStopUpdatesInternal() {
        synchronized (this.mLock) {
            onStopUpdates();
            this.mManager = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            printWriter.append((CharSequence) ("mLastEventSent=" + this.mLastEventSent));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class TimeZoneProviderServiceWrapper extends ITimeZoneProvider.Stub {
        private TimeZoneProviderServiceWrapper() {
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void startUpdates(final ITimeZoneProviderManager iTimeZoneProviderManager, final long j, final long j2) {
            Objects.requireNonNull(iTimeZoneProviderManager);
            TimeZoneProviderService.this.mHandler.post(new Runnable() { // from class: android.service.timezone.TimeZoneProviderService$TimeZoneProviderServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TimeZoneProviderService.TimeZoneProviderServiceWrapper.this.lambda$startUpdates$0(iTimeZoneProviderManager, j, j2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startUpdates$0(ITimeZoneProviderManager iTimeZoneProviderManager, long j, long j2) {
            TimeZoneProviderService.this.onStartUpdatesInternal(iTimeZoneProviderManager, j, j2);
        }

        @Override // android.service.timezone.ITimeZoneProvider
        public void stopUpdates() {
            Handler handler = TimeZoneProviderService.this.mHandler;
            final TimeZoneProviderService timeZoneProviderService = TimeZoneProviderService.this;
            handler.post(new Runnable() { // from class: android.service.timezone.TimeZoneProviderService$TimeZoneProviderServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TimeZoneProviderService.this.onStopUpdatesInternal();
                }
            });
        }
    }
}
