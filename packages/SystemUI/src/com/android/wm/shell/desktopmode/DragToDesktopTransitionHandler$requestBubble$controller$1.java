package com.android.wm.shell.desktopmode;

import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final class DragToDesktopTransitionHandler$requestBubble$controller$1 implements Supplier {
    public static final DragToDesktopTransitionHandler$requestBubble$controller$1 INSTANCE = new DragToDesktopTransitionHandler$requestBubble$controller$1();

    @Override // java.util.function.Supplier
    public final Object get() {
        return new IllegalStateException("BubbleController not set");
    }
}
