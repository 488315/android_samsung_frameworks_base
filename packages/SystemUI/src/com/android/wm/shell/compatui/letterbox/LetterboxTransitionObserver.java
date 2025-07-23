package com.android.wm.shell.compatui.letterbox;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.android.wm.shell.common.transition.TransitionStateHolder;
import com.android.wm.shell.compatui.letterbox.LetterboxControllerStrategy;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class LetterboxTransitionObserver implements Transitions.TransitionObserver {
    public static final Rect EMPTY_BOUNDS;
    public final LetterboxController letterboxController;
    public final LetterboxControllerStrategy letterboxModeStrategy;
    public final TransitionStateHolder transitionStateHolder;
    public final Transitions transitions;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        EMPTY_BOUNDS = new Rect();
    }

    public LetterboxTransitionObserver(ShellInit shellInit, Transitions transitions, LetterboxController letterboxController, TransitionStateHolder transitionStateHolder, LetterboxControllerStrategy letterboxControllerStrategy) {
        this.transitions = transitions;
        this.letterboxController = letterboxController;
        this.transitionStateHolder = transitionStateHolder;
        this.letterboxModeStrategy = letterboxControllerStrategy;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        for (TransitionInfo.Change change : transitionInfo.getChanges()) {
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null) {
                LetterboxKey letterboxKey = new LetterboxKey(taskInfo.displayId, taskInfo.taskId);
                Rect rect = new Rect(change.getEndRelOffset().x, change.getEndRelOffset().y, change.getEndAbsBounds().width(), change.getEndAbsBounds().height());
                LetterboxController letterboxController = this.letterboxController;
                if (!TransitionUtil.isClosingType(change.getMode()) || this.transitionStateHolder.recentsTransitionState >= 2) {
                    boolean isTopActivityLetterboxed = taskInfo.appCompatTaskInfo.isTopActivityLetterboxed();
                    if (isTopActivityLetterboxed) {
                        LetterboxControllerStrategy letterboxControllerStrategy = this.letterboxModeStrategy;
                        letterboxControllerStrategy.currentMode = Math.max(letterboxControllerStrategy.letterboxConfiguration.letterboxActivityCornersRadius, 0) > 0 ? LetterboxControllerStrategy.LetterboxMode.SINGLE_SURFACE : LetterboxControllerStrategy.LetterboxMode.MULTIPLE_SURFACES;
                        letterboxController.createLetterboxSurface(letterboxKey, transaction, change.getLeash());
                        Rect rect2 = taskInfo.appCompatTaskInfo.topActivityLetterboxBounds;
                        if (rect2 == null) {
                            rect2 = EMPTY_BOUNDS;
                        }
                        letterboxController.updateLetterboxSurfaceBounds(letterboxKey, transaction, rect, rect2);
                    }
                    letterboxController.updateLetterboxSurfaceVisibility(letterboxKey, transaction, isTopActivityLetterboxed);
                } else {
                    letterboxController.destroyLetterboxSurface(letterboxKey, transaction2);
                }
                letterboxController.dump();
            }
        }
    }
}
