package com.android.server.pm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstantAppInfo;
import android.content.pm.PermissionInfo;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.permission.PermissionManager;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.PackageUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;

import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.server.pm.parsing.PackageInfoUtils;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateInternal;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedSparseArray;
import com.android.server.utils.WatchedSparseBooleanArray;
import com.android.server.utils.Watcher;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class InstantAppRegistry implements Watchable, Snappable {
    public final Context mContext;
    public final CookiePersistence mCookiePersistence;
    public final DeletePackageHelper mDeletePackageHelper;
    public final WatchedSparseArray mInstalledInstantAppUids;
    public final WatchedSparseArray mInstantGrants;
    public final AnonymousClass1 mObserver;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public final AnonymousClass2 mSnapshot;
    public final WatchedSparseArray mUninstalledInstantApps;
    public final UserManagerInternal mUserManager;
    public final Object mLock = new Object();
    public final WatchableImpl mWatchable = new WatchableImpl();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.InstantAppRegistry$2, reason: invalid class name */
    public final class AnonymousClass2 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            InstantAppRegistry instantAppRegistry =
                    new InstantAppRegistry((InstantAppRegistry) this.mSource);
            instantAppRegistry.mWatchable.seal();
            return instantAppRegistry;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CookiePersistence extends Handler {
        public final SparseArray mPendingPersistCookies;

        public CookiePersistence(Looper looper) {
            super(looper);
            this.mPendingPersistCookies = new SparseArray();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            AndroidPackage androidPackage = (AndroidPackage) message.obj;
            SomeArgs removePendingPersistCookieLPr =
                    removePendingPersistCookieLPr(androidPackage, i);
            if (removePendingPersistCookieLPr == null) {
                return;
            }
            byte[] bArr = (byte[]) removePendingPersistCookieLPr.arg1;
            File file = (File) removePendingPersistCookieLPr.arg2;
            removePendingPersistCookieLPr.recycle();
            InstantAppRegistry instantAppRegistry = InstantAppRegistry.this;
            String packageName = androidPackage.getPackageName();
            synchronized (instantAppRegistry.mLock) {
                try {
                    File instantApplicationDir =
                            InstantAppRegistry.getInstantApplicationDir(i, packageName);
                    if (!instantApplicationDir.exists() && !instantApplicationDir.mkdirs()) {
                        Slog.e("InstantAppRegistry", "Cannot create instant app cookie directory");
                        return;
                    }
                    if (file.exists() && !file.delete()) {
                        Slog.e("InstantAppRegistry", "Cannot delete instant app cookie file");
                    }
                    if (bArr != null && bArr.length > 0) {
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            try {
                                fileOutputStream.write(bArr, 0, bArr.length);
                                fileOutputStream.close();
                            } finally {
                            }
                        } catch (IOException e) {
                            Slog.e(
                                    "InstantAppRegistry",
                                    "Error writing instant app cookie file: " + file,
                                    e);
                        }
                    }
                } finally {
                }
            }
        }

        public final SomeArgs removePendingPersistCookieLPr(AndroidPackage androidPackage, int i) {
            ArrayMap arrayMap = (ArrayMap) this.mPendingPersistCookies.get(i);
            if (arrayMap == null) {
                return null;
            }
            SomeArgs someArgs = (SomeArgs) arrayMap.remove(androidPackage.getPackageName());
            if (!arrayMap.isEmpty()) {
                return someArgs;
            }
            this.mPendingPersistCookies.remove(i);
            return someArgs;
        }

        public final void schedulePersistLPw(int i, AndroidPackage androidPackage, byte[] bArr) {
            File file =
                    new File(
                            InstantAppRegistry.getInstantApplicationDir(
                                    i, androidPackage.getPackageName()),
                            XmlUtils$$ExternalSyntheticOutline0.m(
                                    "cookie_",
                                    PackageUtils.computeSignaturesSha256Digest(
                                            androidPackage.getSigningDetails().getSignatures()),
                                    ".dat"));
            if (!androidPackage.getSigningDetails().hasSignatures()) {
                Slog.wtf("InstantAppRegistry", "Parsed Instant App contains no valid signatures!");
            }
            File peekInstantCookieFile =
                    InstantAppRegistry.peekInstantCookieFile(i, androidPackage.getPackageName());
            if (peekInstantCookieFile != null && !file.equals(peekInstantCookieFile)) {
                peekInstantCookieFile.delete();
            }
            removeMessages(i, androidPackage);
            SomeArgs removePendingPersistCookieLPr =
                    removePendingPersistCookieLPr(androidPackage, i);
            if (removePendingPersistCookieLPr != null) {
                removePendingPersistCookieLPr.recycle();
            }
            ArrayMap arrayMap = (ArrayMap) this.mPendingPersistCookies.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                this.mPendingPersistCookies.put(i, arrayMap);
            }
            SomeArgs obtain = SomeArgs.obtain();
            obtain.arg1 = bArr;
            obtain.arg2 = file;
            arrayMap.put(androidPackage.getPackageName(), obtain);
            sendMessageDelayed(obtainMessage(i, androidPackage), 1000L);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UninstalledInstantAppState {
        public final InstantAppInfo mInstantAppInfo;
        public final long mTimestamp;

        public UninstalledInstantAppState(InstantAppInfo instantAppInfo, long j) {
            this.mInstantAppInfo = instantAppInfo;
            this.mTimestamp = j;
        }
    }

    public InstantAppRegistry(
            Context context,
            PermissionManagerService.PermissionManagerServiceInternalImpl
                    permissionManagerServiceInternalImpl,
            UserManagerService.LocalService localService,
            DeletePackageHelper deletePackageHelper) {
        Watcher watcher = new Watcher() { // from class: com.android.server.pm.InstantAppRegistry.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        Watchable watchable2 = InstantAppRegistry.this;
                        watchable2.dispatchChange(watchable2);
                    }
                };
        this.mContext = context;
        this.mPermissionManager = permissionManagerServiceInternalImpl;
        this.mUserManager = localService;
        this.mDeletePackageHelper = deletePackageHelper;
        this.mCookiePersistence = new CookiePersistence(BackgroundThread.getHandler().getLooper());
        WatchedSparseArray watchedSparseArray = new WatchedSparseArray();
        this.mUninstalledInstantApps = watchedSparseArray;
        WatchedSparseArray watchedSparseArray2 = new WatchedSparseArray();
        this.mInstantGrants = watchedSparseArray2;
        WatchedSparseArray watchedSparseArray3 = new WatchedSparseArray();
        this.mInstalledInstantAppUids = watchedSparseArray3;
        watchedSparseArray.registerObserver(watcher);
        watchedSparseArray2.registerObserver(watcher);
        watchedSparseArray3.registerObserver(watcher);
        Watchable.verifyWatchedAttributes(this, watcher, false);
        this.mSnapshot = new AnonymousClass2(this, this, null);
    }

    public InstantAppRegistry(InstantAppRegistry instantAppRegistry) {
        new Watcher() { // from class: com.android.server.pm.InstantAppRegistry.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                Watchable watchable2 = InstantAppRegistry.this;
                watchable2.dispatchChange(watchable2);
            }
        };
        this.mContext = instantAppRegistry.mContext;
        this.mPermissionManager = instantAppRegistry.mPermissionManager;
        this.mUserManager = instantAppRegistry.mUserManager;
        this.mDeletePackageHelper = instantAppRegistry.mDeletePackageHelper;
        this.mCookiePersistence = null;
        this.mUninstalledInstantApps =
                new WatchedSparseArray(instantAppRegistry.mUninstalledInstantApps);
        this.mInstantGrants = new WatchedSparseArray(instantAppRegistry.mInstantGrants);
        this.mInstalledInstantAppUids =
                new WatchedSparseArray(instantAppRegistry.mInstalledInstantAppUids);
        this.mSnapshot = null;
    }

    public static void deleteDir(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                deleteDir(file2);
            }
        }
        file.delete();
    }

    public static File getInstantApplicationDir(int i, String str) {
        return new File(new File(Environment.getUserSystemDirectory(i), "instant"), str);
    }

    public static InstantAppInfo parseMetadata(TypedXmlPullParser typedXmlPullParser, String str) {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if ("package".equals(typedXmlPullParser.getName())) {
                String attributeValue =
                        typedXmlPullParser.getAttributeValue((String) null, "label");
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if ("permissions".equals(typedXmlPullParser.getName())) {
                        int depth3 = typedXmlPullParser.getDepth();
                        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                            if ("permission".equals(typedXmlPullParser.getName())) {
                                String readStringAttribute =
                                        XmlUtils.readStringAttribute(typedXmlPullParser, "name");
                                arrayList.add(readStringAttribute);
                                if (typedXmlPullParser.getAttributeBoolean(
                                        (String) null, "granted", false)) {
                                    arrayList2.add(readStringAttribute);
                                }
                            }
                        }
                    }
                }
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                String[] strArr2 = new String[arrayList2.size()];
                arrayList2.toArray(strArr2);
                return new InstantAppInfo(str, attributeValue, strArr, strArr2);
            }
        }
        return null;
    }

    public static UninstalledInstantAppState parseMetadataFile(File file) {
        if (!file.exists()) {
            return null;
        }
        try {
            FileInputStream openRead = new AtomicFile(file).openRead();
            try {
                try {
                    return new UninstalledInstantAppState(
                            parseMetadata(
                                    Xml.resolvePullParser(openRead),
                                    file.getParentFile().getName()),
                            file.lastModified());
                } catch (IOException | XmlPullParserException e) {
                    throw new IllegalStateException(
                            "Failed parsing instant metadata file: " + file, e);
                }
            } finally {
                IoUtils.closeQuietly(openRead);
            }
        } catch (FileNotFoundException unused) {
            Slog.i("InstantAppRegistry", "No instant metadata file");
            return null;
        }
    }

    public static File peekInstantCookieFile(int i, String str) {
        File[] listFiles;
        File instantApplicationDir = getInstantApplicationDir(i, str);
        if (!instantApplicationDir.exists()
                || (listFiles = instantApplicationDir.listFiles()) == null) {
            return null;
        }
        for (File file : listFiles) {
            if (!file.isDirectory()
                    && file.getName().startsWith("cookie_")
                    && file.getName().endsWith(".dat")) {
                return file;
            }
        }
        return null;
    }

    public final void addInstantApp(int i, int i2) {
        synchronized (this.mLock) {
            try {
                WatchedSparseBooleanArray watchedSparseBooleanArray =
                        (WatchedSparseBooleanArray) this.mInstalledInstantAppUids.mStorage.get(i);
                WatchedSparseBooleanArray watchedSparseBooleanArray2 = watchedSparseBooleanArray;
                if (watchedSparseBooleanArray == null) {
                    WatchedSparseBooleanArray watchedSparseBooleanArray3 =
                            new WatchedSparseBooleanArray();
                    this.mInstalledInstantAppUids.put(i, watchedSparseBooleanArray3);
                    watchedSparseBooleanArray2 = watchedSparseBooleanArray3;
                }
                watchedSparseBooleanArray2.mStorage.put(i2, true);
                watchedSparseBooleanArray2.dispatchChange(watchedSparseBooleanArray2);
            } catch (Throwable th) {
                throw th;
            }
        }
        dispatchChange(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addUninstalledInstantAppLPw(
            com.android.server.pm.pkg.PackageStateInternal r17, int r18) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstantAppRegistry.addUninstalledInstantAppLPw(com.android.server.pm.pkg.PackageStateInternal,"
                    + " int):void");
    }

    public final InstantAppInfo createInstantAppInfoForPackage(
            PackageStateInternal packageStateInternal, int i, boolean z) {
        AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null || !packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
            return null;
        }
        String[] strArr = new String[pkg.getRequestedPermissions().size()];
        pkg.getRequestedPermissions().toArray(strArr);
        Set grantedPermissions =
                PermissionManagerService.this.mPermissionManagerServiceImpl.getGrantedPermissions(
                        i, pkg.getPackageName());
        String[] strArr2 = new String[grantedPermissions.size()];
        grantedPermissions.toArray(strArr2);
        ApplicationInfo generateApplicationInfo =
                PackageInfoUtils.generateApplicationInfo(
                        packageStateInternal.getPkg(),
                        0L,
                        packageStateInternal.getUserStateOrDefault(i),
                        i,
                        packageStateInternal);
        return z
                ? new InstantAppInfo(generateApplicationInfo, strArr, strArr2)
                : new InstantAppInfo(
                        generateApplicationInfo.packageName,
                        generateApplicationInfo.loadLabel(this.mContext.getPackageManager()),
                        strArr,
                        strArr2);
    }

    public final void deleteInstantApplicationMetadata(int i, String str) {
        synchronized (this.mLock) {
            try {
                removeUninstalledInstantAppStateLPw(
                        i, new InstantAppRegistry$$ExternalSyntheticLambda2(1, str));
                File instantApplicationDir = getInstantApplicationDir(i, str);
                new File(instantApplicationDir, "metadata.xml").delete();
                new File(instantApplicationDir, "icon.png").delete();
                new File(instantApplicationDir, "android_id").delete();
                File peekInstantCookieFile = peekInstantCookieFile(i, str);
                if (peekInstantCookieFile != null) {
                    peekInstantCookieFile.delete();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public final boolean isInstantAccessGranted(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                WatchedSparseArray watchedSparseArray =
                        (WatchedSparseArray) this.mInstantGrants.mStorage.get(i);
                if (watchedSparseArray == null) {
                    return false;
                }
                WatchedSparseBooleanArray watchedSparseBooleanArray =
                        (WatchedSparseBooleanArray) watchedSparseArray.mStorage.get(i2);
                if (watchedSparseBooleanArray == null) {
                    return false;
                }
                return watchedSparseBooleanArray.mStorage.get(i3);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    public final void onPackageUninstalled(
            AndroidPackage androidPackage, PackageSetting packageSetting, int[] iArr, boolean z) {
        WatchedSparseArray watchedSparseArray;
        synchronized (this.mLock) {
            try {
                for (int i : iArr) {
                    if (!z || !packageSetting.getInstalled(i)) {
                        if (packageSetting.getInstantApp(i)) {
                            addUninstalledInstantAppLPw(packageSetting, i);
                            removeInstantAppLPw(i, packageSetting.mAppId);
                        } else {
                            deleteDir(getInstantApplicationDir(i, androidPackage.getPackageName()));
                            CookiePersistence cookiePersistence = this.mCookiePersistence;
                            cookiePersistence.removeMessages(i, androidPackage);
                            SomeArgs removePendingPersistCookieLPr =
                                    cookiePersistence.removePendingPersistCookieLPr(
                                            androidPackage, i);
                            if (removePendingPersistCookieLPr != null) {
                                removePendingPersistCookieLPr.recycle();
                            }
                            int i2 = packageSetting.mAppId;
                            WatchedSparseArray watchedSparseArray2 = this.mInstantGrants;
                            if (watchedSparseArray2 != null
                                    && (watchedSparseArray =
                                                    (WatchedSparseArray)
                                                            watchedSparseArray2.mStorage.get(i))
                                            != null) {
                                watchedSparseArray.delete(i2);
                                dispatchChange(this);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUninstalledInstantApps.delete(i);
            this.mInstalledInstantAppUids.delete(i);
            this.mInstantGrants.delete(i);
            deleteDir(new File(Environment.getUserSystemDirectory(i), "instant"));
        }
    }

    public final void propagateInstantAppPermissionsIfNeeded(AndroidPackage androidPackage, int i) {
        InstantAppInfo instantAppInfo;
        List list;
        String packageName = androidPackage.getPackageName();
        synchronized (this.mLock) {
            try {
                WatchedSparseArray watchedSparseArray = this.mUninstalledInstantApps;
                if (watchedSparseArray != null
                        && (list = (List) watchedSparseArray.mStorage.get(i)) != null) {
                    int size = list.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        UninstalledInstantAppState uninstalledInstantAppState =
                                (UninstalledInstantAppState) list.get(i2);
                        if (uninstalledInstantAppState
                                .mInstantAppInfo
                                .getPackageName()
                                .equals(packageName)) {
                            instantAppInfo = uninstalledInstantAppState.mInstantAppInfo;
                        }
                    }
                }
                UninstalledInstantAppState parseMetadataFile =
                        parseMetadataFile(
                                new File(getInstantApplicationDir(i, packageName), "metadata.xml"));
                instantAppInfo =
                        parseMetadataFile == null ? null : parseMetadataFile.mInstantAppInfo;
            } finally {
            }
        }
        if (instantAppInfo == null || ArrayUtils.isEmpty(instantAppInfo.getGrantedPermissions())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            for (String str : instantAppInfo.getGrantedPermissions()) {
                PermissionInfo permissionInfo =
                        ((PermissionManager)
                                        this.mContext.getSystemService(PermissionManager.class))
                                .getPermissionInfo(str, 0);
                if (permissionInfo != null
                        && ((permissionInfo.getProtection() == 1
                                        || (permissionInfo.getProtectionFlags() & 32) != 0)
                                && (permissionInfo.getProtectionFlags() & 4096) != 0
                                && androidPackage.getRequestedPermissions().contains(str))) {
                    ((PermissionManager) this.mContext.getSystemService(PermissionManager.class))
                            .grantRuntimePermission(
                                    androidPackage.getPackageName(), str, UserHandle.of(i));
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean pruneInstantApps(Computer computer, long j, long j2, final long j3) {
        File[] listFiles;
        File findPathForUuid =
                ((StorageManager) this.mContext.getSystemService(StorageManager.class))
                        .findPathForUuid(StorageManager.UUID_PRIVATE_INTERNAL);
        if (findPathForUuid.getUsableSpace() >= j) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis();
        int[] userIds = this.mUserManager.getUserIds();
        final ArrayMap packageStates = computer.getPackageStates();
        int size = packageStates.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            PackageStateInternal packageStateInternal =
                    (PackageStateInternal) packageStates.valueAt(i);
            AndroidPackage pkg =
                    packageStateInternal == null ? null : packageStateInternal.getPkg();
            if (pkg != null
                    && currentTimeMillis
                                    - packageStateInternal
                                            .getTransientState()
                                            .getLatestPackageUseTimeInMills()
                            >= j2) {
                int length = userIds.length;
                int i2 = 0;
                boolean z = false;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    PackageUserStateInternal userStateOrDefault =
                            packageStateInternal.getUserStateOrDefault(userIds[i2]);
                    if (userStateOrDefault.isInstalled()) {
                        if (!userStateOrDefault.isInstantApp()) {
                            z = false;
                            break;
                        }
                        z = true;
                    }
                    i2++;
                }
                if (z) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(pkg.getPackageName());
                }
            }
        }
        if (arrayList != null) {
            arrayList.sort(
                    new Comparator() { // from class:
                        // com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda0
                        /* JADX WARN: Code restructure failed: missing block: B:28:0x0088, code lost:

                           if (com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(r8.getUserStates()) > com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(r7.getUserStates())) goto L18;
                        */
                        @Override // java.util.Comparator
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final int compare(java.lang.Object r8, java.lang.Object r9) {
                            /*
                                r7 = this;
                                android.util.ArrayMap r7 = r1
                                java.lang.String r8 = (java.lang.String) r8
                                java.lang.String r9 = (java.lang.String) r9
                                java.lang.Object r8 = r7.get(r8)
                                com.android.server.pm.pkg.PackageStateInternal r8 = (com.android.server.pm.pkg.PackageStateInternal) r8
                                java.lang.Object r9 = r7.get(r9)
                                com.android.server.pm.pkg.PackageStateInternal r9 = (com.android.server.pm.pkg.PackageStateInternal) r9
                                r0 = 0
                                if (r8 != 0) goto L17
                                r8 = r0
                                goto L1b
                            L17:
                                com.android.internal.pm.parsing.pkg.AndroidPackageInternal r8 = r8.getPkg()
                            L1b:
                                if (r9 != 0) goto L1e
                                goto L22
                            L1e:
                                com.android.internal.pm.parsing.pkg.AndroidPackageInternal r0 = r9.getPkg()
                            L22:
                                r9 = 0
                                if (r8 != 0) goto L28
                                if (r0 != 0) goto L28
                                goto L8b
                            L28:
                                r1 = -1
                                if (r8 != 0) goto L2d
                            L2b:
                                r9 = r1
                                goto L8b
                            L2d:
                                r2 = 1
                                if (r0 != 0) goto L32
                            L30:
                                r9 = r2
                                goto L8b
                            L32:
                                java.lang.String r8 = r8.getPackageName()
                                java.lang.Object r8 = r7.get(r8)
                                com.android.server.pm.pkg.PackageStateInternal r8 = (com.android.server.pm.pkg.PackageStateInternal) r8
                                if (r8 != 0) goto L3f
                                goto L8b
                            L3f:
                                java.lang.String r0 = r0.getPackageName()
                                java.lang.Object r7 = r7.get(r0)
                                com.android.server.pm.pkg.PackageStateInternal r7 = (com.android.server.pm.pkg.PackageStateInternal) r7
                                if (r7 != 0) goto L4c
                                goto L8b
                            L4c:
                                com.android.server.pm.pkg.PackageStateUnserialized r9 = r8.getTransientState()
                                long r3 = r9.getLatestPackageUseTimeInMills()
                                com.android.server.pm.pkg.PackageStateUnserialized r9 = r7.getTransientState()
                                long r5 = r9.getLatestPackageUseTimeInMills()
                                int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                                if (r9 <= 0) goto L61
                                goto L30
                            L61:
                                com.android.server.pm.pkg.PackageStateUnserialized r9 = r8.getTransientState()
                                long r3 = r9.getLatestPackageUseTimeInMills()
                                com.android.server.pm.pkg.PackageStateUnserialized r9 = r7.getTransientState()
                                long r5 = r9.getLatestPackageUseTimeInMills()
                                int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                                if (r9 >= 0) goto L76
                                goto L2b
                            L76:
                                android.util.SparseArray r8 = r8.getUserStates()
                                long r8 = com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(r8)
                                android.util.SparseArray r7 = r7.getUserStates()
                                long r3 = com.android.server.pm.pkg.PackageStateUtils.getEarliestFirstInstallTime(r7)
                                int r7 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
                                if (r7 <= 0) goto L2b
                                goto L30
                            L8b:
                                return r9
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda0.compare(java.lang.Object,"
                                        + " java.lang.Object):int");
                        }
                    });
        }
        if (arrayList != null) {
            int size2 = arrayList.size();
            for (int i3 = 0; i3 < size2; i3++) {
                if (this.mDeletePackageHelper.deletePackageX(
                                        0, 2, -1L, (String) arrayList.get(i3), true)
                                == 1
                        && findPathForUuid.getUsableSpace() >= j) {
                    return true;
                }
            }
        }
        synchronized (this.mLock) {
            try {
                for (int i4 : this.mUserManager.getUserIds()) {
                    removeUninstalledInstantAppStateLPw(
                            i4,
                            new Predicate() { // from class:
                                              // com.android.server.pm.InstantAppRegistry$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return System.currentTimeMillis()
                                                    - ((InstantAppRegistry
                                                                            .UninstalledInstantAppState)
                                                                    obj)
                                                            .mTimestamp
                                            > j3;
                                }
                            });
                    File file = new File(Environment.getUserSystemDirectory(i4), "instant");
                    if (file.exists() && (listFiles = file.listFiles()) != null) {
                        for (File file2 : listFiles) {
                            if (file2.isDirectory()) {
                                File file3 = new File(file2, "metadata.xml");
                                if (file3.exists()
                                        && System.currentTimeMillis() - file3.lastModified() > j3) {
                                    deleteDir(file2);
                                    if (findPathForUuid.getUsableSpace() >= j) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    public final void removeInstantAppLPw(int i, int i2) {
        WatchedSparseBooleanArray watchedSparseBooleanArray;
        WatchedSparseArray watchedSparseArray = this.mInstalledInstantAppUids;
        if (watchedSparseArray == null
                || (watchedSparseBooleanArray =
                                (WatchedSparseBooleanArray) watchedSparseArray.mStorage.get(i))
                        == null) {
            return;
        }
        try {
            watchedSparseBooleanArray.mStorage.delete(i2);
            watchedSparseBooleanArray.dispatchChange(watchedSparseBooleanArray);
            WatchedSparseArray watchedSparseArray2 = this.mInstantGrants;
            if (watchedSparseArray2 == null) {
                return;
            }
            WatchedSparseArray watchedSparseArray3 =
                    (WatchedSparseArray) watchedSparseArray2.mStorage.get(i);
            if (watchedSparseArray3 == null) {
                return;
            }
            for (int size = watchedSparseArray3.mStorage.size() - 1; size >= 0; size--) {
                WatchedSparseBooleanArray watchedSparseBooleanArray2 =
                        (WatchedSparseBooleanArray) watchedSparseArray3.mStorage.valueAt(size);
                watchedSparseBooleanArray2.mStorage.delete(i2);
                watchedSparseBooleanArray2.dispatchChange(watchedSparseBooleanArray2);
            }
        } finally {
            dispatchChange(this);
        }
    }

    public final void removeUninstalledInstantAppStateLPw(int i, Predicate predicate) {
        List list;
        WatchedSparseArray watchedSparseArray = this.mUninstalledInstantApps;
        if (watchedSparseArray == null
                || (list = (List) watchedSparseArray.mStorage.get(i)) == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            if (predicate.test((UninstalledInstantAppState) list.get(size))) {
                list.remove(size);
                if (list.isEmpty()) {
                    watchedSparseArray.delete(i);
                    dispatchChange(this);
                    return;
                }
            }
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (InstantAppRegistry) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }
}
