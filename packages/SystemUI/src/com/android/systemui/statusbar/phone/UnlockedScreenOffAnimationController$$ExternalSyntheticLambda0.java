package com.android.systemui.statusbar.phone;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final /* synthetic */ class UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UnlockedScreenOffAnimationController f$0;

    public /* synthetic */ UnlockedScreenOffAnimationController$$ExternalSyntheticLambda0(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, int i) {
        this.$r8$classId = i;
        this.f$0 = unlockedScreenOffAnimationController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(Intrinsics.areEqual(this.f$0.decidedToAnimateGoingToSleep, Boolean.FALSE));
            default:
                this.f$0.decidedToAnimateGoingToSleep = null;
                return Unit.INSTANCE;
        }
    }
}
