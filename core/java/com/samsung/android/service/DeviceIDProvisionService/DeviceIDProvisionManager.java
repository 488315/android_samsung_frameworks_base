package com.samsung.android.service.DeviceIDProvisionService;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore2.AndroidKeyStoreSpi;
import android.telecom.ParcelableCallAnalytics;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;

import javax.crypto.KeyGenerator;

/* loaded from: classes6.dex */
public class DeviceIDProvisionManager {
    private static final String TAG = "DeviceIDProvisionManager";
    private Context mContext;

    /* JADX INFO: Access modifiers changed from: private */
    public native int installDeviceID(
            int i,
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            String str8,
            String str9);

    static {
        System.loadLibrary("_nativeJni.dk.samsung");
    }

    public DeviceIDProvisionManager(Context context) {
        this.mContext = context;
    }

    private static boolean isNotExistCriticalKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            Key key = keyStore.getKey("alias_for_first_boot", null);
            return key == null;
        } catch (IOException
                | KeyStoreException
                | NoSuchAlgorithmException
                | UnrecoverableKeyException
                | CertificateException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int generateCriticalKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
            keyStore.load(null);
            Key key = keyStore.getKey("alias_for_first_boot", null);
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance("AES", AndroidKeyStoreSpi.NAME);
                KeyGenParameterSpec.Builder specBuilder =
                        new KeyGenParameterSpec.Builder("alias_for_first_boot", 3);
                specBuilder.setDigests("SHA-256");
                specBuilder.setCriticalToDeviceEncryption(true);
                kg.init(specBuilder.build());
                kg.generateKey();
                return 0;
            }
            return 0;
        } catch (IOException
                | InvalidAlgorithmParameterException
                | KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | UnrecoverableKeyException
                | CertificateException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private int provisionDeviceID(int keyType, boolean isBootTime) {
        String meid;
        String brand = Build.BRAND_FOR_ATTESTATION;
        String device = Build.DEVICE_FOR_ATTESTATION;
        String produt = Build.PRODUCT_FOR_ATTESTATION;
        String manufacturer = Build.MANUFACTURER_FOR_ATTESTATION;
        String model = Build.MODEL_FOR_ATTESTATION;
        String serial = Build.getSerial();
        String[] imei = new String[2];
        String meid2 = null;
        try {
            boolean isWiFiDevice =
                    SystemProperties.get("ro.carrier").toUpperCase(Locale.US).contains("WIFI-ONLY");
            if (!isWiFiDevice) {
                try {
                    TelephonyManager telephonyService =
                            (TelephonyManager) this.mContext.getSystemService("phone");
                    int simSlotCount =
                            Integer.parseInt(SystemProperties.get("ro.multisim.simslotcount"));
                    for (int i = 0; i < simSlotCount; i++) {
                        while (imei[i] == null) {
                            imei[i] = telephonyService.getImei(i);
                            if (imei[i] == null && isBootTime) {
                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException | UnsupportedOperationException e) {
                                    e = e;
                                    e.printStackTrace();
                                    meid = null;
                                    return installDeviceID(
                                            keyType,
                                            brand,
                                            device,
                                            produt,
                                            serial,
                                            imei[0],
                                            imei[1],
                                            meid,
                                            manufacturer,
                                            model);
                                }
                            }
                        }
                    }
                    meid2 = telephonyService.getMeid(0);
                } catch (InterruptedException | UnsupportedOperationException e2) {
                    e = e2;
                    e.printStackTrace();
                    meid = null;
                    return installDeviceID(
                            keyType,
                            brand,
                            device,
                            produt,
                            serial,
                            imei[0],
                            imei[1],
                            meid,
                            manufacturer,
                            model);
                }
            }
            meid = meid2;
        } catch (InterruptedException | UnsupportedOperationException e3) {
            e = e3;
        }
        return installDeviceID(
                keyType,
                brand,
                device,
                produt,
                serial,
                imei[0],
                imei[1],
                meid,
                manufacturer,
                model);
    }

    public boolean isAvailable() {
        boolean isFactoryBinary =
                "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
        boolean isProductionDevice = "0x0".equals(SystemProperties.get("ro.boot.em.status"));
        boolean isSystemFirstApiLevelMoreThanT =
                Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33;
        boolean isVendorFirstApiLevelMoreThanT =
                Integer.parseInt(SystemProperties.get("ro.vendor.build.version.sdk")) >= 33;
        boolean isExceptionHandlingGrfSModules =
                SystemProperties.get("ro.build.flavor", "").contains("a14m")
                        || SystemProperties.get("ro.build.flavor", "").contains("a14xm")
                        || SystemProperties.get("ro.build.flavor", "").contains("a24")
                        || SystemProperties.get("ro.build.flavor", "").contains("a34x");
        boolean isSupportIDAttestation =
                isSystemFirstApiLevelMoreThanT
                        && (isVendorFirstApiLevelMoreThanT || isExceptionHandlingGrfSModules);
        boolean isFirstBoot = isNotExistCriticalKey();
        Log.i(
                TAG,
                "isFactoryBinary : "
                        + isFactoryBinary
                        + " isProductionDevice : "
                        + isProductionDevice
                        + " isSupportIDAttestation : "
                        + isSupportIDAttestation
                        + " isFirstBoot : "
                        + isFirstBoot);
        return !isFactoryBinary && !isProductionDevice && isSupportIDAttestation && isFirstBoot;
    }

    public int provisionForATCommand(int keyType) {
        return provisionDeviceID(keyType, false);
    }

    public void provisionForFirstBoot() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(
                new DeviceIDBootProvisionWorker(handler, 1000, 300),
                ParcelableCallAnalytics.MILLIS_IN_5_MINUTES);
    }

    private class DeviceIDBootProvisionWorker implements Runnable {
        private final Handler handler;
        private final int maxWaitCount;
        private final int waitTimeMs;
        private boolean isWiFiDevice = false;
        private TelephonyManager telephonyService = null;
        private String[] imei = null;
        private String meid = null;
        private int taskno = 0;
        private int waitCount = 0;
        private IDeviceIDProvisionTask[] tasks = {
            new IDeviceIDProvisionTask() { // from class:
                // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.1
                @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.IDeviceIDProvisionTask
                public boolean run() {
                    DeviceIDBootProvisionWorker.this.isWiFiDevice =
                            SystemProperties.get("ro.carrier")
                                    .toUpperCase(Locale.US)
                                    .contains("WIFI-ONLY");
                    if (!DeviceIDBootProvisionWorker.this.isWiFiDevice) {
                        int simSlotCount =
                                Integer.parseInt(SystemProperties.get("ro.multisim.simslotcount"));
                        if (simSlotCount != 2) {
                            Log.w(
                                    DeviceIDProvisionManager.TAG,
                                    String.format(
                                            "Expected 2 SIM slots but found %d SIM slots.",
                                            Integer.valueOf(simSlotCount)));
                            if (simSlotCount > 2) {
                                Log.w(
                                        DeviceIDProvisionManager.TAG,
                                        String.format(
                                                "Only 2 SIM slots are supported. Extra slots will"
                                                    + " not be taken into account.",
                                                new Object[0]));
                                simSlotCount = 2;
                            }
                        }
                        DeviceIDBootProvisionWorker.this.imei = new String[simSlotCount];
                        return true;
                    }
                    Log.i(
                            DeviceIDProvisionManager.TAG,
                            "Skipping imei and meid fetch because this is wifi only model.");
                    return true;
                }
            },
            new IDeviceIDProvisionTask() { // from class:
                // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.2
                @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.IDeviceIDProvisionTask
                public boolean run() {
                    if (DeviceIDBootProvisionWorker.this.isWiFiDevice) {
                        return true;
                    }
                    DeviceIDBootProvisionWorker.this.telephonyService =
                            (TelephonyManager)
                                    DeviceIDProvisionManager.this.mContext.getSystemService(
                                            TelephonyManager.class);
                    if (DeviceIDBootProvisionWorker.this.telephonyService != null) {
                        return true;
                    }
                    Log.w(
                            DeviceIDProvisionManager.TAG,
                            "Failed to connect to telephony service. Postponing to try again...");
                    return false;
                }
            },
            new IDeviceIDProvisionTask() { // from class:
                // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.3
                @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.IDeviceIDProvisionTask
                public boolean run() {
                    if (DeviceIDBootProvisionWorker.this.isWiFiDevice) {
                        return true;
                    }
                    for (int imeino = 0;
                            imeino < DeviceIDBootProvisionWorker.this.imei.length;
                            imeino++) {
                        if (DeviceIDBootProvisionWorker.this.imei[imeino] == null) {
                            try {
                                DeviceIDBootProvisionWorker.this.imei[imeino] =
                                        DeviceIDBootProvisionWorker.this.telephonyService.getImei(
                                                imeino);
                                if (DeviceIDBootProvisionWorker.this.imei[imeino] == null) {
                                    Log.w(
                                            DeviceIDProvisionManager.TAG,
                                            String.format(
                                                    "Failed to fetch imei %d. Postponing to try"
                                                        + " later.",
                                                    Integer.valueOf(imeino)));
                                    return false;
                                }
                            } catch (UnsupportedOperationException e) {
                                e.printStackTrace();
                                Log.w(
                                        DeviceIDProvisionManager.TAG,
                                        String.format(
                                                "Imei %d not supported. Overriding to empty"
                                                    + " string.",
                                                Integer.valueOf(imeino)));
                                DeviceIDBootProvisionWorker.this.imei[imeino] = "";
                            }
                            Log.i(
                                    DeviceIDProvisionManager.TAG,
                                    String.format(
                                            "Successfully fetched imei %d for device id provision.",
                                            Integer.valueOf(imeino)));
                        }
                    }
                    return true;
                }
            },
            new IDeviceIDProvisionTask() { // from class:
                // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.4
                @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.IDeviceIDProvisionTask
                public boolean run() {
                    if (DeviceIDBootProvisionWorker.this.isWiFiDevice) {
                        return true;
                    }
                    try {
                        DeviceIDBootProvisionWorker.this.meid =
                                DeviceIDBootProvisionWorker.this.telephonyService.getMeid(0);
                        if (DeviceIDBootProvisionWorker.this.meid == null) {
                            Log.w(
                                    DeviceIDProvisionManager.TAG,
                                    "Failed to fetch meid. Overriding to empty string.");
                            DeviceIDBootProvisionWorker.this.meid = "";
                            return true;
                        }
                    } catch (UnsupportedOperationException e) {
                        e.printStackTrace();
                        Log.w(
                                DeviceIDProvisionManager.TAG,
                                "Meid not supported. Overriding to empty string.");
                        DeviceIDBootProvisionWorker.this.meid = "";
                    }
                    Log.i(
                            DeviceIDProvisionManager.TAG,
                            "Successfully fetched meid for device id provision.");
                    return true;
                }
            },
            new IDeviceIDProvisionTask() { // from class:
                // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.5
                @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorker.IDeviceIDProvisionTask
                public boolean run() {
                    int ret;
                    int ret2;
                    if (1 != 0
                            && (ret2 = DeviceIDBootProvisionWorker.this.provisionDeviceID(8))
                                    != 0) {
                        Log.e(
                                DeviceIDProvisionManager.TAG,
                                "provisionDeviceID for SKeymaster failed. ret : " + ret2);
                        return true;
                    }
                    if (1 != 0
                            && (ret = DeviceIDBootProvisionWorker.this.provisionDeviceID(9)) != 0) {
                        Log.e(
                                DeviceIDProvisionManager.TAG,
                                "provisionDeviceID for StrongboxKeymaster failed. ret : " + ret);
                        return true;
                    }
                    int ret3 = DeviceIDProvisionManager.generateCriticalKey();
                    if (ret3 != 0) {
                        Log.e(
                                DeviceIDProvisionManager.TAG,
                                "generateCriticalKey failed, ret : " + ret3);
                        return true;
                    }
                    Log.i(DeviceIDProvisionManager.TAG, "provisionDeviceID success");
                    return true;
                }
            }
        };

        private interface IDeviceIDProvisionTask {
            boolean run();
        }

        public DeviceIDBootProvisionWorker(Handler handler, int waitTimeMs, int maxWaitCount) {
            this.handler = handler;
            this.waitTimeMs = waitTimeMs;
            this.maxWaitCount = maxWaitCount;
        }

        @Override // java.lang.Runnable
        public void run() {
            while (this.taskno < this.tasks.length) {
                IDeviceIDProvisionTask currentTask = this.tasks[this.taskno];
                boolean result = currentTask.run();
                if (result) {
                    this.taskno++;
                } else if (this.waitCount >= this.maxWaitCount) {
                    Log.e(
                            DeviceIDProvisionManager.TAG,
                            "Boot time device ID provision wait count expired.");
                    return;
                } else {
                    this.waitCount++;
                    this.handler.postDelayed(this, this.waitTimeMs);
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int provisionDeviceID(int keyType) {
            String brand = Build.BRAND_FOR_ATTESTATION;
            String device = Build.DEVICE_FOR_ATTESTATION;
            String produt = Build.PRODUCT_FOR_ATTESTATION;
            String manufacturer = Build.MANUFACTURER_FOR_ATTESTATION;
            String model = Build.MODEL_FOR_ATTESTATION;
            String serial = Build.getSerial();
            return DeviceIDProvisionManager.this.installDeviceID(
                    keyType,
                    brand,
                    device,
                    produt,
                    serial,
                    (this.imei == null || this.imei.length < 1) ? null : this.imei[0],
                    (this.imei == null || this.imei.length < 2) ? null : this.imei[1],
                    this.meid,
                    manufacturer,
                    model);
        }
    }
}
