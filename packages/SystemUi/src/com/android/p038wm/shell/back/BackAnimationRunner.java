package com.android.p038wm.shell.back;

import android.view.IRemoteAnimationRunner;
import android.window.IOnBackInvokedCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BackAnimationRunner {
    public boolean mAnimationCancelled;
    public final IOnBackInvokedCallback mCallback;
    public final IRemoteAnimationRunner mRunner;
    public boolean mWaitingAnimation;

    public BackAnimationRunner(IOnBackInvokedCallback iOnBackInvokedCallback, IRemoteAnimationRunner iRemoteAnimationRunner) {
        this.mCallback = iOnBackInvokedCallback;
        this.mRunner = iRemoteAnimationRunner;
    }
}
