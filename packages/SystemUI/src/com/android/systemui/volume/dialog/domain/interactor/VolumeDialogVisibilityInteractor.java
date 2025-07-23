package com.android.systemui.volume.dialog.domain.interactor;

import android.os.Trace;
import com.android.systemui.accessibility.data.repository.AccessibilityRepository;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import com.android.systemui.volume.dialog.data.VolumeDialogVisibilityRepository;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0034, code lost:
        
            if (kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r3, r5) == r0) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0036, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0027, code lost:
        
            if (r6 == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r6) {
            /*
                r5 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r5.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L1c
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r6)
                goto L37
            L10:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r6)
                throw r5
            L18:
                kotlin.ResultKt.throwOnFailure(r6)
                goto L2a
            L1c:
                kotlin.ResultKt.throwOnFailure(r6)
                com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r6 = com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor.this
                r5.label = r3
                java.lang.Object r6 = com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor.m3199access$computeTimeout5sfh64U(r6, r5)
                if (r6 != r0) goto L2a
                goto L36
            L2a:
                kotlin.time.Duration r6 = (kotlin.time.Duration) r6
                long r3 = r6.rawValue
                r5.label = r2
                java.lang.Object r5 = kotlinx.coroutines.DelayKt.m3449delayVtjQ1oo(r3, r5)
                if (r5 != r0) goto L37
            L36:
                return r0
            L37:
                com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel$DismissRequested r5 = new com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel$DismissRequested
                r6 = 3
                r5.<init>(r6)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 1, null, 5);
        this.mutableDismissDialogEvents = MutableSharedFlow$default;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(volumeDialogVisibilityRepository.dialogVisibility, new VolumeDialogVisibilityInteractor$dialogVisibility$1(this, null));
        SharingStarted.Companion.getClass();
        this.dialogVisibility = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, SharingStarted.Companion.Eagerly, VolumeDialogVisibilityModel.Invisible.Companion);
        final ChannelLimitedFlowMerge merge = FlowKt.merge(FlowKt.mapLatest(MutableSharedFlow$default, new AnonymousClass1(null)), volumeDialogCallbacksInteractor.event);
        FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r7 instanceof com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1$2$1
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
                        com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel r6 = (com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel) r6
                        com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r7 = r5.this$0
                        r7.getClass()
                        boolean r7 = r6 instanceof com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel.DismissRequested
                        if (r7 == 0) goto L47
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel$Dismissed r7 = new com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel$Dismissed
                        com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel$DismissRequested r6 = (com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel.DismissRequested) r6
                        int r6 = r6.reason
                        r7.<init>(r6)
                        goto L5a
                    L47:
                        boolean r7 = r6 instanceof com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel.ShowRequested
                        if (r7 == 0) goto L59
                        com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel$Visible r7 = new com.android.systemui.volume.dialog.shared.model.VolumeDialogVisibilityModel$Visible
                        com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel$ShowRequested r6 = (com.android.systemui.volume.dialog.domain.model.VolumeDialogEventModel.ShowRequested) r6
                        int r2 = r6.reason
                        boolean r4 = r6.keyguardLocked
                        int r6 = r6.lockTaskModeState
                        r7.<init>(r2, r4, r6)
                        goto L5a
                    L59:
                        r7 = 0
                    L5a:
                        if (r7 == 0) goto L67
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new AnonymousClass3(null)), coroutineScope);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x005d, code lost:
    
        if (r8 == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* renamed from: access$computeTimeout-5sfh64U, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object m3199access$computeTimeout5sfh64U(com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r7.getClass()
            boolean r0 = r8 instanceof com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$computeTimeout$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$computeTimeout$1 r0 = (com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$computeTimeout$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$computeTimeout$1 r0 = new com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor$computeTimeout$1
            r0.<init>(r7, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L43
            if (r2 == r4) goto L3b
            if (r2 != r3) goto L33
            long r1 = r0.J$0
            java.lang.Object r7 = r0.L$0
            com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r7 = (com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L7e
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            java.lang.Object r7 = r0.L$0
            com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor r7 = (com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor) r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L60
        L43:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.time.Duration$Companion r8 = kotlin.time.Duration.Companion
            long r5 = r7.defaultTimeout
            kotlin.time.DurationUnit r8 = kotlin.time.DurationUnit.MILLISECONDS
            int r8 = kotlin.time.Duration.m3444toIntimpl(r5, r8)
            r0.L$0 = r7
            r0.label = r4
            com.android.systemui.shared.settings.data.repository.SecureSettingsRepository r2 = r7.secureSettingsRepository
            java.lang.String r4 = "volume_dialog_dismiss_timeout"
            java.lang.Object r8 = r2.getInt(r4, r8, r0)
            if (r8 != r1) goto L60
            goto L7c
        L60:
            java.lang.Number r8 = (java.lang.Number) r8
            int r8 = r8.intValue()
            kotlin.time.DurationUnit r2 = kotlin.time.DurationUnit.MILLISECONDS
            long r4 = kotlin.time.DurationKt.toDuration(r8, r2)
            com.android.systemui.volume.dialog.domain.interactor.VolumeDialogStateInteractor r8 = r7.stateInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r8.volumeDialogState
            r0.L$0 = r7
            r0.J$0 = r4
            r0.label = r3
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.first(r8, r0)
            if (r8 != r1) goto L7d
        L7c:
            return r1
        L7d:
            r1 = r4
        L7e:
            com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel r8 = (com.android.systemui.volume.dialog.shared.model.VolumeDialogStateModel) r8
            boolean r0 = r8.isHovering
            r3 = 4
            if (r0 == 0) goto L8e
            com.android.systemui.accessibility.data.repository.AccessibilityRepository r7 = r7.accessibilityRepository
            com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl r7 = (com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl) r7
            long r7 = r7.m1001getRecommendedTimeoutUqaQ4Hc(r3, r1)
            goto La6
        L8e:
            com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel r8 = r8.isShowingSafetyWarning
            boolean r8 = r8 instanceof com.android.systemui.volume.dialog.shared.model.VolumeDialogSafetyWarningModel.Visible
            if (r8 == 0) goto L9e
            com.android.systemui.accessibility.data.repository.AccessibilityRepository r7 = r7.accessibilityRepository
            r8 = 6
            com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl r7 = (com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl) r7
            long r7 = r7.m1001getRecommendedTimeoutUqaQ4Hc(r8, r1)
            goto La6
        L9e:
            com.android.systemui.accessibility.data.repository.AccessibilityRepository r7 = r7.accessibilityRepository
            com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl r7 = (com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl) r7
            long r7 = r7.m1001getRecommendedTimeoutUqaQ4Hc(r3, r1)
        La6:
            kotlin.time.Duration r7 = kotlin.time.Duration.m3434boximpl(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor.m3199access$computeTimeout5sfh64U(com.android.systemui.volume.dialog.domain.interactor.VolumeDialogVisibilityInteractor, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void resetDismissTimeout() {
        this.controller.userActivity();
        this.mutableDismissDialogEvents.tryEmit(Unit.INSTANCE);
    }
}
