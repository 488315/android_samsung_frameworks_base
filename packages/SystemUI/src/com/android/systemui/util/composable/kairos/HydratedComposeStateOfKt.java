package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.KairosBuilder;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.EffectScope;
import com.android.systemui.kairos.internal.BuildScopeImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class HydratedComposeStateOfKt {
    public static final <T> State<T> hydratedComposeStateOf(KairosBuilder kairosBuilder, final com.android.systemui.kairos.State state, T t) {
        final MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(t);
        kairosBuilder.onActivated(new Function1() { // from class: com.android.systemui.util.composable.kairos.HydratedComposeStateOfKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return HydratedComposeStateOfKt.hydratedComposeStateOf$lambda$2$lambda$1(state, mutableStateMutableStateOf$default, (BuildScope) obj);
            }
        });
        return mutableStateMutableStateOf$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hydratedComposeStateOf$lambda$2$lambda$1(com.android.systemui.kairos.State state, final MutableState mutableState, BuildScope buildScope) {
        ((BuildScopeImpl) buildScope).observe(state, new Function2() { // from class: com.android.systemui.util.composable.kairos.HydratedComposeStateOfKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return HydratedComposeStateOfKt.hydratedComposeStateOf$lambda$2$lambda$1$lambda$0(mutableState, (EffectScope) obj, obj2);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hydratedComposeStateOf$lambda$2$lambda$1$lambda$0(MutableState mutableState, EffectScope effectScope, Object obj) {
        mutableState.setValue(obj);
        return Unit.INSTANCE;
    }
}
