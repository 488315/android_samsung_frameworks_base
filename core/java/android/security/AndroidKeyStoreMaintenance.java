package android.security;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.os.UserHandle;
import android.security.maintenance.IKeystoreMaintenance;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;

/* loaded from: classes3.dex */
public class AndroidKeyStoreMaintenance {
  public static final int INVALID_ARGUMENT = 20;
  public static final int KEY_NOT_FOUND = 7;
  public static final int PERMISSION_DENIED = 6;
  public static final int SYSTEM_ERROR = 4;
  private static final String TAG = "AndroidKeyStoreMaintenance";

  private static IKeystoreMaintenance getService() {
    return IKeystoreMaintenance.Stub.asInterface(
        ServiceManager.checkService("android.security.maintenance"));
  }

  public static int onUserAdded(int userId) {
    try {
      getService().onUserAdded(userId);
      return 0;
    } catch (ServiceSpecificException e) {
      Log.m97e(TAG, "onUserAdded failed", e);
      return e.errorCode;
    } catch (Exception e2) {
      Log.m97e(TAG, "Can not connect to keystore", e2);
      return 4;
    }
  }

  public static int onUserRemoved(int userId) {
    try {
      getService().onUserRemoved(userId);
      return 0;
    } catch (ServiceSpecificException e) {
      Log.m97e(TAG, "onUserRemoved failed", e);
      return e.errorCode;
    } catch (Exception e2) {
      Log.m97e(TAG, "Can not connect to keystore", e2);
      return 4;
    }
  }

  public static int onUserPasswordChanged(int userId, byte[] password) {
    try {
      getService().onUserPasswordChanged(userId, password);
      return 0;
    } catch (ServiceSpecificException e) {
      Log.m97e(TAG, "onUserPasswordChanged failed", e);
      return e.errorCode;
    } catch (Exception e2) {
      Log.m97e(TAG, "Can not connect to keystore", e2);
      return 4;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:16:0x00a0  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x00b5  */
  /* JADX WARN: Removed duplicated region for block: B:21:0x00a6  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static int clearNamespace(int domain, long namespace) {
    int userId;
    int errorCode = 1;
    try {
      try {
        try {
          getService().clearNamespace(domain, namespace);
          int userId2 =
              domain == 0
                  ? UserHandle.getUserId((int) namespace)
                  : UserHandle.getUserId(Binder.getCallingUid());
          if (!KeyStoreAuditLog.isAuditLogEnabledAsUser(userId2)) {
            return 0;
          }
          KeyStoreAuditLog.AuditLogParams params =
              KeyStoreAuditLog.AuditLogParams.init(null, namespace, domain, 1, TAG, 1);
          params.setUserId(userId2);
          KeyStoreAuditLog.auditLogPrivilegedAsUser(params);
          return 0;
        } catch (Exception e) {
          Log.m97e(TAG, "Can not connect to keystore", e);
          int userId3 =
              domain == 0
                  ? UserHandle.getUserId((int) namespace)
                  : UserHandle.getUserId(Binder.getCallingUid());
          if (!KeyStoreAuditLog.isAuditLogEnabledAsUser(userId3)) {
            return 4;
          }
          KeyStoreAuditLog.AuditLogParams params2 =
              KeyStoreAuditLog.AuditLogParams.init(null, namespace, domain, 1, TAG, 4);
          params2.setUserId(userId3);
          KeyStoreAuditLog.auditLogPrivilegedAsUser(params2);
          return 4;
        }
      } catch (ServiceSpecificException e2) {
        Log.m97e(TAG, "clearNamespace failed", e2);
        int errorCode2 = e2.errorCode;
        try {
          int errorCode3 = e2.errorCode;
          int userId4 =
              domain == 0
                  ? UserHandle.getUserId((int) namespace)
                  : UserHandle.getUserId(Binder.getCallingUid());
          if (KeyStoreAuditLog.isAuditLogEnabledAsUser(userId4)) {
            KeyStoreAuditLog.AuditLogParams params3 =
                KeyStoreAuditLog.AuditLogParams.init(null, namespace, domain, 1, TAG, errorCode2);
            params3.setUserId(userId4);
            KeyStoreAuditLog.auditLogPrivilegedAsUser(params3);
          }
          return errorCode3;
        } catch (Throwable th) {
          e = th;
          errorCode = errorCode2;
          userId =
              domain != 0
                  ? UserHandle.getUserId((int) namespace)
                  : UserHandle.getUserId(Binder.getCallingUid());
          if (KeyStoreAuditLog.isAuditLogEnabledAsUser(userId)) {}
          throw e;
        }
      }
    } catch (Throwable th2) {
      e = th2;
      userId =
          domain != 0
              ? UserHandle.getUserId((int) namespace)
              : UserHandle.getUserId(Binder.getCallingUid());
      if (KeyStoreAuditLog.isAuditLogEnabledAsUser(userId)) {
        KeyStoreAuditLog.AuditLogParams params4 =
            KeyStoreAuditLog.AuditLogParams.init(null, namespace, domain, 1, TAG, errorCode);
        params4.setUserId(userId);
        KeyStoreAuditLog.auditLogPrivilegedAsUser(params4);
      }
      throw e;
    }
  }

  public static int getState(int userId) {
    try {
      return getService().getState(userId);
    } catch (ServiceSpecificException e) {
      Log.m97e(TAG, "getState failed", e);
      return e.errorCode;
    } catch (Exception e2) {
      Log.m97e(TAG, "Can not connect to keystore", e2);
      return 4;
    }
  }

  public static void onDeviceOffBody() {
    try {
      getService().onDeviceOffBody();
    } catch (Exception e) {
      Log.m97e(TAG, "Error while reporting device off body event.", e);
    }
  }

  public static int migrateKeyNamespace(KeyDescriptor source, KeyDescriptor destination) {
    try {
      getService().migrateKeyNamespace(source, destination);
      return 0;
    } catch (ServiceSpecificException e) {
      Log.m97e(TAG, "migrateKeyNamespace failed", e);
      return e.errorCode;
    } catch (Exception e2) {
      Log.m97e(TAG, "Can not connect to keystore", e2);
      return 4;
    }
  }

  public static void deleteAllKeys() throws KeyStoreException {
    StrictMode.noteDiskWrite();
    try {
      getService().deleteAllKeys();
    } catch (RemoteException | NullPointerException e) {
      throw new KeyStoreException(
          4, "Failure to connect to Keystore while trying to delete all keys.");
    } catch (ServiceSpecificException e2) {
      throw new KeyStoreException(e2.errorCode, "Keystore error while trying to delete all keys.");
    }
  }
}
