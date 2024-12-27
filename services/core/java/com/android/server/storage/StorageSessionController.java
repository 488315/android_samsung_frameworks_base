package com.android.server.storage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.VolumeInfo;
import android.util.DebugUtils;
import android.util.SparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.util.Preconditions;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.storage.StorageUserConnection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StorageSessionController {
    public final Context mContext;
    public volatile int mExternalStorageServiceAppId;
    public volatile ComponentName mExternalStorageServiceComponent;
    public volatile String mExternalStorageServicePackageName;
    public volatile boolean mIsResetting;
    public final UserManager mUserManager;
    public final Object mLock = new Object();
    public final SparseArray mConnections = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ExternalStorageServiceException extends Exception {
        public ExternalStorageServiceException(String str) {
            super(str);
        }
    }

    public StorageSessionController(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    public static String createVolumeInfoStrForPulbicVolume(VolumeInfo volumeInfo) {
        String valueToString = DebugUtils.valueToString(VolumeInfo.class, "TYPE_", volumeInfo.type);
        String diskId = volumeInfo.getDiskId() != null ? volumeInfo.getDiskId() : "null";
        String str = volumeInfo.partGuid;
        if (str == null) {
            str = "";
        }
        String flagsToString = DebugUtils.flagsToString(VolumeInfo.class, "MOUNT_FLAG_", volumeInfo.mountFlags);
        String valueToString2 = DebugUtils.valueToString(VolumeInfo.class, "STATE_", volumeInfo.state);
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1("VolumeInfo".concat("{" + volumeInfo.id + "}:"), "\n"));
        StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("type=", valueToString, " diskId=", diskId, " partGuid=");
        m2.append(str);
        m.append(m2.toString());
        StringBuilder m3 = BootReceiver$$ExternalSyntheticOutline0.m(m.toString());
        m3.append(String.format(" mountFlags=%s mountUserId=%d state=%s", flagsToString, Integer.valueOf(volumeInfo.getMountUserId()), valueToString2));
        StringBuilder m4 = BootReceiver$$ExternalSyntheticOutline0.m(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(m3.toString(), "\n"));
        String str2 = volumeInfo.fsType;
        String str3 = volumeInfo.fsUuid;
        String str4 = volumeInfo.path;
        String str5 = volumeInfo.internalPath;
        StringBuilder m5 = InitialConfiguration$$ExternalSyntheticOutline0.m(" fsType=", str2, " fsUuid=", str3, " path=");
        m5.append(str4);
        m5.append(" internalPath=");
        m5.append(str5);
        m4.append(m5.toString());
        return m4.toString();
    }

    public final void freeCache(String str, long j) {
        synchronized (this.mLock) {
            try {
                int size = this.mConnections.size();
                for (int i = 0; i < size; i++) {
                    StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(this.mConnections.keyAt(i));
                    if (storageUserConnection != null) {
                        synchronized (storageUserConnection.mSessionsLock) {
                            try {
                                Iterator it = ((HashMap) storageUserConnection.mSessions).keySet().iterator();
                                while (it.hasNext()) {
                                    storageUserConnection.mActiveConnection.freeCache((String) it.next(), j, str);
                                }
                            } finally {
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getConnectionUserIdForVolume(VolumeInfo volumeInfo) {
        boolean isMediaSharedWithParent = ((UserManager) this.mContext.createContextAsUser(UserHandle.of(volumeInfo.mountUserId), 0).getSystemService(UserManager.class)).isMediaSharedWithParent();
        UserInfo userInfo = this.mUserManager.getUserInfo(volumeInfo.mountUserId);
        return (userInfo == null || !isMediaSharedWithParent) ? volumeInfo.mountUserId : userInfo.profileGroupId;
    }

    public final void notifyAnrDelayStarted(int i, String str) {
        StorageUserConnection storageUserConnection;
        int userId = UserHandle.getUserId(i);
        synchronized (this.mLock) {
            storageUserConnection = (StorageUserConnection) this.mConnections.get(userId);
        }
        if (storageUserConnection != null) {
            List primaryVolumeIds = storageUserConnection.mSmInternal.getPrimaryVolumeIds();
            synchronized (storageUserConnection.mSessionsLock) {
                try {
                    Iterator it = ((HashMap) storageUserConnection.mSessions).keySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        } else if (primaryVolumeIds.contains((String) it.next())) {
                            storageUserConnection.mActiveConnection.notifyAnrDelayStarted(i, str);
                        }
                    }
                } finally {
                }
            }
        }
    }

    public final void notifyVolumeStateChanged(VolumeInfo volumeInfo) {
        if (shouldHandle(volumeInfo)) {
            String id = volumeInfo.getId();
            int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
            synchronized (this.mLock) {
                try {
                    StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
                    if (storageUserConnection != null) {
                        Slog.i("StorageSessionController", "Notifying volume state changed for session with id: " + id);
                        storageUserConnection.notifyVolumeStateChanged(id, volumeInfo.buildStorageVolume(this.mContext, volumeInfo.getMountUserId(), false));
                    } else {
                        Slog.w("StorageSessionController", "No available storage user connection for userId : " + connectionUserIdForVolume);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void onUnlockUser(int i) {
        Slog.i("StorageSessionController", "On user unlock " + i);
        if (i == 0 || i == 77) {
            Slog.i("StorageSessionController", "Initialialising...");
            ProviderInfo resolveContentProvider = this.mContext.getPackageManager().resolveContentProvider("media", 1835008);
            if (resolveContentProvider == null) {
                throw new ExternalStorageServiceException("No valid MediaStore provider found");
            }
            this.mExternalStorageServicePackageName = resolveContentProvider.applicationInfo.packageName;
            this.mExternalStorageServiceAppId = UserHandle.getAppId(resolveContentProvider.applicationInfo.uid);
            Intent intent = new Intent("android.service.storage.ExternalStorageService");
            intent.setPackage(this.mExternalStorageServicePackageName);
            ResolveInfo resolveServiceAsUser = this.mContext.getPackageManager().resolveServiceAsUser(intent, 786564, 0);
            ServiceInfo serviceInfo = resolveServiceAsUser == null ? null : resolveServiceAsUser.serviceInfo;
            if (serviceInfo == null) {
                throw new ExternalStorageServiceException("No valid ExternalStorageService component found");
            }
            ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
            if (!"android.permission.BIND_EXTERNAL_STORAGE_SERVICE".equals(serviceInfo.permission)) {
                throw new ExternalStorageServiceException(componentName.flattenToShortString() + " does not require permission android.permission.BIND_EXTERNAL_STORAGE_SERVICE");
            }
            this.mExternalStorageServiceComponent = componentName;
            Slog.i("StorageSessionController", "mExternalStorageServiceComponent=" + componentName);
        }
    }

    public final void onUserStopping(int i) {
        StorageUserConnection storageUserConnection;
        if (shouldHandle(null)) {
            synchronized (this.mLock) {
                storageUserConnection = (StorageUserConnection) this.mConnections.get(i);
                this.mConnections.remove(i);
            }
            if (storageUserConnection == null) {
                Slog.w("StorageSessionController", "No connection found for user: " + i);
                return;
            }
            Slog.i("StorageSessionController", "Removing all sessions for user: " + i);
            synchronized (storageUserConnection.mSessionsLock) {
                Slog.i("StorageUserConnection", "Removing  " + ((HashMap) storageUserConnection.mSessions).size() + " sessions for user: " + storageUserConnection.mUserId + "...");
                ((HashMap) storageUserConnection.mSessions).clear();
            }
        }
    }

    public final void onVolumeMount(ParcelFileDescriptor parcelFileDescriptor, VolumeInfo volumeInfo) {
        StorageUserConnection storageUserConnection;
        if (shouldHandle(volumeInfo)) {
            if (volumeInfo.type == 0) {
                Slog.i("StorageSessionController", "On volume mount " + createVolumeInfoStrForPulbicVolume(volumeInfo));
            } else {
                Slog.i("StorageSessionController", "On volume mount " + volumeInfo);
            }
            String id = volumeInfo.getId();
            int connectionUserIdForVolume = getConnectionUserIdForVolume(volumeInfo);
            synchronized (this.mLock) {
                try {
                    storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
                    if (storageUserConnection == null) {
                        Slog.i("StorageSessionController", "Creating connection for user: " + connectionUserIdForVolume);
                        storageUserConnection = new StorageUserConnection(this.mContext, connectionUserIdForVolume, this);
                        this.mConnections.put(connectionUserIdForVolume, storageUserConnection);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            Slog.i("StorageSessionController", "Creating and starting session with id: " + id);
            String path = volumeInfo.getPath().getPath();
            String path2 = volumeInfo.getInternalPath().getPath();
            Objects.requireNonNull(id);
            Objects.requireNonNull(path);
            Objects.requireNonNull(path2);
            StorageUserConnection.Session session = new StorageUserConnection.Session(id, path, path2);
            synchronized (storageUserConnection.mSessionsLock) {
                Preconditions.checkArgument(!((HashMap) storageUserConnection.mSessions).containsKey(id));
                ((HashMap) storageUserConnection.mSessions).put(id, session);
            }
            StorageUserConnection.ActiveConnection activeConnection = storageUserConnection.mActiveConnection;
            activeConnection.getClass();
            try {
                try {
                    activeConnection.waitForAsyncVoid(new StorageUserConnection$ActiveConnection$$ExternalSyntheticLambda1(session, parcelFileDescriptor));
                } finally {
                    try {
                        parcelFileDescriptor.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (Exception e) {
                throw new ExternalStorageServiceException("Failed to start session: " + session, e);
            }
        }
    }

    public final StorageUserConnection onVolumeRemove(VolumeInfo volumeInfo) {
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
            try {
                StorageUserConnection storageUserConnection = (StorageUserConnection) this.mConnections.get(connectionUserIdForVolume);
                if (storageUserConnection == null) {
                    Slog.w("StorageSessionController", "Session already removed for vol with id: " + id);
                    return null;
                }
                Slog.i("StorageSessionController", "Removed session for vol with id: " + id);
                storageUserConnection.removeSession(id);
                return storageUserConnection;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean shouldHandle(VolumeInfo volumeInfo) {
        int i;
        return !this.mIsResetting && (volumeInfo == null || (i = volumeInfo.type) == 2 || ((i == 0 && volumeInfo.isVisible()) || volumeInfo.type == 5));
    }
}
