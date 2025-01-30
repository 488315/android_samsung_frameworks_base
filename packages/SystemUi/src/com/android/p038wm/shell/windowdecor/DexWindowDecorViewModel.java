package com.android.p038wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowlessWindowManager;
import android.window.TransitionInfo;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.DisplayController;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.p038wm.shell.windowdecor.WindowDecoration;
import com.android.systemui.R;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DexWindowDecorViewModel implements WindowDecorViewModel {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final Choreographer mMainChoreographer;
    public final Handler mMainHandler;
    public final SyncTransactionQueue mSyncQueue;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final SparseArray mWindowDecorByTaskId = new SparseArray();
    public final Map mWindowDecorToShowDialog = new ArrayMap();

    public DexWindowDecorViewModel(Context context, Handler handler, Choreographer choreographer, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainChoreographer = choreographer;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mSyncQueue = syncTransactionQueue;
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DexWindowDecoration dexWindowDecoration = (DexWindowDecoration) this.mWindowDecorByTaskId.removeReturnOld(runningTaskInfo.taskId);
        if (dexWindowDecoration == null) {
            return;
        }
        dexWindowDecoration.close();
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z;
        DexWindowDecoration dexWindowDecoration;
        int i = runningTaskInfo.taskId;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        synchronized (shellTaskOrganizer.mLock) {
            z = shellTaskOrganizer.mDisplayChangingTasks.get(i) != null;
        }
        if (!z || (dexWindowDecoration = (DexWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) == null || dexWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().equals(runningTaskInfo.configuration.windowConfiguration.getBounds())) {
            return;
        }
        dexWindowDecoration.relayout(runningTaskInfo);
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        if (!(!runningTaskInfo.isResizeable)) {
            return false;
        }
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        DexWindowDecoration dexWindowDecoration = (DexWindowDecoration) sparseArray.get(runningTaskInfo.taskId);
        if (dexWindowDecoration != null) {
            dexWindowDecoration.close();
        }
        sparseArray.put(runningTaskInfo.taskId, new DexWindowDecoration(this.mContext, this.mDisplayController, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainChoreographer, this.mSyncQueue));
        return true;
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionFinished(IBinder iBinder) {
        final DexWindowDecoration dexWindowDecoration = (DexWindowDecoration) ((ArrayMap) this.mWindowDecorToShowDialog).remove(iBinder);
        if (dexWindowDecoration != null) {
            WindowDecoration.AdditionalWindow additionalWindow = dexWindowDecoration.mRestart;
            if (additionalWindow == null || additionalWindow.mWindowViewHost == null) {
                final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                dexWindowDecoration.mContext.getMainThreadHandler().post(new Runnable() { // from class: com.android.wm.shell.windowdecor.DexWindowDecoration$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DexWindowDecoration dexWindowDecoration2 = DexWindowDecoration.this;
                        SurfaceControl.Transaction transaction2 = transaction;
                        CountDownLatch countDownLatch2 = countDownLatch;
                        dexWindowDecoration2.mSnackbarContext = new ContextThemeWrapper(new ContextWrapper(dexWindowDecoration2.mDecorWindowContext.createWindowContext(dexWindowDecoration2.mDisplay, 2038, null)).createConfigurationContext(dexWindowDecoration2.mTaskInfo.getConfiguration()), 2132018368);
                        SurfaceControl build = ((SurfaceControl.Builder) dexWindowDecoration2.mSurfaceControlBuilderSupplier.get()).setName("Restart snackbar of Task=" + dexWindowDecoration2.mTaskInfo.taskId).setContainerLayer().setParent(dexWindowDecoration2.mTaskSurface).build();
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dexWindowDecoration2.mTaskInfo.getConfiguration().windowConfiguration.getBounds().width(), dexWindowDecoration2.mTaskInfo.getConfiguration().windowConfiguration.getBounds().height(), 2038, 8, -2);
                        dexWindowDecoration2.mLayoutParam = layoutParams;
                        layoutParams.setTitle("Additional window of Task=" + dexWindowDecoration2.mTaskInfo.taskId + " (Restart snackbar)");
                        dexWindowDecoration2.mLayoutParam.setTrustedOverlay();
                        WindowlessWindowManager windowlessWindowManager = new WindowlessWindowManager(dexWindowDecoration2.mTaskInfo.configuration, build, (IBinder) null);
                        WindowDecoration.SurfaceControlViewHostFactory surfaceControlViewHostFactory = dexWindowDecoration2.mSurfaceControlViewHostFactory;
                        Context context = dexWindowDecoration2.mSnackbarContext;
                        Display display = dexWindowDecoration2.mDisplay;
                        surfaceControlViewHostFactory.getClass();
                        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display, windowlessWindowManager, "WindowDecoration");
                        View inflate = LayoutInflater.from(dexWindowDecoration2.mSnackbarContext).inflate(R.layout.drag_and_split_help_tip_view, (ViewGroup) null);
                        dexWindowDecoration2.mLayoutParam.setSurfaceInsets(inflate, true, false);
                        surfaceControlViewHost.setView(inflate, dexWindowDecoration2.mLayoutParam);
                        transaction2.setLayer(build, 40000);
                        transaction2.setPosition(build, 0.0f, 0.0f).show(build);
                        dexWindowDecoration2.mRestart = new WindowDecoration.AdditionalWindow(build, surfaceControlViewHost, dexWindowDecoration2.mSurfaceControlTransactionSupplier);
                        countDownLatch2.countDown();
                    }
                });
                try {
                    countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dexWindowDecoration.mSyncQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.windowdecor.DexWindowDecoration$$ExternalSyntheticLambda2
                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                    public final void runWithTransaction(SurfaceControl.Transaction transaction2) {
                        SurfaceControl.Transaction transaction3 = transaction;
                        transaction2.merge(transaction3);
                        transaction3.close();
                    }
                });
                if (dexWindowDecoration.mIsShowingRestart) {
                    return;
                }
                final View view = dexWindowDecoration.mRestart.mWindowViewHost.getView();
                if (view.isAttachedToWindow()) {
                    dexWindowDecoration.showRestartSnackbar(view);
                } else {
                    view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.windowdecor.DexWindowDecoration.1
                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewAttachedToWindow(View view2) {
                            DexWindowDecoration dexWindowDecoration2 = DexWindowDecoration.this;
                            View view3 = view;
                            int i = DexWindowDecoration.$r8$clinit;
                            dexWindowDecoration2.showRestartSnackbar(view3);
                        }

                        @Override // android.view.View.OnAttachStateChangeListener
                        public final void onViewDetachedFromWindow(View view2) {
                        }
                    });
                }
            }
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, TransitionInfo.Change change) {
        boolean z;
        DexWindowDecoration dexWindowDecoration = (DexWindowDecoration) this.mWindowDecorByTaskId.get(change.getTaskInfo().taskId);
        if (dexWindowDecoration == null) {
            return;
        }
        if (change.getMode() == 3) {
            int i = change.getTaskInfo().taskId;
            ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
            synchronized (shellTaskOrganizer.mLock) {
                z = shellTaskOrganizer.mDisplayChangingTasks.get(i) != null;
            }
            if (z) {
                dexWindowDecoration.mTaskInfo = change.getTaskInfo();
                ((ArrayMap) this.mWindowDecorToShowDialog).put(iBinder, dexWindowDecoration);
                return;
            }
        }
        if (change.getMode() == 4) {
            dexWindowDecoration.mContext.getMainThreadHandler().post(new DexWindowDecoration$$ExternalSyntheticLambda0(dexWindowDecoration, 0));
        }
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void setFreeformTaskTransitionStarter(FreeformTaskTransitionHandler freeformTaskTransitionHandler) {
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
    }

    @Override // com.android.p038wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
    }
}
