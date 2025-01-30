package com.android.server.pm;

import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.utils.Snappable;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watcher;

/* loaded from: classes3.dex */
public abstract class SettingBase implements Watchable, Snappable {
    public int mPkgFlags;
    public int mPkgPrivateFlags;
    public final Watchable mWatchable = new WatchableImpl();
    public final LegacyPermissionState mLegacyPermissionsState = new LegacyPermissionState();

    @Override // com.android.server.utils.Watchable
    public void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    @Override // com.android.server.utils.Watchable
    public void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public void onChanged() {
        PackageStateMutator.onPackageStateChanged();
        dispatchChange(this);
    }

    public SettingBase(int i, int i2) {
        setFlags(i);
        setPrivateFlags(i2);
    }

    public SettingBase(SettingBase settingBase) {
        if (settingBase != null) {
            copySettingBase(settingBase);
        }
    }

    public final void copySettingBase(SettingBase settingBase) {
        this.mPkgFlags = settingBase.mPkgFlags;
        this.mPkgPrivateFlags = settingBase.mPkgPrivateFlags;
        this.mLegacyPermissionsState.copyFrom(settingBase.mLegacyPermissionsState);
        onChanged();
    }

    public LegacyPermissionState getLegacyPermissionState() {
        return this.mLegacyPermissionsState;
    }

    public SettingBase setFlags(int i) {
        this.mPkgFlags = i;
        onChanged();
        return this;
    }

    public SettingBase setPrivateFlags(int i) {
        this.mPkgPrivateFlags = i;
        onChanged();
        return this;
    }

    public int getFlags() {
        return this.mPkgFlags;
    }

    public int getPrivateFlags() {
        return this.mPkgPrivateFlags;
    }
}
