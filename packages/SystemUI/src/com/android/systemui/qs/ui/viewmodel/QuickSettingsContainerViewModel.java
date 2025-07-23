package com.android.systemui.qs.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsContainerViewModel extends ExclusiveActivatable {
    public final BrightnessSliderViewModel brightnessSliderViewModel;
    public final DetailsViewModel detailsViewModel;
    public final EditModeViewModel editModeViewModel;
    public final Float expansion;
    public final Hydrator hydrator;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final ShadeHeaderViewModel shadeHeaderViewModel;
    public final State showHeader$delegate;
    public final State showMedia$delegate;
    public final TileGridViewModel tileGridViewModel;
    public final ToolbarViewModel toolbarViewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QuickSettingsContainerViewModel create(boolean z, Float f);
    }

    public QuickSettingsContainerViewModel(BrightnessSliderViewModel.Factory factory, ShadeHeaderViewModel.Factory factory2, TileGridViewModel.Factory factory3, boolean z, Float f, EditModeViewModel editModeViewModel, DetailsViewModel detailsViewModel, ToolbarViewModel.Factory factory4, ShadeModeInteractor shadeModeInteractor, MediaCarouselInteractor mediaCarouselInteractor, MediaCarouselController mediaCarouselController, MediaHost mediaHost) {
        this.expansion = f;
        this.editModeViewModel = editModeViewModel;
        this.detailsViewModel = detailsViewModel;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = mediaHost;
        Hydrator hydrator = new Hydrator("QuickSettingsContainerViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.brightnessSliderViewModel = factory.create(z);
        this.toolbarViewModel = factory4.create();
        this.shadeHeaderViewModel = factory2.create();
        this.tileGridViewModel = factory3.create();
        Boolean valueOf = Boolean.valueOf(!((Boolean) r9.isShadeLayoutWide.$$delegate_0.getValue()).booleanValue());
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide;
        this.showHeader$delegate = hydrator.hydratedStateOf("showHeader", valueOf, new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        r5 = r5 ^ r3
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.showMedia$delegate = hydrator.hydratedStateOf(mediaCarouselInteractor.hasAnyMediaOrRecommendation, "showMedia");
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
            boolean r0 = r5 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1
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
            com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2 r5 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
