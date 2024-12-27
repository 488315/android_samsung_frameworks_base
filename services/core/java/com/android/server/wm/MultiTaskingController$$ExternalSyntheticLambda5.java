package com.android.server.wm;

import android.os.Message;

import com.samsung.android.multiwindow.IRemoteAppTransitionListener;

public final /* synthetic */ class MultiTaskingController$$ExternalSyntheticLambda5 {
    public final /* synthetic */ int $r8$classId;

    public final void accept(
            IRemoteAppTransitionListener iRemoteAppTransitionListener, Message message) {
        switch (this.$r8$classId) {
            case 0:
                iRemoteAppTransitionListener.onStartRecentsAnimation(message.arg1 != 0);
                break;
            case 1:
                iRemoteAppTransitionListener.onFinishRecentsAnimation(message.arg1 != 0);
                break;
            case 2:
                iRemoteAppTransitionListener.onStartHomeAnimation(message.arg1 != 0);
                break;
            default:
                iRemoteAppTransitionListener.onWallpaperVisibilityChanged(
                        message.arg1 != 0, message.arg2 != 0);
                break;
        }
    }
}
