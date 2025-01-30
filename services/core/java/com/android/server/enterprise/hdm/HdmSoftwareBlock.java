package com.android.server.enterprise.hdm;

import android.content.Context;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Log;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.hdm.HdmManager;
import com.samsung.android.knox.nfc.NfcPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class HdmSoftwareBlock {
  public static final String TAG = "HDM - " + HdmSoftwareBlock.class.getSimpleName();
  public boolean deferredServiceStarted = false;
  public EnterpriseDeviceManager edm;
  public RestrictionPolicy restrictionPolicy;
  public Map supportedFunctionMap;
  public boolean swBlockEnabled;
  public int targetSubSystems;

  public HdmSoftwareBlock(Context context) {
    try {
      createRootDir();
      EnterpriseDeviceManager enterpriseDeviceManager =
          EnterpriseDeviceManager.getInstance(context);
      this.edm = enterpriseDeviceManager;
      this.restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
      this.supportedFunctionMap = initControlMap();
      this.targetSubSystems = initTargetSubSystems();
      this.swBlockEnabled = isSwBlockEnabled();
      SystemProperties.set(
          "sys.hdm.double.protection.subsystem", Integer.toString(this.targetSubSystems));
    } catch (Exception e) {
      Log.e(TAG, "HdmSoftwareBlock failed: " + e);
    }
  }

  public boolean isSwBlockEnabled() {
    try {
      File file = new File("/data/misc/hdm/config");
      if (file.exists()) {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        try {
          this.swBlockEnabled = !"false".equals(bufferedReader.readLine());
          bufferedReader.close();
        } finally {
        }
      } else {
        this.swBlockEnabled = true;
      }
      Log.i(TAG, "isSwBlockEnabled: " + this.swBlockEnabled);
    } catch (Exception e) {
      Log.i(TAG, "isSwBlockEnabled failed: " + e);
      this.swBlockEnabled = true;
    }
    return this.swBlockEnabled;
  }

  public boolean setSwBlock(boolean z) {
    try {
      File file = new File("/data/misc/hdm/config");
      if (!file.exists()) {
        file.createNewFile();
      }
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      try {
        bufferedWriter.write(z ? "true" : "false");
        bufferedWriter.close();
        this.swBlockEnabled = z;
        Log.d(TAG, "setSwBlock: " + this.swBlockEnabled);
      } finally {
      }
    } catch (Exception e) {
      Log.i(TAG, "setSwBlock failed: " + e);
      this.swBlockEnabled = true;
    }
    return this.swBlockEnabled;
  }

  public int handleSoftwareBlockBefore(byte[] bArr) {
    if (!this.swBlockEnabled) {
      Log.i(TAG, "handleSoftwareBlockBefore: not enabled");
      return 0;
    }
    try {
      int filterRunTimeOnly = filterRunTimeOnly(getTargetHdmPolicy(bArr));
      if ((this.targetSubSystems & 64) == 0 || (filterRunTimeOnly & 64) == 0) {
        return 0;
      }
      String str = TAG;
      Log.i(str, "handleSoftwareBlockBefore for blockNfc: ");
      boolean controlNfc = controlNfc(false);
      Thread.sleep(1000L);
      Log.i(str, "nfc state change: " + controlNfc);
      return !controlNfc ? 1 : 0;
    } catch (Exception e) {
      Log.e(TAG, "handleSoftwareBlockBefore failed: " + e);
      return -1;
    }
  }

  public boolean isNFCBlockedByHDM(String str) {
    try {
      int appliedHdmPolicy = getAppliedHdmPolicy(str.getBytes(StandardCharsets.UTF_8));
      if ((appliedHdmPolicy & 64) == 0 || (this.targetSubSystems & 64) == 0) {
        return false;
      }
      Log.i(TAG, "nfc is blocked: " + appliedHdmPolicy);
      return true;
    } catch (Exception e) {
      Log.e(TAG, "isNFCBlockedByHDM failed: " + e);
      return false;
    }
  }

  public int syncSwBlockFromBoot(String str) {
    try {
      return applySwBlock(getAppliedHdmPolicy(str.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e) {
      Log.e(TAG, "syncSwBlockFromBoot failed: " + e);
      return -1;
    }
  }

  public int handleSoftwareBlockAfter(byte[] bArr) {
    try {
      return applySwBlock(filterRunTimeOnly(getAppliedHdmPolicy(bArr)));
    } catch (Exception e) {
      Log.e(TAG, "handleSoftwareBlockAfter failed: " + e);
      return -1;
    }
  }

  public final int filterRunTimeOnly(int i) {
    try {
      int bootTimeOnlySubSystem = getBootTimeOnlySubSystem();
      int i2 = (~bootTimeOnlySubSystem) & i;
      Log.i(
          TAG,
          "bt: "
              + Integer.toString(bootTimeOnlySubSystem, 16)
              + ", targetForRunTime: "
              + Integer.toString(i2, 16)
              + ", origin: "
              + Integer.toString(i, 16));
      return i2;
    } catch (Exception e) {
      Log.i(TAG, "filterRunTimeOnly failed: " + e);
      return i;
    }
  }

  public final int getBootTimeOnlySubSystem() {
    try {
      return Integer.parseInt(
          SystemProperties.get("ro.vendor.hdm.btonly.subsystem", "0x0").replace("0x", ""), 16);
    } catch (Exception e) {
      Log.e(TAG, "getBootTimeOnlySubSystem failed: " + e);
      return 0;
    }
  }

  public final void createRootDir() {
    File file = new File("/data/misc/hdm");
    if (file.exists()) {
      return;
    }
    file.mkdir();
  }

  public final Map initControlMap() {
    HashMap hashMap = new HashMap();
    hashMap.put(
        1,
        new Function() { // from class:
                         // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            boolean controlCamera;
            controlCamera = HdmSoftwareBlock.this.controlCamera(((Boolean) obj).booleanValue());
            return Boolean.valueOf(controlCamera);
          }
        });
    hashMap.put(
        8,
        new Function() { // from class:
                         // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda1
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            boolean controlWiFi;
            controlWiFi = HdmSoftwareBlock.this.controlWiFi(((Boolean) obj).booleanValue());
            return Boolean.valueOf(controlWiFi);
          }
        });
    hashMap.put(
        16,
        new Function() { // from class:
                         // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda2
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            boolean controlBt;
            controlBt = HdmSoftwareBlock.this.controlBt(((Boolean) obj).booleanValue());
            return Boolean.valueOf(controlBt);
          }
        });
    hashMap.put(
        64,
        new Function() { // from class:
                         // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda3
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            boolean controlNfc;
            controlNfc = HdmSoftwareBlock.this.controlNfc(((Boolean) obj).booleanValue());
            return Boolean.valueOf(controlNfc);
          }
        });
    hashMap.put(
        128,
        new Function() { // from class:
                         // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda4
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            boolean controlMic;
            controlMic = HdmSoftwareBlock.this.controlMic(((Boolean) obj).booleanValue());
            return Boolean.valueOf(controlMic);
          }
        });
    return hashMap;
  }

  public final int initTargetSubSystems() {
    String[] split = HdmManager.getHdmVersion().split(" - ");
    Log.d(TAG, Arrays.toString(split));
    int parseInt = Integer.parseInt(split[1], 16);
    Iterator it = this.supportedFunctionMap.keySet().iterator();
    int i = 0;
    while (it.hasNext()) {
      int intValue = ((Integer) it.next()).intValue();
      if ((intValue & parseInt) != 0) {
        i += intValue;
      }
    }
    Log.i(
        TAG,
        "hdmSubSystems: "
            + Integer.toHexString(parseInt)
            + ": targetSubSystems: "
            + Integer.toHexString(i));
    return i;
  }

  public final int getTargetHdmPolicy(byte[] bArr) {
    JSONObject payload = getPayload(bArr);
    int parseInt =
        Integer.parseInt(payload.getString("deviceBlock").replaceAll("[^a-fA-F0-9]", ""), 16);
    int parseInt2 =
        Integer.parseInt(payload.getString("compromiseBlock").replaceAll("[^a-fA-F0-9]", ""), 16);
    int i = parseInt | parseInt2;
    Log.i(
        TAG,
        "deviceBlock: "
            + Integer.toHexString(parseInt)
            + ", compromiseBlock: "
            + Integer.toHexString(parseInt2)
            + ", mergedBlock: "
            + Integer.toHexString(i));
    return i;
  }

  public final int getAppliedHdmPolicy(byte[] bArr) {
    Object opt = getPayload(bArr).opt("currentPolicy");
    String str = TAG;
    Log.i(str, "currentPolicy: " + opt);
    if (opt == null) {
      Log.i(str, "currentPolicy is not supported by TA");
      return -4;
    }
    return Integer.parseInt(((String) opt).replaceAll("[^a-fA-F0-9]", ""), 16);
  }

  public final JSONObject getPayload(byte[] bArr) {
    if (bArr == null) {
      throw new Exception("response is null");
    }
    if (!this.swBlockEnabled) {
      throw new Exception("swBlockDisabled");
    }
    return new JSONObject(new String(Base64.decode(new String(bArr).split("\\.")[1], 8)));
  }

  public final int applySwBlock(int i) {
    if (i < 0) {
      Log.i(TAG, "applySwBlock failed for " + i);
      return -1;
    }
    int appliedSwBlock = getAppliedSwBlock();
    startDeferredServiceIfNeeded();
    Log.i(
        TAG,
        "hdm: "
            + Integer.toHexString(i)
            + ", sw: "
            + Integer.toHexString(appliedSwBlock)
            + ", supported: "
            + Integer.toHexString(this.targetSubSystems));
    Iterator it = this.supportedFunctionMap.keySet().iterator();
    while (true) {
      if (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        if ((this.targetSubSystems & intValue) != 0) {
          Function function = (Function) this.supportedFunctionMap.get(Integer.valueOf(intValue));
          boolean z = (intValue & i) == 0;
          String str = TAG;
          Log.i(str, "subSystem: " + intValue + ", enabled: " + z);
          if (function != null) {
            Log.i(
                str,
                intValue + ": " + ((Boolean) function.apply(Boolean.valueOf(z))).booleanValue());
          }
        } else {
          Log.i(TAG, intValue + " doesn't supported");
        }
      } else {
        updateAppliedSwBlock(i);
        return 0;
      }
    }
  }

  public final int getAppliedSwBlock() {
    try {
      File file = new File("/data/misc/hdm/state");
      if (!file.exists()) {
        Log.i(TAG, "stateFile is not exist");
        return 0;
      }
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      try {
        int parseInt = Integer.parseInt(bufferedReader.readLine());
        bufferedReader.close();
        return parseInt;
      } finally {
      }
    } catch (Exception e) {
      Log.i(TAG, "getAppliedSwBlock failed: " + e);
      return -1;
    }
  }

  public final void updateAppliedSwBlock(int i) {
    File file = new File("/data/misc/hdm/state");
    if (i == 0) {
      file.delete();
      return;
    }
    if (!file.exists()) {
      file.createNewFile();
    }
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
    try {
      bufferedWriter.write(Integer.toString(i));
      bufferedWriter.close();
    } catch (Throwable th) {
      try {
        bufferedWriter.close();
      } catch (Throwable th2) {
        th.addSuppressed(th2);
      }
      throw th;
    }
  }

  public final boolean controlCamera(boolean z) {
    try {
      return this.restrictionPolicy.setCameraState(z);
    } catch (Exception e) {
      Log.e(TAG, "controlCamera failed: " + e);
      return false;
    }
  }

  public final boolean controlWiFi(boolean z) {
    try {
      return this.restrictionPolicy.allowWiFi(z);
    } catch (Exception e) {
      Log.e(TAG, "controlWiFi failed: " + e);
      return false;
    }
  }

  public final boolean controlBt(boolean z) {
    try {
      return this.restrictionPolicy.allowBluetooth(z);
    } catch (Exception e) {
      Log.e(TAG, "controlBt failed: " + e);
      return false;
    }
  }

  public final boolean controlNfc(boolean z) {
    boolean z2;
    boolean z3;
    NfcPolicy nfcPolicy;
    boolean z4 = false;
    try {
      nfcPolicy = this.edm.getNfcPolicy();
      nfcPolicy.allowNFCStateChange(true);
    } catch (Exception e) {
      e = e;
      z2 = false;
    }
    if (nfcPolicy.isNFCStateChangeAllowed()) {
      z2 = nfcPolicy.startNFC(z);
      try {
        z3 = nfcPolicy.allowNFCStateChange(z);
      } catch (Exception e2) {
        e = e2;
        Log.e(TAG, "handleNfc failed: " + e, e);
        z3 = false;
        z4 = z2;
        Log.i(TAG, "controlNfc: " + z4 + ", " + z3 + ", " + z);
        return z4;
      }
      z4 = z2;
      Log.i(TAG, "controlNfc: " + z4 + ", " + z3 + ", " + z);
      return z4;
    }
    z3 = false;
    Log.i(TAG, "controlNfc: " + z4 + ", " + z3 + ", " + z);
    return z4;
  }

  public final boolean controlMic(boolean z) {
    try {
      return this.restrictionPolicy.setMicrophoneState(z);
    } catch (Exception e) {
      Log.e(TAG, "controlMic failed: " + e);
      return false;
    }
  }

  public final void startDeferredServiceIfNeeded() {
    if (this.deferredServiceStarted) {
      return;
    }
    if (ServiceManager.getService("wifi_policy") == null
        || ServiceManager.getService("bluetooth_policy") == null
        || ServiceManager.getService("misc_policy") == null) {
      EnterpriseDeviceManagerService.getInstance().startDeferredServicesIfNeeded();
      Thread.sleep(1000L);
    }
    this.deferredServiceStarted = true;
  }
}
