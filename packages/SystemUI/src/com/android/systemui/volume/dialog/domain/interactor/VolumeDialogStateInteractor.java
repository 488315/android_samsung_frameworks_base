package com.android.systemui.volume.dialog.domain.interactor;

import android.util.SparseArray;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.dialog.data.repository.VolumeDialogStateRepository;
import com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogCsdWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public final class VolumeDialogStateInteractor {
    public final VolumeDialogController volumeDialogController;
    public final ReadonlyStateFlow volumeDialogState;
    public final VolumeDialogStateRepository volumeDialogStateRepository;

    /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = VolumeDialogStateInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((VolumeDialogEventModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object value;
            StateFlowImpl stateFlowImpl;
            Object value2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            final VolumeDialogEventModel volumeDialogEventModel = (VolumeDialogEventModel) this.L$0;
            if (volumeDialogEventModel instanceof VolumeDialogEventModel.StateChanged) {
                final VolumeDialogStateInteractor volumeDialogStateInteractor = VolumeDialogStateInteractor.this;
                VolumeDialogStateRepository volumeDialogStateRepository = volumeDialogStateInteractor.volumeDialogStateRepository;
                Function1 function1 = new Function1() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) obj2;
                        VolumeDialogController.State state = ((VolumeDialogEventModel.StateChanged) volumeDialogEventModel).state;
                        volumeDialogStateInteractor.getClass();
                        SparseArray<VolumeDialogController.StreamState> sparseArray = state.states;
                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        SparseArrayKt$keyIterator$1 sparseArrayKt$keyIterator$1 = new SparseArrayKt$keyIterator$1(sparseArray);
                        while (sparseArrayKt$keyIterator$1.hasNext()) {
                            int iNextInt = sparseArrayKt$keyIterator$1.nextInt();
                            VolumeDialogController.StreamState streamState = sparseArray.get(iNextInt);
                            streamState.getClass();
                            linkedHashMap.put(Integer.valueOf(iNextInt), new VolumeDialogStreamModel(iNextInt, iNextInt == state.activeStream, streamState));
                        }
                        return VolumeDialogStateModel.copy$default(volumeDialogStateModel, false, null, null, false, linkedHashMap, state.ringerModeInternal, state.ringerModeExternal, state.zenMode, state.effectsSuppressor, state.effectsSuppressorName, state.activeStream, state.disallowAlarms, state.disallowMedia, state.disallowSystem, state.disallowRinger, 15);
                    }
                };
                do {
                    stateFlowImpl = volumeDialogStateRepository.mutableState;
                    value2 = stateFlowImpl.getValue();
                } while (!stateFlowImpl.compareAndSet(value2, function1.mo781invoke(value2)));
            } else if (volumeDialogEventModel instanceof VolumeDialogEventModel.AccessibilityModeChanged) {
                StateFlowImpl stateFlowImpl2 = VolumeDialogStateInteractor.this.volumeDialogStateRepository.mutableState;
                do {
                    value = stateFlowImpl2.getValue();
                } while (!stateFlowImpl2.compareAndSet(value, VolumeDialogStateModel.copy$default((VolumeDialogStateModel) value, ((VolumeDialogEventModel.AccessibilityModeChanged) volumeDialogEventModel).showA11yStream, null, null, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32766)));
            } else if (volumeDialogEventModel instanceof VolumeDialogEventModel.ShowSafetyWarning) {
                VolumeDialogStateInteractor.this.setSafetyWarning(new VolumeDialogSafetyWarningModel.Visible(((VolumeDialogEventModel.ShowSafetyWarning) volumeDialogEventModel).flags));
            } else if (volumeDialogEventModel instanceof VolumeDialogEventModel.ShowCsdWarning) {
                VolumeDialogStateInteractor volumeDialogStateInteractor2 = VolumeDialogStateInteractor.this;
                VolumeDialogEventModel.ShowCsdWarning showCsdWarning = (VolumeDialogEventModel.ShowCsdWarning) volumeDialogEventModel;
                int i = showCsdWarning.csdWarning;
                Duration.Companion companion = Duration.Companion;
                volumeDialogStateInteractor2.setCsdWarning(new VolumeDialogCsdWarningModel.Visible(i, DurationKt.toDuration(showCsdWarning.durationMs, DurationUnit.MILLISECONDS), null));
            } else if (volumeDialogEventModel instanceof VolumeDialogEventModel.SubscribedToEvents) {
                VolumeDialogStateInteractor.this.volumeDialogController.getState();
            }
            return Unit.INSTANCE;
        }
    }

    public VolumeDialogStateInteractor(VolumeDialogCallbacksInteractor volumeDialogCallbacksInteractor, VolumeDialogController volumeDialogController, VolumeDialogStateRepository volumeDialogStateRepository, CoroutineScope coroutineScope) {
        this.volumeDialogController = volumeDialogController;
        this.volumeDialogStateRepository = volumeDialogStateRepository;
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogCallbacksInteractor.event, new AnonymousClass1(null)), coroutineScope);
        this.volumeDialogState = volumeDialogStateRepository.state;
    }

    public final void setCsdWarning(VolumeDialogCsdWarningModel volumeDialogCsdWarningModel) {
        Object value;
        StateFlowImpl stateFlowImpl = this.volumeDialogStateRepository.mutableState;
        do {
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, VolumeDialogStateModel.copy$default((VolumeDialogStateModel) value, false, null, volumeDialogCsdWarningModel, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32763)));
    }

    public final void setSafetyWarning(VolumeDialogSafetyWarningModel volumeDialogSafetyWarningModel) {
        Object value;
        StateFlowImpl stateFlowImpl = this.volumeDialogStateRepository.mutableState;
        do {
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, VolumeDialogStateModel.copy$default((VolumeDialogStateModel) value, false, volumeDialogSafetyWarningModel, null, false, null, 0, 0, 0, null, null, 0, false, false, false, false, 32765)));
    }
}
