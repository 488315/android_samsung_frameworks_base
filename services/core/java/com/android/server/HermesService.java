package com.android.server;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.weaver.IWeaver;
import android.hardware.weaver.V1_0.IWeaver;
import android.hardware.weaver.V1_0.WeaverReadResponse;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Slog;

import com.android.server.sepunion.AbsSemSystemService;

import com.samsung.android.service.HermesService.IHermesService;

import vendor.samsung.hardware.security.hermes.SehCommandResult;
import vendor.samsung.hardware.security.hermes.extension.ISehHermesExtension;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HermesService extends IHermesService.Stub implements AbsSemSystemService {
    public static Context mContext;
    public final HermesHalAdapter halAdapter;
    public final AnonymousClass3 mReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.HermesService$1, reason: invalid class name */
    public final class AnonymousClass1 extends Thread {
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                if (!HermesBigdataFunction.CHECK_CHIPSET_LISTS[0].equals(
                        SystemProperties.get("ro.soc.model"))) {
                    throw new BigdataException(
                            HermesBigdataFunction.BigdataError.ERR_NOT_SUPPORTED);
                }
                HermesBigdataFunction.unZipDumpstate();
                HermesBigdataFunction.parseDumpstate();
                HermesBigdataFunction.cleanDumpstateFiles();
                HermesService.m65$$Nest$smreportToDiagmon();
                try {
                    TimeUnit.MINUTES.sleep(1L);
                    HermesBigdataFunction.cleanBigdataLogFiles();
                } catch (BigdataException e) {
                    throw e;
                } catch (InterruptedException unused) {
                    throw new BigdataException(
                            HermesBigdataFunction.BigdataError.ERR_INTERRUPTION_EXCEPTION);
                }
            } catch (BigdataException e2) {
                Slog.e("HERMES#Service", "Some problem has occured, Err = " + e2.getErrCode());
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: -$$Nest$mCollectSkeymasterDumpThread, reason: not valid java name */
    public static void m64$$Nest$mCollectSkeymasterDumpThread(HermesService hermesService) {
        hermesService.getClass();
        Slog.i("HERMES#Service", "CollectSkeymasterDumpThread");
        try {
            new AnonymousClass1().start();
        } catch (Exception e) {
            Slog.e(
                    "HERMES#Service",
                    "Error occurs on CollectSkeymasterDumpThread. Err = " + e.toString());
        }
        Slog.i("HERMES#Service", "CollectSkeymasterDumpThread done");
    }

    /* renamed from: -$$Nest$smreportToDiagmon, reason: not valid java name */
    public static void m65$$Nest$smreportToDiagmon() {
        Intent m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        32, "com.sec.android.diagmonagent.intent.REPORT_ERROR_V2");
        try {
            File file = new File("/data/log/sepunion/hermes/parsed_skeymast.txt");
            if (!file.exists() || file.length() == 0) {
                throw new BigdataException(HermesBigdataFunction.BigdataError.ERR_FILE_NOT_FOUND);
            }
            Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            Bundle bundle3 = new Bundle();
            Bundle bundle4 = new Bundle();
            Bundle bundle5 = new Bundle();
            bundle.putBundle("DiagMon", bundle2);
            bundle2.putBundle("CFailLogUpload", bundle3);
            bundle3.putString("ServiceID", "hgi9mdaexj");
            bundle3.putBundle("Ext", bundle4);
            bundle3.putBundle("IntentOnly", bundle5);
            bundle4.putString("ClientV", "1.0.1");
            bundle4.putString("UiMode", "0");
            bundle4.putString("ResultCode", "Device Key");
            bundle4.putString("WifiOnlyFeature", "1");
            bundle4.putString("EventID", "hgi9mdaexj");
            bundle4.putString("Description", "DEVICE KEY DETECTED");
            bundle5.putString("IntentOnlyMode", "1");
            bundle5.putString("Agree", "D");
            bundle5.putString("LogPath", "/data/log/sepunion/hermes/parsed_skeymast.txt");
            bundle5.putString("ServiceName", "SAMSUNG KEYMASTER");
            m.putExtra("uploadMO", bundle);
            m.setFlags(32);
            m.setPackage("com.sec.android.diagmonagent");
            mContext.sendBroadcast(m);
            Slog.i("HERMES#Service", "reportToDiagmon done");
        } catch (Exception unused) {
            throw new BigdataException(HermesBigdataFunction.BigdataError.ERR_SEND_DIAGMON);
        }
    }

    static {
        System.loadLibrary("hermes_jni");
    }

    public HermesService(Context context) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class: com.android.server.HermesService.3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                            if (((KeyguardManager)
                                                    HermesService.mContext.getSystemService(
                                                            "keyguard"))
                                            .inKeyguardRestrictedInputMode()
                                    && SystemProperties.get("security.securenvm.available")
                                            .equals("true")) {
                                HermesService$3$$ExternalSyntheticOutline0.m(
                                        HermesService.this.halAdapter.SecnvmPowerOn(),
                                        "notify key guard showing to hermesd ret : ",
                                        "HERMES#Service");
                                return;
                            }
                            return;
                        }
                        if ("android.intent.action.DATE_CHANGED".equals(intent.getAction())) {
                            Slog.d("HERMES#Service", "ACTION_DATE_CHANGED intent called");
                            HermesService hermesService = HermesService.this;
                            Context context3 = HermesService.mContext;
                            hermesService.TransmitCollectedDataToServer();
                            HermesService.m64$$Nest$mCollectSkeymasterDumpThread(
                                    HermesService.this);
                            return;
                        }
                        if ("com.sec.android.intent.action.TEST_TRIGGER"
                                .equals(intent.getAction())) {
                            Slog.d("HERMES#Service", "Test intent trigger called");
                            HermesService.m64$$Nest$mCollectSkeymasterDumpThread(
                                    HermesService.this);
                            for (int i = 0; i < 16; i++) {
                                Log.i(
                                        "HERMES#Service",
                                        "failcount("
                                                + i
                                                + ") : "
                                                + HermesService.this.getFailureCount(i));
                            }
                        }
                    }
                };
        mContext = context;
        IntentFilter m =
                BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.SCREEN_ON");
        if (!"user".equals(Build.TYPE)) {
            m.addAction("com.sec.android.intent.action.TEST_TRIGGER");
        }
        m.addAction("android.intent.action.DATE_CHANGED");
        mContext.registerReceiver(broadcastReceiver, m);
        HermesHalAdapter hermesHalAdapter = new HermesHalAdapter();
        hermesHalAdapter.hc = null;
        hermesHalAdapter.hce = null;
        hermesHalAdapter.aidlWeaver = null;
        hermesHalAdapter.hidlWeaver = null;
        hermesHalAdapter.hc = hermesHalAdapter.getService();
        hermesHalAdapter.hce = hermesHalAdapter.getExtAidlService();
        hermesHalAdapter.aidlWeaver = hermesHalAdapter.getAidlWeaverService();
        this.halAdapter = hermesHalAdapter;
    }

    public final void TransmitCollectedDataToServer() {
        try {
            new Thread() { // from class: com.android.server.HermesService.2
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    String bigdataLog = HermesService.this.halAdapter.getBigdataLog();
                    if (bigdataLog == null) {
                        Slog.e("HERMES#Service", "getBigdataLog is null, skip send bigdata.");
                        return;
                    }
                    HermesService.this.getClass();
                    String str = SystemProperties.get("ro.hardware.chipname");
                    String str2 = SystemProperties.get("ro.hardware");
                    StringTokenizer stringTokenizer = new StringTokenizer(bigdataLog, "\r\n");
                    Slog.i("HERMES#Service", "Full String : ".concat(bigdataLog));
                    while (stringTokenizer.hasMoreTokens()) {
                        StringTokenizer stringTokenizer2 =
                                new StringTokenizer(stringTokenizer.nextToken(), "=");
                        if (stringTokenizer2.hasMoreTokens()) {
                            String nextToken = stringTokenizer2.nextToken();
                            if (stringTokenizer2.hasMoreTokens()) {
                                String nextToken2 = stringTokenizer2.nextToken();
                                SemHqmManager semHqmManager =
                                        (SemHqmManager)
                                                HermesService.mContext.getSystemService(
                                                        "HqmManagerService");
                                if (semHqmManager != null) {
                                    Slog.i("HERMES#Service", "sendToHQM data : " + nextToken2);
                                    if (nextToken2 == null) {
                                        Slog.i("HERMES#Service", "bigdata is null.");
                                    } else if (!semHqmManager.sendHWParamToHQM(
                                            0,
                                            "GPU",
                                            nextToken,
                                            "ph",
                                            str,
                                            str2,
                                            "",
                                            nextToken2,
                                            "")) {
                                        Slog.i("HERMES#Service", "sendHWParamToHQM is failed.");
                                    }
                                } else {
                                    Slog.i(
                                            "HERMES#Service",
                                            "HQM service is not alive, skip sending a data.");
                                }
                            } else {
                                Slog.i("HERMES#Service", "Hermes bigdata has wrong value.");
                            }
                        } else {
                            Slog.i("HERMES#Service", "Hermes feature has wrong value.");
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            Slog.e(
                    "HERMES#Service",
                    "Error occurs on TransmitCollectedDataToServer. Err = " + e.toString());
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {}

    public final int getFailureCount(int i) {
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "getFailureCount called!.");
        final int[] iArr = {-100};
        try {
            IWeaver aidlWeaverService = hermesHalAdapter.getAidlWeaverService();
            hermesHalAdapter.aidlWeaver = aidlWeaverService;
            if (aidlWeaverService != null) {
                iArr[0] = (int) aidlWeaverService.read(i + 100000, new byte[] {0}).timeout;
            } else {
                try {
                    hermesHalAdapter.hidlWeaver =
                            android.hardware.weaver.V1_0.IWeaver.getService(false);
                } catch (RemoteException | NoSuchElementException unused) {
                    Slog.w(
                            "HERMES#HalAdapter",
                            "Does not have permissions to get HIDL weaver service");
                }
                android.hardware.weaver.V1_0.IWeaver iWeaver = hermesHalAdapter.hidlWeaver;
                hermesHalAdapter.hidlWeaver = iWeaver;
                if (iWeaver != null) {
                    Slog.i("HERMES#HalAdapter", "supporthidlWeaver");
                    ArrayList arrayList = new ArrayList(16);
                    arrayList.add((byte) 0);
                    ((IWeaver.Proxy) hermesHalAdapter.hidlWeaver)
                            .read(
                                    i + 100000,
                                    arrayList,
                                    new IWeaver
                                            .readCallback() { // from class:
                                                              // com.android.server.HermesHalAdapter$$ExternalSyntheticLambda0
                                        @Override // android.hardware.weaver.V1_0.IWeaver.readCallback
                                        public final void onValues(
                                                int i2, WeaverReadResponse weaverReadResponse) {
                                            String str = HermesHalAdapter.HERMES_AIDL_INTERFACE;
                                            iArr[0] = weaverReadResponse.timeout;
                                        }
                                    });
                    return iArr[0];
                }
                Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iArr[0];
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final byte[] hermesCosPatchTest(byte[] bArr) {
        ISehHermesExtension extAidlService;
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "cosPatchTest called!.");
        try {
            extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extAidlService != null) {
            return ((ISehHermesExtension.Stub.Proxy) extAidlService).updateCOSpatchTest(bArr).msg;
        }
        Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
        return null;
    }

    public final byte[] hermesGetSeId() {
        ISehHermesExtension extAidlService;
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "getSeId called!.");
        try {
            extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extAidlService != null) {
            return ((ISehHermesExtension.Stub.Proxy) extAidlService).getSeId().msg;
        }
        Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
        return null;
    }

    public final byte[] hermesGetSecureHWInfo() {
        return this.halAdapter.GetSecureHWInfo();
    }

    public final int hermesProvisioning() {
        return this.halAdapter.Provisioning();
    }

    public final int hermesSecnvmPowerOff() {
        return this.halAdapter.SecnvmPowerOff();
    }

    public final int hermesSecnvmPowerOn() {
        return this.halAdapter.SecnvmPowerOn();
    }

    public final int hermesSecureHwPowerOff() {
        ISehHermesExtension extAidlService;
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "secureHwPowerOff called!.");
        try {
            extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extAidlService != null) {
            return ((ISehHermesExtension.Stub.Proxy) extAidlService)
                    .turnOffSecureHardwarePower()
                    .result;
        }
        Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
        return -1;
    }

    public final int hermesSecureHwPowerOn() {
        ISehHermesExtension extAidlService;
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "secureHwPowerOn called!.");
        try {
            extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extAidlService != null) {
            return ((ISehHermesExtension.Stub.Proxy) extAidlService)
                    .turnOnSecureHardwarePower()
                    .result;
        }
        Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
        return -1;
    }

    public final byte[] hermesSelftest() {
        return this.halAdapter.Selftest();
    }

    public final byte[] hermesSelftest2(String str) {
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "Selftest called.");
        try {
            ISehHermesExtension extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
            if (extAidlService == null) {
                return null;
            }
            SehCommandResult selftest =
                    ((ISehHermesExtension.Stub.Proxy) hermesHalAdapter.hce)
                            .selftest(HermesHalAdapter.getSehSelftestParameter(str));
            if (selftest.result != 0) {
                return selftest.msg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final byte[] hermesSendApdu(byte[] bArr) {
        ISehHermesExtension extAidlService;
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "sendApdu called!.");
        try {
            extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (extAidlService != null) {
            return ((ISehHermesExtension.Stub.Proxy) extAidlService).sendAPDU(bArr).msg;
        }
        Slog.i("HERMES#HalAdapter", "supportExtensionHal failed");
        return null;
    }

    public final int hermesTerminateService() {
        return this.halAdapter.TerminateService();
    }

    public final byte[] hermesUpdateApplet() {
        HermesHalAdapter hermesHalAdapter = this.halAdapter;
        hermesHalAdapter.getClass();
        Slog.i("HERMES#HalAdapter", "UpdateApplet called.");
        try {
            ISehHermesExtension extAidlService = hermesHalAdapter.getExtAidlService();
            hermesHalAdapter.hce = extAidlService;
            if (extAidlService == null) {
                return null;
            }
            SehCommandResult updateApplet =
                    ((ISehHermesExtension.Stub.Proxy) extAidlService).updateApplet();
            if (updateApplet.result == 0) {
                return updateApplet.msg;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final byte[] hermesUpdateCryptoFW() {
        return this.halAdapter.UpdateCryptoFW();
    }

    public final int hermesVerifyProvisioning() {
        return this.halAdapter.VerifyProvisioning();
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Slog.i("HERMES#Service", "HermesService onBootPhase: " + i);
            TransmitCollectedDataToServer();
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}
}
