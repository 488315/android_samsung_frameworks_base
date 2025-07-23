package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.authnrservice.manager.ISemAuthnrService;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
final class AuthenticatorService {
    private static final String SERVICE_NAME = "SemAuthnrService";
    private static final String TAG = "AS";
    private static ISemAuthnrService sService;

    private static ISemAuthnrService getService() {
        if (sService == null) {
            sService = ISemAuthnrService.Stub.asInterface(ServiceManager.getService(SERVICE_NAME));
        }
        return sService;
    }

    static int getVersion() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).getVersion();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getVersion failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    static boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).initialize(parcelFileDescriptor, j, j2);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initialize failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminate() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).terminate();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminate failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] process(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).process(bArr);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr2;
        }
    }

    static boolean setChallenge(byte[] bArr) {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).setChallenge(bArr);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "setChallenge failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] getWrappedObject(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).getWrappedObject(bArr);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getWrappedObject failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr2;
        }
    }

    static boolean initializeDrk() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).initializeDrk();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeDrk failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminateDrk() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).terminateDrk();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateDrk failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] getDrkKeyHandle() {
        byte[] bArr = new byte[0];
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).getDrkKeyHandle();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getDrkKeyHandle failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr;
        }
    }

    static boolean writeFile(byte[] bArr, String str) {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).writeFile(bArr, str);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "writeFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean deleteFile(String str) {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).deleteFile(str);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "deleteFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static List<String> getFiles(String str, String str2) {
        List<String> list = Collections.EMPTY_LIST;
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).getFiles(str, str2);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getFiles failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return list;
        }
    }

    static boolean initializeWithPreloadedTa() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).initializeWithPreloadedTa();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminateWithPreloadedTa() {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).terminateWithPreloadedTa();
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] processWithPreloadedTa(byte[] bArr, String str) {
        byte[] bArr2 = new byte[0];
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).processWithPreloadedTa(bArr, str);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "processWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr2;
        }
    }

    static String readFile(String str) {
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).readFile(str);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "readFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return "";
        }
    }

    static List<String> getMatchedFilePaths(String str, String str2) {
        List<String> list = Collections.EMPTY_LIST;
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).getMatchedFilePaths(str, str2);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getMatchedFilePaths failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return list;
        }
    }

    private static <T> T checkNotNullState(T t) {
        if (t != null) {
            return t;
        }
        throw new IllegalStateException("can not found service");
    }

    private AuthenticatorService() {
        throw new AssertionError();
    }

    static boolean initializePreloadedTa(int i) {
        AuthenticatorLog.d(TAG, "static boolean initializePreloadedTa(int trustedAppType)");
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).initializePreloadedTa(i);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminatePreloadedTa(int i) {
        AuthenticatorLog.d(TAG, "static boolean terminatePreloadedTa(int trustedAppType)");
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).terminatePreloadedTa(i);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] processPreloadedTa(int i, byte[] bArr) {
        AuthenticatorLog.d(TAG, "static byte[] processPreloadedTa(int trustedAppType, byte[] command)");
        byte[] bArr2 = new byte[0];
        try {
            return ((ISemAuthnrService) checkNotNullState(getService())).processPreloadedTa(i, bArr);
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "processWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return bArr2;
        }
    }
}
