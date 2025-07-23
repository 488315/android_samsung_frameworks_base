package com.android.systemui.statusbar.phone;

import com.android.systemui.scrim.ScrimView;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SecLsScrimControlProvider {
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
