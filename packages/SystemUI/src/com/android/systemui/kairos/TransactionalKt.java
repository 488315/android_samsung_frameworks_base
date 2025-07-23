package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.EvalScope;
import com.android.systemui.kairos.internal.TransactionalImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TransactionalKt {
    public static final Transactional transactionally(final Function1 function1) {
        return new Transactional(StateKt.stateOf(new TransactionalImpl.Impl(new Function1() { // from class: com.android.systemui.kairos.TransactionalKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Function1.this.mo779invoke((EvalScope) obj);
            }
        })));
    }
}
