package android.hardware.fingerprint;

import android.os.RemoteException;

public class FingerprintServiceReceiver extends IFingerprintServiceReceiver.Stub {
    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onEnrollResult(Fingerprint fp, int remaining) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAcquired(int acquiredInfo, int vendorCode) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationSucceeded(Fingerprint fp, int userId, boolean isStrongBiometric)
            throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onFingerprintDetected(int sensorId, int userId, boolean isStrongBiometric)
            throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onAuthenticationFailed() throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onError(int error, int vendorCode) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onRemoved(Fingerprint fp, int remaining) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onChallengeGenerated(int sensorId, int userId, long challenge)
            throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerDown(int sensorId) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsPointerUp(int sensorId) throws RemoteException {}

    @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
    public void onUdfpsOverlayShown() throws RemoteException {}
}
