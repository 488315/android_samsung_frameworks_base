package com.android.systemui.volume.dialog.domain.interactor;

import android.os.Trace;
import com.android.systemui.accessibility.data.repository.AccessibilityRepository;
import com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import com.android.systemui.volume.dialog.data.VolumeDialogVisibilityRepository;
import com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel;
import com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel;
import com.android.systemui.volume.dialog.utils.VolumeTracer;
import com.android.systemui.volume.dialog.utils.VolumeTracerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* loaded from: classes3.dex */
public final class VolumeDialogVisibilityInteractor {
    public final AccessibilityRepository accessibilityRepository;
    public final VolumeDialogController controller;
    public final long defaultTimeout;
    public final ReadonlyStateFlow dialogVisibility;
    public final SharedFlowImpl mutableDismissDialogEvents;
    public final VolumeDialogVisibilityRepository repository;
    public final SecureSettingsRepository secureSettingsRepository;
    public final VolumeDialogStateInteractor stateInteractor;
    public final VolumeTracer tracer;

    /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return VolumeDialogVisibilityInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0034, code lost:
        
            if (kotlinx.coroutines.DelayKt.m3468delayVtjQ1oo(r3, r5) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = VolumeDialogVisibilityInteractor.this;
                this.label = 1;
                obj = VolumeDialogVisibilityInteractor.m3215access$computeTimeout5sfh64U(volumeDialogVisibilityInteractor, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return new VolumeDialogEventModel.DismissRequested(3);
            }
            ResultKt.throwOnFailure(obj);
            long j = ((Duration) obj).rawValue;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = VolumeDialogVisibilityInteractor.this.new AnonymousClass3(continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((VolumeDialogVisibilityModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object value;
            VolumeDialogVisibilityModel volumeDialogVisibilityModel;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            VolumeDialogVisibilityModel volumeDialogVisibilityModel2 = (VolumeDialogVisibilityModel) this.L$0;
            VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor = VolumeDialogVisibilityInteractor.this;
            StateFlowImpl stateFlowImpl = volumeDialogVisibilityInteractor.repository.mutableDialogVisibility;
            do {
                value = stateFlowImpl.getValue();
                volumeDialogVisibilityModel = (VolumeDialogVisibilityModel) value;
                if (volumeDialogVisibilityModel.getClass() != volumeDialogVisibilityModel2.getClass()) {
                    ((VolumeTracerImpl) volumeDialogVisibilityInteractor.tracer).getClass();
                    Trace.beginAsyncSection(VolumeTracerImpl.getMethodName(volumeDialogVisibilityModel2), volumeDialogVisibilityModel2.hashCode());
                    volumeDialogVisibilityModel = volumeDialogVisibilityModel2;
                }
            } while (!stateFlowImpl.compareAndSet(value, volumeDialogVisibilityModel));
            if (volumeDialogVisibilityModel2 instanceof VolumeDialogVisibilityModel.Visible) {
                VolumeDialogVisibilityInteractor.this.resetDismissTimeout();
            }
            return Unit.INSTANCE;
        }
    }

    public VolumeDialogVisibilityInteractor(CoroutineScope coroutineScope, VolumeDialogCallbacksInteractor volumeDialogCallbacksInteractor, VolumeDialogStateInteractor volumeDialogStateInteractor, VolumeTracer volumeTracer, VolumeDialogVisibilityRepository volumeDialogVisibilityRepository, AccessibilityRepository accessibilityRepository, VolumeDialogController volumeDialogController, SecureSettingsRepository secureSettingsRepository) {
        this.stateInteractor = volumeDialogStateInteractor;
        this.tracer = volumeTracer;
        this.repository = volumeDialogVisibilityRepository;
        this.accessibilityRepository = accessibilityRepository;
        this.controller = volumeDialogController;
        this.secureSettingsRepository = secureSettingsRepository;
        Duration.Companion companion = Duration.Companion;
        this.defaultTimeout = DurationKt.toDuration(3, DurationUnit.SECONDS);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        this.mutableDismissDialogEvents = sharedFlowImplMutableSharedFlow$default;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogVisibilityRepository.dialogVisibility, new VolumeDialogVisibilityInteractor$dialogVisibility$1(this, null));
        SharingStarted.Companion.getClass();
        this.dialogVisibility = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, VolumeDialogVisibilityModel.Invisible.Companion);
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(FlowKt.mapLatest(sharedFlowImplMutableSharedFlow$default, new AnonymousClass1(null)), volumeDialogCallbacksInteractor.event);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1

            /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ VolumeDialogVisibilityInteractor this$0;

                /* renamed from: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = volumeDialogVisibilityInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object visible;
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
                        VolumeDialogEventModel volumeDialogEventModel = (VolumeDialogEventModel) obj;
                        this.this$0.getClass();
                        if (volumeDialogEventModel instanceof VolumeDialogEventModel.DismissRequested) {
                            visible = new VolumeDialogVisibilityModel.Dismissed(((VolumeDialogEventModel.DismissRequested) volumeDialogEventModel).reason);
                        } else if (volumeDialogEventModel instanceof VolumeDialogEventModel.ShowRequested) {
                            VolumeDialogEventModel.ShowRequested showRequested = (VolumeDialogEventModel.ShowRequested) volumeDialogEventModel;
                            visible = new VolumeDialogVisibilityModel.Visible(showRequested.reason, showRequested.keyguardLocked, showRequested.lockTaskModeState);
                        } else {
                            visible = null;
                        }
                        if (visible != null) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(visible, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new AnonymousClass3(null)), coroutineScope);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* renamed from: access$computeTimeout-5sfh64U, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m3215access$computeTimeout5sfh64U(VolumeDialogVisibilityInteractor volumeDialogVisibilityInteractor, ContinuationImpl continuationImpl) {
        VolumeDialogVisibilityInteractor$computeTimeout$1 volumeDialogVisibilityInteractor$computeTimeout$1;
        long j;
        long jM1003getRecommendedTimeoutUqaQ4Hc;
        volumeDialogVisibilityInteractor.getClass();
        if (continuationImpl instanceof VolumeDialogVisibilityInteractor$computeTimeout$1) {
            volumeDialogVisibilityInteractor$computeTimeout$1 = (VolumeDialogVisibilityInteractor$computeTimeout$1) continuationImpl;
            int i = volumeDialogVisibilityInteractor$computeTimeout$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                volumeDialogVisibilityInteractor$computeTimeout$1.label = i - Integer.MIN_VALUE;
            } else {
                volumeDialogVisibilityInteractor$computeTimeout$1 = new VolumeDialogVisibilityInteractor$computeTimeout$1(volumeDialogVisibilityInteractor, continuationImpl);
            }
        }
        Object objFirst = volumeDialogVisibilityInteractor$computeTimeout$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = volumeDialogVisibilityInteractor$computeTimeout$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            Duration.Companion companion = Duration.Companion;
            int iM3463toIntimpl = Duration.m3463toIntimpl(volumeDialogVisibilityInteractor.defaultTimeout, DurationUnit.MILLISECONDS);
            volumeDialogVisibilityInteractor$computeTimeout$1.L$0 = volumeDialogVisibilityInteractor;
            volumeDialogVisibilityInteractor$computeTimeout$1.label = 1;
            objFirst = volumeDialogVisibilityInteractor.secureSettingsRepository.getInt("volume_dialog_dismiss_timeout", iM3463toIntimpl, volumeDialogVisibilityInteractor$computeTimeout$1);
            if (objFirst != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            j = volumeDialogVisibilityInteractor$computeTimeout$1.J$0;
            volumeDialogVisibilityInteractor = (VolumeDialogVisibilityInteractor) volumeDialogVisibilityInteractor$computeTimeout$1.L$0;
            ResultKt.throwOnFailure(objFirst);
            VolumeDialogStateModel volumeDialogStateModel = (VolumeDialogStateModel) objFirst;
            jM1003getRecommendedTimeoutUqaQ4Hc = (volumeDialogStateModel.isHovering && (volumeDialogStateModel.isShowingSafetyWarning instanceof VolumeDialogSafetyWarningModel.Visible)) ? ((AccessibilityRepositoryImpl) volumeDialogVisibilityInteractor.accessibilityRepository).m1003getRecommendedTimeoutUqaQ4Hc(6, j) : ((AccessibilityRepositoryImpl) volumeDialogVisibilityInteractor.accessibilityRepository).m1003getRecommendedTimeoutUqaQ4Hc(4, j);
            return Duration.m3453boximpl(jM1003getRecommendedTimeoutUqaQ4Hc);
        }
        volumeDialogVisibilityInteractor = (VolumeDialogVisibilityInteractor) volumeDialogVisibilityInteractor$computeTimeout$1.L$0;
        ResultKt.throwOnFailure(objFirst);
        long duration = DurationKt.toDuration(((Number) objFirst).intValue(), DurationUnit.MILLISECONDS);
        ReadonlyStateFlow readonlyStateFlow = volumeDialogVisibilityInteractor.stateInteractor.volumeDialogState;
        volumeDialogVisibilityInteractor$computeTimeout$1.L$0 = volumeDialogVisibilityInteractor;
        volumeDialogVisibilityInteractor$computeTimeout$1.J$0 = duration;
        volumeDialogVisibilityInteractor$computeTimeout$1.label = 2;
        objFirst = FlowKt.first(readonlyStateFlow, volumeDialogVisibilityInteractor$computeTimeout$1);
        if (objFirst != coroutineSingletons) {
            j = duration;
            VolumeDialogStateModel volumeDialogStateModel2 = (VolumeDialogStateModel) objFirst;
            if (volumeDialogStateModel2.isHovering) {
                jM1003getRecommendedTimeoutUqaQ4Hc = ((AccessibilityRepositoryImpl) volumeDialogVisibilityInteractor.accessibilityRepository).m1003getRecommendedTimeoutUqaQ4Hc(4, j);
            }
            return Duration.m3453boximpl(jM1003getRecommendedTimeoutUqaQ4Hc);
        }
        return coroutineSingletons;
    }

    public final void resetDismissTimeout() {
        this.controller.userActivity();
        this.mutableDismissDialogEvents.tryEmit(Unit.INSTANCE);
    }
}
