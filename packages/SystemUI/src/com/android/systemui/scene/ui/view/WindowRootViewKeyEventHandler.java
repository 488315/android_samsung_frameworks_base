package com.android.systemui.scene.ui.view;

import com.android.systemui.classifier.FalsingCollector;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WindowRootViewKeyEventHandler {
    public final FalsingCollector falsingCollector;
    public final Lazy sysUIKeyEventHandlerLazy;

    public WindowRootViewKeyEventHandler(Lazy lazy, FalsingCollector falsingCollector) {
        this.sysUIKeyEventHandlerLazy = lazy;
        this.falsingCollector = falsingCollector;
    }
}
