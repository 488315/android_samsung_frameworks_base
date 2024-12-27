package com.android.server.policy;

import java.util.function.Consumer;

public final /* synthetic */ class KeyCombinationManager$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((KeyCombinationManager.TwoKeysCombinationRule) obj).cancel();
    }
}
