package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import com.android.systemui.settings.DisplayTracker;

public final class ClipboardTransitionExecutor {
    public final ClipboardTransitionExecutor$NULL_ACTIVITY_TRANSITION$1 NULL_ACTIVITY_TRANSITION;
    public final String TAG = "ClipboardTransitionExec";

    public ClipboardTransitionExecutor(Context context, DisplayTracker displayTracker) {
        new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.clipboardoverlay.ClipboardTransitionExecutor$NULL_ACTIVITY_TRANSITION$1
            public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                try {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                } catch (RemoteException e) {
                    Log.e(ClipboardTransitionExecutor.this.TAG, "Error finishing screenshot remote animation", e);
                }
            }

            public final void onAnimationCancelled() {
            }
        };
    }
}
