package com.samsung.android.sdk.scs.base.tasks;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.samsung.android.sdk.scs.base.ResultException;
import com.samsung.android.sdk.scs.base.feature.FeatureStatusCache;
import com.samsung.android.sdk.scs.base.utils.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class TaskRunnable implements Runnable {
    public final TaskCompletionSource mSource;

    public TaskRunnable() {
        this(new TaskCompletionSource());
    }

    public final String createThreadName(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        String str2 = str.split("#")[0];
        if (!str2.startsWith("scs")) {
            str2 = "scs-".concat(str2);
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str2, "#");
        m2m.append(getClass().getSimpleName());
        m2m.append("@");
        m2m.append(Integer.toHexString(hashCode()));
        return m2m.toString();
    }

    public abstract void execute();

    public abstract String getFeatureName();

    @Override // java.lang.Runnable
    public final void run() {
        try {
            boolean interrupted = Thread.interrupted();
            Thread.currentThread().setName(createThreadName(Thread.currentThread().getName()));
            Log.m268i("ScsApi@TaskRunnable<>", "run() - " + Thread.currentThread() + ", interrupt : " + interrupted);
            Integer num = (Integer) FeatureStatusCache.statusMap.get(getFeatureName());
            int intValue = num == null ? -1000 : num.intValue();
            if (intValue == 0 && !interrupted) {
                execute();
                return;
            }
            ResultException resultException = new ResultException(intValue, getFeatureName() + " is not available. statusCode: " + intValue + ", isInterrupted: " + interrupted);
            Log.m268i("ScsApi@TaskRunnable<>", resultException.getMessage());
            this.mSource.setException(resultException);
        } catch (Exception e) {
            android.util.Log.e(Log.concatPrefixTag("ScsApi@TaskRunnable<>"), "Uncaught Exception!!!", e);
            this.mSource.setException(e);
        }
    }

    public TaskRunnable(TaskCompletionSource taskCompletionSource) {
        this.mSource = taskCompletionSource;
    }
}
