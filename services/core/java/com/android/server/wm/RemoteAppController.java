package com.android.server.wm;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;

public final class RemoteAppController implements IController {
    public final ActivityTaskManagerService mAtmService;
    public final ArrayList mListeners = new ArrayList();
    public final Object mLock = new Object();
    public RootWindowContainer mRoot;

    public final class CallerInfo {
        public boolean isResolver;
        public String launchedFromFeatureId;
        public String launchedFromPackage;
        public int launchedFromUid;
        public String resolvedType;
    }

    public RemoteAppController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "[RemoteAppController]", "  isRemoteAppDisplayRunning=");
        m$1.append(isRemoteAppDisplayRunningLocked());
        printWriter.println(m$1.toString());
        synchronized (this.mLock) {
            printWriter.println("  mListeners=" + this.mListeners);
        }
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {}

    /* JADX WARN: Removed duplicated region for block: B:19:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean interceptStartActivityLocked(
            com.android.server.wm.ActivityRecord r18,
            com.android.server.wm.Task r19,
            com.android.server.wm.ActivityRecord r20,
            android.app.ActivityOptions r21,
            int r22,
            com.android.server.uri.NeededUriGrants r23,
            java.lang.String r24,
            com.android.server.wm.ActivityStarter.Request r25) {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.RemoteAppController.interceptStartActivityLocked(com.android.server.wm.ActivityRecord,"
                    + " com.android.server.wm.Task, com.android.server.wm.ActivityRecord,"
                    + " android.app.ActivityOptions, int, com.android.server.uri.NeededUriGrants,"
                    + " java.lang.String, com.android.server.wm.ActivityStarter$Request):boolean");
    }

    public final boolean isRemoteAppDisplayLocked(int i) {
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mRoot.getChildAt(childCount);
            if (displayContent.mDisplayId == i) {
                return displayContent.isRemoteAppDisplay();
            }
        }
        return false;
    }

    public final boolean isRemoteAppDisplayRunningLocked() {
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            if (((DisplayContent) this.mRoot.getChildAt(childCount)).isRemoteAppDisplay()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean notifyStartActivityInterceptedLocked(
            android.content.Intent r15,
            android.app.ActivityOptions r16,
            android.content.pm.ActivityInfo r17,
            int r18,
            boolean r19,
            android.app.ActivityManager.RunningTaskInfo r20,
            com.android.server.wm.ActivityRecord r21,
            com.android.server.uri.NeededUriGrants r22,
            int r23,
            int r24,
            com.android.server.wm.ActivityStarter.Request r25) {
        /*
            r14 = this;
            r0 = r14
            r1 = r21
            r2 = r25
            r3 = 0
            if (r22 == 0) goto L41
            if (r1 == 0) goto L26
            com.android.server.wm.RemoteAppController$CallerInfo r2 = new com.android.server.wm.RemoteAppController$CallerInfo
            r2.<init>()
            int r4 = r1.launchedFromUid
            r2.launchedFromUid = r4
            java.lang.String r4 = r1.launchedFromPackage
            r2.launchedFromPackage = r4
            java.lang.String r4 = r1.launchedFromFeatureId
            r2.launchedFromFeatureId = r4
            boolean r4 = r21.isResolverOrChildActivity()
            r2.isResolver = r4
            java.lang.String r1 = r1.resolvedType
            r2.resolvedType = r1
            goto L42
        L26:
            if (r2 == 0) goto L41
            com.android.server.wm.RemoteAppController$CallerInfo r1 = new com.android.server.wm.RemoteAppController$CallerInfo
            r1.<init>()
            int r4 = r2.callingUid
            r1.launchedFromUid = r4
            java.lang.String r4 = r2.callingPackage
            r1.launchedFromPackage = r4
            java.lang.String r4 = r2.callingFeatureId
            r1.launchedFromFeatureId = r4
            r1.isResolver = r3
            java.lang.String r2 = r2.resolvedType
            r1.resolvedType = r2
            r2 = r1
            goto L42
        L41:
            r2 = 0
        L42:
            java.lang.Object r1 = r0.mLock
            monitor-enter(r1)
            java.util.ArrayList r0 = r0.mListeners     // Catch: java.lang.Throwable -> L6d
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L6d
        L4b:
            boolean r4 = r0.hasNext()     // Catch: java.lang.Throwable -> L6d
            if (r4 == 0) goto L6f
            java.lang.Object r3 = r0.next()     // Catch: java.lang.Throwable -> L6d
            r4 = r3
            com.android.server.remoteappmode.RemoteAppModeService$1 r4 = (com.android.server.remoteappmode.RemoteAppModeService.AnonymousClass1) r4     // Catch: java.lang.Throwable -> L6d
            r5 = r15
            r6 = r16
            r7 = r17
            r8 = r18
            r9 = r19
            r10 = r20
            r11 = r2
            r12 = r23
            r13 = r24
            boolean r3 = r4.onStartActivityInterceptedLocked(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L6d
            goto L4b
        L6d:
            r0 = move-exception
            goto L71
        L6f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L6d
            return r3
        L71:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L6d
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.RemoteAppController.notifyStartActivityInterceptedLocked(android.content.Intent,"
                    + " android.app.ActivityOptions, android.content.pm.ActivityInfo, int, boolean,"
                    + " android.app.ActivityManager$RunningTaskInfo,"
                    + " com.android.server.wm.ActivityRecord,"
                    + " com.android.server.uri.NeededUriGrants, int, int,"
                    + " com.android.server.wm.ActivityStarter$Request):boolean");
    }

    @Override // com.android.server.wm.IController
    public final void setWindowManager(WindowManagerService windowManagerService) {
        this.mRoot = this.mAtmService.mRootWindowContainer;
    }
}
