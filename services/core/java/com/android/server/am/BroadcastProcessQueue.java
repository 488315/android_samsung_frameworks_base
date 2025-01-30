package com.android.server.am;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.TimeUtils;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import dalvik.annotation.optimization.NeverCompile;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes.dex */
public class BroadcastProcessQueue {
  public ProcessRecord app;
  public final BroadcastConstants constants;
  public long lastCpuDelayTime;
  public int lastProcessState;
  public BroadcastRecord mActive;
  public int mActiveAssumedDeliveryCountSinceIdle;
  public int mActiveCountConsecutiveNormal;
  public int mActiveCountConsecutiveUrgent;
  public int mActiveCountSinceIdle;
  public int mActiveIndex;
  public boolean mActiveReEnqueued;
  public boolean mActiveViaColdStart;
  public boolean mActiveWasStopped;
  public String mCachedToShortString;
  public String mCachedToString;
  public int mCountAlarm;
  public int mCountDeferred;
  public int mCountEnqueued;
  public int mCountForeground;
  public int mCountForegroundDeferred;
  public int mCountInstrumented;
  public int mCountInteractive;
  public int mCountManifest;
  public int mCountOrdered;
  public int mCountPrioritizeEarliestRequests;
  public int mCountPrioritized;
  public int mCountPrioritizedDeferred;
  public int mCountResultTo;
  public long mForcedDelayedDurationMs;
  public boolean mLastDeferredStates;
  public boolean mProcessFreezable;
  public boolean mProcessInstrumented;
  public boolean mProcessPersistent;
  public boolean mRunnableAtInvalidated;
  public boolean mUidForeground;
  public final String processName;
  public BroadcastProcessQueue processNameNext;
  public BroadcastProcessQueue runnableAtNext;
  public BroadcastProcessQueue runnableAtPrev;
  public boolean runningOomAdjusted;
  public String runningTraceTrackName;
  public final int uid;
  public final ArrayDeque mPending = new ArrayDeque();
  public final ArrayDeque mPendingUrgent = new ArrayDeque(4);
  public final ArrayDeque mPendingOffload = new ArrayDeque(4);
  public long mRunnableAt = Long.MAX_VALUE;
  public int mRunnableAtReason = 0;

  public interface BroadcastConsumer {
    void accept(BroadcastRecord broadcastRecord, int i);
  }

  public interface BroadcastPredicate {
    boolean test(BroadcastRecord broadcastRecord, int i);
  }

  public BroadcastProcessQueue(BroadcastConstants broadcastConstants, String str, int i) {
    Objects.requireNonNull(broadcastConstants);
    this.constants = broadcastConstants;
    Objects.requireNonNull(str);
    this.processName = str;
    this.uid = i;
  }

  public final ArrayDeque getQueueForBroadcast(BroadcastRecord broadcastRecord) {
    if (broadcastRecord.isUrgent()) {
      return this.mPendingUrgent;
    }
    if (broadcastRecord.isOffload()) {
      return this.mPendingOffload;
    }
    return this.mPending;
  }

  public BroadcastRecord enqueueOrReplaceBroadcast(
      BroadcastRecord broadcastRecord, int i, BroadcastConsumer broadcastConsumer) {
    BroadcastRecord replaceBroadcast;
    if (broadcastRecord.isReplacePending()
        && broadcastRecord.getDeliveryGroupPolicy() == 0
        && (replaceBroadcast = replaceBroadcast(broadcastRecord, i)) != null) {
      return replaceBroadcast;
    }
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = broadcastRecord;
    obtain.argi1 = i;
    getQueueForBroadcast(broadcastRecord).addLast(obtain);
    onBroadcastEnqueued(broadcastRecord, i);
    if (!this.mLastDeferredStates
        || !shouldBeDeferred()
        || broadcastRecord.getDeliveryState(i) != 0) {
      return null;
    }
    broadcastConsumer.accept(broadcastRecord, i);
    return null;
  }

  public void reEnqueueActiveBroadcast() {
    if (!isActive()) {
      BroadcastQueue.logw("Ignoring reEnqueueActiveBroadcast; no active broadcast for " + this);
      return;
    }
    BroadcastRecord active = getActive();
    int activeIndex = getActiveIndex();
    SomeArgs obtain = SomeArgs.obtain();
    obtain.arg1 = active;
    obtain.argi1 = activeIndex;
    obtain.argi2 = 1;
    getQueueForBroadcast(active).addFirst(obtain);
    onBroadcastEnqueued(active, activeIndex);
  }

  public final BroadcastRecord replaceBroadcast(BroadcastRecord broadcastRecord, int i) {
    return replaceBroadcastInQueue(getQueueForBroadcast(broadcastRecord), broadcastRecord, i);
  }

