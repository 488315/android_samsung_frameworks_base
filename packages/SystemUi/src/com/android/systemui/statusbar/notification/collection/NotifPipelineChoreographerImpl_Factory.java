package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.concurrency.DelayableExecutor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotifPipelineChoreographerImpl_Factory implements Provider {
    public final Provider executorProvider;
    public final Provider viewChoreographerProvider;

    public NotifPipelineChoreographerImpl_Factory(Provider provider, Provider provider2) {
        this.viewChoreographerProvider = provider;
        this.executorProvider = provider2;
    }

    public static NotifPipelineChoreographerImpl newInstance(Choreographer choreographer, DelayableExecutor delayableExecutor) {
        return new NotifPipelineChoreographerImpl(choreographer, delayableExecutor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new NotifPipelineChoreographerImpl((Choreographer) this.viewChoreographerProvider.get(), (DelayableExecutor) this.executorProvider.get());
    }
}
