package com.android.systemui.classifier;

import com.android.systemui.CoreStartable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
