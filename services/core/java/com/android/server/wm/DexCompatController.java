package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexCompatController implements IController {
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;
    public H mH;
    public final Rect mTmpRect = new Rect();
    public final SparseArray mDecorCaptionHeightInFullscreen = new SparseArray();
    public final SparseArray mDecorCaptionHeightInFreeform = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H(Looper looper) {
            super(looper);
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x0094  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00b3  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r20) {
            /*
                Method dump skipped, instructions count: 284
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.DexCompatController.H.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Request {
        public SafeActivityOptions activityOptions;
        public String callingFeatureId;
        public String callingPackage;
        public int callingUid;
        public Intent intent;
        public int realCallingPid;
        public int realCallingUid;
        public String reason;
        public int userId;
        public WindowProcessController wpc;
    }

    public DexCompatController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    public final void changeWindowingModeIfNeeded(Task task, ActivityRecord activityRecord) {
        Task rootTask;
        if (activityRecord == null
                || !task.isDexCompatEnabled()
                || (rootTask = task.getRootTask()) == null) {
            return;
        }
        LaunchParamsController.LaunchParams launchParams =
                new LaunchParamsController.LaunchParams();
        this.mAtm.mTaskSupervisor.mLaunchParamsController.calculate(
                task, null, activityRecord, null, null, null, 3, launchParams, null);
        int windowingMode = rootTask.getWindowingMode();
        int i = launchParams.mWindowingMode;
        if (i == 0 || i == windowingMode) {
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        windowingMode, "[DexCompat] changeWindowingModeIfNeeded: prev=", ", next=");
        m.append(launchParams.mWindowingMode);
        m.append(", task=");
        m.append(task);
        Slog.d("DexCompatController", m.toString());
        rootTask.setWindowingMode(launchParams.mWindowingMode, false);
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {
        printWriter.println("[DexCompatController]");
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        int dexModeLocked = activityTaskManagerService.mDexController.getDexModeLocked();
        if (dexModeLocked != 0) {
            DisplayContent displayContent =
                    activityTaskManagerService.mRootWindowContainer.getDisplayContent(
                            dexModeLocked == 1 ? 0 : 2);
            if (displayContent == null) {
                return;
            }
            displayContent.getStableRect(this.mTmpRect);
            int min = Math.min(this.mTmpRect.width(), this.mTmpRect.height());
            int defaultWidth = DexCompatBoundsProvider.getDefaultWidth(dexModeLocked, min);
            int defaultHeight = DexCompatBoundsProvider.getDefaultHeight(dexModeLocked, min);
            StringBuilder sb = new StringBuilder("  DexCompat isDefaultSizeCompatible=");
            sb.append(min <= 0 || min > 1400);
            printWriter.println(sb.toString());
            printWriter.println(
                    "  DexCompat DefaultSize=(" + defaultWidth + "x" + defaultHeight + ")");
        }
        printWriter.println();
    }

    public final void getCompatBounds(Task task, Rect rect, ActivityRecord activityRecord, int i) {
        int orientation;
        DexCompatBoundsProvider compatBoundsProvider = getCompatBoundsProvider(i);
        if (compatBoundsProvider == null) {
            Slog.w(
                    "DexCompatController",
                    "rotateDexCompatTaskLocked: cannot found bounds provider, " + task);
            return;
        }
        ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity != null) {
            orientation = topMostActivity.getOrientation();
            if (orientation == -2) {
                orientation = topMostActivity.info.screenOrientation;
            }
        } else {
            orientation =
                    activityRecord != null
                            ? activityRecord.getRootTask() != null
                                    ? activityRecord.getOrientation()
                                    : activityRecord.info.screenOrientation
                            : -1;
        }
        if (task.getDisplayContent() == null) {
            return;
        }
        compatBoundsProvider.mTask = task;
        compatBoundsProvider.mIsPortrait = compatBoundsProvider.isPortrait(orientation);
        compatBoundsProvider
                .mTask
                .getDisplayContent()
                .getStableRect(compatBoundsProvider.mStableBounds);
        compatBoundsProvider.getBounds(rect);
    }

    public DexCompatBoundsProvider getCompatBoundsProvider(int i) {
        if (i == 1) {
            return new DexCompatBoundsProvider();
        }
        if (i == 2) {
            return new DexCompatBoundsProvider.CustomDexCompatBoundsProvider(0);
        }
        if (i == 3) {
            return new DexCompatBoundsProvider.CustomDexCompatBoundsProvider(1);
        }
        return null;
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {
        this.mH = new H(this.mAtm.mH.getLooper());
    }

    public final boolean isOrientationChanged(ActivityRecord activityRecord) {
        if (activityRecord.getRootTask() == null) {
            return false;
        }
        Task task = activityRecord.task;
        if (!task.isDexCompatEnabled()) {
            return false;
        }
        ActivityRecord activityRecord2 = task.topRunningActivityLocked();
        if (activityRecord2 != null && activityRecord2 != activityRecord) {
            return false;
        }
        int requestedOrientation = activityRecord.getRequestedOrientation();
        char c = 2;
        char c2 =
                ActivityInfo.isFixedOrientationPortrait(requestedOrientation)
                        ? (char) 1
                        : ActivityInfo.isFixedOrientationLandscape(requestedOrientation)
                                ? (char) 2
                                : (char) 0;
        if (c2 == 0) {
            return false;
        }
        this.mTmpRect.set(task.getBounds());
        if (!this.mTmpRect.isEmpty() && this.mTmpRect.width() <= this.mTmpRect.height()) {
            c = 1;
        }
        return c != c2;
    }

    public final void rotateDexCompatTaskLocked(ActivityRecord activityRecord) {
        Point point;
        int i;
        int i2;
        int i3;
        Task task = activityRecord.task;
        if (task == null || !task.isDexCompatEnabled()) {
            return;
        }
        DexCompatBoundsProvider compatBoundsProvider =
                getCompatBoundsProvider(task.mDexCompatUiMode);
        if (compatBoundsProvider == null) {
            Slog.w(
                    "DexCompatController",
                    "rotateDexCompatTaskLocked: cannot found bounds provider, " + task);
            return;
        }
        Rect rect = new Rect();
        Rect bounds = task.getBounds();
        int i4 = (bounds.isEmpty() || bounds.width() > bounds.height()) ? 1 : 0;
        if (task.getDisplayContent() != null) {
            compatBoundsProvider.mTask = task;
            compatBoundsProvider.mIsPortrait = compatBoundsProvider.isPortrait(i4);
            compatBoundsProvider
                    .mTask
                    .getDisplayContent()
                    .getStableRect(compatBoundsProvider.mStableBounds);
            compatBoundsProvider.getBounds(rect);
        }
        Task rootTask = task.getRootTask();
        if (rect.isEmpty()) {
            if (task.mDexCompatUiMode != 3 || rootTask == null) {
                return;
            }
            rootTask.setWindowingMode(1);
            return;
        }
        if (rootTask != null && !rootTask.inFreeformWindowingMode()) {
            rootTask.setWindowingMode(5);
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        if (activityTaskManagerService.mDexController.getDexModeLocked() == 2) {
            point = activityTaskManagerService.mDexController.mDexDisplaySize;
        } else {
            point = new Point();
            activityTaskManagerService.mRootWindowContainer.mDefaultDisplay.mDisplay.getRealSize(
                    point);
        }
        int width = (point.x - rect.width()) / 2;
        if ((task.mDexCompatUiMode == 3
                        && compatBoundsProvider.mIsPortrait
                        && activityRecord.getRequestedConfigurationOrientation() == 1)
                || bounds.isEmpty()) {
            i = 0;
        } else {
            width = bounds.left;
            i = bounds.top;
        }
        rect.offsetTo(width, i);
        int i5 = rect.left;
        if (i5 < 0) {
            i2 = -i5;
        } else {
            int i6 = rect.right;
            int i7 = point.x;
            i2 = i6 > i7 ? i7 - i6 : 0;
        }
        int i8 = rect.top;
        if (i8 < 0) {
            i3 = -i8;
        } else {
            int i9 = rect.bottom;
            int i10 = point.y;
            i3 = i9 > i10 ? i10 - i9 : 0;
        }
        rect.offset(i2, i3);
        activityTaskManagerService.resizeTask(task.mTaskId, rect, 0);
    }

    public final void scheduleStartActivityAsToggleFreeform(
            Task task,
            BooleanSupplier booleanSupplier,
            IntSupplier intSupplier,
            Supplier supplier) {
        ActivityTaskManagerService activityTaskManagerService;
        ActivityRecord rootActivity = task.getRootActivity(true, false);
        if (rootActivity == null) {
            return;
        }
        int displayId = task.getDisplayId();
        if (displayId == -1) {
            Slog.w("DexCompatController", "[DexCompat] Display is not invalid.");
            return;
        }
        Intent intent = task.intent;
        intent.addFlags(1048576);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchDisplayId(displayId);
        if (booleanSupplier.getAsBoolean()) {
            this.mTmpRect.setEmpty();
            makeBasic.setLaunchBounds(this.mTmpRect);
        }
        makeBasic.setForceLaunchWindowingMode(intSupplier.getAsInt());
        String str = (String) supplier.get();
        Request request = new Request();
        request.wpc = null;
        request.intent = null;
        request.callingUid = -1;
        request.callingPackage = null;
        request.realCallingPid = 0;
        request.realCallingUid = -1;
        request.activityOptions = null;
        request.reason = null;
        request.userId = 0;
        WindowProcessController windowProcessController = rootActivity.app;
        try {
            if (windowProcessController != null) {
                String str2 = windowProcessController.mName;
                if (Constants.SYSTEMUI_PACKAGE_NAME.equals(str2) || "system:ui".equals(str2)) {
                    Slog.w(
                            "DexCompatController",
                            "startActivityForDexRestart: cannot kill systemui process, root="
                                    + rootActivity
                                    + ", task="
                                    + task);
                    request.intent = intent;
                    request.callingUid = task.mCallingUid;
                    request.callingPackage = task.mCallingPackage;
                    request.callingFeatureId = task.mCallingFeatureId;
                    request.realCallingPid = Binder.getCallingPid();
                    request.realCallingUid = Binder.getCallingUid();
                    request.activityOptions = new SafeActivityOptions(makeBasic);
                    request.reason = str;
                    request.userId = task.mUserId;
                    activityTaskManagerService = this.mAtm;
                    activityTaskManagerService.deferWindowLayout();
                    activityTaskManagerService.mTaskSupervisor.removeTaskById(
                            task.mTaskId,
                            request.callingUid,
                            request.realCallingPid,
                            str,
                            false,
                            true,
                            false);
                    H h = this.mH;
                    h.sendMessage(h.obtainMessage(0, displayId, 0, request));
                    return;
                }
            }
            activityTaskManagerService.mTaskSupervisor.removeTaskById(
                    task.mTaskId,
                    request.callingUid,
                    request.realCallingPid,
                    str,
                    false,
                    true,
                    false);
            H h2 = this.mH;
            h2.sendMessage(h2.obtainMessage(0, displayId, 0, request));
            return;
        } finally {
            activityTaskManagerService.continueWindowLayout();
        }
        request.wpc = rootActivity.app;
        request.intent = intent;
        request.callingUid = task.mCallingUid;
        request.callingPackage = task.mCallingPackage;
        request.callingFeatureId = task.mCallingFeatureId;
        request.realCallingPid = Binder.getCallingPid();
        request.realCallingUid = Binder.getCallingUid();
        request.activityOptions = new SafeActivityOptions(makeBasic);
        request.reason = str;
        request.userId = task.mUserId;
        activityTaskManagerService = this.mAtm;
        activityTaskManagerService.deferWindowLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldBeApplyDexCompatConfigurationLocked(
            com.android.server.wm.ActivityRecord r6,
            android.content.pm.ApplicationInfo r7,
            int r8) {
        /*
            r5 = this;
            r0 = 0
            if (r7 == 0) goto Lab
            if (r6 == 0) goto L17
            boolean r1 = r6.isDexMode()
            if (r1 == 0) goto Lab
            int r1 = r6.getPid()
            int r2 = android.os.Process.myPid()
            if (r1 != r2) goto L17
            goto Lab
        L17:
            com.android.server.wm.ActivityTaskManagerService r1 = r5.mAtm
            com.android.server.wm.DexController r2 = r1.mDexController
            int r2 = r2.getDexModeLocked()
            r3 = 1
            if (r2 != r3) goto L24
            if (r8 == 0) goto L29
        L24:
            r4 = 2
            if (r2 != r4) goto Lab
            if (r8 != r4) goto Lab
        L29:
            java.lang.Class<com.samsung.android.desktopmode.DesktopModeManagerInternal> r8 = com.samsung.android.desktopmode.DesktopModeManagerInternal.class
            java.lang.Object r8 = com.android.server.LocalServices.getService(r8)
            if (r8 != 0) goto L53
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "[DexCompat] DexCompatLaunchPolicy: task="
            r6.<init>(r7)
            r6.append(r5)
            java.lang.String r5 = ", DesktopModeService is null, caller="
            r6.append(r5)
            r5 = 6
            java.lang.String r5 = android.os.Debug.getCallers(r5)
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            java.lang.String r6 = "DexCompatController"
            android.util.Slog.w(r6, r5)
        L51:
            r5 = r0
            goto La7
        L53:
            r5 = 0
            if (r6 != 0) goto L5d
            com.android.server.wm.DexController r6 = r1.mDexController
            int r5 = r6.getDexPolicyFlags(r5, r7)
            goto La7
        L5d:
            boolean r8 = r6.isActivityTypeHome()
            if (r8 != 0) goto La4
            boolean r8 = r6.isActivityTypeDream()
            if (r8 == 0) goto L6a
            goto La4
        L6a:
            int r8 = r6.mProcessAppLaunchPolicy
            if (r8 != 0) goto L84
            com.android.server.wm.DexController r8 = r1.mDexController
            int r5 = r8.getDexPolicyFlags(r5, r7)
            r6.mProcessAppLaunchPolicy = r5
            boolean r5 = r6.isResizeable(r0)
            if (r5 != 0) goto L81
            int r5 = r6.mProcessAppLaunchPolicy
            r5 = r5 | r3
            r6.mProcessAppLaunchPolicy = r5
        L81:
            int r5 = r6.mProcessAppLaunchPolicy
            goto La7
        L84:
            android.content.pm.ActivityInfo r2 = r6.info
            if (r2 == 0) goto L51
            r8 = r8 & r3
            if (r8 == 0) goto L51
            com.android.server.wm.Task r8 = r6.task
            if (r8 == 0) goto L51
            boolean r8 = r8.isDexCompatEnabled()
            if (r8 == 0) goto L51
            com.android.server.wm.DexController r8 = r1.mDexController
            int r5 = r8.getDexPolicyFlags(r5, r7)
            boolean r6 = r6.isResizeable(r0)
            if (r6 != 0) goto La7
            r5 = r5 | 1
            goto La7
        La4:
            r6.mProcessAppLaunchPolicy = r0
            goto L51
        La7:
            r5 = r5 & r3
            if (r5 == 0) goto Lab
            r0 = r3
        Lab:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DexCompatController.shouldBeApplyDexCompatConfigurationLocked(com.android.server.wm.ActivityRecord,"
                    + " android.content.pm.ApplicationInfo, int):boolean");
    }
}
