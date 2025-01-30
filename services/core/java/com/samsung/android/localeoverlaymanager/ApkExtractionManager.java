package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.util.Log;
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

/* loaded from: classes2.dex */
public class ApkExtractionManager {
    public static final String TAG = "ApkExtractionManager";
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
    public List mTargetPackages = new ArrayList();
    public Set mExtractedLocales = new HashSet();

    public ApkExtractionManager() {
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        this.mTasksWorkQueue = linkedBlockingQueue;
        this.mTasks = new LinkedBlockingQueue();
        int i = NUMBER_OF_CORES;
        this.mThreadPool = new ThreadPoolExecutor(i, i, 1L, KEEP_ALIVE_TIME_UNIT, linkedBlockingQueue);
    }

    public static ApkExtractionManager getInstance() {
        return sInstance;
    }

    public void setCallback(ExtractionCompleteCallback extractionCompleteCallback) {
        this.mCallback = extractionCompleteCallback;
    }

    public final synchronized void onFinishTask(ApkExtractionTask apkExtractionTask) {
        ExtractionCompleteCallback extractionCompleteCallback;
        this.mTargetPackages.remove(apkExtractionTask.getTargetPackage());
        if (this.mTargetPackages.isEmpty() && (extractionCompleteCallback = this.mCallback) != null) {
            extractionCompleteCallback.onExtractionComplete(this.mExtractedLocales, this.mForceEnable);
        }
        recycleTask(apkExtractionTask);
    }

    public void extractLocaleApks(Set set, Set set2, Context context) {
        extractLocaleApks(set, set2, context, false);
    }

    public void extractLocaleApks(Set set, Set set2, Context context, boolean z) {
        String str = TAG;
        Log.i(str, "extractLocaleApks() called with: targetPackages = [" + set + "], context = [" + context + "]");
        if (!this.mTargetPackages.isEmpty()) {
            LogWriter.logDebugInfoAndLogcat(str, "Cancelling extraction for packages: " + this.mTargetPackages);
            cancelCurrent();
            this.mTargetPackages.clear();
        }
        this.mContextRef = new WeakReference(context);
        this.mTargetPackages.addAll(set);
        this.mExtractedLocales.clear();
        this.mLocalesToAdd = set2;
        this.mShouldReplace = z;
        this.mForceEnable = false;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            startExtraction((String) it.next());
        }
    }

    public synchronized void handleState(ApkExtractionTask apkExtractionTask, int i) {
        Log.i(TAG, "handleState() called with: extractionTask = [" + apkExtractionTask + "], state = [" + i + "]");
        if (i == 1) {
            this.mExtractedLocales.addAll(apkExtractionTask.getExtractedLocaleApks());
        }
        onFinishTask(apkExtractionTask);
    }

    public void startExtraction(String str) {
        Set set;
        String str2 = TAG;
        Log.i(str2, "startExtraction() called with: targetPackage = [" + str + "]");
        ApkExtractionManager apkExtractionManager = sInstance;
        ApkExtractionTask apkExtractionTask = (ApkExtractionTask) apkExtractionManager.mTasks.poll();
        if (apkExtractionTask == null) {
            apkExtractionTask = new ApkExtractionTask();
        }
        WeakReference weakReference = this.mContextRef;
        Context context = weakReference != null ? (Context) weakReference.get() : null;
        if (context != null && (set = this.mLocalesToAdd) != null) {
            apkExtractionTask.initializeTask(str, set, context, this.mShouldReplace);
            apkExtractionManager.mThreadPool.execute(apkExtractionTask.getRunnable());
            return;
        }
        Log.e(str2, "startExtraction: Context is " + context + ", localesToAdd is " + this.mLocalesToAdd);
    }

    public void cancelCurrent() {
        ApkExtractionManager apkExtractionManager = sInstance;
        int size = apkExtractionManager.mTasksWorkQueue.size();
        ApkExtractorRunnable[] apkExtractorRunnableArr = new ApkExtractorRunnable[size];
        apkExtractionManager.mTasksWorkQueue.toArray(apkExtractorRunnableArr);
        synchronized (apkExtractionManager) {
            for (int i = 0; i < size; i++) {
                Thread currentThread = apkExtractorRunnableArr[i].getApkExtractionTask().getCurrentThread();
                if (currentThread != null) {
                    currentThread.interrupt();
                }
            }
        }
    }

    public void recycleTask(ApkExtractionTask apkExtractionTask) {
        this.mTasks.offer(apkExtractionTask);
    }

    public void release() {
        this.mTasks.clear();
        this.mCallback = null;
    }

    public void setForceEnable() {
        this.mForceEnable = true;
    }
}
