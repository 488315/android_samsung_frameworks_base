package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.DragResizeInputListener;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;

/* loaded from: classes3.dex */
public class CaptionWindowDecorViewModel implements WindowDecorViewModel, FocusTransitionListener {
    public final ShellExecutor mBgExecutor;
    public final Context mContext;
    public final DesktopConfig mDesktopConfig;
    public final DesktopState mDesktopState;
    public final DisplayController mDisplayController;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public final InputManager mInputManager;
    public final Choreographer mMainChoreographer;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public boolean mShouldPilferCaptionEvents;
    public final SyncTransactionQueue mSyncQueue;
    public TaskOperations mTaskOperations;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Transitions mTransitions;
    public final WindowDecorViewHostSupplier mWindowDecorViewHostSupplier;
    public final IWindowManager mWindowManager;
    public final Region mExclusionRegion = Region.obtain();
    public final SparseArray mWindowDecorByTaskId = new SparseArray();
    public final AnonymousClass1 mGestureExclusionListener = new AnonymousClass1();

    /* renamed from: com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel$1, reason: invalid class name */
    public class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        public AnonymousClass1() {
        }

        public final void onSystemGestureExclusionChanged(final int i, final Region region, Region region2) {
            if (CaptionWindowDecorViewModel.this.mContext.getDisplayId() != i) {
                return;
            }
            CaptionWindowDecorViewModel.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    CaptionWindowDecorViewModel.AnonymousClass1 anonymousClass1 = this.f$0;
                    Region region3 = region;
                    int i2 = i;
                    CaptionWindowDecorViewModel.this.mExclusionRegion.set(region3);
                    CaptionWindowDecorViewModel captionWindowDecorViewModel = CaptionWindowDecorViewModel.this;
                    Region region4 = captionWindowDecorViewModel.mExclusionRegion;
                    int size = captionWindowDecorViewModel.mWindowDecorByTaskId.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) captionWindowDecorViewModel.mWindowDecorByTaskId.valueAt(i3);
                        ActivityManager.RunningTaskInfo runningTaskInfo = captionWindowDecoration.mTaskInfo;
                        if (runningTaskInfo.displayId == i2) {
                            captionWindowDecoration.relayout(runningTaskInfo, captionWindowDecoration.mHasGlobalFocus, region4);
                        }
                    }
                }
            });
        }
    }

    public class CaptionTouchEventListener implements View.OnClickListener, View.OnTouchListener, DragDetector.MotionEventHandler {
        public final int mDisplayId;
        public final DragDetector mDragDetector;
        public int mDragPointerId;
        public final DragPositioningCallback mDragPositioningCallback;
        public boolean mIsDragging;
        public final int mTaskId;
        public final WindowContainerToken mTaskToken;

        public /* synthetic */ CaptionTouchEventListener(CaptionWindowDecorViewModel captionWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, FluidResizeTaskPositioner fluidResizeTaskPositioner) {
            this(runningTaskInfo, (DragPositioningCallback) fluidResizeTaskPositioner);
        }

        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        public final boolean handleMotionEvent(View view, MotionEvent motionEvent) {
            Rect rect;
            DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver;
            ActivityManager.RunningTaskInfo runningTaskInfo = CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
            if (runningTaskInfo.getWindowingMode() == 1) {
                return false;
            }
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                this.mDragPointerId = motionEvent.getPointerId(0);
                this.mDragPositioningCallback.onDragPositioningStart(0, motionEvent.getRawX(0), motionEvent.getRawY(0), motionEvent.getDisplayId());
                this.mIsDragging = false;
                return false;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (motionEvent.findPointerIndex(this.mDragPointerId) == -1) {
                        this.mDragPointerId = motionEvent.getPointerId(0);
                    }
                    DragResizeInputListener dragResizeInputListener = ((CaptionWindowDecoration) CaptionWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)).mDragResizeListener;
                    if (dragResizeInputListener != null && (taskResizeInputEventReceiver = dragResizeInputListener.mInputEventReceiver) != null && taskResizeInputEventReceiver.mShouldHandleEvents) {
                        return true;
                    }
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mDragPointerId);
                    this.mDragPositioningCallback.onDragPositioningMove(motionEvent.getRawX(iFindPointerIndex), motionEvent.getRawY(iFindPointerIndex), motionEvent.getDisplayId());
                    this.mIsDragging = true;
                    return true;
                }
                if (actionMasked != 3) {
                    return true;
                }
            }
            if (motionEvent.findPointerIndex(this.mDragPointerId) == -1) {
                this.mDragPointerId = motionEvent.getPointerId(0);
            }
            int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mDragPointerId);
            Rect rectOnDragPositioningEnd = this.mDragPositioningCallback.onDragPositioningEnd(motionEvent.getRawX(iFindPointerIndex2), motionEvent.getRawY(iFindPointerIndex2), motionEvent.getDisplayId());
            CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) CaptionWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            Context displayContext = captionWindowDecoration.mDisplayController.getDisplayContext(captionWindowDecoration.mTaskInfo.displayId);
            if (displayContext == null) {
                rect = new Rect();
            } else {
                int iLoadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(captionWindowDecoration.mContext.getResources(), R.dimen.caption_left_buttons_width);
                int iLoadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(captionWindowDecoration.mContext.getResources(), displayContext.getResources().getConfiguration().smallestScreenWidthDp >= 600 ? R.dimen.freeform_required_visible_empty_space_in_header : R.dimen.small_screen_required_visible_empty_space_in_header);
                int iLoadDimensionPixelSize3 = WindowDecoration.loadDimensionPixelSize(captionWindowDecoration.mContext.getResources(), R.dimen.caption_right_buttons_width);
                int iWidth = captionWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().width();
                DisplayLayout displayLayout = captionWindowDecoration.mDisplayController.getDisplayLayout(captionWindowDecoration.mTaskInfo.displayId);
                int i = displayLayout.mWidth;
                Rect rect2 = new Rect();
                displayLayout.getStableBounds(rect2, false);
                int i2 = iLoadDimensionPixelSize + iLoadDimensionPixelSize3 + iLoadDimensionPixelSize2;
                rect = new Rect(i2 > iWidth ? 0 : (-iWidth) + iLoadDimensionPixelSize2 + iLoadDimensionPixelSize3, rect2.top, i2 > iWidth ? i - iWidth : (i - iLoadDimensionPixelSize2) - iLoadDimensionPixelSize, rect2.bottom - iLoadDimensionPixelSize2);
            }
            DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rectOnDragPositioningEnd, rect);
            if (rectOnDragPositioningEnd != runningTaskInfo.configuration.windowConfiguration.getBounds()) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(runningTaskInfo.token, rectOnDragPositioningEnd);
                CaptionWindowDecorViewModel.this.mTransitions.startTransition(6, windowContainerTransaction, null);
            }
            boolean z = this.mIsDragging;
            this.mIsDragging = false;
            return z;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.close_window) {
                TaskOperations taskOperations = CaptionWindowDecorViewModel.this.mTaskOperations;
                WindowContainerToken windowContainerToken = this.mTaskToken;
                taskOperations.getClass();
                taskOperations.closeTask(windowContainerToken, new WindowContainerTransaction(), true);
                return;
            }
            if (id == R.id.back_button) {
                TaskOperations taskOperations2 = CaptionWindowDecorViewModel.this.mTaskOperations;
                int i = this.mDisplayId;
                taskOperations2.sendBackEvent(0, i);
                taskOperations2.sendBackEvent(1, i);
                return;
            }
            if (id != R.id.minimize_window) {
                if (id == R.id.maximize_window) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    CaptionWindowDecorViewModel.this.mTaskOperations.maximizeTask(runningTaskInfo, CaptionWindowDecorViewModel.this.mRootTaskDisplayAreaOrganizer.getDisplayAreaInfo(runningTaskInfo.displayId).configuration.windowConfiguration.getWindowingMode());
                    return;
                }
                return;
            }
            TaskOperations taskOperations3 = CaptionWindowDecorViewModel.this.mTaskOperations;
            WindowContainerToken windowContainerToken2 = this.mTaskToken;
            int i2 = this.mTaskId;
            taskOperations3.getClass();
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.reorder(windowContainerToken2, false);
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                taskOperations3.mTransitionStarter.startMinimizedModeTransition(i2, windowContainerTransaction, false);
            } else {
                taskOperations3.mSyncQueue.queue(windowContainerTransaction);
            }
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver;
            if (view.getId() == R.id.caption) {
                if (motionEvent.getAction() == 0) {
                    if (!CaptionWindowDecorViewModel.this.mFocusTransitionObserver.hasGlobalFocus(CaptionWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId))) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.reorder(this.mTaskToken, true, true);
                        CaptionWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction);
                    }
                }
                CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) CaptionWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                int actionMasked = motionEvent.getActionMasked();
                boolean z = actionMasked == 0;
                boolean z2 = actionMasked == 3 || actionMasked == 1;
                if (z) {
                    boolean zContains = captionWindowDecoration.mResult.mCustomizableCaptionRegion.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    boolean zContains2 = CaptionWindowDecorViewModel.this.mExclusionRegion.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    boolean zIsTransparentCaptionBarAppearance = TaskInfoKt.isTransparentCaptionBarAppearance(captionWindowDecoration.mTaskInfo);
                    int[] iArr = new int[2];
                    view.getLocationInWindow(iArr);
                    Point point = new Point(iArr[0], iArr[1]);
                    DragResizeInputListener dragResizeInputListener = captionWindowDecoration.mDragResizeListener;
                    boolean z3 = (dragResizeInputListener == null || (taskResizeInputEventReceiver = dragResizeInputListener.mInputEventReceiver) == null || !taskResizeInputEventReceiver.mDragResizeWindowGeometry.shouldHandleEvent(motionEvent, point)) ? false : true;
                    CaptionWindowDecorViewModel.this.mShouldPilferCaptionEvents = ((zContains && zContains2 && zIsTransparentCaptionBarAppearance) || z3) ? false : true;
                }
                CaptionWindowDecorViewModel captionWindowDecorViewModel = CaptionWindowDecorViewModel.this;
                if (captionWindowDecorViewModel.mShouldPilferCaptionEvents) {
                    InputManager inputManager = captionWindowDecorViewModel.mInputManager;
                    if (inputManager != null) {
                        inputManager.pilferPointers(view.getViewRootImpl().getInputToken());
                    }
                    if (z2) {
                        CaptionWindowDecorViewModel.this.mShouldPilferCaptionEvents = false;
                    }
                    return this.mDragDetector.onMotionEvent(null, motionEvent);
                }
            }
            return false;
        }

        private CaptionTouchEventListener(ActivityManager.RunningTaskInfo runningTaskInfo, DragPositioningCallback dragPositioningCallback) {
            this.mDragPointerId = -1;
            this.mTaskId = runningTaskInfo.taskId;
            this.mTaskToken = runningTaskInfo.token;
            this.mDragPositioningCallback = dragPositioningCallback;
            this.mDragDetector = new DragDetector(this, 0L, ViewConfiguration.get(CaptionWindowDecorViewModel.this.mContext).getScaledTouchSlop());
            this.mDisplayId = runningTaskInfo.displayId;
        }
    }

    public CaptionWindowDecorViewModel(Context context, Handler handler, ShellExecutor shellExecutor, ShellExecutor shellExecutor2, Choreographer choreographer, IWindowManager iWindowManager, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SyncTransactionQueue syncTransactionQueue, Transitions transitions, FocusTransitionObserver focusTransitionObserver, WindowDecorViewHostSupplier windowDecorViewHostSupplier, DesktopState desktopState, DesktopConfig desktopConfig) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mBgExecutor = shellExecutor2;
        this.mWindowManager = iWindowManager;
        this.mMainChoreographer = choreographer;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.mSyncQueue = syncTransactionQueue;
        this.mTransitions = transitions;
        this.mFocusTransitionObserver = focusTransitionObserver;
        this.mWindowDecorViewHostSupplier = windowDecorViewHostSupplier;
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTaskOperations = new TaskOperations(null, context, syncTransactionQueue);
        }
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mDesktopState = desktopState;
        this.mDesktopConfig = desktopConfig;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.windowdecor.CaptionWindowDecorViewModel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CaptionWindowDecorViewModel captionWindowDecorViewModel = this.f$0;
                try {
                    captionWindowDecorViewModel.mWindowManager.registerSystemGestureExclusionListener(captionWindowDecorViewModel.mGestureExclusionListener, captionWindowDecorViewModel.mContext.getDisplayId());
                } catch (RemoteException e) {
                    Log.e("CaptionWindowDecorViewModel", "Failed to register window manager callbacks", e);
                }
                captionWindowDecorViewModel.mFocusTransitionObserver.setLocalFocusTransitionListener(captionWindowDecorViewModel, captionWindowDecorViewModel.mMainExecutor);
            }
        }, this);
    }

    public final void createWindowDecoration$1(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws Resources.NotFoundException {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (captionWindowDecoration != null) {
            captionWindowDecoration.close();
        }
        Context context = this.mContext;
        Context contextCreateContextAsUser = context.createContextAsUser(UserHandle.of(runningTaskInfo.userId), 0);
        Choreographer choreographer = this.mMainChoreographer;
        CaptionWindowDecoration captionWindowDecoration2 = new CaptionWindowDecoration(context, contextCreateContextAsUser, this.mDisplayController, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainExecutor, this.mBgExecutor, choreographer, this.mSyncQueue, this.mWindowDecorViewHostSupplier, this.mDesktopConfig);
        this.mWindowDecorByTaskId.put(runningTaskInfo.taskId, captionWindowDecoration2);
        FluidResizeTaskPositioner fluidResizeTaskPositioner = new FluidResizeTaskPositioner(this.mTaskOrganizer, this.mTransitions, captionWindowDecoration2, this.mDisplayController, this.mDesktopState);
        CaptionTouchEventListener captionTouchEventListener = new CaptionTouchEventListener(this, runningTaskInfo, fluidResizeTaskPositioner);
        captionWindowDecoration2.mOnCaptionButtonClickListener = captionTouchEventListener;
        captionWindowDecoration2.mOnCaptionTouchListener = captionTouchEventListener;
        captionWindowDecoration2.mDragPositioningCallback = fluidResizeTaskPositioner;
        captionWindowDecoration2.mTaskDragResizer = fluidResizeTaskPositioner;
        captionWindowDecoration2.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), this.mExclusionRegion);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo) {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.removeReturnOld(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        captionWindowDecoration.close();
    }

    @Override // com.android.wm.shell.shared.FocusTransitionListener
    public final void onFocusedTaskChanged(int i, boolean z, boolean z2) {
        WindowDecoration windowDecoration = (WindowDecoration) this.mWindowDecorByTaskId.get(i);
        if (windowDecoration != null) {
            windowDecoration.relayout(windowDecoration.mTaskInfo, z2, windowDecoration.mExclusionRegion);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws Resources.NotFoundException {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (!shouldShowWindowDecor$1(runningTaskInfo)) {
            if (captionWindowDecoration != null) {
                destroyWindowDecoration(runningTaskInfo);
            }
        } else if (captionWindowDecoration == null) {
            createWindowDecoration$1(runningTaskInfo, surfaceControl, transaction, transaction2);
        } else {
            captionWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), this.mExclusionRegion);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws Resources.NotFoundException {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        captionWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), this.mExclusionRegion);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws Resources.NotFoundException {
        CaptionWindowDecoration captionWindowDecoration = (CaptionWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (captionWindowDecoration == null) {
            return;
        }
        if (shouldShowWindowDecor$1(runningTaskInfo)) {
            captionWindowDecoration.relayout(runningTaskInfo, captionWindowDecoration.mHasGlobalFocus, captionWindowDecoration.mExclusionRegion);
        } else {
            destroyWindowDecoration(runningTaskInfo);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws Resources.NotFoundException {
        if (!shouldShowWindowDecor$1(runningTaskInfo)) {
            return false;
        }
        createWindowDecoration$1(runningTaskInfo, surfaceControl, transaction, transaction2);
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mTaskOrganizer.getRunningTaskInfo(runningTaskInfo.taskId) == null) {
            destroyWindowDecoration(runningTaskInfo);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setFreeformTaskTransitionStarter(FreeformTaskTransitionStarter freeformTaskTransitionStarter) {
        this.mTaskOperations = new TaskOperations(freeformTaskTransitionStarter, this.mContext, this.mSyncQueue);
    }

    public final boolean shouldShowWindowDecor$1(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mDisplayController.mDisplayManager.getDisplay(runningTaskInfo.displayId) == null) {
            return false;
        }
        if (runningTaskInfo.getWindowingMode() == 5) {
            return true;
        }
        if (runningTaskInfo.getWindowingMode() == 2 || runningTaskInfo.getActivityType() != 1) {
            return false;
        }
        DisplayAreaInfo displayAreaInfo = this.mRootTaskDisplayAreaOrganizer.getDisplayAreaInfo(runningTaskInfo.displayId);
        if (displayAreaInfo != null) {
            return displayAreaInfo.configuration.windowConfiguration.getWindowingMode() == 5;
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.pc")) {
            return true;
        }
        return (runningTaskInfo.displayId == 0 || Settings.Global.getInt(this.mContext.getContentResolver(), "force_desktop_mode_on_external_displays", 0) == 0) ? false : true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setSplitScreenController(SplitScreenController splitScreenController) {
    }
}
