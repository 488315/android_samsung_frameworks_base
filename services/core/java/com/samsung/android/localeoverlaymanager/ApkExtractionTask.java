package com.samsung.android.localeoverlaymanager;

import java.lang.ref.WeakReference;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
