package com.android.server.pm;

import com.android.server.utils.SnapshotCache;
import com.android.server.utils.WatchedArraySet;
import com.android.server.utils.WatchedSparseBooleanMatrix;
import com.android.server.utils.WatchedSparseSetArray;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class AppsFilterSnapshotImpl extends AppsFilterBase {
  public AppsFilterSnapshotImpl(AppsFilterImpl appsFilterImpl) {
    synchronized (appsFilterImpl.mImplicitlyQueryableLock) {
      this.mImplicitlyQueryable =
          (WatchedSparseSetArray) appsFilterImpl.mImplicitQueryableSnapshot.snapshot();
      this.mRetainedImplicitlyQueryable =
          (WatchedSparseSetArray) appsFilterImpl.mRetainedImplicitlyQueryableSnapshot.snapshot();
    }
    this.mImplicitQueryableSnapshot = new SnapshotCache.Sealed();
    this.mRetainedImplicitlyQueryableSnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mQueriesViaPackageLock) {
      this.mQueriesViaPackage =
          (WatchedSparseSetArray) appsFilterImpl.mQueriesViaPackageSnapshot.snapshot();
    }
    this.mQueriesViaPackageSnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mQueriesViaComponentLock) {
      this.mQueriesViaComponent =
          (WatchedSparseSetArray) appsFilterImpl.mQueriesViaComponentSnapshot.snapshot();
    }
    this.mQueriesViaComponentSnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mQueryableViaUsesLibraryLock) {
      this.mQueryableViaUsesLibrary =
          (WatchedSparseSetArray) appsFilterImpl.mQueryableViaUsesLibrarySnapshot.snapshot();
    }
    this.mQueryableViaUsesLibrarySnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mQueryableViaUsesPermissionLock) {
      this.mQueryableViaUsesPermission =
          (WatchedSparseSetArray) appsFilterImpl.mQueryableViaUsesPermissionSnapshot.snapshot();
    }
    this.mQueryableViaUsesPermissionSnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mForceQueryableLock) {
      this.mForceQueryable = (WatchedArraySet) appsFilterImpl.mForceQueryableSnapshot.snapshot();
    }
    this.mForceQueryableSnapshot = new SnapshotCache.Sealed();
    synchronized (appsFilterImpl.mProtectedBroadcastsLock) {
      this.mProtectedBroadcasts =
          (WatchedArraySet) appsFilterImpl.mProtectedBroadcastsSnapshot.snapshot();
    }
    this.mProtectedBroadcastsSnapshot = new SnapshotCache.Sealed();
    this.mQueriesViaComponentRequireRecompute = appsFilterImpl.mQueriesViaComponentRequireRecompute;
    String[] strArr = appsFilterImpl.mForceQueryableByDevicePackageNames;
    this.mForceQueryableByDevicePackageNames = (String[]) Arrays.copyOf(strArr, strArr.length);
    this.mSystemAppsQueryable = appsFilterImpl.mSystemAppsQueryable;
    this.mFeatureConfig = appsFilterImpl.mFeatureConfig.snapshot();
    this.mOverlayReferenceMapper = appsFilterImpl.mOverlayReferenceMapper;
    this.mSystemSigningDetails = appsFilterImpl.mSystemSigningDetails;
    this.mCacheReady = appsFilterImpl.mCacheReady;
    if (this.mCacheReady) {
      synchronized (appsFilterImpl.mCacheLock) {
        this.mShouldFilterCache =
            (WatchedSparseBooleanMatrix) appsFilterImpl.mShouldFilterCacheSnapshot.snapshot();
      }
    } else {
      this.mShouldFilterCache = new WatchedSparseBooleanMatrix();
    }
    this.mCacheEnabled = appsFilterImpl.mCacheEnabled;
    this.mShouldFilterCacheSnapshot = new SnapshotCache.Sealed();
    this.mHandler = null;
  }
}
