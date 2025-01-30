package com.android.server.spay;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.spay.CertInfo;
import android.spay.ITAController;
import android.spay.PaymentTZServiceConfig;
import android.spay.TACommandRequest;
import android.spay.TACommandResponse;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public class TAController extends ITAController.Stub {
  public static final boolean DEBUG = PaymentManagerService.DEBUG;
  public boolean SET_QSEE_SECURE_UI = false;
  public Context mContext;
  public PaymentTZNative mNative;
  public int mTAId;

  public TAController(Context context, int i, PaymentTZServiceConfig.TAConfig tAConfig) {
    if (DEBUG) {
      Log.d(
          "PaymentManagerService",
          "TAController constructor: taId = "
              + i
              + "; maxSendCmdSize = "
              + tAConfig.maxSendCmdSize
              + "; maxRecvRespSize = "
              + tAConfig.maxRecvRespSize);
    }
    this.mContext = context;
    this.mTAId = i;
    this.mNative =
        new PaymentTZNative(
            i,
            tAConfig.taTechnology,
            tAConfig.rootName,
            tAConfig.processName,
            tAConfig.maxSendCmdSize,
            tAConfig.maxRecvRespSize);
  }

  public TACommandResponse processTACommand(TACommandRequest tACommandRequest) {
    TACommandResponse processTACommand;
    if (isBinderAlive()) {
      synchronized (this) {
        PaymentManagerService.checkCallerPermissionFor("processTACommand");
        if (DEBUG) {
          Log.d(
              "PaymentManagerService",
              "TAController::processTACommand: request = "
                  + tACommandRequest
                  + "; request.mCommandId = "
                  + tACommandRequest.mCommandId
                  + "; this.mTAId = "
                  + this.mTAId);
        }
        if (tACommandRequest.mCommandId == 590224) {
          this.SET_QSEE_SECURE_UI = true;
        }
        processTACommand = this.mNative.processTACommand(tACommandRequest);
        this.SET_QSEE_SECURE_UI = false;
      }
      return processTACommand;
    }
    Log.e("PaymentManagerService", "binder for cmd is died");
    return null;
  }

  public boolean loadTA(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) {
    synchronized (this) {
      PaymentManagerService.checkCallerPermissionFor("loadTA");
      boolean z = DEBUG;
      if (z) {
        Log.d("PaymentManagerService", "TAController::loadTA");
      }
      if (parcelFileDescriptor == null) {
        return false;
      }
      int fd = parcelFileDescriptor.getFd();
      if (z) {
        Log.d("PaymentManagerService", "TA fd=" + fd + " offset=" + j + " size=" + j2);
      }
      try {
        return this.mNative.loadTA(this.mContext, fd, j, j2);
      } finally {
        try {
          parcelFileDescriptor.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void unloadTA() {
    synchronized (this) {
      PaymentManagerService.checkCallerPermissionFor("unloadTA");
      if (DEBUG) {
        Log.d("PaymentManagerService", "TAController::unloadTA");
      }
      this.SET_QSEE_SECURE_UI = false;
      this.mNative.unloadTA();
    }
  }

  public boolean makeSystemCall(int i) {
    boolean z = DEBUG;
    if (z) {
      Log.d(
          "PaymentManagerService",
          "entered makeSystemCall in TAController - System Server process");
    }
    PaymentManagerService.checkCallerPermissionFor("makeSystemCall");
    if (z) {
      try {
        Log.d(
            "PaymentManagerService",
            "makesystemcall - start time: " + System.currentTimeMillis() + " ms");
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    if (!makeSysCallInternal(i)) {
      Log.d("PaymentManagerService", "makeSystemCall: failed to make system call");
      return false;
    }
    if (!z) {
      return true;
    }
    Log.d(
        "PaymentManagerService",
        "makeSystemCall: Successful, end time : " + System.currentTimeMillis() + " ms");
    return true;
  }

  /* JADX WARN: Removed duplicated region for block: B:51:0x00cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:58:? A[SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:59:0x00c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0036  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean makeSysCallInternal(int i) {
    FileWriter fileWriter;
    IOException e;
    BufferedWriter bufferedWriter;
    String str = "/sys/devices/system/sec_os_ctrl/migrate_os";
    String str2 = "0";
    switch (i) {
      case 1:
        str2 = "1";
      case 2:
        str = "/sys/class/mstldo/mst_drv/transmit";
        if (DEBUG) {
          Log.d("PaymentManagerService", "Writting \"" + str2 + "\" to -> " + str);
        }
        BufferedWriter bufferedWriter2 = null;
        try {
          fileWriter = new FileWriter(new File(str).getAbsoluteFile());
        } catch (IOException e2) {
          fileWriter = null;
          e = e2;
          bufferedWriter = null;
        } catch (Throwable th) {
          th = th;
          fileWriter = null;
        }
        try {
          bufferedWriter = new BufferedWriter(fileWriter);
          try {
            try {
              bufferedWriter.write(str2);
              try {
                bufferedWriter.close();
              } catch (IOException e3) {
                e3.printStackTrace();
              }
              try {
                fileWriter.close();
                return true;
              } catch (IOException e4) {
                e4.printStackTrace();
                return true;
              }
            } catch (IOException e5) {
              e = e5;
              Log.e("PaymentManagerService", "Error writting \"" + str2 + "\" to file -> " + str);
              e.printStackTrace();
              if (bufferedWriter != null) {
                try {
                  bufferedWriter.close();
                } catch (IOException e6) {
                  e6.printStackTrace();
                }
              }
              if (fileWriter != null) {
                try {
                  fileWriter.close();
                } catch (IOException e7) {
                  e7.printStackTrace();
                }
              }
              return false;
            }
          } catch (Throwable th2) {
            th = th2;
            bufferedWriter2 = bufferedWriter;
            if (bufferedWriter2 != null) {
              try {
                bufferedWriter2.close();
              } catch (IOException e8) {
                e8.printStackTrace();
              }
            }
            if (fileWriter == null) {
              try {
                fileWriter.close();
                throw th;
              } catch (IOException e9) {
                e9.printStackTrace();
                throw th;
              }
            }
            throw th;
          }
        } catch (IOException e10) {
          bufferedWriter = null;
          e = e10;
        } catch (Throwable th3) {
          th = th3;
          if (bufferedWriter2 != null) {}
          if (fileWriter == null) {}
        }
      case 3:
        str = "/dev/mst_ctrl";
        str2 = "1";
        if (DEBUG) {}
        BufferedWriter bufferedWriter22 = null;
        fileWriter = new FileWriter(new File(str).getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(str2);
        bufferedWriter.close();
        fileWriter.close();
        return true;
      case 4:
        str = "/dev/mst_ctrl";
        if (DEBUG) {}
        BufferedWriter bufferedWriter222 = null;
        fileWriter = new FileWriter(new File(str).getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(str2);
        bufferedWriter.close();
        fileWriter.close();
        return true;
      case 5:
        str2 = "b0";
        if (DEBUG) {}
        BufferedWriter bufferedWriter2222 = null;
        fileWriter = new FileWriter(new File(str).getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(str2);
        bufferedWriter.close();
        fileWriter.close();
        return true;
      case 6:
        str2 = "L0";
        if (DEBUG) {}
        BufferedWriter bufferedWriter22222 = null;
        fileWriter = new FileWriter(new File(str).getAbsoluteFile());
        bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(str2);
        bufferedWriter.close();
        fileWriter.close();
        return true;
      default:
        Log.e("PaymentManagerService", "UNKNOWN Command ID: " + i);
        return false;
    }
  }

  public boolean clearDeviceCertificates(String str) {
    PaymentManagerService.checkCallerPermissionFor("clearDeviceCertificates");
    Log.d(
        "PaymentManagerService",
        "TAController::clearDeviceCertificates: Deleting the device certificates for " + str);
    return Utils.deleteDirectory(new File(str));
  }

  public CertInfo checkCertInfo(List list) {
    PaymentManagerService.checkCallerPermissionFor("checkCertInfo");
    if (DEBUG) {
      Log.d("PaymentManagerService", "TAController::checkCertInfo: Lets fetch them if exist");
    }
    CertInfo certInfo = new CertInfo();
    for (int i = 0; i < list.size(); i++) {
      String str = (String) list.get(i);
      certInfo.mCerts.put(str, Utils.readFile(str));
    }
    return certInfo;
  }
}
