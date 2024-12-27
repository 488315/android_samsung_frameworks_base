package com.android.server.pm;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.util.ArrayMap;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ComponentMutateUtils;
import com.android.internal.pm.pkg.component.ParsedProcess;
import com.android.internal.pm.pkg.component.ParsedProcessImpl;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageStateUnserialized;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.Watcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SharedUserSetting extends SettingBase {
    public int mAppId;
    public final WatchedArraySet mDisabledPackages;
    public final SnapshotCache mDisabledPackagesSnapshot;
    public final AnonymousClass1 mObserver;
    public final WatchedArraySet mPackages;
    public final SnapshotCache mPackagesSnapshot;
    public final SnapshotCache mSnapshot;
    public final String name;
    public final ArrayMap processes;
    public int seInfoTargetSdkVersion;
    public final PackageSignatures signatures;
    public Boolean signaturesChanged;
    public final int uidFlags;
    public final int uidPrivateFlags;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.pm.SharedUserSetting$2, reason: invalid class name */
    public final class AnonymousClass2 extends SnapshotCache {
        @Override // com.android.server.utils.SnapshotCache
        public final Object createSnapshot() {
            return new SharedUserSetting((SharedUserSetting) this.mSource);
        }
    }

    public SharedUserSetting(int i, int i2, String str) {
        super(i, i2);
        Watcher watcher = new Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
                    @Override // com.android.server.utils.Watcher
                    public final void onChange(Watchable watchable) {
                        SharedUserSetting.this.onChanged$2();
                    }
                };
        this.signatures = new PackageSignatures();
        this.uidFlags = i;
        this.uidPrivateFlags = i2;
        this.name = str;
        this.seInfoTargetSdkVersion = 10000;
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mPackages = watchedArraySet;
        this.mPackagesSnapshot =
                new SnapshotCache.Auto(
                        watchedArraySet, watchedArraySet, "SharedUserSetting.packages", 0);
        WatchedArraySet watchedArraySet2 = new WatchedArraySet();
        this.mDisabledPackages = watchedArraySet2;
        this.mDisabledPackagesSnapshot =
                new SnapshotCache.Auto(
                        watchedArraySet2,
                        watchedArraySet2,
                        "SharedUserSetting.mDisabledPackages",
                        0);
        this.processes = new ArrayMap();
        watchedArraySet.registerObserver(watcher);
        watchedArraySet2.registerObserver(watcher);
        this.mSnapshot = new AnonymousClass2(this, this, null);
    }

    public SharedUserSetting(SharedUserSetting sharedUserSetting) {
        super(sharedUserSetting);
        new Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
            @Override // com.android.server.utils.Watcher
            public final void onChange(Watchable watchable) {
                SharedUserSetting.this.onChanged$2();
            }
        };
        PackageSignatures packageSignatures = new PackageSignatures();
        this.signatures = packageSignatures;
        this.name = sharedUserSetting.name;
        this.mAppId = sharedUserSetting.mAppId;
        this.uidFlags = sharedUserSetting.uidFlags;
        this.uidPrivateFlags = sharedUserSetting.uidPrivateFlags;
        this.mPackages = (WatchedArraySet) sharedUserSetting.mPackagesSnapshot.snapshot();
        this.mPackagesSnapshot = new SnapshotCache.Auto();
        this.mDisabledPackages =
                (WatchedArraySet) sharedUserSetting.mDisabledPackagesSnapshot.snapshot();
        this.mDisabledPackagesSnapshot = new SnapshotCache.Auto();
        packageSignatures.mSigningDetails = sharedUserSetting.signatures.mSigningDetails;
        this.signaturesChanged = sharedUserSetting.signaturesChanged;
        this.processes = new ArrayMap(sharedUserSetting.processes);
        this.mSnapshot = new SnapshotCache.Auto();
    }

    public final void addPackage(PackageSetting packageSetting) {
        AndroidPackageInternal androidPackageInternal;
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet.mStorage.size() == 0
                && (androidPackageInternal = packageSetting.pkg) != null) {
            this.seInfoTargetSdkVersion = androidPackageInternal.getTargetSdkVersion();
        }
        if (watchedArraySet.add(packageSetting)) {
            this.mPkgFlags |= packageSetting.mPkgFlags;
            onChanged$2();
            setPrivateFlags(this.mPkgPrivateFlags | packageSetting.mPkgPrivateFlags);
            onChanged$2();
        }
        AndroidPackageInternal androidPackageInternal2 = packageSetting.pkg;
        if (androidPackageInternal2 != null) {
            addProcesses(androidPackageInternal2.getProcesses());
        }
    }

    public final void addProcesses(Map map) {
        if (map != null) {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                ParsedProcess parsedProcess = (ParsedProcess) map.get((String) it.next());
                ParsedProcess parsedProcess2 =
                        (ParsedProcess) this.processes.get(parsedProcess.getName());
                if (parsedProcess2 == null) {
                    this.processes.put(
                            parsedProcess.getName(), new ParsedProcessImpl(parsedProcess));
                } else {
                    ComponentMutateUtils.addStateFrom(parsedProcess2, parsedProcess);
                }
            }
            onChanged$2();
        }
    }

    public final void fixSeInfoLocked() {
        AndroidPackageInternal androidPackageInternal;
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet == null || watchedArraySet.mStorage.size() == 0) {
            return;
        }
        for (int i = 0; i < watchedArraySet.mStorage.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) watchedArraySet.mStorage.valueAt(i);
            if (packageSetting != null
                    && (androidPackageInternal = packageSetting.pkg) != null
                    && androidPackageInternal.getTargetSdkVersion() < this.seInfoTargetSdkVersion) {
                this.seInfoTargetSdkVersion = packageSetting.pkg.getTargetSdkVersion();
                onChanged$2();
            }
        }
        for (int i2 = 0; i2 < watchedArraySet.mStorage.size(); i2++) {
            PackageSetting packageSetting2 = (PackageSetting) watchedArraySet.mStorage.valueAt(i2);
            if (packageSetting2 != null && packageSetting2.pkg != null) {
                String seInfo =
                        SELinuxMMAC.getSeInfo(
                                (PackageStateInternal) packageSetting2,
                                (AndroidPackage) packageSetting2.pkg,
                                isPrivileged() | packageSetting2.isPrivileged(),
                                this.seInfoTargetSdkVersion);
                PackageStateUnserialized packageStateUnserialized = packageSetting2.pkgState;
                packageStateUnserialized.overrideSeInfo = seInfo;
                packageStateUnserialized.mPackageSetting.onChanged$2();
                onChanged$2();
            }
        }
    }

    public final List getPackages() {
        AndroidPackageInternal androidPackageInternal;
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet == null || watchedArraySet.mStorage.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(watchedArraySet.mStorage.size());
        for (int i = 0; i < watchedArraySet.mStorage.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) watchedArraySet.mStorage.valueAt(i);
            if (packageSetting != null && (androidPackageInternal = packageSetting.pkg) != null) {
                arrayList.add(androidPackageInternal);
            }
        }
        return arrayList;
    }

    public final boolean isPrivileged() {
        return (this.mPkgPrivateFlags & 8) != 0;
    }

    public final boolean isSingleUser() {
        if (this.mPackages.mStorage.size() != 1) {
            return false;
        }
        WatchedArraySet watchedArraySet = this.mDisabledPackages;
        if (watchedArraySet.mStorage.size() > 1) {
            return false;
        }
        if (watchedArraySet.mStorage.size() != 1) {
            return true;
        }
        AndroidPackageInternal androidPackageInternal =
                ((PackageSetting) watchedArraySet.mStorage.valueAt(0)).pkg;
        return androidPackageInternal != null && androidPackageInternal.isLeavingSharedUser();
    }

    public final void removePackage(PackageSetting packageSetting) {
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet.remove(packageSetting)) {
            if ((this.mPkgFlags & packageSetting.mPkgFlags) != 0) {
                int i = this.uidFlags;
                for (int i2 = 0; i2 < watchedArraySet.mStorage.size(); i2++) {
                    i |= ((PackageSetting) watchedArraySet.mStorage.valueAt(i2)).mPkgFlags;
                }
                this.mPkgFlags = i;
                onChanged$2();
            }
            if ((packageSetting.mPkgPrivateFlags & this.mPkgPrivateFlags) != 0) {
                int i3 = this.uidPrivateFlags;
                for (int i4 = 0; i4 < watchedArraySet.mStorage.size(); i4++) {
                    i3 |= ((PackageSetting) watchedArraySet.mStorage.valueAt(i4)).mPkgPrivateFlags;
                }
                setPrivateFlags(i3);
            }
            updateProcesses();
            onChanged$2();
        }
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (SharedUserSetting) this.mSnapshot.snapshot();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SharedUserSetting{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" ");
        sb.append(this.name);
        sb.append("/");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mAppId, sb, "}");
    }

    public final void updateProcesses() {
        this.processes.clear();
        WatchedArraySet watchedArraySet = this.mPackages;
        for (int size = watchedArraySet.mStorage.size() - 1; size >= 0; size--) {
            AndroidPackageInternal androidPackageInternal =
                    ((PackageSetting) watchedArraySet.mStorage.valueAt(size)).pkg;
            if (androidPackageInternal != null) {
                addProcesses(androidPackageInternal.getProcesses());
            }
        }
    }
}
