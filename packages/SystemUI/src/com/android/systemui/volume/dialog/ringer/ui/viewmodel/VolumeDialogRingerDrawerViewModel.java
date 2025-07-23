package com.android.systemui.volume.dialog.ringer.ui.viewmodel;

import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    arrayList.add(RingerMode.m990boximpl(((RingerButtonViewModel) it.next()).ringerMode));
                }
                volumeDialogLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(2);
                LogBuffer logBuffer = volumeDialogLogger.logBuffer;
                LogMessage obtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) obtain).str1 = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, new VolumeDialogLogger$$ExternalSyntheticLambda0(3), 30);
                logBuffer.commit(obtain);
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
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(RingerDrawerState.Initial.Companion);
        this.drawerState = MutableStateFlow;
        final Flow onConfigChanged = ConfigurationControllerExtKt.getOnConfigChanged(configurationController);
        Flow flow = new Flow() { // from class: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.content.res.Configuration r5 = (android.content.res.Configuration) r5
                        int r5 = r5.orientation
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.ringer.ui.viewmodel.VolumeDialogRingerDrawerViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, Integer.valueOf(context.getResources().getConfiguration().orientation));
        this.orientation = stateIn;
        AudioStream.m989constructorimpl(2);
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.flowOn(FlowKt.combine(notificationsSoundPolicyInteractor.m974isZenMutedtLTdkI8(2), volumeDialogRingerInteractor.ringerModel, MutableStateFlow, stateIn, new VolumeDialogRingerDrawerViewModel$ringerViewModel$1(this, null)), coroutineDispatcher), coroutineScope, startedEagerly, RingerViewModelState.Unavailable.INSTANCE);
        this.ringerViewModel = stateIn2;
        this.level = -1;
        this.levelMax = -1;
        this.sonificiationVibrationAttributes = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(stateIn2, new AnonymousClass1(null)), coroutineScope);
    }

    /* renamed from: toButtonViewModel-L0tBgz0, reason: not valid java name */
    public static RingerButtonViewModel m3200toButtonViewModelL0tBgz0(VolumeDialogRingerModel volumeDialogRingerModel, int i, boolean z, boolean z2) {
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
        RingerMode.m991constructorimpl(1);
        if (list.contains(RingerMode.m990boximpl(1))) {
            return new RingerButtonViewModel(R.drawable.ic_speaker_on, z2 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_hint_unmute, R.string.volume_ringer_hint_vibrate, i, null);
        }
        return new RingerButtonViewModel(R.drawable.ic_speaker_on, z2 ? R.string.volume_ringer_status_normal : R.string.volume_ringer_hint_unmute, R.string.volume_ringer_hint_mute, i, null);
    }

    /* renamed from: onRingerButtonClicked-28s9KyU, reason: not valid java name */
    public final void m3201onRingerButtonClicked28s9KyU(int i, boolean z) {
        Object open;
        VibrationEffect vibrationEffect;
        long currentTimeMillis = this.systemClock.currentTimeMillis();
        if (currentTimeMillis - this.lastClickTime < 400) {
            return;
        }
        this.lastClickTime = currentTimeMillis;
        StateFlowImpl stateFlowImpl = this.drawerState;
        if ((stateFlowImpl.getValue() instanceof RingerDrawerState.Open) && !z) {
            Events.writeEvent(18, Integer.valueOf(i));
            VolumeDialogLogger volumeDialogLogger = this.volumeDialogLogger;
            volumeDialogLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            VolumeDialogLogger$$ExternalSyntheticLambda0 volumeDialogLogger$$ExternalSyntheticLambda0 = new VolumeDialogLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer = volumeDialogLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("SysUI_VolumeDialog", logLevel, volumeDialogLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
            VolumeDialogRingerInteractor volumeDialogRingerInteractor = this.ringerInteractor;
            if (i == 0) {
                vibrationEffect = VibrationEffect.get(0);
            } else if (i == 1) {
                vibrationEffect = VibrationEffect.get(1);
            } else if (i != 2) {
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
