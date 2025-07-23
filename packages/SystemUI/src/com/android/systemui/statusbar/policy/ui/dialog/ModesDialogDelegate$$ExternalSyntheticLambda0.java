package com.android.systemui.statusbar.policy.ui.dialog;

import com.android.systemui.qs.tiles.dialog.QSEnableDndDialogMetricsLogger;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ModesDialogDelegate$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ModesDialogDelegate$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return new QSEnableDndDialogMetricsLogger(((ModesDialogDelegate) this.f$0).context);
            default:
                ((SystemUIDialog) this.f$0).dismiss();
                return Unit.INSTANCE;
        }
    }
}
