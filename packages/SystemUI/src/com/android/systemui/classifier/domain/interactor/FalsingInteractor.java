package com.android.systemui.classifier.domain.interactor;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.FalsingManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FalsingInteractor {
    public final FalsingCollector collector;
    public final FalsingManager manager;

    public FalsingInteractor(FalsingCollector falsingCollector, FalsingManager falsingManager) {
        this.collector = falsingCollector;
        this.manager = falsingManager;
    }
}
