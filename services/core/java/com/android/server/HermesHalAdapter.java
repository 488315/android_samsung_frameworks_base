package com.android.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;
import vendor.samsung.hardware.security.hermes.ISehHermesCommand;
import vendor.samsung.hardware.security.hermes.SehCommandResult;

/* loaded from: classes.dex */
public final class HermesHalAdapter {
  public boolean supportAidlHal = false;

  /* renamed from: hc */
  public ISehHermesCommand f1617hc = getService();

  private native String getPdpData();

  private native byte[] getSecureHWInfo();

  private native int provisioning();

  private native int secnvmPowerOff();

  private native int secnvmPowerOn();

  private native byte[] selftest();

  private native int terminateService();

  private native byte[] updateCryptoFW();

  private native int verifyProvisioning();

  static {
    System.loadLibrary("hermes_jni");
  }

  public synchronized ISehHermesCommand getService() {
    if (this.f1617hc == null) {
      try {
        ISehHermesCommand asInterface =
            ISehHermesCommand.Stub.asInterface(
                ServiceManager.getService(
                    "vendor.samsung.hardware.security.hermes.ISehHermesCommand/default"));
        this.f1617hc = asInterface;
        if (asInterface == null) {
          Log.w("HERMES#HalAdapter", "getService: halService is null");
          return null;
        }
        Log.d("HERMES#HalAdapter", "get HermesHAL");
        try {
          this.f1617hc
              .asBinder()
              .linkToDeath(
                  new IBinder
                      .DeathRecipient() { // from class: com.android.server.HermesHalAdapter.1
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                      HermesHalAdapter.this.f1617hc.asBinder().unlinkToDeath(this, 0);
                    }
                  },
                  0);
          Log.i("HERMES#HalAdapter", "getService:linkToDeath");
          this.supportAidlHal = true;
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      } catch (Exception e2) {
        e2.printStackTrace();
        return null;
      }
    }
    return this.f1617hc;
  }

  public byte[] GetSecureHWInfo() {
    Slog.i("HERMES#HalAdapter", "GetSecureHWInfo called.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return iSehHermesCommand.getSecureHWInfo().msg;
        }
        return null;
      }
      return getSecureHWInfo();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public byte[] UpdateCryptoFW() {
    Slog.i("HERMES#HalAdapter", "UpdateCryptoFW called.");
    try {
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (this.supportAidlHal) {
      ISehHermesCommand iSehHermesCommand = this.f1617hc;
      if (iSehHermesCommand != null) {
        SehCommandResult secureHWInfo = iSehHermesCommand.getSecureHWInfo();
        if (secureHWInfo.result != 0) {
          return secureHWInfo.msg;
        }
        return null;
      }
      return null;
    }
    return updateCryptoFW();
  }

  public byte[] Selftest() {
    Slog.i("HERMES#HalAdapter", "Selftest called.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand == null || iSehHermesCommand.selftest().result == 0) {
          return null;
        }
        return this.f1617hc.selftest().msg;
      }
      return selftest();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public int Provisioning() {
    Slog.i("HERMES#HalAdapter", "Provisioning called.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return iSehHermesCommand.provisioning().result;
        }
        return -1;
      }
      return provisioning();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public int VerifyProvisioning() {
    Slog.i("HERMES#HalAdapter", "VerifyProvisioning called.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return iSehHermesCommand.verifyProvisioning().result;
        }
        return -1;
      }
      return verifyProvisioning();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public int TerminateService() {
    Slog.i("HERMES#HalAdapter", "TerminateService called.");
    if (!hasAccessPermissionForTerm(Binder.getCallingPid(), Binder.getCallingUid())) {
      Slog.i("HERMES#HalAdapter", "TerminateService denied.");
      return -5;
    }
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return iSehHermesCommand.terminateService().result;
        }
        return -1;
      }
      return terminateService();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public int SecnvmPowerOn() {
    Slog.i("HERMES#HalAdapter", "SecnvmPowerOn called!.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return iSehHermesCommand.secnvmPowerOn().result;
        }
        return -1;
      }
      return secnvmPowerOn();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public String getBigdataLog() {
    Slog.i("HERMES#HalAdapter", "getBigdataLog called!.");
    try {
      if (this.supportAidlHal) {
        ISehHermesCommand iSehHermesCommand = this.f1617hc;
        if (iSehHermesCommand != null) {
          return new String(iSehHermesCommand.getBigdataLog().msg, "euc-kr");
        }
        return null;
      }
      return getPdpData();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final boolean hasAccessPermissionForTerm(int i, int i2) {
    Slog.i("HERMES#HalAdapter", "hasAccessPermissionForTerm start.");
    return 1000 == i2;
  }
}
