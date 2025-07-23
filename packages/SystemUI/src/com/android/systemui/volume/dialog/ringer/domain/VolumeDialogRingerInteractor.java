package com.android.systemui.volume.dialog.ringer.domain;

import com.android.settingslib.volume.data.repository.AudioSystemRepository;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepository;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogRingerInteractor {
    public final AudioSystemRepository audioSystemRepository;
    public final VolumeDialogController controller;
    public final VolumeDialogRingerFeedbackRepository ringerFeedbackRepository;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 ringerModel;

    public VolumeDialogRingerInteractor(CoroutineScope coroutineScope, VolumeDialogStateInteractor volumeDialogStateInteractor, VolumeDialogController volumeDialogController, AudioSystemRepository audioSystemRepository, VolumeDialogRingerFeedbackRepository volumeDialogRingerFeedbackRepository) {
        this.controller = volumeDialogController;
        this.audioSystemRepository = audioSystemRepository;
        this.ringerFeedbackRepository = volumeDialogRingerFeedbackRepository;
        final ReadonlyStateFlow readonlyStateFlow = volumeDialogStateInteractor.volumeDialogState;
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogRingerInteractor this$0;

                /* renamed from: com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogRingerInteractor volumeDialogRingerInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogRingerInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                    /*
                        r17 = this;
                        r0 = r17
                        r1 = r19
                        boolean r2 = r1 instanceof com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1$2$1 r2 = (com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1$2$1 r2 = new com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L34
                        if (r4 != r5) goto L2c
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto Lae
                    L2c:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L34:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r18
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r1 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r1
                        com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor r4 = r0.this$0
                        r4.getClass()
                        java.util.Map r6 = r1.streamModels
                        r7 = 2
                        java.lang.Integer r8 = java.lang.Integer.valueOf(r7)
                        java.lang.Object r6 = r6.get(r8)
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel r6 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel) r6
                        if (r6 == 0) goto La0
                        com.android.settingslib.volume.shared.model.RingerMode.m991constructorimpl(r7)
                        com.android.settingslib.volume.shared.model.RingerMode r7 = com.android.settingslib.volume.shared.model.RingerMode.m990boximpl(r7)
                        r8 = 0
                        com.android.settingslib.volume.shared.model.RingerMode.m991constructorimpl(r8)
                        com.android.settingslib.volume.shared.model.RingerMode r9 = com.android.settingslib.volume.shared.model.RingerMode.m990boximpl(r8)
                        com.android.settingslib.volume.shared.model.RingerMode[] r7 = new com.android.settingslib.volume.shared.model.RingerMode[]{r7, r9}
                        java.util.List r10 = kotlin.collections.CollectionsKt__CollectionsKt.mutableListOf(r7)
                        com.android.systemui.plugins.VolumeDialogController r7 = r4.controller
                        boolean r7 = r7.hasVibrator()
                        if (r7 == 0) goto L7b
                        com.android.settingslib.volume.shared.model.RingerMode.m991constructorimpl(r5)
                        com.android.settingslib.volume.shared.model.RingerMode r7 = com.android.settingslib.volume.shared.model.RingerMode.m990boximpl(r5)
                        r9 = r10
                        java.util.ArrayList r9 = (java.util.ArrayList) r9
                        r9.add(r7)
                    L7b:
                        int r11 = r1.ringerModeInternal
                        com.android.settingslib.volume.shared.model.RingerMode.m991constructorimpl(r11)
                        int r13 = r6.level
                        if (r13 == 0) goto L8b
                        boolean r1 = r6.muted
                        if (r1 == 0) goto L89
                        goto L8b
                    L89:
                        r12 = r8
                        goto L8c
                    L8b:
                        r12 = r5
                    L8c:
                        com.android.settingslib.volume.data.repository.AudioSystemRepository r1 = r4.audioSystemRepository
                        com.android.settingslib.volume.data.repository.AudioSystemRepositoryImpl r1 = (com.android.settingslib.volume.data.repository.AudioSystemRepositoryImpl) r1
                        android.content.Context r1 = r1.context
                        boolean r15 = android.media.AudioSystem.isSingleVolume(r1)
                        com.android.systemui.volume.dialog.ringer.shared.model.VolumeDialogRingerModel r9 = new com.android.systemui.volume.dialog.ringer.shared.model.VolumeDialogRingerModel
                        int r14 = r6.levelMax
                        r16 = 0
                        r9.<init>(r10, r11, r12, r13, r14, r15, r16)
                        goto La1
                    La0:
                        r9 = 0
                    La1:
                        if (r9 == 0) goto Lae
                        r2.label = r5
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r9, r2)
                        if (r0 != r3) goto Lae
                        return r3
                    Lae:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.ringerModel = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
