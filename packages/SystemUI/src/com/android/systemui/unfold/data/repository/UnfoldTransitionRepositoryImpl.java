package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Optional;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class UnfoldTransitionRepositoryImpl implements UnfoldTransitionRepository {
    public final Optional unfoldProgressProvider;

    public UnfoldTransitionRepositoryImpl(Optional<UnfoldTransitionProgressProvider> optional) {
        this.unfoldProgressProvider = optional;
    }

    public final Flow getTransitionStatus() {
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) this.unfoldProgressProvider.orElse(null);
        return unfoldTransitionProgressProvider == null ? EmptyFlow.INSTANCE : FlowConflatedKt.conflatedCallbackFlow(new UnfoldTransitionRepositoryImpl$transitionStatus$1(unfoldTransitionProgressProvider, null));
    }
}
