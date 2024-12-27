package com.android.server.wm;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.statusbar.IStatusBar;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class LockTaskController {
    public static final SparseArray STATUS_BAR_FLAG_MAP_LOCKED;
    static final int STATUS_BAR_MASK_LOCKED = 128319488;
    static final int STATUS_BAR_MASK_PINNED = 111083520;
    public final Context mContext;
    IDevicePolicyManager mDevicePolicyManager;
    public final Handler mHandler;
    LockPatternUtils mLockPatternUtils;
    IStatusBarService mStatusBarService;
    public final ActivityTaskSupervisor mSupervisor;
    public final TaskChangeNotificationController mTaskChangeNotificationController;
    TelecomManager mTelecomManager;
    WindowManagerService mWindowManager;
    public final LockTaskToken mToken = new LockTaskToken();
    public final ArrayList mLockTaskModeTasks = new ArrayList();
    public final SparseArray mLockTaskPackages = new SparseArray();
    public final SparseIntArray mLockTaskFeatures = new SparseIntArray();
    public volatile int mLockTaskModeState = 0;
    public int mPendingDisableFromDismiss = -10000;
    public boolean mInteractionControlEnabled = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.LockTaskController$1, reason: invalid class name */
    public final class AnonymousClass1 extends IKeyguardDismissCallback.Stub {
        public final /* synthetic */ int val$userId;

        public AnonymousClass1(int i) {
            this.val$userId = i;
        }

        public final void onDismissCancelled() {
            Slog.i("ActivityTaskManager", "setKeyguardState: dismiss cancelled");
        }

        public final void onDismissError() {
            Slog.i("ActivityTaskManager", "setKeyguardState: failed to dismiss keyguard");
        }

        public final void onDismissSucceeded() {
            LockTaskController.this.mHandler.post(new LockTaskController$$ExternalSyntheticLambda5(this.val$userId, 1, this));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockTaskToken extends Binder {
    }

    static {
        SparseArray sparseArray = new SparseArray();
        STATUS_BAR_FLAG_MAP_LOCKED = sparseArray;
        sparseArray.append(1, new Pair(8388608, 2));
        sparseArray.append(2, new Pair(393216, 4));
        sparseArray.append(4, new Pair(2097152, 0));
        sparseArray.append(8, new Pair(16777216, 0));
        sparseArray.append(16, new Pair(0, 8));
    }

    public LockTaskController(Context context, ActivityTaskSupervisor activityTaskSupervisor, Handler handler, TaskChangeNotificationController taskChangeNotificationController) {
        this.mContext = context;
        this.mSupervisor = activityTaskSupervisor;
        this.mHandler = handler;
        this.mTaskChangeNotificationController = taskChangeNotificationController;
    }

    public final boolean activityBlockedFromFinish(final ActivityRecord activityRecord) {
        Task task = activityRecord.task;
        if (task.mLockTaskAuth != 4 && this.mLockTaskModeTasks.indexOf(task) == 0) {
            ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity(true, true);
            if (activityRecord != task.getRootActivity(true, false) || activityRecord != topNonFinishingActivity) {
                TaskFragment taskFragment = activityRecord.getTaskFragment();
                final TaskFragment taskFragment2 = taskFragment.mAdjacentTaskFragment;
                if (taskFragment.asTask() != null || ((!taskFragment.mDelayLastActivityRemoval && (!CoreRune.MW_EMBED_ACTIVITY || !taskFragment.mIsEmbedded)) || taskFragment2 == null || taskFragment.getActivity(new Predicate() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ActivityRecord activityRecord2 = (ActivityRecord) obj;
                        return (activityRecord2.finishing || activityRecord2 == ActivityRecord.this) ? false : true;
                    }
                }) != null || task.getActivity(new Predicate() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ActivityRecord activityRecord2 = (ActivityRecord) obj;
                        return (activityRecord2.finishing || activityRecord2 == ActivityRecord.this || activityRecord2.getTaskFragment() == taskFragment2) ? false : true;
                    }
                }) != null)) {
                    return false;
                }
            }
            Slog.i("ActivityTaskManager", "Not finishing task in lock task mode");
            showLockTaskToast();
            return true;
        }
        return false;
    }

    public final void clearLockedTask(Task task) {
        if (task == null || this.mLockTaskModeTasks.isEmpty()) {
            return;
        }
        if (task == this.mLockTaskModeTasks.get(0)) {
            for (int size = this.mLockTaskModeTasks.size() - 1; size > 0; size--) {
                clearLockedTask((Task) this.mLockTaskModeTasks.get(size));
            }
        }
        removeLockedTask(task);
        if (this.mLockTaskModeTasks.isEmpty()) {
            return;
        }
        task.performClearTaskForReuse(false);
        this.mSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
    }

    public final void clearLockedTasks(String str) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_LOCKTASK, 8891808212671675155L, 0, null, str);
        }
        if (this.mLockTaskModeTasks.isEmpty()) {
            return;
        }
        clearLockedTask((Task) this.mLockTaskModeTasks.get(0));
    }

    public final void dump(PrintWriter printWriter) {
        String str;
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "  LockTaskController:", "    mLockTaskModeState=");
        int i = this.mLockTaskModeState;
        if (i == 0) {
            str = "NONE";
        } else if (i == 1) {
            str = "LOCKED";
        } else if (i != 2) {
            str = "unknown=" + this.mLockTaskModeState;
        } else {
            str = "PINNED";
        }
        m$1.append(str);
        printWriter.println(m$1.toString());
        printWriter.println("    mLockTaskModeTasks=");
        for (int i2 = 0; i2 < this.mLockTaskModeTasks.size(); i2++) {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "      #", " ");
            m.append(this.mLockTaskModeTasks.get(i2));
            printWriter.println(m.toString());
        }
        printWriter.println("    mLockTaskPackages (userId:packages)=");
        for (int i3 = 0; i3 < this.mLockTaskPackages.size(); i3++) {
            printWriter.println("      u" + this.mLockTaskPackages.keyAt(i3) + ":" + Arrays.toString((Object[]) this.mLockTaskPackages.valueAt(i3)));
        }
        printWriter.println();
    }

    public final IDevicePolicyManager getDevicePolicyManager() {
        if (this.mDevicePolicyManager == null) {
            IDevicePolicyManager asInterface = IDevicePolicyManager.Stub.asInterface(ServiceManager.checkService("device_policy"));
            this.mDevicePolicyManager = asInterface;
            if (asInterface == null) {
                Slog.w("ActivityTaskManager", "warning: no DEVICE_POLICY_SERVICE");
            }
        }
        return this.mDevicePolicyManager;
    }

    public final int getLockTaskAuth(Task task, ActivityRecord activityRecord) {
        ComponentName componentName;
        if (activityRecord == null && task == null) {
            return 0;
        }
        if (activityRecord == null) {
            return 1;
        }
        String packageName = (task == null || (componentName = task.realActivity) == null) ? activityRecord.packageName : componentName.getPackageName();
        int i = task != null ? task.mUserId : activityRecord.mUserId;
        int i2 = activityRecord.lockTaskLaunchMode;
        if (i2 != 0) {
            if (i2 == 1) {
                return 0;
            }
            if (i2 == 2) {
                return 4;
            }
            if (i2 != 3) {
                return 0;
            }
            if (isPackageAllowlisted(i, packageName)) {
                return 2;
            }
        } else if (isPackageAllowlisted(i, packageName)) {
            return 3;
        }
        return 1;
    }

    public Pair getStatusBarDisableFlags(int i) {
        int i2 = 134152192;
        int i3 = 31;
        for (int size = STATUS_BAR_FLAG_MAP_LOCKED.size() - 1; size >= 0; size--) {
            SparseArray sparseArray = STATUS_BAR_FLAG_MAP_LOCKED;
            Pair pair = (Pair) sparseArray.valueAt(size);
            if ((sparseArray.keyAt(size) & i) != 0) {
                i2 &= ~((Integer) pair.first).intValue();
                i3 &= ~((Integer) pair.second).intValue();
            }
        }
        return new Pair(Integer.valueOf(STATUS_BAR_MASK_LOCKED & i2), Integer.valueOf(i3));
    }

    public final IStatusBarService getStatusBarService() {
        if (this.mStatusBarService == null) {
            IStatusBarService asInterface = IStatusBarService.Stub.asInterface(ServiceManager.checkService("statusbar"));
            this.mStatusBarService = asInterface;
            if (asInterface == null) {
                Slog.w("StatusBarManager", "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mStatusBarService;
    }

    public final boolean isLockTaskModeViolation(Task task, boolean z) {
        if ((isTaskLocked(task) && !z) || !isLockTaskModeViolationInternal(task, task.mUserId, task.intent, task.mLockTaskAuth)) {
            return false;
        }
        showLockTaskToast();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        if (r4.equals(r5.getComponent().getPackageName()) != false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isLockTaskModeViolationInternal(com.android.server.wm.WindowContainer r3, int r4, android.content.Intent r5, int r6) {
        /*
            r2 = this;
            boolean r0 = r3.isActivityTypeRecents()
            r1 = 0
            if (r0 == 0) goto L12
            android.util.SparseIntArray r0 = r2.mLockTaskFeatures
            int r0 = r0.get(r4, r1)
            r0 = r0 & 8
            if (r0 == 0) goto L12
            return r1
        L12:
            android.util.SparseIntArray r0 = r2.mLockTaskFeatures
            int r4 = r0.get(r4, r1)
            r4 = r4 & 32
            if (r4 == 0) goto L66
            if (r5 != 0) goto L1f
            goto L66
        L1f:
            android.content.ComponentName r4 = android.telecom.TelecomManager.EMERGENCY_DIALER_COMPONENT
            android.content.ComponentName r0 = r5.getComponent()
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L2c
            goto L65
        L2c:
            java.lang.String r4 = "android.intent.action.CALL_EMERGENCY"
            java.lang.String r0 = r5.getAction()
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L39
            goto L65
        L39:
            android.telecom.TelecomManager r4 = r2.mTelecomManager
            if (r4 != 0) goto L47
            android.content.Context r4 = r2.mContext
            java.lang.Class<android.telecom.TelecomManager> r0 = android.telecom.TelecomManager.class
            java.lang.Object r4 = r4.getSystemService(r0)
            android.telecom.TelecomManager r4 = (android.telecom.TelecomManager) r4
        L47:
            if (r4 == 0) goto L4e
            java.lang.String r4 = r4.getSystemDialerPackage()
            goto L4f
        L4e:
            r4 = 0
        L4f:
            if (r4 == 0) goto L66
            android.content.ComponentName r0 = r5.getComponent()
            if (r0 == 0) goto L66
            android.content.ComponentName r0 = r5.getComponent()
            java.lang.String r0 = r0.getPackageName()
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L66
        L65:
            return r1
        L66:
            boolean r3 = r3.isActivityTypeDream()
            if (r3 == 0) goto L6d
            return r1
        L6d:
            if (r5 != 0) goto L70
            goto L84
        L70:
            android.content.Context r3 = r2.mContext
            android.content.ComponentName r3 = com.android.internal.telephony.CellBroadcastUtils.getDefaultCellBroadcastAlertDialogComponent(r3)
            if (r3 != 0) goto L79
            goto L84
        L79:
            android.content.ComponentName r4 = r5.getComponent()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L84
            return r1
        L84:
            r3 = 2
            if (r6 == r3) goto L96
            r3 = 3
            if (r6 == r3) goto L96
            r3 = 4
            if (r6 == r3) goto L96
            java.util.ArrayList r2 = r2.mLockTaskModeTasks
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L96
            r1 = 1
        L96:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.LockTaskController.isLockTaskModeViolationInternal(com.android.server.wm.WindowContainer, int, android.content.Intent, int):boolean");
    }

    public final boolean isPackageAllowlisted(int i, String str) {
        String[] strArr;
        if (str == null || (strArr = (String[]) this.mLockTaskPackages.get(i)) == null) {
            return false;
        }
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTaskLocked(Task task) {
        return this.mLockTaskModeTasks.contains(task);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x003d, code lost:
    
        if (android.provider.Settings.System.getInt(r6.mContext.getContentResolver(), "interaction_control_exit_locked", 0) != 0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x001e, code lost:
    
        if (android.provider.Settings.System.getInt(r6.mContext.getContentResolver(), "lock_to_app_enabled", 0) != 0) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void lockKeyguardIfNeeded(int r7) {
        /*
            r6 = this;
            r0 = -1
            r1 = 1
            r2 = 0
            android.content.Context r3 = r6.mContext     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            java.lang.String r4 = "lock_to_app_exit_locked"
            r5 = -2
            int r3 = android.provider.Settings.Secure.getIntForUser(r3, r4, r5)     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            if (r3 == 0) goto L21
            android.content.Context r3 = r6.mContext     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            android.content.ContentResolver r3 = r3.getContentResolver()     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            java.lang.String r4 = "lock_to_app_enabled"
            int r7 = android.provider.Settings.System.getInt(r3, r4, r2)     // Catch: android.provider.Settings.SettingNotFoundException -> L23
            if (r7 == 0) goto L21
            goto L61
        L21:
            r1 = r2
            goto L61
        L23:
            android.content.Context r3 = r6.mContext
            android.content.ContentResolver r3 = r3.getContentResolver()
            java.lang.String r4 = "access_control_use"
            int r3 = android.provider.Settings.System.getInt(r3, r4, r2)
            if (r3 == 0) goto L40
            android.content.Context r7 = r6.mContext
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.lang.String r3 = "interaction_control_exit_locked"
            int r7 = android.provider.Settings.System.getInt(r7, r3, r2)
            if (r7 == 0) goto L21
            goto L61
        L40:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r0)
            java.lang.String r2 = ""
            java.lang.String r3 = "127605586"
            java.lang.Object[] r1 = new java.lang.Object[]{r3, r1, r2}
            r2 = 1397638484(0x534e4554, float:8.859264E11)
            android.util.EventLog.writeEvent(r2, r1)
            com.android.internal.widget.LockPatternUtils r1 = r6.mLockPatternUtils
            if (r1 != 0) goto L5d
            com.android.internal.widget.LockPatternUtils r1 = new com.android.internal.widget.LockPatternUtils
            android.content.Context r2 = r6.mContext
            r1.<init>(r2)
        L5d:
            boolean r1 = r1.isSecure(r7)
        L61:
            if (r1 == 0) goto L7c
            com.android.server.wm.WindowManagerService r7 = r6.mWindowManager
            r1 = 0
            r7.lockNow(r1)
            com.android.server.wm.WindowManagerService r7 = r6.mWindowManager
            r7.dismissKeyguard(r1, r1)
            com.android.internal.widget.LockPatternUtils r7 = r6.mLockPatternUtils
            if (r7 != 0) goto L79
            com.android.internal.widget.LockPatternUtils r7 = new com.android.internal.widget.LockPatternUtils
            android.content.Context r6 = r6.mContext
            r7.<init>(r6)
        L79:
            r7.requireCredentialEntry(r0)
        L7c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.LockTaskController.lockKeyguardIfNeeded(int):void");
    }

    public final void notifyLockTaskStateChanged(int i) {
        ActivityTaskManagerServiceExt activityTaskManagerServiceExt = this.mSupervisor.mService.mExt;
        activityTaskManagerServiceExt.getClass();
        boolean z = i != 0;
        if (CoreRune.FW_SUPPORT_LOCK_TASK_MODE_BROADCAST) {
            Intent intent = new Intent("com.kddi.agent.action.SCREEN_PINNING_CONDITION");
            intent.putExtra(Constants.JSON_CLIENT_DATA_STATUS, z);
            activityTaskManagerServiceExt.mContext.sendBroadcastAsUser(intent, UserHandle.CURRENT);
        }
        Intent intent2 = new Intent("com.samsung.android.action.LOCK_TASK_MODE");
        intent2.putExtra("enable", z);
        activityTaskManagerServiceExt.mContext.sendBroadcastAsUser(intent2, UserHandle.CURRENT, "com.samsung.android.permission.LOCK_TASK_MODE");
    }

    public final void removeLockedTask(final Task task) {
        if (this.mLockTaskModeTasks.remove(task)) {
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled;
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_LOCKTASK, 8970634498594714645L, 0, null, String.valueOf(task));
            }
            if (this.mLockTaskModeTasks.isEmpty()) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_LOCKTASK, 8735562128135241598L, 0, null, String.valueOf(task), String.valueOf(Debug.getCallers(3)));
                }
                this.mHandler.post(new Runnable() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        IStatusBarService statusBarService;
                        LockTaskController lockTaskController = LockTaskController.this;
                        Task task2 = task;
                        lockTaskController.getClass();
                        int i = task2.mUserId;
                        int i2 = lockTaskController.mLockTaskModeState;
                        lockTaskController.mLockTaskModeState = 0;
                        boolean z = lockTaskController.mInteractionControlEnabled;
                        lockTaskController.mInteractionControlEnabled = false;
                        TaskChangeNotificationController taskChangeNotificationController = lockTaskController.mTaskChangeNotificationController;
                        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(28, lockTaskController.mLockTaskModeState, 0);
                        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyLockTaskModeChanged, obtainMessage);
                        obtainMessage.sendToTarget();
                        try {
                            lockTaskController.setStatusBarState(lockTaskController.mLockTaskModeState, i);
                            lockTaskController.setKeyguardState(lockTaskController.mLockTaskModeState, i);
                            if (i2 == 2) {
                                lockTaskController.lockKeyguardIfNeeded(i);
                            }
                            if (lockTaskController.getDevicePolicyManager() != null) {
                                lockTaskController.getDevicePolicyManager().notifyLockTaskModeChanged(false, (String) null, i);
                            }
                            if (i2 == 2 && !z && (statusBarService = lockTaskController.getStatusBarService()) != null) {
                                statusBarService.showPinningEnterExitToast(false);
                            }
                            lockTaskController.mWindowManager.onLockTaskStateChanged(lockTaskController.mLockTaskModeState);
                            lockTaskController.notifyLockTaskStateChanged(lockTaskController.mLockTaskModeState);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }
    }

    public final void setKeyguardState(int i, int i2) {
        this.mPendingDisableFromDismiss = -10000;
        LockTaskToken lockTaskToken = this.mToken;
        if (i == 0) {
            this.mWindowManager.reenableKeyguard(lockTaskToken, i2);
            return;
        }
        if (i != 1) {
            this.mWindowManager.disableKeyguard(lockTaskToken, "Lock-to-App", i2);
            return;
        }
        if ((this.mLockTaskFeatures.get(i2, 0) & 32) != 0) {
            this.mWindowManager.reenableKeyguard(lockTaskToken, i2);
        } else if (!this.mWindowManager.isKeyguardLocked() || this.mWindowManager.isKeyguardSecure(i2)) {
            this.mWindowManager.disableKeyguard(lockTaskToken, "Lock-to-App", i2);
        } else {
            this.mPendingDisableFromDismiss = i2;
            this.mWindowManager.dismissKeyguard(new AnonymousClass1(i2), null);
        }
    }

    public final void setLockTaskMode(final int i, final Task task, String str, boolean z) {
        int i2 = task.mLockTaskAuth;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled;
        if (i2 == 0) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, -4314079913933391851L, 0, null, null);
                return;
            }
            return;
        }
        if (isLockTaskModeViolation(task, false)) {
            Slog.e("ActivityTaskManager", "setLockTaskMode: Attempt to start an unauthorized lock task.");
            return;
        }
        final Intent intent = task.intent;
        boolean isEmpty = this.mLockTaskModeTasks.isEmpty();
        ActivityTaskSupervisor activityTaskSupervisor = this.mSupervisor;
        if (isEmpty && intent != null) {
            RecentTasks recentTasks = activityTaskSupervisor.mRecentTasks;
            int i3 = task.mUserId;
            if (i != 1) {
                recentTasks.getClass();
            } else {
                for (int size = recentTasks.mTasks.size() - 1; size >= 0; size--) {
                    Task task2 = (Task) recentTasks.mTasks.get(size);
                    if (task2.mUserId == i3) {
                        recentTasks.mService.getClass();
                        int i4 = task2.mLockTaskAuth;
                        if (i4 != 2 && i4 != 3 && i4 != 4) {
                            recentTasks.remove(task2);
                        }
                    }
                }
            }
            this.mHandler.post(new Runnable() { // from class: com.android.server.wm.LockTaskController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IStatusBarService statusBarService;
                    LockTaskController lockTaskController = LockTaskController.this;
                    Intent intent2 = intent;
                    Task task3 = task;
                    int i5 = i;
                    lockTaskController.getClass();
                    String packageName = intent2.getComponent().getPackageName();
                    int i6 = task3.mUserId;
                    try {
                        boolean z2 = lockTaskController.mWindowManager.mExt.mPolicyExt.mIsInteractionControlEnabled;
                        lockTaskController.mInteractionControlEnabled = z2;
                        if (i5 == 2 && !z2 && (statusBarService = lockTaskController.getStatusBarService()) != null) {
                            statusBarService.showPinningEnterExitToast(true);
                        }
                        lockTaskController.mWindowManager.onLockTaskStateChanged(i5);
                        lockTaskController.mLockTaskModeState = i5;
                        TaskChangeNotificationController taskChangeNotificationController = lockTaskController.mTaskChangeNotificationController;
                        Message obtainMessage = taskChangeNotificationController.mHandler.obtainMessage(28, lockTaskController.mLockTaskModeState, 0);
                        taskChangeNotificationController.forAllLocalListeners(taskChangeNotificationController.mNotifyLockTaskModeChanged, obtainMessage);
                        obtainMessage.sendToTarget();
                        lockTaskController.setStatusBarState(i5, i6);
                        lockTaskController.setKeyguardState(i5, i6);
                        if (lockTaskController.getDevicePolicyManager() != null) {
                            lockTaskController.getDevicePolicyManager().notifyLockTaskModeChanged(true, packageName, i6);
                        }
                        lockTaskController.notifyLockTaskStateChanged(i5);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        if (zArr[3]) {
            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, 3321878763832425380L, 0, null, String.valueOf(task), String.valueOf(Debug.getCallers(4)));
        }
        if (!this.mLockTaskModeTasks.contains(task)) {
            this.mLockTaskModeTasks.add(task);
        }
        if (task.mLockTaskUid == -1) {
            task.mLockTaskUid = task.effectiveUid;
        }
        activityTaskSupervisor.mService.mMultiWindowEnableController.dismissMultiWindowMode();
        if (!z) {
            if (i != 0) {
                activityTaskSupervisor.handleNonResizableTaskIfNeeded(task, 0, activityTaskSupervisor.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea(), task.getRootTask(), true);
            }
        } else {
            this.mSupervisor.findTaskToMoveToFront(task, 0, null, str, i != 0);
            activityTaskSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
            Task rootTask = task.getRootTask();
            if (rootTask != null) {
                rootTask.mDisplayContent.executeAppTransition();
            }
        }
    }

    public final void setStatusBarState(int i, int i2) {
        int i3;
        LockTaskToken lockTaskToken = this.mToken;
        IStatusBarService statusBarService = getStatusBarService();
        if (statusBarService == null) {
            Slog.e("ActivityTaskManager", "Can't find StatusBarService");
            return;
        }
        int i4 = 0;
        if (i == 2) {
            i4 = STATUS_BAR_MASK_PINNED;
            i3 = 0;
        } else if (i == 1) {
            Pair statusBarDisableFlags = getStatusBarDisableFlags(this.mLockTaskFeatures.get(i2, 0));
            i4 = ((Integer) statusBarDisableFlags.first).intValue();
            i3 = ((Integer) statusBarDisableFlags.second).intValue();
        } else {
            i3 = 0;
        }
        try {
            statusBarService.disable(i4, lockTaskToken, this.mContext.getPackageName());
            statusBarService.disable2(i3, lockTaskToken, this.mContext.getPackageName());
        } catch (RemoteException e) {
            Slog.e("ActivityTaskManager", "Failed to set status bar flags", e);
        }
    }

    public final void showLockTaskToast() {
        if (this.mLockTaskModeState != 2 || this.mInteractionControlEnabled) {
            return;
        }
        try {
            IStatusBarService statusBarService = getStatusBarService();
            if (statusBarService != null) {
                statusBarService.showPinningEscapeToast();
            }
        } catch (RemoteException e) {
            Slog.e("ActivityTaskManager", "Failed to send pinning escape toast", e);
        }
    }

    public final void startLockTaskMode(int i, Task task, boolean z) {
        int i2 = task.mLockTaskAuth;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled;
        if (i2 == 0) {
            if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, 737192738184050156L, 0, null, null);
                return;
            }
            return;
        }
        if (!z) {
            task.mLockTaskUid = i;
            if (i2 == 1) {
                if (zArr[3]) {
                    ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, -7119521978513736788L, 0, null, null);
                }
                StatusBarManagerInternal statusBarManagerInternal = (StatusBarManagerInternal) LocalServices.getService(StatusBarManagerInternal.class);
                if (statusBarManagerInternal != null) {
                    int i3 = task.mTaskId;
                    IStatusBar iStatusBar = StatusBarManagerService.this.mBar;
                    if (iStatusBar != null) {
                        try {
                            iStatusBar.showScreenPinningRequest(i3);
                            return;
                        } catch (RemoteException unused) {
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (this.mLockTaskModeState == 2) {
                Slog.i("ActivityTaskManager", "Stop app pinning before entering full lock task mode");
                stopLockTaskMode(i, null, true);
            }
        }
        this.mSupervisor.mRootWindowContainer.removeRootTasksInWindowingModes(2);
        if (zArr[3]) {
            ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_LOCKTASK, -1557441750657584614L, 0, null, z ? "Locking pinned" : "Locking fully");
        }
        setLockTaskMode(z ? 2 : 1, task, "startLockTask", true);
    }

    public final void stopLockTaskMode(int i, Task task, boolean z) {
        if (this.mLockTaskModeState == 0) {
            return;
        }
        if (z) {
            if (this.mLockTaskModeState == 2) {
                clearLockedTasks("stopAppPinning");
                return;
            } else {
                Slog.e("ActivityTaskManager", "Attempted to stop app pinning while fully locked");
                showLockTaskToast();
                return;
            }
        }
        if (task == null) {
            throw new IllegalArgumentException("can't stop LockTask for null task");
        }
        int i2 = task.mLockTaskUid;
        if (i == i2 || (i2 == 0 && i == task.effectiveUid)) {
            clearLockedTask(task);
            return;
        }
        StringBuilder sb = new StringBuilder("Invalid uid, expected ");
        ServiceKeeper$$ExternalSyntheticOutline0.m(task.mLockTaskUid, i, " callingUid=", " effectiveUid=", sb);
        sb.append(task.effectiveUid);
        throw new SecurityException(sb.toString());
    }

    public final void updateLockTaskFeatures(int i, int i2) {
        if (i2 == this.mLockTaskFeatures.get(i, 0)) {
            return;
        }
        this.mLockTaskFeatures.put(i, i2);
        SparseIntArray sparseIntArray = this.mLockTaskFeatures;
        WindowManagerServiceExt windowManagerServiceExt = this.mWindowManager.mExt;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerServiceExt.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                windowManagerServiceExt.mPolicyExt.mLockTaskFeatures = sparseIntArray;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        if (this.mLockTaskModeTasks.isEmpty() || i != ((Task) this.mLockTaskModeTasks.get(0)).mUserId) {
            return;
        }
        this.mHandler.post(new LockTaskController$$ExternalSyntheticLambda5(i, 0, this));
    }

    public final void updateLockTaskPackages(int i, String[] strArr) {
        boolean[] zArr;
        this.mLockTaskPackages.put(i, strArr);
        boolean z = true;
        int size = this.mLockTaskModeTasks.size() - 1;
        boolean z2 = false;
        while (true) {
            zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_LOCKTASK_enabled;
            if (size < 0) {
                break;
            }
            Task task = (Task) this.mLockTaskModeTasks.get(size);
            int i2 = task.mLockTaskAuth;
            boolean z3 = i2 == 2 || i2 == 3;
            task.setLockTaskAuth(task.getRootActivity(true, false));
            int i3 = task.mLockTaskAuth;
            boolean z4 = i3 == 2 || i3 == 3;
            if (this.mLockTaskModeState == 1 && task.mUserId == i && z3 && !z4) {
                if (zArr[0]) {
                    ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_LOCKTASK, -4819015209006579825L, 0, null, String.valueOf(task), String.valueOf(task.lockTaskAuthToString()));
                }
                removeLockedTask(task);
                task.performClearTaskForReuse(false);
                z2 = true;
            }
            size--;
        }
        this.mSupervisor.mRootWindowContainer.forAllTasks(new LockTaskController$$ExternalSyntheticLambda1());
        ActivityRecord activityRecord = this.mSupervisor.mRootWindowContainer.topRunningActivity();
        Task task2 = activityRecord != null ? activityRecord.task : null;
        if (this.mLockTaskModeTasks.isEmpty() && task2 != null && task2.mLockTaskAuth == 2) {
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(ProtoLogGroup.WM_DEBUG_LOCKTASK, 2119751067469297845L, 0, null, String.valueOf(task2));
            }
            setLockTaskMode(1, task2, "package updated", false);
        } else {
            z = z2;
        }
        if (z) {
            this.mSupervisor.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
    }
}
