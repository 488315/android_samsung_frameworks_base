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

/* loaded from: classes3.dex */
public final class ToComposeStateKt {
    public static final <T> State<T> toComposeState(BuildScope buildScope, com.android.systemui.kairos.State state) {
        BuildScopeImpl buildScopeImpl = (BuildScopeImpl) buildScope;
        final MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(buildScopeImpl.sample(state));
        BuildScope.DefaultImpls.observe$default(buildScopeImpl, StateKt.getChanges(state), new Function2() { // from class: com.android.systemui.util.composable.kairos.ToComposeStateKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ToComposeStateKt.toComposeState$lambda$0(mutableStateMutableStateOf$default, (EffectScope) obj, obj2);
            }
        }, 1);
        return mutableStateMutableStateOf$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit toComposeState$lambda$0(MutableState mutableState, EffectScope effectScope, Object obj) {
        mutableState.setValue(obj);
        return Unit.INSTANCE;
    }
}
