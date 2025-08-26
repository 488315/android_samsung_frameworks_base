package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class OngoingCardController$$ExternalSyntheticLambda4 implements Function0 {
    public final /* synthetic */ Function0 f$0;
    public final /* synthetic */ OngoingCardController f$1;

    public /* synthetic */ OngoingCardController$$ExternalSyntheticLambda4(Function0 function0, OngoingCardController ongoingCardController) {
        this.f$0 = function0;
        this.f$1 = ongoingCardController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        this.f$0.invoke();
        Log.i("{OngoingExpandedPipController}", "runCardRemoveAnimation call onAllowStateChanged(false)");
        this.f$1.onAllowStateChanged(false);
        return Unit.INSTANCE;
    }
}
