package com.android.server.enterprise.hdm;

import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Log;

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseService;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.hdm.HdmManager;
import com.samsung.android.knox.nfc.NfcPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdmSoftwareBlock {
    public boolean deferredServiceStarted;
    public EnterpriseDeviceManager edm;
    public RestrictionPolicy restrictionPolicy;
    public Map supportedFunctionMap;
    public boolean swBlockEnabled;
    public int targetSubSystems;

    public static int filterRunTimeOnly(int i) {
        try {
            int subSystem = getSubSystem("ro.vendor.hdm.btonly.subsystem");
            int i2 = (~subSystem) & i;
            Log.i(
                    "HDM - HdmSoftwareBlock",
                    "bt: "
                            + Integer.toString(subSystem, 16)
                            + ", targetForRunTime: "
                            + Integer.toString(i2, 16)
                            + ", origin: "
                            + Integer.toString(i, 16));
            return i2;
        } catch (Exception e) {
            Log.i("HDM - HdmSoftwareBlock", "filterRunTimeOnly failed: " + e);
            return i;
        }
    }

    public static int getSubSystem(String str) {
        try {
            return Integer.parseInt(SystemProperties.get(str, "0x0").replace("0x", ""), 16);
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "getSubSystem failed: ", "HDM - HdmSoftwareBlock");
            return 0;
        }
    }

    public final int applySwBlock(int i, boolean z) {
        int i2 = -1;
        if (i < 0) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "applySwBlock failed for ", "HDM - HdmSoftwareBlock");
            return -1;
        }
        try {
            File file = new File("/data/misc/hdm/state");
            if (file.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                try {
                    int parseInt = Integer.parseInt(bufferedReader.readLine());
                    bufferedReader.close();
                    i2 = parseInt;
                } finally {
                }
            } else {
                Log.i("HDM - HdmSoftwareBlock", "stateFile is not exist");
                i2 = 0;
            }
        } catch (Exception e) {
            Log.i("HDM - HdmSoftwareBlock", "getAppliedSwBlock failed: " + e);
        }
        boolean z2 = true;
        if (!this.deferredServiceStarted) {
            if (ServiceManager.getService("wifi_policy") == null
                    || ServiceManager.getService("bluetooth_policy") == null
                    || ServiceManager.getService("misc_policy") == null) {
                int i3 = EnterpriseDeviceManagerService.$r8$clinit;
                ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance)
                        .startDeferredServicesIfNeeded();
                Thread.sleep(1000L);
            }
            this.deferredServiceStarted = true;
        }
        int subSystem = getSubSystem("ro.vendor.hdm.btonly.subsystem");
        int subSystem2 = getSubSystem("ro.vendor.hdm.btonly.unblock.subsystem");
        StringBuilder sb = new StringBuilder("hdm: ");
        sb.append(Integer.toHexString(i));
        sb.append(", sw: ");
        sb.append(Integer.toHexString(i2));
        sb.append(", supported: ");
        int i4 = this.targetSubSystems;
        sb.append(Integer.toHexString(i4));
        Log.i("HDM - HdmSoftwareBlock", sb.toString());
        for (Integer num : ((HashMap) this.supportedFunctionMap).keySet()) {
            int intValue = num.intValue();
            if ((intValue & i4) != 0) {
                Function function = (Function) ((HashMap) this.supportedFunctionMap).get(num);
                boolean z3 = (intValue & i) == 0 ? z2 : false;
                if (function != null) {
                    if (z) {
                        if (((z3 ? subSystem2 : subSystem) & intValue) != 0) {
                            StringBuilder sb2 = new StringBuilder("skip ");
                            sb2.append(z3 ? "unblock" : "block");
                            sb2.append(" for ");
                            sb2.append(intValue);
                            Log.i("HDM - HdmSoftwareBlock", sb2.toString());
                        }
                    }
                    boolean booleanValue =
                            ((Boolean) function.apply(Boolean.valueOf(z3))).booleanValue();
                    StringBuilder sb3 = new StringBuilder("apply ");
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            intValue, z3 ? "unblock" : "block", " for ", ": ", sb3);
                    FlashNotificationsController$$ExternalSyntheticOutline0.m(
                            "HDM - HdmSoftwareBlock", sb3, booleanValue);
                }
            } else {
                Log.i("HDM - HdmSoftwareBlock", intValue + " doesn't supported");
            }
            z2 = true;
        }
        File file2 = new File("/data/misc/hdm/state");
        if (i == 0) {
            file2.delete();
            return 0;
        }
        if (!file2.exists()) {
            file2.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
        try {
            bufferedWriter.write(Integer.toString(i));
            bufferedWriter.close();
            return 0;
        } finally {
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
        if (!nfcPolicy.isNFCStateChangeAllowed()) {
            z3 = false;
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "HDM - HdmSoftwareBlock",
                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                            "controlNfc: ", z4, ", ", z3, ", "),
                    z);
            return z4;
        }
        z2 = nfcPolicy.startNFC(z);
        try {
            z4 = nfcPolicy.allowNFCStateChange(z);
        } catch (Exception e2) {
            e = e2;
            Log.e("HDM - HdmSoftwareBlock", "handleNfc failed: " + e, e);
            z3 = z4;
            z4 = z2;
            FlashNotificationsController$$ExternalSyntheticOutline0.m(
                    "HDM - HdmSoftwareBlock",
                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                            "controlNfc: ", z4, ", ", z3, ", "),
                    z);
            return z4;
        }
        z3 = z4;
        z4 = z2;
        FlashNotificationsController$$ExternalSyntheticOutline0.m(
                "HDM - HdmSoftwareBlock",
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "controlNfc: ", z4, ", ", z3, ", "),
                z);
        return z4;
    }

    public final int getAppliedHdmPolicy(byte[] bArr) {
        Object opt = getPayload(bArr).opt("currentPolicy");
        Log.i("HDM - HdmSoftwareBlock", "currentPolicy: " + opt);
        if (opt != null) {
            return Integer.parseInt(((String) opt).replaceAll("[^a-fA-F0-9]", ""), 16);
        }
        Log.i("HDM - HdmSoftwareBlock", "currentPolicy is not supported by TA");
        return -4;
    }

    public final JSONObject getPayload(byte[] bArr) {
        if (bArr == null) {
            throw new Exception("response is null");
        }
        if (this.swBlockEnabled) {
            return new JSONObject(new String(Base64.decode(new String(bArr).split("\\.")[1], 8)));
        }
        throw new Exception("swBlockDisabled");
    }

    public final int getTargetHdmPolicy(byte[] bArr) {
        JSONObject payload = getPayload(bArr);
        int parseInt =
                Integer.parseInt(
                        payload.getString("deviceBlock").replaceAll("[^a-fA-F0-9]", ""), 16);
        int parseInt2 =
                Integer.parseInt(
                        payload.getString("compromiseBlock").replaceAll("[^a-fA-F0-9]", ""), 16);
        int i = parseInt | parseInt2;
        Log.i(
                "HDM - HdmSoftwareBlock",
                "deviceBlock: "
                        + Integer.toHexString(parseInt)
                        + ", compromiseBlock: "
                        + Integer.toHexString(parseInt2)
                        + ", mergedBlock: "
                        + Integer.toHexString(i));
        return i;
    }

    public final int handleSoftwareBlockBefore(byte[] bArr) {
        if (!this.swBlockEnabled) {
            Log.i("HDM - HdmSoftwareBlock", "handleSoftwareBlockBefore: not enabled");
            return 0;
        }
        try {
            int filterRunTimeOnly = filterRunTimeOnly(getTargetHdmPolicy(bArr));
            if ((this.targetSubSystems & 64) == 0 || (filterRunTimeOnly & 64) == 0) {
                return 0;
            }
            Log.i("HDM - HdmSoftwareBlock", "handleSoftwareBlockBefore for blockNfc: ");
            boolean controlNfc = controlNfc(false);
            Thread.sleep(1000L);
            Log.i("HDM - HdmSoftwareBlock", "nfc state change: " + controlNfc);
            return !controlNfc ? 1 : 0;
        } catch (Exception e) {
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    e, "handleSoftwareBlockBefore failed: ", "HDM - HdmSoftwareBlock");
            return -1;
        }
    }

    public final Map initControlMap() {
        HashMap hashMap = new HashMap();
        final int i = 0;
        hashMap.put(
                1,
                new Function(
                        this) { // from class:
                                // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
                    public final /* synthetic */ HdmSoftwareBlock f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        int i2 = i;
                        HdmSoftwareBlock hdmSoftwareBlock = this.f$0;
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        switch (i2) {
                            case 0:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z =
                                            hdmSoftwareBlock.restrictionPolicy.setCameraState(
                                                    booleanValue);
                                } catch (Exception e) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e, "controlCamera failed: ", "HDM - HdmSoftwareBlock");
                                    z = false;
                                }
                                return Boolean.valueOf(z);
                            case 1:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z2 = hdmSoftwareBlock.restrictionPolicy.allowWiFi(booleanValue);
                                } catch (Exception e2) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e2, "controlWiFi failed: ", "HDM - HdmSoftwareBlock");
                                    z2 = false;
                                }
                                return Boolean.valueOf(z2);
                            case 2:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z3 =
                                            hdmSoftwareBlock.restrictionPolicy.allowBluetooth(
                                                    booleanValue);
                                } catch (Exception e3) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e3, "controlBt failed: ", "HDM - HdmSoftwareBlock");
                                    z3 = false;
                                }
                                return Boolean.valueOf(z3);
                            case 3:
                                return Boolean.valueOf(hdmSoftwareBlock.controlNfc(booleanValue));
                            default:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z4 =
                                            hdmSoftwareBlock.restrictionPolicy.setMicrophoneState(
                                                    booleanValue);
                                } catch (Exception e4) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e4, "controlMic failed: ", "HDM - HdmSoftwareBlock");
                                    z4 = false;
                                }
                                return Boolean.valueOf(z4);
                        }
                    }
                });
        final int i2 = 1;
        hashMap.put(
                8,
                new Function(
                        this) { // from class:
                                // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
                    public final /* synthetic */ HdmSoftwareBlock f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        int i22 = i2;
                        HdmSoftwareBlock hdmSoftwareBlock = this.f$0;
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        switch (i22) {
                            case 0:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z =
                                            hdmSoftwareBlock.restrictionPolicy.setCameraState(
                                                    booleanValue);
                                } catch (Exception e) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e, "controlCamera failed: ", "HDM - HdmSoftwareBlock");
                                    z = false;
                                }
                                return Boolean.valueOf(z);
                            case 1:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z2 = hdmSoftwareBlock.restrictionPolicy.allowWiFi(booleanValue);
                                } catch (Exception e2) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e2, "controlWiFi failed: ", "HDM - HdmSoftwareBlock");
                                    z2 = false;
                                }
                                return Boolean.valueOf(z2);
                            case 2:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z3 =
                                            hdmSoftwareBlock.restrictionPolicy.allowBluetooth(
                                                    booleanValue);
                                } catch (Exception e3) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e3, "controlBt failed: ", "HDM - HdmSoftwareBlock");
                                    z3 = false;
                                }
                                return Boolean.valueOf(z3);
                            case 3:
                                return Boolean.valueOf(hdmSoftwareBlock.controlNfc(booleanValue));
                            default:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z4 =
                                            hdmSoftwareBlock.restrictionPolicy.setMicrophoneState(
                                                    booleanValue);
                                } catch (Exception e4) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e4, "controlMic failed: ", "HDM - HdmSoftwareBlock");
                                    z4 = false;
                                }
                                return Boolean.valueOf(z4);
                        }
                    }
                });
        final int i3 = 2;
        hashMap.put(
                16,
                new Function(
                        this) { // from class:
                                // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
                    public final /* synthetic */ HdmSoftwareBlock f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        int i22 = i3;
                        HdmSoftwareBlock hdmSoftwareBlock = this.f$0;
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        switch (i22) {
                            case 0:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z =
                                            hdmSoftwareBlock.restrictionPolicy.setCameraState(
                                                    booleanValue);
                                } catch (Exception e) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e, "controlCamera failed: ", "HDM - HdmSoftwareBlock");
                                    z = false;
                                }
                                return Boolean.valueOf(z);
                            case 1:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z2 = hdmSoftwareBlock.restrictionPolicy.allowWiFi(booleanValue);
                                } catch (Exception e2) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e2, "controlWiFi failed: ", "HDM - HdmSoftwareBlock");
                                    z2 = false;
                                }
                                return Boolean.valueOf(z2);
                            case 2:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z3 =
                                            hdmSoftwareBlock.restrictionPolicy.allowBluetooth(
                                                    booleanValue);
                                } catch (Exception e3) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e3, "controlBt failed: ", "HDM - HdmSoftwareBlock");
                                    z3 = false;
                                }
                                return Boolean.valueOf(z3);
                            case 3:
                                return Boolean.valueOf(hdmSoftwareBlock.controlNfc(booleanValue));
                            default:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z4 =
                                            hdmSoftwareBlock.restrictionPolicy.setMicrophoneState(
                                                    booleanValue);
                                } catch (Exception e4) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e4, "controlMic failed: ", "HDM - HdmSoftwareBlock");
                                    z4 = false;
                                }
                                return Boolean.valueOf(z4);
                        }
                    }
                });
        final int i4 = 3;
        hashMap.put(
                64,
                new Function(
                        this) { // from class:
                                // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
                    public final /* synthetic */ HdmSoftwareBlock f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        int i22 = i4;
                        HdmSoftwareBlock hdmSoftwareBlock = this.f$0;
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        switch (i22) {
                            case 0:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z =
                                            hdmSoftwareBlock.restrictionPolicy.setCameraState(
                                                    booleanValue);
                                } catch (Exception e) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e, "controlCamera failed: ", "HDM - HdmSoftwareBlock");
                                    z = false;
                                }
                                return Boolean.valueOf(z);
                            case 1:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z2 = hdmSoftwareBlock.restrictionPolicy.allowWiFi(booleanValue);
                                } catch (Exception e2) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e2, "controlWiFi failed: ", "HDM - HdmSoftwareBlock");
                                    z2 = false;
                                }
                                return Boolean.valueOf(z2);
                            case 2:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z3 =
                                            hdmSoftwareBlock.restrictionPolicy.allowBluetooth(
                                                    booleanValue);
                                } catch (Exception e3) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e3, "controlBt failed: ", "HDM - HdmSoftwareBlock");
                                    z3 = false;
                                }
                                return Boolean.valueOf(z3);
                            case 3:
                                return Boolean.valueOf(hdmSoftwareBlock.controlNfc(booleanValue));
                            default:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z4 =
                                            hdmSoftwareBlock.restrictionPolicy.setMicrophoneState(
                                                    booleanValue);
                                } catch (Exception e4) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e4, "controlMic failed: ", "HDM - HdmSoftwareBlock");
                                    z4 = false;
                                }
                                return Boolean.valueOf(z4);
                        }
                    }
                });
        final int i5 = 4;
        hashMap.put(
                128,
                new Function(
                        this) { // from class:
                                // com.android.server.enterprise.hdm.HdmSoftwareBlock$$ExternalSyntheticLambda0
                    public final /* synthetic */ HdmSoftwareBlock f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        int i22 = i5;
                        HdmSoftwareBlock hdmSoftwareBlock = this.f$0;
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        switch (i22) {
                            case 0:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z =
                                            hdmSoftwareBlock.restrictionPolicy.setCameraState(
                                                    booleanValue);
                                } catch (Exception e) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e, "controlCamera failed: ", "HDM - HdmSoftwareBlock");
                                    z = false;
                                }
                                return Boolean.valueOf(z);
                            case 1:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z2 = hdmSoftwareBlock.restrictionPolicy.allowWiFi(booleanValue);
                                } catch (Exception e2) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e2, "controlWiFi failed: ", "HDM - HdmSoftwareBlock");
                                    z2 = false;
                                }
                                return Boolean.valueOf(z2);
                            case 2:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z3 =
                                            hdmSoftwareBlock.restrictionPolicy.allowBluetooth(
                                                    booleanValue);
                                } catch (Exception e3) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e3, "controlBt failed: ", "HDM - HdmSoftwareBlock");
                                    z3 = false;
                                }
                                return Boolean.valueOf(z3);
                            case 3:
                                return Boolean.valueOf(hdmSoftwareBlock.controlNfc(booleanValue));
                            default:
                                hdmSoftwareBlock.getClass();
                                try {
                                    z4 =
                                            hdmSoftwareBlock.restrictionPolicy.setMicrophoneState(
                                                    booleanValue);
                                } catch (Exception e4) {
                                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                                            e4, "controlMic failed: ", "HDM - HdmSoftwareBlock");
                                    z4 = false;
                                }
                                return Boolean.valueOf(z4);
                        }
                    }
                });
        return hashMap;
    }

    public final int initTargetSubSystems() {
        String[] split = HdmManager.getHdmVersion().split(" - ");
        Log.d("HDM - HdmSoftwareBlock", Arrays.toString(split));
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
                "HDM - HdmSoftwareBlock",
                "hdmSubSystems: "
                        + Integer.toHexString(parseInt)
                        + ": targetSubSystems: "
                        + Integer.toHexString(i));
        return i;
    }

    public final boolean isSwBlockEnabled() {
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
            Log.i("HDM - HdmSoftwareBlock", "isSwBlockEnabled: " + this.swBlockEnabled);
        } catch (Exception e) {
            Log.i("HDM - HdmSoftwareBlock", "isSwBlockEnabled failed: " + e);
            this.swBlockEnabled = true;
        }
        return this.swBlockEnabled;
    }

    public final boolean setSwBlock(boolean z) {
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
                Log.d("HDM - HdmSoftwareBlock", "setSwBlock: " + this.swBlockEnabled);
            } finally {
            }
        } catch (Exception e) {
            Log.i("HDM - HdmSoftwareBlock", "setSwBlock failed: " + e);
            this.swBlockEnabled = true;
        }
        return this.swBlockEnabled;
    }
}
