package com.android.server.knox.zt.devicetrust.task;

import com.samsung.android.knox.zt.devicetrust.IEndpointMonitorListener;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TaskRescheduler {
    public Map mTasks = new HashMap();
    public final Object mLock = new Object();

    public static String generateFingerprint(int... iArr) {
        StringJoiner stringJoiner = new StringJoiner(":", "", "");
        for (int i : iArr) {
            stringJoiner.add(Integer.toString(i));
        }
        return stringJoiner.toString();
    }

    public final void destroy(ReschedulableMonitoringTask reschedulableMonitoringTask) {
        synchronized (this.mLock) {
            this.mTasks.remove(reschedulableMonitoringTask.mFingerprint);
            reschedulableMonitoringTask.terminate();
        }
    }

    public final void keep(ReschedulableMonitoringTask reschedulableMonitoringTask) {
        synchronized (this.mLock) {
            reschedulableMonitoringTask.mListener = null;
            reschedulableMonitoringTask.setFilter(null);
            reschedulableMonitoringTask.mIsStandby = true;
            this.mTasks.put(reschedulableMonitoringTask.mFingerprint, reschedulableMonitoringTask);
        }
    }

    public final ReschedulableMonitoringTask reschedule(
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            IEndpointMonitorListener iEndpointMonitorListener,
            Predicate predicate) {
        ReschedulableMonitoringTask reschedulableMonitoringTask;
        String generateFingerprint = generateFingerprint(i, i2, i3, i4, i5);
        synchronized (this.mLock) {
            try {
                reschedulableMonitoringTask =
                        (ReschedulableMonitoringTask) this.mTasks.get(generateFingerprint);
                if (reschedulableMonitoringTask != null) {
                    reschedulableMonitoringTask.mListener = iEndpointMonitorListener;
                    reschedulableMonitoringTask.setFilter(predicate);
                    reschedulableMonitoringTask.mIsStandby = false;
                    this.mTasks.remove(generateFingerprint);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return reschedulableMonitoringTask;
    }
}
