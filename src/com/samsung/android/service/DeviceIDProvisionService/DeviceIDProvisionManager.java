package com.samsung.android.service.DeviceIDProvisionService;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore2.AndroidKeyStoreSpi;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.SecureKeyConst;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.concurrent.TimeoutException;
import javax.crypto.KeyGenerator;

/* loaded from: classes6.dex */
public class DeviceIDProvisionManager {
    private static final String TAG = "DeviceIDProvisionManager";
    private Context mContext;

    /* JADX INFO: Access modifiers changed from: private */
    public native int installDeviceID(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    static {
        System.loadLibrary("_nativeJni.dk.samsung");
    }

    public DeviceIDProvisionManager(Context context) {
        this.mContext = context;
    }

    private static final class BootProvisionStatusManager {
        private static final String ALIAS_FOR_FIRST_BOOT = "alias_for_first_boot";
        private boolean mProvisioned;

        private static class BPSMHolder {
            static final BootProvisionStatusManager INSTANCE = new BootProvisionStatusManager();

            private BPSMHolder() {
            }
        }

        public static BootProvisionStatusManager getInstance() {
            return BPSMHolder.INSTANCE;
        }

        private BootProvisionStatusManager() {
            this.mProvisioned = !isNotExistCriticalKey();
        }

        public synchronized boolean isProvisioned() {
            return this.mProvisioned;
        }

        public synchronized int setProvisionedUntilReboot() {
            int iDeleteCriticalKey = deleteCriticalKey();
            if (iDeleteCriticalKey != 0) {
                Log.i(DeviceIDProvisionManager.TAG, String.format("Phony set boot provisioned status requested, but failed to clear status in storage. [ret = %d]", Integer.valueOf(iDeleteCriticalKey)));
                return iDeleteCriticalKey;
            }
            this.mProvisioned = true;
            return 0;
        }

        public synchronized int setProvisioned() {
            if (this.mProvisioned) {
                Log.i(DeviceIDProvisionManager.TAG, "Set boot provisioned status requested, but is already set.");
                return 0;
            }
            int iGenerateCriticalKey = generateCriticalKey();
            if (iGenerateCriticalKey != 0) {
                Log.e(DeviceIDProvisionManager.TAG, String.format("Failed to set boot provisioned status. [ret = %d]", Integer.valueOf(iGenerateCriticalKey)));
                return iGenerateCriticalKey;
            }
            this.mProvisioned = true;
            return 0;
        }

        private static boolean isNotExistCriticalKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
            try {
                KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
                keyStore.load(null);
                return keyStore.getKey(ALIAS_FOR_FIRST_BOOT, null) == null;
            } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e) {
                e.printStackTrace();
                return false;
            }
        }

