package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda3(int i, int i2, Object obj, Object obj2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Composer composer = (Composer) obj;
        Integer num = (Integer) obj2;
        switch (this.$r8$classId) {
            case 0:
                num.intValue();
                CommunalHubKt.ObserveScrollEffect((LazyGridState) this.f$0, (BaseCommunalViewModel) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            case 1:
                num.getClass();
                CommunalHubKt.PendingWidgetPlaceholder((CommunalContentModel.WidgetContent.PendingWidget) this.f$0, (Modifier) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            default:
                num.getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1);
                CommunalHubKt.AccessibilityContainer((BaseCommunalViewModel) this.f$1, (ComposableLambdaImpl) this.f$0, composer, updateChangedFlags);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda3(BaseCommunalViewModel baseCommunalViewModel, ComposableLambdaImpl composableLambdaImpl, int i) {
        this.$r8$classId = 2;
        this.f$1 = baseCommunalViewModel;
        this.f$0 = composableLambdaImpl;
        this.f$2 = i;
    }
}