  public final BroadcastRecord replaceBroadcastInQueue(
      ArrayDeque arrayDeque, BroadcastRecord broadcastRecord, int i) {
    Iterator descendingIterator = arrayDeque.descendingIterator();
    Object obj = broadcastRecord.receivers.get(i);
    while (descendingIterator.hasNext()) {
      SomeArgs someArgs = (SomeArgs) descendingIterator.next();
      BroadcastRecord broadcastRecord2 = (BroadcastRecord) someArgs.arg1;
      int i2 = someArgs.argi1;
      Object obj2 = broadcastRecord2.receivers.get(i2);
      if (broadcastRecord == broadcastRecord2) {
        return null;
      }
      if (broadcastRecord.callingUid == broadcastRecord2.callingUid
          && broadcastRecord.userId == broadcastRecord2.userId
          && broadcastRecord.intent.filterEquals(broadcastRecord2.intent)
          && BroadcastRecord.isReceiverEquals(obj, obj2)
          && broadcastRecord2.allReceiversPending()
          && broadcastRecord.isMatchingRecord(broadcastRecord2)) {
        someArgs.arg1 = broadcastRecord;
        someArgs.argi1 = i;
        broadcastRecord.copyEnqueueTimeFrom(broadcastRecord2);
        onBroadcastDequeued(broadcastRecord2, i2);
        onBroadcastEnqueued(broadcastRecord, i);
        return broadcastRecord2;
      }
    }
    return null;
  }

  public boolean forEachMatchingBroadcast(
      BroadcastPredicate broadcastPredicate, BroadcastConsumer broadcastConsumer, boolean z) {
    return forEachMatchingBroadcastInQueue(
            this.mPendingOffload, broadcastPredicate, broadcastConsumer, z)
        | forEachMatchingBroadcastInQueue(this.mPending, broadcastPredicate, broadcastConsumer, z)
        | false
        | forEachMatchingBroadcastInQueue(
            this.mPendingUrgent, broadcastPredicate, broadcastConsumer, z);
  }

  public final boolean forEachMatchingBroadcastInQueue(
      ArrayDeque arrayDeque,
      BroadcastPredicate broadcastPredicate,
      BroadcastConsumer broadcastConsumer,
      boolean z) {
    Iterator it = arrayDeque.iterator();
    boolean z2 = false;
    while (it.hasNext()) {
      SomeArgs someArgs = (SomeArgs) it.next();
      BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
      int i = someArgs.argi1;
      if (broadcastPredicate.test(broadcastRecord, i)) {
        broadcastConsumer.accept(broadcastRecord, i);
        if (z) {
          someArgs.recycle();
          it.remove();
          onBroadcastDequeued(broadcastRecord, i);
        } else {
          invalidateRunnableAt();
        }
        z2 = true;
      }
    }
    return z2;
  }

  public boolean setProcessAndUidState(ProcessRecord processRecord, boolean z, boolean z2) {
    this.app = processRecord;
    this.mCachedToString = null;
    this.mCachedToShortString = null;
    if (processRecord != null) {
      return setProcessPersistent(processRecord.isPersistent())
          | setUidForeground(z)
          | false
          | setProcessFreezable(z2)
          | setProcessInstrumented(processRecord.getActiveInstrumentation() != null);
    }
    return setProcessPersistent(false)
        | setUidForeground(false)
        | false
        | setProcessFreezable(false)
        | setProcessInstrumented(false);
  }

  public final boolean setUidForeground(boolean z) {
    if (this.mUidForeground == z) {
      return false;
    }
    this.mUidForeground = z;
    invalidateRunnableAt();
    return true;
  }

  public final boolean setProcessFreezable(boolean z) {
    if (this.mProcessFreezable == z) {
      return false;
    }
    this.mProcessFreezable = z;
    invalidateRunnableAt();
    return true;
  }

  public final boolean setProcessInstrumented(boolean z) {
    if (this.mProcessInstrumented == z) {
      return false;
    }
    this.mProcessInstrumented = z;
    invalidateRunnableAt();
    return true;
  }

  public final boolean setProcessPersistent(boolean z) {
    if (this.mProcessPersistent == z) {
      return false;
    }
    this.mProcessPersistent = z;
    invalidateRunnableAt();
    return true;
  }

  public boolean isProcessWarm() {
    ProcessRecord processRecord = this.app;
    return (processRecord == null || processRecord.getOnewayThread() == null || this.app.isKilled())
        ? false
        : true;
  }

  public int getPreferredSchedulingGroupLocked() {
    if (!isActive()) {
      return Integer.MIN_VALUE;
    }
    if (this.mCountForeground > this.mCountForegroundDeferred) {
      return 2;
    }
    BroadcastRecord broadcastRecord = this.mActive;
    return (broadcastRecord == null || !broadcastRecord.isForeground()) ? 0 : 2;
  }

  public int getActiveCountSinceIdle() {
    return this.mActiveCountSinceIdle;
  }

  public int getActiveAssumedDeliveryCountSinceIdle() {
    return this.mActiveAssumedDeliveryCountSinceIdle;
  }

  public void setActiveViaColdStart(boolean z) {
    this.mActiveViaColdStart = z;
  }

  public void setActiveWasStopped(boolean z) {
    this.mActiveWasStopped = z;
  }

  public boolean getActiveViaColdStart() {
    return this.mActiveViaColdStart;
  }

  public boolean getActiveWasStopped() {
    return this.mActiveWasStopped;
  }

  public String getPackageName() {
    ProcessRecord processRecord = this.app;
    if (processRecord == null) {
      return null;
    }
    return processRecord.getApplicationInfo().packageName;
  }

