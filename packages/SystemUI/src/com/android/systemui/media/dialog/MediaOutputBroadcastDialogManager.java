package com.android.systemui.media.dialog;

import android.content.Context;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaSwitchingController;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputBroadcastDialogManager {
    public final Executor backgroundExecutor;
    public final Executor mainExecutor;

    public MediaOutputBroadcastDialogManager(Context context, BroadcastSender broadcastSender, DialogTransitionAnimator dialogTransitionAnimator, MediaSwitchingController.Factory factory, Executor executor, Executor executor2) {
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
    }
}
