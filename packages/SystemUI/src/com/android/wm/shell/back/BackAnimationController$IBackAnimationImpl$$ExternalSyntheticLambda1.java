package com.android.wm.shell.back;

import com.android.wm.shell.back.BackAnimationController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class BackAnimationController$IBackAnimationImpl$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = BackAnimationController.IBackAnimationImpl.$r8$clinit;
        ShellBackAnimationRegistry shellBackAnimationRegistry = ((BackAnimationController) obj).mShellBackAnimationRegistry;
        shellBackAnimationRegistry.mAnimationDefinition.remove(1);
        shellBackAnimationRegistry.updateSupportedAnimators();
    }
}
