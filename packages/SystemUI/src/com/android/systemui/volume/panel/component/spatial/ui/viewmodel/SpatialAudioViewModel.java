package com.android.systemui.volume.panel.component.spatial.ui.viewmodel;

import android.content.Context;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.volume.panel.component.button.ui.viewmodel.ButtonViewModel;
import com.android.systemui.volume.panel.component.spatial.domain.SpatialAudioAvailabilityCriteria;
import com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes3.dex */
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

    /* renamed from: com.android.systemui.volume.panel.component.spatial.ui.viewmodel.SpatialAudioViewModel$setEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SpatialAudioEnabledModel $model;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SpatialAudioEnabledModel spatialAudioEnabledModel, Continuation continuation) {
            super(2, continuation);
            this.$model = spatialAudioEnabledModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SpatialAudioViewModel.this.new AnonymousClass1(this.$model, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SpatialAudioComponentInteractor spatialAudioComponentInteractor = SpatialAudioViewModel.this.interactor;
                SpatialAudioEnabledModel spatialAudioEnabledModel = this.$model;
                this.label = 1;
                if (spatialAudioComponentInteractor.setEnabled(spatialAudioEnabledModel, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((SpatialAudioAvailabilityModel) obj) instanceof SpatialAudioAvailabilityModel.HeadTracking);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, Boolean.FALSE);
        this.isAvailable = FlowKt.stateIn(spatialAudioAvailabilityCriteria.isAvailable(), coroutineScope, startedEagerly, Boolean.TRUE);
        this.spatialAudioButtons = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(spatialAudioComponentInteractor.isEnabled, readonlyStateFlow2, new SpatialAudioViewModel$spatialAudioButtons$1(this, null)), coroutineScope, startedEagerly, EmptyList.INSTANCE);
    }

    public static final ButtonViewModel access$toViewModel(SpatialAudioViewModel spatialAudioViewModel, SpatialAudioEnabledModel spatialAudioEnabledModel, boolean z, boolean z2) {
        spatialAudioViewModel.getClass();
        boolean z3 = spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.HeadTrackingEnabled;
        Icon.Resource resource = spatialAudioViewModel.spatialSpeakerIcon;
        if (z3) {
            if (z2) {
                resource = new Icon.Resource(R.drawable.ic_head_tracking, null);
            }
            return new ButtonViewModel(resource, spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_tracking), z);
        }
        if (spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.SpatialAudioEnabled) {
            if (z2) {
                resource = new Icon.Resource(R.drawable.ic_spatial_audio, null);
            }
            return new ButtonViewModel(resource, spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_fixed), z);
        }
        if (spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.Disabled) {
            if (z2) {
                resource = new Icon.Resource(R.drawable.ic_spatial_audio_off, null);
            }
            return new ButtonViewModel(resource, spatialAudioViewModel.context.getString(R.string.volume_panel_spatial_audio_off), z);
        }
        throw new IllegalStateException(("Unsupported model: " + spatialAudioEnabledModel).toString());
    }

    public final void setEnabled(SpatialAudioEnabledModel spatialAudioEnabledModel) {
        this.uiEventLogger.logWithPosition(VolumePanelUiEvent.VOLUME_PANEL_SPATIAL_AUDIO_TOGGLE_CLICKED, 0, (String) null, Intrinsics.areEqual(spatialAudioEnabledModel, SpatialAudioEnabledModel.Disabled.INSTANCE) ? 0 : Intrinsics.areEqual(spatialAudioEnabledModel, SpatialAudioEnabledModel.SpatialAudioEnabled.Companion) ? 1 : Intrinsics.areEqual(spatialAudioEnabledModel, SpatialAudioEnabledModel.HeadTrackingEnabled.INSTANCE) ? 2 : -1);
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(spatialAudioEnabledModel, null), 7);
    }
}
