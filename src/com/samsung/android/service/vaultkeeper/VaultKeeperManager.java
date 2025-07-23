package com.samsung.android.service.vaultkeeper;

import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.service.vaultkeeper.IVaultKeeperService;

/* loaded from: classes6.dex */
public final class VaultKeeperManager {
    public static final int ERR_DEVICE_NOT_READY = -109;
    public static final int ERR_EXCEPTION = -103;
    public static final int ERR_FAILURE_ACQUIRE_LOCK = -105;
    public static final int ERR_GENERAL_FAILED = -101;
    public static final int ERR_INVALID_ARGUMENT = -102;
    public static final int ERR_PERMISSION_DENIED = -106;
    public static final int ERR_SERVICE_NOT_SUPPORT = -104;
    public static final int ERR_TA_SERVICE_ERROR = -110;
    public static final int ERR_VAULT_NOT_AVAILABLE = -107;
    public static final int ERR_VERIFICATION_FAILURE = -108;
    public static final int MAX_LEN_VAULT_NAME = 32;
    public static final int SUCCESS = 0;
    private static final String TAG = "VaultKeeperManager";
    public static final int TYPE_COMMERCIAL_DEVICE = 6;
    public static final int TYPE_EMT = 3;
    public static final int TYPE_NONCE = 1;
    public static final int TYPE_VAULT_DATA_MAX = 2;
    public static final int TYPE_VAULT_DATA_MIN = 1;
    public static final int TYPE_VAULT_DATA_SHELTERED = 2;
    public static final int TYPE_VAULT_DATA_UNSHELTERED = 1;
    public static final int TYPE_VK_API_LEVEL = 9;
    public static final int TYPE_WB = 2;
    private int mErrorCode;
    private IVaultKeeperService mService;
    private String mVaultName;

    private VaultKeeperManager() {
    }

    private VaultKeeperManager(String str) {
        this.mErrorCode = -101;
        this.mVaultName = str;
        this.mService = IVaultKeeperService.Stub.asInterface(ServiceManager.getService("VaultKeeperService"));
    }

    public static VaultKeeperManager getInstance(String str) {
        if (str == null) {
            Log.e(TAG, "vaultName is null");
            return null;
        }
        if (str.length() == 0 || str.length() > 32) {
            Log.e(TAG, "vaultName length is wrong(" + str.length() + "). It should be less than (32)");
            return null;
        }
        return new VaultKeeperManager(str);
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public boolean isInitialized() {
        try {
            return this.mService.isInitialized(this.mVaultName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int initialize(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return this.mService.initialize(this.mVaultName, bArr, null, bArr2, bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int initialize(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        try {
            return this.mService.initialize(this.mVaultName, bArr, bArr2, bArr3, bArr4);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int destroy() {
        try {
            return this.mService.destroy(this.mVaultName, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int destroy(byte[] bArr) {
        try {
            return this.mService.destroy(this.mVaultName, bArr, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int destroy(byte[] bArr, byte[] bArr2) {
        try {
            return this.mService.destroy(this.mVaultName, null, bArr, bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public byte[] read(int i) {
        int[] iArr = new int[1];
        this.mErrorCode = -102;
        try {
            try {
                return this.mService.read(this.mVaultName, i, iArr);
            } catch (Exception e) {
                e.printStackTrace();
                this.mErrorCode = iArr[0];
                return null;
            }
        } finally {
            this.mErrorCode = iArr[0];
        }
    }

    public int write(int i, byte[] bArr) {
        try {
            return this.mService.write(this.mVaultName, i, bArr, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int write(int i, byte[] bArr, byte[] bArr2) {
        try {
            return this.mService.write(this.mVaultName, i, bArr, null, bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int write(int i, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return this.mService.write(this.mVaultName, i, bArr, bArr2, bArr3);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public byte[] sensitiveBox(int i) {
        int[] iArr = new int[1];
        this.mErrorCode = -102;
        try {
            try {
                return this.mService.sensitiveBox(this.mVaultName, i, iArr);
            } catch (Exception e) {
                e.printStackTrace();
                this.mErrorCode = iArr[0];
                return null;
            }
        } finally {
            this.mErrorCode = iArr[0];
        }
    }

    public byte[] encryptMessage(byte[] bArr) {
        try {
            return this.mService.encryptMessage(this.mVaultName, bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean migrationStorage() {
        try {
            return this.mService.migrationStorage(this.mVaultName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyCertificate(byte[] bArr) {
        try {
            return this.mService.verifyCertificate(this.mVaultName, bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int checkDataWritable() {
        try {
            return this.mService.checkDataWritable(this.mVaultName);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }

    public int generateHotpCode() {
        try {
            return this.mService.generateHotpCode(this.mVaultName);
        } catch (Exception e) {
            e.printStackTrace();
            return -103;
        }
    }
}
