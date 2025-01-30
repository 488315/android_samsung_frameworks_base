package com.android.server.pm.split;

import android.content.res.ApkAssets;
import android.content.res.AssetManager;

/* loaded from: classes3.dex */
public interface SplitAssetLoader extends AutoCloseable {
    ApkAssets getBaseApkAssets();

    AssetManager getBaseAssetManager();

    AssetManager getSplitAssetManager(int i);
}
