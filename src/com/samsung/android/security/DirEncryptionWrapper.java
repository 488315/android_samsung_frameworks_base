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

    private static void logD(String str) {
        if (LOCAL_LOGD) {
            Log.d(TAG, str);
        }
    }

    private static void logE(String str) {
        if (LOCAL_LOGE) {
            Log.e(TAG, str);
        }
    }

    private VolumeInfo getVolumeInfo() {
        List<VolumeInfo> volumes = ((StorageManager) this.mContext.getSystemService(Context.STORAGE_SERVICE)).getVolumes();
        Collections.sort(volumes, VolumeInfo.getDescriptionComparator());
        for (VolumeInfo volumeInfo : volumes) {
            if (volumeInfo != null && volumeInfo.getType() == 0 && volumeInfo.getDisk() != null && volumeInfo.getDisk().isSd()) {
                return volumeInfo;
            }
        }
        return null;
    }

    public DirEncryptionWrapper(Context context) {
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
        if (getVolumeInfo() == null) {
            return null;
        }
        getVolumeInfo();
        return VolumeInfo.getEnvironmentForState(getVolumeInfo().getState());
    }

    public String getId() {
        if (getVolumeInfo() != null) {
            return getVolumeInfo().getId();
        }
        return null;
    }

    public int getActivePasswordQuality() {
        return new LockPatternUtils(this.mContext).getActivePasswordQuality(getCurrentUserID());
    }

    public int getKeyguardStoredPasswordQuality() {
        return new LockPatternUtils(this.mContext).getKeyguardStoredPasswordQuality(getCurrentUserID());
    }

    public boolean isSecure() {
        return new LockPatternUtils(this.mContext).isSecure(getCurrentUserID());
    }

    public boolean isExternalSDRemovable() {
        return getExternalSdPath() != null;
    }

    public String getExternalSdPath() {
        if (getVolumeInfo() == null || getVolumeInfo().getPath() == null) {
            return null;
        }
        return getVolumeInfo().getPath().getPath();
    }

    public boolean registerStorageEventListener(StorageEventListener storageEventListener) {
        if (this.mStorageManager != null) {
            return false;
        }
        StorageManager storageManager = (StorageManager) this.mContext.getSystemService(Context.STORAGE_SERVICE);
        this.mStorageManager = storageManager;
        if (storageManager == null) {
            return false;
        }
        storageManager.registerListener(storageEventListener);
        return true;
    }

    public int getCurrentUserID() {
        return UserHandle.myUserId();
    }

    public void setUserDiff(boolean z) {
        mUserDiff = z;
    }

    public boolean getUserDiff() {
        return mUserDiff;
    }

    public void setSavedUserID(int i) {
        mSavedUserId = i;
    }

    public int getSavedUserID() {
        return mSavedUserId;
    }

    public void setExternalSDvolId(String str) {
        mExternalSDvolId = str;
    }

    public String getExternalSDvolId() {
        return mExternalSDvolId;
    }

    public void setExternalSDvolFsUuid(String str) {
        mExternalSDvolFsUuid = str;
    }

    public void setExternalSDvolUsedSize(long j) {
        mExternalSDvolUsedSize = j;
    }

    public String getExternalSDvolFsUuid() {
        return mExternalSDvolFsUuid;
    }

    public void setExternalSDvolState(String str) {
        mExternalSDvolState = str;
    }

    public String getExternalSDvolState() {
        return mExternalSDvolState;
    }

    public long getExternalSDvolUsedSize() {
        logD("getExternalSDvolUsedSize" + mExternalSDvolUsedSize);
        return mExternalSDvolUsedSize;
    }
}
