package com.android.systemui.statusbar.phone;

import com.android.systemui.scrim.ScrimView;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecLsScrimControlProvider {
    public final Supplier mBehindScrimSupplier;
    public final Consumer mDispatchBackScrimStateConsumer;
    public final Supplier mFrontScrimSupplier;
    public final Supplier mKeyguardOccludedSupplier;
    public final Supplier mNotificationsScrimSupplier;
    public final Runnable mUpdateScrimsRunnable;

    public SecLsScrimControlProvider(Supplier<ScrimView> supplier, Supplier<ScrimView> supplier2, Supplier<ScrimView> supplier3, Supplier<Boolean> supplier4, Runnable runnable, Consumer consumer) {
        this.mNotificationsScrimSupplier = supplier;
        this.mBehindScrimSupplier = supplier2;
        this.mFrontScrimSupplier = supplier3;
        this.mKeyguardOccludedSupplier = supplier4;
        this.mUpdateScrimsRunnable = runnable;
        this.mDispatchBackScrimStateConsumer = consumer;
    }
}
