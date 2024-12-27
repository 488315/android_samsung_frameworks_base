package com.android.server.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.content.pm.VersionedPackage;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.storage.StorageManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.PackageUtils;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.compat.PlatformCompat;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedLongSparseArray;
import com.android.server.utils.Watcher;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import libcore.util.HexEncoding;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SharedLibrariesImpl implements Watchable, Snappable {
    public DeletePackageHelper mDeletePackageHelper;
    public final PackageManagerServiceInjector mInjector;
    public final AnonymousClass1 mObserver;
    public final PackageManagerService mPm;
    public final WatchedArrayMap mSharedLibraries;
    public final SnapshotCache mSharedLibrariesSnapshot;
    public final SnapshotCache mSnapshot;
    public final WatchedArrayMap mStaticLibsByDeclaringPackage;
    public final SnapshotCache mStaticLibsByDeclaringPackageSnapshot;
    public final WatchableImpl mWatchable = new WatchableImpl();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.SharedLibrariesImpl$2, reason: invalid class name */
    public final class AnonymousClass2 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            SharedLibrariesImpl sharedLibrariesImpl = new SharedLibrariesImpl((SharedLibrariesImpl) this.mSource);
            sharedLibrariesImpl.mWatchable.seal();
            return sharedLibrariesImpl;
        }
    }

    public SharedLibrariesImpl(PackageManagerService packageManagerService, PackageManagerServiceInjector packageManagerServiceInjector) {
        Watcher watcher = new Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                SharedLibrariesImpl.this.dispatchChange(watchable);
            }
        };
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
        this.mSharedLibraries = watchedArrayMap;
        this.mSharedLibrariesSnapshot = new SnapshotCache.Auto(watchedArrayMap, watchedArrayMap, "SharedLibrariesImpl.mSharedLibraries", 0);
        WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(0);
        this.mStaticLibsByDeclaringPackage = watchedArrayMap2;
        this.mStaticLibsByDeclaringPackageSnapshot = new SnapshotCache.Auto(watchedArrayMap2, watchedArrayMap2, "SharedLibrariesImpl.mStaticLibsByDeclaringPackage", 0);
        watchedArrayMap.registerObserver(watcher);
        watchedArrayMap2.registerObserver(watcher);
        Watchable.verifyWatchedAttributes(this, watcher, false);
        this.mSnapshot = new AnonymousClass2(this, this, null);
    }

    public SharedLibrariesImpl(SharedLibrariesImpl sharedLibrariesImpl) {
        new Watcher() { // from class: com.android.server.pm.SharedLibrariesImpl.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                SharedLibrariesImpl.this.dispatchChange(watchable);
            }
        };
        this.mPm = sharedLibrariesImpl.mPm;
        this.mInjector = sharedLibrariesImpl.mInjector;
        this.mSharedLibraries = (WatchedArrayMap) sharedLibrariesImpl.mSharedLibrariesSnapshot.snapshot();
        this.mSharedLibrariesSnapshot = new SnapshotCache.Auto();
        this.mStaticLibsByDeclaringPackage = (WatchedArrayMap) sharedLibrariesImpl.mStaticLibsByDeclaringPackageSnapshot.snapshot();
        this.mStaticLibsByDeclaringPackageSnapshot = new SnapshotCache.Auto();
        this.mSnapshot = new SnapshotCache.Auto();
    }

    public static boolean hasString(List list, List list2) {
        if (list != null && list2 != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                    if (((String) list2.get(size2)).equals(list.get(size))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void addBuiltInSharedLibraryLPw(SystemConfig.SharedLibraryEntry sharedLibraryEntry) {
        if (getSharedLibraryInfo(-1L, sharedLibraryEntry.name) != null) {
            return;
        }
        commitSharedLibraryInfoLPw(new SharedLibraryInfo(sharedLibraryEntry.filename, null, null, sharedLibraryEntry.name, -1L, 0, new VersionedPackage("android", 0L), null, null, sharedLibraryEntry.isNative));
    }

    public final void applyDefiningSharedLibraryUpdateLPr(AndroidPackage androidPackage, SharedLibraryInfo sharedLibraryInfo, BiConsumer biConsumer) {
        if (AndroidPackageUtils.isLibrary(androidPackage)) {
            if (androidPackage.getSdkLibraryName() != null) {
                SharedLibraryInfo sharedLibraryInfo2 = getSharedLibraryInfo(androidPackage.getSdkLibVersionMajor(), androidPackage.getSdkLibraryName());
                if (sharedLibraryInfo2 != null) {
                    biConsumer.accept(sharedLibraryInfo2, sharedLibraryInfo);
                    return;
                }
                return;
            }
            if (androidPackage.getStaticSharedLibraryName() != null) {
                SharedLibraryInfo sharedLibraryInfo3 = getSharedLibraryInfo(androidPackage.getStaticSharedLibraryVersion(), androidPackage.getStaticSharedLibraryName());
                if (sharedLibraryInfo3 != null) {
                    biConsumer.accept(sharedLibraryInfo3, sharedLibraryInfo);
                    return;
                }
                return;
            }
            Iterator it = androidPackage.getLibraryNames().iterator();
            while (it.hasNext()) {
                SharedLibraryInfo sharedLibraryInfo4 = getSharedLibraryInfo(-1L, (String) it.next());
                if (sharedLibraryInfo4 != null) {
                    biConsumer.accept(sharedLibraryInfo4, sharedLibraryInfo);
                }
            }
        }
    }

    public final ArrayList collectSharedLibraryInfos(AndroidPackage androidPackage, Map map, Map map2) {
        boolean z;
        if (androidPackage == null) {
            return null;
        }
        PlatformCompat compatibility = this.mInjector.getCompatibility();
        ArrayList collectSharedLibraryInfos = androidPackage.getUsesLibraries().isEmpty() ? null : collectSharedLibraryInfos(androidPackage.getUsesLibraries(), null, null, null, androidPackage.getPackageName(), "shared", true, androidPackage.getTargetSdkVersion(), null, map, map2);
        if (!androidPackage.getUsesStaticLibraries().isEmpty()) {
            collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage.getUsesStaticLibraries(), androidPackage.getUsesStaticLibrariesVersions(), androidPackage.getUsesStaticLibrariesCertDigests(), null, androidPackage.getPackageName(), "static shared", true, androidPackage.getTargetSdkVersion(), collectSharedLibraryInfos, map, map2);
        }
        if (!androidPackage.getUsesOptionalLibraries().isEmpty()) {
            collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage.getUsesOptionalLibraries(), null, null, null, androidPackage.getPackageName(), "shared", false, androidPackage.getTargetSdkVersion(), collectSharedLibraryInfos, map, map2);
        }
        String packageName = androidPackage.getPackageName();
        int targetSdkVersion = androidPackage.getTargetSdkVersion();
        if (compatibility.mCompatConfig.willChangeBeEnabled(142191088L, packageName)) {
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.packageName = packageName;
            applicationInfo.targetSdkVersion = targetSdkVersion;
            z = compatibility.isChangeEnabledInternalNoLogging(142191088L, applicationInfo);
        } else {
            z = false;
        }
        if (z) {
            if (!androidPackage.getUsesNativeLibraries().isEmpty()) {
                collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage.getUsesNativeLibraries(), null, null, null, androidPackage.getPackageName(), "native shared", true, androidPackage.getTargetSdkVersion(), collectSharedLibraryInfos, map, map2);
            }
            if (!androidPackage.getUsesOptionalNativeLibraries().isEmpty()) {
                collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage.getUsesOptionalNativeLibraries(), null, null, null, androidPackage.getPackageName(), "native shared", false, androidPackage.getTargetSdkVersion(), collectSharedLibraryInfos, map, map2);
            }
        }
        if (androidPackage.getUsesSdkLibraries().isEmpty()) {
            return collectSharedLibraryInfos;
        }
        return collectSharedLibraryInfos(androidPackage.getUsesSdkLibraries(), androidPackage.getUsesSdkLibrariesVersionsMajor(), androidPackage.getUsesSdkLibrariesCertDigests(), androidPackage.getUsesSdkLibrariesOptional(), androidPackage.getPackageName(), "sdk", !Flags.sdkLibIndependence(), androidPackage.getTargetSdkVersion(), collectSharedLibraryInfos, map, map2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ArrayList collectSharedLibraryInfos(List list, long[] jArr, String[][] strArr, boolean[] zArr, String str, String str2, boolean z, int i, ArrayList arrayList, Map map, Map map2) {
        SharedLibraryInfo sharedLibraryInfo;
        char c;
        int i2;
        String[] computeSignaturesSha256Digests;
        int i3 = 1;
        int size = list.size();
        char c2 = 0;
        ArrayList arrayList2 = arrayList;
        int i4 = 0;
        while (i4 < size) {
            String str3 = (String) list.get(i4);
            long j = jArr != null ? jArr[i4] : -1L;
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    WatchedArrayMap watchedArrayMap = this.mSharedLibraries;
                    if (map2 != null) {
                        WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) ((ArrayMap) map2).get(str3);
                        sharedLibraryInfo = watchedLongSparseArray != null ? (SharedLibraryInfo) watchedLongSparseArray.mStorage.get(j) : null;
                        if (sharedLibraryInfo != null) {
                        }
                    }
                    WatchedLongSparseArray watchedLongSparseArray2 = (WatchedLongSparseArray) watchedArrayMap.mStorage.get(str3);
                    sharedLibraryInfo = watchedLongSparseArray2 == null ? null : (SharedLibraryInfo) watchedLongSparseArray2.mStorage.get(j);
                } catch (Throwable th) {
                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
            if (sharedLibraryInfo != null) {
                if (jArr == null || strArr == null) {
                    c = c2;
                } else {
                    if (sharedLibraryInfo.getLongVersion() != jArr[i4]) {
                        StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Package ", str, " requires unavailable ", str2, " library ");
                        m.append(str3);
                        m.append(" version ");
                        m.append(sharedLibraryInfo.getLongVersion());
                        m.append("; failing!");
                        throw new PackageManagerException(-9, m.toString());
                    }
                    AndroidPackage androidPackage = (AndroidPackage) map.get(sharedLibraryInfo.getPackageName());
                    SigningDetails signingDetails = androidPackage != null ? androidPackage.getSigningDetails() : null;
                    if (signingDetails == null) {
                        throw new PackageManagerException(-9, XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " requires unavailable ", str2, " library; failing!"));
                    }
                    String[] strArr2 = strArr[i4];
                    if (strArr2.length > i3) {
                        if (i >= 27) {
                            computeSignaturesSha256Digests = PackageUtils.computeSignaturesSha256Digests(signingDetails.getSignatures());
                        } else {
                            Signature[] signatureArr = new Signature[i3];
                            signatureArr[c2] = signingDetails.getSignatures()[c2];
                            computeSignaturesSha256Digests = PackageUtils.computeSignaturesSha256Digests(signatureArr);
                        }
                        if (strArr2.length != computeSignaturesSha256Digests.length) {
                            throw new PackageManagerException(-9, XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " requires differently signed ", str2, " library; failing!"));
                        }
                        Arrays.sort(computeSignaturesSha256Digests);
                        Arrays.sort(strArr2);
                        int length = computeSignaturesSha256Digests.length;
                        for (int i5 = 0; i5 < length; i5++) {
                            if (!computeSignaturesSha256Digests[i5].equalsIgnoreCase(strArr2[i5])) {
                                throw new PackageManagerException(-9, XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " requires differently signed ", str2, " library; failing!"));
                            }
                        }
                        c = 0;
                    } else {
                        boolean z4 = c2;
                        try {
                            boolean hasSha256Certificate = signingDetails.hasSha256Certificate(HexEncoding.decode(strArr2[z4 ? 1 : 0], z4));
                            c = z4;
                            if (!hasSha256Certificate) {
                                throw new PackageManagerException(-9, XmlUtils$$ExternalSyntheticOutline0.m("Package ", str, " requires differently signed ", str2, " library; failing!"));
                            }
                        } catch (IllegalArgumentException unused) {
                            throw new PackageManagerException(-130, AudioOffloadInfo$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("Package ", str, " declares bad certificate digest for ", str2, " library "), str3, "; failing!"));
                        }
                    }
                }
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                }
                arrayList2.add(sharedLibraryInfo);
                i2 = 1;
            } else {
                if (z || !(!"sdk".equals(str2) || zArr == null || zArr[i4])) {
                    throw new PackageManagerException(-9, AudioOffloadInfo$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m("Package ", str, " requires unavailable ", str2, " library "), str3, "; failing!"));
                }
                char c3 = c2;
                i2 = i3;
                c = c3;
            }
            i4 += i2;
            int i6 = i2;
            c2 = c;
            i3 = i6;
        }
        return arrayList2;
    }

    public final ArrayList commitSharedLibraryChanges(AndroidPackage androidPackage, PackageSetting packageSetting, List list, Map map, int i) {
        if (ArrayUtils.isEmpty(list)) {
            return null;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    commitSharedLibraryInfoLPw((SharedLibraryInfo) it.next());
                }
                try {
                    updateSharedLibraries(androidPackage, packageSetting, null, null, map);
                } catch (PackageManagerException e) {
                    Slog.e("PackageManager", "updateSharedLibraries failed: ", e);
                }
                if ((i & 16) != 0) {
                    boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                    return null;
                }
                ArrayList updateAllSharedLibrariesLPw = updateAllSharedLibrariesLPw(androidPackage, packageSetting, map);
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                return updateAllSharedLibrariesLPw;
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void commitSharedLibraryInfoLPw(SharedLibraryInfo sharedLibraryInfo) {
        String name = sharedLibraryInfo.getName();
        WatchedArrayMap watchedArrayMap = this.mSharedLibraries;
        WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) watchedArrayMap.mStorage.get(name);
        WatchedLongSparseArray watchedLongSparseArray2 = watchedLongSparseArray;
        if (watchedLongSparseArray == null) {
            WatchedLongSparseArray watchedLongSparseArray3 = new WatchedLongSparseArray();
            watchedArrayMap.put(name, watchedLongSparseArray3);
            watchedLongSparseArray2 = watchedLongSparseArray3;
        }
        Object packageName = sharedLibraryInfo.getDeclaringPackage().getPackageName();
        if (sharedLibraryInfo.getType() == 2) {
            this.mStaticLibsByDeclaringPackage.put(packageName, watchedLongSparseArray2);
        }
        long longVersion = sharedLibraryInfo.getLongVersion();
        Object obj = watchedLongSparseArray2.mStorage.get(longVersion);
        watchedLongSparseArray2.mStorage.put(longVersion, sharedLibraryInfo);
        if (watchedLongSparseArray2.mWatching && (obj instanceof Watchable) && watchedLongSparseArray2.mStorage.indexOfValue(obj) == -1) {
            ((Watchable) obj).unregisterObserver(watchedLongSparseArray2.mObserver);
        }
        if (watchedLongSparseArray2.mWatching && (sharedLibraryInfo instanceof Watchable)) {
            ((Watchable) sharedLibraryInfo).registerObserver(watchedLongSparseArray2.mObserver);
        }
        watchedLongSparseArray2.dispatchChange(watchedLongSparseArray2);
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public final void executeSharedLibrariesUpdateLPw(AndroidPackage androidPackage, PackageSetting packageSetting, AndroidPackage androidPackage2, PackageSetting packageSetting2, ArrayList arrayList, int[] iArr) {
        PackageManagerService packageManagerService;
        final int i = 0;
        applyDefiningSharedLibraryUpdateLPr(androidPackage, null, new BiConsumer() { // from class: com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) obj;
                SharedLibraryInfo sharedLibraryInfo2 = (SharedLibraryInfo) obj2;
                switch (i) {
                    case 0:
                        sharedLibraryInfo.clearDependencies();
                        break;
                    default:
                        sharedLibraryInfo.addDependency(sharedLibraryInfo2);
                        break;
                }
            }
        });
        if (arrayList == null) {
            PackageStateUnserialized packageStateUnserialized = packageSetting.pkgState;
            packageStateUnserialized.setUsesLibraryInfos(Collections.emptyList());
            packageStateUnserialized.usesLibraryFiles = Collections.emptyList();
            packageStateUnserialized.mPackageSetting.onChanged$2();
            return;
        }
        packageSetting.pkgState.setUsesLibraryInfos(arrayList);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = arrayList.iterator();
        while (true) {
            boolean hasNext = it.hasNext();
            packageManagerService = this.mPm;
            if (!hasNext) {
                break;
            }
            SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) it.next();
            if (sharedLibraryInfo.getPath() != null) {
                linkedHashSet.add(sharedLibraryInfo.getPath());
            } else {
                AndroidPackage androidPackage3 = (AndroidPackage) packageManagerService.mPackages.mStorage.get(sharedLibraryInfo.getPackageName());
                PackageSetting packageLPr = packageManagerService.mSettings.getPackageLPr(sharedLibraryInfo.getPackageName());
                if (androidPackage2 != null && androidPackage2.getPackageName().equals(sharedLibraryInfo.getPackageName()) && (androidPackage3 == null || androidPackage3.getPackageName().equals(androidPackage2.getPackageName()))) {
                    androidPackage3 = androidPackage2;
                    packageLPr = packageSetting2;
                }
                if (androidPackage3 != null) {
                    linkedHashSet.addAll(AndroidPackageUtils.getAllCodePaths(androidPackage3));
                    final int i2 = 1;
                    applyDefiningSharedLibraryUpdateLPr(androidPackage, sharedLibraryInfo, new BiConsumer() { // from class: com.android.server.pm.SharedLibrariesImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            SharedLibraryInfo sharedLibraryInfo2 = (SharedLibraryInfo) obj;
                            SharedLibraryInfo sharedLibraryInfo22 = (SharedLibraryInfo) obj2;
                            switch (i2) {
                                case 0:
                                    sharedLibraryInfo2.clearDependencies();
                                    break;
                                default:
                                    sharedLibraryInfo2.addDependency(sharedLibraryInfo22);
                                    break;
                            }
                        }
                    });
                    if (packageLPr != null) {
                        linkedHashSet.addAll(packageLPr.pkgState.usesLibraryFiles);
                    }
                }
            }
        }
        List usesLibraryFiles = packageSetting.getUsesLibraryFiles();
        if (usesLibraryFiles.size() != linkedHashSet.size() || !usesLibraryFiles.containsAll(linkedHashSet)) {
            ArrayList arrayList2 = new ArrayList(linkedHashSet);
            PackageStateUnserialized packageStateUnserialized2 = packageSetting.pkgState;
            packageStateUnserialized2.usesLibraryFiles = arrayList2;
            packageStateUnserialized2.mPackageSetting.onChanged$2();
            packageSetting.onChanged$2();
        }
        int[] iArr2 = new int[iArr.length];
        int i3 = 0;
        for (int i4 = 0; i4 < iArr.length; i4++) {
            if (packageSetting.getInstalled(iArr[i4])) {
                iArr2[i3] = iArr[i4];
                i3++;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SharedLibraryInfo sharedLibraryInfo2 = (SharedLibraryInfo) it2.next();
            if (sharedLibraryInfo2.isStatic()) {
                PackageSetting packageLPr2 = packageManagerService.mSettings.getPackageLPr(sharedLibraryInfo2.getPackageName());
                if (packageLPr2 == null) {
                    Slog.wtf("PackageManager", "Shared lib without setting: " + sharedLibraryInfo2);
                } else {
                    for (int i5 = 0; i5 < i3; i5++) {
                        packageLPr2.setInstalled(iArr2[i5], true);
                    }
                }
            }
        }
    }

    public final List getAllowedSharedLibInfos(InstallRequest installRequest) {
        PackageSetting packageSetting;
        AndroidPackageInternal androidPackageInternal;
        ParsedPackage parsedPackage = installRequest.mParsedPackage;
        installRequest.assertScanResultExists();
        ArrayList arrayList = null;
        if (installRequest.mScanResult.mSdkSharedLibraryInfo == null) {
            installRequest.assertScanResultExists();
            if (installRequest.mScanResult.mStaticSharedLibraryInfo == null) {
                installRequest.assertScanResultExists();
                if (installRequest.mScanResult.mDynamicSharedLibraryInfos == null) {
                    return null;
                }
            }
        }
        installRequest.assertScanResultExists();
        if (installRequest.mScanResult.mSdkSharedLibraryInfo != null) {
            installRequest.assertScanResultExists();
            return Collections.singletonList(installRequest.mScanResult.mSdkSharedLibraryInfo);
        }
        installRequest.assertScanResultExists();
        if (installRequest.mScanResult.mStaticSharedLibraryInfo != null) {
            installRequest.assertScanResultExists();
            return Collections.singletonList(installRequest.mScanResult.mStaticSharedLibraryInfo);
        }
        boolean z = false;
        boolean z2 = installRequest.getScannedPackageSetting() != null && installRequest.getScannedPackageSetting().isSystem();
        if (parsedPackage != null && z2) {
            installRequest.assertScanResultExists();
            if (installRequest.mScanResult.mDynamicSharedLibraryInfos != null) {
                if (installRequest.getScannedPackageSetting() != null && installRequest.getScannedPackageSetting().pkgState.updatedSystemApp) {
                    z = true;
                }
                if (z) {
                    installRequest.assertScanResultExists();
                    if (installRequest.mScanResult.mRequest.mDisabledPkgSetting == null) {
                        installRequest.assertScanResultExists();
                        packageSetting = installRequest.mScanResult.mRequest.mOldPkgSetting;
                    } else {
                        installRequest.assertScanResultExists();
                        packageSetting = installRequest.mScanResult.mRequest.mDisabledPkgSetting;
                    }
                } else {
                    packageSetting = null;
                }
                if (z && ((androidPackageInternal = packageSetting.pkg) == null || androidPackageInternal.getLibraryNames() == null)) {
                    Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares libraries that are not declared on the system image; skipping");
                    return null;
                }
                installRequest.assertScanResultExists();
                arrayList = new ArrayList(installRequest.mScanResult.mDynamicSharedLibraryInfos.size());
                installRequest.assertScanResultExists();
                for (SharedLibraryInfo sharedLibraryInfo : installRequest.mScanResult.mDynamicSharedLibraryInfos) {
                    String name = sharedLibraryInfo.getName();
                    if (!z || packageSetting.pkg.getLibraryNames().contains(name)) {
                        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
                        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                        synchronized (packageManagerTracedLock) {
                            try {
                                if (getSharedLibraryInfo(-1L, name) != null) {
                                    Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares library " + name + " that already exists; skipping");
                                } else {
                                    arrayList.add(sharedLibraryInfo);
                                }
                            } catch (Throwable th) {
                                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                throw th;
                            }
                        }
                    } else {
                        Slog.w("PackageManager", "Package " + parsedPackage.getPackageName() + " declares library " + name + " that is not declared on system image; skipping");
                    }
                }
            }
        }
        return arrayList;
    }

    public final SharedLibraryInfo getLatestStaticSharedLibraVersion(AndroidPackage androidPackage) {
        SharedLibraryInfo latestStaticSharedLibraVersionLPr;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                latestStaticSharedLibraVersionLPr = getLatestStaticSharedLibraVersionLPr(androidPackage);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return latestStaticSharedLibraVersionLPr;
    }

    public final SharedLibraryInfo getLatestStaticSharedLibraVersionLPr(AndroidPackage androidPackage) {
        WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.mStorage.get(androidPackage.getStaticSharedLibraryName());
        if (watchedLongSparseArray == null) {
            return null;
        }
        int size = watchedLongSparseArray.mStorage.size();
        long j = -1;
        for (int i = 0; i < size; i++) {
            long keyAt = watchedLongSparseArray.mStorage.keyAt(i);
            if (keyAt < androidPackage.getStaticSharedLibraryVersion()) {
                j = Math.max(j, keyAt);
            }
        }
        if (j >= 0) {
            return (SharedLibraryInfo) watchedLongSparseArray.mStorage.get(j);
        }
        return null;
    }

    public WatchedArrayMap getSharedLibraries() {
        return this.mSharedLibraries;
    }

    public final SharedLibraryInfo getSharedLibraryInfo(long j, String str) {
        WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.mStorage.get(str);
        if (watchedLongSparseArray == null) {
            return null;
        }
        return (SharedLibraryInfo) watchedLongSparseArray.mStorage.get(j);
    }

    public final WatchedLongSparseArray getSharedLibraryInfos(String str) {
        WatchedLongSparseArray watchedLongSparseArray;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.mStorage.get(str);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        return watchedLongSparseArray;
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    public final boolean pruneUnusedStaticSharedLibraries(Computer computer, long j, long j2) {
        int i;
        PackageSetting packageStateInternal;
        File findPathForUuid = ((StorageManager) this.mInjector.mGetSystemServiceProducer.produce(StorageManager.class)).findPathForUuid(StorageManager.UUID_PRIVATE_INTERNAL);
        ArrayList arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        WatchedArrayMap sharedLibraries = computer.getSharedLibraries();
        int size = sharedLibraries.mStorage.size();
        int i2 = 0;
        while (i2 < size) {
            WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) sharedLibraries.mStorage.valueAt(i2);
            if (watchedLongSparseArray != null) {
                int size2 = watchedLongSparseArray.mStorage.size();
                int i3 = 0;
                while (i3 < size2) {
                    SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(i3);
                    VersionedPackage declaringPackage = sharedLibraryInfo.getDeclaringPackage();
                    if (sharedLibraryInfo.isStatic()) {
                        i = i2;
                        packageStateInternal = computer.getPackageStateInternal(computer.resolveInternalPackageName(declaringPackage.getLongVersionCode(), declaringPackage.getPackageName()));
                    } else {
                        i = i2;
                        packageStateInternal = sharedLibraryInfo.isSdk() ? computer.getPackageStateInternal(declaringPackage.getPackageName()) : null;
                    }
                    if (packageStateInternal != null && currentTimeMillis - packageStateInternal.lastUpdateTime >= j2 && !packageStateInternal.isSystem()) {
                        arrayList.add(new VersionedPackage(packageStateInternal.pkg.getPackageName(), sharedLibraryInfo.getDeclaringPackage().getLongVersionCode()));
                    }
                    i3++;
                    i2 = i;
                }
            }
            i2++;
        }
        int size3 = arrayList.size();
        for (int i4 = 0; i4 < size3; i4++) {
            VersionedPackage versionedPackage = (VersionedPackage) arrayList.get(i4);
            if (this.mDeletePackageHelper.deletePackageX(0, 2, versionedPackage.getLongVersionCode(), versionedPackage.getPackageName(), true) == 1 && findPathForUuid.getUsableSpace() >= j) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    public final void removeSharedLibrary(long j, String str) {
        int i;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                WatchedLongSparseArray watchedLongSparseArray = (WatchedLongSparseArray) this.mSharedLibraries.mStorage.get(str);
                if (watchedLongSparseArray == null) {
                    return;
                }
                int indexOfKey = watchedLongSparseArray.mStorage.indexOfKey(j);
                if (indexOfKey < 0) {
                    return;
                }
                SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo) watchedLongSparseArray.mStorage.valueAt(indexOfKey);
                Computer snapshotComputer = this.mPm.snapshotComputer();
                int[] userIds = this.mPm.mUserManager.getUserIds();
                int length = userIds.length;
                int i2 = 0;
                while (i2 < length) {
                    int i3 = userIds[i2];
                    int i4 = i2;
                    List list = (List) snapshotComputer.getPackagesUsingSharedLibrary(sharedLibraryInfo, 0L, 1000, i3).first;
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(((VersionedPackage) it.next()).getPackageName());
                            if (packageLPr != null) {
                                i = i3;
                                packageLPr.modifyUserState(i).setSharedLibraryOverlayPaths(sharedLibraryInfo.getName(), null);
                                packageLPr.onChanged$2();
                            } else {
                                i = i3;
                            }
                            i3 = i;
                        }
                    }
                    i2 = i4 + 1;
                }
                Object obj = watchedLongSparseArray.mStorage.get(j, null);
                watchedLongSparseArray.mStorage.delete(j);
                if (watchedLongSparseArray.mWatching && (obj instanceof Watchable) && watchedLongSparseArray.mStorage.indexOfValue(obj) == -1) {
                    ((Watchable) obj).unregisterObserver(watchedLongSparseArray.mObserver);
                }
                watchedLongSparseArray.dispatchChange(watchedLongSparseArray);
                if (watchedLongSparseArray.mStorage.size() <= 0) {
                    this.mSharedLibraries.remove(str);
                    if (sharedLibraryInfo.getType() == 2) {
                        this.mStaticLibsByDeclaringPackage.remove(sharedLibraryInfo.getDeclaringPackage().getPackageName());
                    }
                }
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (SharedLibrariesImpl) this.mSnapshot.snapshot();
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    public final ArrayList updateAllSharedLibrariesLPw(AndroidPackage androidPackage, PackageSetting packageSetting, Map map) {
        ArrayList arrayList;
        ArraySet arraySet;
        ArrayList arrayList2;
        if (androidPackage == null || packageSetting == null) {
            arrayList = null;
            arraySet = null;
            arrayList2 = null;
        } else {
            ArrayList arrayList3 = new ArrayList(1);
            arrayList3.add(Pair.create(androidPackage, packageSetting));
            arrayList2 = arrayList3;
            arrayList = null;
            arraySet = null;
        }
        do {
            Pair pair = arrayList2 == null ? null : (Pair) arrayList2.remove(0);
            AndroidPackage androidPackage2 = pair != null ? (AndroidPackage) pair.first : null;
            PackageSetting packageSetting2 = pair != null ? (PackageSetting) pair.second : null;
            PackageManagerService packageManagerService = this.mPm;
            for (int size = packageManagerService.mPackages.mStorage.size() - 1; size >= 0; size--) {
                AndroidPackage androidPackage3 = (AndroidPackage) packageManagerService.mPackages.mStorage.valueAt(size);
                PackageSetting packageLPr = packageManagerService.mSettings.getPackageLPr(androidPackage3.getPackageName());
                if (androidPackage2 == null || hasString(androidPackage3.getUsesLibraries(), androidPackage2.getLibraryNames()) || hasString(androidPackage3.getUsesOptionalLibraries(), androidPackage2.getLibraryNames()) || ArrayUtils.contains(androidPackage3.getUsesStaticLibraries(), androidPackage2.getStaticSharedLibraryName()) || ArrayUtils.contains(androidPackage3.getUsesSdkLibraries(), androidPackage2.getSdkLibraryName())) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    ArrayList arrayList4 = arrayList;
                    arrayList4.add(androidPackage3);
                    if (androidPackage2 != null) {
                        if (arraySet == null) {
                            arraySet = new ArraySet();
                        }
                        if (!arraySet.contains(androidPackage3.getPackageName())) {
                            arraySet.add(androidPackage3.getPackageName());
                            arrayList2.add(Pair.create(androidPackage3, packageLPr));
                        }
                    }
                    ArraySet arraySet2 = arraySet;
                    try {
                        updateSharedLibraries(androidPackage3, packageLPr, androidPackage2, packageSetting2, map);
                    } catch (PackageManagerException e) {
                        boolean isSystem = packageLPr.isSystem();
                        PackageStateUnserialized packageStateUnserialized = packageLPr.pkgState;
                        if (!isSystem || packageStateUnserialized.updatedSystemApp) {
                            boolean z = packageStateUnserialized.updatedSystemApp;
                            PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mInstallLock;
                            packageManagerTracedLock.mLock.lock();
                            try {
                                this.mDeletePackageHelper.deletePackageLIF(androidPackage3.getPackageName(), null, true, packageManagerService.mUserManager.getUserIds(), z ? 1 : 0, new PackageRemovedInfo(), true);
                                packageManagerTracedLock.close();
                            } finally {
                            }
                        }
                        Slog.e("PackageManager", "updateAllSharedLibrariesLPw failed: " + e.getMessage());
                    }
                    arraySet = arraySet2;
                    arrayList = arrayList4;
                }
            }
            if (arrayList2 == null) {
                break;
            }
        } while (arrayList2.size() > 0);
        return arrayList;
    }

    public final void updateSharedLibraries(AndroidPackage androidPackage, PackageSetting packageSetting, AndroidPackage androidPackage2, PackageSetting packageSetting2, Map map) {
        ArrayList collectSharedLibraryInfos = collectSharedLibraryInfos(androidPackage, map, null);
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                executeSharedLibrariesUpdateLPw(androidPackage, packageSetting, androidPackage2, packageSetting2, collectSharedLibraryInfos, this.mPm.mUserManager.getUserIds());
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }
}
