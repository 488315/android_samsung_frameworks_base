package com.android.server.timedetector;

import android.app.ActivityManager;
import android.app.time.ExternalTimeSuggestion;
import android.app.time.ITimeDetectorListener;
import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeConfiguration;
import android.app.time.TimeState;
import android.app.time.UnixEpochTime;
import android.app.timedetector.ITimeDetectorService;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TelephonyTimeSuggestion;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.NtpTrustedTime;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.server.FgThread;
import com.android.server.SystemService;
import com.android.server.location.gnss.TimeDetectorNetworkTimeHelper;
import com.android.server.timezonedetector.CallerIdentityInjector;
import com.android.server.timezonedetector.StateChangeListener;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TimeDetectorService extends ITimeDetectorService.Stub
        implements IBinder.DeathRecipient {
    public final CallerIdentityInjector mCallerIdentityInjector;
    public final Context mContext;
    public final Handler mHandler;
    public final ArrayMap mListeners = new ArrayMap();
    public final TimeDetectorStrategy mTimeDetectorStrategy;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            ServiceConfigAccessorImpl serviceConfigAccessorImpl;
            Context context = getContext();
            Handler handler = FgThread.getHandler();
            synchronized (ServiceConfigAccessorImpl.SLOCK) {
                try {
                    if (ServiceConfigAccessorImpl.sInstance == null) {
                        ServiceConfigAccessorImpl.sInstance =
                                new ServiceConfigAccessorImpl(context);
                    }
                    serviceConfigAccessorImpl = ServiceConfigAccessorImpl.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            TimeDetectorStrategyImpl timeDetectorStrategyImpl =
                    new TimeDetectorStrategyImpl(
                            new EnvironmentImpl(context, handler), serviceConfigAccessorImpl);
            publishLocalService(
                    TimeDetectorInternal.class,
                    new TimeDetectorInternalImpl(
                            context, handler, serviceConfigAccessorImpl, timeDetectorStrategyImpl));
            publishBinderService(
                    "time_detector",
                    new TimeDetectorService(
                            context,
                            handler,
                            CallerIdentityInjector.REAL,
                            timeDetectorStrategyImpl,
                            NtpTrustedTime.getInstance(context)));
        }
    }

    public TimeDetectorService(
            Context context,
            Handler handler,
            CallerIdentityInjector callerIdentityInjector,
            TimeDetectorStrategy timeDetectorStrategy,
            NtpTrustedTime ntpTrustedTime) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        Objects.requireNonNull(callerIdentityInjector);
        this.mCallerIdentityInjector = callerIdentityInjector;
        Objects.requireNonNull(timeDetectorStrategy);
        this.mTimeDetectorStrategy = timeDetectorStrategy;
        Objects.requireNonNull(ntpTrustedTime);
        StateChangeListener stateChangeListener =
                new StateChangeListener() { // from class:
                                            // com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda2
                    @Override // com.android.server.timezonedetector.StateChangeListener
                    public final void onChange() {
                        final TimeDetectorService timeDetectorService = TimeDetectorService.this;
                        timeDetectorService.mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.timedetector.TimeDetectorService$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        TimeDetectorService timeDetectorService2 =
                                                TimeDetectorService.this;
                                        synchronized (timeDetectorService2.mListeners) {
                                            int size = timeDetectorService2.mListeners.size();
                                            for (int i = 0; i < size; i++) {
                                                ITimeDetectorListener iTimeDetectorListener =
                                                        (ITimeDetectorListener)
                                                                timeDetectorService2.mListeners
                                                                        .valueAt(i);
                                                try {
                                                    iTimeDetectorListener.onChange();
                                                } catch (RemoteException e) {
                                                    Slog.w(
                                                            "time_detector",
                                                            "Unable to notify listener="
                                                                    + iTimeDetectorListener,
                                                            e);
                                                }
                                            }
                                        }
                                    }
                                });
                    }
                };
        TimeDetectorStrategyImpl timeDetectorStrategyImpl =
                (TimeDetectorStrategyImpl) timeDetectorStrategy;
        synchronized (timeDetectorStrategyImpl) {
            ((ArrayList) timeDetectorStrategyImpl.mStateChangeListeners).add(stateChangeListener);
        }
    }

    public final void addListener(ITimeDetectorListener iTimeDetectorListener) {
        enforceManageTimeDetectorPermission();
        Objects.requireNonNull(iTimeDetectorListener);
        synchronized (this.mListeners) {
            IBinder asBinder = iTimeDetectorListener.asBinder();
            if (this.mListeners.containsKey(asBinder)) {
                return;
            }
            try {
                asBinder.linkToDeath(this, 0);
                this.mListeners.put(asBinder, iTimeDetectorListener);
            } catch (RemoteException e) {
                Slog.e(
                        "time_detector",
                        "Unable to linkToDeath() for listener=" + iTimeDetectorListener,
                        e);
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.wtf("time_detector", "binderDied() called unexpectedly.");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        synchronized (this.mListeners) {
            try {
                int size = this.mListeners.size() - 1;
                while (true) {
                    if (size < 0) {
                        Slog.w(
                                "time_detector",
                                "Notified of binder death for who="
                                        + iBinder
                                        + ", but did not remove any listeners. mListeners="
                                        + this.mListeners);
                        break;
                    }
                    if (((IBinder) this.mListeners.keyAt(size)).equals(iBinder)) {
                        this.mListeners.removeAt(size);
                        break;
                    }
                    size--;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean confirmTime(UnixEpochTime unixEpochTime) {
        enforceManageTimeDetectorPermission();
        Objects.requireNonNull(unixEpochTime);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                    .confirmTime(unixEpochTime);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "time_detector", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                    .dump(indentingPrintWriter, strArr);
            indentingPrintWriter.flush();
        }
    }

    public final void enforceManageTimeDetectorPermission() {
        this.mContext.enforceCallingPermission(
                "android.permission.MANAGE_TIME_AND_ZONE_DETECTION",
                "manage time and time zone detection");
    }

    public final TimeCapabilitiesAndConfig getCapabilitiesAndConfig() {
        TimeCapabilitiesAndConfig createCapabilitiesAndConfig;
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int callingUserId = UserHandle.getCallingUserId();
        enforceManageTimeDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TimeDetectorStrategyImpl timeDetectorStrategyImpl =
                    (TimeDetectorStrategyImpl) this.mTimeDetectorStrategy;
            synchronized (timeDetectorStrategyImpl) {
                ConfigurationInternal configurationInternal =
                        timeDetectorStrategyImpl.mCurrentConfigurationInternal;
                if (configurationInternal.mUserId != callingUserId) {
                    configurationInternal =
                            ((ServiceConfigAccessorImpl)
                                            timeDetectorStrategyImpl.mServiceConfigAccessor)
                                    .getConfigurationInternal(callingUserId);
                }
                createCapabilitiesAndConfig = configurationInternal.createCapabilitiesAndConfig();
            }
            return createCapabilitiesAndConfig;
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final TimeState getTimeState() {
        enforceManageTimeDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy).getTimeState();
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final UnixEpochTime latestNetworkTime() {
        boolean z = TimeDetectorNetworkTimeHelper.DEBUG;
        NetworkTimeSuggestion latestNetworkSuggestion =
                ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                        .getLatestNetworkSuggestion();
        if (latestNetworkSuggestion != null) {
            return latestNetworkSuggestion.mUnixEpochTime;
        }
        throw new ParcelableException(new DateTimeException("Missing network time fix"));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new TimeDetectorShellCommand(this)
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void removeListener(ITimeDetectorListener iTimeDetectorListener) {
        enforceManageTimeDetectorPermission();
        Objects.requireNonNull(iTimeDetectorListener);
        synchronized (this.mListeners) {
            try {
                IBinder asBinder = iTimeDetectorListener.asBinder();
                if (this.mListeners.remove(asBinder) != null) {
                    asBinder.unlinkToDeath(this, 0);
                } else {
                    Slog.w(
                            "time_detector",
                            "Client asked to remove listener="
                                    + iTimeDetectorListener
                                    + ", but no listeners were removed. mListeners="
                                    + this.mListeners);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setManualTime(ManualTimeSuggestion manualTimeSuggestion) {
        enforceManageTimeDetectorPermission();
        Objects.requireNonNull(manualTimeSuggestion);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int callingUserId = UserHandle.getCallingUserId();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                    .suggestManualTime(callingUserId, manualTimeSuggestion);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) {
        this.mContext.enforceCallingPermission(
                "android.permission.SUGGEST_EXTERNAL_TIME", "suggest time from external source");
        Objects.requireNonNull(externalTimeSuggestion);
        this.mHandler.post(
                new TimeDetectorService$$ExternalSyntheticLambda0(this, externalTimeSuggestion, 0));
    }

    public final boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) {
        this.mContext.enforceCallingPermission(
                "android.permission.SUGGEST_MANUAL_TIME_AND_ZONE",
                "suggest manual time and time zone");
        Objects.requireNonNull(manualTimeSuggestion);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int callingUserId = UserHandle.getCallingUserId();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                    .suggestManualTime(callingUserId, manualTimeSuggestion);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) {
        this.mContext.enforceCallingPermission(
                "android.permission.SUGGEST_TELEPHONY_TIME_AND_ZONE",
                "suggest telephony time and time zone");
        Objects.requireNonNull(telephonyTimeSuggestion);
        this.mHandler.post(
                new TimeDetectorService$$ExternalSyntheticLambda0(this, telephonyTimeSuggestion));
    }

    public final boolean updateConfiguration(int i, TimeConfiguration timeConfiguration) {
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        false,
                        "updateConfiguration",
                        null);
        enforceManageTimeDetectorPermission();
        Objects.requireNonNull(timeConfiguration);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeDetectorStrategyImpl) this.mTimeDetectorStrategy)
                    .updateConfiguration(handleIncomingUser, timeConfiguration);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateConfiguration(TimeConfiguration timeConfiguration) {
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        return updateConfiguration(UserHandle.getCallingUserId(), timeConfiguration);
    }
}
