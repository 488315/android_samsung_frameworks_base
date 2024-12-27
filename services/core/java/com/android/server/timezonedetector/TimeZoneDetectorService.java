package com.android.server.timezonedetector;

import android.app.ActivityManager;
import android.app.time.ITimeZoneDetectorListener;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.app.time.TimeZoneConfiguration;
import android.app.time.TimeZoneState;
import android.app.timezonedetector.ITimeZoneDetectorService;
import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.app.timezonedetector.TelephonyTimeZoneSuggestion;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import com.android.internal.util.DumpUtils;
import com.android.server.FgThread;
import com.android.server.SystemService;
import com.android.server.timezonedetector.CallerIdentityInjector;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimeZoneDetectorService extends ITimeZoneDetectorService.Stub implements IBinder.DeathRecipient {
    public final CallerIdentityInjector mCallerIdentityInjector;
    public final Context mContext;
    public final Handler mHandler;
    public final TimeZoneDetectorStrategy mTimeZoneDetectorStrategy;
    public final ArrayMap mListeners = new ArrayMap();
    public final List mDumpables = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.timezonedetector.TimeZoneDetectorService$Lifecycle$1, reason: invalid class name */
        public final class AnonymousClass1 {
            public final /* synthetic */ TimeZoneDetectorStrategy val$timeZoneDetectorStrategy;

            public AnonymousClass1(TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl) {
                this.val$timeZoneDetectorStrategy = timeZoneDetectorStrategyImpl;
            }
        }

        public Lifecycle(Context context) {
            super(context);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r5v3, types: [android.os.IBinder, com.android.server.timezonedetector.TimeZoneDetectorService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            Context context = getContext();
            Handler handler = FgThread.getHandler();
            TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl = new TimeZoneDetectorStrategyImpl(ServiceConfigAccessorImpl.getInstance(context), new EnvironmentImpl(handler));
            DeviceActivityMonitorImpl deviceActivityMonitorImpl = new DeviceActivityMonitorImpl(context, handler);
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(timeZoneDetectorStrategyImpl);
            synchronized (deviceActivityMonitorImpl) {
                ((ArrayList) deviceActivityMonitorImpl.mListeners).add(anonymousClass1);
            }
            publishLocalService(TimeZoneDetectorInternalImpl.class, new TimeZoneDetectorInternalImpl(context, handler, timeZoneDetectorStrategyImpl));
            ?? timeZoneDetectorService = new TimeZoneDetectorService(context, handler, CallerIdentityInjector.REAL, timeZoneDetectorStrategyImpl);
            synchronized (timeZoneDetectorService.mDumpables) {
                ((ArrayList) timeZoneDetectorService.mDumpables).add(deviceActivityMonitorImpl);
            }
            publishBinderService("time_zone_detector", timeZoneDetectorService);
        }
    }

    public TimeZoneDetectorService(Context context, Handler handler, CallerIdentityInjector callerIdentityInjector, TimeZoneDetectorStrategy timeZoneDetectorStrategy) {
        Objects.requireNonNull(context);
        this.mContext = context;
        Objects.requireNonNull(handler);
        this.mHandler = handler;
        Objects.requireNonNull(callerIdentityInjector);
        this.mCallerIdentityInjector = callerIdentityInjector;
        Objects.requireNonNull(timeZoneDetectorStrategy);
        TimeZoneDetectorStrategy timeZoneDetectorStrategy2 = timeZoneDetectorStrategy;
        this.mTimeZoneDetectorStrategy = timeZoneDetectorStrategy2;
        StateChangeListener stateChangeListener = new StateChangeListener() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda1
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                final TimeZoneDetectorService timeZoneDetectorService = TimeZoneDetectorService.this;
                timeZoneDetectorService.mHandler.post(new Runnable() { // from class: com.android.server.timezonedetector.TimeZoneDetectorService$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        TimeZoneDetectorService timeZoneDetectorService2 = TimeZoneDetectorService.this;
                        synchronized (timeZoneDetectorService2.mListeners) {
                            int size = timeZoneDetectorService2.mListeners.size();
                            for (int i = 0; i < size; i++) {
                                ITimeZoneDetectorListener iTimeZoneDetectorListener = (ITimeZoneDetectorListener) timeZoneDetectorService2.mListeners.valueAt(i);
                                try {
                                    iTimeZoneDetectorListener.onChange();
                                } catch (RemoteException e) {
                                    Slog.w("time_zone_detector", "Unable to notify listener=" + iTimeZoneDetectorListener, e);
                                }
                            }
                        }
                    }
                });
            }
        };
        TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl = (TimeZoneDetectorStrategyImpl) timeZoneDetectorStrategy2;
        synchronized (timeZoneDetectorStrategyImpl) {
            ((ArrayList) timeZoneDetectorStrategyImpl.mStateChangeListeners).add(stateChangeListener);
        }
    }

    public final void addListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) {
        enforceManageTimeZoneDetectorPermission();
        Objects.requireNonNull(iTimeZoneDetectorListener);
        synchronized (this.mListeners) {
            IBinder asBinder = iTimeZoneDetectorListener.asBinder();
            if (this.mListeners.containsKey(asBinder)) {
                return;
            }
            try {
                asBinder.linkToDeath(this, 0);
                this.mListeners.put(asBinder, iTimeZoneDetectorListener);
            } catch (RemoteException e) {
                Slog.e("time_zone_detector", "Unable to linkToDeath() for listener=" + iTimeZoneDetectorListener, e);
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.wtf("time_zone_detector", "binderDied() called unexpectedly.");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        synchronized (this.mListeners) {
            try {
                int size = this.mListeners.size() - 1;
                while (true) {
                    if (size < 0) {
                        Slog.w("time_zone_detector", "Notified of binder death for who=" + iBinder + ", but did not remove any listeners. mListeners=" + this.mListeners);
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

    public final boolean confirmTimeZone(String str) {
        enforceManageTimeZoneDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy).confirmTimeZone(str);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "time_zone_detector", printWriter)) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            ((TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy).dump(indentingPrintWriter, strArr);
            synchronized (this.mDumpables) {
                try {
                    Iterator it = ((ArrayList) this.mDumpables).iterator();
                    while (it.hasNext()) {
                        ((Dumpable) it.next()).dump(indentingPrintWriter, strArr);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.flush();
        }
    }

    public final void enforceManageTimeZoneDetectorPermission() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_TIME_AND_ZONE_DETECTION", "manage time and time zone detection");
    }

    public final TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig() {
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        return getCapabilitiesAndConfig(UserHandle.getCallingUserId());
    }

    public final TimeZoneCapabilitiesAndConfig getCapabilitiesAndConfig(int i) {
        TimeZoneCapabilitiesAndConfig timeZoneCapabilitiesAndConfig;
        enforceManageTimeZoneDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "getCapabilitiesAndConfig", null);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl = (TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy;
            synchronized (timeZoneDetectorStrategyImpl) {
                ConfigurationInternal configurationInternal = timeZoneDetectorStrategyImpl.mCurrentConfigurationInternal;
                if (configurationInternal.mUserId != handleIncomingUser) {
                    configurationInternal = ((ServiceConfigAccessorImpl) timeZoneDetectorStrategyImpl.mServiceConfigAccessor).getConfigurationInternal(handleIncomingUser);
                }
                timeZoneCapabilitiesAndConfig = new TimeZoneCapabilitiesAndConfig(timeZoneDetectorStrategyImpl.mDetectorStatus, configurationInternal.asCapabilities(), new TimeZoneConfiguration.Builder().setAutoDetectionEnabled(configurationInternal.mAutoDetectionEnabledSetting).setGeoDetectionEnabled(configurationInternal.mGeoDetectionEnabledSetting).build());
            }
            return timeZoneCapabilitiesAndConfig;
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final TimeZoneState getTimeZoneState() {
        TimeZoneState timeZoneState;
        enforceManageTimeZoneDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            TimeZoneDetectorStrategyImpl timeZoneDetectorStrategyImpl = (TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy;
            synchronized (timeZoneDetectorStrategyImpl) {
                timeZoneState = new TimeZoneState(timeZoneDetectorStrategyImpl.mEnvironment.getDeviceTimeZone(), timeZoneDetectorStrategyImpl.mEnvironment.getDeviceTimeZoneConfidence() < 100);
            }
            return timeZoneState;
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new TimeZoneDetectorShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void removeListener(ITimeZoneDetectorListener iTimeZoneDetectorListener) {
        enforceManageTimeZoneDetectorPermission();
        Objects.requireNonNull(iTimeZoneDetectorListener);
        synchronized (this.mListeners) {
            try {
                IBinder asBinder = iTimeZoneDetectorListener.asBinder();
                if (this.mListeners.remove(asBinder) != null) {
                    asBinder.unlinkToDeath(this, 0);
                } else {
                    Slog.w("time_zone_detector", "Client asked to remove listener=" + iTimeZoneDetectorListener + ", but no listeners were removed. mListeners=" + this.mListeners);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        enforceManageTimeZoneDetectorPermission();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int callingUserId = UserHandle.getCallingUserId();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy).suggestManualTimeZone(callingUserId, manualTimeZoneSuggestion);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean suggestManualTimeZone(ManualTimeZoneSuggestion manualTimeZoneSuggestion) {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_MANUAL_TIME_AND_ZONE", "suggest manual time and time zone");
        Objects.requireNonNull(manualTimeZoneSuggestion);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int callingUserId = UserHandle.getCallingUserId();
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy).suggestManualTimeZone(callingUserId, manualTimeZoneSuggestion);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void suggestTelephonyTimeZone(TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion) {
        this.mContext.enforceCallingPermission("android.permission.SUGGEST_TELEPHONY_TIME_AND_ZONE", "suggest telephony time and time zone");
        Objects.requireNonNull(telephonyTimeZoneSuggestion);
        this.mHandler.post(new TimeZoneDetectorService$$ExternalSyntheticLambda0(this, telephonyTimeZoneSuggestion, 0));
    }

    public final boolean updateConfiguration(int i, TimeZoneConfiguration timeZoneConfiguration) {
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        int handleIncomingUser = ActivityManager.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, false, "updateConfiguration", null);
        enforceManageTimeZoneDetectorPermission();
        Objects.requireNonNull(timeZoneConfiguration);
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((TimeZoneDetectorStrategyImpl) this.mTimeZoneDetectorStrategy).updateConfiguration(handleIncomingUser, timeZoneConfiguration);
        } finally {
            ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean updateConfiguration(TimeZoneConfiguration timeZoneConfiguration) {
        ((CallerIdentityInjector.Real) this.mCallerIdentityInjector).getClass();
        return updateConfiguration(UserHandle.getCallingUserId(), timeZoneConfiguration);
    }
}