        private static int generateCriticalKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
            try {
                KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
                keyStore.load(null);
                if (keyStore.getKey(ALIAS_FOR_FIRST_BOOT, null) != null) {
                    return 0;
                }
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", AndroidKeyStoreSpi.NAME);
                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(ALIAS_FOR_FIRST_BOOT, 3);
                builder.setDigests("SHA-256");
                builder.setCriticalToDeviceEncryption(true);
                keyGenerator.init(builder.build());
                keyGenerator.generateKey();
                return 0;
            } catch (IOException | InvalidAlgorithmParameterException | KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | UnrecoverableKeyException | CertificateException e) {
                e.printStackTrace();
                return -1;
            }
        }

        private static int deleteCriticalKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
            try {
                KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
                keyStore.load(null);
                if (keyStore.getKey(ALIAS_FOR_FIRST_BOOT, null) == null) {
                    return 0;
                }
                keyStore.deleteEntry(ALIAS_FOR_FIRST_BOOT);
                return 0;
            } catch (IOException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException e) {
                e.printStackTrace();
                return -1;
            }
        }
    }

    public static boolean isAvailable() {
        boolean zEquals = "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
        boolean zEquals2 = "0x1".equals(SystemProperties.get("ro.boot.em.status"));
        boolean z = !zEquals2;
        boolean z2 = (Integer.parseInt(SystemProperties.get("ro.product.first_api_level")) >= 33) && ((Integer.parseInt(SystemProperties.get("ro.board.first_api_level")) >= 33) || (SystemProperties.get("ro.build.flavor", "").contains("a14m") || SystemProperties.get("ro.build.flavor", "").contains("a14xm") || SystemProperties.get("ro.build.flavor", "").contains("a24") || SystemProperties.get("ro.build.flavor", "").contains("a34x")));
        Log.i(TAG, String.format("Device ID provision attributes: [factory binary = %b, production device = %b, device ID attestation support = %b]", Boolean.valueOf(zEquals), Boolean.valueOf(z), Boolean.valueOf(z2)));
        if (zEquals || !zEquals2 || !z2) {
            return false;
        }
        boolean z3 = !BootProvisionStatusManager.getInstance().isProvisioned();
        Log.i(TAG, String.format("Device ID provision criteria: [first boot = %b]", Boolean.valueOf(z3)));
        return z3;
    }

    public int provisionForATCommand(int i) throws InterruptedException {
        DeviceIDProvisionSynchronousWorker deviceIDProvisionSynchronousWorker = new DeviceIDProvisionSynchronousWorker();
        try {
            deviceIDProvisionSynchronousWorker.setupSynchronous(1000, 10);
            int iProvisionDeviceID = deviceIDProvisionSynchronousWorker.provisionDeviceID(i);
            if (iProvisionDeviceID != 0) {
                return iProvisionDeviceID;
            }
            Log.i(TAG, "Successfully provisioned device ID requested externally.");
            return 0;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return SecureKeyConst.ERR_KEYMASTER_TIMEOUT;
        }
    }

    public void provisionForFirstBoot() {
        new DeviceIDBootProvisionWorker(300000, 1000, 300).start();
    }

    private class DeviceIDBootProvisionWorker extends Thread {
        private final int initialWaitTimeMs;
        private final int maxWaitCount;
        private final int retryWaitTimeMs;
        private final DeviceIDProvisionSynchronousWorker worker;

        public DeviceIDBootProvisionWorker(int i, int i2, int i3) {
            this.worker = new DeviceIDProvisionSynchronousWorker();
            if (i <= 0) {
                throw new IllegalArgumentException(String.format("Illegal wait time provided. Expected positive number. [wait time = %d]", Integer.valueOf(i)));
            }
            if (i2 <= 0) {
                throw new IllegalArgumentException(String.format("Illegal wait time provided. Expected positive number. [wait time = %d]", Integer.valueOf(i2)));
            }
            if (i3 < 0) {
                throw new IllegalArgumentException(String.format("Illegal max wait count provided. Expected nonnegative number. [max wait count = %d]", Integer.valueOf(i3)));
            }
            this.initialWaitTimeMs = i;
            this.retryWaitTimeMs = i2;
            this.maxWaitCount = i3;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            int iProvisionDeviceID;
            int iProvisionDeviceID2;
            try {
                Thread.sleep(this.initialWaitTimeMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.w(DeviceIDProvisionManager.TAG, "Initial wait was interrupted while waiting for Device ID boot provision. Ignoring.");
            }
            if (BootProvisionStatusManager.getInstance().isProvisioned()) {
                Log.i(DeviceIDProvisionManager.TAG, "Boot provision was disabled. It will be tried after another reboot.");
                return;
            }
            try {
                this.worker.setupSynchronous(this.retryWaitTimeMs, this.maxWaitCount);
                if (DeviceIDProvisionWorker.isSupportGAKDeviceID() && (iProvisionDeviceID2 = this.worker.provisionDeviceID(8)) != 0) {
                    Log.e(DeviceIDProvisionManager.TAG, "provisionDeviceID for SKeymint failed. ret : " + iProvisionDeviceID2);
                } else if (DeviceIDProvisionWorker.isSupportStrongboxDeviceID() && (iProvisionDeviceID = this.worker.provisionDeviceID(9)) != 0) {
                    Log.e(DeviceIDProvisionManager.TAG, "provisionDeviceID for StrongboxKeymint failed. ret : " + iProvisionDeviceID);
                } else {
                    int provisioned = BootProvisionStatusManager.getInstance().setProvisioned();
                    if (provisioned != 0) {
                        Log.w(DeviceIDProvisionManager.TAG, String.format("Failed to set boot provisioned status. [ret = %d]", Integer.valueOf(provisioned)));
                    }
                    Log.i(DeviceIDProvisionManager.TAG, "provisionDeviceID success");
                }
            } catch (TimeoutException e2) {
                e2.printStackTrace();
            }
        }
    }

    private class DeviceIDProvisionSynchronousWorker extends DeviceIDProvisionWorker {
        private DeviceIDProvisionSynchronousWorker(DeviceIDProvisionManager deviceIDProvisionManager) {
            super(deviceIDProvisionManager);
        }

        public void setupSynchronous(int i, int i2) throws InterruptedException, TimeoutException {
            if (i <= 0) {
                throw new IllegalArgumentException(String.format("Illegal wait time provided. Expected positive number. [wait time = %d]", Integer.valueOf(i)));
            }
            if (i2 < 0) {
                throw new IllegalArgumentException(String.format("Illegal max wait count provided. Expected nonnegative number. [max wait count = %d]", Integer.valueOf(i2)));
            }
            int i3 = 0;
            while (!setupProvisionContext()) {
                Log.w(DeviceIDProvisionManager.TAG, String.format("Device ID provision setup trial %d failed. Waiting before retry.", Integer.valueOf(i3)));
                if (i3 >= i2) {
                    Log.e(DeviceIDProvisionManager.TAG, "Device ID provision wait count expired.");
                    throw new TimeoutException(String.format("Failed to setup Device ID provision context due to timeout. [wait time = %d(ms), max wait count = %d]", Integer.valueOf(i), Integer.valueOf(i2)));
                }
                i3++;
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class DeviceIDProvisionWorker extends DeviceIDBootProvisionWorkerBase {
        private boolean setupCompleted;

        public static boolean isSupportGAKDeviceID() {
            return true;
        }

        public static boolean isSupportStrongboxDeviceID() {
            return true;
        }

        public DeviceIDProvisionWorker(DeviceIDProvisionManager deviceIDProvisionManager) {
            super();
            this.setupCompleted = false;
        }

        @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorkerBase
        public boolean setupProvisionContext() {
            if (this.setupCompleted) {
                return true;
            }
            if (super.setupProvisionContext()) {
                this.setupCompleted = true;
            }
            return this.setupCompleted;
        }

        @Override // com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager.DeviceIDBootProvisionWorkerBase
        public int provisionDeviceID(int i) {
            if (!this.setupCompleted) {
                throw new IllegalStateException("Device ID provision requested but setup is not completed.");
            }
            return super.provisionDeviceID(i);
        }
    }

    private class DeviceIDBootProvisionWorkerBase {
        private String mImei0;
        private String mImei1;
        private String mMeid;
        private String mSerial;

        private DeviceIDBootProvisionWorkerBase() {
            this.mImei0 = null;
            this.mImei1 = null;
            this.mMeid = null;
            this.mSerial = null;
        }

        protected boolean setupProvisionContext() throws NumberFormatException {
            if (SystemProperties.get("ro.carrier").toUpperCase(Locale.US).contains("WIFI-ONLY")) {
                Log.i(DeviceIDProvisionManager.TAG, "Skipping imei and meid fetch because this is wifi only model.");
            } else {
                TelephonyManager telephonyManagerTryGetTelephonyManager = tryGetTelephonyManager();
                if (telephonyManagerTryGetTelephonyManager == null) {
                    Log.w(DeviceIDProvisionManager.TAG, "Failed to connect to telephony service. Postponing to try again...");
                    return false;
                }
                int i = Integer.parseInt(SystemProperties.get("ro.multisim.simslotcount"));
                if (i > 2) {
                    Log.w(DeviceIDProvisionManager.TAG, String.format("Only 2 SIM slots are supported, but SIM slot count was bigger than 2. Extra slots will not be taken into account. [slot count = %d]", Integer.valueOf(i)));
                    i = 2;
                } else if (i <= 0) {
                    Log.w(DeviceIDProvisionManager.TAG, String.format("This is not wifi only model, but SIM slot count is strange. [slot count = %d]", Integer.valueOf(i)));
                    return false;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    String strTryGetImei = tryGetImei(telephonyManagerTryGetTelephonyManager, i2);
                    if (strTryGetImei == null) {
                        Log.w(DeviceIDProvisionManager.TAG, String.format("Failed to fetch imei %d. Postponing to try later.", Integer.valueOf(i2)));
                        return false;
                    }
                    if (i2 == 0) {
                        this.mImei0 = strTryGetImei;
                    } else {
                        if (i2 != 1) {
                            Log.w(DeviceIDProvisionManager.TAG, String.format("Unknown SIM slot number %d found. Please contact the administrator.", Integer.valueOf(i2)));
                            return false;
                        }
                        this.mImei1 = strTryGetImei;
                    }
                }
                String strTryGetMeid = tryGetMeid(telephonyManagerTryGetTelephonyManager, 0);
                this.mMeid = strTryGetMeid;
                if (strTryGetMeid == null) {
                    Log.w(DeviceIDProvisionManager.TAG, "Failed to fetch meid. Skipping.");
                    this.mMeid = "";
                }
            }
            String serial = Build.getSerial();
            this.mSerial = serial;
            if (serial != null) {
                return true;
            }
            Log.w(DeviceIDProvisionManager.TAG, "Failed to retrieve device serial. Postponing to try later.");
            return false;
        }

        protected TelephonyManager tryGetTelephonyManager() {
            TelephonyManager telephonyManager = (TelephonyManager) DeviceIDProvisionManager.this.mContext.getSystemService(TelephonyManager.class);
            if (telephonyManager != null) {
                return telephonyManager;
            }
            Log.w(DeviceIDProvisionManager.TAG, "Failed to connect to telephony service. Postponing to try again...");
            return null;
        }

        protected String tryGetImei(TelephonyManager telephonyManager, int i) {
            if (telephonyManager == null) {
                throw new IllegalStateException("Imei fetch requested but telephony service is not connected.");
            }
            try {
                return telephonyManager.getImei(i);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
                Log.w(DeviceIDProvisionManager.TAG, String.format("Imei %d not supported. Overriding to empty string.", Integer.valueOf(i)));
                return "";
            }
        }

        protected String tryGetMeid(TelephonyManager telephonyManager, int i) {
            if (telephonyManager == null) {
                throw new IllegalStateException("Meid fetch requested but telephony service is not connected.");
            }
            try {
                return telephonyManager.getMeid(0);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
                Log.w(DeviceIDProvisionManager.TAG, "Meid not supported. Overriding to empty string.");
                return "";
            }
        }

        protected int provisionDeviceID(int i) {
            String str = Build.BRAND_FOR_ATTESTATION;
            String str2 = Build.DEVICE_FOR_ATTESTATION;
            String str3 = Build.PRODUCT_FOR_ATTESTATION;
            String str4 = Build.MANUFACTURER_FOR_ATTESTATION;
            String str5 = Build.MODEL_FOR_ATTESTATION;
            Log.i(DeviceIDProvisionManager.TAG, String.format("Proceeding device ID provision. [key type = %d, serial = %s (%d), imei0 = %s (%d), imei1 = %s (%d), meid = %s (%d)]", Integer.valueOf(i), C1OutputConverter.reprNonZero(this.mSerial), Integer.valueOf(C1OutputConverter.getLengthIfNotNull(this.mSerial)), C1OutputConverter.reprNonZero(this.mImei0), Integer.valueOf(C1OutputConverter.getLengthIfNotNull(this.mImei0)), C1OutputConverter.reprNonZero(this.mImei1), Integer.valueOf(C1OutputConverter.getLengthIfNotNull(this.mImei1)), C1OutputConverter.reprNonZero(this.mMeid), Integer.valueOf(C1OutputConverter.getLengthIfNotNull(this.mMeid))));
            return DeviceIDProvisionManager.this.installDeviceID(i, str, str2, str3, this.mSerial, this.mImei0, this.mImei1, this.mMeid, str4, str5);
        }

        /* renamed from: com.samsung.android.service.DeviceIDProvisionService.DeviceIDProvisionManager$DeviceIDBootProvisionWorkerBase$1OutputConverter, reason: invalid class name */
        class C1OutputConverter {
            C1OutputConverter(DeviceIDBootProvisionWorkerBase deviceIDBootProvisionWorkerBase) {
            }

            public static boolean checkNonZero(String str) {
                if (str == null) {
                    return false;
                }
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) != '0') {
                        return true;
                    }
                }
                return false;
            }

            public static String reprNonZero(String str) {
                if (str == null) {
                    return "(unknown)";
                }
                return checkNonZero(str) ? "(non-zero)" : "(zero)";
            }

            public static int getLengthIfNotNull(String str) {
                if (str == null) {
                    return 0;
                }
                return str.length();
            }
        }
    }
}
