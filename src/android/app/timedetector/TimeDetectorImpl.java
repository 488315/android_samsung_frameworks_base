package android.app.timedetector;

import android.app.timedetector.ITimeDetectorService;
import android.os.RemoteException;
import android.os.ServiceManager;

/* loaded from: classes.dex */
public final class TimeDetectorImpl implements TimeDetector {
    private static final boolean DEBUG = false;
    private static final String TAG = "timedetector.TimeDetector";
    private final ITimeDetectorService mITimeDetectorService = ITimeDetectorService.Stub.asInterface(ServiceManager.getServiceOrThrow("time_detector"));

    @Override // android.app.timedetector.TimeDetector
    public void suggestTelephonyTime(TelephonyTimeSuggestion telephonyTimeSuggestion) {
        try {
            this.mITimeDetectorService.suggestTelephonyTime(telephonyTimeSuggestion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.timedetector.TimeDetector
    public boolean suggestManualTime(ManualTimeSuggestion manualTimeSuggestion) {
        try {
            return this.mITimeDetectorService.suggestManualTime(manualTimeSuggestion);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
