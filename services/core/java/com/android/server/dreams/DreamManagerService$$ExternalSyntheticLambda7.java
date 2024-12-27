package com.android.server.dreams;

import android.service.dreams.DreamManagerInternal;

import java.util.Iterator;
import java.util.function.Consumer;

public final /* synthetic */ class DreamManagerService$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ DreamManagerService f$0;
    public final /* synthetic */ Consumer f$1;

    public /* synthetic */ DreamManagerService$$ExternalSyntheticLambda7(
            DreamManagerService dreamManagerService, Consumer consumer) {
        this.f$0 = dreamManagerService;
        this.f$1 = consumer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DreamManagerService dreamManagerService = this.f$0;
        Consumer consumer = this.f$1;
        Iterator it = dreamManagerService.mDreamManagerStateListeners.iterator();
        while (it.hasNext()) {
            consumer.accept((DreamManagerInternal.DreamManagerStateListener) it.next());
        }
    }
}
