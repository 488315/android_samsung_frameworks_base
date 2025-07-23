package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSlidersInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogSlidersViewModel {
    public final VolumeDialogSliderComponent.Factory sliderComponentFactory;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 sliders;
    public final VolumeDialogLogger volumeDialogLogger;

    public VolumeDialogSlidersViewModel(CoroutineScope coroutineScope, VolumeDialogSlidersInteractor volumeDialogSlidersInteractor, VolumeDialogSliderComponent.Factory factory, VolumeDialogLogger volumeDialogLogger) {
        this.sliderComponentFactory = factory;
        this.volumeDialogLogger = volumeDialogLogger;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = volumeDialogSlidersInteractor.sliders;
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogSlidersViewModel this$0;

                /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogSlidersViewModel volumeDialogSlidersViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogSlidersViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r17, kotlin.coroutines.Continuation r18) {
                    /*
                        r16 = this;
                        r0 = r16
                        r1 = r18
                        boolean r2 = r1 instanceof com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2$1 r2 = (com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2$1 r2 = new com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L34
                        if (r4 != r5) goto L2c
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto Ldd
                    L2c:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L34:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r17
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel r1 = (com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSlidersModel) r1
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel r4 = r0.this$0
                        com.android.systemui.volume.dialog.shared.VolumeDialogLogger r6 = r4.volumeDialogLogger
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r7 = r1.slider
                        int r7 = r7.getAudioStream()
                        java.util.List r8 = r1.floatingSliders
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        java.util.ArrayList r9 = new java.util.ArrayList
                        r15 = 10
                        int r10 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r8, r15)
                        r9.<init>(r10)
                        java.util.Iterator r8 = r8.iterator()
                    L58:
                        boolean r10 = r8.hasNext()
                        if (r10 == 0) goto L71
                        java.lang.Object r10 = r8.next()
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r10 = (com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType) r10
                        int r10 = r10.getAudioStream()
                        java.lang.Integer r11 = new java.lang.Integer
                        r11.<init>(r10)
                        r9.add(r11)
                        goto L58
                    L71:
                        r6.getClass()
                        com.android.systemui.log.core.LogLevel r8 = com.android.systemui.log.core.LogLevel.DEBUG
                        com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0 r10 = new com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0
                        r11 = 0
                        r10.<init>(r11)
                        r11 = 0
                        com.android.systemui.log.LogBuffer r6 = r6.logBuffer
                        java.lang.String r12 = "SysUI_VolumeDialog"
                        com.android.systemui.log.core.LogMessage r8 = r6.obtain(r12, r8, r10, r11)
                        r10 = r8
                        com.android.systemui.log.LogMessageImpl r10 = (com.android.systemui.log.LogMessageImpl) r10
                        r10.int1 = r7
                        com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0 r13 = new com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0
                        r7 = 1
                        r13.<init>(r7)
                        r12 = 0
                        r14 = 30
                        r7 = r10
                        java.lang.String r10 = ","
                        r11 = 0
                        java.lang.String r9 = kotlin.collections.CollectionsKt___CollectionsKt.joinToString$default(r9, r10, r11, r12, r13, r14)
                        r7.str1 = r9
                        r6.commit(r8)
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r6 = r1.slider
                        com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent$Factory r4 = r4.sliderComponentFactory
                        com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent r6 = r4.create(r6)
                        java.util.List r1 = r1.floatingSliders
                        java.lang.Iterable r1 = (java.lang.Iterable) r1
                        java.util.ArrayList r7 = new java.util.ArrayList
                        int r8 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r1, r15)
                        r7.<init>(r8)
                        java.util.Iterator r1 = r1.iterator()
                    Lb9:
                        boolean r8 = r1.hasNext()
                        if (r8 == 0) goto Lcd
                        java.lang.Object r8 = r1.next()
                        com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType r8 = (com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType) r8
                        com.android.systemui.volume.dialog.sliders.dagger.VolumeDialogSliderComponent r8 = r4.create(r8)
                        r7.add(r8)
                        goto Lb9
                    Lcd:
                        com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderUiModel r1 = new com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderUiModel
                        r1.<init>(r6, r7)
                        r2.label = r5
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r1, r2)
                        if (r0 != r3) goto Ldd
                        return r3
                    Ldd:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSlidersViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.sliders = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
