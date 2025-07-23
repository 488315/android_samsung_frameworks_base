package android.hardware.fingerprint;

import android.hardware.fingerprint.IFingerprintServiceReceiver;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public class FingerprintServiceReceiver extends IFingerprintServiceReceiver.Stub {
    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAcquired(int i, int i2) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationFailed() throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onEnrollResult(Fingerprint fingerprint, int i) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onError(int i, int i2) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onFingerprintDetected(int i, int i2, boolean z) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onRemoved(Fingerprint fingerprint, int i) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsOverlayShown() throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerDown(int i) throws RemoteException {
    }

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerUp(int i) throws RemoteException {
    }
}
