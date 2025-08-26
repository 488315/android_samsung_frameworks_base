package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalContainerKt$$ExternalSyntheticLambda33 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BoxScopeInstance f$0;

    public /* synthetic */ CommunalContainerKt$$ExternalSyntheticLambda33(BoxScopeInstance boxScopeInstance, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = boxScopeInstance;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                CommunalContainerKt.Scrimmed(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
            case 1:
                CommunalContainerKt.BackgroundTopScrim(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
            case 2:
                CommunalContainerKt.AnimatedLinearGradient(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
            case 3:
                CommunalContainerKt.StaticLinearGradient(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
            default:
                CommunalContainerKt.Background(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
        }
        return Unit.INSTANCE;
    }
}
