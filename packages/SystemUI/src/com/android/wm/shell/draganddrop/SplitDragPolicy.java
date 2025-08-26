package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DragAndDropPermissions;
import android.widget.Toast;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.desktopmode.DesktopModeUtils;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class SplitDragPolicy {
    public final Context mContext;
    public final RectF mDisallowHitRegion;
    public final SparseArray mDropTargetProviders;
    public final FreeformStarter mFreeformStarter;
    public final Starter mFullscreenStarter;
    public boolean mIsIntentSenderDropTarget;
    public InstanceId mLoggerSessionId;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public final MultiWindowManager mMultiWindowManager;
    public DragSession mSession;
    public final SplitScreenController mSplitScreen;
    public final SplitScreenController mSplitscreenStarter;
    public final ArrayList mTargets;
    public Toast mToast;
    public final Transitions mTransitions;

    public class DefaultStarter implements Starter {
        public final Context mContext;

        public DefaultStarter(Context context) {
            this.mContext = context;
        }

        @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
        public final void startIntent(PendingIntent pendingIntent, int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3) throws PendingIntent.CanceledException {
            if (windowContainerToken != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -9147733928334905053L, 0, null);
            }
            try {
                pendingIntent.send(this.mContext, 0, null, null, null, null, bundle);
            } catch (PendingIntent.CanceledException e) {
                Slog.e("SplitDragPolicy", "Failed to launch activity", e);
            }
        }

        @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
        public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
            try {
                ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, null, bundle, userHandle);
            } catch (ActivityNotFoundException e) {
                Slog.e("SplitDragPolicy", "Failed to launch shortcut", e);
            }
        }

        @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
        public final void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken) {
            if (windowContainerToken != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -9147733928334905053L, 0, null);
            }
            try {
                ActivityTaskManager.getService().startActivityFromRecents(i, bundle);
            } catch (RemoteException e) {
                Slog.e("SplitDragPolicy", "Failed to launch task", e);
            }
        }
    }

    public class Target {
        public boolean alreadyRun;
        public final Rect drawRegion;
        public final Rect hitRegion;
        public final int index;
        public final boolean isResizable;
        public final List polygon;
        public final int type;

        public Target(int i, Rect rect, Rect rect2, int i2) {
            this(i, rect, rect2, i2, true, null);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Target) {
                return this.type == ((Target) obj).type;
            }
            return false;
        }

        public final boolean isMultiSplit() {
            int i = this.type;
            return i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11 || i == 12 || i == 13;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Target {type=");
            sb.append(this.type);
            sb.append(" hit=");
            sb.append(this.hitRegion);
            sb.append(" draw=");
            sb.append(this.drawRegion);
            sb.append(" index=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.index, "}", sb);
        }

        public Target(int i, Rect rect, Rect rect2, int i2, boolean z) {
            this(i, rect, rect2, i2, z, null);
        }

        public Target(int i, Rect rect, Rect rect2, int i2, boolean z, List<PointF> list) {
            this.type = i;
            this.hitRegion = rect;
            this.drawRegion = rect2;
            this.index = i2;
            this.isResizable = z;
            this.polygon = list;
        }
    }

    public SplitDragPolicy(Context context, SplitScreenController splitScreenController, DragZoneAnimator dragZoneAnimator) {
        this(context, splitScreenController, dragZoneAnimator, null, null);
    }

    public final void calculateDesktopTaskBounds(LaunchOptions launchOptions, ActivityOptions activityOptions) {
        if (launchOptions == null) {
            return;
        }
        Rect rectCalculateDefaultDesktopTaskBounds = DesktopModeUtils.calculateDefaultDesktopTaskBounds(this.mSession.displayLayout);
        Rect rect = new Rect();
        this.mSession.displayLayout.getStableBounds(rect, false);
        int iWidth = rectCalculateDefaultDesktopTaskBounds.width();
        int iHeight = rectCalculateDefaultDesktopTaskBounds.height();
        int iMax = Math.max(launchOptions.dropPositionX - (iWidth / 2), 0);
        int iMax2 = Math.max(launchOptions.dropPositionY, rect.top);
        int i = iWidth + iMax;
        int iMax3 = Math.max(i - rect.right, 0);
        int i2 = iHeight + iMax2;
        int iMax4 = Math.max(i2 - rect.bottom, 0);
        Rect rect2 = new Rect(iMax, iMax2, i, i2);
        rect2.offset(-iMax3, -iMax4);
        activityOptions.setLaunchBounds(rect2);
    }

    public final Rect getCenterFreeformBounds() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        Rect rect = new Rect();
        DisplayLayout displayLayout = this.mSession.displayLayout;
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        if (!this.mSession.displayLayout.isLandscape() || (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && !isInSubDisplay())) {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_width);
            dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_height);
        } else {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_height);
            dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_width);
        }
        int iWidth = rect.width();
        int iHeight = rect.height();
        int iM = AbsActionBarView$$ExternalSyntheticOutline0.m(iWidth, dimensionPixelSize, 2, rect.left);
        int i = (iHeight - dimensionPixelSize2) / 2;
        rect.set(iM, i, dimensionPixelSize + iM, dimensionPixelSize2 + i);
        return rect;
    }

    public final boolean isInSubDisplay() {
        return this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
    }

    public final void launchApp(DragSession dragSession, Starter starter, int i, WindowContainerToken windowContainerToken, int i2, DragAndDropPermissions dragAndDropPermissions, LaunchOptions launchOptions, int i3) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -884179424239469908L, 1, Long.valueOf(i));
        }
        ClipDescription description = dragSession.mInitialDragData.getDescription();
        boolean zHasMimeType = description.hasMimeType("application/vnd.android.task");
        boolean zHasMimeType2 = description.hasMimeType("application/vnd.android.shortcut");
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(3);
        activityOptionsMakeBasic.setLaunchDisplayId(i3);
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
            calculateDesktopTaskBounds(launchOptions, activityOptionsMakeBasic);
        }
        Bundle bundle = activityOptionsMakeBasic.toBundle();
        if (dragSession.appData.hasExtra("android.intent.extra.ACTIVITY_OPTIONS")) {
            bundle.putAll(dragSession.appData.getBundleExtra("android.intent.extra.ACTIVITY_OPTIONS"));
        }
        UserHandle userHandleMyUserHandle = (UserHandle) dragSession.appData.getParcelableExtra("android.intent.extra.USER");
        if (userHandleMyUserHandle == null) {
            userHandleMyUserHandle = Process.myUserHandle();
        }
        int i4 = launchOptions != null ? launchOptions.splitDivision : -1;
        int i5 = launchOptions != null ? launchOptions.cellPosition : 0;
        boolean z = launchOptions != null ? launchOptions.parallelMultiSplit : false;
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle);
        activityOptionsFromBundle.setStartedFromWindowTypeLauncher(true);
        Bundle bundle2 = activityOptionsFromBundle.toBundle();
        DragSession dragSession2 = this.mSession;
        ExecutableAppHolder executableAppHolder = dragSession2.mExecutableAppHolder;
        if (executableAppHolder != null && executableAppHolder.mExecutableApp != null) {
            startDragAndSplit(starter, dragSession.appData, i, i2, dragAndDropPermissions, bundle2, i4, i5, z);
            if (CoreRune.MW_DND_SA_LOGGING) {
                if ((starter instanceof SplitScreenController) && (i != -1 || i5 != 0)) {
                    CoreSaLogger.logForAdvanced("1000", "From App content DragNSplit");
                    if (this.mSplitScreen.isMultiSplitScreenVisible() || i5 != 0) {
                        CoreSaLogger.logForAdvanced("1021", "From App content DragNSplit");
                    }
                }
                if (starter instanceof FreeformStarter) {
                    CoreSaLogger.logForAdvanced("2004", "From App content DragNSplit");
                    return;
                }
                return;
            }
            return;
        }
        int i6 = i4;
        boolean z2 = z;
        UserHandle userHandle = userHandleMyUserHandle;
        if (zHasMimeType) {
            int intExtra = dragSession.appData.getIntExtra("android.intent.extra.TASK_ID", -1);
            ComponentName component = dragSession.appData.getComponent();
            if (description.isDragFromRecent() && component != null && (starter instanceof SplitScreenController) && i != -1) {
                startSplitScreenWithAllApps(intExtra, null, component, i, userHandle, i6);
                return;
            }
            if (description.isDragFromRecent()) {
                bundle2.putBoolean("android.pendingIntent.backgroundActivityAllowed", true);
            }
            if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET) {
                starter.startTask(intExtra, i, bundle2, windowContainerToken, i6, i5, z2, true);
                return;
            } else {
                starter.startTask(intExtra, i, bundle2, windowContainerToken);
                return;
            }
        }
        int i7 = i5;
        if (zHasMimeType2) {
            if (windowContainerToken != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, 1109347913185901988L, 0, null);
            }
            starter.startShortcut(dragSession.appData.getStringExtra("android.intent.extra.PACKAGE_NAME"), dragSession.appData.getStringExtra("android.intent.extra.shortcut.ID"), i, bundle2, userHandle);
            return;
        }
        if ((starter instanceof SplitScreenController) && i != -1) {
            List nonFloatingTopTask = dragSession2.getNonFloatingTopTask();
            if (!nonFloatingTopTask.isEmpty() && ((ActivityManager.RunningTaskInfo) nonFloatingTopTask.get(0)).topActivityType == 2) {
                PendingIntent pendingIntent = (PendingIntent) dragSession.appData.getParcelableExtra("android.intent.extra.PENDING_INTENT");
                startSplitScreenWithAllApps(-1, pendingIntent, pendingIntent.getIntent().getComponent(), i, userHandle, i6);
                return;
            }
        }
        PendingIntent pendingIntent2 = (PendingIntent) dragSession.appData.getParcelableExtra("android.intent.extra.PENDING_INTENT");
        if (Build.IS_DEBUGGABLE && !userHandle.equals(pendingIntent2.getCreatorUserHandle())) {
            Log.e("SplitDragPolicy", "Expected app intent's EXTRA_USER to match pending intent user");
        }
        if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET) {
            starter.startIntent(pendingIntent2, userHandle.getIdentifier(), null, i, bundle2, windowContainerToken, false, i2, i6, i7, z2);
        } else {
            starter.startIntent(pendingIntent2, userHandle.getIdentifier(), i, bundle2, windowContainerToken, i2);
        }
    }

    public final void launchIntent(DragSession dragSession, Starter starter, int i, int i2, DragAndDropPermissions dragAndDropPermissions, LaunchOptions launchOptions, int i3) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DRAG_AND_DROP_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, -4103711109691327801L, 1, Long.valueOf(i));
        }
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
        activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(2);
        activityOptionsMakeBasic.setPendingIntentLaunchFlags(402653184);
        activityOptionsMakeBasic.setLaunchDisplayId(i3);
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
            calculateDesktopTaskBounds(launchOptions, activityOptionsMakeBasic);
        }
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(activityOptionsMakeBasic.toBundle());
        activityOptionsFromBundle.setStartedFromWindowTypeLauncher(true);
        Bundle bundle = activityOptionsFromBundle.toBundle();
        int i4 = launchOptions != null ? launchOptions.splitDivision : -1;
        int i5 = launchOptions != null ? launchOptions.cellPosition : 0;
        boolean z = launchOptions != null ? launchOptions.parallelMultiSplit : false;
        if (i4 != -1 || i5 != 0) {
            Intent intent = dragSession.launchableIntent.getIntent();
            if (intent != null) {
                startDragAndSplit(starter, intent, i, i2, dragAndDropPermissions, bundle, i4, i5, z);
                return;
            }
            return;
        }
        ActivityOptions activityOptionsFromBundle2 = ActivityOptions.fromBundle(bundle);
        activityOptionsFromBundle2.setLaunchWindowingMode(1);
        activityOptionsFromBundle2.setPendingIntentBackgroundActivityStartMode(3);
        Bundle bundle2 = activityOptionsFromBundle2.toBundle();
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(dragSession.launchableIntent, (Intent) null, bundle2);
        this.mTransitions.startTransition(1, windowContainerTransaction, null);
    }

    public void onDropped(Target target, WindowContainerToken windowContainerToken) {
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
        int i4 = i;
        Starter starter = i2 == 0 ? this.mFullscreenStarter : this.mSplitscreenStarter;
        DragSession dragSession = this.mSession;
        if (dragSession.appData != null) {
            launchApp(dragSession, starter, i4, windowContainerToken, target.index, null, null, -1);
        } else {
            launchIntent(dragSession, starter, i4, target.index, null, null, -1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00e2 A[Catch: all -> 0x008b, TRY_LEAVE, TryCatch #0 {all -> 0x008b, blocks: (B:25:0x0087, B:28:0x008e, B:32:0x0097, B:34:0x009b, B:36:0x00a1, B:38:0x00b1, B:42:0x00c0, B:43:0x00e2), top: B:52:0x0087 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startDragAndSplit(Starter starter, Intent intent, int i, int i2, DragAndDropPermissions dragAndDropPermissions, Bundle bundle, int i3, int i4, boolean z) {
        int i5;
        int i6 = this.mSession.mExecutableAppHolder.mCallingUserId;
        int userId = this.mContext.getUserId();
        SplitScreenController splitScreenController = this.mSplitScreen;
        if (!splitScreenController.isSplitScreenVisible() && i != -1) {
            intent.putExtra("dropResolverActivity.extra.wallpaper", true);
        }
        intent.putExtra("dragPermission", dragAndDropPermissions);
        if (userId != i6) {
            intent.prepareToLeaveUser(i6);
            intent.putExtra("dropResolverActivity.extra.userid", i6);
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
            CoreSaLogger.logForAdvanced("1042", contentType + "," + str + "," + (component != null ? component.getPackageName() : intent.getPackage()));
        }
        boolean z2 = this.mSession.isDragDataDropResolver;
        if (dragAndDropPermissions != null && !z2) {
            try {
                dragAndDropPermissions.takeTransient();
            } catch (Throwable th) {
                if (dragAndDropPermissions != null && !z2) {
                    dragAndDropPermissions.release();
                }
                throw th;
            }
        }
        int i7 = this.mSession.isDragDataDropResolver ? userId : i6;
        if (!(starter instanceof SplitScreenController) || splitScreenController.isSplitScreenVisible()) {
            starter.startDragAndSplit(intent, i, i2, bundle, i7, i3, i4, z);
        } else {
            ArrayList arrayList = (ArrayList) this.mSession.mVisibleTasks.getFullscreenTasks();
            if (!arrayList.isEmpty() && ((i5 = ((ActivityManager.RunningTaskInfo) arrayList.get(0)).topActivityType) == 2 || i5 == 3)) {
                startSplitScreenWithAllApps(-1, PendingIntent.getActivityAsUser(this.mContext, 0, intent, 33554432, null, new UserHandle(i7)), intent.getComponent(), i, new UserHandle(i7), i3);
            }
        }
        if (dragAndDropPermissions == null || z2) {
            return;
        }
        dragAndDropPermissions.release();
    }

    public final void startSplitScreenWithAllApps(int i, PendingIntent pendingIntent, ComponentName componentName, int i2, UserHandle userHandle, int i3) {
        if (i2 == -1) {
            return;
        }
        Intent edgeAllAppsActivityIntent = MultiWindowUtils.getEdgeAllAppsActivityIntent(componentName, userHandle != null ? userHandle.getIdentifier() : 0, i);
        int i4 = i2 == 0 ? 1 : 0;
        edgeAllAppsActivityIntent.putExtra("start_dnd_split_with_all_apps", true);
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
        if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
            return true;
        }
        return CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && this.mMultiWindowManager.supportMultiSplitAppMinimumSize();
    }

    public SplitDragPolicy(Context context, SplitScreenController splitScreenController, DragZoneAnimator dragZoneAnimator, Transitions transitions, MultiInstanceHelper multiInstanceHelper) {
        this(context, splitScreenController, new DefaultStarter(context), dragZoneAnimator, transitions, multiInstanceHelper);
    }

    public SplitDragPolicy(Context context, SplitScreenController splitScreenController, Starter starter, DragZoneAnimator dragZoneAnimator) {
        this(context, splitScreenController, starter, dragZoneAnimator, null, null);
    }

    public SplitDragPolicy(Context context, SplitScreenController splitScreenController, Starter starter, DragZoneAnimator dragZoneAnimator, Transitions transitions, MultiInstanceHelper multiInstanceHelper) {
        this.mTargets = new ArrayList();
        this.mDisallowHitRegion = new RectF();
        new HashMap();
        SparseArray sparseArray = new SparseArray();
        this.mDropTargetProviders = sparseArray;
        this.mContext = context;
        this.mSplitScreen = splitScreenController;
        this.mFullscreenStarter = starter;
        this.mSplitscreenStarter = splitScreenController;
        this.mFreeformStarter = new FreeformStarter(context);
        this.mMultiWindowManager = MultiWindowManager.getInstance();
        sparseArray.put(1, new AospSplitDropTargetProvider(this, context));
        sparseArray.put(2, new MultiSplitDropTargetProvider(this, context));
        this.mTransitions = transitions;
        this.mMultiInstanceHelper = multiInstanceHelper;
    }

    public class LaunchOptions {
        public final int cellPosition;
        public final int dropPositionX;
        public final int dropPositionY;
        public final boolean parallelMultiSplit;
        public final int splitDivision;
        public final int splitPosition;

        public LaunchOptions(int i, int i2, int i3, boolean z) {
            this.splitPosition = i;
            this.cellPosition = i2;
            this.splitDivision = i3;
            this.parallelMultiSplit = z;
            this.dropPositionX = 0;
            this.dropPositionY = 0;
        }

        public LaunchOptions(int i, int i2, int i3, boolean z, int i4, int i5) {
            this.splitPosition = i;
            this.cellPosition = i2;
            this.splitDivision = i3;
            this.parallelMultiSplit = z;
            this.dropPositionX = i4;
            this.dropPositionY = i5;
        }
    }

    public interface Starter {
        void startIntent(PendingIntent pendingIntent, int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3);

        default void startIntent(PendingIntent pendingIntent, int i, Intent intent, int i2, Bundle bundle, WindowContainerToken windowContainerToken, boolean z, int i3, int i4, int i5, boolean z2) {
            startIntent(pendingIntent, i, i2, bundle, windowContainerToken, i3);
        }

        void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle);

        void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken);

        default void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3, int i4, boolean z, boolean z2) {
            startTask(i, i2, bundle, windowContainerToken);
        }

        default void startDragAndSplit(Intent intent, int i, int i2, Bundle bundle, int i3, int i4, int i5, boolean z) {
        }
    }
}
