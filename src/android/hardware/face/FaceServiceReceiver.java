package android.hardware.face;

import android.hardware.face.IFaceServiceReceiver;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public class FaceServiceReceiver extends IFaceServiceReceiver.Stub {
    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAcquired(int i, int i2) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFailed() throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationFrame(FaceAuthenticationFrame faceAuthenticationFrame) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onAuthenticationSucceeded(Face face, int i, boolean z) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollResult(Face face, int i) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onEnrollmentFrame(FaceEnrollFrame faceEnrollFrame) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onError(int i, int i2) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFaceDetected(int i, int i2, boolean z) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureGet(boolean z, int[] iArr, boolean[] zArr) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onFeatureSet(boolean z, int i) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onRemoved(Face face, int i) throws RemoteException {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemAuthenticationSucceeded(Face face, int i, boolean z, byte[] bArr) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemAuthenticationSucceededWithBundle(Face face, int i, boolean z, Bundle bundle) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemImageProcessed(byte[] bArr, int i, int i2, int i3, int i4, Bundle bundle) {
    }

    @Override // android.hardware.face.IFaceServiceReceiver
    public void onSemStatusUpdate(int i, String str) {
    }
}
