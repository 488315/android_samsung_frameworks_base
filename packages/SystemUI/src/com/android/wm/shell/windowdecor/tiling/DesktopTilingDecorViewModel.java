package com.android.wm.shell.windowdecor.tiling;

import android.content.Context;
import android.util.SparseArray;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import com.android.wm.shell.desktopmode.ToggleResizeDesktopTaskTransitionHandler;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTilingDecorViewModel implements DisplayChangeController.OnDisplayChangingListener {
    public final CoroutineScope bgScope;
    public final Context context;
    public final DesktopModeEventLogger desktopModeEventLogger;
    public final DesktopState desktopState;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public final FocusTransitionObserver focusTransitionObserver;
    public final MainCoroutineDispatcher mainDispatcher;
    public final ShellExecutor mainExecutor;
    public final ReturnToDragStartAnimator returnToDragStartAnimator;
    public final RootTaskDisplayAreaOrganizer rootTdaOrganizer;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final SyncTransactionQueue syncQueue;
    public final WindowDecorTaskResourceLoader taskResourceLoader;
    public final SparseArray tilingTransitionHandlerByDisplayId = new SparseArray();
    public final ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler;
    public final Transitions transitions;

    public DesktopTilingDecorViewModel(Context context, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, DisplayController displayController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SyncTransactionQueue syncTransactionQueue, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, ToggleResizeDesktopTaskTransitionHandler toggleResizeDesktopTaskTransitionHandler, ReturnToDragStartAnimator returnToDragStartAnimator, DesktopUserRepositories desktopUserRepositories, DesktopModeEventLogger desktopModeEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, FocusTransitionObserver focusTransitionObserver, ShellExecutor shellExecutor, DesktopState desktopState) {
        this.context = context;
        this.mainDispatcher = mainCoroutineDispatcher;
        this.bgScope = coroutineScope;
        this.displayController = displayController;
        this.rootTdaOrganizer = rootTaskDisplayAreaOrganizer;
        this.syncQueue = syncTransactionQueue;
        this.transitions = transitions;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.toggleResizeDesktopTaskTransitionHandler = toggleResizeDesktopTaskTransitionHandler;
        this.returnToDragStartAnimator = returnToDragStartAnimator;
        this.desktopUserRepositories = desktopUserRepositories;
        this.desktopModeEventLogger = desktopModeEventLogger;
        this.taskResourceLoader = windowDecorTaskResourceLoader;
        this.focusTransitionObserver = focusTransitionObserver;
        this.mainExecutor = shellExecutor;
        this.desktopState = desktopState;
        displayController.addDisplayChangingController(this);
        displayController.addDisplayWindowListener(new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel.1
            @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
            public final void onDisplayRemoved(int i) {
                DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) DesktopTilingDecorViewModel.this.tilingTransitionHandlerByDisplayId.get(i);
                if (desktopTilingWindowDecoration == null) {
                    return;
                }
                desktopTilingWindowDecoration.resetTilingSession();
            }
        }, -1);
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        DesktopTilingWindowDecoration desktopTilingWindowDecoration;
        if (i2 % 2 == i3 % 2 || (desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) this.tilingTransitionHandlerByDisplayId.get(i)) == null) {
            return;
        }
        desktopTilingWindowDecoration.resetTilingSession();
    }

    public static /* synthetic */ void getTilingTransitionHandlerByDisplayId$annotations() {
    }
}
