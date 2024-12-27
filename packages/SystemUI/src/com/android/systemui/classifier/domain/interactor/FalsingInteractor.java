package com.android.systemui.classifier.domain.interactor;

import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.FalsingManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class FalsingInteractor {
    public final FalsingCollector collector;
    public final FalsingManager manager;

    public FalsingInteractor(FalsingCollector falsingCollector, FalsingManager falsingManager) {
        this.collector = falsingCollector;
        this.manager = falsingManager;
    }
}
