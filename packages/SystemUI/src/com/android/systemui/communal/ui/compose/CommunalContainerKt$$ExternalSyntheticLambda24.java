package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.communal.data.repository.CommunalSceneRepositoryImpl;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalContainerKt$$ExternalSyntheticLambda24 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CommunalContainerKt$$ExternalSyntheticLambda24(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(!((Boolean) ((ShadeInteractorImpl) ((CommunalViewModel) this.f$0).shadeInteractor).isAnyFullyExpanded.$$delegate_0.getValue()).booleanValue());
            case 1:
                final CommunalViewModel communalViewModel = (CommunalViewModel) this.f$0;
                return new DisposableEffectResult() { // from class: com.android.systemui.communal.ui.compose.CommunalContainerKt$ObserveOrientationChange$lambda$47$lambda$46$$inlined$onDispose$1
                    @Override // androidx.compose.runtime.DisposableEffectResult
                    public final void dispose() {
                        ((CommunalSceneRepositoryImpl) CommunalViewModel.this.communalSceneInteractor.repository)._communalContainerOrientation.updateState(null, 0);
                    }
                };
            default:
                SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
                if (!((Boolean) ((MutableState) this.f$0).getValue()).booleanValue()) {
                    SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
                }
                return Unit.INSTANCE;
        }
    }
}
