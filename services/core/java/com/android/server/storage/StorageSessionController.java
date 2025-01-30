package com.android.server.storage;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.VolumeInfo;
import android.p005os.IVold;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class StorageSessionController {
    public final Context mContext;
    public volatile int mExternalStorageServiceAppId;
    public volatile ComponentName mExternalStorageServiceComponent;
    public volatile String mExternalStorageServicePackageName;
    public volatile boolean mIsResetting;
    public final UserManager mUserManager;
    public final Object mLock = new Object();
    public final SparseArray mConnections = new SparseArray();

    public StorageSessionController(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public final String createVolumeInfoStrForPulbicVolume(VolumeInfo volumeInfo) {
        String valueToString = DebugUtils.valueToString(VolumeInfo.class, "TYPE_", volumeInfo.type);
        String diskId = volumeInfo.getDiskId() != null ? volumeInfo.getDiskId() : "null";
        String str = volumeInfo.partGuid;
        if (str == null) {
            str = "";
        }
        String flagsToString = DebugUtils.flagsToString(VolumeInfo.class, "MOUNT_FLAG_", volumeInfo.mountFlags);
        String valueToString2 = DebugUtils.valueToString(VolumeInfo.class, "STATE_", volumeInfo.state);
        return ((((("VolumeInfo" + String.format("{%s}:", volumeInfo.id)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format("type=%s diskId=%s partGuid=%s", valueToString, diskId, str)) + String.format(" mountFlags=%s mountUserId=%d state=%s", flagsToString, Integer.valueOf(volumeInfo.getMountUserId()), valueToString2)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE) + String.format(" fsType=%s fsUuid=%s path=%s internalPath=%s", volumeInfo.fsType, volumeInfo.fsUuid, volumeInfo.path, volumeInfo.internalPath);
    }

    public int getConnectionUserIdForVolume(VolumeInfo volumeInfo) {
        boolean isMediaSharedWithParent = ((UserManager) this.mContext.createContextAsUser(UserHandle.of(volumeInfo.mountUserId), 0).getSystemService(UserManager.class)).isMediaSharedWithParent();
        UserInfo userInfo = this.mUserManager.getUserInfo(volumeInfo.mountUserId);
        if (userInfo != null && isMediaSharedWithParent) {
            return userInfo.profileGroupId;
        }
        return volumeInfo.mountUserId;
    }

    public void onVolumeMount(ParcelFileDescriptor parcelFileDescriptor, VolumeInfo volumeInfo) {
        if (shouldHandle(volumeInfo)) {
            if (volumeInfo.type == 0) {
                Slog.i("StorageSessionController", "On volume mount " + createVolumeInfoStrForPulbicVolume(volumeInfo));
            } else {
                Slog.i("StorageSessionController", "On volume mount " + volumeInfo);
            }
            String id = volumeInfo.getId();
            int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
            synchronized (this.mLock) {
                StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection == null) {
                    Slog.i("StorageSessionController", "Creating connection for user: " + connectionUserIdForVolume);
                    storageUserConnection = new StorageUserConnection(this.mContext, connectionUserIdForVolume, this);
                    this.mConnections.put(connectionUserIdForVolume, storageUserConnection);
                }
                Slog.i("StorageSessionController", "Creating and starting session with id: " + id);
                storageUserConnection.startSession(id, parcelFileDescriptor, volumeInfo.getPath().getPath(), volumeInfo.getInternalPath().getPath());
            }
        }
    }

    public void notifyVolumeStateChanged(VolumeInfo volumeInfo) {
        if (shouldHandle(volumeInfo)) {
            String id = volumeInfo.getId();
            int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
            synchronized (this.mLock) {
                StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection != null) {
                    Slog.i("StorageSessionController", "Notifying volume state changed for session with id: " + id);
                    storageUserConnection.notifyVolumeStateChanged(id, volumeInfo.buildStorageVolume(this.mContext, volumeInfo.getMountUserId(), false));
                } else {
                    Slog.w("StorageSessionController", "No available storage user connection for userId : " + connectionUserIdForVolume);
                }
            }
        }
    }

    public void freeCache(String str, long j) {
        synchronized (this.mLock) {
            int size = this.mConnections.size();
            for (int i = 0; i < size; i++) {
                StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(this.mConnections.keyAt(i));
                if (storageUserConnection != null) {
                    storageUserConnection.freeCache(str, j);
                }
            }
        }
    }

    public void notifyAnrDelayStarted(String str, int i, int i2, int i3) {
        StorageUserConnection storageUserConnection;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = (StorageUserConnection) this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAnrDelayStarted(str, i, i2, i3);
        }
    }

    public StorageUserConnection onVolumeRemove(VolumeInfo volumeInfo) {
        if (!shouldHandle(volumeInfo)) {
            return null;
        }
        if (volumeInfo.type == 0) {
            Slog.i("StorageSessionController", "On volume remove " + createVolumeInfoStrForPulbicVolume(volumeInfo));
        } else {
            Slog.i("StorageSessionController", "On volume remove " + volumeInfo);
        }
        String id = volumeInfo.getId();
        int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
        synchronized (this.mLock) {
            StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
            if (storageUserConnection != null) {
                Slog.i("StorageSessionController", "Removed session for vol with id: " + id);
                storageUserConnection.removeSession(id);
                return storageUserConnection;
            }
            Slog.w("StorageSessionController", "Session already removed for vol with id: " + id);
            return null;
        }
    }

    public void onVolumeUnmount(VolumeInfo volumeInfo) {
        StorageUserConnection onVolumeRemove = onVolumeRemove(volumeInfo);
        if (volumeInfo.type == 0) {
            Slog.i("StorageSessionController", "On volume unmount " + createVolumeInfoStrForPulbicVolume(volumeInfo));
        } else {
            Slog.i("StorageSessionController", "On volume unmount " + volumeInfo);
        }
        if (onVolumeRemove != null) {
            String id = volumeInfo.getId();
            try {
                onVolumeRemove.removeSessionAndWait(id);
            } catch (ExternalStorageServiceException e) {
                Slog.e("StorageSessionController", "Failed to end session for vol with id: " + id, e);
            }
        }
    }

    public void onUnlockUser(int i) {
        Slog.i("StorageSessionController", "On user unlock " + i);
        if (i == 0 || i == 77) {
            initExternalStorageServiceComponent();
        }
    }

    public void onUserStopping(int i) {
        StorageUserConnection storageUserConnection;
        if (shouldHandle(null)) {
            synchronized (this.mLock) {
                storageUserConnection = (StorageUserConnection) this.mConnections.get(i);
                this.mConnections.remove(i);
            }
            if (storageUserConnection != null) {
                Slog.i("StorageSessionController", "Removing all sessions for user: " + i);
                storageUserConnection.removeAllSessions();
                return;
            }
            Slog.w("StorageSessionController", "No connection found for user: " + i);
        }
    }

    public void onReset(IVold iVold, Runnable runnable) {
        if (shouldHandle(null)) {
            SparseArray sparseArray = new SparseArray();
            synchronized (this.mLock) {
                this.mIsResetting = true;
                Slog.i("StorageSessionController", "Started resetting external storage service...");
                for (int i = 0; i < this.mConnections.size(); i++) {
                    sparseArray.put(this.mConnections.keyAt(i), (StorageUserConnection) this.mConnections.valueAt(i));
                }
            }
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                StorageUserConnection storageUserConnection = (StorageUserConnection) sparseArray.valueAt(i2);
                for (String str : storageUserConnection.getAllSessionIds()) {
                    try {
                        Slog.i("StorageSessionController", "Unmounting " + str);
                        iVold.unmount(str);
                        Slog.i("StorageSessionController", "Unmounted " + str);
                    } catch (ServiceSpecificException | RemoteException e) {
                        Slog.e("StorageSessionController", "Failed to unmount volume: " + str, e);
                    }
                    try {
                        Slog.i("StorageSessionController", "Exiting " + str);
                        storageUserConnection.removeSessionAndWait(str);
                        Slog.i("StorageSessionController", "Exited " + str);
                    } catch (ExternalStorageServiceException | IllegalStateException e2) {
                        Slog.e("StorageSessionController", "Failed to exit session: " + str + ". Killing MediaProvider...", e2);
                        killExternalStorageService(sparseArray.keyAt(i2));
                    }
                }
                storageUserConnection.close();
            }
            runnable.run();
            synchronized (this.mLock) {
                this.mConnections.clear();
                this.mIsResetting = false;
                Slog.i("StorageSessionController", "Finished resetting external storage service");
            }
        }
    }

    public final void initExternalStorageServiceComponent() {
        Slog.i("StorageSessionController", "Initialialising...");
        ProviderInfo resolveContentProvider = this.mContext.getPackageManager().resolveContentProvider("media", 1835008);
        if (resolveContentProvider == null) {
            throw new ExternalStorageServiceException("No valid MediaStore provider found");
        }
        this.mExternalStorageServicePackageName = resolveContentProvider.applicationInfo.packageName;
        this.mExternalStorageServiceAppId = UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
        ServiceInfo resolveExternalStorageServiceAsUser = resolveExternalStorageServiceAsUser(0);
        if (resolveExternalStorageServiceAsUser == null) {
            throw new ExternalStorageServiceException("No valid ExternalStorageService component found");
        }
        ComponentName componentName = new ComponentName(resolveExternalStorageServiceAsUser.packageName, resolveExternalStorageServiceAsUser.name);
        if (!"android.permission.BIND_EXTERNAL_STORAGE_SERVICE".equals(resolveExternalStorageServiceAsUser.permission)) {
            throw new ExternalStorageServiceException(componentName.flattenToShortString() + " does not require permission android.permission.BIND_EXTERNAL_STORAGE_SERVICE");
        }
        this.mExternalStorageServiceComponent = componentName;
        Slog.i("StorageSessionController", "mExternalStorageServiceComponent=" + componentName);
    }

    public ComponentName getExternalStorageServiceComponentName() {
        return this.mExternalStorageServiceComponent;
    }

    public void notifyAppIoBlocked(String str, int i, int i2, int i3) {
        StorageUserConnection storageUserConnection;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = (StorageUserConnection) this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAppIoBlocked(str, i, i2, i3);
        }
    }

    public void notifyAppIoResumed(String str, int i, int i2, int i3) {
        StorageUserConnection storageUserConnection;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = (StorageUserConnection) this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            storageUserConnection.notifyAppIoResumed(str, i, i2, i3);
        }
    }

    public boolean isAppIoBlocked(int i) {
        StorageUserConnection storageUserConnection;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = (StorageUserConnection) this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            return storageUserConnection.isAppIoBlocked(i);
        }
        return false;
    }

    public final void killExternalStorageService(int i) {
        try {
            ActivityManager.getService().killApplication(this.mExternalStorageServicePackageName, this.mExternalStorageServiceAppId, i, "storage_session_controller reset", 13);
        } catch (RemoteException unused) {
            Slog.i("StorageSessionController", "Failed to kill the ExtenalStorageService for user " + i);
        }
    }

    public static boolean isEmulatedOrPublic(VolumeInfo volumeInfo) {
        int i = volumeInfo.type;
        return i == 2 || (i == 0 && volumeInfo.isVisible());
    }

    public class ExternalStorageServiceException extends Exception {
        public ExternalStorageServiceException(String str) {
            super(str);
        }

        public ExternalStorageServiceException(String str, Throwable th) {
            super(str, th);
        }
    }

    public static boolean isSupportedVolume(VolumeInfo volumeInfo) {
        return isEmulatedOrPublic(volumeInfo) || volumeInfo.type == 5;
    }

    public final boolean shouldHandle(VolumeInfo volumeInfo) {
        return !this.mIsResetting && (volumeInfo == null || isSupportedVolume(volumeInfo));
    }

    public boolean supportsExternalStorage(int i) {
        return resolveExternalStorageServiceAsUser(i) != null;
    }

    public final ServiceInfo resolveExternalStorageServiceAsUser(int i) {
        Intent intent = new Intent("android.service.storage.ExternalStorageService");
        intent.setPackage(this.mExternalStorageServicePackageName);
        ResolveInfo resolveServiceAsUser = this.mContext.getPackageManager().resolveServiceAsUser(intent, 786564, i);
        if (resolveServiceAsUser == null) {
            return null;
        }
        return resolveServiceAsUser.serviceInfo;
    }
}
