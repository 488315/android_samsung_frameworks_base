package com.android.server.location.provider;

import java.util.function.UnaryOperator;

public final /* synthetic */ class AbstractLocationProvider$$ExternalSyntheticLambda0
        implements UnaryOperator {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ AbstractLocationProvider$$ExternalSyntheticLambda0(boolean z) {
        this.f$0 = z;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        boolean z = this.f$0;
        AbstractLocationProvider.State state = (AbstractLocationProvider.State) obj;
        return z == state.allowed
                ? state
                : new AbstractLocationProvider.State(
                        z, state.properties, state.identity, state.extraAttributionTags);
    }
}
