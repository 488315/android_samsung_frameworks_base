package com.android.wm.shell.shared.bubbles;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class DismissView$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DismissView f$0;

    public /* synthetic */ DismissView$$ExternalSyntheticLambda0(DismissView dismissView, int i) {
        this.$r8$classId = i;
        this.f$0 = dismissView;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        DismissView dismissView = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                String str = DismissView.TAG;
                dismissView.setVisibility(4);
                dismissView.circle.setScaleX(1.0f);
                dismissView.circle.setScaleY(1.0f);
                break;
            default:
                dismissView.circle.getGlobalVisibleRect(dismissView.dismissArea);
                break;
        }
        return Unit.INSTANCE;
    }
}
