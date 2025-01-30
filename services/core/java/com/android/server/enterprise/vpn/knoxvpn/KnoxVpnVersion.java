package com.android.server.enterprise.vpn.knoxvpn;

import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.knox.EdmConstants;

/* loaded from: classes2.dex */
public abstract class KnoxVpnVersion {
  public static String getVersion() {
    return "2.3.0";
  }

  public static void writeVersionInProperties() {
    Log.d("KnoxVpnVersion", "writeVersionInProperties : " + getVersion());
    SystemProperties.set("net.knoxvpn.version", getVersionByKnox());
  }

  /* renamed from: com.android.server.enterprise.vpn.knoxvpn.KnoxVpnVersion$1 */
  public abstract /* synthetic */ class AbstractC15081 {

    /* renamed from: $SwitchMap$com$samsung$android$knox$EdmConstants$EnterpriseKnoxSdkVersion */
    public static final /* synthetic */ int[] f1699x3e864b8;

    static {
      int[] iArr = new int[EdmConstants.EnterpriseKnoxSdkVersion.values().length];
      f1699x3e864b8 = iArr;
      try {
        iArr[EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_NONE.ordinal()] = 1;
      } catch (NoSuchFieldError unused) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0.ordinal()] =
            2;
      } catch (NoSuchFieldError unused2) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0_1.ordinal()] =
            3;
      } catch (NoSuchFieldError unused3) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_0_2.ordinal()] =
            4;
      } catch (NoSuchFieldError unused4) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_1_0.ordinal()] =
            5;
      } catch (NoSuchFieldError unused5) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_1_2_0.ordinal()] =
            6;
      } catch (NoSuchFieldError unused6) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_0.ordinal()] =
            7;
      } catch (NoSuchFieldError unused7) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_1.ordinal()] =
            8;
      } catch (NoSuchFieldError unused8) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_2.ordinal()] =
            9;
      } catch (NoSuchFieldError unused9) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_3.ordinal()] =
            10;
      } catch (NoSuchFieldError unused10) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4.ordinal()] =
            11;
      } catch (NoSuchFieldError unused11) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_4_1.ordinal()] =
            12;
      } catch (NoSuchFieldError unused12) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5.ordinal()] =
            13;
      } catch (NoSuchFieldError unused13) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_5_1.ordinal()] =
            14;
      } catch (NoSuchFieldError unused14) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_6.ordinal()] =
            15;
      } catch (NoSuchFieldError unused15) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7.ordinal()] =
            16;
      } catch (NoSuchFieldError unused16) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_7_1.ordinal()] =
            17;
      } catch (NoSuchFieldError unused17) {
      }
      try {
        f1699x3e864b8[
                EdmConstants.EnterpriseKnoxSdkVersion.KNOX_ENTERPRISE_SDK_VERSION_2_8.ordinal()] =
            18;
      } catch (NoSuchFieldError unused18) {
      }
    }
  }

  public static String getVersionByKnox() {
    switch (AbstractC15081.f1699x3e864b8[EdmConstants.getEnterpriseKnoxSdkVersion().ordinal()]) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      default:
        return "2.2.0";
      case 13:
        return "2.2.2";
      case 14:
        return "2.2.3";
      case 15:
        return "2.2.4";
      case 16:
      case 17:
        return "2.3.0";
      case 18:
        return "2.4.0";
    }
  }
}
