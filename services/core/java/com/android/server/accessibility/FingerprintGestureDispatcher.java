package com.android.server.accessibility;

import android.R;
import android.content.res.Resources;
import android.hardware.fingerprint.IFingerprintClientActiveCallback;
import android.hardware.fingerprint.IFingerprintService;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FingerprintGestureDispatcher extends IFingerprintClientActiveCallback.Stub implements Handler.Callback {
    public final IFingerprintService mFingerprintService;
    public final boolean mHardwareSupportsGestures;
    public final Object mLock;
    public boolean mRegisteredReadOnlyExceptInHandler;
    public final List mCapturingClients = new ArrayList(0);
    public final Handler mHandler = new Handler(this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface FingerprintGestureClient {
        boolean isCapturingFingerprintGestures();

        void onFingerprintGesture(int i);

        void onFingerprintGestureDetectionActiveChanged(boolean z);
    }

    public FingerprintGestureDispatcher(IFingerprintService iFingerprintService, Resources resources, Object obj) {
        this.mFingerprintService = iFingerprintService;
        this.mHardwareSupportsGestures = resources.getBoolean(R.bool.config_handleVolumeKeysInWindowManager);
        this.mLock = obj;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        long clearCallingIdentity;
        int i = message.what;
        if (i == 1) {
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    this.mFingerprintService.addClientActiveCallback(this);
                    this.mRegisteredReadOnlyExceptInHandler = true;
                } catch (RemoteException unused) {
                    Slog.e("FingerprintGestureDispatcher", "Failed to register for fingerprint activity callbacks");
                }
                return false;
            } finally {
            }
        }
        if (i != 2) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown message: "), message.what, "FingerprintGestureDispatcher");
            return false;
        }
        clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mFingerprintService.removeClientActiveCallback(this);
            } catch (RemoteException unused2) {
                Slog.e("FingerprintGestureDispatcher", "Failed to unregister for fingerprint activity callbacks");
            }
            this.mRegisteredReadOnlyExceptInHandler = false;
            return true;
        } finally {
        }
    }

    public final void onClientActiveChanged(boolean z) {
        if (this.mHardwareSupportsGestures) {
            synchronized (this.mLock) {
                for (int i = 0; i < ((ArrayList) this.mCapturingClients).size(); i++) {
                    try {
                        ((FingerprintGestureClient) ((ArrayList) this.mCapturingClients).get(i)).onFingerprintGestureDetectionActiveChanged(!z);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }
}
