package com.android.server.wm;

import android.os.Environment;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.io.File;

public final class BaseAppSnapshotPersister$PersistInfoProvider {
    public final String mDirName;
    public final ActivitySnapshotController$$ExternalSyntheticLambda1 mDirectoryResolver;
    public final boolean mEnableLowResSnapshots;
    public final float mLowResScaleFactor;
    public final boolean mUse16BitFormat;

    public BaseAppSnapshotPersister$PersistInfoProvider(
            ActivitySnapshotController$$ExternalSyntheticLambda1
                    activitySnapshotController$$ExternalSyntheticLambda1,
            String str,
            boolean z,
            float f,
            boolean z2) {
        this.mDirectoryResolver = activitySnapshotController$$ExternalSyntheticLambda1;
        this.mDirName = str;
        this.mEnableLowResSnapshots = z;
        this.mLowResScaleFactor = f;
        this.mUse16BitFormat = z2;
    }

    public final File getDirectory(int i) {
        getClass();
        return new File(Environment.getDataSystemCeDirectory(i), this.mDirName);
    }

    public final File getHighResolutionBitmapFile(int i, int i2) {
        return new File(getDirectory(i2), NandswapManager$$ExternalSyntheticOutline0.m(i, ".jpg"));
    }

    public final File getLowResolutionBitmapFile(int i, int i2) {
        return new File(
                getDirectory(i2), NandswapManager$$ExternalSyntheticOutline0.m(i, "_reduced.jpg"));
    }

    public final File getProtoFile(int i, int i2) {
        return new File(
                getDirectory(i2), NandswapManager$$ExternalSyntheticOutline0.m(i, ".proto"));
    }
}
