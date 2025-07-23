package android.os.vibrator;

import android.annotation.SystemApi;
import android.os.CombinedVibration;
import android.os.RemoteException;
import android.os.VibrationEffect;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class VendorVibrationSession implements AutoCloseable {
    public static final int STATUS_CANCELED = 4;
    public static final int STATUS_IGNORED = 2;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_UNKNOWN = 0;
    public static final int STATUS_UNKNOWN_ERROR = 5;
    public static final int STATUS_UNSUPPORTED = 3;
    private static final String TAG = "VendorVibrationSession";
    private final IVibrationSession mSession;

    public interface Callback {
        void onFinished(int i);

        void onFinishing();

        void onStarted(VendorVibrationSession vendorVibrationSession);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public VendorVibrationSession(IVibrationSession iVibrationSession) {
        Objects.requireNonNull(iVibrationSession);
        this.mSession = iVibrationSession;
    }

    public void vibrate(VibrationEffect vibrationEffect, String str) {
        try {
            this.mSession.vibrate(CombinedVibration.createParallel(vibrationEffect), str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to vibrate in a vendor vibration session.", e);
            e.rethrowFromSystemServer();
        }
    }

    public void cancel() {
        try {
            this.mSession.cancelSession();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to cancel vendor vibration session.", e);
            e.rethrowFromSystemServer();
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        try {
            this.mSession.finishSession();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to finish vendor vibration session.", e);
            e.rethrowFromSystemServer();
        }
    }
}
