package com.samsung.android.authenticator;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceManager;

import com.samsung.android.authnrservice.manager.ISemAuthnrService;

import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
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
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            int result = service.getVersion();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getVersion failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    static boolean initialize(ParcelFileDescriptor fd, long offset, long len) {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.initialize(fd, offset, len);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initialize failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminate() {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.terminate();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminate failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] process(byte[] command) {
        byte[] result = new byte[0];
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            byte[] result2 = service.process(command);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "process failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    static boolean setChallenge(byte[] challenge) {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.setChallenge(challenge);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "setChallenge failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] getWrappedObject(byte[] challenge) {
        byte[] result = new byte[0];
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            byte[] result2 = service.getWrappedObject(challenge);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getWrappedObject failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    static boolean initializeDrk() {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.initializeDrk();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeDrk failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminateDrk() {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.terminateDrk();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateDrk failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] getDrkKeyHandle() {
        byte[] result = new byte[0];
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            byte[] result2 = service.getDrkKeyHandle();
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getDrkKeyHandle failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    static boolean writeFile(byte[] data, String path) {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.writeFile(data, path);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "writeFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean deleteFile(String path) {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.deleteFile(path);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "deleteFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static List<String> getFiles(String path, String filter) {
        List<String> result = Collections.emptyList();
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            List<String> result2 = service.getFiles(path, filter);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getFiles failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    static boolean initializeWithPreloadedTa() {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.initializeWithPreloadedTa();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminateWithPreloadedTa() {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.terminateWithPreloadedTa();
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] processWithPreloadedTa(byte[] command, String appId) {
        byte[] result = new byte[0];
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            byte[] result2 = service.processWithPreloadedTa(command, appId);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "processWithPreloadedTa failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    static String readFile(String path) {
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            String result = service.readFile(path);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "readFile failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return "";
        }
    }

    static List<String> getMatchedFilePaths(String path, String filter) {
        List<String> result = Collections.emptyList();
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            List<String> result2 = service.getMatchedFilePaths(path, filter);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "getMatchedFilePaths failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }

    private static <T> T checkNotNullState(T reference) {
        if (reference == null) {
            throw new IllegalStateException("can not found service");
        }
        return reference;
    }

    private AuthenticatorService() {
        throw new AssertionError();
    }

    static boolean initializePreloadedTa(int trustedAppType) {
        AuthenticatorLog.d(TAG, "static boolean initializePreloadedTa(int trustedAppType)");
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.initializePreloadedTa(trustedAppType);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "initializeWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static boolean terminatePreloadedTa(int trustedAppType) {
        AuthenticatorLog.d(TAG, "static boolean terminatePreloadedTa(int trustedAppType)");
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            boolean result = service.terminatePreloadedTa(trustedAppType);
            return result;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "terminateWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return false;
        }
    }

    static byte[] processPreloadedTa(int trustedAppType, byte[] command) {
        AuthenticatorLog.d(
                TAG, "static byte[] processPreloadedTa(int trustedAppType, byte[] command)");
        byte[] result = new byte[0];
        ISemAuthnrService service = (ISemAuthnrService) checkNotNullState(getService());
        try {
            byte[] result2 = service.processPreloadedTa(trustedAppType, command);
            return result2;
        } catch (RemoteException e) {
            AuthenticatorLog.e(TAG, "processWithPreloadedTap failed : " + e.getMessage());
            e.rethrowFromSystemServer();
            return result;
        }
    }
}
