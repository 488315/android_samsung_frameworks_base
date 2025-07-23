package com.android.systemui.qs.footer.ui.viewmodel;

import android.content.Context;
import androidx.lifecycle.LifecycleCoroutineScope;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import javax.inject.Provider;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FooterActionsViewModel {
    public final StateFlowImpl _alpha;
    public final StateFlowImpl _backgroundAlpha;
    public final ReadonlyStateFlow alpha;
    public final ReadonlyStateFlow backgroundAlpha;
    public final Flow foregroundServices;
    public final Function0 initialPower;
    public final Function2 observeDeviceMonitoringDialogRequests;
    public final Flow power;
    public final Flow security;
    public final FooterActionsButtonViewModel settings;
    public final Flow userSwitcher;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
        public final ActivityStarter activityStarter;
        public final Context context;
        public final FalsingManager falsingManager;
        public final FooterActionsInteractor footerActionsInteractor;
        public final Provider globalActionsDialogLiteProvider;
        public final ShadeModeInteractor shadeModeInteractor;
        public final boolean showPowerButton;

        public Factory(Context context, FalsingManager falsingManager, FooterActionsInteractor footerActionsInteractor, ShadeModeInteractor shadeModeInteractor, Provider provider, ActivityStarter activityStarter, boolean z) {
            this.context = context;
            this.falsingManager = falsingManager;
            this.footerActionsInteractor = footerActionsInteractor;
            this.shadeModeInteractor = shadeModeInteractor;
            this.globalActionsDialogLiteProvider = provider;
            this.activityStarter = activityStarter;
            this.showPowerButton = z;
        }

        public final FooterActionsViewModel create(LifecycleCoroutineScope lifecycleCoroutineScope) {
            GlobalActionsDialogLite globalActionsDialogLite = (GlobalActionsDialogLite) this.globalActionsDialogLiteProvider.get();
            if (CoroutineScopeKt.isActive(lifecycleCoroutineScope)) {
                CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScope, null, CoroutineStart.ATOMIC, new FooterActionsViewModel$Factory$create$2(globalActionsDialogLite, null), 3);
            } else {
                globalActionsDialogLite.destroy();
            }
            Context context = this.context;
            ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) this.shadeModeInteractor).shadeMode;
            globalActionsDialogLite.getClass();
            return FooterActionsViewModelKt.createFooterActionsViewModel(context, this.footerActionsInteractor, readonlyStateFlow, this.falsingManager, globalActionsDialogLite, this.activityStarter, this.showPowerButton);
        }
    }

    public FooterActionsViewModel(Flow flow, Flow flow2, Flow flow3, FooterActionsButtonViewModel footerActionsButtonViewModel, Flow flow4, Function0 function0, Function2 function2) {
        this.security = flow;
        this.foregroundServices = flow2;
        this.userSwitcher = flow3;
        this.settings = footerActionsButtonViewModel;
        this.power = flow4;
        this.initialPower = function0;
        this.observeDeviceMonitoringDialogRequests = function2;
        Float valueOf = Float.valueOf(1.0f);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(valueOf);
        this._alpha = MutableStateFlow;
        this.alpha = FlowKt.asStateFlow(MutableStateFlow);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(valueOf);
        this._backgroundAlpha = MutableStateFlow2;
        this.backgroundAlpha = FlowKt.asStateFlow(MutableStateFlow2);
    }
}
