package com.android.wm.shell.pip2.phone;

import android.app.TaskInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.Preconditions;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTransitionState {
    public boolean mInSwipePipToHomeTransition;
    public final Handler mMainHandler;
    public Runnable mOnIdlePipTransitionStateRunnable;
    public SurfaceControl mPinnedTaskLeash;
    public final PipDesktopState mPipDesktopState;
    public TaskInfo mPipTaskInfo;
    public int mState;
    public SurfaceControl mSwipePipToHomeOverlay;
    public final Rect mSwipePipToHomeAppBounds = new Rect();
    public boolean mInFixedRotation = false;
    public final List mCallbacks = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PipTransitionStateChangedListener {
        void onPipTransitionStateChanged(int i, int i2, Bundle bundle);
    }

    public PipTransitionState(Handler handler, PipDesktopState pipDesktopState) {
        this.mMainHandler = handler;
        this.mPipDesktopState = pipDesktopState;
    }

    public static String stateToString(int i) {
        switch (i) {
            case 0:
                return "undefined";
            case 1:
                return "swiping_to_pip";
            case 2:
                return "entering-pip";
            case 3:
                return "entered-pip";
            case 4:
                return "scheduled_bounds_change";
            case 5:
                return "changing-bounds";
            case 6:
                return "changed-bounds";
            case 7:
                return "exiting-pip";
            case 8:
                return "exited-pip";
            default:
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown state: "));
        }
    }

    public final void addPipTransitionStateChangedListener(PipTransitionStateChangedListener pipTransitionStateChangedListener) {
        if (((ArrayList) this.mCallbacks).contains(pipTransitionStateChangedListener)) {
            return;
        }
        ((ArrayList) this.mCallbacks).add(pipTransitionStateChangedListener);
    }

    public final WindowContainerToken getPipTaskToken() {
        TaskInfo taskInfo = this.mPipTaskInfo;
        if (taskInfo != null) {
            return taskInfo.getToken();
        }
        return null;
    }

    public final boolean isInPip() {
        int i = this.mState;
        return i > 2 && i < 7;
    }

    public final void maybeRunOnIdlePipTransitionStateCallback() {
        Runnable runnable = this.mOnIdlePipTransitionStateRunnable;
        if (runnable != null) {
            int i = this.mState;
            if ((i == 3 || i == 6) && !this.mInFixedRotation) {
                this.mMainHandler.post(runnable);
                this.mOnIdlePipTransitionStateRunnable = null;
            }
        }
    }

    public final void setState(final int i, final Bundle bundle) {
        if (i == 2 || i == 1 || i == 4 || i == 5) {
            Preconditions.checkArgument((bundle == null || bundle.isEmpty()) ? false : true, "No extra bundle for " + stateToString(i) + " state.");
        }
        if (!shouldTransitionToState(i)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1666289151864521045L, 0, "PipTransitionState", stateToString(i), String.valueOf(this));
            }
        } else {
            final int i2 = this.mState;
            if (i2 != i) {
                this.mState = i;
                ((ArrayList) this.mCallbacks).forEach(new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipTransitionState$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((PipTransitionState.PipTransitionStateChangedListener) obj).onPipTransitionStateChanged(i2, i, bundle);
                    }
                });
            }
            maybeRunOnIdlePipTransitionStateCallback();
        }
    }

    public boolean shouldTransitionToState(int i) {
        if (i != 4) {
            return true;
        }
        if (!isInPip()) {
            return false;
        }
        PipDesktopState pipDesktopState = this.mPipDesktopState;
        return (pipDesktopState.isDesktopWindowingPipEnabled() && ((DragToDesktopTransitionHandler) pipDesktopState.dragToDesktopTransitionHandlerOptional.get()).getInProgress$1()) ? false : true;
    }

    public final String toString() {
        return "PipTransitionState(mState=" + stateToString(this.mState) + ", mInSwipePipToHomeTransition=" + this.mInSwipePipToHomeTransition + ")";
    }
}
