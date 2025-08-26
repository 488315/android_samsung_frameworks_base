package com.android.systemui.animation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class AnimatedDialog$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ AnimatedDialog f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        AnimatedDialog animatedDialog = this.f$0;
        if (zBooleanValue) {
            animatedDialog.dialog.hide();
        }
        animatedDialog.dialog.setDismissOverride(null);
        animatedDialog.dialog.dismiss();
        return Unit.INSTANCE;
    }
}
