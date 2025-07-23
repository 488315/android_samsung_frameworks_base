package com.android.internal.content.om;

import android.content.Context;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.content.res.AssetManager;
import android.content.res.Flags;
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

/* loaded from: classes5.dex */
public class OverlayManagerImpl {
    private static final boolean DEBUG = false;
    private static final String FRRO_EXTENSION = ".frro";
    private static final String IDMAP_EXTENSION = ".idmap";
    public static final String SELF_TARGET = ".self_target";
    private static final String TAG = "OverlayManagerImpl";
    private Path mBasePath;
    private final Context mContext;

    private static native void createFrroFile(String str, FabricatedOverlayInternal fabricatedOverlayInternal) throws IOException;

    private static native void createIdmapFile(String str, String str2, String str3, String str4, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws IOException;

    private static native FabricatedOverlayInfo getFabricatedOverlayInfo(String str) throws IOException;

    public OverlayManagerImpl(Context context) {
        this.mContext = (Context) Objects.requireNonNull(context);
        if (!Process.myUserHandle().equals(context.getUser())) {
            throw new SecurityException("Self-Targeting doesn't support multiple user now!");
        }
    }

    private static void cleanExpiredOverlays(Path path, Path path2) {
        try {
            final String path3 = path2.toString();
            final String path4 = path.getFileName().toString();
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() { // from class: com.android.internal.content.om.OverlayManagerImpl.1
                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult preVisitDirectory(Path path5, BasicFileAttributes basicFileAttributes) throws IOException {
                    if (path5.getFileName().toString().equals(path3)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return super.preVisitDirectory((AnonymousClass1) path5, basicFileAttributes);
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult visitFile(Path path5, BasicFileAttributes basicFileAttributes) throws IOException {
                    if (!path5.toFile().delete()) {
                        Log.w(OverlayManagerImpl.TAG, "Failed to delete file " + path5);
                    }
                    return super.visitFile((AnonymousClass1) path5, basicFileAttributes);
                }

                @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
                public FileVisitResult postVisitDirectory(Path path5, IOException iOException) throws IOException {
                    String path6 = path5.getFileName().toString();
                    if (!path6.equals(path3) && !path6.equals(path4) && !path5.toFile().delete()) {
                        Log.w(OverlayManagerImpl.TAG, "Failed to delete dir " + path5);
                    }
                    return super.postVisitDirectory((AnonymousClass1) path5, iOException);
                }
            });
        } catch (IOException e) {
            Log.w(TAG, "Unknown fail " + e);
        }
    }

    public void ensureBaseDir() {
        boolean z = false;
        Path fileName = Path.of(this.mContext.getApplicationInfo().getBaseCodePath(), new String[0]).getParent().getFileName();
        File dir = this.mContext.getDir(SELF_TARGET, 0);
        Preconditions.checkArgument(dir.isDirectory() && dir.exists() && dir.canWrite() && dir.canRead() && dir.canExecute(), "Can't work for this context");
        cleanExpiredOverlays(dir.toPath(), fileName);
        File file = new File(dir, fileName.toString());
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.w(TAG, "Failed to create directory " + file);
            }
            FileUtils.setPermissions(file, 448, -1, -1);
        }
        if (file.isDirectory() && file.exists() && file.canWrite() && file.canRead() && file.canExecute()) {
            z = true;
        }
        Preconditions.checkArgument(z, "Can't create a workspace for this context");
        this.mBasePath = file.toPath();
    }

    private boolean isSameWithTargetSignature(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        String packageName = this.mContext.getPackageName();
        return TextUtils.equals(packageName, str) || packageManager.checkSignatures(packageName, str) == 0;
    }

    public static String checkOverlayNameValid(String str) {
        String str2 = (String) Preconditions.checkStringNotEmpty(str, "overlayName should be neither empty nor null string");
        String validateName = FrameworkParsingPackageUtils.validateName(str2, false, true);
        Preconditions.checkArgument(validateName == null, TextUtils.formatSimple("Invalid overlayName \"%s\". The check result is %s.", str2, validateName));
        return str2;
    }

    private void checkPackageName(String str) {
        Preconditions.checkStringNotEmpty(str);
        Preconditions.checkArgument(TextUtils.equals(this.mContext.getPackageName(), str), TextUtils.formatSimple("UID %d doesn't own the package %s", Integer.valueOf(Process.myUid()), str));
    }

