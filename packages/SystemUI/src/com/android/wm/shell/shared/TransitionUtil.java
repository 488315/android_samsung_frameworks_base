package com.android.wm.shell.shared;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.SparseBooleanArray;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class TransitionUtil {

    public class LeafTaskFilter implements Predicate {
        public final SparseBooleanArray mChildTaskTargets = new SparseBooleanArray();

        @Override // java.util.function.Predicate
        public final boolean test(TransitionInfo.Change change) {
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo == null) {
                return false;
            }
            boolean z = this.mChildTaskTargets.get(taskInfo.taskId);
            if (taskInfo.hasParentTask()) {
                this.mChildTaskTargets.put(taskInfo.parentTaskId, true);
            }
            return !z;
        }
    }

    public static SurfaceControl createLeash(TransitionInfo transitionInfo, TransitionInfo.Change change, int i, SurfaceControl.Transaction transaction) {
        if (change.getParent() != null && (change.getFlags() & 2) != 0 && (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || ((transitionInfo.getType() == 11 && !transitionInfo.isKeyguardGoingAway()) || (transitionInfo.getFlags() & 47360) == 0))) {
            return change.getLeash();
        }
        int iRootIndexFor = rootIndexFor(change, transitionInfo);
        SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName(change.getLeash().toString() + "_transition-leash").setContainerLayer().setHidden(false).setParent(transitionInfo.getRoot(iRootIndexFor).getLeash()).build();
        int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, i);
        boolean zIsOpeningType = isOpeningType(transitionInfo.getType());
        int size = transitionInfo.getChanges().size();
        int mode = change.getMode();
        transaction.reparent(surfaceControlBuild, transitionInfo.getRoot(rootIndexFor(change, transitionInfo)).getLeash());
        Rect endAbsBounds = mode == 1 ? change.getEndAbsBounds() : change.getStartAbsBounds();
        transaction.setPosition(surfaceControlBuild, endAbsBounds.left - transitionInfo.getRoot(r5).getOffset().x, endAbsBounds.top - transitionInfo.getRoot(r5).getOffset().y);
        if (isDividerBar(change)) {
            if (isOpeningType(mode)) {
                transaction.setAlpha(surfaceControlBuild, 0.0f);
            }
            transaction.setPosition(surfaceControlBuild, 0.0f, 0.0f);
            transaction.setLayer(surfaceControlBuild, Integer.MAX_VALUE);
        } else {
            if (isDimLayer(change)) {
                transaction.setPosition(surfaceControlBuild, 0.0f, 0.0f);
                transaction.setCrop(surfaceControlBuild, change.getEndAbsBounds());
            }
            if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(change)) {
                transaction.setAlpha(surfaceControlBuild, 0.0f);
                transaction.setPosition(surfaceControlBuild, 0.0f, 0.0f);
            } else {
                if ((change.getFlags2() & 1) != 0) {
                    transaction.setAlpha(surfaceControlBuild, 0.0f);
                }
                if ((change.getFlags() & 2) != 0) {
                    if (mode == 1 || mode == 3) {
                        transaction.setLayer(surfaceControlBuild, (transitionInfo.getChanges().size() + (-size)) - iM);
                    } else {
                        transaction.setLayer(surfaceControlBuild, (-size) - iM);
                    }
                } else if (isOpeningType(mode)) {
                    if (zIsOpeningType) {
                        transaction.setLayer(surfaceControlBuild, (transitionInfo.getChanges().size() + size) - iM);
                        if ((change.getFlags() & 8) == 0) {
                            transaction.setAlpha(surfaceControlBuild, 0.0f);
                        }
                    } else {
                        transaction.setLayer(surfaceControlBuild, size - iM);
                        if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && isHomeTask(change)) {
                            int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                            while (true) {
                                if (iM2 < 0) {
                                    transaction.setAlpha(surfaceControlBuild, 0.0f);
                                    break;
                                }
                                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                                if (isWallpaper(change2) && isOpenOrCloseMode(change2.getMode())) {
                                    break;
                                }
                                iM2--;
                            }
                        }
                    }
                } else if (isClosingType(mode) && zIsOpeningType) {
                    transaction.setLayer(surfaceControlBuild, size - iM);
                } else {
                    transaction.setLayer(surfaceControlBuild, (transitionInfo.getChanges().size() + size) - iM);
                }
            }
        }
        transaction.reparent(change.getLeash(), surfaceControlBuild);
        if (!isDimLayer(change)) {
            transaction.setAlpha(change.getLeash(), 1.0f);
        }
        if (!isDividerBar(change)) {
            transaction.setPosition(change.getLeash(), 0.0f, 0.0f);
        }
        transaction.setLayer(change.getLeash(), 0);
        transaction.show(change.getLeash());
        return surfaceControlBuild;
    }

    public static ArrayList getMergeableTasks(TransitionInfo transitionInfo) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (change.getTaskInfo() != null && change.getMode() != 6) {
                arrayList.add(change);
            }
        }
        if (!arrayList.isEmpty()) {
            arrayList.sort(Comparator.comparingInt(new TransitionUtil$$ExternalSyntheticLambda0()));
        }
        return arrayList;
    }

    public static boolean hasDisplayChange(TransitionInfo transitionInfo) {
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            if (change.getMode() == 6 && change.hasFlags(32)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isClosingMode(int i) {
        return i == 2 || i == 4;
    }

    public static boolean isClosingType(int i) {
        return i == 2 || i == 4;
    }

    public static boolean isDimLayer(TransitionInfo.Change change) {
        return isNonApp(change) && change.hasFlags(33554432);
    }

    public static boolean isDividerBar(TransitionInfo.Change change) {
        return isNonApp(change) && change.hasFlags(16777216);
    }

    public static boolean isHomeOrRecents(TransitionInfo.Change change) {
        if (change.getTaskInfo() != null) {
            return change.getTaskInfo().getActivityType() == 2 || change.getTaskInfo().getActivityType() == 3;
        }
        return false;
    }

    public static boolean isHomeTask(TransitionInfo.Change change) {
        return change.getTaskInfo() != null && change.getTaskInfo().getActivityType() == 2;
    }

    public static boolean isNonApp(TransitionInfo.Change change) {
        return (change.getTaskInfo() != null || change.hasFlags(2) || change.hasFlags(512)) ? false : true;
    }

    public static boolean isOpenOrCloseMode(int i) {
        return isOpeningMode(i) || isClosingMode(i);
    }

    public static boolean isOpeningMode(int i) {
        return i == 1 || i == 3;
    }

    public static boolean isOpeningType(int i) {
        return i == 1 || i == 3 || i == 7 || i == 13;
    }

    public static boolean isOrderOnly(TransitionInfo.Change change) {
        if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && change.getResumedAffordance()) {
            return false;
        }
        return !(change.getConfiguration().windowConfiguration.getWindowingMode() == 5 && change.getConfiguration().windowConfiguration.isAlwaysOnTop()) && change.getMode() == 6 && (change.getFlags() & 1048576) != 0 && change.getStartAbsBounds().equals(change.getEndAbsBounds()) && (change.getLastParent() == null || change.getLastParent().equals(change.getParent()));
    }

    public static boolean isTransientLaunchOverlay(TransitionInfo.Change change) {
        return isNonApp(change) && change.hasFlags(268435456);
    }

    public static boolean isWallpaper(TransitionInfo.Change change) {
        return change.getTaskInfo() == null && change.hasFlags(2) && !change.hasFlags(512);
    }

    public static int newModeToLegacyMode(int i) {
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            if (i == 3) {
                return 0;
            }
            if (i != 4) {
                return 2;
            }
        }
        return 1;
    }

    public static RemoteAnimationTarget newSyntheticTarget(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, int i) {
        WindowConfiguration windowConfiguration;
        int i2;
        boolean z = true;
        if (runningTaskInfo != null) {
            i2 = runningTaskInfo.taskId;
            z = true ^ runningTaskInfo.isRunning;
            windowConfiguration = runningTaskInfo.configuration.windowConfiguration;
        } else {
            windowConfiguration = new WindowConfiguration();
            i2 = -1;
        }
        boolean z2 = z;
        WindowConfiguration windowConfiguration2 = windowConfiguration;
        Rect bounds = windowConfiguration2.getBounds();
        return new RemoteAnimationTarget(i2, newModeToLegacyMode(i), surfaceControl, true, (Rect) null, new Rect(0, 0, 0, 0), 0, (Point) null, bounds, bounds, windowConfiguration2, z2, (SurfaceControl) null, bounds, runningTaskInfo, false, -1);
    }

    public static RemoteAnimationTarget newTarget(TransitionInfo.Change change, int i, boolean z, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, ArrayMap arrayMap) {
        SurfaceControl surfaceControlCreateLeash = createLeash(transitionInfo, change, i, transaction);
        if (arrayMap != null) {
            arrayMap.put(change.getLeash(), surfaceControlCreateLeash);
        }
        return newTarget(change, i, surfaceControlCreateLeash, z);
    }

    public static int rootIndexFor(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        int iFindRootIndex = transitionInfo.findRootIndex(change.getEndDisplayId());
        if (iFindRootIndex >= 0) {
            return iFindRootIndex;
        }
        int iFindRootIndex2 = transitionInfo.findRootIndex(change.getStartDisplayId());
        if (iFindRootIndex2 >= 0) {
            return iFindRootIndex2;
        }
        return 0;
    }

    public static RemoteAnimationTarget newTarget(TransitionInfo.Change change, int i, SurfaceControl surfaceControl, boolean z) {
        boolean z2;
        int i2;
        WindowConfiguration windowConfiguration;
        if (isDividerBar(change)) {
            return new RemoteAnimationTarget(-1, newModeToLegacyMode(change.getMode()), surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
        }
        if (isDimLayer(change)) {
            return new RemoteAnimationTarget(-1, newModeToLegacyMode(change.getMode()), surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 3000);
        }
        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(change)) {
            return new RemoteAnimationTarget(-1, newModeToLegacyMode(change.getMode()), surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2632);
        }
        Rect rect = new Rect(0, 0, 0, 0);
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (taskInfo != null) {
            int i3 = taskInfo.taskId;
            boolean z3 = !taskInfo.isRunning;
            WindowConfiguration windowConfiguration2 = taskInfo.configuration.windowConfiguration;
            rect.set(change.getInsetsForRecentsTransition());
            z2 = z3;
            windowConfiguration = windowConfiguration2;
            i2 = i3;
        } else {
            z2 = true;
            i2 = -1;
            windowConfiguration = new WindowConfiguration();
        }
        Rect rect2 = new Rect(change.getEndAbsBounds());
        rect2.offsetTo(change.getEndRelOffset().x, change.getEndRelOffset().y);
        RemoteAnimationTarget remoteAnimationTarget = new RemoteAnimationTarget(i2, newModeToLegacyMode(change.getMode()), surfaceControl, z || (change.getFlags() & 4) != 0, (Rect) null, rect, i, (Point) null, rect2, new Rect(change.getEndAbsBounds()), windowConfiguration, z2, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), taskInfo, change.isAllowEnterPip(), -1);
        remoteAnimationTarget.setWillShowImeOnTarget((change.getFlags() & 2048) != 0);
        remoteAnimationTarget.setRotationChange(change.getEndRotation() - change.getStartRotation());
        remoteAnimationTarget.backgroundColor = change.getBackgroundColor();
        return remoteAnimationTarget;
    }
}
