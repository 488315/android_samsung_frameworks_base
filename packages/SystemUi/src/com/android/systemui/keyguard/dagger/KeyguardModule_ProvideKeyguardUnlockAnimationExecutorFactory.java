package com.android.systemui.keyguard.dagger;

import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardModule_ProvideKeyguardUnlockAnimationExecutorFactory implements Provider {
    public static Executor provideKeyguardUnlockAnimationExecutor() {
        HandlerThread handlerThread = new HandlerThread("UnlockAnimationThread", -4);
        handlerThread.start();
        return new HandlerExecutor(Handler.createAsync(handlerThread.getLooper()));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideKeyguardUnlockAnimationExecutor();
    }
}
