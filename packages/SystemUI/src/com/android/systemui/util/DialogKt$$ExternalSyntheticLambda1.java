package com.android.systemui.util;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.View;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DialogKt$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DialogKt$$ExternalSyntheticLambda1(Dialog dialog) {
        this.f$0 = dialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Unit registerAnimationOnBackInvoked$lambda$2;
        DisplayMetrics registerAnimationOnBackInvoked$lambda$0;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                registerAnimationOnBackInvoked$lambda$2 = DialogKt.registerAnimationOnBackInvoked$lambda$2((Dialog) obj);
                return registerAnimationOnBackInvoked$lambda$2;
            default:
                registerAnimationOnBackInvoked$lambda$0 = DialogKt.registerAnimationOnBackInvoked$lambda$0((View) obj);
                return registerAnimationOnBackInvoked$lambda$0;
        }
    }

    public /* synthetic */ DialogKt$$ExternalSyntheticLambda1(View view) {
        this.f$0 = view;
    }
}
