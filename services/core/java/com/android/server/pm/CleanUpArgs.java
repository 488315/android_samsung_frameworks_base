package com.android.server.pm;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CleanUpArgs {
    public final File mCodeFile;
    public final String[] mInstructionSets;
    public final String mPackageName;

    public CleanUpArgs(String[] strArr, String str, String str2) {
        this.mPackageName = str;
        this.mCodeFile = new File(str2);
        this.mInstructionSets = strArr;
    }
}
