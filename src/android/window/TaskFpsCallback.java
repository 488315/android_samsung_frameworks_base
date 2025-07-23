package android.window;

import android.annotation.SystemApi;
import android.os.RemoteException;

@SystemApi
/* loaded from: classes5.dex */
public abstract class TaskFpsCallback {
    public abstract void onFpsReported(float f);

    private static void dispatchOnFpsReported(ITaskFpsCallback iTaskFpsCallback, float f) {
        try {
            iTaskFpsCallback.onFpsReported(f);
        } catch (RemoteException unused) {
        }
    }
}
