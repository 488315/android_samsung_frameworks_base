package com.android.systemui.unfold.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Optional;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
