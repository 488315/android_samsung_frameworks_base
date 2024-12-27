package com.android.server.location.provider;

import android.location.provider.ProviderProperties;

import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AbstractLocationProvider$$ExternalSyntheticLambda1
        implements UnaryOperator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AbstractLocationProvider$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((AbstractLocationProvider.State) obj)
                        .withProperties((ProviderProperties) obj2);
            default:
                AbstractLocationProvider.Listener listener =
                        (AbstractLocationProvider.Listener) obj2;
                AbstractLocationProvider.InternalState internalState =
                        (AbstractLocationProvider.InternalState) obj;
                return listener == internalState.listener
                        ? internalState
                        : new AbstractLocationProvider.InternalState(listener, internalState.state);
        }
    }
}
