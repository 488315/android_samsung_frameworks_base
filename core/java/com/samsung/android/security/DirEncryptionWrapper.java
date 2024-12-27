package com.samsung.android.security;

import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.storage.IStorageManager;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.samsung.android.media.AudioParameter;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class DirEncryptionWrapper {
    private static final String TAG = "DirEncryptWrapper";
    private Context mContext;
    private static final boolean LOCAL_LOGE = Build.TYPE.contains("eng");
    private static final boolean LOCAL_LOGD = Build.TYPE.contains("eng");
    private static boolean mUserDiff = false;
    private static int mSavedUserId = 0;
    private static String mExternalSDvolId = null;
    private static String mExternalSDvolFsUuid = null;
    private static String mExternalSDvolState = null;
    private static long mExternalSDvolUsedSize = 0;
    private StorageManager mStorageManager = null;
    private IStorageManager mMountService = null;

    private static void logD(String msg) {
        if (LOCAL_LOGD) {
            Log.d(TAG, msg);
        }
    }

    private static void logE(String msg) {
        if (LOCAL_LOGE) {
            Log.e(TAG, msg);
        }
    }

    private VolumeInfo getVolumeInfo() {
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(Context.STORAGE_SERVICE);
        List<VolumeInfo> volumes = storageManager.getVolumes();
        Collections.sort(volumes, VolumeInfo.getDescriptionComparator());
        for (VolumeInfo vol : volumes) {
            if (vol != null && vol.getType() == 0 && vol.getDisk() != null && vol.getDisk().isSd()) {
                return vol;
            }
        }
        return null;
    }

    public DirEncryptionWrapper(Context context) {
        this.mContext = null;
        this.mContext = context;
    }

    public IStorageManager getMountService() {
        if (this.mMountService == null) {
            IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
            if (service != null) {
                this.mMountService = IStorageManager.Stub.asInterface(service);
            } else {
                logE("Can't get mount service");
            }
        }
        return this.mMountService;
    }

    public boolean mountVolume() {
        try {
            getMountService().mountVolume(getExternalSdPath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unmountVolume() {
        try {
            getMountService().unmount(getExternalSDvolId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unmountHiddenVolume() {
        try {
            getMountService().unmount(getExternalSDvolId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getVolumeState() {
        if (getVolumeInfo() != null) {
            getVolumeInfo();
            return VolumeInfo.getEnvironmentForState(getVolumeInfo().getState());
        }
        return null;
    }

    public String getId() {
        if (getVolumeInfo() != null) {
            return getVolumeInfo().getId();
        }
        return null;
    }

    public int getActivePasswordQuality() {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
        return lockPatternUtils.getActivePasswordQuality(getCurrentUserID());
    }

    public int getKeyguardStoredPasswordQuality() {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
        return lockPatternUtils.getKeyguardStoredPasswordQuality(getCurrentUserID());
    }

    public boolean isSecure() {
        LockPatternUtils lockPatternUtils = new LockPatternUtils(this.mContext);
        return lockPatternUtils.isSecure(getCurrentUserID());
    }

    public boolean isExternalSDRemovable() {
        if (getExternalSdPath() != null) {
            return true;
        }
        return false;
    }

    public String getExternalSdPath() {
        if (getVolumeInfo() != null && getVolumeInfo().getPath() != null) {
            return getVolumeInfo().getPath().getPath();
        }
        return null;
    }

    public boolean registerStorageEventListener(StorageEventListener listner) {
        if (this.mStorageManager != null) {
            return false;
        }
        this.mStorageManager = (StorageManager) this.mContext.getSystemService(Context.STORAGE_SERVICE);
        if (this.mStorageManager == null) {
            return false;
        }
        this.mStorageManager.registerListener(listner);
        return true;
    }

    public int getCurrentUserID() {
        return UserHandle.myUserId();
    }

    public void setUserDiff(boolean in) {
        mUserDiff = in;
    }

    public boolean getUserDiff() {
        return mUserDiff;
    }

    public void setSavedUserID(int in) {
        mSavedUserId = in;
    }

    public int getSavedUserID() {
        return mSavedUserId;
    }

    public void setExternalSDvolId(String volId) {
        mExternalSDvolId = volId;
    }

    public String getExternalSDvolId() {
        return mExternalSDvolId;
    }

    public void setExternalSDvolFsUuid(String volFsUuid) {
        mExternalSDvolFsUuid = volFsUuid;
    }

    public void setExternalSDvolUsedSize(long size) {
        mExternalSDvolUsedSize = size;
    }

    public String getExternalSDvolFsUuid() {
        return mExternalSDvolFsUuid;
    }

    public void setExternalSDvolState(String volState) {
        mExternalSDvolState = volState;
    }

    public String getExternalSDvolState() {
        return mExternalSDvolState;
    }

    public long getExternalSDvolUsedSize() {
        logD("getExternalSDvolUsedSize" + mExternalSDvolUsedSize);
        return mExternalSDvolUsedSize;
    }
}
