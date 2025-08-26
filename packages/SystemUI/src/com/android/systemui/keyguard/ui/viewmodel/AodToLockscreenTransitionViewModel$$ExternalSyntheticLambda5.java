package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$FloatRef;

/* loaded from: classes2.dex */
public final /* synthetic */ class AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda5 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda5(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) ((ShadeInteractor) this.f$1);
                ((AodToLockscreenTransitionViewModel) this.f$0).isShadeExpanded = ((Number) shadeInteractorImpl.baseShadeInteractor.getShadeExpansion().getValue()).floatValue() > 0.0f || ((Number) shadeInteractorImpl.baseShadeInteractor.getQsExpansion().getValue()).floatValue() > 0.0f;
                break;
            default:
                ((Ref$FloatRef) this.f$0).element = ((Number) ((ViewStateAccessor) this.f$1).alpha.invoke()).floatValue();
                break;
        }
        return Unit.INSTANCE;
    }
}
