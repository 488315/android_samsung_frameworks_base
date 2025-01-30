package com.android.server.pm.split;

import android.content.pm.parsing.ApkLiteParseUtils;
import android.content.pm.parsing.PackageLite;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.SparseArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class SplitAssetDependencyLoader extends SplitDependencyLoader implements SplitAssetLoader {
  public final AssetManager[] mCachedAssetManagers;
  public final ApkAssets[][] mCachedSplitApks;
  public final int mFlags;
  public final String[] mSplitPaths;

  public SplitAssetDependencyLoader(PackageLite packageLite, SparseArray sparseArray, int i) {
    super(sparseArray);
    String[] strArr = new String[packageLite.getSplitApkPaths().length + 1];
    this.mSplitPaths = strArr;
    strArr[0] = packageLite.getBaseApkPath();
    System.arraycopy(
        packageLite.getSplitApkPaths(), 0, strArr, 1, packageLite.getSplitApkPaths().length);
    this.mFlags = i;
    this.mCachedSplitApks = new ApkAssets[strArr.length][];
    this.mCachedAssetManagers = new AssetManager[strArr.length];
  }

  public boolean isSplitCached(int i) {
    return this.mCachedAssetManagers[i] != null;
  }

  public static ApkAssets loadApkAssets(String str, int i) {
    if ((i & 1) != 0 && !ApkLiteParseUtils.isApkPath(str)) {
      throw new IllegalArgumentException("Invalid package file: " + str);
    }
    try {
      return ApkAssets.loadFromPath(str);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to load APK at path " + str, e);
    }
  }

  public static AssetManager createAssetManagerWithAssets(ApkAssets[] apkAssetsArr) {
    AssetManager assetManager = new AssetManager();
    assetManager.setConfiguration(
        0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Build.VERSION.RESOURCES_SDK_INT);
    assetManager.setApkAssets(apkAssetsArr, false);
    return assetManager;
  }

  public void constructSplit(int i, int[] iArr, int i2) {
    ArrayList arrayList = new ArrayList();
    if (i2 >= 0) {
      Collections.addAll(arrayList, this.mCachedSplitApks[i2]);
    }
    arrayList.add(loadApkAssets(this.mSplitPaths[i], this.mFlags));
    for (int i3 : iArr) {
      arrayList.add(loadApkAssets(this.mSplitPaths[i3], this.mFlags));
    }
    this.mCachedSplitApks[i] = (ApkAssets[]) arrayList.toArray(new ApkAssets[arrayList.size()]);
    this.mCachedAssetManagers[i] = createAssetManagerWithAssets(this.mCachedSplitApks[i]);
  }

  @Override // com.android.server.pm.split.SplitAssetLoader
  public AssetManager getBaseAssetManager() {
    loadDependenciesForSplit(0);
    return this.mCachedAssetManagers[0];
  }

  @Override // com.android.server.pm.split.SplitAssetLoader
  public AssetManager getSplitAssetManager(int i) {
    int i2 = i + 1;
    loadDependenciesForSplit(i2);
    return this.mCachedAssetManagers[i2];
  }

  @Override // com.android.server.pm.split.SplitAssetLoader
  public ApkAssets getBaseApkAssets() {
    return this.mCachedSplitApks[0][0];
  }

  @Override // java.lang.AutoCloseable
  public void close() {
    for (AssetManager assetManager : this.mCachedAssetManagers) {
      IoUtils.closeQuietly(assetManager);
    }
  }
}
