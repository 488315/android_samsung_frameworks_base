package android.security;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.StrictMode;
import android.security.maintenance.IKeystoreMaintenance;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;

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
        StrictMode.noteDiskWrite();
        try {
            getService().onUserAdded(userId);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserAdded failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int initUserSuperKeys(int userId, byte[] password, boolean allowExisting) {
        StrictMode.noteDiskWrite();
        try {
            getService().initUserSuperKeys(userId, password, allowExisting);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "initUserSuperKeys failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserRemoved(int userId) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserRemoved(userId);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserRemoved failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserPasswordChanged(int userId, byte[] password) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserPasswordChanged(userId, password);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserPasswordChanged failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserLskfRemoved(int userId) {
        StrictMode.noteDiskWrite();
        try {
            getService().onUserLskfRemoved(userId);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "onUserLskfRemoved failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int clearNamespace(int r10, long r11) {
        /*
            java.lang.String r1 = "AndroidKeyStoreMaintenance"
            r8 = -1
            android.os.StrictMode.noteDiskWrite()
            android.security.maintenance.IKeystoreMaintenance r0 = getService()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c android.os.ServiceSpecificException -> L6f
            r0.clearNamespace(r10, r11)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c android.os.ServiceSpecificException -> L6f
            if (r10 != 0) goto L16
            int r0 = (int) r11
            int r0 = android.os.UserHandle.getUserId(r0)
            goto L1e
        L16:
            int r0 = android.os.Binder.getCallingUid()
            int r0 = android.os.UserHandle.getUserId(r0)
        L1e:
            boolean r1 = android.security.KeyStoreAuditLog.isAuditLogEnabledAsUser(r0)
            if (r1 == 0) goto L37
        L26:
            r1 = 0
            r5 = 1
            java.lang.String r6 = "AndroidKeyStoreMaintenance"
            r2 = r11
            r4 = r10
            r7 = r8
            android.security.KeyStoreAuditLog$AuditLogParams r1 = android.security.KeyStoreAuditLog.AuditLogParams.init(r1, r2, r4, r5, r6, r7)
            r1.setUserId(r0)
            android.security.KeyStoreAuditLog.auditLogPrivilegedAsUser(r1)
        L37:
            r0 = 0
            return r0
        L39:
            r0 = move-exception
            goto La4
        L3c:
            r0 = move-exception
            java.lang.String r2 = "Can not connect to keystore"
            android.util.Log.e(r1, r2, r0)     // Catch: java.lang.Throwable -> L39
            r8 = 4
            if (r10 != 0) goto L4c
            int r1 = (int) r11
            int r1 = android.os.UserHandle.getUserId(r1)
            goto L54
        L4c:
            int r1 = android.os.Binder.getCallingUid()
            int r1 = android.os.UserHandle.getUserId(r1)
        L54:
            r9 = r1
            boolean r1 = android.security.KeyStoreAuditLog.isAuditLogEnabledAsUser(r9)
            if (r1 == 0) goto L6d
        L5c:
            r1 = 0
            r5 = 1
            java.lang.String r6 = "AndroidKeyStoreMaintenance"
            r2 = r11
            r4 = r10
            r7 = r8
            android.security.KeyStoreAuditLog$AuditLogParams r1 = android.security.KeyStoreAuditLog.AuditLogParams.init(r1, r2, r4, r5, r6, r7)
            r1.setUserId(r9)
            android.security.KeyStoreAuditLog.auditLogPrivilegedAsUser(r1)
        L6d:
            r1 = 4
            return r1
        L6f:
            r0 = move-exception
            java.lang.String r2 = "clearNamespace failed"
            android.util.Log.e(r1, r2, r0)     // Catch: java.lang.Throwable -> L39
            int r7 = r0.errorCode     // Catch: java.lang.Throwable -> L39
            int r8 = r0.errorCode     // Catch: java.lang.Throwable -> La2
            if (r10 != 0) goto L81
            int r1 = (int) r11
            int r1 = android.os.UserHandle.getUserId(r1)
            goto L89
        L81:
            int r1 = android.os.Binder.getCallingUid()
            int r1 = android.os.UserHandle.getUserId(r1)
        L89:
            r9 = r1
            boolean r1 = android.security.KeyStoreAuditLog.isAuditLogEnabledAsUser(r9)
            if (r1 == 0) goto La1
        L91:
            r1 = 0
            r5 = 1
            java.lang.String r6 = "AndroidKeyStoreMaintenance"
            r2 = r11
            r4 = r10
            android.security.KeyStoreAuditLog$AuditLogParams r1 = android.security.KeyStoreAuditLog.AuditLogParams.init(r1, r2, r4, r5, r6, r7)
            r1.setUserId(r9)
            android.security.KeyStoreAuditLog.auditLogPrivilegedAsUser(r1)
        La1:
            return r8
        La2:
            r0 = move-exception
            r8 = r7
        La4:
            if (r10 != 0) goto Lac
            int r1 = (int) r11
            int r1 = android.os.UserHandle.getUserId(r1)
            goto Lb4
        Lac:
            int r1 = android.os.Binder.getCallingUid()
            int r1 = android.os.UserHandle.getUserId(r1)
        Lb4:
            r9 = r1
            boolean r1 = android.security.KeyStoreAuditLog.isAuditLogEnabledAsUser(r9)
            if (r1 == 0) goto Lcd
        Lbc:
            r1 = 0
            r5 = 1
            java.lang.String r6 = "AndroidKeyStoreMaintenance"
            r2 = r11
            r4 = r10
            r7 = r8
            android.security.KeyStoreAuditLog$AuditLogParams r1 = android.security.KeyStoreAuditLog.AuditLogParams.init(r1, r2, r4, r5, r6, r7)
            r1.setUserId(r9)
            android.security.KeyStoreAuditLog.auditLogPrivilegedAsUser(r1)
        Lcd:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.security.AndroidKeyStoreMaintenance.clearNamespace(int, long):int");
    }

    public static int migrateKeyNamespace(KeyDescriptor source, KeyDescriptor destination) {
        StrictMode.noteDiskWrite();
        try {
            getService().migrateKeyNamespace(source, destination);
            return 0;
        } catch (ServiceSpecificException e) {
            Log.e(TAG, "migrateKeyNamespace failed", e);
            return e.errorCode;
        } catch (Exception e2) {
            Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static long[] getAllAppUidsAffectedBySid(int userId, long userSecureId)
            throws KeyStoreException {
        StrictMode.noteDiskWrite();
        try {
            return getService().getAppUidsAffectedBySid(userId, userSecureId);
        } catch (RemoteException | NullPointerException e) {
            throw new KeyStoreException(
                    4, "Failure to connect to Keystore while trying to get apps affected by SID.");
        } catch (ServiceSpecificException e2) {
            throw new KeyStoreException(
                    e2.errorCode, "Keystore error while trying to get apps affected by SID.");
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
            throw new KeyStoreException(
                    e2.errorCode, "Keystore error while trying to delete all keys.");
        }
    }
}
