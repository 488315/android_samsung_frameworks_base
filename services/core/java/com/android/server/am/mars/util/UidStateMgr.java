package com.android.server.am.mars.util;

import android.app.IUidObserver;

import com.android.server.am.FreecessController;
import com.android.server.am.FreecessHandler;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.EventRecorder;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UidStateMgr {
    public final ConcurrentList mTopUidList = new ConcurrentList(new ArrayList());
    public final ConcurrentList mUidForegroundList = new ConcurrentList(new ArrayList());
    public final ConcurrentList mRunningList = new ConcurrentList(new ArrayList());
    public final ConcurrentList mUidCached = new ConcurrentList(new ArrayList());
    public final ConcurrentList mUidIdleList = new ConcurrentList(new ArrayList());
    public final ConcurrentList mUidGoneList = new ConcurrentList(new ArrayList());
    public final IUidObserver mUidObserver =
            new IUidObserver.Stub() { // from class: com.android.server.am.mars.util.UidStateMgr.1
                /* JADX WARN: Code restructure failed: missing block: B:37:0x003a, code lost:

                   if (android.os.UserHandle.isApp(r6) != false) goto L13;
                */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onUidActive(int r6) {
                    /*
                        r5 = this;
                        com.android.server.am.mars.util.UidStateMgr r0 = com.android.server.am.mars.util.UidStateMgr.this
                        java.lang.Integer r1 = java.lang.Integer.valueOf(r6)
                        com.android.server.am.mars.util.ConcurrentList r0 = r0.mUidIdleList
                        r0.remove(r1)
                        com.android.server.am.mars.util.UidStateMgr r0 = com.android.server.am.mars.util.UidStateMgr.this
                        r0.addToRunningList(r6)
                        com.android.server.am.mars.util.UidStateMgr r5 = com.android.server.am.mars.util.UidStateMgr.this
                        java.lang.Integer r0 = java.lang.Integer.valueOf(r6)
                        com.android.server.am.mars.util.ConcurrentList r5 = r5.mUidGoneList
                        r5.remove(r0)
                        boolean r5 = com.android.server.am.FreecessController.IS_MINIMIZE_OLAF_LOCK
                        com.android.server.am.FreecessController r5 = com.android.server.am.FreecessController.FreecessControllerHolder.INSTANCE
                        r5.getClass()
                        java.lang.String r0 = "onUidActive...uid:"
                        com.android.server.am.MARsPolicyManager$Lock r1 = com.android.server.am.MARsPolicyManager.MARsLock
                        monitor-enter(r1)
                        boolean r2 = r5.mIsScreenOnFreecessEnabled     // Catch: java.lang.Throwable -> L4b
                        if (r2 == 0) goto Lc7
                        boolean r2 = r5.mSkipTriggerLcdOnFreeze     // Catch: java.lang.Throwable -> L4b
                        if (r2 != 0) goto Lc7
                        r2 = 100000(0x186a0, float:1.4013E-40)
                        if (r6 < r2) goto L36
                        goto L3c
                    L36:
                        boolean r2 = android.os.UserHandle.isApp(r6)     // Catch: java.lang.Throwable -> L4b
                        if (r2 == 0) goto Lc7
                    L3c:
                        com.android.server.am.FreecessPkgMap r2 = r5.mFreecessManagedPackages     // Catch: java.lang.Throwable -> L4b
                        android.util.SparseArray r2 = r2.mUidMap     // Catch: java.lang.Throwable -> L4b
                        java.lang.Object r6 = r2.get(r6)     // Catch: java.lang.Throwable -> L4b
                        com.android.server.am.FreecessPkgStatus r6 = (com.android.server.am.FreecessPkgStatus) r6     // Catch: java.lang.Throwable -> L4b
                        if (r6 != 0) goto L4e
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L4b
                        goto Lc8
                    L4b:
                        r5 = move-exception
                        goto Lc9
                    L4e:
                        boolean r2 = r5.mCalmModeEnabled     // Catch: java.lang.Throwable -> L4b
                        if (r2 == 0) goto L5f
                        com.android.server.am.FreecessHandler r5 = com.android.server.am.FreecessHandler.FreecessHandlerHolder.INSTANCE     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = r6.name     // Catch: java.lang.Throwable -> L4b
                        int r6 = r6.userId     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r2 = "CalmMode for restart"
                        r5.sendCalmModeRepeatMsg(r6, r0, r2)     // Catch: java.lang.Throwable -> L4b
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L4b
                        goto Lc8
                    L5f:
                        int r2 = r6.freezedState     // Catch: java.lang.Throwable -> L4b
                        r3 = 2
                        if (r2 == r3) goto L6e
                        boolean r5 = r5.mScreenOn     // Catch: java.lang.Throwable -> L4b
                        if (r5 == 0) goto Lc7
                        com.android.server.am.mars.MARsFreezeStateRecord r5 = r6.freezedRecord     // Catch: java.lang.Throwable -> L4b
                        boolean r5 = r5.isLcdOffFreezed     // Catch: java.lang.Throwable -> L4b
                        if (r5 == 0) goto Lc7
                    L6e:
                        boolean r5 = com.android.server.am.mars.MARsDebugConfig.DEBUG_ENG     // Catch: java.lang.Throwable -> L4b
                        if (r5 == 0) goto L99
                        java.lang.String r5 = "FreecessController"
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4b
                        r2.<init>(r0)     // Catch: java.lang.Throwable -> L4b
                        int r0 = r6.uid     // Catch: java.lang.Throwable -> L4b
                        r2.append(r0)     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = ", packageName: "
                        r2.append(r0)     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = r6.name     // Catch: java.lang.Throwable -> L4b
                        r2.append(r0)     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = ", freezedState: "
                        r2.append(r0)     // Catch: java.lang.Throwable -> L4b
                        int r0 = r6.freezedState     // Catch: java.lang.Throwable -> L4b
                        r2.append(r0)     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L4b
                        android.util.Slog.d(r5, r0)     // Catch: java.lang.Throwable -> L4b
                    L99:
                        com.android.server.am.FreecessHandler r5 = com.android.server.am.FreecessHandler.FreecessHandlerHolder.INSTANCE     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = r6.name     // Catch: java.lang.Throwable -> L4b
                        int r6 = r6.userId     // Catch: java.lang.Throwable -> L4b
                        com.android.server.am.FreecessHandler$MainHandler r2 = r5.mMainHandler     // Catch: java.lang.Throwable -> L4b
                        if (r2 != 0) goto La4
                        goto Lc7
                    La4:
                        r3 = 5
                        r2.removeMessages(r3)     // Catch: java.lang.Throwable -> L4b
                        android.os.Bundle r2 = new android.os.Bundle     // Catch: java.lang.Throwable -> L4b
                        r2.<init>()     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r4 = "packageName"
                        r2.putString(r4, r0)     // Catch: java.lang.Throwable -> L4b
                        java.lang.String r0 = "userId"
                        r2.putInt(r0, r6)     // Catch: java.lang.Throwable -> L4b
                        com.android.server.am.FreecessHandler$MainHandler r6 = r5.mMainHandler     // Catch: java.lang.Throwable -> L4b
                        android.os.Message r6 = r6.obtainMessage(r3)     // Catch: java.lang.Throwable -> L4b
                        r6.setData(r2)     // Catch: java.lang.Throwable -> L4b
                        com.android.server.am.FreecessHandler$MainHandler r5 = r5.mMainHandler     // Catch: java.lang.Throwable -> L4b
                        r5.sendMessage(r6)     // Catch: java.lang.Throwable -> L4b
                    Lc7:
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L4b
                    Lc8:
                        return
                    Lc9:
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L4b
                        throw r5
                    */
                    throw new UnsupportedOperationException(
                            "Method not decompiled:"
                                + " com.android.server.am.mars.util.UidStateMgr.AnonymousClass1.onUidActive(int):void");
                }

                public final void onUidCachedChanged(int i, boolean z) {
                    if (z) {
                        UidStateMgr uidStateMgr = UidStateMgr.this;
                        if (!uidStateMgr.mUidCached.contains(Integer.valueOf(i))) {
                            uidStateMgr.mUidCached.add(Integer.valueOf(i));
                        }
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        if (FreecessController.FreecessControllerHolder.INSTANCE.getPackageStatus(i)
                                != null) {
                            synchronized (MARsPolicyManager.MARsLock) {
                            }
                            return;
                        }
                        return;
                    }
                    UidStateMgr uidStateMgr2 = UidStateMgr.this;
                    uidStateMgr2.mUidCached.remove(Integer.valueOf(i));
                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    if (FreecessController.FreecessControllerHolder.INSTANCE.getPackageStatus(i)
                            != null) {
                        synchronized (MARsPolicyManager.MARsLock) {
                        }
                    }
                }

                public final void onUidGone(int i, boolean z) {
                    UidStateMgr uidStateMgr = UidStateMgr.this;
                    uidStateMgr.mUidIdleList.remove(Integer.valueOf(i));
                    UidStateMgr uidStateMgr2 = UidStateMgr.this;
                    if (uidStateMgr2.mRunningList.remove(Integer.valueOf(i))) {
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        if (FreecessController.FreecessControllerHolder.INSTANCE.getPackageStatus(i)
                                != null) {
                            boolean z3 = EventRecorder.FEATURE_ENABLE;
                            EventRecorder.EventRecorderHolder.INSTANCE.onUidStop(
                                    Integer.valueOf(i), System.currentTimeMillis());
                        }
                    }
                    UidStateMgr uidStateMgr3 = UidStateMgr.this;
                    Integer valueOf = Integer.valueOf(i);
                    ConcurrentList concurrentList = uidStateMgr3.mUidGoneList;
                    if (!concurrentList.contains(valueOf)) {
                        concurrentList.add(Integer.valueOf(i));
                    }
                    boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController =
                            FreecessController.FreecessControllerHolder.INSTANCE;
                    freecessController.getClass();
                    synchronized (MARsPolicyManager.MARsLock) {
                        try {
                            FreecessPkgStatus packageStatus =
                                    freecessController.getPackageStatus(i);
                            if (packageStatus == null) {
                                return;
                            }
                            if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                    packageStatus.userId, packageStatus.name)) {
                                if (freecessController.mScreenOn) {
                                    packageStatus.freezedState = 1;
                                    FreecessHandler freecessHandler =
                                            FreecessHandler.FreecessHandlerHolder.INSTANCE;
                                    freecessHandler.removeBgTriggerMsgByObj(1, packageStatus.name);
                                    freecessHandler.removeBgTriggerMsgByObj(2, packageStatus.name);
                                    freecessHandler.removeBgTriggerMsgByObj(28, packageStatus.name);
                                    freecessHandler.removeBgTriggerMsgByObj(3, packageStatus.name);
                                    freecessHandler.removeBgTriggerMsgByObj(4, packageStatus.name);
                                }
                            }
                        } finally {
                        }
                    }
                }

                public final void onUidIdle(int i, boolean z) {
                    UidStateMgr uidStateMgr = UidStateMgr.this;
                    Integer valueOf = Integer.valueOf(i);
                    ConcurrentList concurrentList = uidStateMgr.mUidIdleList;
                    if (!concurrentList.contains(valueOf)) {
                        concurrentList.add(Integer.valueOf(i));
                    }
                    UidStateMgr.this.addToRunningList(i);
                }

                public final void onUidProcAdjChanged(int i, int i2) {}

                public final void onUidStateChanged(int i, int i2, long j, int i3) {
                    boolean isMARsTarget;
                    if (i2 <= 3) {
                        boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController.FreecessControllerHolder.INSTANCE.unFreezePackage(
                                i, "procstate");
                    }
                    if (i2 == 2) {
                        UidStateMgr uidStateMgr = UidStateMgr.this;
                        Integer valueOf = Integer.valueOf(i);
                        ConcurrentList concurrentList = uidStateMgr.mTopUidList;
                        if (!concurrentList.contains(valueOf)) {
                            concurrentList.add(Integer.valueOf(i));
                        }
                    } else {
                        UidStateMgr uidStateMgr2 = UidStateMgr.this;
                        uidStateMgr2.mTopUidList.remove(Integer.valueOf(i));
                    }
                    if (i2 == 4 || i2 == 5) {
                        UidStateMgr uidStateMgr3 = UidStateMgr.this;
                        Integer valueOf2 = Integer.valueOf(i);
                        ConcurrentList concurrentList2 = uidStateMgr3.mUidForegroundList;
                        if (!concurrentList2.contains(valueOf2)) {
                            concurrentList2.add(Integer.valueOf(i));
                        }
                    } else {
                        UidStateMgr uidStateMgr4 = UidStateMgr.this;
                        uidStateMgr4.mUidForegroundList.remove(Integer.valueOf(i));
                    }
                    UidStateMgr uidStateMgr5 = UidStateMgr.this;
                    Integer valueOf3 = Integer.valueOf(i);
                    uidStateMgr5.getClass();
                    boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    FreecessController freecessController =
                            FreecessController.FreecessControllerHolder.INSTANCE;
                    freecessController.getClass();
                    FreecessPkgStatus packageStatus = freecessController.getPackageStatus(i);
                    if (packageStatus == null) {
                        isMARsTarget = false;
                    } else {
                        isMARsTarget =
                                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(
                                        packageStatus.userId, packageStatus.name);
                    }
                    if (isMARsTarget) {
                        boolean z3 = EventRecorder.FEATURE_ENABLE;
                        EventRecorder eventRecorder = EventRecorder.EventRecorderHolder.INSTANCE;
                        eventRecorder.getClass();
                        if (EventRecorder.FEATURE_ENABLE) {
                            long currentTimeMillis = System.currentTimeMillis();
                            synchronized (eventRecorder.mLock) {
                                try {
                                    EventRecorder.Event event =
                                            (EventRecorder.Event)
                                                    eventRecorder.mLatestUnfreezeEvent.get(
                                                            valueOf3);
                                    if (event != null && event.time + 500 >= currentTimeMillis) {
                                        event.procStateAfterEvent = Integer.valueOf(i2);
                                    }
                                    eventRecorder.mLatestUnfreezeEvent.remove(valueOf3);
                                } finally {
                                }
                            }
                        }
                        synchronized (MARsPolicyManager.MARsLock) {
                            freecessController.getPackageStatus(i).latestProcState = i2;
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class UidStateMgrHolder {
        public static final UidStateMgr INSTANCE = new UidStateMgr();
    }

    public final void addToRunningList(int i) {
        Integer valueOf = Integer.valueOf(i);
        ConcurrentList concurrentList = this.mRunningList;
        if (concurrentList.contains(valueOf)) {
            return;
        }
        concurrentList.add(Integer.valueOf(i));
        boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
        if (FreecessController.FreecessControllerHolder.INSTANCE.getPackageStatus(i) != null) {
            boolean z2 = EventRecorder.FEATURE_ENABLE;
            EventRecorder.EventRecorderHolder.INSTANCE.onUidStart(
                    Integer.valueOf(i), System.currentTimeMillis());
        }
    }

    public final boolean isUidActive(int i) {
        if (this.mRunningList.contains(Integer.valueOf(i))) {
            if (!this.mUidIdleList.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUidIdle(int i) {
        return this.mUidIdleList.contains(Integer.valueOf(i));
    }

    public final boolean isUidRunning(int i) {
        return this.mRunningList.contains(Integer.valueOf(i));
    }
}
