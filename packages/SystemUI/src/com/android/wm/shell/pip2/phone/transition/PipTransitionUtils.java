package com.android.wm.shell.pip2.phone.transition;

import android.app.PictureInPictureParams;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import com.android.internal.util.Preconditions;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTransitionUtils {
    public static TransitionInfo.Change getChangeByToken(TransitionInfo transitionInfo, WindowContainerToken windowContainerToken) {
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if (change.getTaskInfo() != null && change.getTaskInfo().getToken().equals(windowContainerToken)) {
                return change;
            }
        }
        return null;
    }

    public static TransitionInfo.Change getDeferConfigActivityChange(TransitionInfo transitionInfo, WindowContainerToken windowContainerToken) {
        TransitionInfo.Change changeByToken = getChangeByToken(transitionInfo, windowContainerToken);
        if (changeByToken == null) {
            return null;
        }
        TransitionInfo.Change change = changeByToken;
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (change2 != changeByToken && change2.getParent() != null && change2.getParent().equals(change.getContainer())) {
                if (change2.getTaskInfo() == null && change2.hasFlags(4194304)) {
                    return change2;
                }
                change = change2;
            }
        }
        return null;
    }

    public static int getFixedRotationDelta(TransitionInfo transitionInfo, TransitionInfo.Change change, PipDisplayLayoutState pipDisplayLayoutState) {
        TransitionInfo.Change change2;
        int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
        while (true) {
            if (m < 0) {
                change2 = null;
                break;
            }
            change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (change2.getEndFixedRotation() != -1) {
                break;
            }
            m--;
        }
        int startRotation = change.getStartRotation();
        if (change.getEndRotation() != -1 && startRotation != change.getEndRotation()) {
            return 0;
        }
        int endFixedRotation = change2 != null ? change2.getEndFixedRotation() : pipDisplayLayoutState.mDisplayLayout.mRotation;
        if (endFixedRotation == -1) {
            return 0;
        }
        return startRotation - endFixedRotation;
    }

    public static SurfaceControl getLeash(TransitionInfo.Change change) {
        SurfaceControl leash = change.getLeash();
        Preconditions.checkNotNull(leash, "Leash is null for change=" + change);
        return leash;
    }

    public static TransitionInfo.Change getPipChange(TransitionInfo transitionInfo) {
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            if (change.getTaskInfo() != null && change.getTaskInfo().getWindowingMode() == 2) {
                return change;
            }
        }
        return null;
    }

    public static PictureInPictureParams getPipParams(TransitionInfo.Change change) {
        return change.getTaskInfo().pictureInPictureParams != null ? change.getTaskInfo().pictureInPictureParams : new PictureInPictureParams.Builder().build();
    }
}
