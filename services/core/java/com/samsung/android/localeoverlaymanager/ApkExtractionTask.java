package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class ApkExtractionTask {
    public WeakReference mContextRef;
    public Thread mCurrentThread;
    public Set mExtractedLocaleApks = new HashSet();
    public ApkExtractorRunnable mExtractorRunnable = new ApkExtractorRunnable(this, new SevenZFileCopier());
    public Set mLocaleLanguages;
    public boolean mShouldReplace;
    public String mTargetPackage;

    public Runnable getRunnable() {
        return this.mExtractorRunnable;
    }

    public Thread getCurrentThread() {
        Thread thread;
        synchronized (ApkExtractionManager.getInstance()) {
            thread = this.mCurrentThread;
        }
        return thread;
    }

    public void initializeTask(String str, Set set, Context context, boolean z) {
        this.mTargetPackage = str;
        this.mContextRef = new WeakReference(context);
        this.mLocaleLanguages = set;
        this.mShouldReplace = z;
        this.mExtractedLocaleApks.clear();
    }

    public WeakReference getContextRef() {
        return this.mContextRef;
    }

    public String getTargetPackage() {
        return this.mTargetPackage;
    }

    public Set getLocaleLanguages() {
        return this.mLocaleLanguages;
    }

    public void onTaskFailed() {
        ApkExtractionManager.getInstance().handleState(this, 0);
    }

    public void onTaskComplete() {
        ApkExtractionManager.getInstance().handleState(this, 1);
    }

    public String toString() {
        return "ApkExtractionTask{mTargetPackage='" + this.mTargetPackage + "', mLocaleLanguages=" + this.mLocaleLanguages + ", mShouldReplace=" + this.mShouldReplace + ", mContextRef=" + this.mContextRef + ", mExtractorRunnable=" + this.mExtractorRunnable + ", mCurrentThread=" + this.mCurrentThread + ", mExtractedLocaleApks=" + this.mExtractedLocaleApks + '}';
    }

    public Set getExtractedLocaleApks() {
        return this.mExtractedLocaleApks;
    }

    public void onApkExtracted(String str) {
        onApkExtracted(str, false);
    }

    public void onApkExtracted(String str, boolean z) {
        this.mExtractedLocaleApks.add(str);
        if (z) {
            ApkExtractionManager.getInstance().setForceEnable();
        }
    }

    public boolean shouldReplace() {
        return this.mShouldReplace;
    }
}
