package com.android.wm.shell.desktopmode;

import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DragToDesktopTransitionHandler$requestBubble$controller$1 implements Supplier {
    public static final DragToDesktopTransitionHandler$requestBubble$controller$1 INSTANCE = new DragToDesktopTransitionHandler$requestBubble$controller$1();

    @Override // java.util.function.Supplier
    public final Object get() {
        return new IllegalStateException("BubbleController not set");
    }
}
