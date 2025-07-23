package com.android.systemui.qs.ui.viewmodel;

import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.settings.brightness.ui.viewModel.BrightnessMirrorViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsSceneContentViewModel extends ExclusiveActivatable {
    public final BrightnessMirrorViewModel.Factory brightnessMirrorViewModelFactory;
    public final FooterActionsController footerActionsController;
    public final AtomicBoolean footerActionsControllerInitialized = new AtomicBoolean(false);
    public final FooterActionsViewModel.Factory footerActionsViewModelFactory;
    public final ReadonlyStateFlow isMediaVisible;
    public final QSSceneAdapter qsSceneAdapter;
    public final SceneInteractor sceneInteractor;
    public final ShadeHeaderViewModel.Factory shadeHeaderViewModelFactory;
    public final ShadeModeInteractor shadeModeInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
    }

    public QuickSettingsSceneContentViewModel(BrightnessMirrorViewModel.Factory factory, ShadeHeaderViewModel.Factory factory2, QSSceneAdapter qSSceneAdapter, FooterActionsViewModel.Factory factory3, FooterActionsController footerActionsController, MediaCarouselInteractor mediaCarouselInteractor, ShadeModeInteractor shadeModeInteractor, SceneInteractor sceneInteractor) {
        this.brightnessMirrorViewModelFactory = factory;
        this.shadeHeaderViewModelFactory = factory2;
        this.qsSceneAdapter = qSSceneAdapter;
        this.footerActionsViewModelFactory = factory3;
        this.footerActionsController = footerActionsController;
        this.shadeModeInteractor = shadeModeInteractor;
        this.sceneInteractor = sceneInteractor;
        this.isMediaVisible = mediaCarouselInteractor.hasAnyMediaOrRecommendation;
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
            boolean r0 = r5 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$1
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
            com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$2 r5 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel$onActivated$2
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsSceneContentViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
