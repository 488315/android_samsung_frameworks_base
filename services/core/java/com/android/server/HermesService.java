package com.android.server;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SemHqmManager;
import android.os.SystemProperties;
import android.util.Slog;
import com.android.server.sepunion.AbsSemSystemService;
import com.samsung.android.service.HermesService.IHermesService;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/* loaded from: classes.dex */
public final class HermesService extends IHermesService.Stub implements AbsSemSystemService {
  public static Context mContext;
  public HermesHalAdapter halAdapter;
  public final BroadcastReceiver mReceiver;

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {}

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onCreate(Bundle bundle) {}

  static {
    System.loadLibrary("hermes_jni");
  }

  @Override // com.android.server.sepunion.AbsSemSystemService
  public void onBootPhase(int i) {
    if (i == 1000) {
      Slog.i("HERMES#Service", "HermesService onBootPhase: " + i);
      TransmitCollectedDataToServer();
    }
  }

  public static void reportToDiagmon() {
    Intent intent = new Intent("com.sec.android.diagmonagent.intent.REPORT_ERROR_V2");
    intent.addFlags(32);
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
      intent.putExtra("uploadMO", bundle);
      intent.setFlags(32);
      intent.setPackage("com.sec.android.diagmonagent");
      mContext.sendBroadcast(intent);
      Slog.i("HERMES#Service", "reportToDiagmon done");
    } catch (Exception unused) {
      throw new BigdataException(HermesBigdataFunction.BigdataError.ERR_SEND_DIAGMON);
    }
  }

  public final void CollectSkeymasterDumpThread() {
    Slog.i("HERMES#Service", "CollectSkeymasterDumpThread");
    try {
      new Thread() { // from class: com.android.server.HermesService.1
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
          try {
            HermesBigdataFunction hermesBigdataFunction = new HermesBigdataFunction();
            hermesBigdataFunction.makeSkeymasterDumpstate();
            HermesService.reportToDiagmon();
            hermesBigdataFunction.finishSkeymasterDumpstate();
          } catch (BigdataException e) {
            Slog.e("HERMES#Service", "Some problem has occured, Err = " + e.getErrCode());
            e.printStackTrace();
          }
        }
      }.start();
    } catch (Exception e) {
      Slog.e(
          "HERMES#Service", "Error occurs on CollectSkeymasterDumpThread. Err = " + e.toString());
    }
    Slog.i("HERMES#Service", "CollectSkeymasterDumpThread done");
  }

  public final void TransmitCollectedDataToServer() {
    try {
      new Thread() { // from class: com.android.server.HermesService.2
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
          String bigdataLog = HermesService.this.halAdapter.getBigdataLog();
          if (bigdataLog != null) {
            HermesService.this.sendBigData(bigdataLog);
          } else {
            Slog.e("HERMES#Service", "getBigdataLog is null, skip send bigdata.");
          }
        }
      }.start();
    } catch (Exception e) {
      Slog.e(
          "HERMES#Service", "Error occurs on TransmitCollectedDataToServer. Err = " + e.toString());
    }
  }

  public final int sendBigData(String str) {
    String str2 = SystemProperties.get("ro.hardware.chipname");
    String str3 = SystemProperties.get("ro.hardware");
    StringTokenizer stringTokenizer = new StringTokenizer(str, "\r\n");
    int i = 0;
    while (stringTokenizer.hasMoreTokens()) {
      StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "=");
      if (stringTokenizer2.hasMoreTokens()) {
        String nextToken = stringTokenizer2.nextToken();
        if (stringTokenizer2.hasMoreTokens()) {
          String nextToken2 = stringTokenizer2.nextToken();
          SemHqmManager semHqmManager =
              (SemHqmManager) mContext.getSystemService("HqmManagerService");
          if (semHqmManager != null) {
            Slog.i("HERMES#Service", "sendToHQM data : " + nextToken2);
            if (nextToken2 != null) {
              if (!semHqmManager.sendHWParamToHQM(
                  0, "GPU", nextToken, "ph", str2, str3, "", nextToken2, "")) {
                Slog.i("HERMES#Service", "sendHWParamToHQM is failed.");
                i = -23;
              }
            } else {
              Slog.i("HERMES#Service", "bigdata is null.");
              i = -25;
            }
          } else {
            Slog.i("HERMES#Service", "HQM service is not alive, skip sending a data.");
            i = -26;
          }
        } else {
          Slog.i("HERMES#Service", "Hermes bigdata has wrong value.");
        }
      } else {
        Slog.i("HERMES#Service", "Hermes feature has wrong value.");
      }
    }
    return i;
  }

  public HermesService(Context context) {
    BroadcastReceiver broadcastReceiver =
        new BroadcastReceiver() { // from class: com.android.server.HermesService.3
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
              if (((KeyguardManager) HermesService.mContext.getSystemService("keyguard"))
                      .inKeyguardRestrictedInputMode()
                  && SystemProperties.get("security.securenvm.available").equals("true")) {
                Slog.i(
                    "HERMES#Service",
                    "notify key guard showing to hermesd ret : "
                        + HermesService.this.hermesSecnvmPowerOn());
                return;
              }
              return;
            }
            if ("android.intent.action.DATE_CHANGED".equals(intent.getAction())) {
              Slog.d("HERMES#Service", "ACTION_DATE_CHANGED intent called");
              HermesService.this.TransmitCollectedDataToServer();
              HermesService.this.CollectSkeymasterDumpThread();
            } else if ("com.sec.android.intent.action.TEST_TRIGGER".equals(intent.getAction())) {
              Slog.d("HERMES#Service", "Test intent trigger called");
              HermesService.this.CollectSkeymasterDumpThread();
            }
          }
        };
    this.mReceiver = broadcastReceiver;
    mContext = context;
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    if (!"user".equals(Build.TYPE)) {
      intentFilter.addAction("com.sec.android.intent.action.TEST_TRIGGER");
    }
    intentFilter.addAction("android.intent.action.DATE_CHANGED");
    mContext.registerReceiver(broadcastReceiver, intentFilter);
    this.halAdapter = new HermesHalAdapter();
  }

  public byte[] hermesGetSecureHWInfo() {
    return this.halAdapter.GetSecureHWInfo();
  }

  public byte[] hermesUpdateCryptoFW() {
    return this.halAdapter.UpdateCryptoFW();
  }

  public byte[] hermesSelftest() {
    return this.halAdapter.Selftest();
  }

  public int hermesProvisioning() {
    return this.halAdapter.Provisioning();
  }

  public int hermesVerifyProvisioning() {
    return this.halAdapter.VerifyProvisioning();
  }

  public int hermesTerminateService() {
    return this.halAdapter.TerminateService();
  }

  public int hermesSecnvmPowerOn() {
    return this.halAdapter.SecnvmPowerOn();
  }
}
