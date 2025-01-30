package com.samsung.android.lib.dexcontrol.utils;

/* loaded from: classes2.dex */
public class ErrorData {
    public int mDexFanErrorCntr;
    public int mUvdmOpenErrorCntr;
    public int mUvdmReadErrorCntr;
    public int mUvdmWriteErrorCntr;

    public void reset() {
        this.mUvdmOpenErrorCntr = 0;
        this.mUvdmWriteErrorCntr = 0;
        this.mUvdmReadErrorCntr = 0;
        this.mDexFanErrorCntr = 0;
    }

    public ErrorData() {
        reset();
    }

    public void count(int i) {
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

    public int getCountNumber(int i) {
        if (i == -5) {
            return this.mDexFanErrorCntr;
        }
        if (i == -3) {
            return this.mUvdmReadErrorCntr;
        }
        if (i == -2) {
            return this.mUvdmWriteErrorCntr;
        }
        if (i != -1) {
            return 0;
        }
        return this.mUvdmOpenErrorCntr;
    }
}
