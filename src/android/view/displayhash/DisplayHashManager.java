package android.view.displayhash;

import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManagerGlobal;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes4.dex */
public final class DisplayHashManager {
    private static final String TAG = "DisplayHashManager";
    private static Set<String> sSupportedHashAlgorithms;
    private final Object mSupportedHashingAlgorithmLock = new Object();

    public Set<String> getSupportedHashAlgorithms() {
        synchronized (this.mSupportedHashingAlgorithmLock) {
            Set<String> set = sSupportedHashAlgorithms;
            if (set != null) {
                return set;
            }
            try {
                String[] supportedDisplayHashAlgorithms = WindowManagerGlobal.getWindowManagerService().getSupportedDisplayHashAlgorithms();
                if (supportedDisplayHashAlgorithms == null) {
                    return Collections.EMPTY_SET;
                }
                ArraySet arraySet = new ArraySet(supportedDisplayHashAlgorithms);
                sSupportedHashAlgorithms = arraySet;
                return arraySet;
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to send request getSupportedHashingAlgorithms", e);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public VerifiedDisplayHash verifyDisplayHash(DisplayHash displayHash) {
        try {
            return WindowManagerGlobal.getWindowManagerService().verifyDisplayHash(displayHash);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to send request verifyImpressionToken", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDisplayHashThrottlingEnabled(boolean z) {
        try {
            WindowManagerGlobal.getWindowManagerService().setDisplayHashThrottlingEnabled(z);
        } catch (RemoteException unused) {
        }
    }
}
