package com.android.server.pm;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OriginInfo {
    public final String mCid;
    public final boolean mExisting;
    public final File mFile;
    public final File mResolvedFile;
    public final String mResolvedPath;
    public final boolean mStaged;

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
