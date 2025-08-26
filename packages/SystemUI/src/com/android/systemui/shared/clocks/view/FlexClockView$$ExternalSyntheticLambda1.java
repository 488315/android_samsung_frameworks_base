package com.android.systemui.shared.clocks.view;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class FlexClockView$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ FlexClockView f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ FlexClockView$$ExternalSyntheticLambda1(FlexClockView flexClockView, boolean z, boolean z2) {
        this.f$0 = flexClockView;
        this.f$1 = z;
        this.f$2 = z2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        FlexClockView.animateDoze$executeDozeAnimation(this.f$0, this.f$1, this.f$2);
        return Unit.INSTANCE;
    }
}
