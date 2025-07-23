package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardDismissActionBinder implements CoreStartable {
    public final Lazy interactorLazy;

    public KeyguardDismissActionBinder(Lazy lazy, CoroutineScope coroutineScope) {
        this.interactorLazy = lazy;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ComposeBouncerFlags.INSTANCE.getClass();
    }
}
