package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.TransactionalImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public abstract class TransactionalKt {
    public static final Transactional transactionally(final Function1 function1) {
        return new Transactional(StateKt.stateOf(new TransactionalImpl.Impl(new Function1() { // from class: com.android.systemui.kairos.TransactionalKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return function1.mo781invoke((EvalScope) obj);
            }
        })));
    }
}
