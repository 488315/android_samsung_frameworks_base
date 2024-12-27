package com.samsung.android.localeoverlaymanager;

import java.lang.ref.WeakReference;
import java.util.Set;

public final class ApkExtractionTask {
    public WeakReference mContextRef;
    public Set mExtractedLocaleApks;
    public ApkExtractorRunnable mExtractorRunnable;
    public Set mLocaleLanguages;
    public boolean mShouldReplace;
    public String mTargetPackage;

    public final String toString() {
        return "ApkExtractionTask{mTargetPackage='"
                + this.mTargetPackage
                + "', mLocaleLanguages="
                + this.mLocaleLanguages
                + ", mShouldReplace="
                + this.mShouldReplace
                + ", mContextRef="
                + this.mContextRef
                + ", mExtractorRunnable="
                + this.mExtractorRunnable
                + ", mCurrentThread=null, mExtractedLocaleApks="
                + this.mExtractedLocaleApks
                + '}';
    }
}
