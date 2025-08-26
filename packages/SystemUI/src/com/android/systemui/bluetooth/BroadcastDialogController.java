package com.android.systemui.bluetooth;

import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.BroadcastDialogDelegate;

/* loaded from: classes.dex */
public class BroadcastDialogController {
    public final BroadcastDialogDelegate.Factory mBroadcastDialogFactory;
    public final DialogTransitionAnimator mDialogTransitionAnimator;

    public BroadcastDialogController(DialogTransitionAnimator dialogTransitionAnimator, BroadcastDialogDelegate.Factory factory) {
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mBroadcastDialogFactory = factory;
    }
}
