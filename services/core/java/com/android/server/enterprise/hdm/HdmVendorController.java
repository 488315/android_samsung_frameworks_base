package com.android.server.enterprise.hdm;

import android.os.ServiceManager;
import android.util.Log;
import vendor.samsung.hardware.khdm.ISehKhdm;
import vendor.samsung.hardware.khdm.SehDeviceInfo;

/* loaded from: classes2.dex */
public class HdmVendorController {
  public final boolean hidlSupport;

  public static native byte[] hdm_apply_policy(
      byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, boolean z);

  public static native int hdm_delete_keys();

  public static native byte[] hdm_get_id(byte[] bArr, byte[] bArr2, boolean z);

  public static native int hdm_get_key();

  public static native byte[] hdm_get_policy(boolean z, byte[] bArr, byte[] bArr2);

  public static native int hdm_kg_cmd(int i);

  public static native int hdm_load();

  public static native int hdm_set_key(byte[] bArr);

  public static native int hdm_unload();

  public HdmVendorController() {
    int parseInt = Integer.parseInt("33");
    boolean z = !isAidlServiceDeclared();
    this.hidlSupport = z;
    Log.i("Hdm - VendorInterface", "apiLevel: " + parseInt + ", hidlSupport: " + z);
  }

  public int hdmLoad() {
    try {
      if (this.hidlSupport) {
        return hdm_load();
      }
      ISehKhdm service = getService();
      int initiate = service.initiate();
      Log.i("Hdm - VendorInterface", "hdmLoad using ISehKhdm: " + initiate);
      if (initiate != 0) {
        service.terminate();
      }
      return initiate;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmLoad failed: " + e, e);
      return -1;
    }
  }

  public int hdmUnload() {
    try {
      if (this.hidlSupport) {
        return hdm_unload();
      }
      int terminate = getService().terminate();
      Log.i("Hdm - VendorInterface", "hdmUnload using ISehKhdm: " + terminate);
      return terminate;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmUnload failed: " + e, e);
      return -1;
    }
  }

  public int hdmGetKey() {
    try {
      if (this.hidlSupport) {
        return hdm_get_key();
      }
      int checkKey = getService().checkKey();
      Log.i("Hdm - VendorInterface", "hdmGetKey using ISehKhdm: " + checkKey);
      return checkKey;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "checkKey failed: " + e, e);
      return -1;
    }
  }

  public int hdmSetKey(byte[] bArr) {
    try {
      if (this.hidlSupport) {
        return hdm_set_key(bArr);
      }
      int key = getService().setKey(bArr, true);
      Log.i("Hdm - VendorInterface", "hdmSetKey using ISehKhdm: " + key);
      return key;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmSetKey failed: " + e, e);
      return -1;
    }
  }

  public int hdmDeleteKeys() {
    try {
      if (this.hidlSupport) {
        return hdm_delete_keys();
      }
      int deleteKey = getService().deleteKey();
      Log.i("Hdm - VendorInterface", "hdmDeleteKeys using ISehKhdm: " + deleteKey);
      return deleteKey;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmDeleteKeys failed: " + e, e);
      return -1;
    }
  }

  public int hdmKgCmd(int i) {
    try {
      if (this.hidlSupport) {
        return hdm_kg_cmd(i);
      }
      ISehKhdm service = getService();
      int executeHdmCmd = service.executeHdmCmd(i);
      Log.i("Hdm - VendorInterface", "hdmKgCmd using ISehKhdm: " + executeHdmCmd);
      if (executeHdmCmd != 0) {
        service.terminate();
      }
      return executeHdmCmd;
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmKgCmd failed: " + e, e);
      return -1;
    }
  }

  public byte[] hdmGetPolicy(boolean z, byte[] bArr, byte[] bArr2) {
    try {
      if (this.hidlSupport) {
        return hdm_get_policy(z, bArr, bArr2);
      }
      byte[] bArr3 = new byte[15000];
      return parseDeviceResponse(
          "hdmGetPolicy", bArr3, getService().getPolicy(z, bArr, bArr2, bArr3));
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmGetPolicy failed: " + e, e);
      return null;
    }
  }

  public byte[] hdmGetId(byte[] bArr, byte[] bArr2, boolean z) {
    try {
      Log.i("Hdm - VendorInterface", "hdmGetId");
      if (this.hidlSupport) {
        return hdm_get_id(bArr, bArr2, z);
      }
      byte[] bArr3 = new byte[15000];
      return parseDeviceResponse(
          "hdmGetId",
          bArr3,
          getService().getDeviceId(buildSehDeviceInfo(null, bArr, null, bArr2, z), bArr3));
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmGetId failed: " + e, e);
      return null;
    }
  }

  public byte[] hdmApplyPolicy(
      byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5, boolean z) {
    try {
      Log.i("Hdm - VendorInterface", "hdmApplyPolicy policy: ");
      if (this.hidlSupport) {
        return hdm_apply_policy(bArr, bArr2, bArr3, bArr4, bArr5, z);
      }
      byte[] bArr6 = new byte[15000];
      return parseDeviceResponse(
          "hdmApplyPolicy",
          bArr6,
          getService().applyPolicy(bArr, buildSehDeviceInfo(bArr2, bArr3, bArr4, bArr5, z), bArr6));
    } catch (Exception e) {
      Log.e("Hdm - VendorInterface", "hdmApplyPolicy failed: " + e, e);
      return null;
    }
  }

  public final ISehKhdm getService() {
    return ISehKhdm.Stub.asInterface(
        ServiceManager.waitForService("vendor.samsung.hardware.khdm.ISehKhdm/default"));
  }

  public final boolean isAidlServiceDeclared() {
    try {
      boolean isDeclared =
          ServiceManager.isDeclared("vendor.samsung.hardware.khdm.ISehKhdm/default");
      Log.i("Hdm - VendorInterface", "isAidlServiceDeclared: " + isDeclared);
      return isDeclared;
    } catch (Exception e) {
      Log.i("Hdm - VendorInterface", "isAidlServiceDeclared failed: " + e);
      return false;
    }
  }

  public final byte[] parseDeviceResponse(String str, byte[] bArr, int i) {
    Log.i("Hdm - VendorInterface", str + " using ISehKhdm: len: " + i);
    if (i < 10) {
      return null;
    }
    byte[] bArr2 = new byte[i];
    System.arraycopy(bArr, 0, bArr2, 0, i);
    return bArr2;
  }

  public final SehDeviceInfo buildSehDeviceInfo(
      byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, boolean z) {
    SehDeviceInfo sehDeviceInfo = new SehDeviceInfo();
    sehDeviceInfo.serialNumber = constructContent(bArr);
    sehDeviceInfo.imei0 = constructContent(bArr2);
    sehDeviceInfo.hashedImei = constructContent(bArr3);
    sehDeviceInfo.macAddr = constructContent(bArr4);
    sehDeviceInfo.isWrappedKey = z;
    return sehDeviceInfo;
  }

  public final byte[] constructContent(byte[] bArr) {
    byte[] bArr2 = new byte[128];
    if (bArr != null) {
      System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }
    return bArr2;
  }
}
