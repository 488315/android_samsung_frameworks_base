package com.android.systemui.bouncer.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageAuditLogger implements CoreStartable {
    public final BouncerMessageRepository repository;
    public final CoroutineScope scope;

    public BouncerMessageAuditLogger(CoroutineScope coroutineScope, BouncerMessageRepository bouncerMessageRepository) {
        this.scope = coroutineScope;
        this.repository = bouncerMessageRepository;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new BouncerMessageAuditLogger$start$1(this, null), 7);
    }
}
