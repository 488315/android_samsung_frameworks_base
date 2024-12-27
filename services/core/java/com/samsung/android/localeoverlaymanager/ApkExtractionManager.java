package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.util.Log;

import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ApkExtractionManager {
    public ExtractionCompleteCallback mCallback;
    public WeakReference mContextRef;
    public boolean mForceEnable;
    public Set mLocalesToAdd;
    public boolean mShouldReplace;
    public final Queue mTasks;
    public final BlockingQueue mTasksWorkQueue;
    public final ThreadPoolExecutor mThreadPool;
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    public static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    public static final ApkExtractionManager sInstance = new ApkExtractionManager();
    public final List mTargetPackages = new ArrayList();
    public final Set mExtractedLocales = new HashSet();

    public ApkExtractionManager() {
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        this.mTasksWorkQueue = linkedBlockingQueue;
        this.mTasks = new LinkedBlockingQueue();
        TimeUnit timeUnit = KEEP_ALIVE_TIME_UNIT;
        int i = NUMBER_OF_CORES;
        this.mThreadPool = new ThreadPoolExecutor(i, i, 1L, timeUnit, linkedBlockingQueue);
    }

    public final void extractLocaleApks(Set set, Set set2, Context context, boolean z) {
        Set set3;
        Log.i(
                "ApkExtractionManager",
                "extractLocaleApks() called with: targetPackages = ["
                        + set
                        + "], context = ["
                        + context
                        + "]");
        if (!((ArrayList) this.mTargetPackages).isEmpty()) {
            LogWriter.logDebugInfoAndLogcat(
                    "ApkExtractionManager",
                    "Cancelling extraction for packages: " + this.mTargetPackages);
            ApkExtractionManager apkExtractionManager = sInstance;
            int size = ((LinkedBlockingQueue) apkExtractionManager.mTasksWorkQueue).size();
            ApkExtractorRunnable[] apkExtractorRunnableArr = new ApkExtractorRunnable[size];
            ((LinkedBlockingQueue) apkExtractionManager.mTasksWorkQueue)
                    .toArray(apkExtractorRunnableArr);
            synchronized (apkExtractionManager) {
                for (int i = 0; i < size; i++) {
                    try {
                        apkExtractorRunnableArr[i].mExtractionTask.getClass();
                        synchronized (sInstance) {
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            ((ArrayList) this.mTargetPackages).clear();
        }
        this.mContextRef = new WeakReference(context);
        ((ArrayList) this.mTargetPackages).addAll(set);
        ((HashSet) this.mExtractedLocales).clear();
        this.mLocalesToAdd = set2;
        this.mShouldReplace = z;
        this.mForceEnable = false;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                    "startExtraction() called with: targetPackage = [",
                    str,
                    "]",
                    "ApkExtractionManager");
            ApkExtractionManager apkExtractionManager2 = sInstance;
            ApkExtractionTask apkExtractionTask =
                    (ApkExtractionTask) ((LinkedBlockingQueue) apkExtractionManager2.mTasks).poll();
            if (apkExtractionTask == null) {
                apkExtractionTask = new ApkExtractionTask();
                apkExtractionTask.mExtractedLocaleApks = new HashSet();
                apkExtractionTask.mExtractorRunnable =
                        new ApkExtractorRunnable(apkExtractionTask, new SevenZFileCopier());
            }
            WeakReference weakReference = this.mContextRef;
            Context context2 = weakReference != null ? (Context) weakReference.get() : null;
            if (context2 == null || (set3 = this.mLocalesToAdd) == null) {
                Log.e(
                        "ApkExtractionManager",
                        "startExtraction: Context is "
                                + context2
                                + ", localesToAdd is "
                                + this.mLocalesToAdd);
            } else {
                boolean z2 = this.mShouldReplace;
                apkExtractionTask.mTargetPackage = str;
                apkExtractionTask.mContextRef = new WeakReference(context2);
                apkExtractionTask.mLocaleLanguages = set3;
                apkExtractionTask.mShouldReplace = z2;
                ((HashSet) apkExtractionTask.mExtractedLocaleApks).clear();
                apkExtractionManager2.mThreadPool.execute(apkExtractionTask.mExtractorRunnable);
            }
        }
    }

    public final synchronized void handleState(ApkExtractionTask apkExtractionTask, int i) {
        try {
            Log.i(
                    "ApkExtractionManager",
                    "handleState() called with: extractionTask = ["
                            + apkExtractionTask
                            + "], state = ["
                            + i
                            + "]");
            if (i == 1) {
                this.mExtractedLocales.addAll(apkExtractionTask.mExtractedLocaleApks);
            }
            onFinishTask(apkExtractionTask);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void onFinishTask(ApkExtractionTask apkExtractionTask) {
        ExtractionCompleteCallback extractionCompleteCallback;
        try {
            ((ArrayList) this.mTargetPackages).remove(apkExtractionTask.mTargetPackage);
            if (((ArrayList) this.mTargetPackages).isEmpty()
                    && (extractionCompleteCallback = this.mCallback) != null) {
                ((LocaleOverlayManager) extractionCompleteCallback)
                        .onExtractionComplete(this.mExtractedLocales, this.mForceEnable);
            }
            ((LinkedBlockingQueue) this.mTasks).offer(apkExtractionTask);
        } catch (Throwable th) {
            throw th;
        }
    }
}
