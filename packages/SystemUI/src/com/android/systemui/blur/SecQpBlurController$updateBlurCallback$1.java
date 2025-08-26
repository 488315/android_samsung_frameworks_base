package com.android.systemui.blur;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class SecQpBlurController$updateBlurCallback$1 extends FunctionReferenceImpl implements Function1 {
    public SecQpBlurController$updateBlurCallback$1(Object obj) {
        super(1, obj, SecQpBlurController.class, "doFrame", "doFrame(J)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((Number) obj).longValue();
        SecQpBlurController.access$doFrame((SecQpBlurController) this.receiver);
        return Unit.INSTANCE;
    }
}
