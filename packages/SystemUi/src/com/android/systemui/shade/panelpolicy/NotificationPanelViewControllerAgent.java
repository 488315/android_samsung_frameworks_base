package com.android.systemui.shade.panelpolicy;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationPanelViewControllerAgent {
    public final Consumer trackingStoppedConsumer;

    public NotificationPanelViewControllerAgent(Consumer<Boolean> consumer, Runnable runnable, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2, Runnable runnable2, Runnable runnable3, BooleanSupplier booleanSupplier3) {
        this.trackingStoppedConsumer = consumer;
    }
}