    public void registerFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) throws IOException, PackageManager.NameNotFoundException {
        String str;
        ensureBaseDir();
        Objects.requireNonNull(fabricatedOverlayInternal);
        boolean z = true;
        Preconditions.checkArgument(!((List) Objects.requireNonNull(fabricatedOverlayInternal.entries)).isEmpty(), "overlay entries shouldn't be empty");
        String checkOverlayNameValid = checkOverlayNameValid(fabricatedOverlayInternal.overlayName);
        checkPackageName(fabricatedOverlayInternal.packageName);
        if (Flags.selfTargetingAndroidResourceFrro()) {
            Preconditions.checkStringNotEmpty(fabricatedOverlayInternal.targetPackageName);
        } else {
            checkPackageName(fabricatedOverlayInternal.targetPackageName);
            Preconditions.checkStringNotEmpty(fabricatedOverlayInternal.targetOverlayable, "Target overlayable should be neither null nor empty string.");
        }
        ApplicationInfo applicationInfo = this.mContext.getApplicationInfo();
        if (Flags.selfTargetingAndroidResourceFrro() && TextUtils.equals(fabricatedOverlayInternal.targetPackageName, "android")) {
            str = AssetManager.FRAMEWORK_APK_PATH;
        } else {
            str = (String) Preconditions.checkStringNotEmpty(applicationInfo.getBaseCodePath());
        }
        Path resolve = this.mBasePath.resolve(checkOverlayNameValid + FRRO_EXTENSION);
        Path resolve2 = this.mBasePath.resolve(checkOverlayNameValid + IDMAP_EXTENSION);
        createFrroFile(resolve.toString(), fabricatedOverlayInternal);
        try {
            String path = resolve.toString();
            String path2 = resolve2.toString();
            if (!applicationInfo.isSystemApp() && !applicationInfo.isSystemExt()) {
                z = false;
            }
            createIdmapFile(str, path, path2, checkOverlayNameValid, z, applicationInfo.isVendor(), applicationInfo.isProduct(), isSameWithTargetSignature(fabricatedOverlayInternal.targetPackageName), applicationInfo.isOdm(), applicationInfo.isOem());
        } catch (IOException e) {
            if (!resolve.toFile().delete()) {
                Log.w(TAG, "Failed to delete file " + resolve);
                throw e;
            }
            throw e;
        }
    }

    public void unregisterFabricatedOverlay(String str) {
        ensureBaseDir();
        checkOverlayNameValid(str);
        Path resolve = this.mBasePath.resolve(str + FRRO_EXTENSION);
        Path resolve2 = this.mBasePath.resolve(str + IDMAP_EXTENSION);
        if (!resolve.toFile().delete()) {
            Log.w(TAG, "Failed to delete file " + resolve);
        }
        if (resolve2.toFile().delete()) {
            return;
        }
        Log.w(TAG, "Failed to delete file " + resolve2);
    }

    public void commit(OverlayManagerTransaction overlayManagerTransaction) throws PackageManager.NameNotFoundException, IOException {
        Objects.requireNonNull(overlayManagerTransaction);
        Iterator<OverlayManagerTransaction.Request> requests = overlayManagerTransaction.getRequests();
        while (requests.hasNext()) {
            OverlayManagerTransaction.Request next = requests.next();
            if (next.type == 2) {
                FabricatedOverlayInternal fabricatedOverlayInternal = (FabricatedOverlayInternal) Objects.requireNonNull((FabricatedOverlayInternal) next.extras.getParcelable(OverlayManagerTransaction.Request.BUNDLE_FABRICATED_OVERLAY, FabricatedOverlayInternal.class));
                if (TextUtils.isEmpty(fabricatedOverlayInternal.packageName)) {
                    fabricatedOverlayInternal.packageName = this.mContext.getPackageName();
                } else if (!TextUtils.equals(fabricatedOverlayInternal.packageName, this.mContext.getPackageName())) {
                    throw new IllegalArgumentException("Unknown package name in transaction");
                }
                registerFabricatedOverlay(fabricatedOverlayInternal);
            } else if (next.type == 3) {
                unregisterFabricatedOverlay(((OverlayIdentifier) Objects.requireNonNull(next.overlay)).getOverlayName());
            } else {
                throw new IllegalArgumentException("Unknown request in transaction " + next);
            }
        }
    }

    public List<OverlayInfo> getOverlayInfosForTarget(String str) {
        ensureBaseDir();
        File[] listFiles = this.mBasePath.toFile().listFiles(new FilenameFilter() { // from class: com.android.internal.content.om.OverlayManagerImpl$$ExternalSyntheticLambda0
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str2) {
                return OverlayManagerImpl.lambda$getOverlayInfosForTarget$0(file, str2);
            }
        });
        ArrayList arrayList = new ArrayList();
        for (File file : listFiles) {
            try {
                FabricatedOverlayInfo fabricatedOverlayInfo = getFabricatedOverlayInfo(file.getAbsolutePath());
                if (TextUtils.equals(str, fabricatedOverlayInfo.targetPackageName)) {
                    arrayList.add(new OverlayInfo(fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName, fabricatedOverlayInfo.targetPackageName, fabricatedOverlayInfo.targetOverlayable, null, file.getAbsolutePath(), 3, UserHandle.myUserId(), Integer.MAX_VALUE, true, true));
                }
            } catch (IOException unused) {
                Log.w(TAG, "can't load " + file);
            }
        }
        return arrayList;
    }

    static /* synthetic */ boolean lambda$getOverlayInfosForTarget$0(File file, String str) {
        if (!str.endsWith(FRRO_EXTENSION)) {
            return false;
        }
        return new File(file, str.substring(0, str.length() - 5) + IDMAP_EXTENSION).exists();
    }
}
