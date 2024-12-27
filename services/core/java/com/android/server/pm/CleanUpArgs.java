package com.android.server.pm;

import java.io.File;

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
