package com.android.server.wm;

import android.util.ArrayMap;
import android.util.ArraySet;

import java.util.ArrayList;

public final class AnimatingActivityRegistry {
    public ArraySet mAnimatingActivities;
    public boolean mEndingDeferredFinish;
    public ArrayMap mFinishedTokens;
    public ArrayList mTmpRunnableList;

    public final void endDeferringFinished() {
        if (this.mEndingDeferredFinish) {
            return;
        }
        try {
            this.mEndingDeferredFinish = true;
            for (int size = this.mFinishedTokens.size() - 1; size >= 0; size--) {
                this.mTmpRunnableList.add((Runnable) this.mFinishedTokens.valueAt(size));
            }
            this.mFinishedTokens.clear();
            for (int size2 = this.mTmpRunnableList.size() - 1; size2 >= 0; size2--) {
                ((Runnable) this.mTmpRunnableList.get(size2)).run();
            }
            this.mTmpRunnableList.clear();
            this.mEndingDeferredFinish = false;
        } catch (Throwable th) {
            this.mEndingDeferredFinish = false;
            throw th;
        }
    }
}
