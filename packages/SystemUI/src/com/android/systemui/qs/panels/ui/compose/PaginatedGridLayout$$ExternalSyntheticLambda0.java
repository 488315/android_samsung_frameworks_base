package com.android.systemui.qs.panels.ui.compose;

import androidx.compose.foundation.pager.PagerState;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class PaginatedGridLayout$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PaginatedGridLayout$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return ((PaginatedGridLayout) this.f$0).viewModelFactory.create();
            case 1:
                return Integer.valueOf(((List) this.f$0).size());
            case 2:
                Boolean bool = (Boolean) ((Function0) this.f$0).invoke();
                bool.booleanValue();
                return bool;
            default:
                return Boolean.valueOf(((PagerState) this.f$0).getCurrentPage() == 0);
        }
    }
}
