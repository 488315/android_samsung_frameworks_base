package android.window;

import android.app.ActivityManager;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;
import android.window.ITaskOrganizer;
import android.window.TaskOrganizer;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class TaskOrganizer extends WindowOrganizer {
    private final Executor mExecutor;
    private final ITaskOrganizer mInterface;
    private final ITaskOrganizerController mTaskOrganizerController;

    public void addStartingWindow(StartingWindowInfo startingWindowInfo) {
    }

    public void copySplashScreenView(int i) {
    }

    public void minimizeAllDesktopTasks(int i) {
    }

    public void onAppSplashScreenViewRemoved(int i) {
    }

    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void onImeDrawnOnTask(int i) {
    }

    public void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) {
    }

    public void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
    }

    public void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
    }

    public void preloadSplashScreenAppIcon(ActivityInfo activityInfo, int i, Configuration configuration) {
    }

    public void removeStartingWindow(StartingWindowRemovalInfo startingWindowRemovalInfo) {
    }

    public void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
    }

    public void resetStashedFreeform(int i, boolean z) {
    }

    public TaskOrganizer() {
        this(null, null);
    }

    public TaskOrganizer(ITaskOrganizerController iTaskOrganizerController, Executor executor) {
        this.mInterface = new AnonymousClass1();
        this.mExecutor = executor == null ? new PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mTaskOrganizerController = iTaskOrganizerController == null ? getController() : iTaskOrganizerController;
    }

    public List<TaskAppearedInfo> registerOrganizer() {
        try {
            return this.mTaskOrganizerController.registerTaskOrganizer(this.mInterface).getList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterOrganizer() {
        try {
            this.mTaskOrganizerController.unregisterTaskOrganizer(this.mInterface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createRootTask(int i, int i2, IBinder iBinder, boolean z, boolean z2) {
        try {
            this.mTaskOrganizerController.createRootTask(i, i2, iBinder, z, z2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createRootTask(int i, int i2, IBinder iBinder) {
        createRootTask(i, i2, iBinder, false, false);
    }

    public void createStageRootTask(int i, int i2, int i3, IBinder iBinder) {
        try {
            this.mTaskOrganizerController.createStageRootTask(i, i2, i3, iBinder);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createDeskRootTask(int i, int i2, int i3, IBinder iBinder, boolean z, boolean z2) {
        try {
            this.mTaskOrganizerController.createDeskRootTask(i, i2, i3, iBinder, z, z2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean deleteRootTask(WindowContainerToken windowContainerToken) {
        try {
            return this.mTaskOrganizerController.deleteRootTask(windowContainerToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ActivityManager.RunningTaskInfo> getChildTasks(WindowContainerToken windowContainerToken, int[] iArr) {
        try {
            return this.mTaskOrganizerController.getChildTasks(windowContainerToken, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) {
        try {
            return this.mTaskOrganizerController.getRootTasks(i, iArr);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WindowContainerToken getImeTarget(int i) {
        try {
            return this.mTaskOrganizerController.getImeTarget(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z) {
        try {
            this.mTaskOrganizerController.setInterceptBackPressedOnTaskRoot(windowContainerToken, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken) {
        try {
            this.mTaskOrganizerController.restartTaskTopActivityProcessIfVisible(windowContainerToken);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken windowContainerToken, boolean z) {
        try {
            this.mTaskOrganizerController.setFreeformTaskSurfaceOverlappedWithNavi(windowContainerToken, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Executor getExecutor() {
        return this.mExecutor;
    }

    /* renamed from: android.window.TaskOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends ITaskOrganizer.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addStartingWindow$0(StartingWindowInfo startingWindowInfo) {
            TaskOrganizer.this.addStartingWindow(startingWindowInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void addStartingWindow(final StartingWindowInfo startingWindowInfo) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$addStartingWindow$0(startingWindowInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeStartingWindow$1(StartingWindowRemovalInfo startingWindowRemovalInfo) {
            TaskOrganizer.this.removeStartingWindow(startingWindowRemovalInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void removeStartingWindow(final StartingWindowRemovalInfo startingWindowRemovalInfo) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$removeStartingWindow$1(startingWindowRemovalInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copySplashScreenView$2(int i) {
            TaskOrganizer.this.copySplashScreenView(i);
        }

        @Override // android.window.ITaskOrganizer
        public void copySplashScreenView(final int i) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$copySplashScreenView$2(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppSplashScreenViewRemoved$3(int i) {
            TaskOrganizer.this.onAppSplashScreenViewRemoved(i);
        }

        @Override // android.window.ITaskOrganizer
        public void onAppSplashScreenViewRemoved(final int i) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onAppSplashScreenViewRemoved$3(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskAppeared$4(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
            TaskOrganizer.this.onTaskAppeared(runningTaskInfo, surfaceControl);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskAppeared(final ActivityManager.RunningTaskInfo runningTaskInfo, final SurfaceControl surfaceControl) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onTaskAppeared$4(runningTaskInfo, surfaceControl);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskVanished$5(ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.onTaskVanished(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskVanished(final ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onTaskVanished$5(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskInfoChanged$6(ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.onTaskInfoChanged(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskInfoChanged(final ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onTaskInfoChanged$6(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackPressedOnTaskRoot$7(ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.onBackPressedOnTaskRoot(runningTaskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onBackPressedOnTaskRoot(final ActivityManager.RunningTaskInfo runningTaskInfo) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onBackPressedOnTaskRoot$7(runningTaskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImeDrawnOnTask$8(int i) {
            TaskOrganizer.this.onImeDrawnOnTask(i);
        }

        @Override // android.window.ITaskOrganizer
        public void onImeDrawnOnTask(final int i) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onImeDrawnOnTask$8(i);
                }
            });
        }

        @Override // android.window.ITaskOrganizer
        public void onSplitLayoutChangeRequested(final ActivityManager.RunningTaskInfo runningTaskInfo, final Bundle bundle) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$onSplitLayoutChangeRequested$9(runningTaskInfo, bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSplitLayoutChangeRequested$9(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) {
            TaskOrganizer.this.onSplitLayoutChangeRequested(runningTaskInfo, bundle);
        }

        @Override // android.window.ITaskOrganizer
        public void resetStashedFreeform(final int i, final boolean z) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$resetStashedFreeform$10(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resetStashedFreeform$10(int i, boolean z) {
            TaskOrganizer.this.resetStashedFreeform(i, z);
        }

        @Override // android.window.ITaskOrganizer
        public void requestAffordanceAnim(final ActivityManager.RunningTaskInfo runningTaskInfo, final int i) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$requestAffordanceAnim$11(runningTaskInfo, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestAffordanceAnim$11(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
            TaskOrganizer.this.requestAffordanceAnim(runningTaskInfo, i);
        }

        @Override // android.window.ITaskOrganizer
        public void preloadSplashScreenAppIcon(final ActivityInfo activityInfo, final int i, final Configuration configuration) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$preloadSplashScreenAppIcon$12(activityInfo, i, configuration);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$preloadSplashScreenAppIcon$12(ActivityInfo activityInfo, int i, Configuration configuration) {
            TaskOrganizer.this.preloadSplashScreenAppIcon(activityInfo, i, configuration);
        }

        @Override // android.window.ITaskOrganizer
        public void minimizeAllDesktopTasks(final int i) {
            TaskOrganizer.this.mExecutor.execute(new Runnable() { // from class: android.window.TaskOrganizer$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    TaskOrganizer.AnonymousClass1.this.lambda$minimizeAllDesktopTasks$13(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$minimizeAllDesktopTasks$13(int i) {
            TaskOrganizer.this.minimizeAllDesktopTasks(i);
        }
    }

    private ITaskOrganizerController getController() {
        try {
            return getWindowOrganizerController().getTaskOrganizerController();
        } catch (RemoteException unused) {
            return null;
        }
    }
}
