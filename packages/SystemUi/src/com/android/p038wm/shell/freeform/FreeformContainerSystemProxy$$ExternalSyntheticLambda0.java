package com.android.p038wm.shell.freeform;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda0 implements ThreadFactory {
    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        return new Thread(runnable, "FreeformContainerSystemProxy");
    }
}
