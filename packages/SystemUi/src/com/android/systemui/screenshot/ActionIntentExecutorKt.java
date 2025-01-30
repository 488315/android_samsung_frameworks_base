package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
