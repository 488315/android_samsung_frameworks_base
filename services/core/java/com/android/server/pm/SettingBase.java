package com.android.server.pm;

import com.android.server.pm.permission.LegacyPermissionState;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import com.android.server.utils.Snappable;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.Watcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class SettingBase implements Watchable, Snappable {
    public final LegacyPermissionState mLegacyPermissionsState;
    public int mPkgFlags;
    public int mPkgPrivateFlags;
    public final WatchableImpl mWatchable;

    public SettingBase(int i, int i2) {
        this.mWatchable = new WatchableImpl();
        this.mLegacyPermissionsState = new LegacyPermissionState();
        this.mPkgFlags = i;
        onChanged$2();
        setPrivateFlags(i2);
    }

    public SettingBase(SettingBase settingBase) {
        this.mWatchable = new WatchableImpl();
        LegacyPermissionState legacyPermissionState = new LegacyPermissionState();
        this.mLegacyPermissionsState = legacyPermissionState;
        if (settingBase != null) {
            this.mPkgFlags = settingBase.mPkgFlags;
            this.mPkgPrivateFlags = settingBase.mPkgPrivateFlags;
            legacyPermissionState.copyFrom(settingBase.mLegacyPermissionsState);
            onChanged$2();
        }
    }

    @Override // com.android.server.utils.Watchable
    public final void dispatchChange(Watchable watchable) {
        this.mWatchable.dispatchChange(watchable);
    }

    public final int getFlags() {
        return this.mPkgFlags;
    }

    public LegacyPermissionState getLegacyPermissionState() {
        return this.mLegacyPermissionsState;
    }

    public final int getPrivateFlags() {
        return this.mPkgPrivateFlags;
    }

    @Override // com.android.server.utils.Watchable
    public final boolean isRegisteredObserver(Watcher watcher) {
        return this.mWatchable.isRegisteredObserver(watcher);
    }

    public final void onChanged$2() {
        PackageStateMutator.sStateChangeSequence.incrementAndGet();
        dispatchChange(this);
    }

    @Override // com.android.server.utils.Watchable
    public final void registerObserver(Watcher watcher) {
        this.mWatchable.registerObserver(watcher);
    }

    public final void setPrivateFlags(int i) {
        this.mPkgPrivateFlags = i;
        onChanged$2();
    }

    @Override // com.android.server.utils.Watchable
    public final void unregisterObserver(Watcher watcher) {
        this.mWatchable.unregisterObserver(watcher);
    }
}
