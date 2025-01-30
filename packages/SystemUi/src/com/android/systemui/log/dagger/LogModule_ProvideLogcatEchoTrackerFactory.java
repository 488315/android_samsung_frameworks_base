package com.android.systemui.log.dagger;

import android.content.ContentResolver;
import android.os.Build;
import android.os.Looper;
import com.android.systemui.log.LogcatEchoTracker;
import com.android.systemui.log.LogcatEchoTrackerDebug;
import com.android.systemui.log.LogcatEchoTrackerProd;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LogModule_ProvideLogcatEchoTrackerFactory implements Provider {
    public final Provider contentResolverProvider;
    public final Provider looperProvider;

    public LogModule_ProvideLogcatEchoTrackerFactory(Provider provider, Provider provider2) {
        this.contentResolverProvider = provider;
        this.looperProvider = provider2;
    }

    public static LogcatEchoTracker provideLogcatEchoTracker(ContentResolver contentResolver, Looper looper) {
        LogcatEchoTracker create = Build.isDebuggable() ? LogcatEchoTrackerDebug.create(contentResolver, looper) : new LogcatEchoTrackerProd();
        Preconditions.checkNotNullFromProvides(create);
        return create;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideLogcatEchoTracker((ContentResolver) this.contentResolverProvider.get(), (Looper) this.looperProvider.get());
    }
}
