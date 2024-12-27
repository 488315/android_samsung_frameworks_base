package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.overlay.OverlayPaths;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Pair;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageUserStateImpl extends WatchableImpl
        implements PackageUserStateInternal, Snappable {
    public ArchiveState mArchiveState;
    public int mBooleans;
    public long mCeDataInode;
    public WatchedArrayMap mComponentLabelIconOverrideMap;
    public long mDeDataInode;
    public WatchedArraySet mDisabledComponentsWatched;
    public int mDistractionFlags;
    public WatchedArraySet mEnabledComponentsWatched;
    public int mEnabledState;
    public long mFirstInstallTimeMillis;
    public String mHarmfulAppWarning;
    public int mInstallReason;
    public String mLastDisableAppCaller;
    public int mMinAspectRatio;
    public OverlayPaths mOverlayPaths;
    public WatchedArrayMap mSharedLibraryOverlayPaths;
    public final SnapshotCache mSnapshot;
    public String mSplashScreenTheme;
    public WatchedArrayMap mSuspendParams;
    public int mUninstallReason;
    public Watchable mWatchable;

    public PackageUserStateImpl(Watchable watchable) {
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mMinAspectRatio = 0;
        this.mWatchable = watchable;
        this.mSnapshot =
                new SnapshotCache(
                        this,
                        this) { // from class: com.android.server.pm.pkg.PackageUserStateImpl.1
                    @Override // com.android.server.utils.SnapshotCache
                    public final Object createSnapshot() {
                        return new PackageUserStateImpl(
                                PackageUserStateImpl.this.mWatchable,
                                (PackageUserStateImpl) this.mSource);
                    }
                };
        setBoolean$1(1, true);
    }

    public PackageUserStateImpl(Watchable watchable, PackageUserStateImpl packageUserStateImpl) {
        WatchedArrayMap watchedArrayMap;
        WatchedArrayMap watchedArrayMap2;
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mMinAspectRatio = 0;
        this.mWatchable = watchable;
        this.mBooleans = packageUserStateImpl.mBooleans;
        WatchedArraySet watchedArraySet = packageUserStateImpl.mDisabledComponentsWatched;
        WatchedArrayMap watchedArrayMap3 = null;
        this.mDisabledComponentsWatched =
                watchedArraySet == null ? null : watchedArraySet.snapshot();
        WatchedArraySet watchedArraySet2 = packageUserStateImpl.mEnabledComponentsWatched;
        this.mEnabledComponentsWatched =
                watchedArraySet2 == null ? null : watchedArraySet2.snapshot();
        this.mOverlayPaths = packageUserStateImpl.mOverlayPaths;
        WatchedArrayMap watchedArrayMap4 = packageUserStateImpl.mSharedLibraryOverlayPaths;
        if (watchedArrayMap4 == null) {
            watchedArrayMap = null;
        } else {
            watchedArrayMap = new WatchedArrayMap(0);
            WatchedArrayMap.snapshot(watchedArrayMap, watchedArrayMap4);
        }
        this.mSharedLibraryOverlayPaths = watchedArrayMap;
        this.mCeDataInode = packageUserStateImpl.mCeDataInode;
        this.mDeDataInode = packageUserStateImpl.mDeDataInode;
        this.mDistractionFlags = packageUserStateImpl.mDistractionFlags;
        this.mEnabledState = packageUserStateImpl.mEnabledState;
        this.mInstallReason = packageUserStateImpl.mInstallReason;
        this.mUninstallReason = packageUserStateImpl.mUninstallReason;
        this.mHarmfulAppWarning = packageUserStateImpl.mHarmfulAppWarning;
        this.mLastDisableAppCaller = packageUserStateImpl.mLastDisableAppCaller;
        this.mSplashScreenTheme = packageUserStateImpl.mSplashScreenTheme;
        this.mMinAspectRatio = packageUserStateImpl.mMinAspectRatio;
        WatchedArrayMap watchedArrayMap5 = packageUserStateImpl.mSuspendParams;
        if (watchedArrayMap5 == null) {
            watchedArrayMap2 = null;
        } else {
            watchedArrayMap2 = new WatchedArrayMap(0);
            WatchedArrayMap.snapshot(watchedArrayMap2, watchedArrayMap5);
        }
        this.mSuspendParams = watchedArrayMap2;
        WatchedArrayMap watchedArrayMap6 = packageUserStateImpl.mComponentLabelIconOverrideMap;
        if (watchedArrayMap6 != null) {
            watchedArrayMap3 = new WatchedArrayMap(0);
            WatchedArrayMap.snapshot(watchedArrayMap3, watchedArrayMap6);
        }
        this.mComponentLabelIconOverrideMap = watchedArrayMap3;
        this.mFirstInstallTimeMillis = packageUserStateImpl.mFirstInstallTimeMillis;
        this.mArchiveState = packageUserStateImpl.mArchiveState;
        this.mSnapshot = new SnapshotCache.Auto();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean dataExists() {
        return this.mCeDataInode > 0 || this.mDeDataInode > 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PackageUserStateImpl.class != obj.getClass()) {
            return false;
        }
        PackageUserStateImpl packageUserStateImpl = (PackageUserStateImpl) obj;
        return this.mBooleans == packageUserStateImpl.mBooleans
                && Objects.equals(
                        this.mDisabledComponentsWatched,
                        packageUserStateImpl.mDisabledComponentsWatched)
                && Objects.equals(
                        this.mEnabledComponentsWatched,
                        packageUserStateImpl.mEnabledComponentsWatched)
                && this.mCeDataInode == packageUserStateImpl.mCeDataInode
                && this.mDeDataInode == packageUserStateImpl.mDeDataInode
                && this.mDistractionFlags == packageUserStateImpl.mDistractionFlags
                && this.mEnabledState == packageUserStateImpl.mEnabledState
                && this.mInstallReason == packageUserStateImpl.mInstallReason
                && this.mUninstallReason == packageUserStateImpl.mUninstallReason
                && Objects.equals(this.mHarmfulAppWarning, packageUserStateImpl.mHarmfulAppWarning)
                && Objects.equals(
                        this.mLastDisableAppCaller, packageUserStateImpl.mLastDisableAppCaller)
                && Objects.equals(this.mOverlayPaths, packageUserStateImpl.mOverlayPaths)
                && Objects.equals(
                        this.mSharedLibraryOverlayPaths,
                        packageUserStateImpl.mSharedLibraryOverlayPaths)
                && Objects.equals(this.mSplashScreenTheme, packageUserStateImpl.mSplashScreenTheme)
                && this.mMinAspectRatio == packageUserStateImpl.mMinAspectRatio
                && Objects.equals(this.mSuspendParams, packageUserStateImpl.mSuspendParams)
                && Objects.equals(
                        this.mComponentLabelIconOverrideMap,
                        packageUserStateImpl.mComponentLabelIconOverrideMap)
                && this.mFirstInstallTimeMillis == packageUserStateImpl.mFirstInstallTimeMillis
                && Objects.equals(this.mArchiveState, packageUserStateImpl.mArchiveState);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final OverlayPaths getAllOverlayPaths() {
        if (this.mOverlayPaths == null && this.mSharedLibraryOverlayPaths == null) {
            return null;
        }
        OverlayPaths.Builder builder = new OverlayPaths.Builder();
        builder.addAll(this.mOverlayPaths);
        WatchedArrayMap watchedArrayMap = this.mSharedLibraryOverlayPaths;
        if (watchedArrayMap != null) {
            Iterator it = watchedArrayMap.values().iterator();
            while (it.hasNext()) {
                builder.addAll((OverlayPaths) it.next());
            }
        }
        return builder.build();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final ArchiveState getArchiveState() {
        return this.mArchiveState;
    }

    public final boolean getBoolean$1(int i) {
        return (this.mBooleans & i) != 0;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getCeDataInode() {
        return this.mCeDataInode;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getDeDataInode() {
        return this.mDeDataInode;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    /* renamed from: getDisabledComponents, reason: merged with bridge method [inline-methods] */
    public final ArraySet m787getDisabledComponents() {
        WatchedArraySet watchedArraySet = this.mDisabledComponentsWatched;
        return watchedArraySet == null ? new ArraySet() : watchedArraySet.mStorage;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArraySet getDisabledComponentsNoCopy() {
        return this.mDisabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getDistractionFlags() {
        return this.mDistractionFlags;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    /* renamed from: getEnabledComponents, reason: merged with bridge method [inline-methods] */
    public final ArraySet m788getEnabledComponents() {
        WatchedArraySet watchedArraySet = this.mEnabledComponentsWatched;
        return watchedArraySet == null ? new ArraySet() : watchedArraySet.mStorage;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArraySet getEnabledComponentsNoCopy() {
        return this.mEnabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getEnabledState() {
        return this.mEnabledState;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final long getFirstInstallTimeMillis() {
        return this.mFirstInstallTimeMillis;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getHarmfulAppWarning() {
        return this.mHarmfulAppWarning;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getInstallReason() {
        return this.mInstallReason;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getLastDisableAppCaller() {
        return this.mLastDisableAppCaller;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getMinAspectRatio() {
        return this.mMinAspectRatio;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final OverlayPaths getOverlayPaths() {
        return this.mOverlayPaths;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final Pair getOverrideLabelIconForComponent(ComponentName componentName) {
        if (ArrayUtils.isEmpty(this.mComponentLabelIconOverrideMap)) {
            return null;
        }
        return (Pair) this.mComponentLabelIconOverrideMap.mStorage.get(componentName);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final Map getSharedLibraryOverlayPaths() {
        WatchedArrayMap watchedArrayMap = this.mSharedLibraryOverlayPaths;
        return watchedArrayMap == null ? Collections.emptyMap() : watchedArrayMap;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final String getSplashScreenTheme() {
        return this.mSplashScreenTheme;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public final WatchedArrayMap getSuspendParams() {
        return this.mSuspendParams;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final int getUninstallReason() {
        return this.mUninstallReason;
    }

    public final int hashCode() {
        return (Objects.hashCode(this.mArchiveState)
                        + ((Long.hashCode(this.mFirstInstallTimeMillis)
                                        + ((Objects.hashCode(this.mComponentLabelIconOverrideMap)
                                                        + ((Objects.hashCode(this.mSuspendParams)
                                                                        + ((((Objects.hashCode(
                                                                                                                this
                                                                                                                        .mSplashScreenTheme)
                                                                                                        + ((Objects
                                                                                                                                .hashCode(
                                                                                                                                        this
                                                                                                                                                .mSharedLibraryOverlayPaths)
                                                                                                                        + ((Objects
                                                                                                                                                .hashCode(
                                                                                                                                                        this
                                                                                                                                                                .mOverlayPaths)
                                                                                                                                        + ((Objects
                                                                                                                                                                .hashCode(
                                                                                                                                                                        this
                                                                                                                                                                                .mLastDisableAppCaller)
                                                                                                                                                        + ((Objects
                                                                                                                                                                                .hashCode(
                                                                                                                                                                                        this
                                                                                                                                                                                                .mHarmfulAppWarning)
                                                                                                                                                                        + ((((((((((Long
                                                                                                                                                                                                                                                                .hashCode(
                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                .mDeDataInode)
                                                                                                                                                                                                                                                        + ((Long
                                                                                                                                                                                                                                                                                .hashCode(
                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                .mCeDataInode)
                                                                                                                                                                                                                                                                        + ((Objects
                                                                                                                                                                                                                                                                                                .hashCode(
                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                .mEnabledComponentsWatched)
                                                                                                                                                                                                                                                                                        + ((Objects
                                                                                                                                                                                                                                                                                                                .hashCode(
                                                                                                                                                                                                                                                                                                                        this
                                                                                                                                                                                                                                                                                                                                .mDisabledComponentsWatched)
                                                                                                                                                                                                                                                                                                        + ((this
                                                                                                                                                                                                                                                                                                                                .mBooleans
                                                                                                                                                                                                                                                                                                                        + 31)
                                                                                                                                                                                                                                                                                                                * 31))
                                                                                                                                                                                                                                                                                                * 31))
                                                                                                                                                                                                                                                                                * 31))
                                                                                                                                                                                                                                                                * 31))
                                                                                                                                                                                                                                                * 31)
                                                                                                                                                                                                                                        + this
                                                                                                                                                                                                                                                .mDistractionFlags)
                                                                                                                                                                                                                                * 31)
                                                                                                                                                                                                                        + this
                                                                                                                                                                                                                                .mEnabledState)
                                                                                                                                                                                                                * 31)
                                                                                                                                                                                                        + this
                                                                                                                                                                                                                .mInstallReason)
                                                                                                                                                                                                * 31)
                                                                                                                                                                                        + this
                                                                                                                                                                                                .mUninstallReason)
                                                                                                                                                                                * 31))
                                                                                                                                                                * 31))
                                                                                                                                                * 31))
                                                                                                                                * 31))
                                                                                                                * 31))
                                                                                                * 31)
                                                                                        + this
                                                                                                .mMinAspectRatio)
                                                                                * 31))
                                                                * 31))
                                                * 31))
                                * 961))
                * 31;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isComponentDisabled(String str) {
        WatchedArraySet watchedArraySet = this.mDisabledComponentsWatched;
        return watchedArraySet != null && watchedArraySet.mStorage.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isComponentEnabled(String str) {
        WatchedArraySet watchedArraySet = this.mEnabledComponentsWatched;
        return watchedArraySet != null && watchedArraySet.mStorage.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isHidden() {
        return getBoolean$1(8);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isInstalled() {
        return getBoolean$1(1);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isInstantApp() {
        return getBoolean$1(16);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isNotLaunched() {
        return getBoolean$1(4);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isQuarantined() {
        if (!isSuspended()) {
            return false;
        }
        WatchedArrayMap watchedArrayMap = this.mSuspendParams;
        int size = watchedArrayMap.mStorage.size();
        for (int i = 0; i < size; i++) {
            if (((SuspendParams) watchedArrayMap.mStorage.valueAt(i)).mQuarantined) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isStopped() {
        return getBoolean$1(2);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isSuspended() {
        return !CollectionUtils.isEmpty(this.mSuspendParams);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public final boolean isVirtualPreload() {
        return getBoolean$1(32);
    }

    public final void onChanged$4() {
        Watchable watchable = this.mWatchable;
        if (watchable != null) {
            watchable.dispatchChange(watchable);
        }
        dispatchChange(this);
    }

    public boolean overrideLabelAndIcon(ComponentName componentName, String str, Integer num) {
        Integer num2;
        String str2;
        Pair pair;
        WatchedArrayMap watchedArrayMap = this.mComponentLabelIconOverrideMap;
        if (watchedArrayMap == null
                || (pair = (Pair) watchedArrayMap.mStorage.get(componentName)) == null) {
            num2 = null;
            str2 = null;
        } else {
            str2 = (String) pair.first;
            num2 = (Integer) pair.second;
        }
        boolean z = (TextUtils.equals(str2, str) && Objects.equals(num2, num)) ? false : true;
        if (z) {
            if (str == null && num == null) {
                this.mComponentLabelIconOverrideMap.remove(componentName);
                if (this.mComponentLabelIconOverrideMap.mStorage.isEmpty()) {
                    this.mComponentLabelIconOverrideMap = null;
                }
            } else {
                if (this.mComponentLabelIconOverrideMap == null) {
                    WatchedArrayMap watchedArrayMap2 = new WatchedArrayMap(1);
                    this.mComponentLabelIconOverrideMap = watchedArrayMap2;
                    watchedArrayMap2.registerObserver(this.mSnapshot);
                }
                this.mComponentLabelIconOverrideMap.put(componentName, Pair.create(str, num));
            }
            onChanged$4();
        }
        return z;
    }

    public final void setBoolean$1(int i, boolean z) {
        if (z) {
            this.mBooleans = i | this.mBooleans;
        } else {
            this.mBooleans = (~i) & this.mBooleans;
        }
    }

    public final void setDisabledComponents(ArraySet arraySet) {
        if (this.mDisabledComponentsWatched == null) {
            WatchedArraySet watchedArraySet = new WatchedArraySet();
            this.mDisabledComponentsWatched = watchedArraySet;
            watchedArraySet.registerObserver(this.mSnapshot);
        }
        this.mDisabledComponentsWatched.clear();
        if (arraySet != null) {
            WatchedArraySet watchedArraySet2 = this.mDisabledComponentsWatched;
            watchedArraySet2.mStorage.addAll((Collection) arraySet);
            watchedArraySet2.dispatchChange(watchedArraySet2);
        }
        onChanged$4();
    }

    public final void setEnabledComponents(ArraySet arraySet) {
        if (this.mEnabledComponentsWatched == null) {
            WatchedArraySet watchedArraySet = new WatchedArraySet();
            this.mEnabledComponentsWatched = watchedArraySet;
            watchedArraySet.registerObserver(this.mSnapshot);
        }
        this.mEnabledComponentsWatched.clear();
        if (arraySet != null) {
            WatchedArraySet watchedArraySet2 = this.mEnabledComponentsWatched;
            watchedArraySet2.mStorage.addAll((Collection) arraySet);
            watchedArraySet2.dispatchChange(watchedArraySet2);
        }
        onChanged$4();
    }

    public final boolean setSharedLibraryOverlayPaths(String str, OverlayPaths overlayPaths) {
        if (this.mSharedLibraryOverlayPaths == null) {
            WatchedArrayMap watchedArrayMap = new WatchedArrayMap(0);
            this.mSharedLibraryOverlayPaths = watchedArrayMap;
            watchedArrayMap.registerObserver(this.mSnapshot);
        }
        if (Objects.equals(
                overlayPaths, (OverlayPaths) this.mSharedLibraryOverlayPaths.mStorage.get(str))) {
            return false;
        }
        if (overlayPaths == null || overlayPaths.isEmpty()) {
            boolean z = this.mSharedLibraryOverlayPaths.remove(str) != null;
            onChanged$4();
            return z;
        }
        this.mSharedLibraryOverlayPaths.put(str, overlayPaths);
        onChanged$4();
        return true;
    }

    @Override // com.android.server.utils.Snappable
    public final Object snapshot() {
        return (PackageUserStateImpl) this.mSnapshot.snapshot();
    }
}
