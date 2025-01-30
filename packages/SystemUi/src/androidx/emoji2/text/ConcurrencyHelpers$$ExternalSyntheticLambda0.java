package androidx.emoji2.text;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ConcurrencyHelpers$$ExternalSyntheticLambda0 implements ThreadFactory {
    public final /* synthetic */ String f$0;

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, this.f$0);
        thread.setPriority(10);
        return thread;
    }
}
