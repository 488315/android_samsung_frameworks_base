package com.android.server.wm;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.p005os.IInstalld;
import android.util.Slog;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.InsetsState;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Toast;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ToBooleanFunction;
import com.android.server.wm.Transition;
import com.google.android.collect.Sets;
import com.samsung.android.multiwindow.IFreeformCallback;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class FreeformController implements IController {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public final ActivityTaskManagerService mAtm;
    public boolean mBlockToAddForceHideFreeformTasks;
    public boolean mDeferMinimizeCallback;
    public WindowState mForceHideFreeformRequester;
    public WindowState mForceHideMinimizeRequester;
    public int mFreeformCaptionType;
    public final WindowManagerGlobalLock mGlobalLock;

    /* renamed from: mH */
    public HandlerC2922H f1741mH;
    public FreeformContainerServiceBinder mMinimizeContainerServiceBinder;
    public int mNewDexCaptionType;
    public FreeformContainerServiceBinder mSmartPopupViewServiceBinder;
    public WindowState mTmpForceHideFreeformRequester;
    public WindowState mTmpForceHideMinimizeRequester;
    public WindowState mTmpWindow;
    public final TransitionController mTransitionController;
    public final ArrayList mDeferredCallbacks = new ArrayList();
    public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    public final ArrayList mForceHiddenFreeformContainers = new ArrayList();
    public final ArrayList mForceHiddenFreeformTasks = new ArrayList();
    public final SparseArray mFreeformCornerRadius = new SparseArray();
    public int mMaxFreeformOverWrittenCnt = -1;
    public int mMaxDexFreeformOverWrittenCnt = -1;
    public final PointF mFreeformContainerPoint = new PointF(-1.0f, -1.0f);
    public final Rect mTmpRect = new Rect();

    public final boolean hasConfigChangesToResize(int i) {
        return (i & 128) == 0 && (134217728 & i) == 0 && (i & 7168) != 0;
    }

    public FreeformController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
        this.mTransitionController = activityTaskManagerService.getTransitionController();
        this.mGlobalLock = activityTaskManagerService.mGlobalLock;
        this.mMinimizeContainerServiceBinder = new MinimizeContainerServiceBinder(activityTaskManagerService);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            this.mSmartPopupViewServiceBinder = new SmartPopupViewServiceBinder(activityTaskManagerService);
        }
    }

    @Override // com.android.server.wm.IController
    public void initialize() {
        this.f1741mH = new HandlerC2922H(this.mAtm.f1734mH.getLooper());
    }

    public void onConfigurationChanged(DisplayContent displayContent) {
        Resources resources = displayContent.getDisplayPolicy().getContext().getResources();
        this.mFreeformCornerRadius.put(displayContent.getDisplayId(), Integer.valueOf(resources.getDimensionPixelSize(R.dimen.harmful_app_message_padding_right)));
    }

    public void bindFreeformContainerService(String str) {
        scheduleBindMinimizeContainerService(str);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            scheduleBindSmartPopupViewService(str);
        }
    }

    public void unbindFreeformContainerService(String str) {
        scheduleUnbindMinimizeContainerService(str);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            scheduleUnbindSmartPopupViewService(str);
        }
    }

    public void scheduleBindMinimizeContainerService(String str) {
        Message obtainMessage = this.f1741mH.obtainMessage(101);
        obtainMessage.obj = str;
        this.f1741mH.sendMessage(obtainMessage);
    }

    public void scheduleUnbindMinimizeContainerService(String str) {
        Message obtainMessage = this.f1741mH.obtainMessage(102);
        obtainMessage.obj = str;
        this.f1741mH.sendMessage(obtainMessage);
    }

    public void scheduleBindSmartPopupViewService(String str) {
        Message obtainMessage = this.f1741mH.obtainMessage(103);
        obtainMessage.obj = str;
        this.f1741mH.sendMessage(obtainMessage);
    }

    public void scheduleUnbindSmartPopupViewService(String str) {
        Message obtainMessage = this.f1741mH.obtainMessage(104);
        obtainMessage.obj = str;
        this.f1741mH.sendMessage(obtainMessage);
    }

    public void registerFreeformCallback(IFreeformCallback iFreeformCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.register(iFreeformCallback);
        }
    }

    public void deferMinimizeStateChangedCallbacks() {
        this.mDeferMinimizeCallback = true;
    }

    public void continueMinimizeStateChangedCallbacks() {
        if (this.mDeferMinimizeCallback) {
            this.mDeferMinimizeCallback = false;
            Iterator it = this.mDeferredCallbacks.iterator();
            while (it.hasNext()) {
                Message message = (Message) it.next();
                this.f1741mH.sendMessage(message);
                if (CoreRune.SAFE_DEBUG) {
                    Slog.d("FreeformController", "continueMinimizeStateChangedCallbacks: " + message);
                }
            }
            this.mDeferredCallbacks.clear();
        }
    }

    public void unregisterFreeformCallback(IFreeformCallback iFreeformCallback) {
        synchronized (this.mCallbacks) {
            this.mCallbacks.unregister(iFreeformCallback);
        }
    }

    public void notifyFreeformMinimizeStateChanged(Task task) {
        notifyFreeformMinimizeStateChanged(task, -1, -1, true);
    }

    public void notifyFreeformMinimizeStateChanged(Task task, int i, int i2, boolean z) {
        if (task.isDesktopModeEnabled()) {
            return;
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = task.realActivity;
        obtain.argi1 = task.mTaskId;
        obtain.argi2 = task.mUserId;
        obtain.argi3 = i;
        obtain.argi4 = i2;
        obtain.argi5 = z ? 1 : 0;
        Message obtainMessage = this.f1741mH.obtainMessage(task.mIsMinimized ? 201 : 202, obtain);
        if (this.mDeferMinimizeCallback) {
            this.mDeferredCallbacks.add(0, obtainMessage);
        } else {
            this.f1741mH.sendMessage(obtainMessage);
        }
    }

    public void notifyFreeformMinimizeAnimationEnd(int i, PointF pointF) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i);
                if (anyTaskForId != null) {
                    reportFreeformContainerPoint(pointF);
                    notifyFreeformMinimizeAnimationEnd(anyTaskForId);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void notifyFreeformMinimizeAnimationEnd(Task task) {
        if (task.isDesktopModeEnabled()) {
            return;
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.argi1 = task.mTaskId;
        if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            task.updateMinimizeChangeInfo(0);
        }
        HandlerC2922H handlerC2922H = this.f1741mH;
        handlerC2922H.sendMessage(handlerC2922H.obtainMessage(203, obtain));
    }

    public void reportFreeformContainerPoint(PointF pointF) {
        this.mFreeformContainerPoint.set(pointF);
    }

    public PointF getFreeformContainerPoint() {
        return this.mFreeformContainerPoint;
    }

    public ParceledListSlice getMinimizedFreeformTasksForCurrentUser() {
        ArrayList arrayList = new ArrayList();
        WindowManagerGlobalLock globalLock = this.mAtm.getGlobalLock();
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (globalLock) {
            try {
                if (this.mAtm.mRootWindowContainer.getDefaultDisplay() != null) {
                    for (Task task : this.mAtm.mRootWindowContainer.getDefaultDisplay().getRootTasks(5, 1)) {
                        if (task.isMinimized() && this.mAtm.mAmInternal.isCurrentProfile(task.mUserId)) {
                            arrayList.add(task.getTaskInfo());
                        }
                    }
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return new ParceledListSlice(arrayList);
    }

    public void setFreeformWindowingModeByCornerGestureLocked(int i, int i2, Rect rect) {
        this.mAtm.deferWindowLayout();
        try {
            Task anyTaskForId = this.mAtm.mRootWindowContainer.anyTaskForId(i, 0);
            if (anyTaskForId == null) {
                Slog.w("FreeformController", "setFreeformWindowingModeByCornerGestureLocked: taskId=" + i + " not found");
            } else if (this.mAtm.getLockTaskController().isTaskLocked(anyTaskForId)) {
                this.mAtm.getLockTaskController().showLockTaskToast();
                Slog.w("FreeformController", "setFreeformWindowingModeByCornerGestureLocked: task is locked");
            } else {
                DisplayContent displayContent = anyTaskForId.getDisplayContent();
                if (displayContent == null) {
                    Slog.w("FreeformController", "setFreeformWindowingModeByCornerGestureLocked: cannot find display");
                } else {
                    if (anyTaskForId.inSplitScreenWindowingMode()) {
                        ActivityRecord rootActivity = anyTaskForId.getRootActivity();
                        if (rootActivity == null) {
                            Slog.w("FreeformController", "setFreeformWindowingModeByCornerGestureLocked: root activity not found");
                        } else {
                            Task task = rootActivity.getTask();
                            if (task != null) {
                                DisplayContent displayContent2 = task.getDisplayContent();
                                if (displayContent2 != null && displayContent2.getDisplayId() == 0) {
                                    task.reparent(displayContent2.getDefaultTaskDisplayArea(), Integer.MAX_VALUE);
                                    task.setWindowingMode(5);
                                    task.setBounds(rect);
                                }
                            }
                        }
                    } else {
                        if (anyTaskForId.getWindowingMode() != 5) {
                            anyTaskForId.mLastNonFullscreenBounds = new Rect(rect);
                            anyTaskForId.setWindowingMode(5);
                            notifyActivityForcedResizableIfNeeded(anyTaskForId);
                            if (rect == null || rect.isEmpty()) {
                                rect = anyTaskForId.getLaunchBounds();
                            }
                        }
                        int rotation = anyTaskForId.getWindowConfiguration().getRotation();
                        if (i2 != rotation) {
                            if (rect != null) {
                                displayContent.rotateBounds(i2, rotation, rect);
                            } else {
                                Slog.w("FreeformController", "setFreeformWindowingModeByCornerGestureLocked: bounds is null");
                            }
                        }
                        anyTaskForId.resize(rect, 2, false);
                        this.mAtm.mRootWindowContainer.ensureActivitiesVisible(null, 0, true);
                        this.mAtm.mRootWindowContainer.resumeFocusedTasksTopActivities();
                    }
                }
            }
        } finally {
            this.mAtm.continueWindowLayout();
        }
    }

    /* renamed from: com.android.server.wm.FreeformController$H */
    public final class HandlerC2922H extends Handler {
        public HandlerC2922H(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            boolean z = true;
            if (i != 1) {
                switch (i) {
                    case 101:
                        FreeformController.this.mMinimizeContainerServiceBinder.bindServiceIfNeeded((String) message.obj);
                        return;
                    case 102:
                        FreeformController.this.mMinimizeContainerServiceBinder.unbindServiceIfNeeded((String) message.obj);
                        return;
                    case 103:
                        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                            FreeformController.this.mSmartPopupViewServiceBinder.bindServiceIfNeeded((String) message.obj);
                            return;
                        }
                        return;
                    case 104:
                        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
                            FreeformController.this.mSmartPopupViewServiceBinder.unbindServiceIfNeeded((String) message.obj);
                            return;
                        }
                        return;
                    default:
                        switch (i) {
                            case 201:
                                synchronized (FreeformController.this.mCallbacks) {
                                    SomeArgs someArgs = (SomeArgs) message.obj;
                                    ComponentName componentName = (ComponentName) someArgs.arg1;
                                    int i3 = someArgs.argi1;
                                    int i4 = someArgs.argi2;
                                    int i5 = someArgs.argi3;
                                    int i6 = someArgs.argi4;
                                    if (someArgs.argi5 != 1) {
                                        z = false;
                                    }
                                    int beginBroadcast = FreeformController.this.mCallbacks.beginBroadcast();
                                    while (i2 < beginBroadcast) {
                                        int i7 = i6;
                                        try {
                                            FreeformController.this.mCallbacks.getBroadcastItem(i2).onMinimized(componentName, i3, i4, i5, i6, z);
                                        } catch (RemoteException unused) {
                                            Slog.e("FreeformController", "onMinimized, RemoteException occurred");
                                        }
                                        i2++;
                                        i6 = i7;
                                    }
                                    FreeformController.this.mCallbacks.finishBroadcast();
                                }
                                return;
                            case 202:
                                synchronized (FreeformController.this.mCallbacks) {
                                    int i8 = ((SomeArgs) message.obj).argi1;
                                    int beginBroadcast2 = FreeformController.this.mCallbacks.beginBroadcast();
                                    while (i2 < beginBroadcast2) {
                                        try {
                                            FreeformController.this.mCallbacks.getBroadcastItem(i2).onUnminimized(i8);
                                        } catch (RemoteException unused2) {
                                            Slog.e("FreeformController", "onRestored, RemoteException occurred");
                                        }
                                        i2++;
                                    }
                                    FreeformController.this.mCallbacks.finishBroadcast();
                                }
                                return;
                            case 203:
                                synchronized (FreeformController.this.mCallbacks) {
                                    int i9 = ((SomeArgs) message.obj).argi1;
                                    int beginBroadcast3 = FreeformController.this.mCallbacks.beginBroadcast();
                                    while (i2 < beginBroadcast3) {
                                        try {
                                            FreeformController.this.mCallbacks.getBroadcastItem(i2).onMinimizeAnimationEnd(i9);
                                        } catch (RemoteException unused3) {
                                            Slog.e("FreeformController", "onMinimizeAnimationEnd, RemoteException occurred");
                                        }
                                        i2++;
                                    }
                                    FreeformController.this.mCallbacks.finishBroadcast();
                                }
                                return;
                            case 204:
                                synchronized (FreeformController.this.mCallbacks) {
                                    SomeArgs someArgs2 = (SomeArgs) message.obj;
                                    int i10 = someArgs2.argi1;
                                    Point point = (Point) someArgs2.arg1;
                                    int beginBroadcast4 = FreeformController.this.mCallbacks.beginBroadcast();
                                    while (i2 < beginBroadcast4) {
                                        try {
                                            FreeformController.this.mCallbacks.getBroadcastItem(i2).onTaskMoveStarted(i10, point);
                                        } catch (RemoteException unused4) {
                                            Slog.e("FreeformController", "onTaskMoveStarted, RemoteException occurred");
                                        }
                                        i2++;
                                    }
                                    FreeformController.this.mCallbacks.finishBroadcast();
                                }
                                return;
                            case 205:
                                synchronized (FreeformController.this.mCallbacks) {
                                    SomeArgs someArgs3 = (SomeArgs) message.obj;
                                    int i11 = someArgs3.argi1;
                                    IRemoteCallback iRemoteCallback = (IRemoteCallback) someArgs3.arg1;
                                    int beginBroadcast5 = FreeformController.this.mCallbacks.beginBroadcast();
                                    while (i2 < beginBroadcast5) {
                                        try {
                                            FreeformController.this.mCallbacks.getBroadcastItem(i2).onTaskMoveEnded(i11, iRemoteCallback);
                                        } catch (RemoteException unused5) {
                                            Slog.e("FreeformController", "onTaskMoveEnded, RemoteException occurred");
                                        }
                                        i2++;
                                    }
                                    FreeformController.this.mCallbacks.finishBroadcast();
                                }
                                return;
                            default:
                                return;
                        }
                }
            }
            Toast.makeText(new ContextThemeWrapper(FreeformController.this.mAtm.mContext, R.style.Theme.DeviceDefault.Light), FreeformController.this.mAtm.mContext.getResources().getString(R.string.httpErrorRedirectLoop), 0).show();
        }
    }

    public void showNotSupportMultiWindowToast(ActivityRecord activityRecord) {
        if (CoreRune.SAFE_DEBUG) {
            Slog.i("FreeformController", "showNotSupportMultiWindowToast: r=" + activityRecord);
        }
        this.f1741mH.removeMessages(1);
        this.f1741mH.sendEmptyMessage(1);
    }

    public void notifyActivityForcedResizableIfNeeded(Task task) {
        ActivityRecord topNonFinishingActivity = task.getTopNonFinishingActivity();
        if (topNonFinishingActivity == null || topNonFinishingActivity.noDisplay || !topNonFinishingActivity.canForceResizeNonResizable(task.getWindowingMode())) {
            return;
        }
        this.mAtm.getTaskChangeNotificationController().notifyActivityForcedResizable(task.mTaskId, 3, topNonFinishingActivity.info.applicationInfo.packageName);
    }

    public final int getMaxVisibleFreeformCntLocked(boolean z, int i) {
        boolean z2 = i == 3;
        if (z) {
            if (CoreRune.MT_NEW_DEX_LIMIT_RUNNING_APPS && z2) {
                return 15;
            }
            int i2 = this.mMaxDexFreeformOverWrittenCnt;
            if (i2 > 0) {
                return i2;
            }
            return 20;
        }
        int i3 = this.mMaxFreeformOverWrittenCnt;
        if (i3 > 0) {
            return i3;
        }
        return 5;
    }

    public void minimizeExcessiveAllVisibleFreeformLocked() {
        int childCount = this.mAtm.mRootWindowContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DisplayContent displayContent = (DisplayContent) this.mAtm.mRootWindowContainer.getChildAt(i);
            if (displayContent == null) {
                Slog.w("FreeformController", "minimizeAllFreeformLocked: activityDisplay is null.");
                return;
            }
            for (int childCount2 = displayContent.getChildCount() - 1; childCount2 >= 0; childCount2--) {
                TaskDisplayArea asTaskDisplayArea = ((DisplayArea) displayContent.getChildAt(childCount2)).asTaskDisplayArea();
                if (asTaskDisplayArea != null) {
                    minimizeExcessiveVisibleFreeformLocked(asTaskDisplayArea);
                }
            }
        }
    }

    public void minimizeExcessiveVisibleFreeformLocked(TaskDisplayArea taskDisplayArea) {
        int i = 0;
        int rootTaskIndex = taskDisplayArea.getRootTaskIndex(taskDisplayArea.getRootTask(0, 2));
        boolean isDesktopModeEnabled = taskDisplayArea.mDisplayContent.getConfiguration().isDesktopModeEnabled();
        int maxVisibleFreeformCntLocked = getMaxVisibleFreeformCntLocked(isDesktopModeEnabled, taskDisplayArea.mDisplayContent.getConfiguration().dexMode);
        for (int rootTaskCount = taskDisplayArea.getRootTaskCount() - 1; rootTaskCount > rootTaskIndex; rootTaskCount--) {
            Task asTask = taskDisplayArea.getChildAt(rootTaskCount).asTask();
            if (asTask != null && !asTask.mCreatedByOrganizer && isVisibleFreeformRootTaskLocked(asTask) && (i = i + 1) > maxVisibleFreeformCntLocked) {
                this.mAtm.mMultiTaskingController.minimizeTaskLocked(asTask, true);
            }
        }
        if (i > maxVisibleFreeformCntLocked) {
            makeMaxCountToastLocked(taskDisplayArea.getDisplayContent().getDisplay(), isDesktopModeEnabled, maxVisibleFreeformCntLocked);
        }
    }

    public final boolean isVisibleFreeformRootTaskLocked(Task task) {
        return task.shouldBeVisible(null) && task.getWindowingMode() == 5;
    }

    public final void makeMaxCountToastLocked(Display display, boolean z, int i) {
        final String string;
        final Context createDisplayContext = new ContextThemeWrapper(this.mAtm.mContext, R.style.Theme.DeviceDefault.Light).createDisplayContext(display);
        if (z) {
            string = this.mAtm.mContext.getResources().getQuantityString(R.plurals.duration_days_shortest_future, i, Integer.valueOf(i));
        } else {
            string = this.mAtm.mContext.getResources().getString(R.string.lockscreen_transport_pause_description, Integer.valueOf(i));
        }
        this.f1741mH.post(new Runnable() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FreeformController.lambda$makeMaxCountToastLocked$0(createDisplayContext, string);
            }
        });
    }

    public static /* synthetic */ void lambda$makeMaxCountToastLocked$0(Context context, String str) {
        Toast.makeText(context, str, 0).show();
    }

    public void setMaxVisibleFreeformCountForDex(int i, int i2) {
        if (i <= 0) {
            i = -1;
        }
        this.mMaxFreeformOverWrittenCnt = i;
        if (i2 <= 0) {
            i2 = -1;
        }
        this.mMaxDexFreeformOverWrittenCnt = i2;
        if (DEBUG) {
            Slog.d("FreeformController", "setMaxVisibleFreeformCount: mMaxFreeformOverWrittenCnt=" + this.mMaxFreeformOverWrittenCnt + ", mMaxDexFreeformOverWrittenCnt=" + this.mMaxDexFreeformOverWrittenCnt + ", caller=" + Debug.getCallers(5));
        }
        minimizeExcessiveAllVisibleFreeformLocked();
    }

    public boolean hasVisibleFreeform(DisplayContent displayContent) {
        Task topRootTaskInWindowingMode = displayContent.getDefaultTaskDisplayArea().getTopRootTaskInWindowingMode(5);
        return topRootTaskInWindowingMode != null && topRootTaskInWindowingMode.shouldBeVisible(null);
    }

    public static boolean useAlwaysOnTopFreeform(int i, DisplayContent displayContent) {
        return (displayContent == null || displayContent.isDesktopModeEnabled() || i != 5) ? false : true;
    }

    public void performDisplayOverrideConfigUpdate(final int i, int i2, final float f, final float f2, final Rect rect, final Rect rect2) {
        final DisplayContent displayContent = this.mAtm.mRootWindowContainer.getDisplayContent(i2);
        if (displayContent == null) {
            return;
        }
        final boolean z = (i & IInstalld.FLAG_USE_QUOTA) != 0;
        final boolean hasConfigChangesToResize = hasConfigChangesToResize(i);
        displayContent.forAllTasks(new Consumer() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FreeformController.this.lambda$performDisplayOverrideConfigUpdate$1(z, i, displayContent, rect, rect2, hasConfigChangesToResize, f, f2, (Task) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performDisplayOverrideConfigUpdate$1(boolean z, int i, DisplayContent displayContent, Rect rect, Rect rect2, boolean z2, float f, float f2, Task task) {
        if (z) {
            task.updateMinMaxSizeIfNeeded();
        }
        if (task.isRootTask() && task.inFreeformWindowingMode()) {
            if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && canApplyScreenRatioResize(task, i) && (!CoreRune.MT_SUPPORT_SIZE_COMPAT || !displayContent.isNewDexMode() || SizeCompatPolicyManager.get().getCompatPolicy(task) == null)) {
                MultiWindowUtils.adjustBoundsForScreenRatio(rect, rect2, task.mLastNonFullscreenBounds, this.mTmpRect);
                task.resize(this.mTmpRect, 0, false);
            }
            if (z2) {
                task.resize(f, f2);
            }
        }
    }

    public final boolean canApplyScreenRatioResize(Task task, int i) {
        return ((i & 128) == 0 || !task.inFreeformWindowingMode() || task.getDisplayContent() == null || !task.getDisplayContent().isDefaultDisplay || task.getDisplayContent().isDexMode() || this.mAtm.mWindowManager.isFolded()) ? false : true;
    }

    public void beginPostLayoutPolicyLw() {
        DisplayContent defaultDisplayContentLocked = this.mAtm.mWindowManager.getDefaultDisplayContentLocked();
        if (defaultDisplayContentLocked.isDexMode()) {
            return;
        }
        computeForceHideRequester(defaultDisplayContentLocked);
        if (this.mForceHideFreeformRequester != this.mTmpForceHideFreeformRequester) {
            Slog.d("FreeformController", "beginPostLayoutPolicyLw: forceHideRequester changed, old=" + this.mForceHideFreeformRequester + ", new=" + this.mTmpForceHideFreeformRequester);
            this.mForceHideFreeformRequester = this.mTmpForceHideFreeformRequester;
            setBlockToAddForceHideFreeformTasks(false);
        }
        if (this.mForceHideMinimizeRequester != this.mTmpForceHideMinimizeRequester) {
            Slog.d("FreeformController", "beginPostLayoutPolicyLw: forceHide minimize Requester changed, old=" + this.mForceHideMinimizeRequester + ", new=" + this.mTmpForceHideMinimizeRequester);
            this.mForceHideMinimizeRequester = this.mTmpForceHideMinimizeRequester;
        }
    }

    public final void computeForceHideRequester(DisplayContent displayContent) {
        this.mTmpForceHideFreeformRequester = null;
        this.mTmpForceHideMinimizeRequester = null;
        displayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FreeformController.this.lambda$computeForceHideRequester$2((WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$computeForceHideRequester$2(WindowState windowState) {
        ActivityRecord activityRecord;
        ActivityRecord activityRecord2;
        if (!(windowState.isVisible() || (windowState.isVisibleRequested() && (activityRecord2 = windowState.mActivityRecord) != null && activityRecord2.isVisibleRequested())) || windowState.inFreeformWindowingMode()) {
            return;
        }
        if (windowState.getTask() == null || windowState.getTask().shouldBeVisible(null)) {
            int i = windowState.mAttrs.samsungFlags;
            if ((this.mTmpForceHideFreeformRequester == null && (67108864 & i) != 0) || ((activityRecord = windowState.mActivityRecord) != null && activityRecord.mRequestFreeformForceHiding)) {
                this.mTmpForceHideFreeformRequester = windowState;
            }
            if (this.mTmpForceHideMinimizeRequester != null || (i & 33554432) == 0) {
                return;
            }
            this.mTmpForceHideMinimizeRequester = windowState;
        }
    }

    public void finishPostLayoutPolicyLw() {
        Task task;
        TaskDisplayArea defaultTaskDisplayArea = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
        Task focusedRootTask = defaultTaskDisplayArea != null ? defaultTaskDisplayArea.getFocusedRootTask() : null;
        if (focusedRootTask != null && focusedRootTask.isFreeformForceHidden() && (task = defaultTaskDisplayArea.getTask(new Predicate() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$finishPostLayoutPolicyLw$3;
                lambda$finishPostLayoutPolicyLw$3 = FreeformController.lambda$finishPostLayoutPolicyLw$3((Task) obj);
                return lambda$finishPostLayoutPolicyLw$3;
            }
        }, true)) != null) {
            this.mAtm.setFocusedTask(task.mTaskId, null);
        }
        setBlockToAddForceHideFreeformTasks(isForceHideRequesterHomeOrRecents());
    }

    public static /* synthetic */ boolean lambda$finishPostLayoutPolicyLw$3(Task task) {
        return task.isLeafTask() && task.isTopActivityFocusable() && !task.isFreeformForceHidden();
    }

    public final boolean isForceHideRequesterHomeOrRecents() {
        WindowState windowState = this.mForceHideFreeformRequester;
        return windowState != null && (windowState.isActivityTypeHomeOrRecents() || this.mForceHideFreeformRequester.getWindowType() == 2632);
    }

    public final void setBlockToAddForceHideFreeformTasks(boolean z) {
        if (this.mBlockToAddForceHideFreeformTasks != z) {
            this.mBlockToAddForceHideFreeformTasks = z;
            Slog.d("FreeformController", "setBlockToAddForceHideFreeformTasks: blockToAddForceHide=" + this.mBlockToAddForceHideFreeformTasks + ", Caller=" + Debug.getCaller());
        }
    }

    public final boolean shouldApplyForceHidePolicyLocked(WindowState windowState, boolean z) {
        WindowState windowState2;
        Task task = windowState.getTask();
        DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null || !displayContent.isDefaultDisplay || displayContent.isDexMode()) {
            return false;
        }
        if (z && this.mForceHideMinimizeRequester != null) {
            return true;
        }
        if (this.mBlockToAddForceHideFreeformTasks && task != null && !this.mForceHiddenFreeformTasks.contains(task)) {
            return false;
        }
        if ((CoreRune.MW_SHELL_CHANGE_TRANSITION && task != null && this.mAtm.mChangeTransitController.isInChangeTransition(task)) || (windowState2 = this.mForceHideFreeformRequester) == null || windowState2 == windowState) {
            return false;
        }
        ActivityRecord activityRecord = windowState2.mActivityRecord;
        return activityRecord == null || activityRecord != windowState.mActivityRecord;
    }

    public boolean applyForceHidePolicyIfNeededLocked(WindowState windowState) {
        boolean z = CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER && windowState.mAttrs.type == 2604;
        Task task = windowState.getTask();
        if (((task != null && task.inFreeformWindowingMode()) || z) && shouldApplyForceHidePolicyLocked(windowState, z)) {
            if (z && !this.mForceHiddenFreeformContainers.contains(windowState)) {
                this.mForceHiddenFreeformContainers.add(windowState);
                windowState.hide(true, true);
            }
            if (task != null && canBeForceHiddenTask(task) && !this.mForceHiddenFreeformTasks.contains(task)) {
                this.mAtm.mWindowManager.mTaskSnapshotController.takeSnapshotByForce(task);
                this.mForceHiddenFreeformTasks.add(task);
                requestForceHideTransition(task, 1);
                task.dispatchTaskInfoChangedIfNeeded(true);
                Slog.d("FreeformController", "applyForceHidePolicyIfNeededLocked: " + task);
            }
            return true;
        }
        lambda$releaseForceHideTaskLocked$4(windowState);
        return false;
    }

    public final boolean canBeForceHiddenTask(Task task) {
        if (!isForceHideRequesterHomeOrRecents() || task.getTopResumedActivity() != null) {
            return task.mSurfaceControl != null && task.shouldBeVisible(null);
        }
        if (CoreRune.SAFE_DEBUG) {
            Slog.d("FreeformController", "canBeForceHiddenTask: false, reason=launching_from_recents");
        }
        return false;
    }

    public void releaseForceHideTaskLocked(Task task) {
        String str;
        if (this.mForceHiddenFreeformTasks.contains(task)) {
            StringBuilder sb = new StringBuilder();
            sb.append("releaseForceHideTaskLocked: ");
            sb.append(task);
            if (CoreRune.SAFE_DEBUG) {
                str = ", Callers=" + Debug.getCallers(3);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.d("FreeformController", sb.toString());
            task.forAllWindows(new Consumer() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    FreeformController.this.lambda$releaseForceHideTaskLocked$4((WindowState) obj);
                }
            }, true);
            this.mAtm.mWindowManager.requestTraversal();
        }
    }

    /* renamed from: releaseForceHidePolicyIfNeededLocked, reason: merged with bridge method [inline-methods] */
    public final void lambda$releaseForceHideTaskLocked$4(WindowState windowState) {
        Task task = windowState.getTask();
        if (this.mForceHiddenFreeformContainers.contains(windowState)) {
            this.mForceHiddenFreeformContainers.remove(windowState);
            windowState.show(false, true);
        }
        if (!this.mForceHiddenFreeformTasks.contains(task) || task == null) {
            return;
        }
        this.mForceHiddenFreeformTasks.remove(task);
        requestForceHideTransition(task, 2);
        task.dispatchTaskInfoChangedIfNeeded(true);
        if (task.isMinimized()) {
            this.mAtm.mWindowManager.mTaskSnapshotController.addSkipClosingAppSnapshotTasks(Sets.newArraySet(new Task[]{task}));
        }
        Slog.d("FreeformController", "releaseForceHidePolicyIfNeededLocked: " + task);
    }

    public final void requestForceHideTransition(Task task, int i) {
        Transition createTransition = !this.mTransitionController.isCollecting() ? this.mTransitionController.createTransition(6) : null;
        Transition collectingTransition = this.mTransitionController.getCollectingTransition();
        boolean shouldAddToSyncSet = shouldAddToSyncSet(task, i);
        Slog.d("FreeformController", "requestForceHideTransition: tid #" + task.mTaskId + ", type=" + MultiWindowManager.forceHidingTransitToString(i) + ", forceSync=" + shouldAddToSyncSet + ", newTransit=" + createTransition);
        if (collectingTransition != null) {
            collectingTransition.collect(task, shouldAddToSyncSet);
            this.mTransitionController.setForceHidingTransit(task, i);
        }
        if (createTransition != null) {
            this.mTransitionController.requestStartTransition(createTransition, task, null, null);
            createTransition.setReady(task, true);
        }
    }

    public final boolean shouldAddToSyncSet(Task task, int i) {
        Transition.ChangeInfo changeInfo = this.mTransitionController.isCollecting() ? (Transition.ChangeInfo) this.mTransitionController.getCollectingTransition().mChanges.get(task) : null;
        if (changeInfo == null) {
            return false;
        }
        Rect bounds = task.getBounds();
        Rect rect = changeInfo.mAbsoluteBounds;
        if (task.mSyncGroup == null && i == 2 && changeInfo.mVisible && task.isVisibleRequested()) {
            return (bounds.width() == rect.width() && bounds.height() == rect.height()) ? false : true;
        }
        return false;
    }

    public void onWindowRemovedLocked(WindowState windowState) {
        if (this.mForceHiddenFreeformContainers.contains(windowState)) {
            this.mForceHiddenFreeformContainers.remove(windowState);
        }
    }

    public boolean isForceHiddenTask(Task task) {
        return this.mForceHiddenFreeformTasks.contains(task);
    }

    public void updateFreeformBoundsForDisplayDeviceTypeChanged(Task task) {
        Rect mainDisplayBounds;
        Rect coverDisplayBounds;
        if (task == null || task.getRootTask() == null) {
            return;
        }
        Configuration globalConfiguration = this.mAtm.getGlobalConfiguration();
        Task rootTask = task.getRootTask();
        int lastMinimizedDisplayType = rootTask.getLastMinimizedDisplayType();
        int lastMinimizedRotation = rootTask.getLastMinimizedRotation();
        if (lastMinimizedDisplayType == -1 || lastMinimizedDisplayType == globalConfiguration.semDisplayDeviceType) {
            return;
        }
        DisplayContent defaultDisplay = this.mAtm.mRootWindowContainer.getDefaultDisplay();
        int rotation = globalConfiguration.windowConfiguration.getRotation();
        Rect bounds = defaultDisplay.getBounds();
        boolean z = bounds.width() <= bounds.height();
        boolean z2 = globalConfiguration.semDisplayDeviceType == 0;
        Rect bounds2 = task.getBounds();
        Rect rect = new Rect();
        if (z2) {
            mainDisplayBounds = this.mAtm.mMultiWindowFoldController.getCoverDisplayBounds(z);
        } else {
            mainDisplayBounds = this.mAtm.mMultiWindowFoldController.getMainDisplayBounds(z);
        }
        Rect rect2 = mainDisplayBounds;
        if (z2) {
            coverDisplayBounds = this.mAtm.mMultiWindowFoldController.getMainDisplayBounds(z);
        } else {
            coverDisplayBounds = this.mAtm.mMultiWindowFoldController.getCoverDisplayBounds(z);
        }
        Rect rect3 = coverDisplayBounds;
        if (lastMinimizedRotation != -1 && rotation != -1 && lastMinimizedRotation != rotation) {
            defaultDisplay.mDisplayContent.rotateBounds(lastMinimizedRotation, rotation, bounds2);
        }
        task.updateMinMaxSizeIfNeeded();
        calculateFreeformBoundsForLidStateChanged(task, rect2, rect3, bounds2, rect);
        if (rect.isEmpty()) {
            return;
        }
        task.resize(rect, 0, false);
    }

    public final void calculateFreeformBoundsForLidStateChanged(Task task, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        int i;
        if (rect3.isEmpty()) {
            return;
        }
        rect4.setEmpty();
        int i2 = 0;
        boolean z = rect2.width() < rect3.width();
        boolean z2 = rect2.height() < rect3.height();
        if (z || z2) {
            Rect rect5 = this.mTmpRect;
            this.mAtm.mWindowManager.getStableInsetsLocked(0, rect5);
            if (z) {
                rect4.left = rect2.left + rect5.left + 8;
                rect4.right = (rect2.right - rect5.right) - 8;
            } else {
                int width = (int) ((rect2.width() - rect3.width()) * (rect3.left / (rect.width() - rect3.width())));
                rect4.left = width;
                rect4.right = rect3.width() + width;
            }
            if (z2) {
                rect4.top = rect2.top + rect5.top + 8;
                rect4.bottom = (rect2.bottom - rect5.bottom) - 8;
            } else {
                int height = (int) ((rect2.height() - rect3.height()) * (rect3.top / (rect.height() - rect3.height())));
                rect4.top = height;
                rect4.bottom = rect3.height() + height;
            }
            int width2 = rect4.width();
            int height2 = rect4.height();
            task.adjustForMinimalTaskDimensions(rect4, new Rect(), task.getParent().getConfiguration());
            rect4.offset(width2 != rect4.width() ? 0 - ((rect4.width() - width2) / 2) : 0, height2 != rect4.height() ? 0 - ((rect4.height() - height2) / 2) : 0);
        } else {
            rect4.set(0, 0, rect3.width(), rect3.height());
            rect4.offset((int) ((rect2.width() - rect3.width()) * (rect3.left / (rect.width() - rect3.width()))), (int) ((rect2.height() - rect3.height()) * (rect3.top / (rect.height() - rect3.height()))));
        }
        int i3 = rect4.left;
        if (8 > i3) {
            i = 8 - i3;
        } else {
            int i4 = rect2.right;
            int i5 = i4 - 8;
            int i6 = rect4.right;
            i = i5 < i6 ? (i4 - 8) - i6 : 0;
        }
        int i7 = rect4.top;
        if (8 > i7) {
            i2 = 8 - i7;
        } else {
            int i8 = rect2.bottom;
            int i9 = i8 - 8;
            int i10 = rect4.bottom;
            if (i9 < i10) {
                i2 = (i8 - 8) - i10;
            }
        }
        rect4.offset(i, i2);
        if (task.isFreeformStashed()) {
            Rect stashedBounds = task.getStashedBounds();
            rect4.offsetTo(task.isLeftStash() ? stashedBounds.right - rect4.width() : stashedBounds.left, rect4.top);
        }
    }

    public static Animation loadWindowAnimationIfNeeded(Animation animation, WindowState windowState, int i) {
        DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null || !((i == 1 || i == 2) && windowState.inFreeformWindowingMode() && windowState.mAttrs.isFullscreen() && windowState.mAttrs.type == 2 && WindowAnimationSpec.findTranslateAnimation(animation) != null)) {
            return null;
        }
        Slog.d("FreeformController", "loadWindowAnimationIfNeeded: " + windowState + ", transit=" + i);
        return displayContent.mAppTransition.mTransitionAnimation.loadDefaultAnimationRes(i == 1 ? R.anim.ft_avd_tooverflow_rectangle_path_1_animation : R.anim.ft_avd_tooverflow_rectangle_3_pivot_animation);
    }

    public void updateFreeformHeaderType(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFreeformCaptionType != i) {
                    this.mFreeformCaptionType = i;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public int getDecorCaptionHeight(Resources resources, WindowContainer windowContainer, boolean z, int i) {
        boolean z2 = CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && resources.getConfiguration().isNewDexMode();
        if (i == -1) {
            i = z2 ? this.mNewDexCaptionType : this.mFreeformCaptionType;
        }
        if (windowContainer.isDexMode()) {
            if (windowContainer.inFreeformWindowingMode() || z) {
                return resources.getDimensionPixelSize(R.dimen.toast_text_size);
            }
            return resources.getDimensionPixelSize(R.dimen.toast_width);
        }
        if (!windowContainer.isNewDexMode()) {
            if (i == 1) {
                return resources.getDimensionPixelSize(R.dimen.timepicker_time_label_size);
            }
            return resources.getDimensionPixelSize(R.dimen.toast_elevation);
        }
        if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && i == 0) {
            return resources.getDimensionPixelSize(R.dimen.toast_elevation);
        }
        if (windowContainer.inFreeformWindowingMode() || z) {
            return resources.getDimensionPixelSize(R.dimen.toast_y_offset);
        }
        return resources.getDimensionPixelSize(R.dimen.tooltip_corner_radius);
    }

    public void updateNewDexHeaderType(int i) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mNewDexCaptionType != i) {
                    this.mNewDexCaptionType = i;
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public int getFreeformThickness(Resources resources) {
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.tooltip_horizontal_padding);
        return dimensionPixelSize % 2 == 0 ? dimensionPixelSize : dimensionPixelSize + 1;
    }

    public void onDisplayRemovedLocked(int i) {
        this.mFreeformCornerRadius.remove(i);
    }

    public int getFreeformCornerRadiusLocked(int i) {
        return ((Integer) this.mFreeformCornerRadius.get(i, 0)).intValue();
    }

    public boolean canApplyDimsLocked(final WindowState windowState) {
        if (windowState.mAttrs.isFullscreen() || windowState.getTask() == null) {
            return true;
        }
        this.mTmpWindow = null;
        final Rect rect = new Rect();
        rect.set(windowState.getFrame());
        InsetsState insetsStateWithVisibilityOverride = windowState.getInsetsStateWithVisibilityOverride();
        int i = windowState.mAttrs.type;
        int windowingMode = windowState.getWindowingMode();
        WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        rect.inset(insetsStateWithVisibilityOverride.calculateVisibleInsets(rect, i, windowingMode, layoutParams.softInputMode, layoutParams.flags));
        windowState.getTask().forAllWindows(new ToBooleanFunction() { // from class: com.android.server.wm.FreeformController$$ExternalSyntheticLambda1
            public final boolean apply(Object obj) {
                boolean lambda$canApplyDimsLocked$6;
                lambda$canApplyDimsLocked$6 = FreeformController.this.lambda$canApplyDimsLocked$6(windowState, rect, (WindowState) obj);
                return lambda$canApplyDimsLocked$6;
            }
        }, false);
        return this.mTmpWindow != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$canApplyDimsLocked$6(WindowState windowState, Rect rect, WindowState windowState2) {
        ActivityRecord activityRecord = windowState2.mActivityRecord;
        if (activityRecord != windowState.mActivityRecord && activityRecord != null && (!activityRecord.mVisibleRequested || activityRecord.mIsExiting)) {
            return false;
        }
        if (windowState2 == windowState) {
            this.mTmpWindow = null;
            return true;
        }
        Rect rect2 = this.mTmpRect;
        rect2.set(windowState2.getFrame());
        InsetsState insetsStateWithVisibilityOverride = windowState.getInsetsStateWithVisibilityOverride();
        int i = windowState2.mAttrs.type;
        int windowingMode = windowState2.getWindowingMode();
        WindowManager.LayoutParams layoutParams = windowState2.mAttrs;
        rect.inset(insetsStateWithVisibilityOverride.calculateVisibleInsets(rect2, i, windowingMode, layoutParams.softInputMode, layoutParams.flags));
        if (!windowState2.mHasSurface || (!rect2.contains(rect) && (rect2.height() < rect.height() || rect2.width() < rect.width()))) {
            return false;
        }
        this.mTmpWindow = windowState2;
        return true;
    }

    @Override // com.android.server.wm.IController
    public void dumpLocked(PrintWriter printWriter, String str) {
        printWriter.println("[FreeformController]");
        if (!this.mForceHiddenFreeformContainers.isEmpty()) {
            printWriter.println(str + "mForceHiddenFreeformWindows=" + this.mForceHiddenFreeformContainers);
        }
        printWriter.println(str + "mMaxFreeformOverWrittenCnt=" + this.mMaxFreeformOverWrittenCnt);
        printWriter.println(str + "mMaxDexFreeformOverWrittenCnt=" + this.mMaxDexFreeformOverWrittenCnt);
        if (this.mForceHideFreeformRequester != null) {
            printWriter.println(str + "mForceHideFreeformRequester=" + this.mForceHideFreeformRequester);
        }
        if (this.mForceHideMinimizeRequester != null) {
            printWriter.println(str + "mForceHideMinimizeRequester=" + this.mForceHideMinimizeRequester);
        }
        if (!this.mForceHiddenFreeformTasks.isEmpty()) {
            printWriter.println(str + "mForceHiddenFreeformTasks=" + this.mForceHiddenFreeformTasks);
        }
        if (this.mBlockToAddForceHideFreeformTasks) {
            printWriter.println(str + "mBlockToAddForceHideFreeformTasks=true");
        }
        this.mMinimizeContainerServiceBinder.dumpLocked(printWriter);
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            this.mSmartPopupViewServiceBinder.dumpLocked(printWriter);
        }
        printWriter.println();
    }
}
