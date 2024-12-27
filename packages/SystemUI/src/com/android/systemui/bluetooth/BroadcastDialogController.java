package com.android.systemui.bluetooth;

import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.BroadcastDialogDelegate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BroadcastDialogController {
    public final BroadcastDialogDelegate.Factory mBroadcastDialogFactory;
    public final DialogTransitionAnimator mDialogTransitionAnimator;

    public BroadcastDialogController(DialogTransitionAnimator dialogTransitionAnimator, BroadcastDialogDelegate.Factory factory) {
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mBroadcastDialogFactory = factory;
    }
}
