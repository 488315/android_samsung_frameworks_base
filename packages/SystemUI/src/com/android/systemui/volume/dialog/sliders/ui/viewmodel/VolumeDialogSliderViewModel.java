package com.android.systemui.volume.dialog.sliders.ui.viewmodel;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.volume.Events;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStreamModel;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInputEventsInteractor;
import com.android.systemui.volume.dialog.sliders.domain.interactor.VolumeDialogSliderInteractor;
import com.android.systemui.volume.dialog.sliders.domain.model.VolumeDialogSliderType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogSliderViewModel {
    public final Context context;
    public final VolumeDialogSliderInputEventsInteractor inputEventsInteractor;
    public final VolumeDialogSliderInteractor interactor;
    public final VolumeDialogLogger logger;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 model;
    public final VolumeDialogSliderType sliderType;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 state;
    public final SystemClock systemClock;
    public final UiEventLogger uiEventLogger;
    public final StateFlowImpl userVolumeUpdates;
    public final VolumeDialogVisibilityInteractor visibilityInteractor;
    public final VolumeDialogSliderIconProvider volumeDialogSliderIconProvider;

    /* renamed from: com.android.systemui.volume.dialog.sliders.ui.viewmodel.VolumeDialogSliderViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int I$0;
        int I$1;
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = VolumeDialogSliderViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((VolumeUpdate) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object[] objArr;
            VolumeUpdate volumeUpdate;
            int i;
            int i2;
            Object[] objArr2;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = this.label;
            if (i3 == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeUpdate volumeUpdate2 = (VolumeUpdate) this.L$0;
                VolumeDialogSliderInteractor volumeDialogSliderInteractor = VolumeDialogSliderViewModel.this.interactor;
                int i4 = volumeUpdate2.newVolumeLevel;
                VolumeDialogSliderType volumeDialogSliderType = volumeDialogSliderInteractor.sliderType;
                int audioStream = volumeDialogSliderType.getAudioStream();
                VolumeDialogController volumeDialogController = volumeDialogSliderInteractor.volumeDialogController;
                volumeDialogController.setStreamVolume(audioStream, i4);
                volumeDialogController.setActiveStream(volumeDialogSliderType.getAudioStream());
                objArr = new Object[2];
                FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = VolumeDialogSliderViewModel.this.model;
                this.L$0 = volumeUpdate2;
                this.L$1 = objArr;
                this.L$2 = objArr;
                this.I$0 = 9;
                this.I$1 = 0;
                this.label = 1;
                Object objFirst = FlowKt.first(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, this);
                if (objFirst == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = objFirst;
                volumeUpdate = volumeUpdate2;
                i = 9;
                i2 = 0;
                objArr2 = objArr;
            } else {
                if (i3 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = this.I$1;
                i = this.I$0;
                objArr = (Object[]) this.L$2;
                objArr2 = (Object[]) this.L$1;
                volumeUpdate = (VolumeUpdate) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            objArr[i2] = new Integer(((VolumeDialogStreamModel) obj).stream);
            objArr2[1] = volumeUpdate;
            Events.writeEvent(i, objArr2);
            return Unit.INSTANCE;
        }
    }

    public final class VolumeUpdate {
        public final int newVolumeLevel;
        public final long timestampMillis;

        public VolumeUpdate(int i, long j) {
            this.newVolumeLevel = i;
            this.timestampMillis = j;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof VolumeUpdate)) {
                return false;
            }
            VolumeUpdate volumeUpdate = (VolumeUpdate) obj;
            return this.newVolumeLevel == volumeUpdate.newVolumeLevel && this.timestampMillis == volumeUpdate.timestampMillis;
        }

        public final int hashCode() {
            return Long.hashCode(this.timestampMillis) + (Integer.hashCode(this.newVolumeLevel) * 31);
        }

        public final String toString() {
            return "VolumeUpdate(newVolumeLevel=" + this.newVolumeLevel + ", timestampMillis=" + this.timestampMillis + ")";
        }
    }

    public VolumeDialogSliderViewModel(Context context, VolumeDialogSliderType volumeDialogSliderType, VolumeDialogSliderInteractor volumeDialogSliderInteractor, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, CoroutineScope coroutineScope, VolumeDialogSliderIconProvider volumeDialogSliderIconProvider, VolumeDialogSliderInputEventsInteractor volumeDialogSliderInputEventsInteractor, SystemClock systemClock, VolumeDialogLogger volumeDialogLogger, UiEventLogger uiEventLogger) {
        this.context = context;
        this.sliderType = volumeDialogSliderType;
        this.interactor = volumeDialogSliderInteractor;
        this.visibilityInteractor = volumeDialogVisibilityInteractor;
        this.volumeDialogSliderIconProvider = volumeDialogSliderIconProvider;
        this.inputEventsInteractor = volumeDialogSliderInputEventsInteractor;
        this.systemClock = systemClock;
        this.logger = volumeDialogLogger;
        this.uiEventLogger = uiEventLogger;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.userVolumeUpdates = stateFlowImplMutableStateFlow;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(volumeDialogSliderInteractor.slider, stateFlowImplMutableStateFlow, new VolumeDialogSliderViewModel$model$1(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, null));
        this.model = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
        this.state = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.stateIn(FlowKt.combine(volumeDialogSliderInteractor.isDisabledByZenMode, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, FlowKt.transformLatest(flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, new VolumeDialogSliderViewModel$special$$inlined$flatMapLatest$1(null, this)), new VolumeDialogSliderViewModel$state$2(this, null)), coroutineScope, startedEagerly, null));
        FlowKt.launchIn(FlowKt.mapLatest(new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(stateFlowImplMutableStateFlow), new AnonymousClass1(null)), coroutineScope);
    }
}
