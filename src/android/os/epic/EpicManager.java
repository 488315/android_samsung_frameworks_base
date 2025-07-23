package android.os.epic;

import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class EpicManager {
    private static final String TAG = "EpicManager";
    final IEpicManager mService;

    public EpicManager(IEpicManager iEpicManager) {
        this.mService = iEpicManager;
    }

    IEpicObject Create(int i) {
        IEpicManager iEpicManager = this.mService;
        if (iEpicManager == null) {
            return null;
        }
        try {
            return iEpicManager.Create(i);
        } catch (RemoteException unused) {
            return null;
        }
    }

    IEpicObject Creates(int[] iArr) {
        IEpicManager iEpicManager = this.mService;
        if (iEpicManager == null) {
            return null;
        }
        try {
            return iEpicManager.Creates(iArr);
        } catch (RemoteException unused) {
            return null;
        }
    }
}
