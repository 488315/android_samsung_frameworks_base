package com.android.server.pm;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import java.io.File;

/* loaded from: classes3.dex */
public final class OriginInfo {
    public final String mCid;
    public final boolean mExisting;
    public final File mFile;
    public final File mResolvedFile;
    public final String mResolvedPath;
    public final boolean mStaged;

    public static OriginInfo fromStagedContainer(String str) {
        return new OriginInfo(null, true, false, str);
    }

    public static OriginInfo fromNothing() {
        return new OriginInfo(null, false, false);
    }

    public static OriginInfo fromExistingFile(File file) {
        return new OriginInfo(file, false, true);
    }

    public static OriginInfo fromStagedFile(File file) {
        return new OriginInfo(file, true, false);
    }

    public OriginInfo(File file, boolean z, boolean z2) {
        this(file, z, z2, null);
    }

    public OriginInfo(File file, boolean z, boolean z2, String str) {
        this.mFile = file;
        this.mStaged = z;
        this.mExisting = z2;
        this.mCid = str;
        if (str != null) {
            String sdDir = PackageHelperExt.getSdDir(str);
            this.mResolvedPath = sdDir;
            this.mResolvedFile = new File(sdDir);
        } else if (file != null) {
            this.mResolvedPath = file.getAbsolutePath();
            this.mResolvedFile = file;
        } else {
            this.mResolvedPath = null;
            this.mResolvedFile = null;
        }
    }
}
