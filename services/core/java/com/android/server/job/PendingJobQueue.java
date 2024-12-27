package com.android.server.job;

import android.util.ArraySet;
import android.util.Pools;
import android.util.SparseArray;

import com.android.server.job.controllers.JobStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public final class PendingJobQueue {
    public final Pools.Pool mAppJobQueuePool = new Pools.SimplePool(8);
    public final SparseArray mCurrentQueues = new SparseArray();
    public final PriorityQueue mOrderedQueues =
            new PriorityQueue(new PendingJobQueue$$ExternalSyntheticLambda0(0));
    public int mSize = 0;
    public boolean mOptimizeIteration = true;
    public int mPullCount = 0;
    public boolean mNeedToResetIterators = false;

    public final class AppJobQueue {
        public static final PendingJobQueue$$ExternalSyntheticLambda0 sJobComparator =
                new PendingJobQueue$$ExternalSyntheticLambda0(1);
        public static final Pools.Pool mAdjustedJobStatusPool = new Pools.SimplePool(16);
        public final List mJobs = new ArrayList();
        public int mCurIndex = 0;

        public final class AdjustedJobStatus {
            public long adjustedEnqueueTime;
            public JobStatus job;
        }

        public final int indexOf(JobStatus jobStatus) {
            int size = ((ArrayList) this.mJobs).size();
            for (int i = 0; i < size; i++) {
                if (((AdjustedJobStatus) ((ArrayList) this.mJobs).get(i)).job == jobStatus) {
                    return i;
                }
            }
            return -1;
        }

        public final int peekNextOverrideState() {
            if (this.mCurIndex >= ((ArrayList) this.mJobs).size()) {
                return -1;
            }
            return ((AdjustedJobStatus) ((ArrayList) this.mJobs).get(this.mCurIndex))
                    .job
                    .overrideState;
        }

        public final long peekNextTimestamp() {
            if (this.mCurIndex >= ((ArrayList) this.mJobs).size()) {
                return -1L;
            }
            return ((AdjustedJobStatus) ((ArrayList) this.mJobs).get(this.mCurIndex))
                    .adjustedEnqueueTime;
        }
    }

    public final void add(JobStatus jobStatus) {
        AppJobQueue appJobQueue = getAppJobQueue(jobStatus.sourceUid, true);
        long peekNextTimestamp = appJobQueue.peekNextTimestamp();
        AppJobQueue.AdjustedJobStatus adjustedJobStatus =
                (AppJobQueue.AdjustedJobStatus) AppJobQueue.mAdjustedJobStatusPool.acquire();
        if (adjustedJobStatus == null) {
            adjustedJobStatus = new AppJobQueue.AdjustedJobStatus();
        }
        adjustedJobStatus.adjustedEnqueueTime = jobStatus.enqueueTime;
        adjustedJobStatus.job = jobStatus;
        int binarySearch =
                Collections.binarySearch(
                        appJobQueue.mJobs, adjustedJobStatus, AppJobQueue.sJobComparator);
        if (binarySearch < 0) {
            binarySearch = ~binarySearch;
        }
        ((ArrayList) appJobQueue.mJobs).add(binarySearch, adjustedJobStatus);
        if (binarySearch < appJobQueue.mCurIndex) {
            appJobQueue.mCurIndex = binarySearch;
        }
        if (binarySearch > 0) {
            adjustedJobStatus.adjustedEnqueueTime =
                    Math.max(
                            ((AppJobQueue.AdjustedJobStatus)
                                            ((ArrayList) appJobQueue.mJobs).get(binarySearch - 1))
                                    .adjustedEnqueueTime,
                            adjustedJobStatus.adjustedEnqueueTime);
        }
        int size = ((ArrayList) appJobQueue.mJobs).size();
        if (binarySearch < size - 1) {
            while (binarySearch < size) {
                AppJobQueue.AdjustedJobStatus adjustedJobStatus2 =
                        (AppJobQueue.AdjustedJobStatus)
                                ((ArrayList) appJobQueue.mJobs).get(binarySearch);
                long j = adjustedJobStatus.adjustedEnqueueTime;
                if (j < adjustedJobStatus2.adjustedEnqueueTime) {
                    break;
                }
                adjustedJobStatus2.adjustedEnqueueTime = j;
                binarySearch++;
            }
        }
        this.mSize++;
        if (peekNextTimestamp != appJobQueue.peekNextTimestamp()) {
            this.mOrderedQueues.remove(appJobQueue);
            this.mOrderedQueues.offer(appJobQueue);
        }
    }

    public final void addAll(ArraySet arraySet) {
        SparseArray sparseArray = new SparseArray();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
            List list = (List) sparseArray.get(jobStatus.sourceUid);
            if (list == null) {
                list = new ArrayList();
                sparseArray.put(jobStatus.sourceUid, list);
            }
            list.add(jobStatus);
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            AppJobQueue appJobQueue = getAppJobQueue(sparseArray.keyAt(size2), true);
            List list2 = (List) sparseArray.valueAt(size2);
            appJobQueue.getClass();
            int i = Integer.MAX_VALUE;
            for (int size3 = list2.size() - 1; size3 >= 0; size3--) {
                JobStatus jobStatus2 = (JobStatus) list2.get(size3);
                AppJobQueue.AdjustedJobStatus adjustedJobStatus =
                        (AppJobQueue.AdjustedJobStatus)
                                AppJobQueue.mAdjustedJobStatusPool.acquire();
                if (adjustedJobStatus == null) {
                    adjustedJobStatus = new AppJobQueue.AdjustedJobStatus();
                }
                adjustedJobStatus.adjustedEnqueueTime = jobStatus2.enqueueTime;
                adjustedJobStatus.job = jobStatus2;
                int binarySearch =
                        Collections.binarySearch(
                                appJobQueue.mJobs, adjustedJobStatus, AppJobQueue.sJobComparator);
                if (binarySearch < 0) {
                    binarySearch = ~binarySearch;
                }
                ((ArrayList) appJobQueue.mJobs).add(binarySearch, adjustedJobStatus);
                if (binarySearch < appJobQueue.mCurIndex) {
                    appJobQueue.mCurIndex = binarySearch;
                }
                i = Math.min(i, binarySearch);
            }
            int size4 = ((ArrayList) appJobQueue.mJobs).size();
            for (int max = Math.max(i, 1); max < size4; max++) {
                AppJobQueue.AdjustedJobStatus adjustedJobStatus2 =
                        (AppJobQueue.AdjustedJobStatus) ((ArrayList) appJobQueue.mJobs).get(max);
                adjustedJobStatus2.adjustedEnqueueTime =
                        Math.max(
                                adjustedJobStatus2.adjustedEnqueueTime,
                                ((AppJobQueue.AdjustedJobStatus)
                                                ((ArrayList) appJobQueue.mJobs).get(max - 1))
                                        .adjustedEnqueueTime);
            }
        }
        this.mSize = arraySet.size() + this.mSize;
        this.mOrderedQueues.clear();
    }

    public final boolean contains(JobStatus jobStatus) {
        AppJobQueue appJobQueue = (AppJobQueue) this.mCurrentQueues.get(jobStatus.sourceUid);
        return appJobQueue != null && appJobQueue.indexOf(jobStatus) >= 0;
    }

    public final AppJobQueue getAppJobQueue(int i, boolean z) {
        AppJobQueue appJobQueue = (AppJobQueue) this.mCurrentQueues.get(i);
        if (appJobQueue != null || !z) {
            return appJobQueue;
        }
        AppJobQueue appJobQueue2 = (AppJobQueue) this.mAppJobQueuePool.acquire();
        if (appJobQueue2 == null) {
            appJobQueue2 = new AppJobQueue();
        }
        AppJobQueue appJobQueue3 = appJobQueue2;
        this.mCurrentQueues.put(i, appJobQueue3);
        return appJobQueue3;
    }

    public final JobStatus next() {
        if (this.mNeedToResetIterators) {
            this.mOrderedQueues.clear();
            for (int size = this.mCurrentQueues.size() - 1; size >= 0; size--) {
                AppJobQueue appJobQueue = (AppJobQueue) this.mCurrentQueues.valueAt(size);
                appJobQueue.mCurIndex = 0;
                this.mOrderedQueues.offer(appJobQueue);
            }
            this.mNeedToResetIterators = false;
            this.mPullCount = 0;
        } else if (this.mOrderedQueues.size() == 0) {
            for (int size2 = this.mCurrentQueues.size() - 1; size2 >= 0; size2--) {
                this.mOrderedQueues.offer((AppJobQueue) this.mCurrentQueues.valueAt(size2));
            }
            this.mPullCount = 0;
        }
        int size3 = this.mOrderedQueues.size();
        JobStatus jobStatus = null;
        if (size3 == 0) {
            return null;
        }
        int min = this.mOptimizeIteration ? Math.min(3, ((size3 - 1) >>> 2) + 1) : 1;
        AppJobQueue appJobQueue2 = (AppJobQueue) this.mOrderedQueues.peek();
        if (appJobQueue2 != null) {
            if (appJobQueue2.mCurIndex < ((ArrayList) appJobQueue2.mJobs).size()) {
                List list = appJobQueue2.mJobs;
                int i = appJobQueue2.mCurIndex;
                appJobQueue2.mCurIndex = i + 1;
                jobStatus = ((AppJobQueue.AdjustedJobStatus) ((ArrayList) list).get(i)).job;
            }
            int i2 = this.mPullCount + 1;
            this.mPullCount = i2;
            if (i2 >= min
                    || ((jobStatus != null
                                    && appJobQueue2.peekNextOverrideState()
                                            != jobStatus.overrideState)
                            || appJobQueue2.peekNextTimestamp() == -1)) {
                this.mOrderedQueues.poll();
                if (appJobQueue2.peekNextTimestamp() != -1) {
                    this.mOrderedQueues.offer(appJobQueue2);
                }
                this.mPullCount = 0;
            }
        }
        return jobStatus;
    }

    public final boolean remove(JobStatus jobStatus) {
        AppJobQueue appJobQueue = getAppJobQueue(jobStatus.sourceUid, false);
        if (appJobQueue == null) {
            return false;
        }
        long peekNextTimestamp = appJobQueue.peekNextTimestamp();
        int indexOf = appJobQueue.indexOf(jobStatus);
        if (indexOf < 0) {
            return false;
        }
        AppJobQueue.AdjustedJobStatus adjustedJobStatus =
                (AppJobQueue.AdjustedJobStatus) ((ArrayList) appJobQueue.mJobs).remove(indexOf);
        adjustedJobStatus.adjustedEnqueueTime = 0L;
        adjustedJobStatus.job = null;
        AppJobQueue.mAdjustedJobStatusPool.release(adjustedJobStatus);
        int i = appJobQueue.mCurIndex;
        if (indexOf < i) {
            appJobQueue.mCurIndex = i - 1;
        }
        this.mSize--;
        if (((ArrayList) appJobQueue.mJobs).size() == 0) {
            this.mCurrentQueues.remove(jobStatus.sourceUid);
            this.mOrderedQueues.remove(appJobQueue);
            ((ArrayList) appJobQueue.mJobs).clear();
            appJobQueue.mCurIndex = 0;
            this.mAppJobQueuePool.release(appJobQueue);
        } else if (peekNextTimestamp != appJobQueue.peekNextTimestamp()) {
            this.mOrderedQueues.remove(appJobQueue);
            this.mOrderedQueues.offer(appJobQueue);
        }
        return true;
    }

    public void setOptimizeIteration(boolean z) {
        this.mOptimizeIteration = z;
    }
}
