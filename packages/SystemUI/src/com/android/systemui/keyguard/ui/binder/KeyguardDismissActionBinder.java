package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

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
