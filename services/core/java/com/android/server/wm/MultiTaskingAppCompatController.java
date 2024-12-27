package com.android.server.wm;

import android.content.ComponentName;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;

import com.android.server.wm.MultiTaskingAppCompatOrientationPolicy.AnonymousClass1;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiTaskingAppCompatController implements IController {
    public final MultiTaskingAppCompatAspectRatioOverrides mAspectRatioOverrides;
    public final MultiTaskingAppCompatAspectRatioPolicy mAspectRatioPolicy;
    public final ActivityTaskManagerService mAtmService;
    public final MultiTaskingAppCompatStatusLogger mMultiTaskingAppCompatStatusLogger;
    public final MultiTaskingAppCompatOrientationOverrides mOrientationOverrides;
    public final MultiTaskingAppCompatOrientationPolicy mOrientationPolicy;
    public List mOverridesObservers;
    public final MultiTaskingAppCompatResizeOverrides mResizeOverrides =
            new MultiTaskingAppCompatResizeOverrides();
    public final MultiTaskingAppCompatSizeCompatModePolicy mSizeCompatModePolicy =
            new MultiTaskingAppCompatSizeCompatModePolicy();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OverridesObserver {
        default void onDumpInTask(Task task, PrintWriter printWriter, String str) {}

        default void onOverridesChangedIfNeededInTask(int i, Task task, String str, boolean z) {}

        default void resetUserOverrides(int i, int i2) {}
    }

    public MultiTaskingAppCompatController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
        registerOverridesObserver(new MultiTaskingAppCompatSizeCompatModePolicy.AnonymousClass1());
        if (CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY) {
            MultiTaskingAppCompatAspectRatioOverrides multiTaskingAppCompatAspectRatioOverrides =
                    new MultiTaskingAppCompatAspectRatioOverrides();
            this.mAspectRatioOverrides = multiTaskingAppCompatAspectRatioOverrides;
            this.mAspectRatioPolicy =
                    new MultiTaskingAppCompatAspectRatioPolicy(
                            multiTaskingAppCompatAspectRatioOverrides);
        }
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
            MultiTaskingAppCompatOrientationOverrides multiTaskingAppCompatOrientationOverrides =
                    new MultiTaskingAppCompatOrientationOverrides();
            this.mOrientationOverrides = multiTaskingAppCompatOrientationOverrides;
            MultiTaskingAppCompatOrientationPolicy multiTaskingAppCompatOrientationPolicy =
                    new MultiTaskingAppCompatOrientationPolicy(
                            multiTaskingAppCompatOrientationOverrides);
            this.mOrientationPolicy = multiTaskingAppCompatOrientationPolicy;
            registerOverridesObserver(multiTaskingAppCompatOrientationPolicy.new AnonymousClass1());
        }
        if (CoreRune.MT_APP_COMPAT_STATUS_LOGGING) {
            this.mMultiTaskingAppCompatStatusLogger = new MultiTaskingAppCompatStatusLogger();
        }
    }

    public static boolean inAllowedWindowingMode(ActivityRecord activityRecord) {
        Task rootTask = activityRecord.getRootTask();
        if (rootTask != null) {
            if (rootTask.inMultiWindowMode()) {
                return rootTask.inPinnedWindowingMode()
                        && !activityRecord.mWaitForEnteringPinnedMode
                        && activityRecord.getRequestedOverrideWindowingMode() == 1;
            }
            if (rootTask.isFullscreenRootForStageTask()) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.wm.IController
    public final void dumpLocked(PrintWriter printWriter) {}

    @Override // com.android.server.wm.IController
    public final void initialize() {}

    public final void onOverridesChanged(
            final int i, final Task task, final String str, final boolean z) {
        synchronized (this) {
            try {
                List list = this.mOverridesObservers;
                if (list == null) {
                    return;
                }
                ((ArrayList) list)
                        .forEach(
                                new Consumer() { // from class:
                                                 // com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        MultiTaskingAppCompatController.OverridesObserver
                                                overridesObserver =
                                                        (MultiTaskingAppCompatController
                                                                        .OverridesObserver)
                                                                obj;
                                        overridesObserver.onOverridesChangedIfNeededInTask(
                                                i, task, str, z);
                                    }
                                });
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerOverridesObserver(OverridesObserver overridesObserver) {
        synchronized (this) {
            try {
                if (this.mOverridesObservers == null) {
                    this.mOverridesObservers = new ArrayList();
                }
                ((ArrayList) this.mOverridesObservers).add(overridesObserver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeTaskWithoutRemoveFromRecents(
            final int i, final String str, final boolean z, final List list) {
        if (list.isEmpty()) {
            return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAtmService.mRootWindowContainer.forAllTasks(
                        new Consumer() { // from class:
                                         // com.android.server.wm.MultiTaskingAppCompatController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ComponentName componentName;
                                MultiTaskingAppCompatController multiTaskingAppCompatController =
                                        MultiTaskingAppCompatController.this;
                                int i2 = i;
                                List list2 = list;
                                boolean z2 = z;
                                String str2 = str;
                                Task task = (Task) obj;
                                multiTaskingAppCompatController.getClass();
                                if (i2 == task.mUserId
                                        && (componentName = task.realActivity) != null
                                        && list2.contains(componentName.getPackageName())) {
                                    ActivityTaskManagerService activityTaskManagerService =
                                            multiTaskingAppCompatController.mAtmService;
                                    activityTaskManagerService.mMultiTaskingAppCompatController
                                            .mSizeCompatModePolicy.getClass();
                                    boolean z3 = true;
                                    task.forAllActivities(
                                            new MultiTaskingAppCompatSizeCompatModePolicy$$ExternalSyntheticLambda0(
                                                    z3, z3));
                                    ActivityTaskSupervisor activityTaskSupervisor =
                                            activityTaskManagerService.mTaskSupervisor;
                                    if (z2) {
                                        str2 =
                                                ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                        .m("MultiTaskingAppCompat:", str2);
                                    }
                                    activityTaskSupervisor.removeTask(
                                            task, true, false, str2, false, 1000, -1);
                                }
                            }
                        });
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
