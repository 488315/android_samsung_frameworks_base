package com.android.p038wm.shell.common;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ExecutorUtils$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ RemoteCallable f$1;

    public /* synthetic */ ExecutorUtils$$ExternalSyntheticLambda0(Consumer consumer, RemoteCallable remoteCallable, int i) {
        this.$r8$classId = i;
        this.f$0 = consumer;
        this.f$1 = remoteCallable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.accept(this.f$1);
                break;
            case 1:
                this.f$0.accept(this.f$1);
                break;
            default:
                this.f$0.accept(this.f$1);
                break;
        }
    }
}
