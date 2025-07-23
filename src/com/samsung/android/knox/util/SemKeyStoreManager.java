package com.samsung.android.knox.util;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.knox.util.ISemKeyStoreService;

/* loaded from: classes6.dex */
public class SemKeyStoreManager {

    @Deprecated(forRemoval = true, since = "16.0")
    public static final int KEYSTORE_STATUS_LOCKED = 2;

    @Deprecated(forRemoval = true, since = "16.0")
    public static final int KEYSTORE_STATUS_UNINITIALIZED = 3;

    @Deprecated(forRemoval = true, since = "16.0")
    public static final int KEYSTORE_STATUS_UNKNOWN = 0;

    @Deprecated(forRemoval = true, since = "16.0")
    public static final int KEYSTORE_STATUS_UNLOCKED = 1;
    private ISemKeyStoreService mRemoteServiceKeystore;

    private SemKeyStoreManager(IBinder iBinder) {
        this.mRemoteServiceKeystore = ISemKeyStoreService.Stub.asInterface(iBinder);
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public int installCertInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, boolean z, int i) throws RemoteException {
        return this.mRemoteServiceKeystore.installCertificateInAndroidKeyStore(semCertByte, str, cArr, i);
    }

    public boolean hasAlias(String str, boolean z) throws RemoteException {
        return this.mRemoteServiceKeystore.isAliasExists(str) == 0;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public int installCaCert(SemCertAndroidKeyStore semCertAndroidKeyStore) throws RemoteException {
        return this.mRemoteServiceKeystore.installCACert(semCertAndroidKeyStore);
    }

    public void grantAccess(int i, String str) throws RemoteException {
        this.mRemoteServiceKeystore.grantAccessForAKS(i, str);
    }

    public int getKeystoreStatus() throws RemoteException {
        return this.mRemoteServiceKeystore.getKeystoreStatus();
    }

    public static SemKeyStoreManager getInstance() {
        return new SemKeyStoreManager(ServiceManager.getService("emailksproxy"));
    }
}
