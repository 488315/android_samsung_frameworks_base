package com.android.systemui.dreams.homecontrols;

import com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class HomeControlsDreamServiceImpl$$ExternalSyntheticLambda1 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HomeControlsDreamServiceImpl f$0;

    public /* synthetic */ HomeControlsDreamServiceImpl$$ExternalSyntheticLambda1(HomeControlsDreamServiceImpl homeControlsDreamServiceImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = homeControlsDreamServiceImpl;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                HomeControlsDreamServiceImpl.Companion companion = HomeControlsDreamServiceImpl.Companion;
                homeControlsDreamServiceImpl.endDream(false);
                return Unit.INSTANCE;
            default:
                return homeControlsDreamServiceImpl.wakeLockBuilder.setMaxTimeout(-1L).setTag("HomeControlsDreamServiceImpl").setLevelsAndFlags(10).build();
        }
    }
}
