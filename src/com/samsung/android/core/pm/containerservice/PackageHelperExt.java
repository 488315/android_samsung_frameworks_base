package com.samsung.android.core.pm.containerservice;

import android.content.pm.parsing.PackageLite;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.storage.IStorageManager;
import android.os.storage.StorageVolume;
import android.util.DataUnit;
import android.util.Log;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.media.AudioParameter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* loaded from: classes6.dex */
public class PackageHelperExt {
    private static final boolean DEBUG_MOVE = false;
    private static final long MB_IN_BYTES = DataUnit.MEBIBYTES.toBytes(1);
    public static final int OperationSucceeded = 0;
    private static String TAG = "SamsungPackageHelper";

    public interface StorageManagerExt {
        int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException;

        int destroySecureContainer(String str, boolean z) throws RemoteException;

        int finalizeSecureContainer(String str) throws RemoteException;

        void finishMediaUpdate() throws RemoteException;

        int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException;

        String getSecureContainerFilesystemPath(String str) throws RemoteException;

        String[] getSecureContainerList() throws RemoteException;

        String getSecureContainerPath(String str) throws RemoteException;

        int getUsedSpaceSecureContainer(String str) throws RemoteException;

        StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException;

        boolean isSecureContainerMounted(String str) throws RemoteException;

        int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException;

        int renameSecureContainer(String str, String str2) throws RemoteException;

        int resizeSecureContainer(String str, int i, String str2) throws RemoteException;

        int trimSecureContainer(String str, int i, String str2) throws RemoteException;

        int unmountSecureContainer(String str, boolean z) throws RemoteException;
    }

