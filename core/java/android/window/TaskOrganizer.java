package android.window;

import android.app.ActivityManager;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.SurfaceControl;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskOrganizer extends WindowOrganizer {
    private final Executor mExecutor;
    private final ITaskOrganizer mInterface;
    private final ITaskOrganizerController mTaskOrganizerController;

    public TaskOrganizer() {
        this(null, null);
    }

    public TaskOrganizer(ITaskOrganizerController taskOrganizerController, Executor executor) {
        this.mInterface = new AnonymousClass1();
        this.mExecutor =
                executor != null ? executor : new PendingIntent$$ExternalSyntheticLambda0();
        this.mTaskOrganizerController =
                taskOrganizerController != null ? taskOrganizerController : getController();
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

    public void addStartingWindow(StartingWindowInfo info) {}

    public void removeStartingWindow(StartingWindowRemovalInfo removalInfo) {}

    public void copySplashScreenView(int taskId) {}

    public void onAppSplashScreenViewRemoved(int taskId) {}

    public void onTaskAppeared(ActivityManager.RunningTaskInfo taskInfo, SurfaceControl leash) {}

    public void onTaskVanished(ActivityManager.RunningTaskInfo taskInfo) {}

    public void onTaskInfoChanged(ActivityManager.RunningTaskInfo taskInfo) {}

    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo) {}

    public void onImeDrawnOnTask(int taskId) {}

    public void onSplitLayoutChangeRequested(
            ActivityManager.RunningTaskInfo taskInfo, Bundle infoBundle) {}

    public void requestAffordanceAnim(ActivityManager.RunningTaskInfo taskInfo, int gestureFrom) {}

    public void onImmersiveModeChanged(int taskId, boolean immersive) {}

    public void createRootTask(
            int displayId,
            int windowingMode,
            IBinder launchCookie,
            boolean removeWithTaskOrganizer) {
        try {
            this.mTaskOrganizerController.createRootTask(
                    displayId, windowingMode, launchCookie, removeWithTaskOrganizer);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void createRootTask(int displayId, int windowingMode, IBinder launchCookie) {
        createRootTask(displayId, windowingMode, launchCookie, false);
    }

    public void createStageRootTask(
            int displayId, int windowingMode, int stageType, IBinder launchCookie) {
        try {
            this.mTaskOrganizerController.createStageRootTask(
                    displayId, windowingMode, stageType, launchCookie);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean deleteRootTask(WindowContainerToken task) {
        try {
            return this.mTaskOrganizerController.deleteRootTask(task);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ActivityManager.RunningTaskInfo> getChildTasks(
            WindowContainerToken parent, int[] activityTypes) {
        try {
            return this.mTaskOrganizerController.getChildTasks(parent, activityTypes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ActivityManager.RunningTaskInfo> getRootTasks(int displayId, int[] activityTypes) {
        try {
            return this.mTaskOrganizerController.getRootTasks(displayId, activityTypes);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public WindowContainerToken getImeTarget(int display) {
        try {
            return this.mTaskOrganizerController.getImeTarget(display);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setInterceptBackPressedOnTaskRoot(
            WindowContainerToken task, boolean interceptBackPressed) {
        try {
            this.mTaskOrganizerController.setInterceptBackPressedOnTaskRoot(
                    task, interceptBackPressed);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restartTaskTopActivityProcessIfVisible(WindowContainerToken task) {
        try {
            this.mTaskOrganizerController.restartTaskTopActivityProcessIfVisible(task);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getFreeformTaskOpacity(int taskId) {
        try {
            return this.mTaskOrganizerController.getFreeformTaskOpacity(taskId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 1.0f;
        }
    }

    public void setFreeformTaskOpacity(int taskId, float alpha) {
        try {
            this.mTaskOrganizerController.setFreeformTaskOpacity(taskId, alpha);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean togglePinTaskState(int taskId) {
        try {
            return this.mTaskOrganizerController.togglePinTaskState(taskId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPinStateChangeable(int taskId) {
        try {
            return this.mTaskOrganizerController.isPinStateChangeable(taskId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setFreeformTaskSurfaceOverlappedWithNavi(
            WindowContainerToken task, boolean overlap) {
        try {
            this.mTaskOrganizerController.setFreeformTaskSurfaceOverlappedWithNavi(task, overlap);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetStashedFreeform(int taskId, boolean anim) {}

    public void preloadSplashScreenAppIcon(ActivityInfo info, int userId, Configuration config) {}

    public Executor getExecutor() {
        return this.mExecutor;
    }

    /* renamed from: android.window.TaskOrganizer$1, reason: invalid class name */
    class AnonymousClass1 extends ITaskOrganizer.Stub {
        AnonymousClass1() {}

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addStartingWindow$0(StartingWindowInfo windowInfo) {
            TaskOrganizer.this.addStartingWindow(windowInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void addStartingWindow(final StartingWindowInfo windowInfo) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$addStartingWindow$0(
                                    windowInfo);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeStartingWindow$1(
                StartingWindowRemovalInfo removalInfo) {
            TaskOrganizer.this.removeStartingWindow(removalInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void removeStartingWindow(final StartingWindowRemovalInfo removalInfo) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$removeStartingWindow$1(
                                    removalInfo);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copySplashScreenView$2(int taskId) {
            TaskOrganizer.this.copySplashScreenView(taskId);
        }

        @Override // android.window.ITaskOrganizer
        public void copySplashScreenView(final int taskId) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$copySplashScreenView$2(
                                    taskId);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppSplashScreenViewRemoved$3(int taskId) {
            TaskOrganizer.this.onAppSplashScreenViewRemoved(taskId);
        }

        @Override // android.window.ITaskOrganizer
        public void onAppSplashScreenViewRemoved(final int taskId) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this
                                    .lambda$onAppSplashScreenViewRemoved$3(taskId);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskAppeared$4(
                ActivityManager.RunningTaskInfo taskInfo, SurfaceControl leash) {
            TaskOrganizer.this.onTaskAppeared(taskInfo, leash);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskAppeared(
                final ActivityManager.RunningTaskInfo taskInfo, final SurfaceControl leash) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onTaskAppeared$4(
                                    taskInfo, leash);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskVanished$5(
                ActivityManager.RunningTaskInfo taskInfo) {
            TaskOrganizer.this.onTaskVanished(taskInfo);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskVanished(final ActivityManager.RunningTaskInfo taskInfo) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onTaskVanished$5(taskInfo);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTaskInfoChanged$6(
                ActivityManager.RunningTaskInfo info) {
            TaskOrganizer.this.onTaskInfoChanged(info);
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskInfoChanged(final ActivityManager.RunningTaskInfo info) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onTaskInfoChanged$6(info);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackPressedOnTaskRoot$7(
                ActivityManager.RunningTaskInfo info) {
            TaskOrganizer.this.onBackPressedOnTaskRoot(info);
        }

        @Override // android.window.ITaskOrganizer
        public void onBackPressedOnTaskRoot(final ActivityManager.RunningTaskInfo info) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onBackPressedOnTaskRoot$7(
                                    info);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImeDrawnOnTask$8(int taskId) {
            TaskOrganizer.this.onImeDrawnOnTask(taskId);
        }

        @Override // android.window.ITaskOrganizer
        public void onImeDrawnOnTask(final int taskId) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onImeDrawnOnTask$8(taskId);
                        }
                    });
        }

        @Override // android.window.ITaskOrganizer
        public void onSplitLayoutChangeRequested(
                final ActivityManager.RunningTaskInfo taskInfo, final Bundle infoBundle) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this
                                    .lambda$onSplitLayoutChangeRequested$9(taskInfo, infoBundle);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSplitLayoutChangeRequested$9(
                ActivityManager.RunningTaskInfo taskInfo, Bundle infoBundle) {
            TaskOrganizer.this.onSplitLayoutChangeRequested(taskInfo, infoBundle);
        }

        @Override // android.window.ITaskOrganizer
        public void onImmersiveModeChanged(final int taskId, final boolean immersive) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$onImmersiveModeChanged$10(
                                    taskId, immersive);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImmersiveModeChanged$10(
                int taskId, boolean immersive) {
            TaskOrganizer.this.onImmersiveModeChanged(taskId, immersive);
        }

        @Override // android.window.ITaskOrganizer
        public void resetStashedFreeform(final int taskId, final boolean anim) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$resetStashedFreeform$11(
                                    taskId, anim);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resetStashedFreeform$11(int taskId, boolean anim) {
            TaskOrganizer.this.resetStashedFreeform(taskId, anim);
        }

        @Override // android.window.ITaskOrganizer
        public void requestAffordanceAnim(
                final ActivityManager.RunningTaskInfo taskInfo, final int gestureFrom) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$requestAffordanceAnim$12(
                                    taskInfo, gestureFrom);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestAffordanceAnim$12(
                ActivityManager.RunningTaskInfo taskInfo, int gestureFrom) {
            TaskOrganizer.this.requestAffordanceAnim(taskInfo, gestureFrom);
        }

        @Override // android.window.ITaskOrganizer
        public void preloadSplashScreenAppIcon(
                final ActivityInfo info, final int userId, final Configuration config) {
            TaskOrganizer.this.mExecutor.execute(
                    new Runnable() { // from class:
                                     // android.window.TaskOrganizer$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            TaskOrganizer.AnonymousClass1.this.lambda$preloadSplashScreenAppIcon$13(
                                    info, userId, config);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$preloadSplashScreenAppIcon$13(
                ActivityInfo info, int userId, Configuration config) {
            TaskOrganizer.this.preloadSplashScreenAppIcon(info, userId, config);
        }
    }

    private ITaskOrganizerController getController() {
        try {
            return getWindowOrganizerController().getTaskOrganizerController();
        } catch (RemoteException e) {
            return null;
        }
    }
}
