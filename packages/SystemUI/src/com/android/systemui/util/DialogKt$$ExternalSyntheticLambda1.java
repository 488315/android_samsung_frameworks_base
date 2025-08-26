package com.android.systemui.util;

import android.app.Dialog;
import android.view.View;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class DialogKt$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DialogKt$$ExternalSyntheticLambda1(Dialog dialog) {
        this.f$0 = dialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                return DialogKt.registerAnimationOnBackInvoked$lambda$2((Dialog) obj);
            default:
                return DialogKt.registerAnimationOnBackInvoked$lambda$0((View) obj);
        }
    }

    public /* synthetic */ DialogKt$$ExternalSyntheticLambda1(View view) {
        this.f$0 = view;
    }
}
