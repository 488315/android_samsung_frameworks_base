package com.android.internal.pm.split;

import android.content.res.ApkAssets;
import android.content.res.AssetManager;

public interface SplitAssetLoader extends AutoCloseable {
    ApkAssets getBaseApkAssets();

    AssetManager getBaseAssetManager() throws IllegalArgumentException;

    AssetManager getSplitAssetManager(int i) throws IllegalArgumentException;
}
