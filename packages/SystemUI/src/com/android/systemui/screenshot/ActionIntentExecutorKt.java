package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ActionIntentExecutorKt {
    public static final ActionIntentExecutorKt$SCREENSHOT_REMOTE_RUNNER$1 SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.screenshot.ActionIntentExecutorKt$SCREENSHOT_REMOTE_RUNNER$1
        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                Log.e("ActionIntentExecutor", "Error finishing screenshot remote animation", e);
            }
        }

        public final void onAnimationCancelled() {
        }
    };
}
