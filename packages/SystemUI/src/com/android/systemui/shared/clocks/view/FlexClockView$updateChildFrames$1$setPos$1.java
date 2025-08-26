package com.android.systemui.shared.clocks.view;

import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class FlexClockView$updateChildFrames$1$setPos$1 extends FunctionReferenceImpl implements Function4 {
    public FlexClockView$updateChildFrames$1$setPos$1(Object obj) {
        super(4, obj, SimpleDigitalClockTextView.class, "layout", "layout(IIII)V", 0);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        ((SimpleDigitalClockTextView) this.receiver).layout(((Number) obj).intValue(), ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
        return Unit.INSTANCE;
    }
}
