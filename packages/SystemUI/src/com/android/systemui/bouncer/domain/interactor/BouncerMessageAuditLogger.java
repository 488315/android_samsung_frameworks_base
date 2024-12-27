package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepository;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class BouncerMessageAuditLogger implements CoreStartable {
    public final BouncerMessageRepository repository;
    public final CoroutineScope scope;

    public BouncerMessageAuditLogger(CoroutineScope coroutineScope, BouncerMessageRepository bouncerMessageRepository) {
        this.scope = coroutineScope;
        this.repository = bouncerMessageRepository;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new BouncerMessageAuditLogger$start$1(this, null), 3);
    }
}
