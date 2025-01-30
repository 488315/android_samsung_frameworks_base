package com.android.p038wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.app.WindowConfiguration;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragAndDropPermissions;
import android.view.SurfaceControl;
import android.widget.Toast;
import android.window.TaskAppearedInfo;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragAndDropPolicy {
    public final ActivityTaskManager mActivityTaskManager;
    public String mCallingPackageName;
    public final Context mContext;
    public final RectF mDisallowHitRegion;
    public final SparseArray mDropTargetProviders;
    public final FreeformStarter mFreeformStarter;
    public InstanceId mLoggerSessionId;
    public final MultiWindowManager mMultiWindowManager;
    public DragSession mSession;
    public final SplitScreenController mSplitScreen;
    public final Starter mStarter;
    public final ArrayList mTargets;
    public Toast mToast;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultStarter implements Starter {
        public final Context mContext;

        public DefaultStarter(Context context) {
            this.mContext = context;
        }

        @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
        public final void startIntent(int i, int i2, PendingIntent pendingIntent, Intent intent, Bundle bundle) {
            try {
                pendingIntent.send(this.mContext, 0, intent, null, null, null, bundle);
            } catch (PendingIntent.CanceledException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch activity", e);
            }
        }

        @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
        public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
            try {
                ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, null, bundle, userHandle);
            } catch (ActivityNotFoundException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch shortcut", e);
            }
        }

        @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
        public final void startTask(int i, int i2, Bundle bundle) {
            try {
                ActivityTaskManager.getService().startActivityFromRecents(i, bundle);
            } catch (RemoteException e) {
                Slog.e("DragAndDropPolicy", "Failed to launch task", e);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DragSession {
        public DisplayLayout displayLayout;
        public Intent dragData;
        public boolean dragItemSupportsSplitscreen;
        public boolean isDragDataDropResolver;
        public boolean isDragFromRecent;
        public final ActivityTaskManager mActivityTaskManager;
        public final ExecutableAppHolder mExecutableAppHolder;
        public final ClipData mInitialDragData;
        public final VisibleTasks mVisibleTasks;
        public int runningTaskActType = 1;
        public boolean runningTaskSupportsSplitScreen;

        public DragSession(ActivityTaskManager activityTaskManager, DisplayLayout displayLayout, ClipData clipData, MultiWindowManager multiWindowManager, ExecutableAppHolder executableAppHolder, VisibleTasks visibleTasks) {
            this.mActivityTaskManager = activityTaskManager;
            this.mInitialDragData = clipData;
            this.displayLayout = displayLayout;
            this.mExecutableAppHolder = executableAppHolder;
            this.mVisibleTasks = visibleTasks;
        }

        public final List getNonFloatingTopTask(final int i) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) this.mActivityTaskManager.getTasks(Integer.MAX_VALUE, false).stream().filter(new Predicate() { // from class: com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj;
                    DragAndDropPolicy.DragSession.this.getClass();
                    if (((TaskInfo) runningTaskInfo2).displayId != 0) {
                        return false;
                    }
                    return !WindowConfiguration.isFloating(runningTaskInfo2.getWindowingMode());
                }
            }).filter(new Predicate() { // from class: com.android.wm.shell.draganddrop.DragAndDropPolicy$DragSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((ActivityManager.RunningTaskInfo) obj).taskId != i;
                }
            }).findFirst().orElse(null);
            return runningTaskInfo == null ? Collections.EMPTY_LIST : List.of(runningTaskInfo);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LaunchOptions {
        public final int cellPosition;
        public final int splitDivision;
        public final int splitPosition;

        public LaunchOptions(int i, int i2, int i3) {
            this.splitPosition = i;
            this.cellPosition = i2;
            this.splitDivision = i3;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Target {
        public boolean alreadyRun;
        public final Rect drawRegion;
        public final Rect hitRegion;
        public final boolean isResizable;
        public final List polygon;
        public final int type;

        public Target(int i, Rect rect, Rect rect2) {
            this(i, rect, rect2, true);
        }

        public final int convertTypeToCellStagePosition() {
            switch (this.type) {
                case 6:
                    return 24;
                case 7:
                    return 72;
                case 8:
                    return 48;
                case 9:
                    return 96;
                default:
                    return 0;
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Target) {
                return this.type == ((Target) obj).type;
            }
            return false;
        }

        public final boolean isMultiSplit() {
            int i = this.type;
            return i == 6 || i == 7 || i == 8 || i == 9;
        }

        public final String toString() {
            return "Target {hit=" + this.hitRegion + " draw=" + this.drawRegion + "}";
        }

        public Target(int i, Rect rect, Rect rect2, boolean z) {
            this(i, rect, rect2, z, null);
        }

        public Target(int i, Rect rect, Rect rect2, boolean z, List<PointF> list) {
            this.type = i;
            this.hitRegion = rect;
            this.drawRegion = rect2;
            this.isResizable = z;
            this.polygon = list;
        }
    }

    public DragAndDropPolicy(Context context, SplitScreenController splitScreenController) {
        this(context, ActivityTaskManager.getInstance(), splitScreenController, new DefaultStarter(context), MultiWindowManager.getInstance());
    }

    public final Rect getCenterFreeformBounds() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        Rect rect = new Rect();
        DisplayLayout displayLayout = this.mSession.displayLayout;
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        DisplayLayout displayLayout2 = this.mSession.displayLayout;
        boolean z = displayLayout2.mWidth > displayLayout2.mHeight;
        Context context = this.mContext;
        if (!z || (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && !isInSubDisplay())) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_width);
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_height);
        } else {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_height);
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_width);
        }
        int width = rect.width();
        int height = rect.height();
        int m8m = AbsActionBarView$$ExternalSyntheticOutline0.m8m(width, dimensionPixelSize, 2, rect.left);
        int i = (height - dimensionPixelSize2) / 2;
        rect.set(m8m, i, dimensionPixelSize + m8m, dimensionPixelSize2 + i);
        return rect;
    }

    public void handleDrop(Target target, ClipData clipData) {
        int i;
        SplitScreenController splitScreenController;
        if (target == null || !this.mTargets.contains(target)) {
            return;
        }
        int i2 = target.type;
        int i3 = (i2 == 2 || i2 == 1) ? 1 : 0;
        if (i2 == 0 || (splitScreenController = this.mSplitScreen) == null) {
            i = -1;
        } else {
            i = i3 ^ 1;
            splitScreenController.onDroppedToSplit(i, this.mLoggerSessionId);
        }
        startClipDescription(clipData.getDescription(), this.mSession.dragData, i, this.mStarter, null, null, -1);
    }

    public final boolean isInSubDisplay() {
        return this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public final void sendSALogging(Starter starter, int i, int i2, String str, boolean z) {
        String str2 = "noti".equalsIgnoreCase(str) ? "From Noti_D&D" : ("hun".equalsIgnoreCase(str) || "edgelighting".equalsIgnoreCase(str)) ? "From NotiPopUp_D&D" : "ctw".equalsIgnoreCase(str) ? "From App content DragNSplit" : "taskbar".equalsIgnoreCase(str) ? "From Taskbar_D&D" : "appsEdge".equalsIgnoreCase(str) ? "From Apps edge_D&D" : "taskEdge".equalsIgnoreCase(str) ? "From Task edge_D&D" : ("com.sec.android.app.launcher".equals(this.mCallingPackageName) && z) ? "From Recent_taskLP" : null;
        if (str2 != null) {
            if ((starter instanceof SplitScreenController) && (i != -1 || i2 != 0)) {
                CoreSaLogger.logForAdvanced("1000", str2);
                if (this.mSplitScreen.isMultiSplitScreenVisible() || i2 != 0) {
                    CoreSaLogger.logForAdvanced("1021", str2);
                }
            }
            if (starter instanceof FreeformStarter) {
                CoreSaLogger.logForAdvanced("2004", str2);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0135, code lost:
    
        startSplitScreenWithAllApps(-1, android.app.PendingIntent.getActivityAsUser(r19.mContext, 0, r21, com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING, null, new android.os.UserHandle(r11)), r21.getComponent(), r22, new android.os.UserHandle(r11), r16);
     */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x029f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startClipDescription(ClipDescription clipDescription, Intent intent, int i, Starter starter, DragAndDropPermissions dragAndDropPermissions, LaunchOptions launchOptions, int i2) {
        int i3;
        int i4;
        TaskAppearedInfo taskAppearedInfo;
        int i5;
        boolean hasMimeType = clipDescription.hasMimeType("application/vnd.android.task");
        boolean hasMimeType2 = clipDescription.hasMimeType("application/vnd.android.shortcut");
        Bundle bundleExtra = intent.hasExtra("android.intent.extra.ACTIVITY_OPTIONS") ? intent.getBundleExtra("android.intent.extra.ACTIVITY_OPTIONS") : new Bundle();
        UserHandle userHandle = (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
        int i6 = launchOptions != null ? launchOptions.splitDivision : -1;
        int i7 = launchOptions != null ? launchOptions.cellPosition : 0;
        if (bundleExtra != null) {
            bundleExtra.putInt("android.activity.launchDisplayId", i2);
        }
        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundleExtra);
        boolean z = true;
        fromBundle.setStartedFromWindowTypeLauncher(true);
        Bundle bundle = fromBundle.toBundle();
        ExecutableAppHolder executableAppHolder = this.mSession.mExecutableAppHolder;
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (executableAppHolder != null) {
            if (executableAppHolder.mExecutableApp != null) {
                int i8 = executableAppHolder.mCallingUserId;
                int userId = this.mContext.getUserId();
                if (!splitScreenController.isSplitScreenVisible() && i != -1) {
                    intent.putExtra("dropResolverActivity.extra.wallpaper", true);
                }
                intent.putExtra("dragPermission", dragAndDropPermissions);
                if (userId != i8) {
                    intent.prepareToLeaveUser(i8);
                    intent.putExtra("dropResolverActivity.extra.userid", i8);
                }
                AppResult appResult = this.mSession.mExecutableAppHolder.mResult;
                String contentType = appResult != null ? appResult.getContentType() : null;
                DragSession dragSession = this.mSession;
                String str = dragSession.mExecutableAppHolder.mCallingPackageName;
                if (dragSession.isDragDataDropResolver) {
                    intent.putExtra("dropResolverActivity.extra.contentType", contentType);
                    intent.putExtra("dropResolverActivity.extra.callingPackage", str);
                } else {
                    ComponentName component = intent.getComponent();
                    String packageName = component != null ? component.getPackageName() : intent.getPackage();
                    CoreSaLogger.logForAdvanced("1042", contentType + "," + str + "," + packageName);
                    if (CoreRune.SAFE_DEBUG) {
                        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("handleDrop: SALogging... contentType=", contentType, ", callingPackage=", str, ", calleePackage=");
                        m87m.append(packageName);
                        Slog.d("DragAndDropPolicy", m87m.toString());
                    }
                }
                boolean z2 = this.mSession.isDragDataDropResolver;
                if (dragAndDropPermissions != null && !z2) {
                    try {
                        dragAndDropPermissions.takeTransient();
                    } finally {
                        if (dragAndDropPermissions != null && !z2) {
                            dragAndDropPermissions.release();
                        }
                    }
                }
                DragSession dragSession2 = this.mSession;
                int i9 = dragSession2.isDragDataDropResolver ? userId : i8;
                if (starter instanceof SplitScreenController) {
                    ArrayList arrayList = (ArrayList) dragSession2.mVisibleTasks.getFullscreenTasks();
                    if (arrayList.isEmpty() || ((i5 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).topActivityType) != 2 && i5 != 3)) {
                        z = false;
                    }
                }
                starter.startDragAndSplit(intent, i, bundle, i9, i6, i7);
                if (CoreRune.MW_DND_SA_LOGGING) {
                    sendSALogging(starter, i, i7, "ctw", false);
                    return;
                }
                return;
            }
        }
        if (hasMimeType) {
            int intExtra = intent.getIntExtra("android.intent.extra.TASK_ID", -1);
            ComponentName component2 = intent.getComponent();
            if (!clipDescription.isDragFromRecent() || component2 == null || !(starter instanceof SplitScreenController) || i == -1) {
                if (clipDescription.isDragFromRecent()) {
                    bundle.putBoolean("android.pendingIntent.backgroundActivityAllowed", true);
                }
                starter.startTask(intExtra, i, bundle, i6, i7, true);
            } else {
                startSplitScreenWithAllApps(intExtra, null, component2, i, userHandle, i6);
            }
        } else if (hasMimeType2) {
            starter.startShortcut(intent.getStringExtra("android.intent.extra.PACKAGE_NAME"), intent.getStringExtra("android.intent.extra.shortcut.ID"), i, bundle, userHandle, i6, i7);
        } else {
            int dragSourceTaskId = clipDescription.getDragSourceTaskId();
            if (dragSourceTaskId != -1) {
                ShellTaskOrganizer shellTaskOrganizer = splitScreenController != null ? splitScreenController.mTaskOrganizer : null;
                if (shellTaskOrganizer != null) {
                    synchronized (shellTaskOrganizer.mLock) {
                        taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(dragSourceTaskId);
                    }
                    if (taskAppearedInfo != null && taskAppearedInfo.getTaskInfo().isVisible() && taskAppearedInfo.getTaskInfo().getWindowingMode() == 1) {
                        Slog.d("DragAndDropPolicy", "hideDragSourceTaskImmediately: leash=" + taskAppearedInfo.getLeash());
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.setAlpha(taskAppearedInfo.getLeash(), 0.0f);
                        transaction.apply();
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.reorder(taskAppearedInfo.getTaskInfo().token, false);
                        shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                    }
                }
            }
            if ((starter instanceof SplitScreenController) && i != -1) {
                List nonFloatingTopTask = this.mSession.getNonFloatingTopTask(dragSourceTaskId);
                if (!nonFloatingTopTask.isEmpty()) {
                    i3 = 0;
                    if (((ActivityManager.RunningTaskInfo) nonFloatingTopTask.get(0)).topActivityType == 2) {
                        i4 = 0;
                        i3 = 1;
                        if (i3 != 0) {
                            PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android.intent.extra.PENDING_INTENT");
                            startSplitScreenWithAllApps(-1, pendingIntent, pendingIntent.getIntent().getComponent(), i, userHandle, i6);
                        } else {
                            PendingIntent pendingIntent2 = (PendingIntent) intent.getParcelableExtra("android.intent.extra.PENDING_INTENT");
                            bundle.putBoolean("android.pendingIntent.backgroundActivityAllowed", true);
                            bundle.putBoolean("android.pendingIntent.backgroundActivityAllowedByPermission", true);
                            starter.startIntent(pendingIntent2, userHandle != null ? userHandle.getIdentifier() : i4, null, i, bundle, i6, i7);
                        }
                    }
                    i4 = i3;
                    if (i3 != 0) {
                    }
                }
            }
            i3 = 0;
            i4 = i3;
            if (i3 != 0) {
            }
        }
        if (CoreRune.MW_DND_SA_LOGGING) {
            sendSALogging(starter, i, i7, intent.getStringExtra("com.samsung.android.intent.extra.DRAG_AND_DROP_REQUESTER"), clipDescription.isDragFromRecent());
        }
    }

    public final void startSplitScreenWithAllApps(int i, PendingIntent pendingIntent, ComponentName componentName, int i2, UserHandle userHandle, int i3) {
        if (i2 == -1) {
            return;
        }
        Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(componentName, userHandle != null ? userHandle.getIdentifier() : 0, i);
        int i4 = i2 == 0 ? 1 : 0;
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (pendingIntent != null) {
            splitScreenController.startPendingIntentAndIntent(pendingIntent, edgeAllAppsActivityIntent, i4, i3);
        } else {
            splitScreenController.startTaskAndIntent(i, edgeAllAppsActivityIntent, i4, i3);
        }
    }

    public final boolean supportMultiSplitDropTarget() {
        if (isInSubDisplay() || MultiWindowUtils.isFlexPanelEnabled(this.mContext)) {
            return false;
        }
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (splitScreenController != null && splitScreenController.isSplitScreenVisible()) {
            if (!(CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && this.mMultiWindowManager.supportMultiSplitAppMinimumSize())) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DragAndDropPolicy(Context context, ActivityTaskManager activityTaskManager, SplitScreenController splitScreenController, Starter starter, MultiWindowManager multiWindowManager) {
        this.mTargets = new ArrayList();
        this.mDisallowHitRegion = new RectF();
        this.mCallingPackageName = null;
        SparseArray sparseArray = new SparseArray();
        this.mDropTargetProviders = sparseArray;
        this.mContext = context;
        this.mActivityTaskManager = activityTaskManager;
        this.mSplitScreen = splitScreenController;
        this.mStarter = splitScreenController == null ? starter : splitScreenController;
        this.mFreeformStarter = new FreeformStarter(context);
        this.mMultiWindowManager = multiWindowManager;
        sparseArray.put(1, new AospSplitDropTargetProvider(this, context));
        sparseArray.put(2, new MultiSplitDropTargetProvider(this, context));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Starter {
        void startIntent(int i, int i2, PendingIntent pendingIntent, Intent intent, Bundle bundle);

        default void startIntent(PendingIntent pendingIntent, int i, Intent intent, int i2, Bundle bundle, int i3, int i4) {
            startIntent(i, i2, pendingIntent, intent, bundle);
        }

        void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle);

        default void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle, int i2, int i3) {
            startShortcut(str, str2, i, bundle, userHandle);
        }

        void startTask(int i, int i2, Bundle bundle);

        default void startTask(int i, int i2, Bundle bundle, int i3, int i4, boolean z) {
            startTask(i, i2, bundle);
        }

        default void startDragAndSplit(Intent intent, int i, Bundle bundle, int i2, int i3, int i4) {
        }
    }
}
