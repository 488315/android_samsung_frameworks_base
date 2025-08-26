package com.samsung.android.sdk.scs.base.tasks;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.scs.base.ResultException;
import com.samsung.android.sdk.scs.base.feature.FeatureStatusCache;
import com.samsung.android.sdk.scs.base.utils.Log;

/* loaded from: classes4.dex */
public abstract class TaskRunnable implements Runnable {
    private static final String TAG = "ScsApi@TaskRunnable<>";
    private static final String TASK_DELIMITER = "#";
    private static final String THREAD_NAME_PREFIX = "scs";
    protected final TaskCompletionSource mSource;

    public TaskRunnable() {
        this(new TaskCompletionSource());
    }

    public final String createThreadName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String strConcat = str.split(TASK_DELIMITER)[0];
        if (!strConcat.startsWith(THREAD_NAME_PREFIX)) {
            strConcat = "scs-".concat(strConcat);
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(strConcat, TASK_DELIMITER);
        sbM.append(getClass().getSimpleName());
        sbM.append("@");
        sbM.append(Integer.toHexString(hashCode()));
        return sbM.toString();
    }

    public abstract void execute();

    public abstract String getFeatureName();

    public TaskCompletionSource getSource() {
        return this.mSource;
    }

    public Task getTask() {
        return this.mSource.task;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            boolean zInterrupted = Thread.interrupted();
            Thread.currentThread().setName(createThreadName(Thread.currentThread().getName()));
            Log.i(TAG, "run() - " + Thread.currentThread() + ", interrupt : " + zInterrupted);
            Integer num = (Integer) FeatureStatusCache.statusMap.get(getFeatureName());
            int iIntValue = num == null ? -1000 : num.intValue();
            if (iIntValue == 0 && !zInterrupted) {
                execute();
                return;
            }
            ResultException resultException = new ResultException(iIntValue, getFeatureName() + " is not available. statusCode: " + iIntValue + ", isInterrupted: " + zInterrupted);
            Log.i(TAG, resultException.getMessage());
            this.mSource.setException(resultException);
        } catch (Exception e) {
            android.util.Log.e(Log.concatPrefixTag(TAG), "Uncaught Exception!!!", e);
            TaskImpl taskImpl = this.mSource.task;
            taskImpl.getClass();
            synchronized (taskImpl.mLock) {
                try {
                    if (taskImpl.mComplete) {
                        return;
                    }
                    taskImpl.mComplete = true;
                    taskImpl.mException = e;
                    taskImpl.mListenersManager.processCompletion(taskImpl);
                } finally {
                }
            }
        }
    }

    public TaskRunnable(TaskCompletionSource taskCompletionSource) {
        this.mSource = taskCompletionSource;
    }
}
