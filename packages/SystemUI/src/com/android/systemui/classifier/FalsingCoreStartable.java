package com.android.systemui.classifier;

import com.android.systemui.CoreStartable;

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
