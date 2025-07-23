package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.EffectScope;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ToComposeStateKt {
    public static final <T> State<T> toComposeState(BuildScope buildScope, com.android.systemui.kairos.State state) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        final MutableState mutableStateOf$default = SnapshotStateKt.mutableStateOf$default(buildScopeImpl.sample(state));
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, StateKt.getChanges(state), new Function2() { // from class: com.android.systemui.util.composable.kairos.ToComposeStateKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Unit composeState$lambda$0;
                composeState$lambda$0 = ToComposeStateKt.toComposeState$lambda$0(MutableState.this, (EffectScope) obj, obj2);
                return composeState$lambda$0;
            }
        }, 1);
        return mutableStateOf$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toComposeState$lambda$0(MutableState mutableState, EffectScope effectScope, Object obj) {
        mutableState.setValue(obj);
        return Unit.INSTANCE;
    }
}
