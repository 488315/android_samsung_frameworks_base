package com.android.systemui.qs.composefragment;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QSFragmentCompose f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda3(QSFragmentCompose qSFragmentCompose, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = qSFragmentCompose;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        QSFragmentCompose qSFragmentCompose = this.f$0;
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                int i2 = QSFragmentCompose.$r8$clinit;
                qSFragmentCompose.Content$1(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
            default:
                int i3 = QSFragmentCompose.$r8$clinit;
                qSFragmentCompose.CollapsableQuickSettingsSTL(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
        }
        return Unit.INSTANCE;
    }
}
