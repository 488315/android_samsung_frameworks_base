package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class InfiniteGridLayout$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InfiniteGridLayout f$0;

    public /* synthetic */ InfiniteGridLayout$$ExternalSyntheticLambda0(InfiniteGridLayout infiniteGridLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = infiniteGridLayout;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
        }
        return this.f$0.viewModelFactory.create();
    }
}
