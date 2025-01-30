package com.android.server.wm;

import android.util.ArrayMap;
import android.util.ArraySet;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AnimatingActivityRegistry {
    public boolean mEndingDeferredFinish;
    public ArraySet mAnimatingActivities = new ArraySet();
    public ArrayMap mFinishedTokens = new ArrayMap();
    public ArrayList mTmpRunnableList = new ArrayList();

    public void notifyStarting(ActivityRecord activityRecord) {
        this.mAnimatingActivities.add(activityRecord);
    }

    public void notifyFinished(ActivityRecord activityRecord) {
        this.mAnimatingActivities.remove(activityRecord);
        this.mFinishedTokens.remove(activityRecord);
        if (this.mAnimatingActivities.isEmpty()) {
            endDeferringFinished();
        }
    }

    public boolean notifyAboutToFinish(ActivityRecord activityRecord, Runnable runnable) {
        if (!this.mAnimatingActivities.remove(activityRecord)) {
            return false;
        }
        if (this.mAnimatingActivities.isEmpty()) {
            endDeferringFinished();
            return false;
        }
        this.mFinishedTokens.put(activityRecord, runnable);
        return true;
    }

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
        } finally {
            this.mEndingDeferredFinish = false;
        }
    }

    public void dump(PrintWriter printWriter, String str, String str2) {
        if (this.mAnimatingActivities.isEmpty() && this.mFinishedTokens.isEmpty()) {
            return;
        }
        printWriter.print(str2);
        printWriter.println(str);
        String str3 = str2 + "  ";
        printWriter.print(str3);
        printWriter.print("mAnimatingActivities=");
        printWriter.println(this.mAnimatingActivities);
        printWriter.print(str3);
        printWriter.print("mFinishedTokens=");
        printWriter.println(this.mFinishedTokens);
    }
}
