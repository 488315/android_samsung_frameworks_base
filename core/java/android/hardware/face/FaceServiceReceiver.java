package android.hardware.face;

import android.hardware.face.IFaceServiceReceiver;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public class FaceServiceReceiver extends IFaceServiceReceiver.Stub {
    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollResult(Face face, int remaining) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAcquired(int acquiredInfo, int vendorCode) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationSucceeded(Face face, int userId, boolean isStrongBiometric) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFaceDetected(int sensorId, int userId, boolean isStrongBiometric) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFailed() throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onError(int error, int vendorCode) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onRemoved(Face face, int remaining) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureSet(boolean success, int feature) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureGet(boolean success, int[] features, boolean[] featureState) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onChallengeGenerated(int sensorId, int userId, long challenge) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFrame(FaceAuthenticationFrame frame) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollmentFrame(FaceEnrollFrame frame) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemAuthenticationSucceeded(Face face, int userId, boolean isStrongBiometric, byte[] fidoResultData) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemAuthenticationSucceededWithBundle(Face face, int userId, boolean isStrongBiometric, Bundle b) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemImageProcessed(byte[] data, int width, int height, int orientation, int imageFormat, Bundle b) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemStatusUpdate(int status, String msg) {
    }
}
