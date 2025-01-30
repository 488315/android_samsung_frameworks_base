package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.LocalServices;
import com.android.server.wm.DexCompatBoundsProvider;
import com.android.server.wm.LaunchParamsController;
import com.samsung.android.desktopmode.DesktopModeManagerInternal;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class DexCompatController implements IController {
    public static final boolean DEBUG_DEX_COMPAT = CoreRune.SAFE_DEBUG;
    public final ActivityTaskManagerService mAtm;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public HandlerC2890H f1737mH;
    public final Rect mTmpRect = new Rect();
    public final SparseArray mDecorCaptionHeightInFullscreen = new SparseArray();
    public final SparseArray mDecorCaptionHeightInFreeform = new SparseArray();

    public DexCompatController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1737mH = new HandlerC2890H(this.mAtm.f1734mH.getLooper());
    }

    public boolean resolveDexCompatConfigurationLocked(ActivityRecord activityRecord, ApplicationInfo applicationInfo, int i, Configuration configuration, String str) {
        if (!shouldBeApplyDexCompatConfigurationLocked(activityRecord, applicationInfo, i)) {
            return false;
        }
        applyDexCompatConfigurationLocked(activityRecord, applicationInfo, configuration, str);
        return true;
    }

    public boolean shouldBeApplyDexCompatConfigurationLocked(ActivityRecord activityRecord, ApplicationInfo applicationInfo, int i) {
        if ((activityRecord != null && !activityRecord.isDexMode()) || applicationInfo == null) {
            return false;
        }
        if (activityRecord != null && activityRecord.getPid() == Process.myPid()) {
            return false;
        }
        int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
        return ((dexModeLocked == 1 && i == 0) || (dexModeLocked == 2 && i == 2)) && (getResolvedLaunchPolicyForPackage(activityRecord, applicationInfo) & 1) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void applyDexCompatConfigurationLocked(ActivityRecord activityRecord, ApplicationInfo applicationInfo, Configuration configuration, String str) {
        String str2;
        boolean z;
        int i;
        int max;
        int min;
        if (applicationInfo == null || (str2 = applicationInfo.packageName) == null) {
            return;
        }
        if (activityRecord == null) {
            activityRecord = getTopCompatActivityLocked(str2);
        }
        Task task = activityRecord != null ? activityRecord.getTask() : null;
        if (task != null) {
            z = task.getMergedOverrideConfiguration().dexCompatEnabled == 2;
            if (task.getMergedOverrideConfiguration().orientation == 2) {
                i = 2;
                if (!z) {
                    configuration.updateFromDexCompatTaskConfig(task.getMergedOverrideConfiguration());
                } else {
                    int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
                    DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(dexModeLocked != 1 ? 2 : 0);
                    if (displayContent == null) {
                        return;
                    }
                    displayContent.getStableRect(this.mTmpRect);
                    int min2 = Math.min(this.mTmpRect.width(), this.mTmpRect.height());
                    int defaultWidth = DexCompatBoundsProvider.getDefaultWidth(dexModeLocked, min2);
                    int defaultHeight = DexCompatBoundsProvider.getDefaultHeight(dexModeLocked, min2);
                    if (defaultWidth <= 0 || defaultHeight <= 0) {
                        Slog.w("DexCompatController", "applyDexCompatConfigurationLocked: Invalid size, " + defaultWidth + "x" + defaultHeight);
                    }
                    if (i == 1) {
                        max = Math.min(defaultWidth, defaultHeight);
                        min = Math.max(defaultWidth, defaultHeight);
                    } else {
                        max = Math.max(defaultWidth, defaultHeight);
                        min = Math.min(defaultWidth, defaultHeight);
                    }
                    float f = configuration.densityDpi * 0.00625f;
                    int i2 = (int) ((max / f) + 0.5f);
                    int i3 = (int) ((min / f) + 0.5f);
                    int resetScreenLayout = Configuration.resetScreenLayout(configuration.screenLayout);
                    int max2 = Math.max(i2, i3);
                    int min3 = Math.min(i2, i3);
                    configuration.screenWidthDp = i2;
                    configuration.screenHeightDp = i3;
                    configuration.smallestScreenWidthDp = min3;
                    configuration.orientation = i;
                    configuration.screenLayout = Configuration.reduceScreenLayout(resetScreenLayout, max2, min3);
                }
                configuration.dexCompatEnabled = 2;
                if (!DEBUG_DEX_COMPAT || CoreRune.SAFE_DEBUG) {
                    Slog.i("DexCompatController", "[DexCompat] applyDexCompatConfigurationLocked:  packageName=" + applicationInfo.packageName + ", outConfig=" + configuration + ", reason=" + str + ", candidate=" + activityRecord);
                }
                return;
            }
        } else {
            z = false;
        }
        i = 1;
        if (!z) {
        }
        configuration.dexCompatEnabled = 2;
        if (DEBUG_DEX_COMPAT) {
        }
        Slog.i("DexCompatController", "[DexCompat] applyDexCompatConfigurationLocked:  packageName=" + applicationInfo.packageName + ", outConfig=" + configuration + ", reason=" + str + ", candidate=" + activityRecord);
    }

    public final ActivityRecord getTopCompatActivityLocked(final String str) {
        DisplayContent displayContent;
        int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
        if (dexModeLocked == 2) {
            displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(2);
        } else {
            displayContent = dexModeLocked == 1 ? this.mAtm.mRootWindowContainer.getDisplayContent(0) : null;
        }
        if (str != null && displayContent != null) {
            final ArrayList arrayList = new ArrayList();
            displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.DexCompatController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DexCompatController.lambda$getTopCompatActivityLocked$0(arrayList, (Task) obj);
                }
            });
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ActivityRecord activity = ((Task) it.next()).getActivity(new Predicate() { // from class: com.android.server.wm.DexCompatController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$getTopCompatActivityLocked$1;
                        lambda$getTopCompatActivityLocked$1 = DexCompatController.lambda$getTopCompatActivityLocked$1(str, (ActivityRecord) obj);
                        return lambda$getTopCompatActivityLocked$1;
                    }
                }, true);
                if (activity != null) {
                    return activity;
                }
            }
        }
        return null;
    }

    public static /* synthetic */ void lambda$getTopCompatActivityLocked$0(ArrayList arrayList, Task task) {
        if (task.isDexCompatEnabled()) {
            arrayList.add(task);
        }
    }

    public static /* synthetic */ boolean lambda$getTopCompatActivityLocked$1(String str, ActivityRecord activityRecord) {
        return str.equals(activityRecord.processName) && !activityRecord.finishing;
    }

    public final int getResolvedLaunchPolicyForPackage(ActivityRecord activityRecord, ApplicationInfo applicationInfo) {
        if (((DesktopModeManagerInternal) LocalServices.getService(DesktopModeManagerInternal.class)) == null) {
            Slog.w("DexCompatController", "[DexCompat] DexCompatLaunchPolicy: task=" + this + ", DesktopModeService is null, caller=" + Debug.getCallers(6));
            return 0;
        }
        if (activityRecord == null) {
            return this.mAtm.mDexController.getDexPolicyFlags(applicationInfo);
        }
        if (activityRecord.isActivityTypeHome() || activityRecord.isActivityTypeDream()) {
            activityRecord.mProcessAppLaunchPolicy = 0;
            return 0;
        }
        int i = activityRecord.mProcessAppLaunchPolicy;
        if (i == 0) {
            activityRecord.mProcessAppLaunchPolicy = this.mAtm.mDexController.getDexPolicyFlags(applicationInfo);
            if (!activityRecord.isResizeable(false)) {
                activityRecord.mProcessAppLaunchPolicy |= 1;
            }
            return activityRecord.mProcessAppLaunchPolicy;
        }
        if (activityRecord.info == null || (i & 1) == 0 || activityRecord.getTask() == null || !activityRecord.getTask().isDexCompatEnabled()) {
            return 0;
        }
        int dexPolicyFlags = this.mAtm.mDexController.getDexPolicyFlags(applicationInfo);
        return !activityRecord.isResizeable(false) ? dexPolicyFlags | 1 : dexPolicyFlags;
    }

    public int getDecorCaptionHeight(int i, int i2) {
        Integer num;
        if (i2 == 1) {
            num = (Integer) this.mDecorCaptionHeightInFullscreen.get(i);
        } else {
            num = i2 == 5 ? (Integer) this.mDecorCaptionHeightInFreeform.get(i) : null;
        }
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public void loadResources(int i) {
        if (this.mAtm.mRootWindowContainer.getDisplayContent(i) == null) {
            Slog.w("DexCompatController", "loadResources: failed, cannot find display!");
            return;
        }
        Context displayContext = this.mAtm.mDexController.getDisplayContext(i);
        if (displayContext == null) {
            displayContext = this.mAtm.mContext;
        }
        Resources resources = displayContext.getResources();
        this.mDecorCaptionHeightInFullscreen.put(i, Integer.valueOf(resources.getDimensionPixelSize(R.dimen.toast_width)));
        this.mDecorCaptionHeightInFreeform.put(i, Integer.valueOf(resources.getDimensionPixelSize(R.dimen.toast_text_size)));
    }

    public void updateDexCompatLaunchPolicy(Task task, ActivityInfo activityInfo) {
        String str;
        if (task.isDexMode()) {
            task.mDexLaunchPolicy = this.mAtm.mDexController.getDexPolicyFlags(activityInfo.applicationInfo);
            if (!task.isResizeable(false)) {
                task.mDexLaunchPolicy |= 1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[DexCompat] DexCompatLaunchPolicy: task=");
            sb.append(task);
            sb.append(" mDexLaunchPolicy=0x");
            sb.append(Integer.toHexString(task.mDexLaunchPolicy));
            sb.append(" mResizeMode=");
            sb.append(task.mResizeMode);
            sb.append(" info=");
            sb.append(activityInfo);
            if (DEBUG_DEX_COMPAT) {
                str = ", caller=" + Debug.getCallers(6);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i("DexCompatController", sb.toString());
        }
    }

    public void changeWindowingModeIfNeeded(Task task, Task task2, ActivityRecord activityRecord) {
        LaunchParamsController.LaunchParams launchParams = new LaunchParamsController.LaunchParams();
        this.mAtm.mTaskSupervisor.getLaunchParamsController().calculate(task2, null, activityRecord, null, null, null, 3, launchParams);
        int windowingMode = task.getWindowingMode();
        int i = launchParams.mWindowingMode;
        if (i == 0 || windowingMode == i) {
            return;
        }
        Slog.d("DexCompatController", "[DexCompat] changeWindowingModeIfNeeded: prev=" + windowingMode + ", next=" + launchParams.mWindowingMode + ", task=" + task2);
        task.setWindowingMode(launchParams.mWindowingMode, false);
    }

    public boolean isOrientationChangedLocked(Task task, ActivityRecord activityRecord) {
        int convertToConfigurationOrientation;
        if (!task.isDexCompatEnabled()) {
            return false;
        }
        Task rootTask = task.getRootTask();
        ActivityRecord activityRecord2 = task.topRunningActivityLocked();
        if (rootTask == null || activityRecord == null) {
            return false;
        }
        if ((activityRecord2 != null && activityRecord2 != activityRecord) || (convertToConfigurationOrientation = convertToConfigurationOrientation(activityRecord.getRequestedOrientation())) == 0) {
            return false;
        }
        this.mTmpRect.set(task.getBounds());
        return ((this.mTmpRect.isEmpty() || this.mTmpRect.width() > this.mTmpRect.height()) ? 2 : 1) != convertToConfigurationOrientation;
    }

    public DexCompatBoundsProvider getCompatBoundsProvider(int i) {
        if (i == 1) {
            return new DexCompatBoundsProvider();
        }
        if (i == 2) {
            return new DexCompatBoundsProvider.CustomDexCompatBoundsProvider();
        }
        if (i == 3) {
            return new DexCompatBoundsProvider.FullscreenDexCompatBoundsProvider();
        }
        return null;
    }

    public void getCompatBounds(Task task, Rect rect, ActivityRecord activityRecord) {
        getCompatBounds(task, rect, activityRecord, task.mDexCompatUiMode);
    }

    public void getCompatBounds(Task task, Rect rect, ActivityRecord activityRecord, int i) {
        DexCompatBoundsProvider compatBoundsProvider = getCompatBoundsProvider(i);
        if (compatBoundsProvider == null) {
            Slog.w("DexCompatController", "rotateDexCompatTaskLocked: cannot found bounds provider, " + task);
            return;
        }
        if (compatBoundsProvider.setInitialState(task, getAppOrientation(task, activityRecord))) {
            compatBoundsProvider.getBounds(rect);
        }
    }

    public final int getAppOrientation(Task task, ActivityRecord activityRecord) {
        ActivityRecord topMostActivity = task.getTopMostActivity();
        if (topMostActivity != null) {
            return topMostActivity.getOrientation() != -2 ? topMostActivity.getOrientation() : topMostActivity.info.screenOrientation;
        }
        if (activityRecord == null) {
            return -1;
        }
        if (activityRecord.getRootTask() != null) {
            return activityRecord.getOrientation();
        }
        return activityRecord.info.screenOrientation;
    }

    public void rotateDexCompatTaskLocked(ActivityRecord activityRecord) {
        int i;
        int i2;
        int i3;
        Task task = activityRecord.getTask();
        if (task == null || !task.isDexCompatEnabled()) {
            return;
        }
        Rect bounds = task.getBounds();
        int i4 = (bounds.isEmpty() || bounds.width() > bounds.height()) ? 1 : 0;
        DexCompatBoundsProvider compatBoundsProvider = getCompatBoundsProvider(task.mDexCompatUiMode);
        if (compatBoundsProvider == null) {
            Slog.w("DexCompatController", "rotateDexCompatTaskLocked: cannot found bounds provider, " + task);
            return;
        }
        Rect rect = new Rect();
        if (compatBoundsProvider.setInitialState(task, i4)) {
            compatBoundsProvider.getBounds(rect);
        }
        Task rootTask = task.getRootTask();
        if (!rect.isEmpty()) {
            if (rootTask != null && !rootTask.inFreeformWindowingMode()) {
                rootTask.setWindowingMode(5);
            }
            Point dexScreenSizeLocked = getDexScreenSizeLocked();
            int width = (dexScreenSizeLocked.x - rect.width()) / 2;
            if (task.isDexCompatUiFullscreen() && compatBoundsProvider.mIsPortrait && activityRecord.getRequestedConfigurationOrientation() == 1) {
                i = getDecorCaptionHeight(activityRecord.getDisplayId(), activityRecord.getWindowingMode());
            } else if (bounds.isEmpty()) {
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
                int i7 = dexScreenSizeLocked.x;
                i2 = i6 > i7 ? i7 - i6 : 0;
            }
            int i8 = rect.top;
            if (i8 < 0) {
                i3 = -i8;
            } else {
                int i9 = rect.bottom;
                int i10 = dexScreenSizeLocked.y;
                i3 = i9 > i10 ? i10 - i9 : 0;
            }
            rect.offset(i2, i3);
            this.mAtm.resizeTask(task.mTaskId, rect, 0);
        } else if (task.isDexCompatUiFullscreen() && rootTask != null) {
            rootTask.setWindowingMode(1);
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.i("DexCompatController", "[DexCompat] rotateCompatTaskLocked: " + task + ", prev=" + bounds + ", new=" + rect);
        }
    }

    public final Point getDexScreenSizeLocked() {
        if (this.mAtm.mDexController.getDexModeLocked() == 2) {
            return this.mAtm.mDexController.getDexDisplaySizeLocked();
        }
        Point point = new Point();
        this.mAtm.mRootWindowContainer.getDefaultDisplay().mDisplay.getRealSize(point);
        return point;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002a, code lost:
    
        if (r4.width() <= r4.height()) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005e, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003d, code lost:
    
        if (r4.x > r4.y) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0048, code lost:
    
        if (r4.width() > r4.height()) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005a, code lost:
    
        if (r4.width() > r4.height()) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getOrientationFromTaskBounds(Task task) {
        if (!task.isDexCompatEnabled()) {
            return 0;
        }
        Rect requestedOverrideBounds = task.getRequestedOverrideBounds();
        int defaultOrientation = DexCompatBoundsProvider.getDefaultOrientation();
        int i = task.mDexCompatUiMode;
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return defaultOrientation;
                }
                if (!requestedOverrideBounds.isEmpty()) {
                }
                return 2;
            }
            if (requestedOverrideBounds.isEmpty()) {
                Point point = task.mDexCompatCustomSize;
                if (point == null) {
                    return defaultOrientation;
                }
            }
        } else if (requestedOverrideBounds.isEmpty()) {
            return defaultOrientation;
        }
    }

    public static int convertToConfigurationOrientation(int i) {
        if (ActivityInfo.isFixedOrientationPortrait(i)) {
            return 1;
        }
        return ActivityInfo.isFixedOrientationLandscape(i) ? 2 : 0;
    }

    public void startActivityForDexRestart(Task task) {
        ActivityRecord rootActivity = task.getRootActivity();
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
        makeBasic.setForceLaunchWindowingMode(task.getWindowingMode());
        Request request = new Request();
        WindowProcessController windowProcessController = rootActivity.app;
        if (windowProcessController != null && isSystemUiProcessName(windowProcessController.mName)) {
            Slog.w("DexCompatController", "startActivityForDexRestart: cannot kill systemui process, root=" + rootActivity + ", task=" + task);
        } else {
            request.wpc = rootActivity.app;
        }
        request.intent = intent;
        request.callingUid = task.mCallingUid;
        request.callingPackage = task.mCallingPackage;
        request.callingFeatureId = task.mCallingFeatureId;
        request.realCallingPid = Binder.getCallingPid();
        request.realCallingUid = Binder.getCallingUid();
        request.activityOptions = new SafeActivityOptions(makeBasic);
        request.reason = "startActivityForDexRestart";
        request.userId = task.mUserId;
        this.mAtm.deferWindowLayout();
        try {
            this.mAtm.mTaskSupervisor.removeTaskById(task.mTaskId, false, true, "startActivityForDexRestart", request.callingUid);
            scheduleStartActivityAsToggleFreeform(request, displayId);
        } finally {
            this.mAtm.continueWindowLayout();
        }
    }

    public void toggleFreeformForDexCompatApp(Task task) {
        ActivityRecord rootActivity = task.getRootActivity();
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
        if (task.mDexCompatUiMode != 3) {
            this.mTmpRect.setEmpty();
            makeBasic.setLaunchBounds(this.mTmpRect);
            makeBasic.setForceLaunchWindowingMode(1);
        } else {
            makeBasic.setForceLaunchWindowingMode(5);
        }
        Request request = new Request();
        WindowProcessController windowProcessController = rootActivity.app;
        if (windowProcessController != null && isSystemUiProcessName(windowProcessController.mName)) {
            Slog.w("DexCompatController", "startActivityForDexRestart: cannot kill systemui process, root=" + rootActivity + ", task=" + task);
        } else {
            request.wpc = rootActivity.app;
        }
        request.intent = intent;
        request.callingUid = task.mCallingUid;
        request.callingPackage = task.mCallingPackage;
        request.callingFeatureId = task.mCallingFeatureId;
        request.realCallingPid = Binder.getCallingPid();
        request.realCallingUid = Binder.getCallingUid();
        request.activityOptions = new SafeActivityOptions(makeBasic);
        request.reason = "toggleFreeformWindowingMode";
        request.userId = task.mUserId;
        this.mAtm.deferWindowLayout();
        try {
            this.mAtm.mTaskSupervisor.removeTaskById(task.mTaskId, false, true, "toggleFreeformWindowingMode", request.callingUid);
            scheduleStartActivityAsToggleFreeform(request, displayId);
        } finally {
            this.mAtm.continueWindowLayout();
        }
    }

    public final void scheduleStartActivityAsToggleFreeform(Request request, int i) {
        HandlerC2890H handlerC2890H = this.f1737mH;
        handlerC2890H.sendMessage(handlerC2890H.obtainMessage(0, i, 0, request));
    }

    public final void startActivityAsToggleFreeform(Request request, int i, boolean z) {
        WindowProcessController windowProcessController = request.wpc;
        if (windowProcessController != null) {
            this.mAtm.mDexController.killProcessIfNeeded(windowProcessController, i, request.reason, z);
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w("DexCompatController", "startActivityAsToggleFreeform: failed, cannot find display#" + i);
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                displayContent.prepareAppTransition(6);
                WindowManagerService.resetPriorityAfterLockedSection();
                this.mAtm.getActivityStartController().startActivityInPackage(request.callingUid, request.realCallingPid, request.realCallingUid, request.callingPackage, request.callingFeatureId, request.intent, null, null, null, 0, 0, request.activityOptions, request.userId, null, request.reason, false, null, BackgroundStartPrivileges.NONE);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public class Request {
        public SafeActivityOptions activityOptions;
        public String callingFeatureId;
        public String callingPackage;
        public Intent intent;
        public String reason;
        public int userId;
        public WindowProcessController wpc;
        public int callingUid = -1;
        public int realCallingPid = 0;
        public int realCallingUid = -1;

        public Request() {
            reset();
        }

        public void reset() {
            this.wpc = null;
            this.intent = null;
            this.callingUid = -1;
            this.callingPackage = null;
            this.realCallingPid = 0;
            this.realCallingUid = -1;
            this.activityOptions = null;
            this.reason = null;
            this.userId = 0;
        }
    }

    /* renamed from: com.android.server.wm.DexCompatController$H */
    public final class HandlerC2890H extends Handler {
        public HandlerC2890H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            DexCompatController.this.startActivityAsToggleFreeform((Request) message.obj, message.arg1, message.arg2 != 0);
        }
    }

    public static boolean isSystemUiProcessName(String str) {
        return "com.android.systemui".equals(str) || "system:ui".equals(str);
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[DexCompatController]");
        int dexModeLocked = this.mAtm.mDexController.getDexModeLocked();
        if (dexModeLocked != 0) {
            DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(dexModeLocked == 1 ? 0 : 2);
            if (displayContent == null) {
                return;
            }
            displayContent.getStableRect(this.mTmpRect);
            int min = Math.min(this.mTmpRect.width(), this.mTmpRect.height());
            int defaultWidth = DexCompatBoundsProvider.getDefaultWidth(dexModeLocked, min);
            int defaultHeight = DexCompatBoundsProvider.getDefaultHeight(dexModeLocked, min);
            printWriter.println(str + "DexCompat isDefaultSizeCompatible=" + DexCompatBoundsProvider.isDefaultSizeCompatible(min));
            printWriter.println(str + "DexCompat DefaultSize=(" + defaultWidth + "x" + defaultHeight + ")");
        }
        printWriter.println();
    }
}
