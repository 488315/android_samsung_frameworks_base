package com.samsung.android.sdk.scs.base.tasks;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.scs.base.ResultException;
import com.samsung.android.sdk.scs.base.feature.FeatureStatusCache;
import com.samsung.android.sdk.scs.base.utils.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String str2 = str.split(TASK_DELIMITER)[0];
        if (!str2.startsWith(THREAD_NAME_PREFIX)) {
            str2 = "scs-".concat(str2);
        }
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str2, TASK_DELIMITER);
        m.append(getClass().getSimpleName());
        m.append("@");
        m.append(Integer.toHexString(hashCode()));
        return m.toString();
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
            boolean interrupted = Thread.interrupted();
            Thread.currentThread().setName(createThreadName(Thread.currentThread().getName()));
            Log.i(TAG, "run() - " + Thread.currentThread() + ", interrupt : " + interrupted);
            Integer num = (Integer) FeatureStatusCache.statusMap.get(getFeatureName());
            int intValue = num == null ? -1000 : num.intValue();
            if (intValue == 0 && !interrupted) {
                execute();
                return;
            }
            ResultException resultException = new ResultException(intValue, getFeatureName() + " is not available. statusCode: " + intValue + ", isInterrupted: " + interrupted);
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
