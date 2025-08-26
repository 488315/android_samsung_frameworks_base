package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda24 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BaseCommunalViewModel f$0;
    public final /* synthetic */ Modifier f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda24(BaseCommunalViewModel baseCommunalViewModel, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = baseCommunalViewModel;
        this.f$1 = modifier;
        this.f$2 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                CommunalHubKt.CtaTileInViewModeContent(this.f$0, this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            default:
                CommunalHubKt.UmoLegacy(this.f$0, this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
