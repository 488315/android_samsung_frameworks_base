package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.lazy.grid.LazyGridState;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda8 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda8(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((Function0) this.f$0).invoke();
                return Unit.INSTANCE;
            case 1:
                ((BaseCommunalViewModel) this.f$0).onOpenWidgetEditor(true);
                return Unit.INSTANCE;
            default:
                LazyGridState lazyGridState = (LazyGridState) this.f$0;
                return new Pair(Integer.valueOf(lazyGridState.scrollPosition.getIndex()), Integer.valueOf(lazyGridState.scrollPosition.getScrollOffset()));
        }
    }
}
