package com.android.wm.shell.windowdecor.tiling;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.DragResizeWindowGeometry;
import com.android.wm.shell.windowdecor.ResizeVeil;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTilingWindowDecoration implements Transitions.TransitionHandler, ShellTaskOrganizer.FocusListener, ShellTaskOrganizer.TaskVanishedListener, DragPositioningCallbackUtility.DragEventListener, Transitions.TransitionObserver, FocusTransitionListener {
    public static final String TAG;
    public final CoroutineScope bgScope;
    public final Context context;
    public final DesktopModeEventLogger desktopModeEventLogger;
    public final DesktopState desktopState;
    public DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public final int displayId;
    public Rect dividerBounds;
    public final FocusTransitionObserver focusTransitionObserver;
    public boolean isDarkMode;
    public boolean isResizing;
    public boolean isTilingManagerInitialised;
    public AppResizingHelper leftTaskResizingHelper;
    public final MainCoroutineDispatcher mainDispatcher;
    public final ShellExecutor mainExecutor;
    public final ReturnToDragStartAnimator returnToDragStartAnimator;
    public AppResizingHelper rightTaskResizingHelper;
    public final RootTaskDisplayAreaOrganizer rootTdaOrganizer;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final SyncTransactionQueue syncQueue;
    public final WindowDecorTaskResourceLoader taskResourceLoader;
    public final ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler;
    public final Supplier transactionSupplier;
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AppResizingHelper {
        public final CoroutineScope bgScope;
        public final Rect bounds;
        public final DesktopModeWindowDecoration desktopModeWindowDecoration;
        public final Context displayContext;
        public final DisplayController displayController;
        public boolean isInitialised;
        public final MainCoroutineDispatcher mainDispatcher;
        public final Rect newBounds;
        public ResizeVeil resizeVeil;
        public final ActivityManager.RunningTaskInfo taskInfo;
        public final WindowDecorTaskResourceLoader taskResourceLoader;
        public final Supplier transactionSupplier;
        public Function0 visibilityCallback;

        public AppResizingHelper(ActivityManager.RunningTaskInfo runningTaskInfo, DesktopModeWindowDecoration desktopModeWindowDecoration, Context context, Rect rect, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, Supplier<SurfaceControl.Transaction> supplier) {
            this.taskInfo = runningTaskInfo;
            this.desktopModeWindowDecoration = desktopModeWindowDecoration;
            this.bounds = rect;
            this.displayController = displayController;
            this.taskResourceLoader = windowDecorTaskResourceLoader;
            this.mainDispatcher = mainCoroutineDispatcher;
            this.bgScope = coroutineScope;
            this.transactionSupplier = supplier;
            this.newBounds = new Rect(rect);
            this.displayContext = displayController.getDisplayContext(runningTaskInfo.displayId);
            context.createContextAsUser(UserHandle.of(runningTaskInfo.userId), 0);
        }

        public final void initIfNeeded() {
            if (this.isInitialised) {
                return;
            }
            if (this.displayContext != null) {
                this.resizeVeil = new ResizeVeil(this.displayContext, this.displayController, this.taskResourceLoader, this.mainDispatcher, this.bgScope, this.desktopModeWindowDecoration.mTaskSurface, this.transactionSupplier, null, null, this.taskInfo, 384, null);
            }
            this.isInitialised = true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DesktopTasksController.SnapPosition.values().length];
            try {
                iArr[DesktopTasksController.SnapPosition.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DesktopTasksController.SnapPosition.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        TAG = "DesktopTilingWindowDecoration";
    }

    public DesktopTilingWindowDecoration(Context context, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, SyncTransactionQueue syncTransactionQueue, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, int i, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, ReturnToDragStartAnimator returnToDragStartAnimator, DesktopUserRepositories desktopUserRepositories, DesktopModeEventLogger desktopModeEventLogger, FocusTransitionObserver focusTransitionObserver, ShellExecutor shellExecutor, DesktopState desktopState, Supplier<SurfaceControl.Transaction> supplier) {
        this.context = context;
        this.mainDispatcher = mainCoroutineDispatcher;
        this.bgScope = coroutineScope;
        this.syncQueue = syncTransactionQueue;
        this.displayController = displayController;
        this.taskResourceLoader = windowDecorTaskResourceLoader;
        this.displayId = i;
        this.rootTdaOrganizer = rootTaskDisplayAreaOrganizer;
        this.transitions = transitions;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.toggleResizeDesktopTaskTransitionHandler = toggleResizeDesktopTaskTransitionHandler;
        this.returnToDragStartAnimator = returnToDragStartAnimator;
        this.desktopUserRepositories = desktopUserRepositories;
        this.desktopModeEventLogger = desktopModeEventLogger;
        this.focusTransitionObserver = focusTransitionObserver;
        this.mainExecutor = shellExecutor;
        this.desktopState = desktopState;
        this.transactionSupplier = supplier;
    }

    public final boolean allTiledTasksVisible() {
        AppResizingHelper appResizingHelper;
        AppResizingHelper appResizingHelper2 = this.leftTaskResizingHelper;
        if (appResizingHelper2 == null || (appResizingHelper = this.rightTaskResizingHelper) == null) {
            return false;
        }
        DesktopRepository current = this.desktopUserRepositories.getCurrent();
        return current.isVisibleTask(appResizingHelper2.taskInfo.taskId) && current.isVisibleTask(appResizingHelper.taskInfo.taskId);
    }

    public final Rect getSnapBounds(DesktopTasksController.SnapPosition snapPosition) {
        int i;
        int dimensionPixelSize;
        int m;
        DisplayController displayController = this.displayController;
        int i2 = this.displayId;
        DisplayLayout displayLayout = displayController.getDisplayLayout(i2);
        if (displayLayout == null) {
            return new Rect();
        }
        Context displayContext = displayController.getDisplayContext(i2);
        if (displayContext == null) {
            return new Rect();
        }
        Rect rect = new Rect();
        displayLayout.getStableBounds(rect, false);
        AppResizingHelper appResizingHelper = this.leftTaskResizingHelper;
        AppResizingHelper appResizingHelper2 = this.rightTaskResizingHelper;
        int width = rect.width() / 2;
        int i3 = WhenMappings.$EnumSwitchMapping$0[snapPosition.ordinal()];
        if (i3 != 1) {
            if (i3 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (appResizingHelper == null) {
                m = (displayContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2) + (rect.right - width);
            } else {
                m = StrongAuthPopup$$ExternalSyntheticOutline0.m(displayContext, R.dimen.split_divider_bar_width, appResizingHelper.bounds.right);
            }
            return new Rect(m, rect.top, rect.right, rect.bottom);
        }
        if (appResizingHelper2 == null) {
            i = rect.left + width;
            dimensionPixelSize = displayContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width) / 2;
        } else {
            i = appResizingHelper2.bounds.left;
            dimensionPixelSize = displayContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
        }
        return new Rect(rect.left, rect.top, i - dimensionPixelSize, rect.bottom);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    public final boolean moveTiledPairToFront(int i, boolean z) {
        AppResizingHelper appResizingHelper;
        AppResizingHelper appResizingHelper2;
        AppResizingHelper appResizingHelper3;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        AppResizingHelper appResizingHelper4;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        DesktopTilingDividerWindowManager desktopTilingDividerWindowManager2;
        ActivityManager.RunningTaskInfo runningTaskInfo4;
        if (this.isTilingManagerInitialised && z && (appResizingHelper = this.leftTaskResizingHelper) != null && (appResizingHelper2 = this.rightTaskResizingHelper) != null && allTiledTasksVisible()) {
            boolean z2 = i == appResizingHelper.taskInfo.taskId;
            AppResizingHelper appResizingHelper5 = this.leftTaskResizingHelper;
            if ((appResizingHelper5 != null && (runningTaskInfo4 = appResizingHelper5.taskInfo) != null && i == runningTaskInfo4.taskId) || ((appResizingHelper3 = this.rightTaskResizingHelper) != null && (runningTaskInfo = appResizingHelper3.taskInfo) != null && i == runningTaskInfo.taskId)) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.transactionSupplier.get();
                AppResizingHelper appResizingHelper6 = this.leftTaskResizingHelper;
                if (appResizingHelper6 != null && (runningTaskInfo3 = appResizingHelper6.taskInfo) != null && i == runningTaskInfo3.taskId && (desktopTilingDividerWindowManager2 = this.desktopTilingDividerWindowManager) != null) {
                    transaction.setRelativeLayer(desktopTilingDividerWindowManager2.leash, appResizingHelper.desktopModeWindowDecoration.mTaskSurface, 1);
                }
                AppResizingHelper appResizingHelper7 = this.rightTaskResizingHelper;
                if (appResizingHelper7 != null && (runningTaskInfo2 = appResizingHelper7.taskInfo) != null && i == runningTaskInfo2.taskId && (desktopTilingDividerWindowManager = this.desktopTilingDividerWindowManager) != null) {
                    transaction.setRelativeLayer(desktopTilingDividerWindowManager.leash, appResizingHelper2.desktopModeWindowDecoration.mTaskSurface, 1);
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                AppResizingHelper appResizingHelper8 = this.leftTaskResizingHelper;
                if (appResizingHelper8 != null && (appResizingHelper4 = this.rightTaskResizingHelper) != null) {
                    if (z2) {
                        windowContainerTransaction.reorder(appResizingHelper4.taskInfo.token, true);
                        windowContainerTransaction.reorder(appResizingHelper8.taskInfo.token, true);
                    } else {
                        windowContainerTransaction.reorder(appResizingHelper8.taskInfo.token, true);
                        windowContainerTransaction.reorder(appResizingHelper4.taskInfo.token, true);
                    }
                }
                this.transitions.startTransition(3, windowContainerTransaction, null);
                transaction.apply();
                return true;
            }
        }
        return false;
    }

    public final boolean onDividerHandleMoved(Rect rect, SurfaceControl.Transaction transaction) {
        AppResizingHelper appResizingHelper;
        SurfaceControl.Transaction transaction2;
        AppResizingHelper appResizingHelper2 = this.leftTaskResizingHelper;
        if (appResizingHelper2 != null && (appResizingHelper = this.rightTaskResizingHelper) != null) {
            Rect rect2 = new Rect();
            DisplayLayout displayLayout = this.displayController.getDisplayLayout(this.displayId);
            if (displayLayout != null) {
                displayLayout.getStableBounds(rect2, false);
            }
            if (!rect2.isEmpty()) {
                Rect rect3 = appResizingHelper2.bounds;
                Rect rect4 = appResizingHelper.bounds;
                Rect rect5 = new Rect(rect3.left, rect3.top, rect.left, rect3.bottom);
                Rect rect6 = new Rect(rect.right, rect4.top, rect4.right, rect4.bottom);
                int width = rect5.width();
                int width2 = rect3.width();
                AppResizingHelper appResizingHelper3 = this.leftTaskResizingHelper;
                DesktopModeWindowDecoration desktopModeWindowDecoration = appResizingHelper3 != null ? appResizingHelper3.desktopModeWindowDecoration : null;
                DesktopStateImpl desktopStateImpl = (DesktopStateImpl) this.desktopState;
                if (!DragPositioningCallbackUtility.isExceedingWidthConstraint(width, width2, rect2, this.displayController, desktopModeWindowDecoration, desktopStateImpl.canEnterDesktopMode, true)) {
                    int width3 = rect6.width();
                    int width4 = rect4.width();
                    AppResizingHelper appResizingHelper4 = this.rightTaskResizingHelper;
                    if (!DragPositioningCallbackUtility.isExceedingWidthConstraint(width3, width4, rect2, this.displayController, appResizingHelper4 != null ? appResizingHelper4.desktopModeWindowDecoration : null, desktopStateImpl.canEnterDesktopMode, true)) {
                        appResizingHelper2.newBounds.set(rect5);
                        appResizingHelper.newBounds.set(rect6);
                        if (this.isResizing) {
                            transaction2 = transaction;
                            ResizeVeil resizeVeil = appResizingHelper2.resizeVeil;
                            if (resizeVeil == null) {
                                resizeVeil = null;
                            }
                            Rect rect7 = appResizingHelper2.newBounds;
                            if (resizeVeil.isVisible) {
                                ValueAnimator valueAnimator = resizeVeil.veilAnimator;
                                if (valueAnimator != null && valueAnimator.isStarted()) {
                                    valueAnimator.removeAllUpdateListeners();
                                    valueAnimator.end();
                                }
                                resizeVeil.relayout(rect7, transaction2);
                            }
                            ResizeVeil resizeVeil2 = appResizingHelper.resizeVeil;
                            if (resizeVeil2 == null) {
                                resizeVeil2 = null;
                            }
                            Rect rect8 = appResizingHelper.newBounds;
                            if (resizeVeil2.isVisible) {
                                ValueAnimator valueAnimator2 = resizeVeil2.veilAnimator;
                                if (valueAnimator2 != null && valueAnimator2.isStarted()) {
                                    valueAnimator2.removeAllUpdateListeners();
                                    valueAnimator2.end();
                                }
                                resizeVeil2.relayout(rect8, transaction2);
                            }
                        } else {
                            ResizeVeil resizeVeil3 = appResizingHelper2.resizeVeil;
                            if (resizeVeil3 == null) {
                                resizeVeil3 = null;
                            }
                            SurfaceControl surfaceControl = appResizingHelper2.desktopModeWindowDecoration.mTaskSurface;
                            Rect rect9 = appResizingHelper2.bounds;
                            ActivityManager.RunningTaskInfo runningTaskInfo = appResizingHelper2.taskInfo;
                            int i = ResizeVeil.$r8$clinit;
                            resizeVeil3.updateTransactionWithShowVeil(transaction, surfaceControl, rect9, runningTaskInfo, false);
                            ResizeVeil resizeVeil4 = appResizingHelper.resizeVeil;
                            if (resizeVeil4 == null) {
                                resizeVeil4 = null;
                            }
                            resizeVeil4.updateTransactionWithShowVeil(transaction, appResizingHelper.desktopModeWindowDecoration.mTaskSurface, appResizingHelper.bounds, appResizingHelper.taskInfo, false);
                            transaction2 = transaction;
                            this.isResizing = true;
                        }
                        transaction2.apply();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.DragEventListener
    public final void onDragMove(int i) {
        removeTaskIfTiled(i, false, false);
    }

    @Override // com.android.wm.shell.shared.FocusTransitionListener
    public final void onFocusedTaskChanged(int i, boolean z, boolean z2) {
        moveTiledPairToFront(i, z);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskVanishedListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        removeTaskIfTiled(runningTaskInfo.taskId, true, true);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        DesktopTilingDividerWindowManager desktopTilingDividerWindowManager;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        AppResizingHelper appResizingHelper;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        AppResizingHelper appResizingHelper2;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        ActivityManager.RunningTaskInfo runningTaskInfo4;
        ActivityManager.RunningTaskInfo taskInfo;
        Iterator it = transitionInfo.getChanges().iterator();
        boolean z = false;
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
            if (taskInfo2 != null) {
                if (taskInfo2.getWindowingMode() != 1) {
                    int mode = change.getMode();
                    int type = transitionInfo.getType();
                    if (mode != 4 || (type != 1020 && type != 4 && type != 1)) {
                        int type2 = transitionInfo.getType();
                        if (change.getTaskInfo() == null || (taskInfo = change.getTaskInfo()) == null || taskInfo.getWindowingMode() != 2 || !(type2 == 1 || type2 == 3 || type2 == 6 || type2 == 10)) {
                            int mode2 = change.getMode();
                            int type3 = transitionInfo.getType();
                            if (mode2 == 3 && type3 == 3) {
                                int i = taskInfo2.taskId;
                                AppResizingHelper appResizingHelper3 = this.leftTaskResizingHelper;
                                if (appResizingHelper3 == null || (runningTaskInfo4 = appResizingHelper3.taskInfo) == null || i != runningTaskInfo4.taskId) {
                                    AppResizingHelper appResizingHelper4 = this.rightTaskResizingHelper;
                                    if (appResizingHelper4 != null && (runningTaskInfo = appResizingHelper4.taskInfo) != null && i == runningTaskInfo.taskId) {
                                        Function0 function0 = appResizingHelper4.visibilityCallback;
                                        if (function0 != null) {
                                            function0.invoke();
                                        }
                                        appResizingHelper4.visibilityCallback = null;
                                    }
                                } else {
                                    Function0 function02 = appResizingHelper3.visibilityCallback;
                                    if (function02 != null) {
                                        function02.invoke();
                                    }
                                    appResizingHelper3.visibilityCallback = null;
                                }
                                z = z || !((appResizingHelper2 = this.leftTaskResizingHelper) == null || (runningTaskInfo3 = appResizingHelper2.taskInfo) == null || taskInfo2.taskId != runningTaskInfo3.taskId);
                                if (!z2 && ((appResizingHelper = this.rightTaskResizingHelper) == null || (runningTaskInfo2 = appResizingHelper.taskInfo) == null || taskInfo2.taskId != runningTaskInfo2.taskId)) {
                                    r2 = false;
                                }
                                z2 = r2;
                            }
                        } else {
                            removeTaskIfTiled(taskInfo2.taskId, true, taskInfo2.getWindowingMode() == 1);
                        }
                    }
                }
                removeTaskIfTiled(taskInfo2.taskId, false, taskInfo2.getWindowingMode() == 1);
            }
        }
        if (!z || !z2 || (desktopTilingDividerWindowManager = this.desktopTilingDividerWindowManager) == null || desktopTilingDividerWindowManager.dividerShown) {
            return;
        }
        SurfaceControl.Transaction transaction3 = (SurfaceControl.Transaction) desktopTilingDividerWindowManager.transactionSupplier.get();
        transaction3.show(desktopTilingDividerWindowManager.leash);
        transaction3.apply();
        desktopTilingDividerWindowManager.dividerShown = true;
    }

    public final void removeTask(AppResizingHelper appResizingHelper, boolean z, boolean z2) {
        if (appResizingHelper == null) {
            return;
        }
        if (!z) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = appResizingHelper.desktopModeWindowDecoration;
            desktopModeWindowDecoration.mTaskDragResizer.removeDragEventListener(this);
            desktopModeWindowDecoration.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.NONE, z2);
        }
        if (appResizingHelper.isInitialised) {
            ResizeVeil resizeVeil = appResizingHelper.resizeVeil;
            if (resizeVeil == null) {
                resizeVeil = null;
            }
            resizeVeil.dispose();
        }
    }

    public final void removeTaskIfTiled(int i, boolean z, final boolean z2) {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        ActivityManager.RunningTaskInfo runningTaskInfo3;
        ActivityManager.RunningTaskInfo runningTaskInfo4;
        DesktopUserRepositories desktopUserRepositories = this.desktopUserRepositories;
        DesktopRepository current = desktopUserRepositories.getCurrent();
        AppResizingHelper appResizingHelper = this.leftTaskResizingHelper;
        int i2 = this.displayId;
        Integer num = null;
        if (appResizingHelper != null && (runningTaskInfo3 = appResizingHelper.taskInfo) != null && i == runningTaskInfo3.taskId) {
            DesktopRepository current2 = desktopUserRepositories.getCurrent();
            DesktopRepository.logD("removeLeftTiledTask for displayId=%d", Integer.valueOf(i2));
            DesktopRepository.DesktopData desktopData = current2.desktopData;
            DesktopRepository.Desk defaultDesk = desktopData.getDefaultDesk(i2);
            if (defaultDesk == null) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Expected desk in display: ").toString());
            }
            DesktopRepository.logD("removeLeftTiledTaskToDesk for displayId=%d", Integer.valueOf(i2));
            int i3 = defaultDesk.deskId;
            DesktopRepository.Desk desk = desktopData.getDesk(i3);
            if (desk == null) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Did not find desk: ").toString());
            }
            desk.leftTiledTaskId = null;
            if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
                current2.updatePersistentRepository(i2);
            }
            removeTask(this.leftTaskResizingHelper, z, z2);
            this.leftTaskResizingHelper = null;
            AppResizingHelper appResizingHelper2 = this.rightTaskResizingHelper;
            if (appResizingHelper2 != null && (runningTaskInfo4 = appResizingHelper2.taskInfo) != null) {
                num = Integer.valueOf(runningTaskInfo4.taskId);
            }
            final int i4 = 0;
            Function0 function0 = new Function0(this) { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration$$ExternalSyntheticLambda0
                public final /* synthetic */ DesktopTilingWindowDecoration f$0;

                {
                    this.f$0 = this;
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DesktopModeWindowDecoration desktopModeWindowDecoration;
                    DesktopModeWindowDecoration desktopModeWindowDecoration2;
                    switch (i4) {
                        case 0:
                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = this.f$0.rightTaskResizingHelper;
                            if (appResizingHelper3 != null && (desktopModeWindowDecoration = appResizingHelper3.desktopModeWindowDecoration) != null) {
                                desktopModeWindowDecoration.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.NONE, z2);
                            }
                            break;
                        default:
                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = this.f$0.leftTaskResizingHelper;
                            if (appResizingHelper4 != null && (desktopModeWindowDecoration2 = appResizingHelper4.desktopModeWindowDecoration) != null) {
                                desktopModeWindowDecoration2.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.NONE, z2);
                            }
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
            if (num == null || !current.isVisibleTask(num.intValue())) {
                AppResizingHelper appResizingHelper3 = this.rightTaskResizingHelper;
                if (appResizingHelper3 != null) {
                    appResizingHelper3.visibilityCallback = function0;
                }
            } else {
                function0.invoke();
            }
            tearDownTiling();
            return;
        }
        AppResizingHelper appResizingHelper4 = this.rightTaskResizingHelper;
        if (appResizingHelper4 == null || (runningTaskInfo = appResizingHelper4.taskInfo) == null || i != runningTaskInfo.taskId) {
            return;
        }
        DesktopRepository current3 = desktopUserRepositories.getCurrent();
        DesktopRepository.logD("removeRightTiledTask for displayId=%d", Integer.valueOf(i2));
        DesktopRepository.DesktopData desktopData2 = current3.desktopData;
        DesktopRepository.Desk defaultDesk2 = desktopData2.getDefaultDesk(i2);
        if (defaultDesk2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Expected desk in display: ").toString());
        }
        DesktopRepository.logD("removeRightTiledTaskFromDesk for displayId=%d", Integer.valueOf(i2));
        int i5 = defaultDesk2.deskId;
        DesktopRepository.Desk desk2 = desktopData2.getDesk(i5);
        if (desk2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "Did not find desk: ").toString());
        }
        desk2.rightTiledTaskId = null;
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            current3.updatePersistentRepository(i2);
        }
        removeTask(this.rightTaskResizingHelper, z, z2);
        this.rightTaskResizingHelper = null;
        AppResizingHelper appResizingHelper5 = this.leftTaskResizingHelper;
        if (appResizingHelper5 != null && (runningTaskInfo2 = appResizingHelper5.taskInfo) != null) {
            num = Integer.valueOf(runningTaskInfo2.taskId);
        }
        final int i6 = 1;
        Function0 function02 = new Function0(this) { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopTilingWindowDecoration f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DesktopModeWindowDecoration desktopModeWindowDecoration;
                DesktopModeWindowDecoration desktopModeWindowDecoration2;
                switch (i6) {
                    case 0:
                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper32 = this.f$0.rightTaskResizingHelper;
                        if (appResizingHelper32 != null && (desktopModeWindowDecoration = appResizingHelper32.desktopModeWindowDecoration) != null) {
                            desktopModeWindowDecoration.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.NONE, z2);
                        }
                        break;
                    default:
                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper42 = this.f$0.leftTaskResizingHelper;
                        if (appResizingHelper42 != null && (desktopModeWindowDecoration2 = appResizingHelper42.desktopModeWindowDecoration) != null) {
                            desktopModeWindowDecoration2.updateDisabledResizingEdge(DragResizeWindowGeometry.DisabledEdge.NONE, z2);
                        }
                        break;
                }
                return Unit.INSTANCE;
            }
        };
        if (num == null || !current.isVisibleTask(num.intValue())) {
            AppResizingHelper appResizingHelper6 = this.leftTaskResizingHelper;
            if (appResizingHelper6 != null) {
                appResizingHelper6.visibilityCallback = function02;
            }
        } else {
            function02.invoke();
        }
        tearDownTiling();
    }

    public final void resetTilingSession() {
        AppResizingHelper appResizingHelper = this.leftTaskResizingHelper;
        if (appResizingHelper != null) {
            removeTask(appResizingHelper, false, true);
            this.leftTaskResizingHelper = null;
        }
        AppResizingHelper appResizingHelper2 = this.rightTaskResizingHelper;
        if (appResizingHelper2 != null) {
            removeTask(appResizingHelper2, false, true);
            this.rightTaskResizingHelper = null;
        }
        tearDownTiling();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        AppResizingHelper appResizingHelper;
        AppResizingHelper appResizingHelper2 = this.leftTaskResizingHelper;
        if (appResizingHelper2 == null || (appResizingHelper = this.rightTaskResizingHelper) == null) {
            return false;
        }
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            change.getClass();
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            Integer valueOf = taskInfo != null ? Integer.valueOf(taskInfo.taskId) : null;
            int i = appResizingHelper2.taskInfo.taskId;
            if (valueOf == null || valueOf.intValue() != i) {
                int i2 = appResizingHelper.taskInfo.taskId;
                if (valueOf == null || valueOf.intValue() != i2) {
                    Log.d(TAG, "startAnimation: skip non-tiled change=" + change);
                }
            }
            SurfaceControl leash = change.getLeash();
            ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
            Rect rect = (taskInfo2 == null || taskInfo2.taskId != appResizingHelper2.taskInfo.taskId) ? appResizingHelper.bounds : appResizingHelper2.bounds;
            transaction.setWindowCrop(leash, rect.width(), rect.height());
            transaction2.setWindowCrop(leash, rect.width(), rect.height());
        }
        transaction.apply();
        ResizeVeil resizeVeil = appResizingHelper2.resizeVeil;
        if (resizeVeil == null) {
            resizeVeil = null;
        }
        resizeVeil.hideVeil();
        ResizeVeil resizeVeil2 = appResizingHelper.resizeVeil;
        if (resizeVeil2 == null) {
            resizeVeil2 = null;
        }
        resizeVeil2.hideVeil();
        transitionFinishCallback.onTransitionFinished(null);
        return true;
    }

    public final void tearDownTiling() {
        if (this.isTilingManagerInitialised) {
            ((HashMap) this.focusTransitionObserver.mLocalListeners).remove(this);
        }
        if (this.leftTaskResizingHelper == null && this.rightTaskResizingHelper == null) {
            ShellTaskOrganizer shellTaskOrganizer = this.shellTaskOrganizer;
            Iterator it = shellTaskOrganizer.mListenerIterator;
            if (it != null) {
                it.remove();
            } else {
                synchronized (shellTaskOrganizer.mLock) {
                    shellTaskOrganizer.mTaskVanishedListeners.remove(this);
                }
            }
        }
        this.isTilingManagerInitialised = false;
        DesktopTilingDividerWindowManager desktopTilingDividerWindowManager = this.desktopTilingDividerWindowManager;
        if (desktopTilingDividerWindowManager != null) {
            desktopTilingDividerWindowManager.tilingDividerView = null;
            SurfaceControlViewHost surfaceControlViewHost = desktopTilingDividerWindowManager.viewHost;
            if (surfaceControlViewHost == null) {
                surfaceControlViewHost = null;
            }
            surfaceControlViewHost.release();
            ((SurfaceControl.Transaction) desktopTilingDividerWindowManager.transactionSupplier.get()).hide(desktopTilingDividerWindowManager.leash).remove(desktopTilingDividerWindowManager.leash).apply();
        }
        this.desktopTilingDividerWindowManager = null;
        this.transitions.mObservers.remove(this);
    }

    public /* synthetic */ DesktopTilingWindowDecoration(Context context, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, SyncTransactionQueue syncTransactionQueue, DisplayController displayController, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, int i, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, ReturnToDragStartAnimator returnToDragStartAnimator, DesktopUserRepositories desktopUserRepositories, DesktopModeEventLogger desktopModeEventLogger, FocusTransitionObserver focusTransitionObserver, ShellExecutor shellExecutor, DesktopState desktopState, Supplier supplier, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, mainCoroutineDispatcher, coroutineScope, syncTransactionQueue, displayController, windowDecorTaskResourceLoader, i, rootTaskDisplayAreaOrganizer, transitions, shellTaskOrganizer, toggleResizeDesktopTaskTransitionHandler, returnToDragStartAnimator, desktopUserRepositories, desktopModeEventLogger, focusTransitionObserver, shellExecutor, desktopState, (i2 & 131072) != 0 ? new Supplier() { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        } : supplier);
    }

    @Override // com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.DragEventListener
    public final void onDragStart(int i) {
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.FocusListener
    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public static /* synthetic */ void getDesktopTilingDividerWindowManager$annotations() {
    }
}
