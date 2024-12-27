package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.spatial.domain.SpatialAudioAvailabilityCriteria;
import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

public final class SpatialAudioViewModel {
    public final Context context;
    public final SpatialAudioComponentInteractor interactor;
    public final ReadonlyStateFlow isAvailable;
    public final CoroutineScope scope;
    public final ReadonlyStateFlow shouldUsePopup;
    public final ReadonlyStateFlow spatialAudioButton;
    public final ReadonlyStateFlow spatialAudioButtons;
    public final Icon.Resource spatialSpeakerIcon = new Icon.Resource(R.drawable.ic_spatial_speaker, null);
    public final UiEventLogger uiEventLogger;

    public SpatialAudioViewModel(Context context, CoroutineScope coroutineScope, SpatialAudioAvailabilityCriteria spatialAudioAvailabilityCriteria, SpatialAudioComponentInteractor spatialAudioComponentInteractor, UiEventLogger uiEventLogger) {
        this.context = context;
        this.scope = coroutineScope;
        this.interactor = spatialAudioComponentInteractor;
        this.uiEventLogger = uiEventLogger;
        ReadonlyStateFlow readonlyStateFlow = spatialAudioComponentInteractor.isEnabled;
        SpatialAudioViewModel$spatialAudioButton$1 spatialAudioViewModel$spatialAudioButton$1 = new SpatialAudioViewModel$spatialAudioButton$1(this, null);
        final ReadonlyStateFlow readonlyStateFlow2 = spatialAudioComponentInteractor.isAvailable;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, spatialAudioViewModel$spatialAudioButton$1);
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.spatialAudioButton = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, null);
        this.shouldUsePopup = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel r5 = (com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel) r5
                        boolean r5 = r5 instanceof com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel.HeadTracking
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, Boolean.FALSE);
        this.isAvailable = FlowKt.stateIn(spatialAudioAvailabilityCriteria.isAvailable(), coroutineScope, startedEagerly, Boolean.TRUE);
        this.spatialAudioButtons = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow, readonlyStateFlow2, new SpatialAudioViewModel$spatialAudioButtons$1(this, null)), coroutineScope, startedEagerly, EmptyList.INSTANCE);
    }

    public static final ButtonViewModel access$toViewModel(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioEnabledModel spatialAudioEnabledModel, boolean z, boolean z2) {
        spatialAudioViewModel.getClass();
        boolean z3 = spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.HeadTrackingEnabled;
        Icon.Resource resource = spatialAudioViewModel.spatialSpeakerIcon;
        if (z3) {
            if (z2) {
                resource = new Icon.Resource(R.drawable.ic_head_tracking, null);
            }
            String string = spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_tracking);
            Intrinsics.checkNotNull(string);
            return new ButtonViewModel(resource, string, z);
        }
        if (spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.SpatialAudioEnabled) {
            if (z2) {
                resource = new Icon.Resource(R.drawable.ic_spatial_audio, null);
            }
            String string2 = spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_fixed);
            Intrinsics.checkNotNull(string2);
            return new ButtonViewModel(resource, string2, z);
        }
        if (!(spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.Disabled)) {
            throw new IllegalStateException(("Unsupported model: " + spatialAudioEnabledModel).toString());
        }
        if (z2) {
            resource = new Icon.Resource(R.drawable.ic_spatial_audio_off, null);
        }
        String string3 = spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_off);
        Intrinsics.checkNotNull(string3);
        return new ButtonViewModel(resource, string3, z);
    }

    public final void setEnabled(SpatialAudioEnabledModel spatialAudioEnabledModel) {
        this.uiEventLogger.logWithPosition(VolumePanelUiEvent.VOLUME_PANEL_SPATIAL_AUDIO_TOGGLE_CLICKED, 0, (String) null, spatialAudioEnabledModel.equals(SpatialAudioEnabledModel.Disabled.INSTANCE) ? 0 : spatialAudioEnabledModel.equals(SpatialAudioEnabledModel.SpatialAudioEnabled.Companion) ? 1 : spatialAudioEnabledModel.equals(SpatialAudioEnabledModel.HeadTrackingEnabled.INSTANCE) ? 2 : -1);
        BuildersKt.launch$default(this.scope, null, null, new SpatialAudioViewModel$setEnabled$1(this, spatialAudioEnabledModel, null), 3);
    }
}