  public void makeActiveNextPending() {
    SomeArgs removeNextBroadcast = removeNextBroadcast();
    BroadcastRecord broadcastRecord = (BroadcastRecord) removeNextBroadcast.arg1;
    this.mActive = broadcastRecord;
    int i = removeNextBroadcast.argi1;
    this.mActiveIndex = i;
    this.mActiveReEnqueued = removeNextBroadcast.argi2 == 1;
    this.mActiveCountSinceIdle++;
    this.mActiveAssumedDeliveryCountSinceIdle += broadcastRecord.isAssumedDelivered(i) ? 1 : 0;
    this.mActiveViaColdStart = false;
    this.mActiveWasStopped = false;
    removeNextBroadcast.recycle();
    onBroadcastDequeued(this.mActive, this.mActiveIndex);
  }

  public void makeActiveIdle() {
    this.mActive = null;
    this.mActiveIndex = 0;
    this.mActiveReEnqueued = false;
    this.mActiveCountSinceIdle = 0;
    this.mActiveAssumedDeliveryCountSinceIdle = 0;
    this.mActiveViaColdStart = false;
    invalidateRunnableAt();
  }

  public boolean wasActiveBroadcastReEnqueued() {
    return this.mActiveReEnqueued;
  }

  public final void onBroadcastEnqueued(BroadcastRecord broadcastRecord, int i) {
    boolean isMARsTargetReceiver = broadcastRecord.isMARsTargetReceiver(i);
    this.mCountEnqueued++;
    if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
      this.mCountDeferred++;
    }
    if (broadcastRecord.isForeground()) {
      if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
        this.mCountForegroundDeferred++;
      }
      this.mCountForeground++;
    }
    if (broadcastRecord.ordered) {
      this.mCountOrdered++;
    }
    if (broadcastRecord.alarm) {
      this.mCountAlarm++;
    }
    if (broadcastRecord.prioritized) {
      if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
        this.mCountPrioritizedDeferred++;
      }
      this.mCountPrioritized++;
    }
    if (broadcastRecord.interactive) {
      this.mCountInteractive++;
    }
    if (broadcastRecord.resultTo != null) {
      this.mCountResultTo++;
    }
    if (broadcastRecord.callerInstrumented) {
      this.mCountInstrumented++;
    }
    if (broadcastRecord.receivers.get(i) instanceof ResolveInfo) {
      this.mCountManifest++;
    }
    invalidateRunnableAt();
  }

  public final void onBroadcastDequeued(BroadcastRecord broadcastRecord, int i) {
    boolean isMARsTargetReceiver = broadcastRecord.isMARsTargetReceiver(i);
    this.mCountEnqueued--;
    if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
      this.mCountDeferred--;
    }
    if (broadcastRecord.isForeground()) {
      if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
        this.mCountForegroundDeferred--;
      }
      this.mCountForeground--;
    }
    if (broadcastRecord.ordered) {
      this.mCountOrdered--;
    }
    if (broadcastRecord.alarm) {
      this.mCountAlarm--;
    }
    if (broadcastRecord.prioritized) {
      if (broadcastRecord.deferUntilActive || isMARsTargetReceiver) {
        this.mCountPrioritizedDeferred--;
      }
      this.mCountPrioritized--;
    }
    if (broadcastRecord.interactive) {
      this.mCountInteractive--;
    }
    if (broadcastRecord.resultTo != null) {
      this.mCountResultTo--;
    }
    if (broadcastRecord.callerInstrumented) {
      this.mCountInstrumented--;
    }
    if (broadcastRecord.receivers.get(i) instanceof ResolveInfo) {
      this.mCountManifest--;
    }
    invalidateRunnableAt();
  }

  public void traceProcessStartingBegin() {
    Trace.asyncTraceForTrackBegin(
        64L, this.runningTraceTrackName, toShortString() + " starting", hashCode());
  }

  public void traceProcessRunningBegin() {
    Trace.asyncTraceForTrackBegin(
        64L, this.runningTraceTrackName, toShortString() + " running", hashCode());
  }

  public void traceProcessEnd() {
    Trace.asyncTraceForTrackEnd(64L, this.runningTraceTrackName, hashCode());
  }

  public void traceActiveBegin() {
    Trace.asyncTraceForTrackBegin(
        64L, this.runningTraceTrackName, this.mActive.toShortString() + " scheduled", hashCode());
  }

  public void traceActiveEnd() {
    Trace.asyncTraceForTrackEnd(64L, this.runningTraceTrackName, hashCode());
  }

  public BroadcastRecord getActive() {
    BroadcastRecord broadcastRecord = this.mActive;
    Objects.requireNonNull(broadcastRecord);
    return broadcastRecord;
  }

  public int getActiveIndex() {
    Objects.requireNonNull(this.mActive);
    return this.mActiveIndex;
  }

  public boolean isEmpty() {
    return this.mPending.isEmpty()
        && this.mPendingUrgent.isEmpty()
        && this.mPendingOffload.isEmpty();
  }

  public boolean isActive() {
    return this.mActive != null;
  }

  public boolean forceDelayBroadcastDelivery(long j) {
    if (this.mForcedDelayedDurationMs == j) {
      return false;
    }
    this.mForcedDelayedDurationMs = j;
    invalidateRunnableAt();
    return true;
  }

  public final SomeArgs removeNextBroadcast() {
    ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
    if (queueForNextBroadcast == this.mPendingUrgent) {
      this.mActiveCountConsecutiveUrgent++;
    } else if (queueForNextBroadcast == this.mPending) {
      this.mActiveCountConsecutiveUrgent = 0;
      this.mActiveCountConsecutiveNormal++;
    } else if (queueForNextBroadcast == this.mPendingOffload) {
      this.mActiveCountConsecutiveUrgent = 0;
      this.mActiveCountConsecutiveNormal = 0;
    }
    if (isQueueEmpty(queueForNextBroadcast)) {
      return null;
    }
    return (SomeArgs) queueForNextBroadcast.removeFirst();
  }

  public ArrayDeque queueForNextBroadcast() {
    return queueForNextBroadcast(
        this.mPendingUrgent,
        queueForNextBroadcast(
            this.mPending,
            this.mPendingOffload,
            this.mActiveCountConsecutiveNormal,
            this.constants.MAX_CONSECUTIVE_NORMAL_DISPATCHES),
        this.mActiveCountConsecutiveUrgent,
        this.constants.MAX_CONSECUTIVE_URGENT_DISPATCHES);
  }

  public final ArrayDeque queueForNextBroadcast(
      ArrayDeque arrayDeque, ArrayDeque arrayDeque2, int i, int i2) {
    if (isQueueEmpty(arrayDeque)) {
      return arrayDeque2;
    }
    if (isQueueEmpty(arrayDeque2)) {
      return arrayDeque;
    }
    SomeArgs someArgs = (SomeArgs) arrayDeque2.peekFirst();
    BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
    int i3 = someArgs.argi1;
    BroadcastRecord broadcastRecord2 = (BroadcastRecord) ((SomeArgs) arrayDeque.peekFirst()).arg1;
    boolean z = false;
    if ((this.mCountPrioritizeEarliestRequests > 0 || i >= i2)
        && broadcastRecord.enqueueTime <= broadcastRecord2.enqueueTime
        && !broadcastRecord.isBlocked(i3)) {
      z = true;
    }
    return z ? arrayDeque2 : arrayDeque;
  }

  public static boolean isQueueEmpty(ArrayDeque arrayDeque) {
    return arrayDeque == null || arrayDeque.isEmpty();
  }

  public boolean addPrioritizeEarliestRequest() {
    int i = this.mCountPrioritizeEarliestRequests;
    if (i == 0) {
      this.mCountPrioritizeEarliestRequests = i + 1;
      invalidateRunnableAt();
      return true;
    }
    this.mCountPrioritizeEarliestRequests = i + 1;
    return false;
  }

  public boolean removePrioritizeEarliestRequest() {
    int i = this.mCountPrioritizeEarliestRequests - 1;
    this.mCountPrioritizeEarliestRequests = i;
    if (i == 0) {
      invalidateRunnableAt();
      return true;
    }
    if (i < 0) {
      this.mCountPrioritizeEarliestRequests = 0;
    }
    return false;
  }

  public SomeArgs peekNextBroadcast() {
    ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
    if (isQueueEmpty(queueForNextBroadcast)) {
      return null;
    }
    return (SomeArgs) queueForNextBroadcast.peekFirst();
  }

  public BroadcastRecord peekNextBroadcastRecord() {
    ArrayDeque queueForNextBroadcast = queueForNextBroadcast();
    if (isQueueEmpty(queueForNextBroadcast)) {
      return null;
    }
    return (BroadcastRecord) ((SomeArgs) queueForNextBroadcast.peekFirst()).arg1;
  }

  public boolean isPendingManifest() {
    return this.mCountManifest > 0;
  }

  public boolean isPendingOrdered() {
    return this.mCountOrdered > 0;
  }

  public boolean isPendingResultTo() {
    return this.mCountResultTo > 0;
  }

  public boolean isPendingUrgent() {
    BroadcastRecord peekNextBroadcastRecord = peekNextBroadcastRecord();
    if (peekNextBroadcastRecord != null) {
      return peekNextBroadcastRecord.isUrgent();
    }
    return false;
  }

  public boolean isIdle() {
    return (!isActive() && isEmpty()) || isDeferredUntilActive();
  }

  public boolean isBeyondBarrierLocked(long j) {
    SomeArgs someArgs = (SomeArgs) this.mPending.peekFirst();
    SomeArgs someArgs2 = (SomeArgs) this.mPendingUrgent.peekFirst();
    SomeArgs someArgs3 = (SomeArgs) this.mPendingOffload.peekFirst();
    BroadcastRecord broadcastRecord = this.mActive;
    return ((broadcastRecord == null
                || (broadcastRecord.enqueueTime > j
                        ? 1
                        : (broadcastRecord.enqueueTime == j ? 0 : -1))
                    > 0)
            && (someArgs == null
                || (((BroadcastRecord) someArgs.arg1).enqueueTime > j
                        ? 1
                        : (((BroadcastRecord) someArgs.arg1).enqueueTime == j ? 0 : -1))
                    > 0)
            && (someArgs2 == null
                || (((BroadcastRecord) someArgs2.arg1).enqueueTime > j
                        ? 1
                        : (((BroadcastRecord) someArgs2.arg1).enqueueTime == j ? 0 : -1))
                    > 0)
            && (someArgs3 == null
                || (((BroadcastRecord) someArgs3.arg1).enqueueTime > j
                        ? 1
                        : (((BroadcastRecord) someArgs3.arg1).enqueueTime == j ? 0 : -1))
                    > 0))
        || isDeferredUntilActive();
  }

  public boolean isDispatched(Intent intent) {
    BroadcastRecord broadcastRecord = this.mActive;
    return ((broadcastRecord == null || !intent.filterEquals(broadcastRecord.intent))
            && isDispatchedInQueue(this.mPending, intent)
            && isDispatchedInQueue(this.mPendingUrgent, intent)
            && isDispatchedInQueue(this.mPendingOffload, intent))
        || isDeferredUntilActive();
  }

  public final boolean isDispatchedInQueue(ArrayDeque arrayDeque, Intent intent) {
    SomeArgs someArgs;
    Iterator it = arrayDeque.iterator();
    while (it.hasNext() && (someArgs = (SomeArgs) it.next()) != null) {
      if (intent.filterEquals(((BroadcastRecord) someArgs.arg1).intent)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRunnable() {
    if (this.mRunnableAtInvalidated) {
      updateRunnableAt();
    }
    return this.mRunnableAt != Long.MAX_VALUE;
  }

  public boolean isDeferredUntilActive() {
    if (this.mRunnableAtInvalidated) {
      updateRunnableAt();
    }
    return this.mRunnableAtReason == 8;
  }

  public long getRunnableAt() {
    if (this.mRunnableAtInvalidated) {
      updateRunnableAt();
    }
    return this.mRunnableAt;
  }

  public int getRunnableAtReason() {
    if (this.mRunnableAtInvalidated) {
      updateRunnableAt();
    }
    return this.mRunnableAtReason;
  }

  public void invalidateRunnableAt() {
    this.mRunnableAtInvalidated = true;
  }

  public static String reasonToString(int i) {
    switch (i) {
      case 0:
        return "EMPTY";
      case 1:
        return "CACHED";
      case 2:
        return "NORMAL";
      case 3:
        return "MAX_PENDING";
      case 4:
        return "BLOCKED";
      case 5:
        return "INSTRUMENTED";
      case 6:
        return "PERSISTENT";
      case 7:
        return "FORCE_DELAYED";
      case 8:
        return "INFINITE_DEFER";
      case 9:
      default:
        return Integer.toString(i);
      case 10:
        return "CONTAINS_FOREGROUND";
      case 11:
        return "CONTAINS_ORDERED";
      case 12:
        return "CONTAINS_ALARM";
      case 13:
        return "CONTAINS_PRIORITIZED";
      case 14:
        return "CONTAINS_INTERACTIVE";
      case 15:
        return "CONTAINS_RESULT_TO";
      case 16:
        return "CONTAINS_INSTRUMENTED";
      case 17:
        return "CONTAINS_MANIFEST";
      case 18:
        return "FOREGROUND";
      case 19:
        return "CORE_UID";
      case 20:
        return "TOP_PROCESS";
      case 21:
        return "PERCEPTIBLE_APP";
    }
  }

  public void updateRunnableAt() {
    if (this.mRunnableAtInvalidated) {
      this.mRunnableAtInvalidated = false;
      SomeArgs peekNextBroadcast = peekNextBroadcast();
      if (peekNextBroadcast != null) {
        BroadcastRecord broadcastRecord = (BroadcastRecord) peekNextBroadcast.arg1;
        int i = peekNextBroadcast.argi1;
        long j = broadcastRecord.enqueueTime;
        if (broadcastRecord.isBlocked(i)) {
          this.mRunnableAt = Long.MAX_VALUE;
          this.mRunnableAtReason = 4;
          return;
        }
        long j2 = this.mForcedDelayedDurationMs;
        if (j2 > 0) {
          this.mRunnableAt = j2 + j;
          this.mRunnableAtReason = 7;
        } else if (this.mCountForeground > this.mCountForegroundDeferred) {
          this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
          this.mRunnableAtReason = 10;
        } else if (this.mCountInteractive > 0) {
          this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
          this.mRunnableAtReason = 14;
        } else if (this.mCountInstrumented > 0) {
          this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
          this.mRunnableAtReason = 16;
        } else if (this.mProcessInstrumented) {
          this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
          this.mRunnableAtReason = 5;
        } else if (this.mUidForeground) {
          this.mRunnableAt = this.constants.DELAY_FOREGROUND_PROC_MILLIS + j;
          this.mRunnableAtReason = 18;
        } else {
          ProcessRecord processRecord = this.app;
          if (processRecord != null && processRecord.getSetProcState() == 2) {
            this.mRunnableAt = this.constants.DELAY_FOREGROUND_PROC_MILLIS + j;
            this.mRunnableAtReason = 20;
          } else if (this.mProcessPersistent) {
            this.mRunnableAt = this.constants.DELAY_PERSISTENT_PROC_MILLIS + j;
            this.mRunnableAtReason = 6;
          } else if (UserHandle.isCore(this.uid)) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 19;
          } else if (this.mCountOrdered > 0) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 11;
          } else if (this.mCountAlarm > 0) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 12;
          } else if (this.mCountPrioritized > this.mCountPrioritizedDeferred) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 13;
          } else if (this.mCountManifest > 0) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 17;
          } else if (this.mProcessFreezable) {
            if (broadcastRecord.deferUntilActive || broadcastRecord.isMARsTargetReceiver(i)) {
              if (this.mCountDeferred == this.mCountEnqueued) {
                this.mRunnableAt = Long.MAX_VALUE;
                this.mRunnableAtReason = 8;
              } else if (broadcastRecord.isForeground()) {
                this.mRunnableAt = this.constants.DELAY_URGENT_MILLIS + j;
                this.mRunnableAtReason = 10;
              } else if (broadcastRecord.prioritized) {
                this.mRunnableAt = j;
                this.mRunnableAtReason = 13;
              } else if (broadcastRecord.resultTo != null) {
                this.mRunnableAt = j;
                this.mRunnableAtReason = 15;
              } else {
                this.mRunnableAt = this.constants.DELAY_CACHED_MILLIS + j;
                this.mRunnableAtReason = 1;
              }
            } else {
              this.mRunnableAt = this.constants.DELAY_CACHED_MILLIS + j;
              this.mRunnableAtReason = 1;
            }
          } else if (this.mCountResultTo > 0) {
            this.mRunnableAt = j;
            this.mRunnableAtReason = 15;
          } else {
            ProcessRecord processRecord2 = this.app;
            if (processRecord2 != null && processRecord2.getSetAdj() <= 200) {
              this.mRunnableAt = j;
              this.mRunnableAtReason = 21;
            } else {
              this.mRunnableAt = this.constants.DELAY_NORMAL_MILLIS + j;
              this.mRunnableAtReason = 2;
            }
          }
        }
        if (this.mPending.size() + this.mPendingUrgent.size() + this.mPendingOffload.size()
            >= this.constants.MAX_PENDING_BROADCASTS) {
          this.mRunnableAt = Math.min(this.mRunnableAt, j);
          this.mRunnableAtReason = 3;
          return;
        }
        return;
      }
      this.mRunnableAt = Long.MAX_VALUE;
      this.mRunnableAtReason = 0;
    }
  }

  public void updateDeferredStates(
      BroadcastConsumer broadcastConsumer, BroadcastConsumer broadcastConsumer2) {
    boolean shouldBeDeferred = shouldBeDeferred();
    if (this.mLastDeferredStates != shouldBeDeferred) {
      this.mLastDeferredStates = shouldBeDeferred;
      if (shouldBeDeferred) {
        forEachMatchingBroadcast(
            new BroadcastPredicate() { // from class:
                                       // com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda0
              @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
              public final boolean test(BroadcastRecord broadcastRecord, int i) {
                boolean lambda$updateDeferredStates$0;
                lambda$updateDeferredStates$0 =
                    BroadcastProcessQueue.lambda$updateDeferredStates$0(broadcastRecord, i);
                return lambda$updateDeferredStates$0;
              }
            },
            broadcastConsumer,
            false);
      } else {
        forEachMatchingBroadcast(
            new BroadcastPredicate() { // from class:
                                       // com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda1
              @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
              public final boolean test(BroadcastRecord broadcastRecord, int i) {
                boolean lambda$updateDeferredStates$1;
                lambda$updateDeferredStates$1 =
                    BroadcastProcessQueue.lambda$updateDeferredStates$1(broadcastRecord, i);
                return lambda$updateDeferredStates$1;
              }
            },
            broadcastConsumer2,
            false);
      }
    }
  }

  public static /* synthetic */ boolean lambda$updateDeferredStates$0(
      BroadcastRecord broadcastRecord, int i) {
    return broadcastRecord.getDeliveryState(i) == 0;
  }

  public static /* synthetic */ boolean lambda$updateDeferredStates$1(
      BroadcastRecord broadcastRecord, int i) {
    return broadcastRecord.getDeliveryState(i) == 6;
  }

  public void clearDeferredStates(BroadcastConsumer broadcastConsumer) {
    if (this.mLastDeferredStates) {
      this.mLastDeferredStates = false;
      forEachMatchingBroadcast(
          new BroadcastPredicate() { // from class:
                                     // com.android.server.am.BroadcastProcessQueue$$ExternalSyntheticLambda2
            @Override // com.android.server.am.BroadcastProcessQueue.BroadcastPredicate
            public final boolean test(BroadcastRecord broadcastRecord, int i) {
              boolean lambda$clearDeferredStates$2;
              lambda$clearDeferredStates$2 =
                  BroadcastProcessQueue.lambda$clearDeferredStates$2(broadcastRecord, i);
              return lambda$clearDeferredStates$2;
            }
          },
          broadcastConsumer,
          false);
    }
  }

  public static /* synthetic */ boolean lambda$clearDeferredStates$2(
      BroadcastRecord broadcastRecord, int i) {
    return broadcastRecord.getDeliveryState(i) == 6;
  }

  public boolean shouldBeDeferred() {
    if (this.mRunnableAtInvalidated) {
      updateRunnableAt();
    }
    int i = this.mRunnableAtReason;
    return i == 1 || i == 8;
  }

  public void assertHealthLocked() {
    if (!isActive()) {
      Preconditions.checkState(!this.mRunnableAtInvalidated, "mRunnableAtInvalidated");
    }
    assertHealthLocked(this.mPending);
    assertHealthLocked(this.mPendingUrgent);
    assertHealthLocked(this.mPendingOffload);
  }

  public final void assertHealthLocked(ArrayDeque arrayDeque) {
    if (arrayDeque.isEmpty()) {
      return;
    }
    Iterator descendingIterator = arrayDeque.descendingIterator();
    while (descendingIterator.hasNext()) {
      SomeArgs someArgs = (SomeArgs) descendingIterator.next();
      BroadcastRecord broadcastRecord = (BroadcastRecord) someArgs.arg1;
      int i = someArgs.argi1;
      if (!BroadcastRecord.isDeliveryStateTerminal(broadcastRecord.getDeliveryState(i))
          && !broadcastRecord.isDeferUntilActive()
          && !broadcastRecord.isMARsTargetReceiver(i)) {
        Preconditions.checkState(
            SystemClock.uptimeMillis() - broadcastRecord.enqueueTime < 600000, "waitingTime");
      }
    }
  }

  public static BroadcastProcessQueue insertIntoRunnableList(
      BroadcastProcessQueue broadcastProcessQueue, BroadcastProcessQueue broadcastProcessQueue2) {
    if (broadcastProcessQueue == null) {
      return broadcastProcessQueue2;
    }
    long runnableAt = broadcastProcessQueue2.getRunnableAt();
    BroadcastProcessQueue broadcastProcessQueue3 = null;
    BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue;
    while (broadcastProcessQueue4 != null) {
      if (broadcastProcessQueue4.getRunnableAt() > runnableAt) {
        broadcastProcessQueue2.runnableAtNext = broadcastProcessQueue4;
        broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue4.runnableAtPrev;
        broadcastProcessQueue4.runnableAtPrev = broadcastProcessQueue2;
        BroadcastProcessQueue broadcastProcessQueue5 = broadcastProcessQueue2.runnableAtPrev;
        if (broadcastProcessQueue5 != null) {
          broadcastProcessQueue5.runnableAtNext = broadcastProcessQueue2;
        }
        return broadcastProcessQueue4 == broadcastProcessQueue
            ? broadcastProcessQueue2
            : broadcastProcessQueue;
      }
      broadcastProcessQueue3 = broadcastProcessQueue4;
      broadcastProcessQueue4 = broadcastProcessQueue4.runnableAtNext;
    }
    broadcastProcessQueue2.runnableAtPrev = broadcastProcessQueue3;
    broadcastProcessQueue3.runnableAtNext = broadcastProcessQueue2;
    return broadcastProcessQueue;
  }

  public static BroadcastProcessQueue removeFromRunnableList(
      BroadcastProcessQueue broadcastProcessQueue, BroadcastProcessQueue broadcastProcessQueue2) {
    if (broadcastProcessQueue == broadcastProcessQueue2) {
      broadcastProcessQueue = broadcastProcessQueue2.runnableAtNext;
    }
    BroadcastProcessQueue broadcastProcessQueue3 = broadcastProcessQueue2.runnableAtNext;
    if (broadcastProcessQueue3 != null) {
      broadcastProcessQueue3.runnableAtPrev = broadcastProcessQueue2.runnableAtPrev;
    }
    BroadcastProcessQueue broadcastProcessQueue4 = broadcastProcessQueue2.runnableAtPrev;
    if (broadcastProcessQueue4 != null) {
      broadcastProcessQueue4.runnableAtNext = broadcastProcessQueue3;
    }
    broadcastProcessQueue2.runnableAtNext = null;
    broadcastProcessQueue2.runnableAtPrev = null;
    return broadcastProcessQueue;
  }

  public String toString() {
    if (this.mCachedToString == null) {
      this.mCachedToString = "BroadcastProcessQueue{" + toShortString() + "}";
    }
    return this.mCachedToString;
  }

  public String toShortString() {
    if (this.mCachedToShortString == null) {
      StringBuilder sb = new StringBuilder();
      sb.append(Integer.toHexString(System.identityHashCode(this)));
      sb.append(" ");
      ProcessRecord processRecord = this.app;
      sb.append(processRecord != null ? Integer.valueOf(processRecord.getPid()) : "?");
      sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
      sb.append(this.processName);
      sb.append("/");
      sb.append(UserHandle.formatUid(this.uid));
      this.mCachedToShortString = sb.toString();
    }
    return this.mCachedToShortString;
  }

  public String describeStateLocked() {
    return describeStateLocked(SystemClock.uptimeMillis());
  }

  public String describeStateLocked(long j) {
    StringBuilder sb = new StringBuilder();
    if (isRunnable()) {
      sb.append("runnable at ");
      TimeUtils.formatDuration(getRunnableAt(), j, sb);
    } else {
      sb.append("not runnable");
    }
    sb.append(" because ");
    sb.append(reasonToString(this.mRunnableAtReason));
    return sb.toString();
  }

  @NeverCompile
  public void dumpLocked(long j, IndentingPrintWriter indentingPrintWriter) {
    if (this.mActive == null && isEmpty()) {
      return;
    }
    indentingPrintWriter.print(toShortString());
    indentingPrintWriter.print(" ");
    indentingPrintWriter.print(describeStateLocked(j));
    indentingPrintWriter.println();
    indentingPrintWriter.increaseIndent();
    dumpProcessState(indentingPrintWriter);
    dumpBroadcastCounts(indentingPrintWriter);
    BroadcastRecord broadcastRecord = this.mActive;
    if (broadcastRecord != null) {
      dumpRecord("ACTIVE", j, indentingPrintWriter, broadcastRecord, this.mActiveIndex);
    }
    Iterator it = this.mPendingUrgent.iterator();
    while (it.hasNext()) {
      SomeArgs someArgs = (SomeArgs) it.next();
      dumpRecord(
          "URGENT", j, indentingPrintWriter, (BroadcastRecord) someArgs.arg1, someArgs.argi1);
    }
    Iterator it2 = this.mPending.iterator();
    while (it2.hasNext()) {
      SomeArgs someArgs2 = (SomeArgs) it2.next();
      dumpRecord(null, j, indentingPrintWriter, (BroadcastRecord) someArgs2.arg1, someArgs2.argi1);
    }
    Iterator it3 = this.mPendingOffload.iterator();
    while (it3.hasNext()) {
      SomeArgs someArgs3 = (SomeArgs) it3.next();
      dumpRecord(
          "OFFLOAD", j, indentingPrintWriter, (BroadcastRecord) someArgs3.arg1, someArgs3.argi1);
    }
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.println();
  }

  @NeverCompile
  public final void dumpProcessState(IndentingPrintWriter indentingPrintWriter) {
    StringBuilder sb = new StringBuilder();
    if (this.mUidForeground) {
      sb.append("FG");
    }
    if (this.mProcessFreezable) {
      if (sb.length() > 0) {
        sb.append("|");
      }
      sb.append("FRZ");
    }
    if (this.mProcessInstrumented) {
      if (sb.length() > 0) {
        sb.append("|");
      }
      sb.append("INSTR");
    }
    if (this.mProcessPersistent) {
      if (sb.length() > 0) {
        sb.append("|");
      }
      sb.append("PER");
    }
    if (sb.length() > 0) {
      indentingPrintWriter.print("state:");
      indentingPrintWriter.println(sb);
    }
    if (this.runningOomAdjusted) {
      indentingPrintWriter.print("runningOomAdjusted:");
      indentingPrintWriter.println(this.runningOomAdjusted);
    }
    if (this.mActiveReEnqueued) {
      indentingPrintWriter.print("activeReEnqueued:");
      indentingPrintWriter.println(this.mActiveReEnqueued);
    }
  }

  @NeverCompile
  public final void dumpBroadcastCounts(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.print("e:");
    indentingPrintWriter.print(this.mCountEnqueued);
    indentingPrintWriter.print(" d:");
    indentingPrintWriter.print(this.mCountDeferred);
    indentingPrintWriter.print(" f:");
    indentingPrintWriter.print(this.mCountForeground);
    indentingPrintWriter.print(" fd:");
    indentingPrintWriter.print(this.mCountForegroundDeferred);
    indentingPrintWriter.print(" o:");
    indentingPrintWriter.print(this.mCountOrdered);
    indentingPrintWriter.print(" a:");
    indentingPrintWriter.print(this.mCountAlarm);
    indentingPrintWriter.print(" p:");
    indentingPrintWriter.print(this.mCountPrioritized);
    indentingPrintWriter.print(" pd:");
    indentingPrintWriter.print(this.mCountPrioritizedDeferred);
    indentingPrintWriter.print(" int:");
    indentingPrintWriter.print(this.mCountInteractive);
    indentingPrintWriter.print(" rt:");
    indentingPrintWriter.print(this.mCountResultTo);
    indentingPrintWriter.print(" ins:");
    indentingPrintWriter.print(this.mCountInstrumented);
    indentingPrintWriter.print(" m:");
    indentingPrintWriter.print(this.mCountManifest);
    indentingPrintWriter.print(" csi:");
    indentingPrintWriter.print(this.mActiveCountSinceIdle);
    indentingPrintWriter.print(" adcsi:");
    indentingPrintWriter.print(this.mActiveAssumedDeliveryCountSinceIdle);
    indentingPrintWriter.print(" ccu:");
    indentingPrintWriter.print(this.mActiveCountConsecutiveUrgent);
    indentingPrintWriter.print(" ccn:");
    indentingPrintWriter.print(this.mActiveCountConsecutiveNormal);
    indentingPrintWriter.println();
  }

  @NeverCompile
  public final void dumpRecord(
      String str,
      long j,
      IndentingPrintWriter indentingPrintWriter,
      BroadcastRecord broadcastRecord,
      int i) {
    TimeUtils.formatDuration(broadcastRecord.enqueueTime, j, indentingPrintWriter);
    indentingPrintWriter.print(' ');
    indentingPrintWriter.println(broadcastRecord.toShortString());
    indentingPrintWriter.print("    ");
    int i2 = broadcastRecord.delivery[i];
    indentingPrintWriter.print(BroadcastRecord.deliveryStateToString(i2));
    if (i2 == 4) {
      indentingPrintWriter.print(" at ");
      TimeUtils.formatDuration(broadcastRecord.scheduledTime[i], j, indentingPrintWriter);
    }
    if (str != null) {
      indentingPrintWriter.print(' ');
      indentingPrintWriter.print(str);
    }
    Object obj = broadcastRecord.receivers.get(i);
    if (obj instanceof BroadcastFilter) {
      indentingPrintWriter.print(" for registered ");
      indentingPrintWriter.print(
          Integer.toHexString(System.identityHashCode((BroadcastFilter) obj)));
    } else {
      indentingPrintWriter.print(" for manifest ");
      indentingPrintWriter.print(((ResolveInfo) obj).activityInfo.name);
    }
    indentingPrintWriter.println();
    int i3 = broadcastRecord.blockedUntilBeyondCount[i];
    if (i3 != -1) {
      indentingPrintWriter.print("    blocked until ");
      indentingPrintWriter.print(i3);
      indentingPrintWriter.print(", currently at ");
      indentingPrintWriter.print(broadcastRecord.beyondCount);
      indentingPrintWriter.print(" of ");
      indentingPrintWriter.println(broadcastRecord.receivers.size());
    }
  }
}
