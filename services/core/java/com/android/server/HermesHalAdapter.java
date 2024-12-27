package com.android.server;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.weaver.IWeaver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

import vendor.samsung.hardware.security.hermes.ISehHermesCommand;
import vendor.samsung.hardware.security.hermes.SehCommandResult;
import vendor.samsung.hardware.security.hermes.extension.ISehHermesExtension;
import vendor.samsung.hardware.security.hermes.extension.SehSelftestParameter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HermesHalAdapter {
    public static final String HERMES_AIDL_INTERFACE =
            AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    new StringBuilder(), ISehHermesCommand.DESCRIPTOR, "/default");
    public IWeaver aidlWeaver;
    public ISehHermesCommand hc;
    public ISehHermesExtension hce;
    public android.hardware.weaver.V1_0.IWeaver hidlWeaver;

    static {
        String str = ISehHermesExtension.DESCRIPTOR;
        System.loadLibrary("hermes_jni");
    }

    private native String getPdpData();

    private native byte[] getSecureHWInfo();

    public static SehSelftestParameter[] getSehSelftestParameter(String str) {
        String[] strArr;
        String[] strArr2;
        try {
            strArr = str.split("/");
        } catch (Exception e) {
            e.printStackTrace();
            strArr = null;
        }
        if (strArr == null) {
            return null;
        }
        SehSelftestParameter[] sehSelftestParameterArr = new SehSelftestParameter[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            String str2 = strArr[i];
            sehSelftestParameterArr[i] = new SehSelftestParameter();
            try {
                strArr2 = str2.split(":");
            } catch (Exception e2) {
                e2.printStackTrace();
                strArr2 = null;
            }
            if (strArr2.length > 0) {
                sehSelftestParameterArr[i].type = Integer.parseInt(strArr2[0]);
                sehSelftestParameterArr[i].cnt = Integer.parseInt(strArr2[1]);
            }
        }
        return sehSelftestParameterArr;
    }

    private native int provisioning();

    private native int secnvmPowerOff();

    private native int secnvmPowerOn();

    private native byte[] selftest();

    private native int terminateService();

    private native byte[] updateCryptoFW();

    private native int verifyProvisioning();

    public final byte[] GetSecureHWInfo() {
        Slog.i("HERMES#HalAdapter", "GetSecureHWInfo called.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            if (service == null) {
                return getSecureHWInfo();
            }
            SehCommandResult secureHWInfo =
                    ((ISehHermesCommand.Stub.Proxy) service).getSecureHWInfo();
            if (secureHWInfo.result == 0) {
                return secureHWInfo.msg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int Provisioning() {
        Slog.i("HERMES#HalAdapter", "Provisioning called.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            return service != null
                    ? ((ISehHermesCommand.Stub.Proxy) service).provisioning().result
                    : provisioning();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final int SecnvmPowerOff() {
        Slog.i("HERMES#HalAdapter", "SecnvmPowerOff called!.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            return service != null
                    ? ((ISehHermesCommand.Stub.Proxy) service).secnvmPowerOff().result
                    : secnvmPowerOff();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final int SecnvmPowerOn() {
        Slog.i("HERMES#HalAdapter", "SecnvmPowerOn called!.");
        try {
            this.hc = getService();
            this.hce = getExtAidlService();
            ISehHermesCommand iSehHermesCommand = this.hc;
            return iSehHermesCommand != null
                    ? ((ISehHermesCommand.Stub.Proxy) iSehHermesCommand).secnvmPowerOn().result
                    : secnvmPowerOn();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final byte[] Selftest() {
        Slog.i("HERMES#HalAdapter", "Selftest called.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            if (service == null) {
                return selftest();
            }
            SehCommandResult selftest = ((ISehHermesCommand.Stub.Proxy) service).selftest();
            if (selftest.result != 0) {
                return selftest.msg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int TerminateService() {
        Slog.i("HERMES#HalAdapter", "TerminateService called.");
        Binder.getCallingPid();
        int callingUid = Binder.getCallingUid();
        Slog.i("HERMES#HalAdapter", "hasAccessPermissionForTerm start.");
        if (1000 != callingUid) {
            Slog.i("HERMES#HalAdapter", "TerminateService denied.");
            return -5;
        }
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            return service != null
                    ? ((ISehHermesCommand.Stub.Proxy) service).terminateService().result
                    : terminateService();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final byte[] UpdateCryptoFW() {
        Slog.i("HERMES#HalAdapter", "UpdateCryptoFW called.");
        try {
            this.hc = getService();
            ISehHermesExtension extAidlService = getExtAidlService();
            this.hce = extAidlService;
            if (extAidlService != null) {
                SehCommandResult updateCOSpatch =
                        ((ISehHermesExtension.Stub.Proxy) extAidlService).updateCOSpatch();
                if (updateCOSpatch.result == 0) {
                    return updateCOSpatch.msg;
                }
                return null;
            }
            ISehHermesCommand iSehHermesCommand = this.hc;
            if (iSehHermesCommand == null) {
                return updateCryptoFW();
            }
            SehCommandResult updateCryptoFW =
                    ((ISehHermesCommand.Stub.Proxy) iSehHermesCommand).updateCryptoFW();
            if (updateCryptoFW.result == 0) {
                return updateCryptoFW.msg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final int VerifyProvisioning() {
        Slog.i("HERMES#HalAdapter", "VerifyProvisioning called.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            return service != null
                    ? ((ISehHermesCommand.Stub.Proxy) service).verifyProvisioning().result
                    : verifyProvisioning();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final IWeaver getAidlWeaverService() {
        IWeaver iWeaver;
        try {
            StringBuilder sb = new StringBuilder();
            String str = IWeaver.DESCRIPTOR;
            sb.append(str);
            sb.append("/default");
            IBinder waitForDeclaredService = ServiceManager.waitForDeclaredService(sb.toString());
            int i = IWeaver.Stub.$r8$clinit;
            if (waitForDeclaredService == null) {
                iWeaver = null;
            } else {
                IInterface queryLocalInterface = waitForDeclaredService.queryLocalInterface(str);
                if (queryLocalInterface == null || !(queryLocalInterface instanceof IWeaver)) {
                    IWeaver.Stub.Proxy proxy = new IWeaver.Stub.Proxy();
                    proxy.mCachedVersion = -1;
                    proxy.mRemote = waitForDeclaredService;
                    iWeaver = proxy;
                } else {
                    iWeaver = (IWeaver) queryLocalInterface;
                }
            }
            this.aidlWeaver = iWeaver;
        } catch (SecurityException unused) {
            Slog.w("HERMES#HalAdapter", "Does not have permissions to get AIDL weaver service");
        }
        return this.aidlWeaver;
    }

    public final String getBigdataLog() {
        Slog.i("HERMES#HalAdapter", "getBigdataLog called!.");
        try {
            ISehHermesCommand service = getService();
            this.hc = service;
            return service != null
                    ? new String(
                            ((ISehHermesCommand.Stub.Proxy) service).getBigdataLog().msg, "euc-kr")
                    : getPdpData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final synchronized ISehHermesExtension getExtAidlService() {
        try {
            ISehHermesExtension asInterface =
                    ISehHermesExtension.Stub.asInterface(
                            ServiceManager.getService(HERMES_AIDL_INTERFACE).getExtension());
            this.hce = asInterface;
            if (asInterface == null) {
                Log.w("HERMES#HalAdapter", "getExtAidlService: ext halService is null");
                return null;
            }
            Log.d("HERMES#HalAdapter", "get getExtAidlService" + this.hce);
            return this.hce;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final synchronized ISehHermesCommand getService() {
        try {
            ISehHermesCommand asInterface =
                    ISehHermesCommand.Stub.asInterface(
                            ServiceManager.getService(HERMES_AIDL_INTERFACE));
            this.hc = asInterface;
            if (asInterface == null) {
                Log.w("HERMES#HalAdapter", "getService: halService is null");
                return null;
            }
            Log.d("HERMES#HalAdapter", "get HermesHAL : " + this.hc);
            return this.hc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
