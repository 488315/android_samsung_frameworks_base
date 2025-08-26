package com.android.internal.content;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.dex.DexMetadataHelper;
import android.content.pm.parsing.PackageLite;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.IStorageManager;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.core.pm.containerservice.AsecUtils;
import com.samsung.android.media.AudioParameter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public class InstallLocationUtils {
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_FAILED_ALREADY_EXISTS = -4;
    public static final int RECOMMEND_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int RECOMMEND_FAILED_INVALID_APK = -2;
    public static final int RECOMMEND_FAILED_INVALID_LOCATION = -3;
    public static final int RECOMMEND_FAILED_INVALID_URI = -6;
    public static final int RECOMMEND_INSTALL_EPHEMERAL = 3;
    public static final int RECOMMEND_INSTALL_EXTERNAL = 2;
    public static final int RECOMMEND_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_MEDIA_UNAVAILABLE = -5;
    private static final String TAG = "PackageHelper";
    private static TestableInterface sDefaultTestableInterface;

    public static abstract class TestableInterface {
        public abstract boolean getAllow3rdPartyOnInternalConfig(Context context);

        public abstract File getDataDirectory();

        public abstract ApplicationInfo getExistingAppInfo(Context context, String str);

        public abstract boolean getForceAllowOnExternalSetting(Context context);

        public abstract StorageManager getStorageManager(Context context);
    }

    public static int getInstallationErrorCode(int i) {
        if (i == -3) {
            return -19;
        }
        if (i == -4) {
            return -1;
        }
        if (i == -1) {
            return -4;
        }
        if (i == -2) {
            return -2;
        }
        if (i == -6) {
            return -3;
        }
        return i == -5 ? -20 : 1;
    }

    public static int installLocationPolicy(int i, int i2, int i3, boolean z, boolean z2) {
        if ((i3 & 2) == 0) {
            return -4;
        }
        if (z || i == 1) {
            return 1;
        }
        return i == 2 ? i2 : z2 ? 2 : 1;
    }

    public static int translateAllocateFlags(int i) {
        return (i & 32768) != 0 ? 1 : 0;
    }

    public static IStorageManager getStorageManager() throws RemoteException {
        IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
        if (service != null) {
            return IStorageManager.Stub.asInterface(service);
        }
        Log.e(TAG, "Can't get storagemanager service");
        throw new RemoteException("Could not contact storagemanager service");
    }

    private static synchronized TestableInterface getDefaultTestableInterface() {
        if (sDefaultTestableInterface == null) {
            sDefaultTestableInterface = new TestableInterface() { // from class: com.android.internal.content.InstallLocationUtils.1
                @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                public StorageManager getStorageManager(Context context) {
                    return (StorageManager) context.getSystemService(StorageManager.class);
                }

                @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                public boolean getForceAllowOnExternalSetting(Context context) {
                    return Settings.Global.getInt(context.getContentResolver(), Settings.Global.FORCE_ALLOW_ON_EXTERNAL, 0) != 0;
                }

                @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                public boolean getAllow3rdPartyOnInternalConfig(Context context) {
                    return context.getResources().getBoolean(R.bool.config_allow3rdPartyAppOnInternal);
                }

                @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                public ApplicationInfo getExistingAppInfo(Context context, String str) {
                    try {
                        return context.getPackageManager().getApplicationInfo(str, 4194304);
                    } catch (PackageManager.NameNotFoundException unused) {
                        return null;
                    }
                }

                @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                public File getDataDirectory() {
                    return Environment.getDataDirectory();
                }
            };
        }
        return sDefaultTestableInterface;
    }

    @Deprecated
    public static String resolveInstallVolume(Context context, String str, int i, long j, TestableInterface testableInterface) throws IOException {
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = str;
        sessionParams.installLocation = i;
        sessionParams.sizeBytes = j;
        return resolveInstallVolume(context, sessionParams, testableInterface);
    }

    public static String resolveInstallVolume(Context context, PackageInstaller.SessionParams sessionParams) throws IOException {
        return resolveInstallVolume(context, sessionParams.appPackageName, sessionParams.installLocation, sessionParams.sizeBytes, getDefaultTestableInterface());
    }

    private static boolean checkFitOnVolume(StorageManager storageManager, String str, PackageInstaller.SessionParams sessionParams) throws Throwable {
        if (str == null) {
            return false;
        }
        int iTranslateAllocateFlags = translateAllocateFlags(sessionParams.installFlags);
        UUID uuidForPath = storageManager.getUuidForPath(new File(str));
        long allocatableBytes = storageManager.getAllocatableBytes(uuidForPath, iTranslateAllocateFlags | 8);
        if (sessionParams.sizeBytes <= allocatableBytes) {
            return true;
        }
        return sessionParams.sizeBytes <= allocatableBytes + storageManager.getAllocatableBytes(uuidForPath, iTranslateAllocateFlags | 16);
    }

    public static String resolveInstallVolume(Context context, PackageInstaller.SessionParams sessionParams, TestableInterface testableInterface) throws Throwable {
        StorageManager storageManager = testableInterface.getStorageManager(context);
        boolean forceAllowOnExternalSetting = testableInterface.getForceAllowOnExternalSetting(context);
        boolean allow3rdPartyOnInternalConfig = testableInterface.getAllow3rdPartyOnInternalConfig(context);
        ApplicationInfo existingAppInfo = testableInterface.getExistingAppInfo(context, sessionParams.appPackageName);
        ArrayMap arrayMap = new ArrayMap();
        String str = null;
        for (VolumeInfo volumeInfo : storageManager.getVolumes()) {
            if (volumeInfo.type == 1 && volumeInfo.isMountedWritable()) {
                boolean zEquals = VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id);
                if (zEquals) {
                    str = volumeInfo.path;
                }
                if (!zEquals || allow3rdPartyOnInternalConfig) {
                    arrayMap.put(volumeInfo.fsUuid, volumeInfo.path);
                }
            }
        }
        if (existingAppInfo != null && existingAppInfo.isSystemApp()) {
            if (!checkFitOnVolume(storageManager, str, sessionParams)) {
                throw new IOException("Not enough space on existing volume " + existingAppInfo.volumeUuid + " for system app " + sessionParams.appPackageName + " upgrade");
            }
            return StorageManager.UUID_PRIVATE_INTERNAL;
        }
        if (!forceAllowOnExternalSetting && sessionParams.installLocation == 1) {
            if (existingAppInfo == null || Objects.equals(existingAppInfo.volumeUuid, StorageManager.UUID_PRIVATE_INTERNAL)) {
                if (!allow3rdPartyOnInternalConfig) {
                    throw new IOException("Not allowed to install non-system apps on internal storage");
                }
                if (checkFitOnVolume(storageManager, str, sessionParams)) {
                    return StorageManager.UUID_PRIVATE_INTERNAL;
                }
                throw new IOException("Requested internal only, but not enough space");
            }
            throw new IOException("Cannot automatically move " + sessionParams.appPackageName + " from " + existingAppInfo.volumeUuid + " to internal storage");
        }
        if (existingAppInfo != null) {
            if (Objects.equals(existingAppInfo.volumeUuid, StorageManager.UUID_PRIVATE_INTERNAL)) {
                str = str;
            } else if (arrayMap.containsKey(existingAppInfo.volumeUuid)) {
                str = (String) arrayMap.get(existingAppInfo.volumeUuid);
            }
            if (!checkFitOnVolume(storageManager, str, sessionParams)) {
                throw new IOException("Not enough space on existing volume " + existingAppInfo.volumeUuid + " for " + sessionParams.appPackageName + " upgrade");
            }
            return existingAppInfo.volumeUuid;
        }
        str = arrayMap.isEmpty() ? null : (String) arrayMap.keyAt(0);
        if (arrayMap.size() == 1) {
            if (checkFitOnVolume(storageManager, (String) arrayMap.valueAt(0), sessionParams)) {
                return str;
            }
        } else {
            long j = Long.MIN_VALUE;
            for (String str2 : arrayMap.keySet()) {
                long allocatableBytes = storageManager.getAllocatableBytes(storageManager.getUuidForPath(new File((String) arrayMap.get(str2))), translateAllocateFlags(sessionParams.installFlags));
                if (allocatableBytes >= j) {
                    str = str2;
                    j = allocatableBytes;
                }
            }
            if (j >= sessionParams.sizeBytes) {
                return str;
            }
        }
        if (!arrayMap.isEmpty() && 2147483647L == sessionParams.sizeBytes && SystemProperties.getBoolean("debug.pm.install_skip_size_check_for_maxint", false)) {
            return str;
        }
        throw new IOException("No special requests, but no room on allowed volumes.  allow3rdPartyOnInternal? " + allow3rdPartyOnInternalConfig);
    }

    public static boolean fitsOnInternal(Context context, PackageInstaller.SessionParams sessionParams) throws Throwable {
        StorageManager storageManager = (StorageManager) context.getSystemService(StorageManager.class);
        UUID uuidForPath = storageManager.getUuidForPath(Environment.getDataDirectory());
        int iTranslateAllocateFlags = translateAllocateFlags(sessionParams.installFlags);
        long allocatableBytes = storageManager.getAllocatableBytes(uuidForPath, iTranslateAllocateFlags | 8);
        if (sessionParams.sizeBytes <= allocatableBytes) {
            return true;
        }
        return sessionParams.sizeBytes <= allocatableBytes + storageManager.getAllocatableBytes(uuidForPath, iTranslateAllocateFlags | 16);
    }

    public static boolean fitsOnExternal(Context context, PackageInstaller.SessionParams sessionParams) {
        StorageManager storageManager = (StorageManager) context.getSystemService(StorageManager.class);
        storageManager.getPrimaryVolume();
        return AsecUtils.fitsOnExternal(sessionParams, storageManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int resolveInstallLocation(Context context, PackageInstaller.SessionParams sessionParams) throws IOException {
        ApplicationInfo applicationInfo;
        char c;
        boolean z;
        boolean z2;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(sessionParams.appPackageName, 4194304);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        if ((sessionParams.installFlags & 2048) != 0) {
            z2 = false;
            c = 1;
            z = true;
        } else if ((sessionParams.installFlags & 16) == 0) {
            if ((sessionParams.installFlags & 8) != 0) {
                c = 2;
                z2 = false;
                z = false;
            } else if (sessionParams.installLocation == 1) {
                z2 = false;
                z = false;
                c = 1;
            } else if (sessionParams.installLocation != 2) {
                if (sessionParams.installLocation == 0) {
                    c = (applicationInfo == null || (applicationInfo.flags & 262144) == 0) ? (char) 1 : (char) 2;
                    z = false;
                    z2 = true;
                }
                z2 = false;
                z = false;
                c = 1;
            }
        }
        boolean zFitsOnInternal = (z2 || c == 1) ? fitsOnInternal(context, sessionParams) : false;
        boolean zFitsOnExternal = (z2 || c == 2) ? fitsOnExternal(context, sessionParams) : false;
        if (c == 1) {
            if (zFitsOnInternal) {
                return z ? 3 : 1;
            }
        } else if (c == 2 && zFitsOnExternal) {
            return 2;
        }
        if (!z2) {
            return -1;
        }
        if (zFitsOnInternal) {
            return 1;
        }
        return zFitsOnExternal ? 2 : -1;
    }

    @Deprecated
    public static long calculateInstalledSize(PackageLite packageLite, boolean z, String str) throws IOException {
        return calculateInstalledSize(packageLite, str);
    }

    public static long calculateInstalledSize(PackageLite packageLite, String str) throws IOException {
        return calculateInstalledSize(packageLite, str, (FileDescriptor) null);
    }

    public static long calculateInstalledSize(PackageLite packageLite, String str, FileDescriptor fileDescriptor) throws IOException {
        NativeLibraryHelper.Handle handleCreate;
        NativeLibraryHelper.Handle handle = null;
        try {
            if (fileDescriptor != null) {
                handleCreate = NativeLibraryHelper.Handle.createFd(packageLite, fileDescriptor);
            } else {
                handleCreate = NativeLibraryHelper.Handle.create(packageLite);
            }
            handle = handleCreate;
            return calculateInstalledSize(packageLite, handle, str);
        } finally {
            IoUtils.closeQuietly(handle);
        }
    }

    @Deprecated
    public static long calculateInstalledSize(PackageLite packageLite, boolean z, NativeLibraryHelper.Handle handle, String str) throws IOException {
        return calculateInstalledSize(packageLite, handle, str);
    }

    public static long calculateInstalledSize(PackageLite packageLite, NativeLibraryHelper.Handle handle, String str) throws IOException {
        Iterator<String> it = packageLite.getAllApkPaths().iterator();
        long length = 0;
        while (it.hasNext()) {
            length += new File(it.next()).length();
        }
        long packageDexMetadataSize = length + DexMetadataHelper.getPackageDexMetadataSize(packageLite);
        return packageLite.isExtractNativeLibs() ? packageDexMetadataSize + NativeLibraryHelper.sumNativeBinariesWithOverride(handle, str) : packageDexMetadataSize;
    }

    public static String replaceEnd(String str, String str2, String str3) {
        if (!str.endsWith(str2)) {
            throw new IllegalArgumentException("Expected " + str + " to end with " + str2);
        }
        return str.substring(0, str.length() - str2.length()) + str3;
    }
}
