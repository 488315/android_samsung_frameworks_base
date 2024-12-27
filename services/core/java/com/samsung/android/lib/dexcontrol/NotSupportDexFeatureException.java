package com.samsung.android.lib.dexcontrol;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class NotSupportDexFeatureException extends Exception {
    private int pid;

    public NotSupportDexFeatureException(int i) {
        this.pid = i;
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        int i = this.pid;
        return i != 40992
                ? i != 41001
                        ? "your dex does not support this feature"
                        : "DeX Pad does not support this feature"
                : "DeX Station does not support this feature";
    }
}
