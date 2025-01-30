package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;
import com.android.server.UiThread;
import com.android.server.am.ActivityManagerService;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.DexController;
import com.android.server.uri.NeededUriGrants;
import com.samsung.android.rune.CoreRune;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class DexActivityStartInterceptor {
    public static final boolean SAFE_DEBUG = CoreRune.SAFE_DEBUG;
    public final ActivityTaskManagerService mAtmService;
    public final DexController mDexController;
    public VirtualDisplay mDexDisplay = null;
    public final DexRestartAppDialogController mDexRestartAppDialogController = new DexRestartAppDialogController();

    public DexActivityStartInterceptor(DexController dexController, ActivityTaskManagerService activityTaskManagerService) {
        this.mDexController = dexController;
        this.mAtmService = activityTaskManagerService;
    }

    public boolean intercept(Task task, ActivityRecord activityRecord, ActivityRecord activityRecord2, int i, int i2, ActivityOptions activityOptions, NeededUriGrants neededUriGrants, Task task2) {
        boolean z = false;
        if (cancelIntercept(activityRecord, i2)) {
            return false;
        }
        ActivityRecord rootActivity = task != null ? task.getRootActivity() : null;
        ActivityRecord activityRecord3 = (rootActivity == null || activityRecord.processName.equals(rootActivity.processName)) ? rootActivity : null;
        if (this.mDexController.getDexModeLocked() != 2) {
            if (!(activityRecord3 != null && i2 == 0 && activityRecord3.getDisplayId() == 2)) {
                return false;
            }
            if (activityRecord3.getState() != ActivityRecord.State.STOPPING) {
                this.mDexController.moveTaskToDefaultDisplayAndLayoutTask(task, activityRecord3, activityRecord2, activityOptions);
                return false;
            }
            Slog.i("DexActivityStartInterceptor", "delay stopping dex activity process kill, " + activityRecord);
        }
        ActivityRecord activityRecord4 = activityRecord3 != null ? activityRecord3 : activityRecord;
        String str = activityRecord4.processName;
        int uid = activityRecord4.getUid();
        WindowProcessController processController = this.mAtmService.getProcessController(str, uid);
        if (processController == null || (processController.getDisplayId() == i2 && !processController.isKeepProcessAlive())) {
            if (this.mDexController.collectShouldKillProcess(task, str, i2).size() <= 0) {
                Slog.i("DexActivityStartInterceptor", "do not need process kill, " + activityRecord);
                return false;
            }
        } else if (processController.getPid() == ActivityManagerService.MY_PID) {
            Slog.w("DexActivityStartInterceptor", "Skip intercept " + activityRecord4 + " is system process activity");
            return false;
        }
        if (SAFE_DEBUG) {
            Slog.d("DexActivityStartInterceptor", "intercept " + activityRecord + " to d" + i2 + " reusedTask=" + task);
        }
        DexRestartAppInfo createPendingActivityLaunchType = DexRestartAppInfo.createPendingActivityLaunchType(new PendingActivityLaunch(activityRecord, activityRecord2, i, activityRecord.getRootTask(), null, neededUriGrants), str, uid, activityRecord4.info.applicationInfo, task, task2, activityOptions, i2);
        if (task != null) {
            if (task.getDisplayId() == i2) {
                return false;
            }
            Iterator it = createPendingActivityLaunchType.getMovingTaskLocked(this.mDexController, i2).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((DexController.FindTaskResult) it.next()).mTask.isVisible()) {
                    z = true;
                    break;
                }
            }
            if (task.isVisible() || z) {
                this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(createPendingActivityLaunchType, i2);
                return true;
            }
            if (processController != null && this.mDexController.shouldKillProcess(processController, i2) && !this.mDexController.getTaskLocked(str, uid, true, true, i2).isEmpty()) {
                this.mDexController.KillProcessAndWaitDisposed(createPendingActivityLaunchType, i2);
                return true;
            }
            this.mDexController.scheduleKillProcessAndStartActivity(createPendingActivityLaunchType, i2);
            return true;
        }
        if (!this.mDexController.shouldRestartProcess(str)) {
            Iterator it2 = this.mDexController.getTaskLocked(str, uid, false, i2).iterator();
            boolean z2 = false;
            while (it2.hasNext()) {
                Task task3 = ((DexController.FindTaskResult) it2.next()).mTask;
                if (task3 != null) {
                    ActivityRecord topActivity = task3.getTopActivity(false, true);
                    if (topActivity != null && topActivity.isVisible()) {
                        this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(createPendingActivityLaunchType, i2);
                        return true;
                    }
                    z2 = true;
                }
            }
            if (!z2) {
                return false;
            }
            this.mDexController.scheduleKillProcessAndStartActivity(createPendingActivityLaunchType, i2);
            return true;
        }
        if (!this.mDexController.getTaskLocked(str, uid, true, i2).isEmpty()) {
            this.mDexRestartAppDialogController.showRestartAppDialogLocked(createPendingActivityLaunchType, i2);
            return true;
        }
        if (!this.mDexController.getTaskLocked(str, uid, true, true, i2).isEmpty()) {
            this.mDexController.KillProcessAndWaitDisposed(createPendingActivityLaunchType, i2);
            return true;
        }
        this.mDexController.scheduleKillProcessAndStartActivity(createPendingActivityLaunchType, i2);
        return true;
    }

    public boolean interceptStartFromRecents(Task task, ActivityOptions activityOptions) {
        int launchDisplayId = activityOptions != null ? activityOptions.getLaunchDisplayId() : -1;
        ActivityRecord rootActivity = task.getRootActivity();
        boolean z = false;
        if (rootActivity == null || cancelIntercept(rootActivity, launchDisplayId)) {
            return false;
        }
        if (this.mDexController.getDexModeLocked() != 2) {
            if (!(launchDisplayId == 0 && rootActivity.getDisplayId() == 2)) {
                return false;
            }
            if (rootActivity.getState() != ActivityRecord.State.STOPPING) {
                this.mDexController.moveTaskToDefaultDisplayAndLayoutTask(task, rootActivity, null, activityOptions);
                return false;
            }
            Slog.i("DexActivityStartInterceptor", "delay stopping dex activity process kill, " + rootActivity);
        }
        WindowProcessController processController = this.mAtmService.getProcessController(rootActivity.processName, rootActivity.getUid());
        if (processController == null || processController.getDisplayId() == launchDisplayId || task.getDisplayId() == launchDisplayId) {
            Slog.i("DexActivityStartInterceptor", "do not need process kill, " + rootActivity);
            return false;
        }
        DexRestartAppInfo createStartActivityFromRecentsType = DexRestartAppInfo.createStartActivityFromRecentsType(rootActivity.processName, rootActivity.getUid(), rootActivity.info.applicationInfo, task, activityOptions, launchDisplayId);
        if (processController.isKeepProcessAlive()) {
            if (!task.isVisible() || task.getDisplayId() == launchDisplayId) {
                return false;
            }
            createStartActivityFromRecentsType.setKeepProcessAlive(true);
            this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(createStartActivityFromRecentsType, launchDisplayId);
            return true;
        }
        Iterator it = createStartActivityFromRecentsType.getMovingTaskLocked(this.mDexController, launchDisplayId).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (((DexController.FindTaskResult) it.next()).mTask.isVisible()) {
                z = true;
                break;
            }
        }
        if (task.isVisible() || z) {
            this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(createStartActivityFromRecentsType, launchDisplayId);
        } else {
            this.mDexController.scheduleKillProcessAndStartActivity(createStartActivityFromRecentsType, launchDisplayId);
        }
        return true;
    }

    public final boolean cancelIntercept(ActivityRecord activityRecord, int i) {
        if (!DexController.isDefaultOrDexDisplay(i) || !activityRecord.isActivityTypeStandardOrUndefined()) {
            return true;
        }
        if (!activityRecord.isTaskOverlay()) {
            return false;
        }
        Slog.i("DexActivityStartInterceptor", "Overlay activity can be launched on any display.");
        return true;
    }

    public void setDexDisplay(VirtualDisplay virtualDisplay) {
        this.mDexDisplay = virtualDisplay;
    }

    public void setDoNotShowAgainChecked(boolean z) {
        this.mDexRestartAppDialogController.setDoNotShowAgainChecked(z);
    }

    public class DexRestartAppDialogController {
        public boolean mIsDoNotShowAgainChecked = true;
        public boolean mRestartDialogShowRequested = false;
        public final UiHandler mUiHandler = new UiHandler();

        public DexRestartAppDialogController() {
        }

        public void setDoNotShowAgainChecked(boolean z) {
            this.mIsDoNotShowAgainChecked = z;
        }

        public void setRestartDialogShowRequested(boolean z) {
            this.mRestartDialogShowRequested = z;
        }

        public void showRestartAppDialogLocked(DexRestartAppInfo dexRestartAppInfo, int i) {
            if (DexActivityStartInterceptor.SAFE_DEBUG) {
                Slog.d("DexActivityStartInterceptor", "showRestartAppDialogLocked on #" + i + ", for " + dexRestartAppInfo + ", " + Debug.getCallers(5));
            }
            dexRestartAppInfo.skipToShow();
            if (DexActivityStartInterceptor.SAFE_DEBUG) {
                Slog.d("DexActivityStartInterceptor", "Skip to show Restart app DialogmDoNotShowAgainChecked=" + this.mIsDoNotShowAgainChecked);
            }
            DexActivityStartInterceptor.this.mDexController.scheduleMoveTasksBackAndStartPendingActivity(dexRestartAppInfo, i);
        }

        public final class UiHandler extends Handler {
            public UiHandler() {
                super(UiThread.get().getLooper(), null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                Context createDisplayContext;
                Context context;
                if (message.what != 0) {
                    return;
                }
                Slog.d("DexActivityStartInterceptor", "Show dex restart app dialog");
                DexRestartAppInfo dexRestartAppInfo = (DexRestartAppInfo) message.obj;
                int i = message.arg1;
                WindowManagerGlobalLock windowManagerGlobalLock = DexActivityStartInterceptor.this.mAtmService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    if (i == 2) {
                        try {
                            if (DexActivityStartInterceptor.this.mDexDisplay != null) {
                                createDisplayContext = DexActivityStartInterceptor.this.mAtmService.getUiContext().createDisplayContext(DexActivityStartInterceptor.this.mDexDisplay.getDisplay());
                                context = createDisplayContext;
                            }
                        } catch (Throwable th) {
                            WindowManagerService.resetPriorityAfterLockedSection();
                            throw th;
                        }
                    }
                    createDisplayContext = DexActivityStartInterceptor.this.mAtmService.getUiContext();
                    context = createDisplayContext;
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                new DexRestartAppDialog(context, DexActivityStartInterceptor.this.mAtmService, DexActivityStartInterceptor.this.mDexController, DexActivityStartInterceptor.this.mDexRestartAppDialogController, dexRestartAppInfo, i).show();
            }
        }
    }
}
