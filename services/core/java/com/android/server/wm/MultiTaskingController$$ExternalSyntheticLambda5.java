package com.android.server.wm;

import android.os.Message;

import com.samsung.android.multiwindow.IRemoteAppTransitionListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
