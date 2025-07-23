package com.android.systemui.classifier;

import com.android.systemui.CoreStartable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FalsingCoreStartable implements CoreStartable {
    public final FalsingCollector falsingCollector;

    public FalsingCoreStartable(FalsingCollector falsingCollector) {
        this.falsingCollector = falsingCollector;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.falsingCollector.init();
    }
}
