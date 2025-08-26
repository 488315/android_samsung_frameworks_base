package com.android.systemui.volume.dialog.ringer.domain;

import android.media.AudioSystem;
import com.android.settingslib.volume.data.repository.AudioSystemRepository;
import com.android.settingslib.volume.data.repository.AudioSystemRepositoryImpl;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor;
import com.android.systemui.volume.dialog.ringer.data.repository.VolumeDialogRingerFeedbackRepository;
import com.android.systemui.volume.dialog.ringer.shared.model.VolumeDialogRingerModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    VolumeDialogRingerModel volumeDialogRingerModel;
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
                        VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) obj;
                        VolumeDialogRingerInteractor volumeDialogRingerInteractor = this.this$0;
                        volumeDialogRingerInteractor.getClass();
                        VolumeDialogStreamModel volumeDialogStreamModel = (VolumeDialogStreamModel) volumeDialogStateModel.streamModels.get(2);
                        if (volumeDialogStreamModel != null) {
                            RingerMode.m993constructorimpl(2);
                            RingerMode ringerModeM992boximpl = RingerMode.m992boximpl(2);
                            RingerMode.m993constructorimpl(0);
                            List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(ringerModeM992boximpl, RingerMode.m992boximpl(0));
                            if (volumeDialogRingerInteractor.controller.hasVibrator()) {
                                RingerMode.m993constructorimpl(1);
                                ((ArrayList) listMutableListOf).add(RingerMode.m992boximpl(1));
                            }
                            int i3 = volumeDialogStateModel.ringerModeInternal;
                            RingerMode.m993constructorimpl(i3);
                            int i4 = volumeDialogStreamModel.level;
                            volumeDialogRingerModel = new VolumeDialogRingerModel(listMutableListOf, i3, i4 == 0 || volumeDialogStreamModel.muted, i4, volumeDialogStreamModel.levelMax, AudioSystem.isSingleVolume(((AudioSystemRepositoryImpl) volumeDialogRingerInteractor.audioSystemRepository).context), null);
                        } else {
                            volumeDialogRingerModel = null;
                        }
                        if (volumeDialogRingerModel != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(volumeDialogRingerModel, anonymousClass1) == coroutineSingletons) {
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
        this.ringerModel = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.Eagerly, null));
    }
}
