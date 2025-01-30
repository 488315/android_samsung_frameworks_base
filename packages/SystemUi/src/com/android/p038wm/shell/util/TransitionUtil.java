package com.android.p038wm.shell.util;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.SparseBooleanArray;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TransitionUtil {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LeafTaskFilter implements Predicate {
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
        if (change.getParent() != null && (change.getFlags() & 2) != 0 && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || (transitionInfo.getFlags() & 14592) == 0)) {
            return change.getLeash();
        }
        int rootIndexFor = rootIndexFor(change, transitionInfo);
        SurfaceControl build = new SurfaceControl.Builder().setName(change.getLeash().toString() + "_transition-leash").setContainerLayer().setHidden(false).setParent(transitionInfo.getRoot(rootIndexFor).getLeash()).build();
        int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, i);
        boolean isOpeningType = isOpeningType(transitionInfo.getType());
        int size = transitionInfo.getChanges().size();
        int mode = change.getMode();
        transaction.reparent(build, transitionInfo.getRoot(rootIndexFor(change, transitionInfo)).getLeash());
        Rect endAbsBounds = mode == 1 ? change.getEndAbsBounds() : change.getStartAbsBounds();
        transaction.setPosition(build, endAbsBounds.left - transitionInfo.getRoot(r5).getOffset().x, endAbsBounds.top - transitionInfo.getRoot(r5).getOffset().y);
        if (isDividerBar(change)) {
            if (isOpeningType(mode)) {
                transaction.setAlpha(build, 0.0f);
            }
            transaction.setPosition(build, 0.0f, 0.0f);
            transaction.setLayer(build, 0);
        } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(change)) {
            transaction.setAlpha(build, 0.0f);
            transaction.setPosition(build, 0.0f, 0.0f);
        } else if ((change.getFlags() & 2) != 0) {
            if (mode == 1 || mode == 3) {
                transaction.setLayer(build, (transitionInfo.getChanges().size() + (-size)) - m136m);
            } else {
                transaction.setLayer(build, (-size) - m136m);
            }
        } else if (isOpeningType(mode)) {
            if (isOpeningType) {
                transaction.setLayer(build, (transitionInfo.getChanges().size() + size) - m136m);
                if ((change.getFlags() & 8) == 0) {
                    transaction.setAlpha(build, 0.0f);
                }
            } else {
                transaction.setLayer(build, size - m136m);
            }
        } else if (!isClosingType(mode)) {
            transaction.setLayer(build, (transitionInfo.getChanges().size() + size) - m136m);
        } else if (isOpeningType) {
            transaction.setLayer(build, size - m136m);
        } else {
            transaction.setLayer(build, (transitionInfo.getChanges().size() + size) - m136m);
        }
        transaction.reparent(change.getLeash(), build);
        transaction.setAlpha(change.getLeash(), 1.0f);
        transaction.show(change.getLeash());
        if (!isDividerBar(change)) {
            transaction.setPosition(change.getLeash(), 0.0f, 0.0f);
        }
        transaction.setLayer(change.getLeash(), 0);
        return build;
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
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            if (change.getMode() == 6 && change.hasFlags(32)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasDisplayRotationChange(TransitionInfo transitionInfo) {
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (change.hasFlags(32) && change.getStartRotation() != change.getEndRotation()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isClosingType(int i) {
        return i == 2 || i == 4;
    }

    public static boolean isDividerBar(TransitionInfo.Change change) {
        return isNonApp(change) && change.hasFlags(QuickStepContract.SYSUI_STATE_BACK_DISABLED);
    }

    public static boolean isHomeOrRecents(TransitionInfo.Change change) {
        return change.getTaskInfo() != null && (change.getTaskInfo().getActivityType() == 2 || change.getTaskInfo().getActivityType() == 3);
    }

    public static boolean isNonApp(TransitionInfo.Change change) {
        return (change.getTaskInfo() != null || change.hasFlags(2) || change.hasFlags(512)) ? false : true;
    }

    public static boolean isOpeningType(int i) {
        return i == 1 || i == 3 || i == 7;
    }

    public static boolean isOrderOnly(TransitionInfo.Change change) {
        if (!change.getResumedAffordance() && change.getMode() == 6 && (change.getFlags() & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0 && change.getStartAbsBounds().equals(change.getEndAbsBounds())) {
            return change.getLastParent() == null || change.getLastParent().equals(change.getParent());
        }
        return false;
    }

    public static boolean isTopApp(TransitionInfo transitionInfo, TransitionInfo.Change change, boolean z) {
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if ((change2.getTaskInfo() != null || change2.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) && (!(z && isClosingType(change2.getMode())) && (z || !isOpeningType(change2.getMode())))) {
                return change2 == change;
            }
        }
        return false;
    }

    public static boolean isTransientLaunchOverlay(TransitionInfo.Change change) {
        return isNonApp(change) && change.hasFlags(134217728);
    }

    public static boolean isWallpaper(TransitionInfo.Change change) {
        return change.getTaskInfo() == null && change.hasFlags(2) && !change.hasFlags(512);
    }

    public static RemoteAnimationTarget newTarget(TransitionInfo.Change change, int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, ArrayMap arrayMap) {
        SurfaceControl createLeash = createLeash(transitionInfo, change, i, transaction);
        if (arrayMap != null) {
            arrayMap.put(change.getLeash(), createLeash);
        }
        return newTarget(change, i, createLeash, false);
    }

    public static int rootIndexFor(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        int findRootIndex = transitionInfo.findRootIndex(change.getEndDisplayId());
        if (findRootIndex >= 0) {
            return findRootIndex;
        }
        int findRootIndex2 = transitionInfo.findRootIndex(change.getStartDisplayId());
        if (findRootIndex2 >= 0) {
            return findRootIndex2;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x013e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static RemoteAnimationTarget newTarget(TransitionInfo.Change change, int i, SurfaceControl surfaceControl, boolean z) {
        boolean z2;
        WindowConfiguration windowConfiguration;
        int i2;
        int i3;
        int i4;
        int i5;
        if (isDividerBar(change)) {
            int mode = change.getMode();
            if (mode != 1) {
                if (mode != 2) {
                    if (mode != 3) {
                        if (mode != 4) {
                            i5 = 2;
                            return new RemoteAnimationTarget(-1, i5, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
                        }
                    }
                }
                i5 = 1;
                return new RemoteAnimationTarget(-1, i5, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
            }
            i5 = 0;
            return new RemoteAnimationTarget(-1, i5, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2034);
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && isTransientLaunchOverlay(change)) {
            int mode2 = change.getMode();
            if (mode2 != 1) {
                if (mode2 != 2) {
                    if (mode2 != 3) {
                        if (mode2 != 4) {
                            i4 = 2;
                            return new RemoteAnimationTarget(-1, i4, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2632);
                        }
                    }
                }
                i4 = 1;
                return new RemoteAnimationTarget(-1, i4, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2632);
            }
            i4 = 0;
            return new RemoteAnimationTarget(-1, i4, surfaceControl, false, (Rect) null, (Rect) null, Integer.MAX_VALUE, new Point(0, 0), change.getStartAbsBounds(), change.getStartAbsBounds(), new WindowConfiguration(), true, (SurfaceControl) null, (Rect) null, (ActivityManager.RunningTaskInfo) null, false, 2632);
        }
        Rect rect = new Rect(0, 0, 0, 0);
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (taskInfo != null) {
            int i6 = taskInfo.taskId;
            boolean z3 = !taskInfo.isRunning;
            WindowConfiguration windowConfiguration2 = taskInfo.configuration.windowConfiguration;
            rect.set(change.getInsetsForRecentsTransition());
            i2 = i6;
            z2 = z3;
            windowConfiguration = windowConfiguration2;
        } else {
            z2 = true;
            windowConfiguration = new WindowConfiguration();
            i2 = -1;
        }
        Rect rect2 = new Rect(change.getEndAbsBounds());
        rect2.offsetTo(change.getEndRelOffset().x, change.getEndRelOffset().y);
        int mode3 = change.getMode();
        if (mode3 != 1) {
            if (mode3 != 2) {
                if (mode3 != 3) {
                    if (mode3 != 4) {
                        i3 = 2;
                        RemoteAnimationTarget remoteAnimationTarget = new RemoteAnimationTarget(i2, i3, surfaceControl, (z && (change.getFlags() & 4) == 0) ? false : true, (Rect) null, rect, i, (Point) null, rect2, new Rect(change.getEndAbsBounds()), windowConfiguration, z2, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), taskInfo, change.getAllowEnterPip(), -1);
                        remoteAnimationTarget.setWillShowImeOnTarget((change.getFlags() & 2048) != 0);
                        remoteAnimationTarget.setRotationChange(change.getEndRotation() - change.getStartRotation());
                        remoteAnimationTarget.backgroundColor = change.getBackgroundColor();
                        return remoteAnimationTarget;
                    }
                }
            }
            i3 = 1;
            RemoteAnimationTarget remoteAnimationTarget2 = new RemoteAnimationTarget(i2, i3, surfaceControl, (z && (change.getFlags() & 4) == 0) ? false : true, (Rect) null, rect, i, (Point) null, rect2, new Rect(change.getEndAbsBounds()), windowConfiguration, z2, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), taskInfo, change.getAllowEnterPip(), -1);
            remoteAnimationTarget2.setWillShowImeOnTarget((change.getFlags() & 2048) != 0);
            remoteAnimationTarget2.setRotationChange(change.getEndRotation() - change.getStartRotation());
            remoteAnimationTarget2.backgroundColor = change.getBackgroundColor();
            return remoteAnimationTarget2;
        }
        i3 = 0;
        RemoteAnimationTarget remoteAnimationTarget22 = new RemoteAnimationTarget(i2, i3, surfaceControl, (z && (change.getFlags() & 4) == 0) ? false : true, (Rect) null, rect, i, (Point) null, rect2, new Rect(change.getEndAbsBounds()), windowConfiguration, z2, (SurfaceControl) null, new Rect(change.getStartAbsBounds()), taskInfo, change.getAllowEnterPip(), -1);
        remoteAnimationTarget22.setWillShowImeOnTarget((change.getFlags() & 2048) != 0);
        remoteAnimationTarget22.setRotationChange(change.getEndRotation() - change.getStartRotation());
        remoteAnimationTarget22.backgroundColor = change.getBackgroundColor();
        return remoteAnimationTarget22;
    }
}
