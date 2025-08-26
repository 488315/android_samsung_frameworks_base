package com.android.systemui.volume.dialog.sliders.domain.interactor;

import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.statusbar.policy.domain.model.ActiveZenModes;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class VolumeDialogSliderInteractor {
    public final Flow isDisabledByZenMode;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 slider;
    public final VolumeDialogSliderType sliderType;
    public final VolumeDialogController volumeDialogController;

    /* JADX WARN: Removed duplicated region for block: B:7:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public VolumeDialogSliderInteractor(VolumeDialogSliderType volumeDialogSliderType, CoroutineScope coroutineScope, VolumeDialogStateInteractor volumeDialogStateInteractor, VolumeDialogController volumeDialogController, ZenModeInteractor zenModeInteractor) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.sliderType = volumeDialogSliderType;
        this.volumeDialogController = volumeDialogController;
        if (volumeDialogSliderType instanceof VolumeDialogSliderType.Stream) {
            int i = ((VolumeDialogSliderType.Stream) volumeDialogSliderType).audioStream;
            AudioStream.m991constructorimpl(i);
            if (zenModeInteractor.zenModeByStreamPredicates.containsKey(Integer.valueOf(i))) {
                int i2 = ((VolumeDialogSliderType.Stream) volumeDialogSliderType).audioStream;
                AudioStream.m991constructorimpl(i2);
                final Flow flowM3108activeModesBlockingStreamtLTdkI8 = zenModeInteractor.m3108activeModesBlockingStreamtLTdkI8(i2);
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1

                    /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                                Boolean boolValueOf = Boolean.valueOf(((ActiveZenModes) obj).mainMode != null);
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
                        Object objCollect = flowM3108activeModesBlockingStreamtLTdkI8.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        }
        this.isDisabledByZenMode = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogSliderInteractor this$0;

                /* renamed from: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogSliderInteractor volumeDialogSliderInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogSliderInteractor;
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
                        VolumeDialogStreamModel volumeDialogStreamModelCopy$default = (VolumeDialogStreamModel) CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(this.this$0.sliderType.getAudioStream(), ((VolumeDialogStateModel) obj).streamModels);
                        if (volumeDialogStreamModelCopy$default != null) {
                            int i3 = volumeDialogStreamModelCopy$default.levelMax;
                            int i4 = volumeDialogStreamModelCopy$default.level;
                            int i5 = volumeDialogStreamModelCopy$default.levelMin;
                            if (i4 < i5 || i4 > i3) {
                                volumeDialogStreamModelCopy$default = VolumeDialogStreamModel.copy$default(volumeDialogStreamModelCopy$default, RangesKt___RangesKt.coerceIn(i4, i5, i3));
                            }
                        } else {
                            volumeDialogStreamModelCopy$default = null;
                        }
                        if (volumeDialogStreamModelCopy$default != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(volumeDialogStreamModelCopy$default, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.slider = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
