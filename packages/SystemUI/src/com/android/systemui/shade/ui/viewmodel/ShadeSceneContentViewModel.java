package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.settings.brightness.ui.viewModel.BrightnessMirrorViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeSceneContentViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _isEmptySpaceClickable;
    public final StateFlowImpl _isQsEnabled;
    public final BrightnessMirrorViewModel.Factory brightnessMirrorViewModelFactory;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DisableFlagsInteractor disableFlagsInteractor;
    public final FooterActionsController footerActionsController;
    public final AtomicBoolean footerActionsControllerInitialized;
    public final FooterActionsViewModel.Factory footerActionsViewModelFactory;
    public final ReadonlyStateFlow isEmptySpaceClickable;
    public final ReadonlyStateFlow isMediaVisible;
    public final ReadonlyStateFlow isQsEnabled;
    public final QSSceneAdapter qsSceneAdapter;
    public final SceneInteractor sceneInteractor;
    public final ShadeHeaderViewModel.Factory shadeHeaderViewModelFactory;
    public final ReadonlyStateFlow shadeMode;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public ShadeSceneContentViewModel(QSSceneAdapter qSSceneAdapter, ShadeHeaderViewModel.Factory factory, BrightnessMirrorViewModel.Factory factory2, MediaCarouselInteractor mediaCarouselInteractor, ShadeModeInteractor shadeModeInteractor, DisableFlagsInteractor disableFlagsInteractor, FooterActionsViewModel.Factory factory3, FooterActionsController footerActionsController, UnfoldTransitionInteractor unfoldTransitionInteractor, DeviceEntryInteractor deviceEntryInteractor, SceneInteractor sceneInteractor) {
        this.qsSceneAdapter = qSSceneAdapter;
        this.shadeHeaderViewModelFactory = factory;
        this.brightnessMirrorViewModelFactory = factory2;
        this.disableFlagsInteractor = disableFlagsInteractor;
        this.footerActionsViewModelFactory = factory3;
        this.footerActionsController = footerActionsController;
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneInteractor = sceneInteractor;
        this.shadeMode = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((Boolean) deviceEntryInteractor.isDeviceEntered.$$delegate_0.getValue()).booleanValue()));
        this._isEmptySpaceClickable = MutableStateFlow;
        this.isEmptySpaceClickable = FlowKt.asStateFlow(MutableStateFlow);
        this.isMediaVisible = mediaCarouselInteractor.hasActiveMediaOrRecommendation;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((DisableFlagsModel) disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).isQuickSettingsEnabled()));
        this._isQsEnabled = MutableStateFlow2;
        this.isQsEnabled = FlowKt.asStateFlow(MutableStateFlow2);
        this.footerActionsControllerInitialized = new AtomicBoolean(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1 r0 = (com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1 r0 = new com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2 r5 = new com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
