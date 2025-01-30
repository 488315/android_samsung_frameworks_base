package com.android.server.biometrics.sensors.fingerprint.aidl;

import com.att.iqi.lib.metrics.p020mm.MM05;

/* loaded from: classes.dex */
public abstract class AidlConversionUtils {
  public static byte toAidlAcquiredInfo(int i) {
    if (i == 8) {
      return (byte) 0;
    }
    if (i == 0) {
      return (byte) 1;
    }
    if (i == 1) {
      return (byte) 2;
    }
    if (i == 2) {
      return (byte) 3;
    }
    if (i == 3) {
      return (byte) 4;
    }
    if (i == 4) {
      return (byte) 5;
    }
    if (i == 5) {
      return (byte) 6;
    }
    if (i == 6) {
      return (byte) 7;
    }
    if (i == 7) {
      return (byte) 8;
    }
    if (i == 10) {
      return (byte) 10;
    }
    if (i == 9) {
      return MM05.IQ_SIP_CALL_STATE_DISCONNECTING;
    }
    return (byte) 8;
  }

  public static byte toAidlError(int i) {
    if (i == 17) {
      return (byte) 0;
    }
    if (i == 1) {
      return (byte) 1;
    }
    if (i == 2) {
      return (byte) 2;
    }
    if (i == 3) {
      return (byte) 3;
    }
    if (i == 4) {
      return (byte) 4;
    }
    if (i == 5) {
      return (byte) 5;
    }
    if (i == 6) {
      return (byte) 6;
    }
    if (i == 8) {
      return (byte) 7;
    }
    return i == 18 ? (byte) 8 : (byte) 0;
  }

  public static int toFrameworkAcquiredInfo(byte b) {
    if (b == 0) {
      return 8;
    }
    if (b == 1) {
      return 0;
    }
    if (b == 2) {
      return 1;
    }
    if (b == 3) {
      return 2;
    }
    if (b == 4) {
      return 3;
    }
    if (b == 5) {
      return 4;
    }
    if (b == 6) {
      return 5;
    }
    if (b == 7) {
      return 6;
    }
    if (b == 8) {
      return 7;
    }
    if (b == 9) {
      return 8;
    }
    if (b == 10) {
      return 10;
    }
    if (b == 11) {
      return 9;
    }
    return (b != 12 && b == 14) ? 11 : 8;
  }

  public static int toFrameworkError(byte b) {
    if (b == 0) {
      return 17;
    }
    if (b == 1) {
      return 1;
    }
    if (b == 2) {
      return 2;
    }
    if (b == 3) {
      return 3;
    }
    if (b == 4) {
      return 4;
    }
    if (b == 5) {
      return 5;
    }
    if (b == 6) {
      return 6;
    }
    if (b == 7) {
      return 8;
    }
    if (b == 8) {
      return 18;
    }
    return b == 9 ? 19 : 17;
  }
}
