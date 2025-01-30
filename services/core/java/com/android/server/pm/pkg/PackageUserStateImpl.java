package com.android.server.pm.pkg;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.overlay.OverlayPaths;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.server.utils.Snappable;
import com.android.server.utils.SnapshotCache;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchableImpl;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class PackageUserStateImpl extends WatchableImpl implements PackageUserStateInternal, Snappable {
    public long mCeDataInode;
    public WatchedArrayMap mComponentLabelIconOverrideMap;
    public WatchedArraySet mDisabledComponentsWatched;
    public int mDistractionFlags;
    public WatchedArraySet mEnabledComponentsWatched;
    public int mEnabledState;
    public long mFirstInstallTime;
    public String mHarmfulAppWarning;
    public boolean mHidden;
    public int mInstallReason;
    public boolean mInstalled;
    public boolean mInstantApp;
    public String mLastDisableAppCaller;
    public boolean mNotLaunched;
    public OverlayPaths mOverlayPaths;
    public WatchedArrayMap mSharedLibraryOverlayPaths;
    public final SnapshotCache mSnapshot;
    public String mSplashScreenTheme;
    public boolean mStopped;
    public WatchedArrayMap mSuspendParams;
    public int mUninstallReason;
    public boolean mVirtualPreload;
    public Watchable mWatchable;

    public final boolean snapshotEquals(SnapshotCache snapshotCache) {
        return true;
    }

    public final int snapshotHashCode() {
        return 0;
    }

    public final boolean watchableEquals(Watchable watchable) {
        return true;
    }

    public final int watchableHashCode() {
        return 0;
    }

    public final SnapshotCache makeCache() {
        return new SnapshotCache(this, this) { // from class: com.android.server.pm.pkg.PackageUserStateImpl.1
            @Override // com.android.server.utils.SnapshotCache
            public PackageUserStateImpl createSnapshot() {
                return new PackageUserStateImpl(PackageUserStateImpl.this.mWatchable, (PackageUserStateImpl) this.mSource);
            }
        };
    }

    public PackageUserStateImpl(Watchable watchable) {
        this.mInstalled = true;
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mWatchable = watchable;
        this.mSnapshot = makeCache();
    }

    public PackageUserStateImpl(Watchable watchable, PackageUserStateImpl packageUserStateImpl) {
        this.mInstalled = true;
        this.mEnabledState = 0;
        this.mInstallReason = 0;
        this.mUninstallReason = 0;
        this.mWatchable = watchable;
        WatchedArraySet watchedArraySet = packageUserStateImpl.mDisabledComponentsWatched;
        this.mDisabledComponentsWatched = watchedArraySet == null ? null : watchedArraySet.snapshot();
        WatchedArraySet watchedArraySet2 = packageUserStateImpl.mEnabledComponentsWatched;
        this.mEnabledComponentsWatched = watchedArraySet2 == null ? null : watchedArraySet2.snapshot();
        this.mOverlayPaths = packageUserStateImpl.mOverlayPaths;
        WatchedArrayMap watchedArrayMap = packageUserStateImpl.mSharedLibraryOverlayPaths;
        this.mSharedLibraryOverlayPaths = watchedArrayMap == null ? null : watchedArrayMap.snapshot();
        this.mCeDataInode = packageUserStateImpl.mCeDataInode;
        this.mInstalled = packageUserStateImpl.mInstalled;
        this.mStopped = packageUserStateImpl.mStopped;
        this.mNotLaunched = packageUserStateImpl.mNotLaunched;
        this.mHidden = packageUserStateImpl.mHidden;
        this.mDistractionFlags = packageUserStateImpl.mDistractionFlags;
        this.mInstantApp = packageUserStateImpl.mInstantApp;
        this.mVirtualPreload = packageUserStateImpl.mVirtualPreload;
        this.mEnabledState = packageUserStateImpl.mEnabledState;
        this.mInstallReason = packageUserStateImpl.mInstallReason;
        this.mUninstallReason = packageUserStateImpl.mUninstallReason;
        this.mHarmfulAppWarning = packageUserStateImpl.mHarmfulAppWarning;
        this.mLastDisableAppCaller = packageUserStateImpl.mLastDisableAppCaller;
        this.mSplashScreenTheme = packageUserStateImpl.mSplashScreenTheme;
        WatchedArrayMap watchedArrayMap2 = packageUserStateImpl.mSuspendParams;
        this.mSuspendParams = watchedArrayMap2 == null ? null : watchedArrayMap2.snapshot();
        WatchedArrayMap watchedArrayMap3 = packageUserStateImpl.mComponentLabelIconOverrideMap;
        this.mComponentLabelIconOverrideMap = watchedArrayMap3 != null ? watchedArrayMap3.snapshot() : null;
        this.mFirstInstallTime = packageUserStateImpl.mFirstInstallTime;
        this.mSnapshot = new SnapshotCache.Sealed();
    }

    public final void onChanged() {
        Watchable watchable = this.mWatchable;
        if (watchable != null) {
            watchable.dispatchChange(watchable);
        }
        dispatchChange(this);
    }

    @Override // com.android.server.utils.Snappable
    public PackageUserStateImpl snapshot() {
        return (PackageUserStateImpl) this.mSnapshot.snapshot();
    }

    public boolean setOverlayPaths(OverlayPaths overlayPaths) {
        if (Objects.equals(overlayPaths, this.mOverlayPaths)) {
            return false;
        }
        if ((this.mOverlayPaths == null && overlayPaths.isEmpty()) || (overlayPaths == null && this.mOverlayPaths.isEmpty())) {
            return false;
        }
        this.mOverlayPaths = overlayPaths;
        onChanged();
        return true;
    }

    public boolean setSharedLibraryOverlayPaths(String str, OverlayPaths overlayPaths) {
        if (this.mSharedLibraryOverlayPaths == null) {
            WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
            this.mSharedLibraryOverlayPaths = watchedArrayMap;
            watchedArrayMap.registerObserver(this.mSnapshot);
        }
        if (Objects.equals(overlayPaths, (OverlayPaths) this.mSharedLibraryOverlayPaths.get(str))) {
            return false;
        }
        if (overlayPaths == null || overlayPaths.isEmpty()) {
            boolean z = this.mSharedLibraryOverlayPaths.remove(str) != null;
            onChanged();
            return z;
        }
        this.mSharedLibraryOverlayPaths.put(str, overlayPaths);
        onChanged();
        return true;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public WatchedArraySet getDisabledComponentsNoCopy() {
        return this.mDisabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public WatchedArraySet getEnabledComponentsNoCopy() {
        return this.mEnabledComponentsWatched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    /* renamed from: getDisabledComponents, reason: merged with bridge method [inline-methods] */
    public ArraySet m10013getDisabledComponents() {
        WatchedArraySet watchedArraySet = this.mDisabledComponentsWatched;
        return watchedArraySet == null ? new ArraySet() : watchedArraySet.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    /* renamed from: getEnabledComponents, reason: merged with bridge method [inline-methods] */
    public ArraySet m10014getEnabledComponents() {
        WatchedArraySet watchedArraySet = this.mEnabledComponentsWatched;
        return watchedArraySet == null ? new ArraySet() : watchedArraySet.untrackedStorage();
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentEnabled(String str) {
        WatchedArraySet watchedArraySet = this.mEnabledComponentsWatched;
        return watchedArraySet != null && watchedArraySet.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isComponentDisabled(String str) {
        WatchedArraySet watchedArraySet = this.mDisabledComponentsWatched;
        return watchedArraySet != null && watchedArraySet.contains(str);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public OverlayPaths getAllOverlayPaths() {
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

    public boolean overrideLabelAndIcon(ComponentName componentName, String str, Integer num) {
        Integer num2;
        String str2;
        Pair pair;
        WatchedArrayMap watchedArrayMap = this.mComponentLabelIconOverrideMap;
        if (watchedArrayMap == null || (pair = (Pair) watchedArrayMap.get(componentName)) == null) {
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
                if (this.mComponentLabelIconOverrideMap.isEmpty()) {
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
            onChanged();
        }
        return z;
    }

    public void resetOverrideComponentLabelIcon() {
        this.mComponentLabelIconOverrideMap = null;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public Pair getOverrideLabelIconForComponent(ComponentName componentName) {
        if (ArrayUtils.isEmpty(this.mComponentLabelIconOverrideMap)) {
            return null;
        }
        return (Pair) this.mComponentLabelIconOverrideMap.get(componentName);
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isSuspended() {
        return !CollectionUtils.isEmpty(this.mSuspendParams);
    }

    public PackageUserStateImpl putSuspendParams(String str, SuspendParams suspendParams) {
        if (this.mSuspendParams == null) {
            WatchedArrayMap watchedArrayMap = new WatchedArrayMap();
            this.mSuspendParams = watchedArrayMap;
            watchedArrayMap.registerObserver(this.mSnapshot);
        }
        if (!this.mSuspendParams.containsKey(str) || !Objects.equals(this.mSuspendParams.get(str), suspendParams)) {
            this.mSuspendParams.put(str, suspendParams);
            onChanged();
        }
        return this;
    }

    public PackageUserStateImpl removeSuspension(String str) {
        WatchedArrayMap watchedArrayMap = this.mSuspendParams;
        if (watchedArrayMap != null) {
            watchedArrayMap.remove(str);
            onChanged();
        }
        return this;
    }

    public PackageUserStateImpl setDisabledComponents(ArraySet arraySet) {
        if (arraySet == null) {
            return this;
        }
        if (this.mDisabledComponentsWatched == null) {
            WatchedArraySet watchedArraySet = new WatchedArraySet();
            this.mDisabledComponentsWatched = watchedArraySet;
            watchedArraySet.registerObserver(this.mSnapshot);
        }
        this.mDisabledComponentsWatched.clear();
        this.mDisabledComponentsWatched.addAll(arraySet);
        onChanged();
        return this;
    }

    public PackageUserStateImpl setEnabledComponents(ArraySet arraySet) {
        if (arraySet == null) {
            return this;
        }
        if (this.mEnabledComponentsWatched == null) {
            WatchedArraySet watchedArraySet = new WatchedArraySet();
            this.mEnabledComponentsWatched = watchedArraySet;
            watchedArraySet.registerObserver(this.mSnapshot);
        }
        this.mEnabledComponentsWatched.clear();
        this.mEnabledComponentsWatched.addAll(arraySet);
        onChanged();
        return this;
    }

    public PackageUserStateImpl setCeDataInode(long j) {
        this.mCeDataInode = j;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setInstalled(boolean z) {
        this.mInstalled = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setStopped(boolean z) {
        this.mStopped = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setNotLaunched(boolean z) {
        this.mNotLaunched = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setHidden(boolean z) {
        this.mHidden = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setDistractionFlags(int i) {
        this.mDistractionFlags = i;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setInstantApp(boolean z) {
        this.mInstantApp = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setVirtualPreload(boolean z) {
        this.mVirtualPreload = z;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setEnabledState(int i) {
        this.mEnabledState = i;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setInstallReason(int i) {
        this.mInstallReason = i;
        AnnotationValidations.validate(PackageManager.InstallReason.class, (Annotation) null, i);
        onChanged();
        return this;
    }

    public PackageUserStateImpl setUninstallReason(int i) {
        this.mUninstallReason = i;
        AnnotationValidations.validate(PackageManager.UninstallReason.class, (Annotation) null, i);
        onChanged();
        return this;
    }

    public PackageUserStateImpl setHarmfulAppWarning(String str) {
        this.mHarmfulAppWarning = str;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setLastDisableAppCaller(String str) {
        this.mLastDisableAppCaller = str;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setSplashScreenTheme(String str) {
        this.mSplashScreenTheme = str;
        onChanged();
        return this;
    }

    public PackageUserStateImpl setSuspendParams(ArrayMap arrayMap) {
        if (arrayMap == null) {
            return this;
        }
        if (this.mSuspendParams == null) {
            this.mSuspendParams = new WatchedArrayMap();
            registerObserver(this.mSnapshot);
        }
        this.mSuspendParams.clear();
        this.mSuspendParams.putAll(arrayMap);
        onChanged();
        return this;
    }

    public PackageUserStateImpl setFirstInstallTime(long j) {
        this.mFirstInstallTime = j;
        onChanged();
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public Map getSharedLibraryOverlayPaths() {
        WatchedArrayMap watchedArrayMap = this.mSharedLibraryOverlayPaths;
        return watchedArrayMap == null ? Collections.emptyMap() : watchedArrayMap;
    }

    public PackageUserStateImpl setWatchable(Watchable watchable) {
        this.mWatchable = watchable;
        return this;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getCeDataInode() {
        return this.mCeDataInode;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstalled() {
        return this.mInstalled;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isStopped() {
        return this.mStopped;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isNotLaunched() {
        return this.mNotLaunched;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isHidden() {
        return this.mHidden;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getDistractionFlags() {
        return this.mDistractionFlags;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isInstantApp() {
        return this.mInstantApp;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public boolean isVirtualPreload() {
        return this.mVirtualPreload;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getEnabledState() {
        return this.mEnabledState;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getInstallReason() {
        return this.mInstallReason;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public int getUninstallReason() {
        return this.mUninstallReason;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public String getHarmfulAppWarning() {
        return this.mHarmfulAppWarning;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public String getLastDisableAppCaller() {
        return this.mLastDisableAppCaller;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public OverlayPaths getOverlayPaths() {
        return this.mOverlayPaths;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public String getSplashScreenTheme() {
        return this.mSplashScreenTheme;
    }

    @Override // com.android.server.pm.pkg.PackageUserStateInternal
    public WatchedArrayMap getSuspendParams() {
        return this.mSuspendParams;
    }

    @Override // com.android.server.pm.pkg.PackageUserState
    public long getFirstInstallTimeMillis() {
        return this.mFirstInstallTime;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PackageUserStateImpl packageUserStateImpl = (PackageUserStateImpl) obj;
        return Objects.equals(this.mDisabledComponentsWatched, packageUserStateImpl.mDisabledComponentsWatched) && Objects.equals(this.mEnabledComponentsWatched, packageUserStateImpl.mEnabledComponentsWatched) && this.mCeDataInode == packageUserStateImpl.mCeDataInode && this.mInstalled == packageUserStateImpl.mInstalled && this.mStopped == packageUserStateImpl.mStopped && this.mNotLaunched == packageUserStateImpl.mNotLaunched && this.mHidden == packageUserStateImpl.mHidden && this.mDistractionFlags == packageUserStateImpl.mDistractionFlags && this.mInstantApp == packageUserStateImpl.mInstantApp && this.mVirtualPreload == packageUserStateImpl.mVirtualPreload && this.mEnabledState == packageUserStateImpl.mEnabledState && this.mInstallReason == packageUserStateImpl.mInstallReason && this.mUninstallReason == packageUserStateImpl.mUninstallReason && Objects.equals(this.mHarmfulAppWarning, packageUserStateImpl.mHarmfulAppWarning) && Objects.equals(this.mLastDisableAppCaller, packageUserStateImpl.mLastDisableAppCaller) && Objects.equals(this.mOverlayPaths, packageUserStateImpl.mOverlayPaths) && Objects.equals(this.mSharedLibraryOverlayPaths, packageUserStateImpl.mSharedLibraryOverlayPaths) && Objects.equals(this.mSplashScreenTheme, packageUserStateImpl.mSplashScreenTheme) && Objects.equals(this.mSuspendParams, packageUserStateImpl.mSuspendParams) && Objects.equals(this.mComponentLabelIconOverrideMap, packageUserStateImpl.mComponentLabelIconOverrideMap) && this.mFirstInstallTime == packageUserStateImpl.mFirstInstallTime && watchableEquals(packageUserStateImpl.mWatchable) && snapshotEquals(packageUserStateImpl.mSnapshot);
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((((((Objects.hashCode(this.mDisabledComponentsWatched) + 31) * 31) + Objects.hashCode(this.mEnabledComponentsWatched)) * 31) + Long.hashCode(this.mCeDataInode)) * 31) + Boolean.hashCode(this.mInstalled)) * 31) + Boolean.hashCode(this.mStopped)) * 31) + Boolean.hashCode(this.mNotLaunched)) * 31) + Boolean.hashCode(this.mHidden)) * 31) + this.mDistractionFlags) * 31) + Boolean.hashCode(this.mInstantApp)) * 31) + Boolean.hashCode(this.mVirtualPreload)) * 31) + this.mEnabledState) * 31) + this.mInstallReason) * 31) + this.mUninstallReason) * 31) + Objects.hashCode(this.mHarmfulAppWarning)) * 31) + Objects.hashCode(this.mLastDisableAppCaller)) * 31) + Objects.hashCode(this.mOverlayPaths)) * 31) + Objects.hashCode(this.mSharedLibraryOverlayPaths)) * 31) + Objects.hashCode(this.mSplashScreenTheme)) * 31) + Objects.hashCode(this.mSuspendParams)) * 31) + Objects.hashCode(this.mComponentLabelIconOverrideMap)) * 31) + Long.hashCode(this.mFirstInstallTime)) * 31) + watchableHashCode()) * 31) + snapshotHashCode();
    }
}
