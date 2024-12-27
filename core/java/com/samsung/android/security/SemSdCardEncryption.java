package com.samsung.android.security;

import android.content.Context;
import android.os.IBinder;
import android.os.IVoldTaskListener;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IStorageManager;
import android.util.Log;

import com.samsung.android.media.AudioParameter;

import java.io.File;

public class SemSdCardEncryption {
    public static final String ADMIN_START = "adminStart";
    public static final String CHECK_OTHER_DEVICE = "OtherDevice";
    private static final boolean DEBUG = true;
    public static final int DECRYPT = 3;
    public static final int ENCRYPT = 2;
    public static final int ENCRYPT_FULL_OFF = 5;
    public static final int ENCRYPT_FULL_ON = 4;
    public static final int ENC_META_CHECK = 692;
    public static final int ERROR_FEATURE_UNAVAILABLE = 200;
    public static final int ERR_INVALID_PARAMETER = 203;
    public static final int ERR_INVALID_PERMISSION = 204;
    public static final int ERR_NOK = 201;
    public static final int ERR_SD_NOT_MOUNTED = 202;
    public static final int EXCL_MEDIA_OFF = 7;
    public static final int EXCL_MEDIA_ON = 6;
    public static final String INTERNAL_STORAGE_PATH = "/mnt/sdcard";
    public static final String MOVE_MOUNT = "MoveMount";
    private static final int MSG_BASE = 0;
    private static final int MSG_ERR_BASE = 200;
    public static final String NAME = "DirEncryptService";
    public static final int OK = 13;
    public static final int POLICY_ALREADY_SET = 10;
    public static final int POLICY_CAN_NOT_BE_SET_UNDER_BUSY_STATE = 15;
    public static final int POLICY_NOT_SAVED = 9;
    public static final int POLICY_SAVED = 8;
    public static final String SD_CARD_ENCRYPTION_ACTION =
            "com.sec.app.action.START_SDCARD_ENCRYPTION";
    public static final String STATUS_BUSY = "busy";
    public static final String STATUS_DONE = "done";
    public static final String STATUS_FREE = "free";
    public static final String STATUS_MOUNT = "Mount";
    private static final String TAG = "SemSdCardEncryption";
    public static final String VOLUME_STATE_HIDDEN = "HiddenMount";
    private Context mContext;
    private DirEncryptionWrapper mDew;
    private IDirEncryptService m_InstDirEncSvc;
    private static final boolean IS_SUPPORT_SDCARD_SLOT =
            new File("/sys/class/sec/sdinfo").exists();
    public static int SECURITY_POLICY_NOTIFICATION_ID = -889275714;
    private static boolean mPolicyChanged = false;

    public static final class Status {
        public static final int DECRYPTING = 3;
        public static final int ENCRYPTING = 2;
        public static final int FREE = 0;
        public static final int READY = 1;

        private Status() {}
    }

    public static final class EncryptionState {
        public static final int DECRYPTED = 3;
        public static final int DECRYPTING = 1;
        public static final int ENCRYPTED = 2;
        public static final int ENCRYPTING = 0;
        public static final int SET_ADMIN = -1;

        private EncryptionState() {}
    }

    public static final class Error {
        public static final int DECRYPT = 6;
        public static final int ENCRYPT = 5;
        public static final int FILE_OPEN = 11;
        public static final int MOUNT = 7;
        public static final int NO = 0;
        public static final int OTHER_ENCRYPT = 8;
        public static final int PRESCAN_FULL = 4;
        public static final int PWD_CREATE = 1;
        public static final int PWD_DELETE = 3;
        public static final int PWD_UPDATE = 2;
        public static final int UNMOUNT = 8;

        private Error() {}
    }

    public SemSdCardEncryption(Context context) {
        this.mContext = null;
        this.mDew = null;
        this.m_InstDirEncSvc = null;
        this.mContext = context;
        this.mDew = new DirEncryptionWrapper(this.mContext);
        this.m_InstDirEncSvc = IDirEncryptService.Stub.asInterface(ServiceManager.getService(NAME));
        if (this.m_InstDirEncSvc == null) {
            Log.e(TAG, "Unable to get DirEncryptService instance.");
        }
        if (!IS_SUPPORT_SDCARD_SLOT) {
            Log.e(TAG, "Dir Encryption not available");
            this.m_InstDirEncSvc = null;
        }
    }

