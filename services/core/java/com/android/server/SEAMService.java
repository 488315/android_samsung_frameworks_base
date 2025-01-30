package com.android.server;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.seams.ISEAMS;
import com.samsung.android.knox.seams.SEAMSPolicy;

/* loaded from: classes.dex */
public class SEAMService extends ISEAMS.Stub {
    public static int SELF_PID;
    public static SKLogger mSKLog = SKLogger.getLogger();
    public final Context mContext;
    public final BrHandler mHandler;
    public final HandlerThread mHandlerThread;
    public final Object mLock = new Object[0];
    public final Handler mSKHandler = new Handler();

    public int activateDomain(boolean z) {
        return -1;
    }

    public int addAppToContainer(String str, String str2, int i, int i2) {
        return -1;
    }

    public int changeAppDomain(String str, boolean z) {
        return -1;
    }

    public int createSEContainer() {
        return -1;
    }

    public int deActivateDomain() {
        return -1;
    }

    public String getAMSLog(ContextInfo contextInfo) {
        return null;
    }

    public int getAMSLogLevel(ContextInfo contextInfo) {
        return -1;
    }

    public int getAMSMode(ContextInfo contextInfo) {
        return -1;
    }

    public String getAVCLog(ContextInfo contextInfo) {
        return null;
    }

    public int getActivationStatus() {
        return -1;
    }

    public String getDataType(ContextInfo contextInfo, String str, int i) {
        return null;
    }

    public String getDomain(ContextInfo contextInfo, String str, int i) {
        return null;
    }

    public String[] getPackageNamesFromSEContainer(int i, int i2) {
        return null;
    }

    public String getSEAMSLog(ContextInfo contextInfo) {
        return null;
    }

    public int[] getSEContainerIDs() {
        return null;
    }

    public int[] getSEContainerIDsFromPackageName(String str, int i) {
        return null;
    }

    public int getSELinuxMode(ContextInfo contextInfo) {
        return 0;
    }

    public String getSepolicyVersion(ContextInfo contextInfo) {
        return null;
    }

    public String getSignatureFromCertificate(byte[] bArr) {
        return null;
    }

    public String getSignatureFromPackage(String str) {
        return null;
    }

    public int hasKnoxContainers() {
        return 0;
    }

    public int hasSEContainers() {
        return 0;
    }

    public int isSEAndroidLogDumpStateInclude(ContextInfo contextInfo) {
        return 0;
    }

    public int isSEPolicyAutoUpdateEnabled(ContextInfo contextInfo) {
        return -1;
    }

    public int loadContainerSetting(String str) {
        return -1;
    }

    public int relabelAppDir(String str) {
        return -1;
    }

    public int relabelData(ContextInfo contextInfo) {
        return -1;
    }

    public int removeAppFromContainer(String str, String str2, int i, int i2) {
        return -1;
    }

    public int removeSEContainer(int i) {
        return -1;
    }

    public int setAMSLogLevel(ContextInfo contextInfo, int i) {
        return -1;
    }

    public int setSEAndroidLogDumpStateInclude(ContextInfo contextInfo, boolean z) {
        return -1;
    }

    public SEAMService(Context context) {
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("SEAMService");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new BrHandler(handlerThread.getLooper());
        new Thread("Service Keeper Thread") { // from class: com.android.server.SEAMService.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                SEAMService.this.mSKHandler.post(new Runnable() { // from class: com.android.server.SEAMService.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ServiceKeeper.getServiceKeeper();
                        boolean authorizeLoadProcedure = ServiceKeeper.authorizeLoadProcedure();
                        if (SEAMSPolicy.DEBUG) {
                            SEAMService.mSKLog.logAll("SEAMService", "Service Keeper Initialized = " + authorizeLoadProcedure);
                        }
                    }
                });
            }
        }.start();
        SELF_PID = Process.myPid();
    }

    public final class BrHandler extends Handler {
        @Override // android.os.Handler
        public void handleMessage(Message message) {
        }

        public BrHandler(Looper looper) {
            super(looper);
        }
    }

    public int isAuthorized(int i, int i2, String str, String str2) {
        if (SELF_PID == i) {
            return 0;
        }
        ServiceKeeper.getServiceKeeper();
        if (!ServiceKeeper.isTableActive()) {
            if (SEAMSPolicy.DEBUG) {
                mSKLog.logAll("SEAMService", "Returning 0 directly as tables are not ready in SK.");
            }
            return 0;
        }
        return ServiceKeeper.isAuthorized(this.mContext, i, i2, str, str2);
    }
}
