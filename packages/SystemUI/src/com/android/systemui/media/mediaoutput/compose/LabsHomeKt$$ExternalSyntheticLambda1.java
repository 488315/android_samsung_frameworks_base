package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LabsHomeKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LabsViewModel f$0;

    public /* synthetic */ LabsHomeKt$$ExternalSyntheticLambda1(LabsViewModel labsViewModel, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = labsViewModel;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                LabsHomeKt.ChromecastLabs(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 1:
                LabsHomeKt.DebugLabs(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 2:
                LabsHomeKt.ResetItem(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 3:
                LabsHomeKt.Labs(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 4:
                LabsHomeKt.ActionLabs(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                LabsHomeKt.SmartThingsLabs(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
        }
        return Unit.INSTANCE;
    }
}
