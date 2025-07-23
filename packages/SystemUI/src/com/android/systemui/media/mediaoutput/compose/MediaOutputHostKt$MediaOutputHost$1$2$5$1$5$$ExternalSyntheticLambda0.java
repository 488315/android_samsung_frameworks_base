package com.android.systemui.media.mediaoutput.compose;

import androidx.navigation.NavHostController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavHostController f$0;

    public /* synthetic */ MediaOutputHostKt$MediaOutputHost$1$2$5$1$5$$ExternalSyntheticLambda0(NavHostController navHostController, int i) {
        this.$r8$classId = i;
        this.f$0 = navHostController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.popBackStack();
                break;
            case 1:
                this.f$0.popBackStack();
                break;
            default:
                this.f$0.popBackStack();
                break;
        }
        return Unit.INSTANCE;
    }
}
