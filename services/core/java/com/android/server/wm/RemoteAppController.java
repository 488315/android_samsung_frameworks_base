package com.android.server.wm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.secutil.Slog;
import com.android.server.wm.ActivityStarter;
import com.android.server.uri.NeededUriGrants;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class RemoteAppController implements IController {
    public final ActivityTaskManagerService mAtmService;
    public final ArrayList mListeners = new ArrayList();
    public final Object mLock = new Object();
    public RootWindowContainer mRoot;

    @Override // com.android.server.wm.IController
    public void initialize() {
    }

    public RemoteAppController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
    }

    @Override // com.android.server.wm.IController
    public void setWindowManager(WindowManagerService windowManagerService) {
        this.mRoot = this.mAtmService.mRootWindowContainer;
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[RemoteAppController]");
        printWriter.println(str + "isRemoteAppDisplayRunning=" + isRemoteAppDisplayRunningLocked());
        synchronized (this.mLock) {
            printWriter.println(str + "mListeners=" + this.mListeners);
        }
    }

    public void registerCallbacks(RemoteAppControllerCallbacks remoteAppControllerCallbacks) {
        synchronized (this.mLock) {
            this.mListeners.add(remoteAppControllerCallbacks);
            Slog.d("RemoteAppController", "registerCallbacks: " + remoteAppControllerCallbacks);
        }
    }

    public void unregisterCallbacks(RemoteAppControllerCallbacks remoteAppControllerCallbacks) {
        synchronized (this.mLock) {
            this.mListeners.remove(remoteAppControllerCallbacks);
            Slog.d("RemoteAppController", "unregisterCallbacks: " + remoteAppControllerCallbacks);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean notifyStartActivityInterceptedLocked(Intent intent, ActivityOptions activityOptions, ActivityInfo activityInfo, int i, boolean z, ActivityManager.RunningTaskInfo runningTaskInfo, ActivityRecord activityRecord, NeededUriGrants neededUriGrants, int i2, int i3, ActivityStarter.Request request) {
        CallerInfo callerInfo;
        CallerInfo callerInfo2;
        boolean z2;
        if (neededUriGrants != null) {
            if (activityRecord != null) {
                callerInfo = new CallerInfo(activityRecord);
                callerInfo2 = callerInfo;
                synchronized (this.mLock) {
                    Iterator it = this.mListeners.iterator();
                    z2 = false;
                    while (it.hasNext()) {
                        z2 = ((RemoteAppControllerCallbacks) it.next()).onStartActivityInterceptedLocked(intent, activityOptions, activityInfo, i, z, runningTaskInfo, callerInfo2, neededUriGrants, i2, i3);
                    }
                }
                return z2;
            }
            if (request != null) {
                callerInfo2 = new CallerInfo(request);
                synchronized (this.mLock) {
                }
            }
        }
        callerInfo = null;
        callerInfo2 = callerInfo;
        synchronized (this.mLock) {
        }
    }

    public boolean interceptStartActivityFromRecentsLocked(Task task, ActivityOptions activityOptions, int i, String str) {
        ActivityRecord rootActivity = task.getRootActivity();
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (rootActivity != null && rootActivity.intent != null && displayContent != null) {
            if (rootActivity.isTaskOverlay()) {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("RemoteAppController", "interceptStartActivityFromRecentsLocked: Can't intercept taskOverlay, r=" + rootActivity);
                }
                return false;
            }
            if (rootActivity.isResolverOrDelegateActivity()) {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("RemoteAppController", "interceptStartActivityFromRecentsLocked: Can't intercept Chooser/Resolver, r=" + rootActivity);
                }
                return false;
            }
            if (i == 0 && isRemoteAppDisplayLocked(task.getDisplayId())) {
                boolean notifyStartActivityInterceptedLocked = notifyStartActivityInterceptedLocked(rootActivity.intent, activityOptions, rootActivity.info, i, task.isVisible(), task.getTaskInfo(), null, null, rootActivity.mUserId, 2, null);
                Slog.d("RemoteAppController", "interceptStartActivityFromRecentsLocked: intercepted=" + notifyStartActivityInterceptedLocked + ", reason=" + str + ", r=" + rootActivity + ", intent=" + rootActivity.intent + ", displayId=" + i + ", recentTask=" + task + ", intercept_reason=" + RemoteAppControllerCallbacks.interceptReasonToString(2));
                return notifyStartActivityInterceptedLocked;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean interceptStartActivityLocked(ActivityRecord activityRecord, Task task, ActivityRecord activityRecord2, ActivityOptions activityOptions, int i, NeededUriGrants neededUriGrants, String str, ActivityStarter.Request request) {
        int i2;
        int i3;
        int i4;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        boolean z;
        DisplayContent displayContent = this.mRoot.getDisplayContent(i);
        if (activityRecord.intent != null && displayContent != null) {
            if (activityRecord.isTaskOverlay()) {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("RemoteAppController", "interceptStartActivityLocked: Can't intercept taskOverlay, r=" + activityRecord);
                }
                return false;
            }
            if (activityRecord.isResolverOrDelegateActivity()) {
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("RemoteAppController", "interceptStartActivityLocked: Can't intercept Chooser/Resolver, r=" + activityRecord);
                }
                return false;
            }
            int i5 = 1;
            if (isInterceptRequested(activityRecord.intent)) {
                i3 = 4;
            } else {
                if (displayContent.isRemoteAppDisplay()) {
                    boolean z2 = task == null;
                    Task topMostTask = displayContent.getTopMostTask();
                    if (topMostTask != null && topMostTask.getChildCount() > 0 && (z2 || topMostTask != task)) {
                        i4 = 1;
                    } else if (task == null || task.getDisplayId() == i) {
                        i4 = 0;
                        i5 = 0;
                    } else {
                        i5 = 3;
                        i4 = 1;
                    }
                    i2 = i5;
                    i5 = i4;
                } else if (displayContent.getDisplayId() == 0 && task != null && isRemoteAppDisplayLocked(task.getDisplayId())) {
                    i3 = 2;
                } else {
                    i5 = 0;
                    i2 = 0;
                }
                if (i5 != 0) {
                    if (task != null) {
                        z = task.isVisible();
                        runningTaskInfo = task.getTaskInfo();
                    } else {
                        runningTaskInfo = null;
                        z = false;
                    }
                    boolean notifyStartActivityInterceptedLocked = notifyStartActivityInterceptedLocked(activityRecord.intent, activityOptions, activityRecord.info, i, z, runningTaskInfo, activityRecord2, neededUriGrants, activityRecord.mUserId, i2, request);
                    Slog.d("RemoteAppController", "interceptStartActivityLocked: intercepted=" + notifyStartActivityInterceptedLocked + ", reason=" + str + ", r=" + activityRecord + ", intent=" + activityRecord.intent + ", opts=" + activityOptions + ", displayId=" + i + ", reusedTask=" + task + ", sourceRecord=" + activityRecord2 + ", intentGrants=" + neededUriGrants + ", intercept_reason=" + RemoteAppControllerCallbacks.interceptReasonToString(i2));
                    return notifyStartActivityInterceptedLocked;
                }
            }
            i2 = i3;
            if (i5 != 0) {
            }
        }
        return false;
    }

    public boolean isRemoteAppDisplayRunningLocked() {
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            if (((DisplayContent) this.mRoot.getChildAt(childCount)).isRemoteAppDisplay()) {
                return true;
            }
        }
        return false;
    }

    public boolean isRemoteAppDisplayLocked(int i) {
        for (int childCount = this.mRoot.getChildCount() - 1; childCount >= 0; childCount--) {
            DisplayContent displayContent = (DisplayContent) this.mRoot.getChildAt(childCount);
            if (displayContent.getDisplayId() == i) {
                return displayContent.isRemoteAppDisplay();
            }
        }
        return false;
    }

    public static boolean isValidActivityTypeLocked(ActivityRecord activityRecord) {
        return activityRecord.isActivityTypeStandardOrUndefined() || activityRecord.isActivityTypeAssistant();
    }

    public static boolean isInterceptRequested(Intent intent) {
        try {
            return intent.isRemoteAppLaunch();
        } catch (Exception unused) {
            return false;
        }
    }

    public void startActivityAsCaller(Intent intent, CallerInfo callerInfo, int i, Bundle bundle) {
        this.mAtmService.getActivityStartController().obtainStarter(intent, "startActivityAsCaller").setCallingUid(callerInfo.launchedFromUid).setCallingPackage(callerInfo.launchedFromPackage).setCallingFeatureId(callerInfo.launchedFromFeatureId).setResolvedType(callerInfo.resolvedType).setRequestCode(-1).setActivityOptions(bundle).setUserId(i).setIgnoreTargetSecurity(false).setFilterCallingUid(callerInfo.isResolver ? 0 : callerInfo.launchedFromUid).setBackgroundStartPrivileges(BackgroundStartPrivileges.ALLOW_BAL).execute();
    }

    public class CallerInfo {
        public boolean isResolver;
        public String launchedFromFeatureId;
        public String launchedFromPackage;
        public int launchedFromUid;
        public String resolvedType;

        public CallerInfo(ActivityRecord activityRecord) {
            this.launchedFromUid = activityRecord.launchedFromUid;
            this.launchedFromPackage = activityRecord.launchedFromPackage;
            this.launchedFromFeatureId = activityRecord.launchedFromFeatureId;
            this.isResolver = activityRecord.isResolverOrChildActivity();
            this.resolvedType = activityRecord.resolvedType;
        }

        public CallerInfo(ActivityStarter.Request request) {
            this.launchedFromUid = request.callingUid;
            this.launchedFromPackage = request.callingPackage;
            this.launchedFromFeatureId = request.callingFeatureId;
            this.isResolver = false;
            this.resolvedType = request.resolvedType;
        }
    }
}
