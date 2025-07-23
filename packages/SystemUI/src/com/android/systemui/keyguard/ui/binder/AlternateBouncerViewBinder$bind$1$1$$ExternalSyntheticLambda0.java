package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.SwipeUpAnywhereGestureHandler;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.statusbar.gesture.TapGestureDetector;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class AlternateBouncerViewBinder$bind$1$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AlternateBouncerViewBinder$bind$1$1$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((SwipeUpAnywhereGestureHandler) this.f$0).removeOnGestureDetectedCallback("AlternateBouncer-SWIPE");
                ((TapGestureDetector) this.f$1).removeOnGestureDetectedCallback("AlternateBouncer-TAP");
                break;
            case 1:
                PowerInteractor.onUserTouch$default(((AlternateBouncerDependencies) this.f$0).powerInteractor);
                ((AlternateBouncerViewModel) this.f$1).statusBarKeyguardViewManager.showPrimaryBouncer("AlternateBouncerViewModel#onTapped", true);
                break;
            default:
                PowerInteractor.onUserTouch$default(((AlternateBouncerDependencies) this.f$0).powerInteractor);
                ((AlternateBouncerViewModel) this.f$1).statusBarKeyguardViewManager.showPrimaryBouncer("AlternateBouncerViewModel#onTapped", true);
                break;
        }
        return Unit.INSTANCE;
    }
}
