package com.android.systemui.unfold.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Optional;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

public final class UnfoldTransitionRepositoryImpl implements UnfoldTransitionRepository {
    public final Optional unfoldProgressProvider;

    public UnfoldTransitionRepositoryImpl(Optional<UnfoldTransitionProgressProvider> optional) {
        this.unfoldProgressProvider = optional;
    }

    public final Flow getTransitionStatus() {
        UnfoldTransitionProgressProvider unfoldTransitionProgressProvider = (UnfoldTransitionProgressProvider) this.unfoldProgressProvider.orElse(null);
        if (unfoldTransitionProgressProvider == null) {
            return EmptyFlow.INSTANCE;
        }
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UnfoldTransitionRepositoryImpl$transitionStatus$1 unfoldTransitionRepositoryImpl$transitionStatus$1 = new UnfoldTransitionRepositoryImpl$transitionStatus$1(unfoldTransitionProgressProvider, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(unfoldTransitionRepositoryImpl$transitionStatus$1);
    }
}