    public void registerListener(IDirEncryptServiceListener listener) {
        if (this.m_InstDirEncSvc == null) {
            return;
        }
        try {
            this.m_InstDirEncSvc.registerListener(listener);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
    }

    public void unregisterListener(IDirEncryptServiceListener listener) {
        if (this.m_InstDirEncSvc == null) {
            return;
        }
        try {
            this.m_InstDirEncSvc.unregisterListener(listener);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
    }

    public static boolean isEncryptionFeatureEnabled() {
        if (IS_SUPPORT_SDCARD_SLOT) {
            return true;
        }
        return false;
    }

    public boolean isEncryptionSupported() {
        if (IS_SUPPORT_SDCARD_SLOT && getCurrentUserID() == 0) {
            return true;
        }
        return false;
    }

    public boolean isExternalSDRemovable() {
        return this.mDew.isExternalSDRemovable();
    }

    public String getExternalSdPath() {
        return this.mDew.getExternalSdPath();
    }

    public String getVolumeState() {
        return this.mDew.getVolumeState();
    }

    public int getKeyguardStoredPasswordQuality() {
        return this.mDew.getKeyguardStoredPasswordQuality();
    }

    public boolean mountVolume() {
        return this.mDew.mountVolume();
    }

    public void setUserDiff(boolean in) {
        this.mDew.setUserDiff(in);
    }

    public boolean getUserDiff() {
        return this.mDew.getUserDiff();
    }

    public void setSavedUserID(int in) {
        this.mDew.setSavedUserID(in);
    }

    public int getSavedUserID() {
        return this.mDew.getSavedUserID();
    }

    public int getCurrentUserID() {
        return this.mDew.getCurrentUserID();
    }

    public String getExternalSDvolFsUuid() {
        return this.mDew.getExternalSDvolFsUuid();
    }

    public void setNeedToCreateKey(boolean in) {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return;
        }
        try {
            this.m_InstDirEncSvc.setNeedToCreateKey(in);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
    }

    public void setMountSDcardToHelper(boolean in) {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return;
        }
        try {
            this.m_InstDirEncSvc.setMountSDcardToHelper(in);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
    }

    public IVoldTaskListener getListener() {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return null;
        }
        try {
            return this.m_InstDirEncSvc.getListener();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return null;
        }
    }

    public int setStorageCardEncryptionPolicy(int encType) {
        int result = 200;
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return 200;
        }
        try {
            result = this.m_InstDirEncSvc.setStorageCardEncryptionPolicy(encType, 4, 7);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
        Log.i(TAG, "setStorageCardEncryptionPolicy result : " + result);
        if (result == 8 || result == 10) {
            Log.i(TAG, "result : POLICY_SAVED || POLICY_ALREADY_SET");
            unmountSDCardByAdmin();
        }
        setPolicyChanged(true);
        return result;
    }

    public int setAdminPolicy(boolean enable, String uuid) {
        return setSdCardEncryptionPolicy(!enable ? 0 : 1, -1, uuid);
    }

    public int setSdCardEncryptionPolicy(int isPolicy, int status, String uuid) {
        int result = 200;
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return 200;
        }
        try {
            result = this.m_InstDirEncSvc.setSdCardEncryptionPolicy(isPolicy, status, uuid);
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
        Log.i(TAG, "setSdCardEncryptionPolicy result : " + result);
        if (result == 8 || result == 10) {
            Log.i(TAG, "result : POLICY_SAVED || POLICY_ALREADY_SET");
            unmountSDCardByAdmin();
        }
        setPolicyChanged(true);
        return result;
    }

    public boolean isStorageCardEncryptionPoliciesApplied() {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return false;
        }
        try {
            boolean result = this.m_InstDirEncSvc.isStorageCardEncryptionPoliciesApplied() == 1;
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return false;
        }
    }

    public int setPassword(String password) {
        isEncryptionSupported();
        return 200;
    }

    public int encryptStorage(boolean state) {
        try {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service == null) {
                return 200;
            }
            int result = IStorageManager.Stub.asInterface(service).encryptExternalStorage(state);
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return 200;
        }
    }

    public int getCurrentStatus() {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return 200;
        }
        try {
            int result = this.m_InstDirEncSvc.getCurrentStatus();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return 200;
        }
    }

    public int getLastError() {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return 200;
        }
        try {
            int result = this.m_InstDirEncSvc.getLastError();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return 200;
        }
    }

    public int getAdditionalSpaceRequired() {
        if (!isEncryptionSupported() || this.m_InstDirEncSvc == null) {
            return 200;
        }
        try {
            int result = this.m_InstDirEncSvc.getAdditionalSpaceRequired();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return 200;
        }
    }

    public boolean isSdCardEncryped() {
        if (this.m_InstDirEncSvc == null) {
            return false;
        }
        try {
            boolean result = this.m_InstDirEncSvc.isSdCardEncryped();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
            return false;
        }
    }

    public void unmountSDCardByAdmin() {
        if (this.m_InstDirEncSvc == null) {
            return;
        }
        try {
            this.m_InstDirEncSvc.unmountSDCardByAdmin();
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to communicate with DirEncryptService");
        }
    }

    public void setPolicyChanged(boolean in) {
        mPolicyChanged = in;
    }

    public boolean getPolicyChanged() {
        return mPolicyChanged;
    }
}
