package com.samsung.android.lib.dexcontrol.utils;

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
