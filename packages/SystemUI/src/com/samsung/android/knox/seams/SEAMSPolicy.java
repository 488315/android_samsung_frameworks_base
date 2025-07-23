package com.samsung.android.knox.seams;

import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.seams.ISEAMS;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SEAMSPolicy {
    public static final int BBC_SECURED_APPTYPE = 5;
    public static final int CLIPBOARD_DISABLE_BIDIRECTIONAL = 1;
    public static final int CLIPBOARD_ENABLE_BIDIRECTIONAL = 0;
    public static final boolean DEBUG = false;
    public static final int ERROR_ALREADY_CONTAINER_APP = -9;
    public static final int ERROR_CERTS_NOT_MATCHED = -13;
    public static final int ERROR_CONTAINER_COUNTS_OVERFLOW = -7;
    public static final int ERROR_CONTAINER_ID_MISMATCH = -12;
    public static final int ERROR_CONTAINER_NOT_EMPTY = -11;
    public static final int ERROR_NOT_SUPPORTED = -3;
    public static final int ERROR_NO_CERTS = -14;
    public static final int FALSE = 0;
    public static final int GENERIC_SECURED_APPTYPE = 3;
    public static final int GENERIC_TRUSTED_APPTYPE = 4;
    public static final int GET_SERVICE_ERROR = -10;
    public static final int IRM_PLATFORM_APPTYPE = 7;
    public static final int IRM_UNTRUST_APPTYPE = 8;
    public static final int NOT_INSTALLED = -4;
    public static final int POLICY_FAILED = -1;
    public static final int POLICY_OK = 0;
    public static final int POLICY_REFUSED = -2;
    public static final int RUNNING = -8;
    public static final int SET_DEFAULT_MASK = 0;
    public static final String TAG = "SEAMS";
    public static final int TRUE = 1;
    public static SEAMSPolicy mSEAMS;
    public static final Object mSync = new Object();
    public ISEAMS mSEAMService;

    private SEAMSPolicy(ContextInfo contextInfo, Context context) {
    }

    public static SEAMSPolicy createInstance(ContextInfo contextInfo, Context context) {
        return new SEAMSPolicy(contextInfo, context.getApplicationContext());
    }

    public static SEAMSPolicy getInstance(Context context) {
        SEAMSPolicy sEAMSPolicy;
        if (context == null) {
            return null;
        }
        synchronized (mSync) {
            try {
                if (mSEAMS == null) {
                    mSEAMS = new SEAMSPolicy(new ContextInfo(Process.myUid()), context.getApplicationContext());
                }
                sEAMSPolicy = mSEAMS;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sEAMSPolicy;
    }

    public int activateDomain() {
        return -1;
    }

    public int addAppToContainer(String str, String[] strArr, int i, int i2) {
        return -1;
    }

    public int changeAppDomain(String str, int i, String str2, boolean z) {
        return -1;
    }

    public int createSEContainer() {
        return -1;
    }

    public int deActivateDomain() {
        return -1;
    }

    public int forceAuthorized(int i, int i2, String str, String str2) {
        if (getService() != null) {
            return isAuthorized(i, i2, str, str2);
        }
        Log.w(TAG, "Service is null");
        if (Process.myPid() == i) {
            return 0;
        }
        Log.w(TAG, "Process ID rejected.");
        return -1;
    }

    public String getAMSLog() {
        return null;
    }

    public int getAMSLogLevel() {
        return -1;
    }

    public int getAMSMode() {
        return -1;
    }

    public String getAVCLog() {
        return null;
    }

    public int getActivationStatus() {
        return -1;
    }

    public String getDataType(String str) {
        return null;
    }

    public String getDomain(String str) {
        return null;
    }

    public String[] getPackageNamesFromSEContainer(int i, int i2) {
        return null;
    }

    public String getSEAMSLog() {
        return null;
    }

    public int[] getSEContainerIDs() {
        return null;
    }

    public int[] getSEContainerIDsFromPackageName(String str, int i) {
        return null;
    }

    public int getSELinuxMode() {
        return -1;
    }

    public String getSepolicyVersion() {
        return null;
    }

    public final synchronized ISEAMS getService() {
        try {
            if (this.mSEAMService == null) {
                this.mSEAMService = ISEAMS.Stub.asInterface(ServiceManager.getService("SEAMService"));
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mSEAMService;
    }

    public String getSignatureFromCertificate(byte[] bArr) {
        return null;
    }

    public String getSignatureFromMac(String str) {
        return null;
    }

    public String getSignatureFromPackage(String str) {
        return null;
    }

    public int hasKnoxContainers() {
        return -1;
    }

    public int hasSEContainers() {
        return -1;
    }

    public int isAuthorized(int i, int i2, String str, String str2) {
        if (getService() != null) {
            try {
                return this.mSEAMService.isAuthorized(i, i2, str, str2);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to check the authenticity of the caller");
            }
        }
        Log.w(TAG, "SystemService null. Returning GET_SERVICE_ERROR.");
        return -10;
    }

    public int isSEAndroidLogDumpStateInclude() {
        return -1;
    }

    public int isSEPolicyAutoUpdateEnabled() {
        return -1;
    }

    public int loadContainerSetting(String str) {
        return -1;
    }

    public int relabelAppDir(String str) {
        return -1;
    }

    public int relabelData() {
        return -1;
    }

    public int removeAppFromContainer(String str, String[] strArr) {
        return -1;
    }

    public int removeSEContainer(int i) {
        return -1;
    }

    public int setAMSLogLevel(int i) {
        return -1;
    }

    public int setSEAndroidLogDumpStateInclude(boolean z) {
        return -1;
    }

    public int activateDomain(boolean z) {
        return -1;
    }

    public String getDataType(String str, int i) {
        return null;
    }

    public String getDomain(String str, int i) {
        return null;
    }

    public int removeAppFromContainer(String str, String[] strArr, int i, int i2) {
        return -1;
    }

    public static SEAMSPolicy getInstance(ContextInfo contextInfo, Context context) {
        SEAMSPolicy sEAMSPolicy;
        synchronized (mSync) {
            try {
                if (mSEAMS == null) {
                    mSEAMS = new SEAMSPolicy(contextInfo, context.getApplicationContext());
                }
                sEAMSPolicy = mSEAMS;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sEAMSPolicy;
    }
}
