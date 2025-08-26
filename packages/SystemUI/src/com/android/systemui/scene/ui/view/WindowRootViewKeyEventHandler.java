package com.android.systemui.scene.ui.view;

import com.android.systemui.classifier.FalsingCollector;
import dagger.Lazy;

/* loaded from: classes2.dex */
public final class WindowRootViewKeyEventHandler {
    public final FalsingCollector falsingCollector;
    public final Lazy sysUIKeyEventHandlerLazy;

    public WindowRootViewKeyEventHandler(Lazy lazy, FalsingCollector falsingCollector) {
        this.sysUIKeyEventHandlerLazy = lazy;
        this.falsingCollector = falsingCollector;
    }
}
