package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.viewmodel.InfiniteGridViewModel;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class InfiniteGridLayout$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InfiniteGridViewModel f$0;

    public /* synthetic */ InfiniteGridLayout$$ExternalSyntheticLambda1(InfiniteGridViewModel infiniteGridViewModel, int i) {
        this.$r8$classId = i;
        this.f$0 = infiniteGridViewModel;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.dynamicIconTilesViewModelFactory.create();
            case 1:
                return this.f$0.columnsWithMediaViewModelFactory.createWithoutMediaTracking();
            case 2:
                return this.f$0.dynamicIconTilesViewModelFactory.create();
            default:
                return this.f$0.columnsWithMediaViewModelFactory.create(0);
        }
    }
}
