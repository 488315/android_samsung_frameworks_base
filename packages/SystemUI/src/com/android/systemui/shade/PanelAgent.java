package com.android.systemui.shade;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class PanelAgent {
    public final Runnable closeQsRunnable;
    public final Runnable instantCollapseRunnable;
    public final BooleanSupplier keyguardShowing;
    public final BooleanSupplier panelExpandedSupplier;
    public final Consumer trackingStoppedConsumer;
    public final BooleanSupplier trackingSupplier;
    public final Runnable updateResourcesRunnable;

    public PanelAgent(Consumer<Boolean> consumer, Runnable runnable, BooleanSupplier booleanSupplier, BooleanSupplier booleanSupplier2, Runnable runnable2, Runnable runnable3, BooleanSupplier booleanSupplier3, Function0 function0, Function0 function02) {
        this.trackingStoppedConsumer = consumer;
        this.closeQsRunnable = runnable;
        this.keyguardShowing = booleanSupplier;
        this.trackingSupplier = booleanSupplier2;
        this.instantCollapseRunnable = runnable2;
        this.updateResourcesRunnable = runnable3;
        this.panelExpandedSupplier = booleanSupplier3;
    }
}
