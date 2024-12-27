package android.view.displayhash;

import android.os.RemoteException;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManagerGlobal;

import java.util.Collections;
import java.util.Set;

public final class DisplayHashManager {
    private static final String TAG = "DisplayHashManager";
    private static Set<String> sSupportedHashAlgorithms;
    private final Object mSupportedHashingAlgorithmLock = new Object();

    public Set<String> getSupportedHashAlgorithms() {
        synchronized (this.mSupportedHashingAlgorithmLock) {
            if (sSupportedHashAlgorithms != null) {
                return sSupportedHashAlgorithms;
            }
            try {
                String[] supportedAlgorithms =
                        WindowManagerGlobal.getWindowManagerService()
                                .getSupportedDisplayHashAlgorithms();
                if (supportedAlgorithms == null) {
                    return Collections.emptySet();
                }
                sSupportedHashAlgorithms = new ArraySet(supportedAlgorithms);
                return sSupportedHashAlgorithms;
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

    public void setDisplayHashThrottlingEnabled(boolean enable) {
        try {
            WindowManagerGlobal.getWindowManagerService().setDisplayHashThrottlingEnabled(enable);
        } catch (RemoteException e) {
        }
    }
}
