package com.android.server.pm;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.Environment;
import android.os.FileUtils;
import android.os.RecoverySystem;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import com.samsung.android.server.pm.PmLog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserDataPreparer {
    public final Context mContext;
    public final PackageManagerTracedLock mInstallLock;
    public final Installer mInstaller;

    public UserDataPreparer(
            Installer installer,
            PackageManagerTracedLock packageManagerTracedLock,
            Context context) {
        this.mInstallLock = packageManagerTracedLock;
        this.mContext = context;
        this.mInstaller = installer;
    }

    public static void enforceSerialNumber(File file, int i) {
        int serialNumber = getSerialNumber(file);
        Slog.v("UserDataPreparer", "Found " + file + " with serial number " + serialNumber);
        if (serialNumber != -1) {
            if (serialNumber != i) {
                throw new IOException(
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                serialNumber,
                                i,
                                "Found serial number ",
                                " doesn't match expected "));
            }
            return;
        }
        Slog.d(
                "UserDataPreparer",
                "Serial number missing on " + file + "; assuming current is valid");
        try {
            try {
                Os.setxattr(
                        file.getAbsolutePath(),
                        "user.serial",
                        Integer.toString(i).getBytes(StandardCharsets.UTF_8),
                        OsConstants.XATTR_CREATE);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } catch (IOException e2) {
            Slog.w("UserDataPreparer", "Failed to set serial number on " + file, e2);
        }
    }

    public static int getSerialNumber(File file) throws IOException {
        try {
            String str = new String(Os.getxattr(file.getAbsolutePath(), "user.serial"));
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
                throw new IOException("Bad serial number: ".concat(str));
            }
        } catch (ErrnoException e) {
            if (e.errno == OsConstants.ENODATA) {
                return -1;
            }
            throw e.rethrowAsIOException();
        }
    }

    public final void destroyUserDataLI(int i, int i2, String str) {
        StorageManager storageManager =
                (StorageManager) this.mContext.getSystemService(StorageManager.class);
        try {
            Installer installer = this.mInstaller;
            if (installer.checkBeforeRemote()) {
                try {
                    installer.mInstalld.destroyUserData(str, i, i2);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
            if (Objects.equals(str, StorageManager.UUID_PRIVATE_INTERNAL)) {
                if ((i2 & 1) != 0) {
                    FileUtils.deleteContentsAndDir(getUserSystemDirectory(i));
                    FileUtils.deleteContents(getDataSystemDeDirectory(i));
                }
                if ((i2 & 2) != 0) {
                    FileUtils.deleteContents(getDataSystemCeDirectory(i));
                }
            }
            storageManager.destroyUserStorage(str, i, i2);
        } catch (Exception e2) {
            StringBuilder m =
                    DirEncryptService$$ExternalSyntheticOutline0.m(
                            i, "Failed to destroy user ", " on volume ", str, ": ");
            m.append(e2);
            PackageManagerServiceUtils.logCriticalInfo(5, m.toString());
        }
    }

    public File getDataMiscCeDirectory(int i) {
        return Environment.getDataMiscCeDirectory(i);
    }

    public File getDataMiscDeDirectory(int i) {
        return Environment.getDataMiscDeDirectory(i);
    }

    public File getDataSystemCeDirectory(int i) {
        return Environment.getDataSystemCeDirectory(i);
    }

    public File getDataSystemDeDirectory(int i) {
        return Environment.getDataSystemDeDirectory(i);
    }

    public File getDataUserCeDirectory(String str, int i) {
        return Environment.getDataUserCeDirectory(str, i);
    }

    public File getDataUserDeDirectory(String str, int i) {
        return Environment.getDataUserDeDirectory(str, i);
    }

    public File getUserSystemDirectory(int i) {
        return Environment.getUserSystemDirectory(i);
    }

    public final void prepareUserData(int i, UserInfo userInfo) {
        PackageManagerTracedLock packageManagerTracedLock = this.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            StorageManager storageManager =
                    (StorageManager) this.mContext.getSystemService(StorageManager.class);
            prepareUserDataLI(null, userInfo, i, true);
            Iterator it = storageManager.getWritablePrivateVolumes().iterator();
            while (it.hasNext()) {
                String fsUuid = ((VolumeInfo) it.next()).getFsUuid();
                if (fsUuid != null) {
                    prepareUserDataLI(fsUuid, userInfo, i, true);
                }
            }
            packageManagerTracedLock.close();
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void prepareUserDataLI(String str, UserInfo userInfo, int i, boolean z) {
        int i2 = userInfo.id;
        int i3 = userInfo.serialNumber;
        StorageManager storageManager =
                (StorageManager) this.mContext.getSystemService(StorageManager.class);
        boolean z2 = userInfo.lastLoggedInTime == 0;
        Slogf.d(
                "UserDataPreparer",
                "Preparing user data; volumeUuid=%s, userId=%d, flags=0x%x, isNewUser=%s",
                str,
                Integer.valueOf(i2),
                Integer.valueOf(i),
                Boolean.valueOf(z2));
        try {
            storageManager.prepareUserStorage(str, i2, i);
            if ((i & 1) != 0) {
                enforceSerialNumber(getDataUserDeDirectory(str, i2), i3);
                if (Objects.equals(str, StorageManager.UUID_PRIVATE_INTERNAL)) {
                    enforceSerialNumber(getDataSystemDeDirectory(i2), i3);
                }
            }
            int i4 = i & 2;
            if (i4 != 0) {
                enforceSerialNumber(getDataUserCeDirectory(str, i2), i3);
                if (Objects.equals(str, StorageManager.UUID_PRIVATE_INTERNAL)) {
                    enforceSerialNumber(getDataSystemCeDirectory(i2), i3);
                }
            }
            Installer installer = this.mInstaller;
            if (installer.checkBeforeRemote()) {
                try {
                    installer.mInstalld.createUserData(str, i2, i3, i);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
            if (i4 == 0 || i2 != 0) {
                return;
            }
            String str2 = "sys.user." + i2 + ".ce_available";
            Slog.d("UserDataPreparer", "Setting property: " + str2 + "=true");
            SystemProperties.set(str2, "true");
        } catch (Exception e2) {
            if (z2) {
                StringBuilder m =
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i2,
                                "Destroying user ",
                                " on volume ",
                                str,
                                " because we failed to prepare: ");
                m.append(e2);
                PackageManagerServiceUtils.logCriticalInfo(6, m.toString());
                destroyUserDataLI(i2, i, str);
            } else {
                StringBuilder m2 =
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i2, "Failed to prepare user ", " on volume ", str, ": ");
                m2.append(e2);
                PackageManagerServiceUtils.logCriticalInfo(6, m2.toString());
            }
            if (z) {
                prepareUserDataLI(str, userInfo, i | 1, false);
                return;
            }
            try {
                Log.e("UserDataPreparer", "prepareUserData failed for user " + i2, e2);
                if (z2 && i2 == 0 && str == null) {
                    RecoverySystem.rebootPromptAndWipeUserData(
                            this.mContext, "failed to prepare internal storage for system user");
                }
                if (z2 && i2 == 77) {
                    PmLog.logDebugInfoAndLogcat(
                            "Failed to prepare user data of Maintenance mode", "MaintenanceMode");
                    throw new RuntimeException("Failed to prepare user data of Maintenance mode");
                }
            } catch (IOException e3) {
                throw new RuntimeException("error rebooting into recovery", e3);
            }
        }
    }

    public void reconcileUsers(String str, List list, List list2) {
        int size = list.size();
        SparseArray sparseArray = new SparseArray(size);
        for (int i = 0; i < size; i++) {
            UserInfo userInfo = (UserInfo) list.get(i);
            sparseArray.put(userInfo.id, userInfo);
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (file.isDirectory()) {
                try {
                    int parseInt = Integer.parseInt(file.getName());
                    UserInfo userInfo2 = (UserInfo) sparseArray.get(parseInt);
                    if (userInfo2 == null) {
                        PackageManagerServiceUtils.logCriticalInfo(
                                5,
                                "Destroying user directory "
                                        + file
                                        + " because no matching user was found");
                    } else {
                        try {
                            enforceSerialNumber(file, userInfo2.serialNumber);
                        } catch (IOException e) {
                            PackageManagerServiceUtils.logCriticalInfo(
                                    5,
                                    "Destroying user directory "
                                            + file
                                            + " because we failed to enforce serial number: "
                                            + e);
                        }
                    }
                    PackageManagerTracedLock packageManagerTracedLock = this.mInstallLock;
                    packageManagerTracedLock.mLock.lock();
                    try {
                        destroyUserDataLI(parseInt, 3, str);
                        packageManagerTracedLock.close();
                    } catch (Throwable th) {
                        try {
                            packageManagerTracedLock.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (NumberFormatException unused) {
                    Slog.w("UserDataPreparer", "Invalid user directory " + file);
                }
            }
        }
    }
}
