package com.android.systemui.bluetooth;

import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.BroadcastDialogDelegate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BroadcastDialogController {
    public final BroadcastDialogDelegate.Factory mBroadcastDialogFactory;
    public final DialogTransitionAnimator mDialogTransitionAnimator;

    public BroadcastDialogController(DialogTransitionAnimator dialogTransitionAnimator, BroadcastDialogDelegate.Factory factory) {
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mBroadcastDialogFactory = factory;
    }
}