    public static StorageManagerExt getStorageManagerExt() throws RemoteException {
        return new StorageManagerExt() { // from class: com.samsung.android.core.pm.containerservice.PackageHelperExt.1
            IStorageManager mStorageManager = PackageHelperExt.getStorageManager();

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int createSecureContainer(String str, int i, String str2, String str3, int i2, boolean z) throws RemoteException {
                return this.mStorageManager.createSecureContainer(str, i, str2, str3, i2, z);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public String getSecureContainerPath(String str) throws RemoteException {
                return this.mStorageManager.getSecureContainerPath(str);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int resizeSecureContainer(String str, int i, String str2) throws RemoteException {
                return this.mStorageManager.resizeSecureContainer(str, i, str2);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int mountSecureContainer(String str, String str2, int i, boolean z) throws RemoteException {
                return this.mStorageManager.mountSecureContainer(str, str2, i, z);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int renameSecureContainer(String str, String str2) throws RemoteException {
                return this.mStorageManager.renameSecureContainer(str, str2);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public String getSecureContainerFilesystemPath(String str) throws RemoteException {
                return this.mStorageManager.getSecureContainerFilesystemPath(str);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int finalizeSecureContainer(String str) throws RemoteException {
                return this.mStorageManager.finalizeSecureContainer(str);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int destroySecureContainer(String str, boolean z) throws RemoteException {
                return this.mStorageManager.destroySecureContainer(str, z);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public boolean isSecureContainerMounted(String str) throws RemoteException {
                return this.mStorageManager.isSecureContainerMounted(str);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int unmountSecureContainer(String str, boolean z) throws RemoteException {
                return this.mStorageManager.unmountSecureContainer(str, z);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public String[] getSecureContainerList() throws RemoteException {
                return this.mStorageManager.getSecureContainerList();
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int fixPermissionsSecureContainer(String str, int i, String str2) throws RemoteException {
                return this.mStorageManager.fixPermissionsSecureContainer(str, i, str2);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public void finishMediaUpdate() throws RemoteException {
                this.mStorageManager.finishMediaUpdate();
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int getUsedSpaceSecureContainer(String str) throws RemoteException {
                return this.mStorageManager.getUsedSpaceSecureContainer(str);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public int trimSecureContainer(String str, int i, String str2) throws RemoteException {
                return this.mStorageManager.trimSecureContainer(str, i, str2);
            }

            @Override // com.samsung.android.core.pm.containerservice.PackageHelperExt.StorageManagerExt
            public StorageVolume[] getVolumeList(int i, String str, int i2) throws RemoteException {
                return this.mStorageManager.getVolumeList(i, str, i2);
            }
        };
    }

    public static IStorageManager getStorageManager() throws RemoteException {
        IBinder service = ServiceManager.getService(AudioParameter.VALUE_MOUNT);
        if (service != null) {
            return IStorageManager.Stub.asInterface(service);
        }
        Log.e(TAG, "Can't get storagemanager service");
        throw new RemoteException("Could not contact storagemanager service");
    }

    public static long calculateInstalledSize(PackageLite packageLite, NativeLibraryHelper.Handle handle, String str) throws IOException {
        return InstallLocationUtils.calculateInstalledSize(packageLite, handle, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057 A[Catch: RemoteException -> 0x005c, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x005c, blocks: (B:3:0x0013, B:7:0x0020, B:9:0x0045, B:12:0x0057, B:14:0x0032), top: B:2:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0045 A[Catch: RemoteException -> 0x005c, TryCatch #0 {RemoteException -> 0x005c, blocks: (B:3:0x0013, B:7:0x0020, B:9:0x0045, B:12:0x0057, B:14:0x0032), top: B:2:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String createSdDir(long r8, java.lang.String r10, java.lang.String r11, int r12, boolean r13) {
        /*
            java.lang.String r0 = "Failed to create secure container "
            double r8 = (double) r8
            r1 = 4607317526788838523(0x3ff07ae147ae147b, double:1.03)
            double r8 = r8 * r1
            long r1 = com.samsung.android.core.pm.containerservice.PackageHelperExt.MB_IN_BYTES
            double r3 = (double) r1
            double r8 = r8 + r3
            double r1 = (double) r1
            double r8 = r8 / r1
            int r8 = (int) r8
            int r3 = r8 + 1
            r8 = 0
            com.samsung.android.core.pm.containerservice.PackageHelperExt$StorageManagerExt r1 = getStorageManagerExt()     // Catch: android.os.RemoteException -> L5c
            boolean r9 = android.os.Environment.isExternalStorageEmulated()     // Catch: android.os.RemoteException -> L5c
            if (r9 != 0) goto L32
            if (r13 != 0) goto L20
            goto L32
        L20:
            java.lang.String r9 = com.samsung.android.core.pm.containerservice.PackageHelperExt.TAG     // Catch: android.os.RemoteException -> L5c
            java.lang.String r2 = "createSdDir with fat"
            android.util.Log.i(r9, r2)     // Catch: android.os.RemoteException -> L5c
            java.lang.String r4 = "fat"
            r2 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            int r9 = r1.createSecureContainer(r2, r3, r4, r5, r6, r7)     // Catch: android.os.RemoteException -> L5c
            goto L43
        L32:
            r2 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            java.lang.String r9 = com.samsung.android.core.pm.containerservice.PackageHelperExt.TAG     // Catch: android.os.RemoteException -> L5c
            java.lang.String r10 = "createSdDir with ext4"
            android.util.Log.i(r9, r10)     // Catch: android.os.RemoteException -> L5c
            java.lang.String r4 = "ext4"
            int r9 = r1.createSecureContainer(r2, r3, r4, r5, r6, r7)     // Catch: android.os.RemoteException -> L5c
        L43:
            if (r9 == 0) goto L57
            java.lang.String r9 = com.samsung.android.core.pm.containerservice.PackageHelperExt.TAG     // Catch: android.os.RemoteException -> L5c
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: android.os.RemoteException -> L5c
            r10.<init>(r0)     // Catch: android.os.RemoteException -> L5c
            r10.append(r2)     // Catch: android.os.RemoteException -> L5c
            java.lang.String r10 = r10.toString()     // Catch: android.os.RemoteException -> L5c
            android.util.Log.e(r9, r10)     // Catch: android.os.RemoteException -> L5c
            return r8
        L57:
            java.lang.String r8 = r1.getSecureContainerPath(r2)     // Catch: android.os.RemoteException -> L5c
            return r8
        L5c:
            java.lang.String r9 = com.samsung.android.core.pm.containerservice.PackageHelperExt.TAG
            java.lang.String r10 = "StorageManagerService running?"
            android.util.Log.e(r9, r10)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.core.pm.containerservice.PackageHelperExt.createSdDir(long, java.lang.String, java.lang.String, int, boolean):java.lang.String");
    }

    public static boolean resizeSdDir(long j, String str, String str2) {
        long j2 = MB_IN_BYTES;
        try {
            if (getStorageManagerExt().resizeSecureContainer(str, ((int) ((j + j2) / j2)) + 1, str2) == 0) {
                return true;
            }
        } catch (RemoteException unused) {
            Log.e(TAG, "StorageManagerService running?");
        }
        Log.e(TAG, "Failed to create secure container " + str);
        return false;
    }

    public static String mountSdDir(String str, String str2, int i) {
        return mountSdDir(str, str2, i, true);
    }

    public static String mountSdDir(String str, String str2, int i, boolean z) {
        try {
            StorageManagerExt storageManagerExt = getStorageManagerExt();
            int mountSecureContainer = storageManagerExt.mountSecureContainer(str, str2, i, z);
            if (mountSecureContainer != 0) {
                Log.i(TAG, "Failed to mount container " + str + ", rc: " + mountSecureContainer);
                return null;
            }
            return storageManagerExt.getSecureContainerPath(str);
        } catch (RemoteException unused) {
            Log.e(TAG, "StorageManagerService running?");
            return null;
        }
    }

    public static boolean unMountSdDir(String str, boolean z) {
        try {
            int unmountSecureContainer = getStorageManagerExt().unmountSecureContainer(str, z);
            if (unmountSecureContainer == 0) {
                return true;
            }
            Log.e(TAG, "Failed to unmount " + str + ", force: " + z + ", rc: " + unmountSecureContainer);
            return false;
        } catch (RemoteException unused) {
            Log.e(TAG, "StorageManagerService running?");
            return false;
        }
    }

    public static boolean renameSdDir(String str, String str2) {
        try {
            int renameSecureContainer = getStorageManagerExt().renameSecureContainer(str, str2);
            if (renameSecureContainer == 0) {
                return true;
            }
            Log.e(TAG, "Failed to rename " + str + " to " + str2 + ", rc: " + renameSecureContainer);
            return false;
        } catch (RemoteException e) {
            Log.i(TAG, "Failed to rename  " + str + " to " + str2 + " with exception " + e);
            return false;
        }
    }

    public static String getSdDir(String str) {
        try {
            return getStorageManagerExt().getSecureContainerPath(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get container path for " + str + " with exception " + e);
            return null;
        }
    }

    public static String getSdFilesystem(String str) {
        try {
            return getStorageManagerExt().getSecureContainerFilesystemPath(str);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get container file system path for " + str + " with exception " + e);
            return null;
        }
    }

    public static boolean finalizeSdDir(String str) {
        try {
            if (getStorageManagerExt().finalizeSecureContainer(str) == 0) {
                return true;
            }
            Log.i(TAG, "Failed to finalize container " + str);
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to finalize container " + str + " with exception " + e);
            return false;
        }
    }

    public static boolean destroySdDir(String str) {
        try {
            if (getStorageManagerExt().destroySecureContainer(str, true) == 0) {
                return true;
            }
            Log.i(TAG, "Failed to destroy container " + str);
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to destroy container " + str + " with exception " + e);
            return false;
        }
    }

    public static String[] getSecureContainerList() {
        try {
            return getStorageManagerExt().getSecureContainerList();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get secure container list with exception " + e);
            return null;
        }
    }

    public static boolean isContainerMounted(String str) {
        try {
            return getStorageManagerExt().isSecureContainerMounted(str);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to find out if container " + str + " mounted");
            return false;
        }
    }

    public static int getUsedSpaceSecureContainer(String str) {
        try {
            return getStorageManagerExt().getUsedSpaceSecureContainer(str);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to find the occupied size of container " + str);
            return -1;
        }
    }

    public static boolean trimSecureContainer(String str, int i, String str2) {
        try {
            if (getStorageManagerExt().trimSecureContainer(str, i, str2) == 0) {
                return true;
            }
            Log.i(TAG, "Failed to trim container " + str);
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to trim container " + str + " with exception " + e);
            return false;
        }
    }

    public static long extractPublicFiles(File file, File file2) throws IOException {
        FileOutputStream fileOutputStream;
        ZipOutputStream zipOutputStream;
        if (file2 == null) {
            fileOutputStream = null;
            zipOutputStream = null;
        } else {
            fileOutputStream = new FileOutputStream(file2);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            Log.d(TAG, "Extracting " + file + " to " + file2);
        }
        try {
            ZipFile zipFile = new ZipFile(file.getAbsolutePath());
            try {
                Iterator it = Collections.list(zipFile.entries()).iterator();
                long j = 0;
                while (it.hasNext()) {
                    ZipEntry zipEntry = (ZipEntry) it.next();
                    String name = zipEntry.getName();
                    if ("AndroidManifest.xml".equals(name) || "resources.arsc".equals(name) || name.startsWith("res/")) {
                        j += zipEntry.getSize();
                        if (file2 != null) {
                            copyZipEntry(zipEntry, zipFile, zipOutputStream);
                        }
                    }
                }
                if (file2 != null) {
                    zipOutputStream.finish();
                    zipOutputStream.flush();
                    FileUtils.sync(fileOutputStream);
                    zipOutputStream.close();
                    FileUtils.setPermissions(file2.getAbsolutePath(), 420, -1, -1);
                }
                return j;
            } finally {
                try {
                    zipFile.close();
                } catch (IOException unused) {
                }
            }
        } finally {
            IoUtils.closeQuietly(zipOutputStream);
        }
    }

    private static void copyZipEntry(ZipEntry zipEntry, ZipFile zipFile, ZipOutputStream zipOutputStream) throws IOException {
        ZipEntry zipEntry2;
        byte[] bArr = new byte[4096];
        if (zipEntry.getMethod() == 0) {
            zipEntry2 = new ZipEntry(zipEntry);
        } else {
            zipEntry2 = new ZipEntry(zipEntry.getName());
        }
        zipOutputStream.putNextEntry(zipEntry2);
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read > 0) {
                    zipOutputStream.write(bArr, 0, read);
                } else {
                    zipOutputStream.flush();
                    return;
                }
            } finally {
                IoUtils.closeQuietly(inputStream);
            }
        }
    }

    public static boolean fixSdPermissions(String str, int i, String str2) {
        try {
            if (getStorageManagerExt().fixPermissionsSecureContainer(str, i, str2) == 0) {
                return true;
            }
            Log.i(TAG, "Failed to fixperms container " + str);
            return false;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to fixperms container " + str + " with exception " + e);
            return false;
        }
    }

    public static StorageVolume[] getVolumeList(int i, String str, int i2) {
        try {
            return getStorageManagerExt().getVolumeList(i, str, i2);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to get the volume list with exception" + e);
            return null;
        }
    }
}
