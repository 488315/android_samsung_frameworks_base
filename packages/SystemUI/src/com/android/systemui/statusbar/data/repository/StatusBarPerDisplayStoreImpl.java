package com.android.systemui.statusbar.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.PerDisplayStoreImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class StatusBarPerDisplayStoreImpl extends PerDisplayStoreImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;

    public StatusBarPerDisplayStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository) {
        super(coroutineScope, displayRepository);
        this.backgroundApplicationScope = coroutineScope;
        this.displayRepository = displayRepository;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl, com.android.systemui.CoreStartable
    public final void start() {
        getInstanceClass().getSimpleName();
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new StatusBarPerDisplayStoreImpl$start$1(this, null), 6);
    }
}
