package com.android.systemui.volume.dialog.sliders.domain.interactor;

import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSliderInteractor {
    public final Flow isDisabledByZenMode;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 slider;
    public final VolumeDialogSliderType sliderType;
    public final VolumeDialogController volumeDialogController;

    public VolumeDialogSliderInteractor(VolumeDialogSliderType volumeDialogSliderType, CoroutineScope coroutineScope, VolumeDialogStateInteractor volumeDialogStateInteractor, VolumeDialogController volumeDialogController, ZenModeInteractor zenModeInteractor) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        this.sliderType = volumeDialogSliderType;
        this.volumeDialogController = volumeDialogController;
        if (volumeDialogSliderType instanceof VolumeDialogSliderType.Stream) {
            int i = ((VolumeDialogSliderType.Stream) volumeDialogSliderType).audioStream;
            AudioStream.m989constructorimpl(i);
            if (zenModeInteractor.zenModeByStreamPredicates.containsKey(Integer.valueOf(i))) {
                int i2 = ((VolumeDialogSliderType.Stream) volumeDialogSliderType).audioStream;
                AudioStream.m989constructorimpl(i2);
                final Flow m3091activeModesBlockingStreamtLTdkI8 = zenModeInteractor.m3091activeModesBlockingStreamtLTdkI8(i2);
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                                boolean r0 = r6 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L4a
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.statusbar.policy.domain.model.ActiveZenModes r5 = (com.android.systemui.statusbar.policy.domain.model.ActiveZenModes) r5
                                com.android.systemui.statusbar.policy.domain.model.ZenModeInfo r5 = r5.mainMode
                                if (r5 == 0) goto L3a
                                r5 = r3
                                goto L3b
                            L3a:
                                r5 = 0
                            L3b:
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L4a
                                return r1
                            L4a:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                this.isDisabledByZenMode = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
                final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
                Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                            /*
                                r5 = this;
                                boolean r0 = r7 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r7
                                com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1
                                r0.<init>(r7)
                            L18:
                                java.lang.Object r7 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r7)
                                goto L67
                            L27:
                                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                                r5.<init>(r6)
                                throw r5
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r7)
                                com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r6
                                java.util.Map r6 = r6.streamModels
                                com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor r7 = r5.this$0
                                com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r7 = r7.sliderType
                                int r7 = r7.getAudioStream()
                                java.lang.Object r6 = com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(r7, r6)
                                com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel) r6
                                if (r6 == 0) goto L59
                                int r7 = r6.levelMax
                                int r2 = r6.level
                                int r4 = r6.levelMin
                                if (r2 < r4) goto L50
                                if (r2 <= r7) goto L5a
                            L50:
                                int r7 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r2, r4, r7)
                                com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r6 = com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel.copy$default(r6, r7)
                                goto L5a
                            L59:
                                r6 = 0
                            L5a:
                                if (r6 == 0) goto L67
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                                java.lang.Object r5 = r5.emit(r6, r0)
                                if (r5 != r1) goto L67
                                return r1
                            L67:
                                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                                return r5
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                SharingStarted.Companion.getClass();
                this.slider = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
            }
        }
        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.isDisabledByZenMode = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        final Flow readonlyStateFlow2 = volumeDialogStateInteractor.volumeDialogState;
        Flow flow2 = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
                        boolean r0 = r7 instanceof com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L67
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r6
                        java.util.Map r6 = r6.streamModels
                        com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor r7 = r5.this$0
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r7 = r7.sliderType
                        int r7 = r7.getAudioStream()
                        java.lang.Object r6 = com.android.systemui.communal.data.repository.CommunalWidgetRepositoryLocalImpl$restoreWidgets$1$$ExternalSyntheticOutline0.m(r7, r6)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel) r6
                        if (r6 == 0) goto L59
                        int r7 = r6.levelMax
                        int r2 = r6.level
                        int r4 = r6.levelMin
                        if (r2 < r4) goto L50
                        if (r2 <= r7) goto L5a
                    L50:
                        int r7 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r2, r4, r7)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r6 = com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel.copy$default(r6, r7)
                        goto L5a
                    L59:
                        r6 = 0
                    L5a:
                        if (r6 == 0) goto L67
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.slider = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
