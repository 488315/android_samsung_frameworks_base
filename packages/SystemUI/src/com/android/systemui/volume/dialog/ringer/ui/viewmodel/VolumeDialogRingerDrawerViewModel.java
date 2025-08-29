package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioAttributes;
import android.os.VibrationEffect;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.notification.domain.interactor.NotificationsSoundPolicyInteractor;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.systemui.R;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.volume.Events;
import com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor;
import com.android.systemui.volume.dialog.ringer.domain.VolumeDialogRingerInteractor;
import com.android.systemui.volume.dialog.ringer.shared.model.VolumeDialogRingerModel;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerDrawerState;
import com.android.systemui.volume.dialog.ringer.ui.viewmodel.RingerViewModelState;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger;
import com.android.systemui.volume.dialog.shared.VolumeDialogLogger$$ExternalSyntheticLambda0;
import com.android.systemui.volume.dialog.ui.VolumeDialogUiEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class VolumeDialogRingerDrawerViewModel {
    public final Context applicationContext;
    public final CoroutineScope coroutineScope;
    public final StateFlowImpl drawerState;
    public long lastClickTime;
    public int level;
    public int levelMax;
    public final ReadonlyStateFlow orientation;
    public final VolumeDialogRingerInteractor ringerInteractor;
    public final ReadonlyStateFlow ringerViewModel;
    public final AudioAttributes sonificiationVibrationAttributes;
    public final SystemClock systemClock;
    public final UiEventLogger uiEventLogger;
    public final VibratorHelper vibrator;
    public final VolumeDialogVisibilityInteractor visibilityInteractor;
    public final VolumeDialogLogger volumeDialogLogger;

    /* renamed from: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = VolumeDialogRingerDrawerViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((RingerViewModelState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            RingerViewModelState ringerViewModelState = (RingerViewModelState) this.L$0;
            if (ringerViewModelState instanceof RingerViewModelState.Available) {
                VolumeDialogLogger volumeDialogLogger = VolumeDialogRingerDrawerViewModel.this.volumeDialogLogger;
                List list = ((RingerViewModelState.Available) ringerViewModelState).uiModel.availableButtons;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(RingerMode.m992boximpl(((RingerButtonViewModel) it.next()).ringerMode));
                }
                volumeDialogLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = volumeDialogLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new VolumeDialogLogger$$ExternalSyntheticLambda0(3), 30);
                logBuffer.commit(logMessageObtain);
            } else {
                if (!(ringerViewModelState instanceof RingerViewModelState.Unavailable)) {
                    throw new NoWhenBranchMatchedException();
                }
                VolumeDialogLogger volumeDialogLogger2 = VolumeDialogRingerDrawerViewModel.this.volumeDialogLogger;
                volumeDialogLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda02 = new VolumeDialogLogger$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer2 = volumeDialogLogger2.logBuffer;
                logBuffer2.commit(logBuffer2.obtain("SysUI_VolumeDialog", logLevel2, volumeDialogLogger$$ExternalSyntheticLambda02, null));
            }
            return Unit.INSTANCE;
        }
    }

    public VolumeDialogRingerDrawerViewModel(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, NotificationsSoundPolicyInteractor notificationsSoundPolicyInteractor, VolumeDialogRingerInteractor volumeDialogRingerInteractor, VibratorHelper vibratorHelper, VolumeDialogLogger volumeDialogLogger, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, ConfigurationController configurationController, UiEventLogger uiEventLogger, SystemClock systemClock) {
        this.applicationContext = context;
        this.coroutineScope = coroutineScope;
        this.ringerInteractor = volumeDialogRingerInteractor;
        this.vibrator = vibratorHelper;
        this.volumeDialogLogger = volumeDialogLogger;
        this.visibilityInteractor = volumeDialogVisibilityInteractor;
        this.uiEventLogger = uiEventLogger;
        this.systemClock = systemClock;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(RingerDrawerState.Initial.Companion);
        this.drawerState = stateFlowImplMutableStateFlow;
        final Flow onConfigChanged = ConfigurationControllerExtKt.getOnConfigChanged(configurationController);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((Configuration) obj).orientation);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = onConfigChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, Integer.valueOf(context.getResources().getConfiguration().orientation));
        this.orientation = readonlyStateFlowStateIn;
        AudioStream.m991constructorimpl(2);
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowKt.flowOn(FlowKt.combine(notificationsSoundPolicyInteractor.m976isZenMutedtLTdkI8(2), volumeDialogRingerInteractor.ringerModel, stateFlowImplMutableStateFlow, readonlyStateFlowStateIn, new VolumeDialogRingerDrawerViewModel$ringerViewModel$1(this, null)), coroutineDispatcher), coroutineScope, startedEagerly, RingerViewModelState.Unavailable.INSTANCE);
        this.ringerViewModel = readonlyStateFlowStateIn2;
        this.level = -1;
        this.levelMax = -1;
        this.sonificiationVibrationAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(readonlyStateFlowStateIn2, new AnonymousClass1(null)), coroutineScope);
    }

    /* renamed from: toButtonViewModel-L0tBgz0, reason: not valid java name */
    public static RingerButtonViewModel m3216toButtonViewModelL0tBgz0(VolumeDialogRingerModel volumeDialogRingerModel, int i, boolean z, boolean z2) {
        if (i == 0) {
            return new RingerButtonViewModel(R.drawable.ic_speaker_mute, z2 ? R.string.volume_ringer_status_silent : R.string.volume_ringer_hint_mute, R.string.volume_ringer_hint_unmute, i, null);
        }
        if (i == 1) {
            return new RingerButtonViewModel(R.drawable.ic_volume_ringer_vibrate, z2 ? R.string.volume_ringer_status_vibrate : R.string.volume_ringer_hint_vibrate, R.string.volume_ringer_hint_vibrate, i, null);
        }
        if (i != 2) {
            return null;
        }
        if (volumeDialogRingerModel.isMuted && !z) {
            return new RingerButtonViewModel(z2 ? R.drawable.ic_speaker_mute : R.drawable.ic_speaker_on, z2 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_hint_unmute, R.string.volume_ringer_hint_unmute, i, null);
        }
        List list = volumeDialogRingerModel.availableModes;
        RingerMode.m993constructorimpl(1);
        if (list.contains(RingerMode.m992boximpl(1))) {
            return new RingerButtonViewModel(R.drawable.ic_speaker_on, z2 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_hint_unmute, R.string.volume_ringer_hint_vibrate, i, null);
        }
        return new RingerButtonViewModel(R.drawable.ic_speaker_on, z2 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_hint_unmute, R.string.volume_ringer_hint_mute, i, null);
    }

    /* renamed from: onRingerButtonClicked-28s9KyU, reason: not valid java name */
    public final void m3217onRingerButtonClicked28s9KyU(int i, boolean z) {
        Object open;
        VibrationEffect vibrationEffect;
        long jCurrentTimeMillis = this.systemClock.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastClickTime < 400) {
            return;
        }
        this.lastClickTime = jCurrentTimeMillis;
        StateFlowImpl stateFlowImpl = this.drawerState;
        if ((stateFlowImpl.getValue() instanceof RingerDrawerState.Open) && !z) {
            Events.writeEvent(18, Integer.valueOf(i));
            VolumeDialogLogger volumeDialogLogger = this.volumeDialogLogger;
            volumeDialogLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer = volumeDialogLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            VolumeDialogRingerInteractor volumeDialogRingerInteractor = this.ringerInteractor;
            if (i == 0) {
                vibrationEffect = VibrationEffect.get(0);
            } else if (i == 1 || i != 2) {
                vibrationEffect = VibrationEffect.get(1);
            } else {
                volumeDialogRingerInteractor.controller.scheduleTouchFeedback();
                vibrationEffect = null;
            }
            if (vibrationEffect != null) {
                this.vibrator.vibrate(vibrationEffect, this.sonificiationVibrationAttributes);
            }
            BuildersKt.launch$default(this.coroutineScope, null, null, new VolumeDialogRingerDrawerViewModel$maybeShowToast$1(this, i, null), 3);
            volumeDialogRingerInteractor.controller.setRingerMode(i, false);
            VolumeDialogUiEvent volumeDialogUiEvent = i != 0 ? i != 1 ? i != 2 ? null : VolumeDialogUiEvent.RINGER_MODE_NORMAL : VolumeDialogUiEvent.RINGER_MODE_VIBRATE : VolumeDialogUiEvent.RINGER_MODE_SILENT;
            if (volumeDialogUiEvent != null) {
                this.uiEventLogger.log(volumeDialogUiEvent);
            }
        }
        this.visibilityInteractor.resetDismissTimeout();
        RingerDrawerState ringerDrawerState = (RingerDrawerState) stateFlowImpl.getValue();
        if (ringerDrawerState instanceof RingerDrawerState.Initial) {
            open = new RingerDrawerState.Open(i, null);
        } else if (ringerDrawerState instanceof RingerDrawerState.Open) {
            open = new RingerDrawerState.Closed(i, ((RingerDrawerState.Open) stateFlowImpl.getValue()).mode, null);
        } else {
            if (!(ringerDrawerState instanceof RingerDrawerState.Closed)) {
                throw new NoWhenBranchMatchedException();
            }
            open = new RingerDrawerState.Open(i, null);
        }
        stateFlowImpl.updateState(null, open);
    }
}
