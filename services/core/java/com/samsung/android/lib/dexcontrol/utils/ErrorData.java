package com.samsung.android.lib.dexcontrol.utils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ErrorData {
    public int mDexFanErrorCntr;
    public int mUvdmOpenErrorCntr;
    public int mUvdmReadErrorCntr;
    public int mUvdmWriteErrorCntr;

    public final void count(int i) {
        if (i == -5) {
            this.mDexFanErrorCntr++;
            return;
        }
        if (i == -3) {
            this.mUvdmReadErrorCntr++;
        } else if (i == -2) {
            this.mUvdmWriteErrorCntr++;
        } else {
            if (i != -1) {
                return;
            }
            this.mUvdmOpenErrorCntr++;
        }
    }
}
