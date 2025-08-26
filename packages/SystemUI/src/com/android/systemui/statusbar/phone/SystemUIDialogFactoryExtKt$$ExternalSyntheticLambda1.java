package com.android.systemui.statusbar.phone;

import android.app.Dialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SystemUIDialogFactoryExtKt$$ExternalSyntheticLambda1(Dialog dialog) {
        this.f$0 = dialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((Dialog) this.f$0).dismiss();
                break;
            default:
                ((SystemUIDialog) this.f$0).dismiss();
                break;
        }
        return Unit.INSTANCE;
    }
}
