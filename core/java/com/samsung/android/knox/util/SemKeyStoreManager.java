package com.samsung.android.knox.util;

import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;

/* loaded from: classes5.dex */
public class SemKeyStoreManager {
  public static final int KEYSTORE_STATUS_LOCKED = 2;
  public static final int KEYSTORE_STATUS_UNINITIALIZED = 3;
  public static final int KEYSTORE_STATUS_UNKNOWN = 0;
  public static final int KEYSTORE_STATUS_UNLOCKED = 1;
  private ISemKeyStoreService mRemoteServiceKeystore;

  private SemKeyStoreManager(IBinder binder) {
    this.mRemoteServiceKeystore = ISemKeyStoreService.Stub.asInterface(binder);
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public int installCertInAndroidKeyStore(
      SemCertByte certificate,
      String aliasName,
      char[] password,
      boolean installWithWIFI,
      int scepUid)
      throws RemoteException {
    return this.mRemoteServiceKeystore.installCertificateInAndroidKeyStore(
        certificate, aliasName, password, scepUid);
  }

  public boolean hasAlias(String alias, boolean arg1) throws RemoteException {
    return this.mRemoteServiceKeystore.isAliasExists(alias) == 0;
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public int installCaCert(SemCertAndroidKeyStore caCert) throws RemoteException {
    return this.mRemoteServiceKeystore.installCACert(caCert);
  }

  public void grantAccess(int uid, String alias) throws RemoteException {
    this.mRemoteServiceKeystore.grantAccessForAKS(uid, alias);
  }

  public int getKeystoreStatus() throws RemoteException {
    return this.mRemoteServiceKeystore.getKeystoreStatus();
  }

  public static SemKeyStoreManager getInstance() {
    return new SemKeyStoreManager(ServiceManager.getService("emailksproxy"));
  }
}
