package com.android.systemui.classifier.domain.interactor;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.FalsingManager;

public final class FalsingInteractor {
    public final FalsingCollector collector;
    public final FalsingManager manager;

    public FalsingInteractor(FalsingCollector falsingCollector, FalsingManager falsingManager) {
        this.collector = falsingCollector;
        this.manager = falsingManager;
    }
}
