package android.app.time;

import android.annotation.SystemApi;
import android.app.time.ITimeZoneDetectorListener;
import android.app.time.TimeManager;
import android.app.timedetector.ITimeDetectorService;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timezonedetector.ITimeZoneDetectorService;
import android.app.timezonedetector.ManualTimeZoneSuggestion;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public final class TimeManager {
    private static final boolean DEBUG = false;
    private static final String TAG = "time.TimeManager";
    private ArrayMap<TimeZoneDetectorListener, TimeZoneDetectorListener> mTimeZoneDetectorListeners;
    private ITimeZoneDetectorListener mTimeZoneDetectorReceiver;
    private final Object mLock = new Object();
    private final ITimeZoneDetectorService mITimeZoneDetectorService = ITimeZoneDetectorService.Stub.asInterface(ServiceManager.getServiceOrThrow("time_zone_detector"));
    private final ITimeDetectorService mITimeDetectorService = ITimeDetectorService.Stub.asInterface(ServiceManager.getServiceOrThrow("time_detector"));

    @FunctionalInterface
    public interface TimeZoneDetectorListener {
        void onChange();
    }

    public TimeZoneCapabilitiesAndConfig getTimeZoneCapabilitiesAndConfig() {
        try {
            return this.mITimeZoneDetectorService.getCapabilitiesAndConfig();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig() {
        try {
            return this.mITimeDetectorService.getCapabilitiesAndConfig();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateTimeConfiguration(TimeConfiguration timeConfiguration) {
        try {
            return this.mITimeDetectorService.updateConfiguration(timeConfiguration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateTimeZoneConfiguration(TimeZoneConfiguration timeZoneConfiguration) {
        try {
            return this.mITimeZoneDetectorService.updateConfiguration(timeZoneConfiguration);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addTimeZoneDetectorListener(final Executor executor, final TimeZoneDetectorListener timeZoneDetectorListener) {
        synchronized (this.mLock) {
            ArrayMap<TimeZoneDetectorListener, TimeZoneDetectorListener> arrayMap = this.mTimeZoneDetectorListeners;
            if (arrayMap == null) {
                this.mTimeZoneDetectorListeners = new ArrayMap<>();
            } else if (arrayMap.containsKey(timeZoneDetectorListener)) {
                return;
            }
            if (this.mTimeZoneDetectorReceiver == null) {
                ITimeZoneDetectorListener.Stub stub = new ITimeZoneDetectorListener.Stub() { // from class: android.app.time.TimeManager.1
                    @Override // android.app.time.ITimeZoneDetectorListener
                    public void onChange() {
                        TimeManager.this.notifyTimeZoneDetectorListeners();
                    }
                };
                this.mTimeZoneDetectorReceiver = stub;
                try {
                    this.mITimeZoneDetectorService.addListener(stub);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mTimeZoneDetectorListeners.put(timeZoneDetectorListener, new TimeZoneDetectorListener() { // from class: android.app.time.TimeManager$$ExternalSyntheticLambda1
                @Override // android.app.time.TimeManager.TimeZoneDetectorListener
                public final void onChange() {
                    TimeManager.lambda$addTimeZoneDetectorListener$0(executor, timeZoneDetectorListener);
                }
            });
        }
    }

    static /* synthetic */ void lambda$addTimeZoneDetectorListener$0(Executor executor, final TimeZoneDetectorListener timeZoneDetectorListener) {
        Objects.requireNonNull(timeZoneDetectorListener);
        executor.execute(new Runnable() { // from class: android.app.time.TimeManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TimeManager.TimeZoneDetectorListener.this.onChange();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyTimeZoneDetectorListeners() {
        synchronized (this.mLock) {
            ArrayMap<TimeZoneDetectorListener, TimeZoneDetectorListener> arrayMap = this.mTimeZoneDetectorListeners;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                ArrayMap arrayMap2 = new ArrayMap(this.mTimeZoneDetectorListeners);
                int size = arrayMap2.size();
                for (int i = 0; i < size; i++) {
                    ((TimeZoneDetectorListener) arrayMap2.valueAt(i)).onChange();
                }
            }
        }
    }

    public void removeTimeZoneDetectorListener(TimeZoneDetectorListener timeZoneDetectorListener) {
        synchronized (this.mLock) {
            ArrayMap<TimeZoneDetectorListener, TimeZoneDetectorListener> arrayMap = this.mTimeZoneDetectorListeners;
            if (arrayMap != null && !arrayMap.isEmpty()) {
                this.mTimeZoneDetectorListeners.remove(timeZoneDetectorListener);
                if (this.mTimeZoneDetectorListeners.isEmpty()) {
                    try {
                        try {
                            this.mITimeZoneDetectorService.removeListener(this.mTimeZoneDetectorReceiver);
                        } catch (RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    } finally {
                        this.mTimeZoneDetectorReceiver = null;
                    }
                }
            }
        }
    }

    public void suggestExternalTime(ExternalTimeSuggestion externalTimeSuggestion) {
        try {
            this.mITimeDetectorService.suggestExternalTime(externalTimeSuggestion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TimeState getTimeState() {
        try {
            return this.mITimeDetectorService.getTimeState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean confirmTime(UnixEpochTime unixEpochTime) {
        try {
            return this.mITimeDetectorService.confirmTime(unixEpochTime);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setManualTime(UnixEpochTime unixEpochTime) {
        try {
            ManualTimeSuggestion manualTimeSuggestion = new ManualTimeSuggestion(unixEpochTime);
            manualTimeSuggestion.addDebugInfo("TimeManager.setTime()");
            manualTimeSuggestion.addDebugInfo("UID: " + Process.myUid());
            manualTimeSuggestion.addDebugInfo("UserHandle: " + Process.myUserHandle());
            manualTimeSuggestion.addDebugInfo("Process: " + Process.myProcessName());
            return this.mITimeDetectorService.setManualTime(manualTimeSuggestion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TimeZoneState getTimeZoneState() {
        try {
            return this.mITimeZoneDetectorService.getTimeZoneState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean confirmTimeZone(String str) {
        try {
            return this.mITimeZoneDetectorService.confirmTimeZone(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setManualTimeZone(String str) {
        try {
            ManualTimeZoneSuggestion manualTimeZoneSuggestion = new ManualTimeZoneSuggestion(str);
            manualTimeZoneSuggestion.addDebugInfo("TimeManager.setManualTimeZone()");
            manualTimeZoneSuggestion.addDebugInfo("UID: " + Process.myUid());
            manualTimeZoneSuggestion.addDebugInfo("Process: " + Process.myProcessName());
            return this.mITimeZoneDetectorService.setManualTimeZone(manualTimeZoneSuggestion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
