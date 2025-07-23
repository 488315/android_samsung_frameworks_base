package com.android.internal.content;

import android.content.Context;
import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.os.Build;
import android.os.IBinder;
import android.os.SELinux;
import android.os.ServiceManager;
import android.os.incremental.IIncrementalService;
import android.os.incremental.IncrementalManager;
import android.os.incremental.IncrementalStorage;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Slog;
import dalvik.system.CloseGuard;
import dalvik.system.VMRuntime;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/* loaded from: classes5.dex */
public class NativeLibraryHelper {
    private static final int BITCODE_PRESENT = 1;
    public static final String CLEAR_ABI_OVERRIDE = "-";
    private static final boolean DEBUG_NATIVE = false;
    public static final String LIB64_DIR_NAME = "lib64";
    public static final String LIB_DIR_NAME = "lib";
    private static final String TAG = "NativeHelper";

    private static native int hasRenderscriptBitcode(long j);

    private static native int nativeCheckAlignment(long j, String str, String str2, boolean z, boolean z2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClose(long j);

    private static native int nativeCopyNativeBinaries(long j, String str, String str2, boolean z, boolean z2, boolean z3);

    private static native int nativeFindSupportedAbi(long j, String[] strArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenApk(String str);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeOpenApkFd(FileDescriptor fileDescriptor, String str);

    private static native long nativeSumNativeBinaries(long j, String str);

    public static class Handle implements Closeable {
        final long[] apkHandles;
        final String[] apkPaths;
        final boolean debuggable;
        final boolean extractNativeLibs;
        private volatile boolean mClosed;
        private final CloseGuard mGuard;
        final boolean multiArch;
        final boolean pageSizeCompatDisabled;

        public static Handle create(File file) throws IOException {
            ParseResult<PackageLite> parsePackageLite = ApkLiteParseUtils.parsePackageLite(ParseTypeImpl.forDefaultParsing().reset(), file, 0);
            if (parsePackageLite.isError()) {
                throw new IOException("Failed to parse package: " + file, parsePackageLite.getException());
            }
            return create(parsePackageLite.getResult());
        }

        public static Handle create(PackageLite packageLite) throws IOException {
            return create(packageLite.getAllApkPaths(), packageLite.isMultiArch(), packageLite.isExtractNativeLibs(), packageLite.isDebuggable(), packageLite.getPageSizeCompat() == 64);
        }

        public static Handle create(List<String> list, boolean z, boolean z2, boolean z3, boolean z4) throws IOException {
            int size = list.size();
            String[] strArr = new String[size];
            long[] jArr = new long[size];
            for (int i = 0; i < size; i++) {
                String str = list.get(i);
                strArr[i] = str;
                long nativeOpenApk = NativeLibraryHelper.nativeOpenApk(str);
                jArr[i] = nativeOpenApk;
                if (nativeOpenApk == 0) {
                    for (int i2 = 0; i2 < i; i2++) {
                        NativeLibraryHelper.nativeClose(jArr[i2]);
                    }
                    throw new IOException("Unable to open APK: " + str);
                }
            }
            return new Handle(strArr, jArr, z, z2, z3, z4);
        }

        public static Handle createFd(PackageLite packageLite, FileDescriptor fileDescriptor) throws IOException {
            String baseApkPath = packageLite.getBaseApkPath();
            long[] jArr = {NativeLibraryHelper.nativeOpenApkFd(fileDescriptor, baseApkPath)};
            if (jArr[0] == 0) {
                throw new IOException("Unable to open APK " + baseApkPath + " from fd " + fileDescriptor);
            }
            return new Handle(new String[]{baseApkPath}, jArr, packageLite.isMultiArch(), packageLite.isExtractNativeLibs(), packageLite.isDebuggable(), packageLite.getPageSizeCompat() == 64);
        }

        Handle(String[] strArr, long[] jArr, boolean z, boolean z2, boolean z3, boolean z4) {
            CloseGuard closeGuard = CloseGuard.get();
            this.mGuard = closeGuard;
            this.apkPaths = strArr;
            this.apkHandles = jArr;
            this.multiArch = z;
            this.extractNativeLibs = z2;
            this.debuggable = z3;
            this.pageSizeCompatDisabled = z4;
            closeGuard.open("close");
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            for (long j : this.apkHandles) {
                NativeLibraryHelper.nativeClose(j);
            }
            this.mGuard.close();
            this.mClosed = true;
        }

        protected void finalize() throws Throwable {
            CloseGuard closeGuard = this.mGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            try {
                if (!this.mClosed) {
                    close();
                }
            } finally {
                super.finalize();
            }
        }
    }

    private static long sumNativeBinaries(Handle handle, String str) {
        long j = 0;
        for (long j2 : handle.apkHandles) {
            j += nativeSumNativeBinaries(j2, str);
        }
        return j;
    }

    public static int copyNativeBinaries(Handle handle, File file, String str) {
        long[] jArr = handle.apkHandles;
        int length = jArr.length;
        int i = 0;
        while (i < length) {
            String str2 = str;
            int nativeCopyNativeBinaries = nativeCopyNativeBinaries(jArr[i], file.getPath(), str2, handle.extractNativeLibs, handle.debuggable, handle.pageSizeCompatDisabled);
            if (nativeCopyNativeBinaries != 1) {
                return nativeCopyNativeBinaries;
            }
            i++;
            str = str2;
        }
        return 1;
    }

    public static int findSupportedAbi(Handle handle, String[] strArr) {
        int i = -114;
        for (long j : handle.apkHandles) {
            int nativeFindSupportedAbi = nativeFindSupportedAbi(j, strArr);
            if (nativeFindSupportedAbi != -114) {
                if (nativeFindSupportedAbi != -113) {
                    if (nativeFindSupportedAbi < 0) {
                        return nativeFindSupportedAbi;
                    }
                    if (i < 0 || nativeFindSupportedAbi < i) {
                        i = nativeFindSupportedAbi;
                    }
                } else if (i < 0) {
                    i = -113;
                }
            }
        }
        return i;
    }

    public static void removeNativeBinariesLI(String str) {
        if (str == null) {
            return;
        }
        removeNativeBinariesFromDirLI(new File(str), false);
    }

    public static void removeNativeBinariesFromDirLI(File file, boolean z) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isDirectory()) {
                        removeNativeBinariesFromDirLI(listFiles[i], true);
                    } else if (!listFiles[i].delete()) {
                        Slog.w(TAG, "Could not delete native binary: " + listFiles[i].getPath());
                    }
                }
            }
            if (!z || file.delete()) {
                return;
            }
            Slog.w(TAG, "Could not delete native binary directory: " + file.getPath());
        }
    }

    public static void createNativeLibrarySubdir(File file) throws IOException {
        if (!file.isDirectory()) {
            file.delete();
            if (!file.mkdir()) {
                throw new IOException("Cannot create " + file.getPath());
            }
            try {
                Os.chmod(file.getPath(), OsConstants.S_IRWXU | OsConstants.S_IRGRP | OsConstants.S_IXGRP | OsConstants.S_IROTH | OsConstants.S_IXOTH);
                return;
            } catch (ErrnoException e) {
                throw new IOException("Cannot chmod native library directory " + file.getPath(), e);
            }
        }
        if (SELinux.restorecon(file)) {
            return;
        }
        throw new IOException("Cannot set SELinux context for " + file.getPath());
    }

    private static long sumNativeBinariesForSupportedAbi(Handle handle, String[] strArr) {
        int findSupportedAbi = findSupportedAbi(handle, strArr);
        if (findSupportedAbi >= 0) {
            return sumNativeBinaries(handle, strArr[findSupportedAbi]);
        }
        return 0L;
    }

    public static int copyNativeBinariesForSupportedAbi(Handle handle, File file, String[] strArr, boolean z, boolean z2) throws IOException {
        int findSupportedAbi = findSupportedAbi(handle, strArr);
        if (findSupportedAbi >= 0) {
            String str = strArr[findSupportedAbi];
            File file2 = z ? new File(file, VMRuntime.getInstructionSet(str)) : file;
            if (z2) {
                int incrementalConfigureNativeBinariesForSupportedAbi = incrementalConfigureNativeBinariesForSupportedAbi(handle, file2, str);
                if (incrementalConfigureNativeBinariesForSupportedAbi != 1) {
                    return incrementalConfigureNativeBinariesForSupportedAbi;
                }
            } else {
                createNativeLibrarySubdir(file);
                if (file2 != file) {
                    createNativeLibrarySubdir(file2);
                }
                int copyNativeBinaries = copyNativeBinaries(handle, file2, str);
                if (copyNativeBinaries != 1) {
                    return copyNativeBinaries;
                }
            }
        }
        return findSupportedAbi;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0070 A[Catch: IOException -> 0x00a4, TryCatch #0 {IOException -> 0x00a4, blocks: (B:3:0x0008, B:7:0x0013, B:9:0x0019, B:10:0x001e, B:12:0x0025, B:16:0x0031, B:19:0x0041, B:21:0x0046, B:25:0x0052, B:28:0x0062, B:33:0x0070, B:34:0x0077, B:37:0x007e, B:39:0x0084, B:40:0x0086, B:43:0x008e, B:45:0x0075), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0075 A[Catch: IOException -> 0x00a4, TryCatch #0 {IOException -> 0x00a4, blocks: (B:3:0x0008, B:7:0x0013, B:9:0x0019, B:10:0x001e, B:12:0x0025, B:16:0x0031, B:19:0x0041, B:21:0x0046, B:25:0x0052, B:28:0x0062, B:33:0x0070, B:34:0x0077, B:37:0x007e, B:39:0x0084, B:40:0x0086, B:43:0x008e, B:45:0x0075), top: B:2:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int copyNativeBinariesWithOverride(com.android.internal.content.NativeLibraryHelper.Handle r8, java.io.File r9, java.lang.String r10, boolean r11) {
        /*
            java.lang.String r0 = "NativeHelper"
            java.lang.String r1 = "Failure copying 64 bit native libraries; copyRet="
            java.lang.String r2 = "Failure copying native libraries [errorCode="
            java.lang.String r3 = "Failure copying 32 bit native libraries; copyRet="
            boolean r4 = r8.multiArch     // Catch: java.io.IOException -> La4
            java.lang.String r5 = "-"
            r6 = -114(0xffffffffffffff8e, float:NaN)
            r7 = 1
            if (r4 == 0) goto L62
            if (r10 == 0) goto L1e
            boolean r10 = r5.equals(r10)     // Catch: java.io.IOException -> La4
            if (r10 != 0) goto L1e
            java.lang.String r10 = "Ignoring abiOverride for multi arch application."
            android.util.Slog.w(r0, r10)     // Catch: java.io.IOException -> La4
        L1e:
            java.lang.String[] r10 = android.os.Build.SUPPORTED_32_BIT_ABIS     // Catch: java.io.IOException -> La4
            int r10 = r10.length     // Catch: java.io.IOException -> La4
            r2 = -113(0xffffffffffffff8f, float:NaN)
            if (r10 <= 0) goto L41
            java.lang.String[] r10 = android.os.Build.SUPPORTED_32_BIT_ABIS     // Catch: java.io.IOException -> La4
            int r10 = copyNativeBinariesForSupportedAbi(r8, r9, r10, r7, r11)     // Catch: java.io.IOException -> La4
            if (r10 >= 0) goto L41
            if (r10 == r6) goto L41
            if (r10 == r2) goto L41
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.io.IOException -> La4
            r8.<init>(r3)     // Catch: java.io.IOException -> La4
            r8.append(r10)     // Catch: java.io.IOException -> La4
            java.lang.String r8 = r8.toString()     // Catch: java.io.IOException -> La4
            android.util.Slog.w(r0, r8)     // Catch: java.io.IOException -> La4
            return r10
        L41:
            java.lang.String[] r10 = android.os.Build.SUPPORTED_64_BIT_ABIS     // Catch: java.io.IOException -> La4
            int r10 = r10.length     // Catch: java.io.IOException -> La4
            if (r10 <= 0) goto La3
            java.lang.String[] r10 = android.os.Build.SUPPORTED_64_BIT_ABIS     // Catch: java.io.IOException -> La4
            int r8 = copyNativeBinariesForSupportedAbi(r8, r9, r10, r7, r11)     // Catch: java.io.IOException -> La4
            if (r8 >= 0) goto La3
            if (r8 == r6) goto La3
            if (r8 == r2) goto La3
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.io.IOException -> La4
            r9.<init>(r1)     // Catch: java.io.IOException -> La4
            r9.append(r8)     // Catch: java.io.IOException -> La4
            java.lang.String r9 = r9.toString()     // Catch: java.io.IOException -> La4
            android.util.Slog.w(r0, r9)     // Catch: java.io.IOException -> La4
            return r8
        L62:
            boolean r1 = r5.equals(r10)     // Catch: java.io.IOException -> La4
            r3 = 0
            if (r1 == 0) goto L6a
            goto L6d
        L6a:
            if (r10 == 0) goto L6d
            goto L6e
        L6d:
            r10 = r3
        L6e:
            if (r10 == 0) goto L75
            java.lang.String[] r1 = new java.lang.String[]{r10}     // Catch: java.io.IOException -> La4
            goto L77
        L75:
            java.lang.String[] r1 = android.os.Build.SUPPORTED_ABIS     // Catch: java.io.IOException -> La4
        L77:
            java.lang.String[] r3 = android.os.Build.SUPPORTED_64_BIT_ABIS     // Catch: java.io.IOException -> La4
            int r3 = r3.length     // Catch: java.io.IOException -> La4
            if (r3 <= 0) goto L86
            if (r10 != 0) goto L86
            boolean r10 = hasRenderscriptBitcode(r8)     // Catch: java.io.IOException -> La4
            if (r10 == 0) goto L86
            java.lang.String[] r1 = android.os.Build.SUPPORTED_32_BIT_ABIS     // Catch: java.io.IOException -> La4
        L86:
            int r8 = copyNativeBinariesForSupportedAbi(r8, r9, r1, r7, r11)     // Catch: java.io.IOException -> La4
            if (r8 >= 0) goto La3
            if (r8 == r6) goto La3
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.io.IOException -> La4
            r9.<init>(r2)     // Catch: java.io.IOException -> La4
            r9.append(r8)     // Catch: java.io.IOException -> La4
            java.lang.String r10 = "]"
            r9.append(r10)     // Catch: java.io.IOException -> La4
            java.lang.String r9 = r9.toString()     // Catch: java.io.IOException -> La4
            android.util.Slog.w(r0, r9)     // Catch: java.io.IOException -> La4
            return r8
        La3:
            return r7
        La4:
            r8 = move-exception
            java.lang.String r9 = "Copying native libraries failed"
            android.util.Slog.e(r0, r9, r8)
            r8 = -110(0xffffffffffffff92, float:NaN)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.content.NativeLibraryHelper.copyNativeBinariesWithOverride(com.android.internal.content.NativeLibraryHelper$Handle, java.io.File, java.lang.String, boolean):int");
    }

    public static int checkAlignmentForCompatMode(Handle handle, String str, boolean z, String str2) {
        int findSupportedAbi = findSupportedAbi(handle, Build.SUPPORTED_64_BIT_ABIS);
        if (findSupportedAbi < 0) {
            return -1;
        }
        String instructionSet = VMRuntime.getInstructionSet(Build.SUPPORTED_64_BIT_ABIS[findSupportedAbi]);
        if (z) {
            str = str + "/" + instructionSet;
        }
        String str3 = str;
        int i = 0;
        for (long j : handle.apkHandles) {
            int nativeCheckAlignment = nativeCheckAlignment(j, str3, Build.SUPPORTED_64_BIT_ABIS[findSupportedAbi], handle.extractNativeLibs, handle.debuggable);
            if (nativeCheckAlignment == -1) {
                return nativeCheckAlignment;
            }
            i |= nativeCheckAlignment;
        }
        return i;
    }

    public static long sumNativeBinariesWithOverride(Handle handle, String str) throws IOException {
        if (handle.multiArch) {
            if (str != null && !CLEAR_ABI_OVERRIDE.equals(str)) {
                Slog.w(TAG, "Ignoring abiOverride for multi arch application.");
            }
            long sumNativeBinariesForSupportedAbi = Build.SUPPORTED_32_BIT_ABIS.length > 0 ? sumNativeBinariesForSupportedAbi(handle, Build.SUPPORTED_32_BIT_ABIS) : 0L;
            return Build.SUPPORTED_64_BIT_ABIS.length > 0 ? sumNativeBinariesForSupportedAbi + sumNativeBinariesForSupportedAbi(handle, Build.SUPPORTED_64_BIT_ABIS) : sumNativeBinariesForSupportedAbi;
        }
        if (CLEAR_ABI_OVERRIDE.equals(str) || str == null) {
            str = null;
        }
        String[] strArr = str != null ? new String[]{str} : Build.SUPPORTED_ABIS;
        if (Build.SUPPORTED_64_BIT_ABIS.length > 0 && str == null && hasRenderscriptBitcode(handle)) {
            strArr = Build.SUPPORTED_32_BIT_ABIS;
        }
        return sumNativeBinariesForSupportedAbi(handle, strArr);
    }

    private static int incrementalConfigureNativeBinariesForSupportedAbi(Handle handle, File file, String str) {
        String[] strArr = handle.apkPaths;
        if (strArr == null || strArr.length == 0) {
            Slog.e(TAG, "No apks to extract native libraries from.");
            return -110;
        }
        IBinder service = ServiceManager.getService(Context.INCREMENTAL_SERVICE);
        if (service == null) {
            return -110;
        }
        IncrementalManager incrementalManager = new IncrementalManager(IIncrementalService.Stub.asInterface(service));
        File parentFile = new File(strArr[0]).getParentFile();
        IncrementalStorage openStorage = incrementalManager.openStorage(parentFile.getAbsolutePath());
        if (openStorage == null) {
            Slog.e(TAG, "Failed to find incremental storage");
            return -110;
        }
        String relativePath = getRelativePath(parentFile, file);
        if (relativePath == null) {
            return -110;
        }
        for (String str2 : strArr) {
            if (!openStorage.configureNativeBinaries(str2, relativePath, str, handle.extractNativeLibs)) {
                return -110;
            }
        }
        return 1;
    }

    private static String getRelativePath(File file, File file2) {
        try {
            Path relativize = file.toPath().relativize(file2.toPath());
            if (relativize.toString().isEmpty()) {
                return "";
            }
            return relativize.toString();
        } catch (IllegalArgumentException unused) {
            Slog.e(TAG, "Failed to find relative path between: " + file.getAbsolutePath() + " and: " + file2.getAbsolutePath());
            return null;
        }
    }

    public static boolean hasRenderscriptBitcode(Handle handle) throws IOException {
        for (long j : handle.apkHandles) {
            int hasRenderscriptBitcode = hasRenderscriptBitcode(j);
            if (hasRenderscriptBitcode < 0) {
                throw new IOException("Error scanning APK, code: " + hasRenderscriptBitcode);
            }
            if (hasRenderscriptBitcode == 1) {
                return true;
            }
        }
        return false;
    }
}
