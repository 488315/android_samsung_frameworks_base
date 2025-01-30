package com.android.server.pm;

import android.content.pm.SigningDetails;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.proto.ProtoOutputStream;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.SharedUserApi;
import com.android.server.pm.pkg.component.ComponentMutateUtils;
import com.android.server.pm.pkg.component.ParsedProcess;
import com.android.server.pm.pkg.component.ParsedProcessImpl;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.Watcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class SharedUserSetting extends SettingBase implements SharedUserApi {
    public int mAppId;
    public final WatchedArraySet mDisabledPackages;
    public final SnapshotCache mDisabledPackagesSnapshot;
    public final Watcher mObserver;
    public final WatchedArraySet mPackages;
    public final SnapshotCache mPackagesSnapshot;
    public final SnapshotCache mSnapshot;
    public final String name;
    public final ArrayMap processes;
    public int seInfoTargetSdkVersion;
    public final PackageSignatures signatures;
    public Boolean signaturesChanged;
    public int uidFlags;
    public int uidPrivateFlags;

    public final SnapshotCache makeCache() {
        return new SnapshotCache(this, this) { // from class: com.android.server.pm.SharedUserSetting.2
            @Override // com.android.server.utils.SnapshotCache
            public SharedUserSetting createSnapshot() {
                return new SharedUserSetting();
            }
        };
    }

    public SharedUserSetting(String str, int i, int i2) {
        super(i, i2);
        this.mObserver = new Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                SharedUserSetting.this.onChanged();
            }
        };
        this.signatures = new PackageSignatures();
        this.uidFlags = i;
        this.uidPrivateFlags = i2;
        this.name = str;
        this.seInfoTargetSdkVersion = 10000;
        WatchedArraySet watchedArraySet = new WatchedArraySet();
        this.mPackages = watchedArraySet;
        this.mPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet, watchedArraySet, "SharedUserSetting.packages");
        WatchedArraySet watchedArraySet2 = new WatchedArraySet();
        this.mDisabledPackages = watchedArraySet2;
        this.mDisabledPackagesSnapshot = new SnapshotCache.Auto(watchedArraySet2, watchedArraySet2, "SharedUserSetting.mDisabledPackages");
        this.processes = new ArrayMap();
        registerObservers();
        this.mSnapshot = makeCache();
    }

    public SharedUserSetting(SharedUserSetting sharedUserSetting) {
        super(sharedUserSetting);
        this.mObserver = new Watcher() { // from class: com.android.server.pm.SharedUserSetting.1
            @Override // com.android.server.utils.Watcher
            public void onChange(Watchable watchable) {
                SharedUserSetting.this.onChanged();
            }
        };
        PackageSignatures packageSignatures = new PackageSignatures();
        this.signatures = packageSignatures;
        this.name = sharedUserSetting.name;
        this.mAppId = sharedUserSetting.mAppId;
        this.uidFlags = sharedUserSetting.uidFlags;
        this.uidPrivateFlags = sharedUserSetting.uidPrivateFlags;
        this.mPackages = (WatchedArraySet) sharedUserSetting.mPackagesSnapshot.snapshot();
        this.mPackagesSnapshot = new SnapshotCache.Sealed();
        this.mDisabledPackages = (WatchedArraySet) sharedUserSetting.mDisabledPackagesSnapshot.snapshot();
        this.mDisabledPackagesSnapshot = new SnapshotCache.Sealed();
        packageSignatures.mSigningDetails = sharedUserSetting.signatures.mSigningDetails;
        this.signaturesChanged = sharedUserSetting.signaturesChanged;
        this.processes = new ArrayMap(sharedUserSetting.processes);
        this.mSnapshot = new SnapshotCache.Sealed();
    }

    public final void registerObservers() {
        this.mPackages.registerObserver(this.mObserver);
        this.mDisabledPackages.registerObserver(this.mObserver);
    }

    @Override // com.android.server.utils.Snappable
    public SharedUserSetting snapshot() {
        return (SharedUserSetting) this.mSnapshot.snapshot();
    }

    public String toString() {
        return "SharedUserSetting{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + "/" + this.mAppId + "}";
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mAppId);
        protoOutputStream.write(1138166333442L, this.name);
        protoOutputStream.end(start);
    }

    public void addProcesses(Map map) {
        if (map != null) {
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                ParsedProcess parsedProcess = (ParsedProcess) map.get((String) it.next());
                ParsedProcess parsedProcess2 = (ParsedProcess) this.processes.get(parsedProcess.getName());
                if (parsedProcess2 == null) {
                    this.processes.put(parsedProcess.getName(), new ParsedProcessImpl(parsedProcess));
                } else {
                    ComponentMutateUtils.addStateFrom(parsedProcess2, parsedProcess);
                }
            }
            onChanged();
        }
    }

    public boolean removePackage(PackageSetting packageSetting) {
        if (!this.mPackages.remove(packageSetting)) {
            return false;
        }
        if ((getFlags() & packageSetting.getFlags()) != 0) {
            int i = this.uidFlags;
            for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
                i |= ((PackageSetting) this.mPackages.valueAt(i2)).getFlags();
            }
            setFlags(i);
        }
        if ((packageSetting.getPrivateFlags() & getPrivateFlags()) != 0) {
            int i3 = this.uidPrivateFlags;
            for (int i4 = 0; i4 < this.mPackages.size(); i4++) {
                i3 |= ((PackageSetting) this.mPackages.valueAt(i4)).getPrivateFlags();
            }
            setPrivateFlags(i3);
        }
        updateProcesses();
        onChanged();
        return true;
    }

    public void addPackage(PackageSetting packageSetting) {
        if (this.mPackages.size() == 0 && packageSetting.getPkg() != null) {
            this.seInfoTargetSdkVersion = packageSetting.getPkg().getTargetSdkVersion();
        }
        if (this.mPackages.add(packageSetting)) {
            setFlags(getFlags() | packageSetting.getFlags());
            setPrivateFlags(getPrivateFlags() | packageSetting.getPrivateFlags());
            onChanged();
        }
        if (packageSetting.getPkg() != null) {
            addProcesses(packageSetting.getPkg().getProcesses());
        }
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public List getPackages() {
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet == null || watchedArraySet.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.mPackages.size());
        for (int i = 0; i < this.mPackages.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) this.mPackages.valueAt(i);
            if (packageSetting != null && packageSetting.getPkg() != null) {
                arrayList.add(packageSetting.getPkg());
            }
        }
        return arrayList;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public boolean isPrivileged() {
        return (getPrivateFlags() & 8) != 0;
    }

    public boolean isSingleUser() {
        if (this.mPackages.size() != 1 || this.mDisabledPackages.size() > 1) {
            return false;
        }
        if (this.mDisabledPackages.size() != 1) {
            return true;
        }
        AndroidPackageInternal pkg = ((PackageSetting) this.mDisabledPackages.valueAt(0)).getPkg();
        return pkg != null && pkg.isLeavingSharedUser();
    }

    public void fixSeInfoLocked() {
        WatchedArraySet watchedArraySet = this.mPackages;
        if (watchedArraySet == null || watchedArraySet.size() == 0) {
            return;
        }
        for (int i = 0; i < this.mPackages.size(); i++) {
            PackageSetting packageSetting = (PackageSetting) this.mPackages.valueAt(i);
            if (packageSetting != null && packageSetting.getPkg() != null && packageSetting.getPkg().getTargetSdkVersion() < this.seInfoTargetSdkVersion) {
                this.seInfoTargetSdkVersion = packageSetting.getPkg().getTargetSdkVersion();
                onChanged();
            }
        }
        for (int i2 = 0; i2 < this.mPackages.size(); i2++) {
            PackageSetting packageSetting2 = (PackageSetting) this.mPackages.valueAt(i2);
            if (packageSetting2 != null && packageSetting2.getPkg() != null) {
                packageSetting2.getPkgState().setOverrideSeInfo(SELinuxMMAC.getSeInfo(packageSetting2.getPkg(), isPrivileged() | packageSetting2.isPrivileged(), this.seInfoTargetSdkVersion));
                onChanged();
            }
        }
    }

    public void updateProcesses() {
        this.processes.clear();
        for (int size = this.mPackages.size() - 1; size >= 0; size--) {
            AndroidPackageInternal pkg = ((PackageSetting) this.mPackages.valueAt(size)).getPkg();
            if (pkg != null) {
                addProcesses(pkg.getProcesses());
            }
        }
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public String getName() {
        return this.name;
    }

    public int getAppId() {
        return this.mAppId;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public int getSeInfoTargetSdkVersion() {
        return this.seInfoTargetSdkVersion;
    }

    public WatchedArraySet getPackageSettings() {
        return this.mPackages;
    }

    public WatchedArraySet getDisabledPackageSettings() {
        return this.mDisabledPackages;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public ArraySet getPackageStates() {
        return this.mPackages.untrackedStorage();
    }

    public ArraySet getDisabledPackageStates() {
        return this.mDisabledPackages.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public SigningDetails getSigningDetails() {
        return this.signatures.mSigningDetails;
    }

    @Override // com.android.server.pm.pkg.SharedUserApi
    public LegacyPermissionState getSharedUserLegacyPermissionState() {
        return super.getLegacyPermissionState();
    }
}
