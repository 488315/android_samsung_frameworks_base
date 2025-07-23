package com.android.systemui.bouncer.domain.startable;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import dagger.Lazy;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
