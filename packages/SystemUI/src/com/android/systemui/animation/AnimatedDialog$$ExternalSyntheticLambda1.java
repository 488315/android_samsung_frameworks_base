package com.android.systemui.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AnimatedDialog$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ AnimatedDialog f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        AnimatedDialog animatedDialog = this.f$0;
        if (booleanValue) {
            animatedDialog.dialog.hide();
        }
        animatedDialog.dialog.setDismissOverride(null);
        animatedDialog.dialog.dismiss();
        return Unit.INSTANCE;
    }
}
