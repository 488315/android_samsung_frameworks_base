package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RemoteAnimationTargetCompat {
    public static final int ACTIVITY_TYPE_ASSISTANT = 4;
    public static final int ACTIVITY_TYPE_HOME = 2;
    public static final int ACTIVITY_TYPE_RECENTS = 3;
    public static final int ACTIVITY_TYPE_STANDARD = 1;
    public static final int ACTIVITY_TYPE_UNDEFINED = 0;
    public static final int MODE_CHANGING = 2;
    public static final int MODE_CLOSING = 1;
    public static final int MODE_OPENING = 0;
    public final int activityType;
    public final boolean allowEnterPip;
    public final Rect clipRect;
    public final Rect contentInsets;
    public final boolean isNotInRecents;
    public final boolean isTranslucent;
    public final SurfaceControl leash;
    public final Rect localBounds;
    private final SurfaceControl mStartLeash;
    public final int mode;
    public final Point position;
    public final int prefixOrderIndex;
    public final int rotationChange;
    public final Rect screenSpaceBounds;
    public final Rect sourceContainerBounds;
    private final Rect startBounds;
    public final Rect startScreenSpaceBounds;
    public final int taskId;
    public final ActivityManager.RunningTaskInfo taskInfo;
    public final WindowConfiguration windowConfiguration;
    public final int windowType;

    public RemoteAnimationTargetCompat(RemoteAnimationTarget remoteAnimationTarget) {
        this.taskId = remoteAnimationTarget.taskId;
        this.mode = remoteAnimationTarget.mode;
        this.leash = remoteAnimationTarget.leash;
        this.isTranslucent = remoteAnimationTarget.isTranslucent;
        this.clipRect = remoteAnimationTarget.clipRect;
        this.position = remoteAnimationTarget.position;
        this.localBounds = remoteAnimationTarget.localBounds;
        this.sourceContainerBounds = remoteAnimationTarget.sourceContainerBounds;
        Rect rect = remoteAnimationTarget.screenSpaceBounds;
        this.screenSpaceBounds = rect;
        this.startScreenSpaceBounds = rect;
        this.prefixOrderIndex = remoteAnimationTarget.prefixOrderIndex;
        this.isNotInRecents = remoteAnimationTarget.isNotInRecents;
        this.contentInsets = remoteAnimationTarget.contentInsets;
        this.activityType = remoteAnimationTarget.windowConfiguration.getActivityType();
        this.taskInfo = remoteAnimationTarget.taskInfo;
        this.allowEnterPip = remoteAnimationTarget.allowEnterPip;
        this.rotationChange = 0;
        this.mStartLeash = remoteAnimationTarget.startLeash;
        this.windowType = remoteAnimationTarget.windowType;
        this.windowConfiguration = remoteAnimationTarget.windowConfiguration;
        this.startBounds = remoteAnimationTarget.startBounds;
    }

    private static SurfaceControl createLeash(TransitionInfo transitionInfo, TransitionInfo.Change change, int i, SurfaceControl.Transaction transaction) {
        if (change.getParent() != null && (change.getFlags() & 2) != 0) {
            return change.getLeash();
        }
        SurfaceControl build = new SurfaceControl.Builder().setName(change.getLeash().toString() + "_transition-leash").setContainerLayer().setParent(change.getParent() == null ? transitionInfo.getRootLeash() : transitionInfo.getChange(change.getParent()).getLeash()).build();
        setupLeash(build, change, KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, i), transitionInfo, transaction);
        transaction.reparent(change.getLeash(), build);
        transaction.setAlpha(change.getLeash(), 1.0f);
        transaction.show(change.getLeash());
        transaction.setPosition(change.getLeash(), 0.0f, 0.0f);
        transaction.setLayer(change.getLeash(), 0);
        return build;
    }

    private static int newModeToLegacyMode(int i) {
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

    private static void setupLeash(SurfaceControl surfaceControl, TransitionInfo.Change change, int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction) {
        boolean z = transitionInfo.getType() == 1 || transitionInfo.getType() == 3;
        int size = transitionInfo.getChanges().size();
        int mode = change.getMode();
        transaction.reparent(surfaceControl, transitionInfo.getRoot(rootIndexFor(change, transitionInfo)).getLeash());
        transaction.setPosition(surfaceControl, change.getStartAbsBounds().left - transitionInfo.getRoot(r5).getOffset().x, change.getStartAbsBounds().top - transitionInfo.getRoot(r5).getOffset().y);
        transaction.show(surfaceControl);
        if (mode == 1 || mode == 3) {
            if (!z) {
                transaction.setLayer(surfaceControl, size - i);
                return;
            }
            transaction.setLayer(surfaceControl, (transitionInfo.getChanges().size() + size) - i);
            if ((change.getFlags() & 8) == 0) {
                transaction.setAlpha(surfaceControl, 0.0f);
                return;
            }
            return;
        }
        if (mode != 2 && mode != 4) {
            transaction.setLayer(surfaceControl, (transitionInfo.getChanges().size() + size) - i);
        } else if (z) {
            transaction.setLayer(surfaceControl, size - i);
        } else {
            transaction.setLayer(surfaceControl, (transitionInfo.getChanges().size() + size) - i);
        }
    }

    public static RemoteAnimationTargetCompat[] wrap(RemoteAnimationTarget[] remoteAnimationTargetArr) {
        int length = remoteAnimationTargetArr != null ? remoteAnimationTargetArr.length : 0;
        RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr = new RemoteAnimationTargetCompat[length];
        for (int i = 0; i < length; i++) {
            remoteAnimationTargetCompatArr[i] = new RemoteAnimationTargetCompat(remoteAnimationTargetArr[i]);
        }
        return remoteAnimationTargetCompatArr;
    }

    public void release() {
        SurfaceControl surfaceControl = this.leash;
        if (surfaceControl != null) {
            surfaceControl.release();
        }
        SurfaceControl surfaceControl2 = this.mStartLeash;
        if (surfaceControl2 != null) {
            surfaceControl2.release();
        }
    }

    public RemoteAnimationTarget unwrap() {
        return new RemoteAnimationTarget(this.taskId, this.mode, this.leash, this.isTranslucent, this.clipRect, this.contentInsets, this.prefixOrderIndex, this.position, this.localBounds, this.screenSpaceBounds, this.windowConfiguration, this.isNotInRecents, this.mStartLeash, this.startBounds, this.taskInfo, this.allowEnterPip, this.windowType);
    }

    public static RemoteAnimationTargetCompat[] wrap(TransitionInfo transitionInfo, boolean z, SurfaceControl.Transaction transaction, ArrayMap<SurfaceControl, SurfaceControl> arrayMap) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (z == ((change.getFlags() & 2) != 0)) {
                arrayList.add(new RemoteAnimationTargetCompat(change, KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, i), transitionInfo, transaction));
                if (arrayMap != null) {
                    arrayMap.put(change.getLeash(), ((RemoteAnimationTargetCompat) arrayList.get(arrayList.size() - 1)).leash);
                }
            }
        }
        return (RemoteAnimationTargetCompat[]) arrayList.toArray(new RemoteAnimationTargetCompat[arrayList.size()]);
    }

    public RemoteAnimationTargetCompat(TransitionInfo.Change change, int i, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction) {
        WindowConfiguration windowConfiguration;
        this.taskId = change.getTaskInfo() != null ? change.getTaskInfo().taskId : -1;
        this.mode = newModeToLegacyMode(change.getMode());
        this.leash = createLeash(transitionInfo, change, i, transaction);
        this.isTranslucent = ((change.getFlags() & 4) == 0 && (change.getFlags() & 1) == 0) ? false : true;
        this.clipRect = null;
        this.position = null;
        Rect rect = new Rect(change.getEndAbsBounds());
        this.localBounds = rect;
        rect.offsetTo(change.getEndRelOffset().x, change.getEndRelOffset().y);
        this.sourceContainerBounds = null;
        this.screenSpaceBounds = new Rect(change.getEndAbsBounds());
        this.startScreenSpaceBounds = new Rect(change.getStartAbsBounds());
        this.prefixOrderIndex = i;
        this.contentInsets = new Rect(0, 0, 0, 0);
        if (change.getTaskInfo() != null) {
            this.isNotInRecents = !change.getTaskInfo().isRunning;
            this.activityType = change.getTaskInfo().getActivityType();
        } else {
            this.isNotInRecents = true;
            this.activityType = 0;
        }
        this.taskInfo = change.getTaskInfo();
        this.allowEnterPip = change.getAllowEnterPip();
        this.mStartLeash = null;
        this.rotationChange = change.getEndRotation() - change.getStartRotation();
        this.windowType = -1;
        if (change.getTaskInfo() != null) {
            windowConfiguration = change.getTaskInfo().configuration.windowConfiguration;
        } else {
            windowConfiguration = new WindowConfiguration();
        }
        this.windowConfiguration = windowConfiguration;
        this.startBounds = change.getStartAbsBounds();
    }
}
