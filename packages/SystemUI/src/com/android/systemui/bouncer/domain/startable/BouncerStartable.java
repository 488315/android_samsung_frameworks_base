package com.android.systemui.bouncer.domain.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class BouncerStartable implements CoreStartable {
    public final Lazy keyguardMediaKeyInteractor;

    public BouncerStartable(Lazy lazy, CoroutineScope coroutineScope) {
        this.keyguardMediaKeyInteractor = lazy;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ComposeBouncerFlags.INSTANCE.getClass();
    }
}
