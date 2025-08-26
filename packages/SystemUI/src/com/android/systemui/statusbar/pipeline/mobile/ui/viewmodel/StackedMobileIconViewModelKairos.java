package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.KairosBuilder;
import com.android.systemui.KairosBuilderImpl;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.kairos.BuildScope;
import com.android.systemui.kairos.CombineKt;
import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorKairosImpl;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel;
import com.android.systemui.util.composable.kairos.HydratedComposeStateOfKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final class StackedMobileIconViewModelKairos implements KairosBuilder, StackedMobileIconViewModel {
    public final /* synthetic */ KairosBuilderImpl $$delegate_0 = new KairosBuilderImpl();
    public final State dualSim$delegate;
    public final StateInit iconList;
    public final State networkTypeIcon$delegate;

    public interface Factory {
    }

    public StackedMobileIconViewModelKairos(MobileIconsViewModelKairos mobileIconsViewModelKairos) {
        HydratedComposeStateOfKt.hydratedComposeStateOf(this, ((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).isStackable, Boolean.FALSE);
        StateInit stateInitCombine = CombineKt.combine(mobileIconsViewModelKairos.icons, StateKt.map(((MobileIconsInteractorKairosImpl) mobileIconsViewModelKairos.interactor).activeDataIconInteractor, new MobileIconsViewModelKairos$$ExternalSyntheticLambda0(1)), new StackedMobileIconViewModelKairos$$ExternalSyntheticLambda0());
        this.iconList = stateInitCombine;
        this.dualSim$delegate = HydratedComposeStateOfKt.hydratedComposeStateOf(this, StateKt.flatMap(stateInitCombine, new StackedMobileIconViewModelKairos$$ExternalSyntheticLambda1(this, 0)), null);
        this.networkTypeIcon$delegate = HydratedComposeStateOfKt.hydratedComposeStateOf(this, StateKt.flatMap(stateInitCombine, new StackedMobileIconViewModelKairos$$ExternalSyntheticLambda2()), null);
    }

    @Override // com.android.systemui.KairosActivatable
    public final void activate(BuildScope buildScope) {
        this.$$delegate_0.activate(buildScope);
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final StackedMobileIconViewModel.DualSim getDualSim() {
        return (StackedMobileIconViewModel.DualSim) this.dualSim$delegate.getValue();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.StackedMobileIconViewModel
    public final Icon.Resource getNetworkTypeIcon() {
        return (Icon.Resource) this.networkTypeIcon$delegate.getValue();
    }

    @Override // com.android.systemui.KairosBuilder
    public final void onActivated(Function1 function1) {
        this.$$delegate_0.onActivated(function1);
    }
}
