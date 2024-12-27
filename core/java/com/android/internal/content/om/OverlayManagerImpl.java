package com.android.internal.content.om;

import android.content.Context;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.pm.PackageManager;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.os.FabricatedOverlayInfo;
import android.os.FabricatedOverlayInternal;
import android.os.FileUtils;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.Preconditions;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class OverlayManagerImpl {
    private static final boolean DEBUG = false;
    private static final String FRRO_EXTENSION = ".frro";
    private static final String IDMAP_EXTENSION = ".idmap";
    public static final String SELF_TARGET = ".self_target";
    private static final String TAG = "OverlayManagerImpl";
    private Path mBasePath;
    private final Context mContext;

    private static native void createFrroFile(
            String str, FabricatedOverlayInternal fabricatedOverlayInternal) throws IOException;

    private static native void createIdmapFile(
            String str,
            String str2,
            String str3,
            String str4,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6)
            throws IOException;

    private static native FabricatedOverlayInfo getFabricatedOverlayInfo(String str)
            throws IOException;

    public OverlayManagerImpl(Context context) {
        this.mContext = (Context) Objects.requireNonNull(context);
        if (!Process.myUserHandle().equals(context.getUser())) {
            throw new SecurityException("Self-Targeting doesn't support multiple user now!");
        }
    }

    private static void cleanExpiredOverlays(
            Path selfTargetingBasePath, Path folderForCurrentBaseApk) {
        try {
            final String currentBaseFolder = folderForCurrentBaseApk.toString();
            final String selfTargetingDir = selfTargetingBasePath.getFileName().toString();
            Files.walkFileTree(
                    selfTargetingBasePath,
                    new SimpleFileVisitor<
                            Path>() { // from class:
                                      // com.android.internal.content.om.OverlayManagerImpl.1
                        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                        public FileVisitResult preVisitDirectory(
                                Path dir, BasicFileAttributes attrs) throws IOException {
                            String fileName = dir.getFileName().toString();
                            if (fileName.equals(currentBaseFolder)) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                            return super.preVisitDirectory((AnonymousClass1) dir, attrs);
                        }

                        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                throws IOException {
                            if (!file.toFile().delete()) {
                                Log.w(OverlayManagerImpl.TAG, "Failed to delete file " + file);
                            }
                            return super.visitFile((AnonymousClass1) file, attrs);
                        }

                        @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                                throws IOException {
                            String fileName = dir.getFileName().toString();
                            if (!fileName.equals(currentBaseFolder)
                                    && !fileName.equals(selfTargetingDir)
                                    && !dir.toFile().delete()) {
                                Log.w(OverlayManagerImpl.TAG, "Failed to delete dir " + dir);
                            }
                            return super.postVisitDirectory((AnonymousClass1) dir, exc);
                        }
                    });
        } catch (IOException e) {
            Log.w(TAG, "Unknown fail " + e);
        }
    }

    public void ensureBaseDir() {
        String baseApkPath = this.mContext.getApplicationInfo().getBaseCodePath();
        boolean z = false;
        Path baseApkFolderName = Path.of(baseApkPath, new String[0]).getParent().getFileName();
        File selfTargetingBaseFile = this.mContext.getDir(SELF_TARGET, 0);
        Preconditions.checkArgument(
                selfTargetingBaseFile.isDirectory()
                        && selfTargetingBaseFile.exists()
                        && selfTargetingBaseFile.canWrite()
                        && selfTargetingBaseFile.canRead()
                        && selfTargetingBaseFile.canExecute(),
                "Can't work for this context");
        cleanExpiredOverlays(selfTargetingBaseFile.toPath(), baseApkFolderName);
        File baseFile = new File(selfTargetingBaseFile, baseApkFolderName.toString());
        if (!baseFile.exists()) {
            if (!baseFile.mkdirs()) {
                Log.w(TAG, "Failed to create directory " + baseFile);
            }
            FileUtils.setPermissions(baseFile, 448, -1, -1);
        }
        if (baseFile.isDirectory()
                && baseFile.exists()
                && baseFile.canWrite()
                && baseFile.canRead()
                && baseFile.canExecute()) {
            z = true;
        }
        Preconditions.checkArgument(z, "Can't create a workspace for this context");
        this.mBasePath = baseFile.toPath();
    }

    private boolean isSameWithTargetSignature(String targetPackage) {
        PackageManager packageManager = this.mContext.getPackageManager();
        String packageName = this.mContext.getPackageName();
        return TextUtils.equals(packageName, targetPackage)
                || packageManager.checkSignatures(packageName, targetPackage) == 0;
    }

    public static String checkOverlayNameValid(String name) {
        String overlayName =
                (String)
                        Preconditions.checkStringNotEmpty(
                                name, "overlayName should be neither empty nor null string");
        String checkOverlayNameResult =
                FrameworkParsingPackageUtils.validateName(overlayName, false, true);
        Preconditions.checkArgument(
                checkOverlayNameResult == null,
                TextUtils.formatSimple(
                        "Invalid overlayName \"%s\". The check result is %s.",
                        overlayName, checkOverlayNameResult));
        return overlayName;
    }

    private void checkPackageName(String packageName) {
        Preconditions.checkStringNotEmpty(packageName);
        Preconditions.checkArgument(
                TextUtils.equals(this.mContext.getPackageName(), packageName),
                TextUtils.formatSimple(
                        "UID %d doesn't own the package %s",
                        Integer.valueOf(Process.myUid()), packageName));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void registerFabricatedOverlay(android.os.FabricatedOverlayInternal r20)
            throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r19.ensureBaseDir()
            java.util.Objects.requireNonNull(r20)
            java.util.List<android.os.FabricatedOverlayInternalEntry> r0 = r2.entries
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r3 = r0
            java.util.List r3 = (java.util.List) r3
            boolean r0 = r3.isEmpty()
            r4 = 1
            r0 = r0 ^ r4
            java.lang.String r5 = "overlay entries shouldn't be empty"
            com.android.internal.util.Preconditions.checkArgument(r0, r5)
            java.lang.String r0 = r2.overlayName
            java.lang.String r15 = checkOverlayNameValid(r0)
            java.lang.String r0 = r2.packageName
            r1.checkPackageName(r0)
            java.lang.String r0 = r2.targetPackageName
            r1.checkPackageName(r0)
            java.lang.String r0 = r2.targetOverlayable
            java.lang.String r5 = "Target overlayable should be neither null nor empty string."
            com.android.internal.util.Preconditions.checkStringNotEmpty(r0, r5)
            android.content.Context r0 = r1.mContext
            android.content.pm.ApplicationInfo r16 = r0.getApplicationInfo()
            java.lang.String r0 = r16.getBaseCodePath()
            java.lang.CharSequence r0 = com.android.internal.util.Preconditions.checkStringNotEmpty(r0)
            r17 = r0
            java.lang.String r17 = (java.lang.String) r17
            java.nio.file.Path r0 = r1.mBasePath
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r15)
            java.lang.String r6 = ".frro"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.nio.file.Path r14 = r0.resolve(r5)
            java.nio.file.Path r0 = r1.mBasePath
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r15)
            java.lang.String r6 = ".idmap"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.nio.file.Path r18 = r0.resolve(r5)
            java.lang.String r0 = r14.toString()
            createFrroFile(r0, r2)
            java.lang.String r6 = r14.toString()     // Catch: java.io.IOException -> Lc0
            java.lang.String r7 = r18.toString()     // Catch: java.io.IOException -> Lc0
            boolean r0 = r16.isSystemApp()     // Catch: java.io.IOException -> Lc0
            if (r0 != 0) goto L9d
            boolean r0 = r16.isSystemExt()     // Catch: java.io.IOException -> L9a
            if (r0 == 0) goto L98
            goto L9d
        L98:
            r4 = 0
            goto L9d
        L9a:
            r0 = move-exception
            r4 = r14
            goto Lc2
        L9d:
            r9 = r4
            boolean r10 = r16.isVendor()     // Catch: java.io.IOException -> Lc0
            boolean r11 = r16.isProduct()     // Catch: java.io.IOException -> Lc0
            java.lang.String r0 = r2.targetPackageName     // Catch: java.io.IOException -> Lc0
            boolean r12 = r1.isSameWithTargetSignature(r0)     // Catch: java.io.IOException -> Lc0
            boolean r13 = r16.isOdm()     // Catch: java.io.IOException -> Lc0
            boolean r0 = r16.isOem()     // Catch: java.io.IOException -> Lc0
            r5 = r17
            r8 = r15
            r4 = r14
            r14 = r0
            createIdmapFile(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch: java.io.IOException -> Lbe
            return
        Lbe:
            r0 = move-exception
            goto Lc2
        Lc0:
            r0 = move-exception
            r4 = r14
        Lc2:
            java.io.File r5 = r4.toFile()
            boolean r5 = r5.delete()
            if (r5 != 0) goto Le4
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Failed to delete file "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "OverlayManagerImpl"
            android.util.Log.w(r6, r5)
        Le4:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.content.om.OverlayManagerImpl.registerFabricatedOverlay(android.os.FabricatedOverlayInternal):void");
    }

    public void unregisterFabricatedOverlay(String overlayName) {
        ensureBaseDir();
        checkOverlayNameValid(overlayName);
        Path frroPath = this.mBasePath.resolve(overlayName + FRRO_EXTENSION);
        Path idmapPath = this.mBasePath.resolve(overlayName + IDMAP_EXTENSION);
        if (!frroPath.toFile().delete()) {
            Log.w(TAG, "Failed to delete file " + frroPath);
        }
        if (!idmapPath.toFile().delete()) {
            Log.w(TAG, "Failed to delete file " + idmapPath);
        }
    }

    public void commit(OverlayManagerTransaction transaction)
            throws PackageManager.NameNotFoundException, IOException {
        Objects.requireNonNull(transaction);
        Iterator<OverlayManagerTransaction.Request> it = transaction.getRequests();
        while (it.hasNext()) {
            OverlayManagerTransaction.Request request = it.next();
            if (request.type == 2) {
                FabricatedOverlayInternal fabricatedOverlayInternal =
                        (FabricatedOverlayInternal)
                                Objects.requireNonNull(
                                        (FabricatedOverlayInternal)
                                                request.extras.getParcelable(
                                                        OverlayManagerTransaction.Request
                                                                .BUNDLE_FABRICATED_OVERLAY,
                                                        FabricatedOverlayInternal.class));
                if (TextUtils.isEmpty(fabricatedOverlayInternal.packageName)) {
                    fabricatedOverlayInternal.packageName = this.mContext.getPackageName();
                } else if (!TextUtils.equals(
                        fabricatedOverlayInternal.packageName, this.mContext.getPackageName())) {
                    throw new IllegalArgumentException("Unknown package name in transaction");
                }
                registerFabricatedOverlay(fabricatedOverlayInternal);
            } else if (request.type == 3) {
                OverlayIdentifier overlayIdentifier =
                        (OverlayIdentifier) Objects.requireNonNull(request.overlay);
                unregisterFabricatedOverlay(overlayIdentifier.getOverlayName());
            } else {
                throw new IllegalArgumentException("Unknown request in transaction " + request);
            }
        }
    }

    public List<OverlayInfo> getOverlayInfosForTarget(String targetPackage) {
        ensureBaseDir();
        File base = this.mBasePath.toFile();
        File[] frroFiles =
                base.listFiles(
                        new FilenameFilter() { // from class:
                                               // com.android.internal.content.om.OverlayManagerImpl$$ExternalSyntheticLambda0
                            @Override // java.io.FilenameFilter
                            public final boolean accept(File file, String str) {
                                return OverlayManagerImpl.lambda$getOverlayInfosForTarget$0(
                                        file, str);
                            }
                        });
        ArrayList<OverlayInfo> overlayInfos = new ArrayList<>();
        for (File file : frroFiles) {
            try {
                FabricatedOverlayInfo fabricatedOverlayInfo =
                        getFabricatedOverlayInfo(file.getAbsolutePath());
                if (TextUtils.equals(targetPackage, fabricatedOverlayInfo.targetPackageName)) {
                    OverlayInfo overlayInfo =
                            new OverlayInfo(
                                    fabricatedOverlayInfo.packageName,
                                    fabricatedOverlayInfo.overlayName,
                                    fabricatedOverlayInfo.targetPackageName,
                                    fabricatedOverlayInfo.targetOverlayable,
                                    null,
                                    file.getAbsolutePath(),
                                    3,
                                    UserHandle.myUserId(),
                                    Integer.MAX_VALUE,
                                    true,
                                    true);
                    overlayInfos.add(overlayInfo);
                }
            } catch (IOException e) {
                Log.w(TAG, "can't load " + file);
            }
        }
        return overlayInfos;
    }

    static /* synthetic */ boolean lambda$getOverlayInfosForTarget$0(File dir, String name) {
        if (!name.endsWith(FRRO_EXTENSION)) {
            return false;
        }
        String idmapFileName =
                name.substring(0, name.length() - FRRO_EXTENSION.length()) + IDMAP_EXTENSION;
        File idmapFile = new File(dir, idmapFileName);
        return idmapFile.exists();
    }
}
