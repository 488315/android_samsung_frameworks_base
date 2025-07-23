package com.android.systemui.biometrics.ui.viewmodel;

import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PromptIconViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ PromptSelectorInteractor $promptSelectorInteractor$inlined;
    final /* synthetic */ PromptViewModel $promptViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromptIconViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptIconViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, PromptIconViewModel promptIconViewModel, PromptSelectorInteractor promptSelectorInteractor, PromptViewModel promptViewModel) {
        super(3, continuation);
        this.this$0 = promptIconViewModel;
        this.$promptSelectorInteractor$inlined = promptSelectorInteractor;
        this.$promptViewModel$inlined = promptViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromptIconViewModel$special$$inlined$flatMapLatest$1 promptIconViewModel$special$$inlined$flatMapLatest$1 = new PromptIconViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$promptSelectorInteractor$inlined, this.$promptViewModel$inlined);
        promptIconViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        promptIconViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return promptIconViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int i2 = PromptIconViewModel.WhenMappings.$EnumSwitchMapping$1[((PromptIconViewModel.AuthType) this.L$1).ordinal()];
            if (i2 == 1) {
                final PromptIconViewModel promptIconViewModel = this.this$0;
                DisplayStateInteractorImpl displayStateInteractorImpl = (DisplayStateInteractorImpl) promptIconViewModel.displayStateInteractor;
                ReadonlyStateFlow readonlyStateFlow = displayStateInteractorImpl.currentRotation;
                ReadonlyStateFlow readonlyStateFlow2 = displayStateInteractorImpl.isInRearDisplayMode;
                StateFlow stateFlow = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).fingerprintSensorType;
                PromptViewModel promptViewModel = this.$promptViewModel$inlined;
                final Flow[] flowArr = {readonlyStateFlow, readonlyStateFlow2, stateFlow, promptViewModel.isAuthenticated, promptViewModel.isAuthenticating, promptViewModel.showingError};
                flow = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;
                        final /* synthetic */ PromptIconViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass3(Continuation continuation, PromptIconViewModel promptIconViewModel) {
                            super(3, continuation);
                            this.this$0 = promptIconViewModel;
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:42:0x00c5, code lost:
                        
                            if (r1 != false) goto L37;
                         */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invokeSuspend(java.lang.Object r10) {
                            /*
                                r9 = this;
                                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r1 = r9.label
                                r2 = 1
                                if (r1 == 0) goto L16
                                if (r1 != r2) goto Le
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto Ld8
                            Le:
                                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                r9.<init>(r10)
                                throw r9
                            L16:
                                kotlin.ResultKt.throwOnFailure(r10)
                                java.lang.Object r10 = r9.L$0
                                kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
                                java.lang.Object r1 = r9.L$1
                                java.lang.Object[] r1 = (java.lang.Object[]) r1
                                r3 = 0
                                r3 = r1[r3]
                                r4 = r1[r2]
                                r5 = 2
                                r5 = r1[r5]
                                r6 = 3
                                r6 = r1[r6]
                                r7 = 4
                                r7 = r1[r7]
                                r8 = 5
                                r1 = r1[r8]
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                java.lang.Boolean r7 = (java.lang.Boolean) r7
                                boolean r7 = r7.booleanValue()
                                com.android.systemui.biometrics.ui.viewmodel.PromptAuthState r6 = (com.android.systemui.biometrics.ui.viewmodel.PromptAuthState) r6
                                com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = (com.android.systemui.biometrics.shared.model.FingerprintSensorType) r5
                                java.lang.Boolean r4 = (java.lang.Boolean) r4
                                boolean r4 = r4.booleanValue()
                                com.android.systemui.biometrics.shared.model.DisplayRotation r3 = (com.android.systemui.biometrics.shared.model.DisplayRotation) r3
                                int[] r8 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.WhenMappings.$EnumSwitchMapping$0
                                int r5 = r5.ordinal()
                                r5 = r8[r5]
                                r8 = -1
                                if (r5 != r2) goto L97
                                com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r5 = r9.this$0
                                boolean r6 = r6.isAuthenticated
                                kotlinx.coroutines.flow.StateFlowImpl r5 = r5._previousIconWasError
                                if (r6 == 0) goto L72
                                java.lang.Object r1 = r5.getValue()
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                if (r1 == 0) goto L6d
                                r8 = 2131886095(0x7f12000f, float:1.940676E38)
                                goto Lca
                            L6d:
                                int r8 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.getSfpsAsset_fingerprintToSuccess(r3, r4)
                                goto Lca
                            L72:
                                if (r7 == 0) goto L90
                                java.lang.Object r1 = r5.getValue()
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                if (r1 == 0) goto L85
                                int r8 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.getSfpsAsset_errorToFingerprint(r3, r4)
                                goto Lca
                            L85:
                                if (r4 == 0) goto L8c
                                r1 = 2131886114(0x7f120022, float:1.9406798E38)
                            L8a:
                                r8 = r1
                                goto Lca
                            L8c:
                                r1 = 2131886097(0x7f120011, float:1.9406763E38)
                                goto L8a
                            L90:
                                if (r1 == 0) goto Lca
                                int r8 = com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel.getSfpsAsset_fingerprintToError(r3, r4)
                                goto Lca
                            L97:
                                com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel r3 = r9.this$0
                                boolean r4 = r6.isAuthenticated
                                kotlinx.coroutines.flow.StateFlowImpl r3 = r3._previousIconWasError
                                if (r4 == 0) goto Lb3
                                java.lang.Object r1 = r3.getValue()
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                if (r1 == 0) goto Laf
                                r8 = 2131886137(0x7f120039, float:1.9406844E38)
                                goto Lca
                            Laf:
                                r8 = 2131886140(0x7f12003c, float:1.940685E38)
                                goto Lca
                            Lb3:
                                if (r7 == 0) goto Lc5
                                java.lang.Object r1 = r3.getValue()
                                java.lang.Boolean r1 = (java.lang.Boolean) r1
                                boolean r1 = r1.booleanValue()
                                if (r1 == 0) goto Lc7
                                r8 = 2131886136(0x7f120038, float:1.9406842E38)
                                goto Lca
                            Lc5:
                                if (r1 == 0) goto Lca
                            Lc7:
                                r8 = 2131886139(0x7f12003b, float:1.9406848E38)
                            Lca:
                                java.lang.Integer r1 = new java.lang.Integer
                                r1.<init>(r8)
                                r9.label = r2
                                java.lang.Object r9 = r10.emit(r1, r9)
                                if (r9 != r0) goto Ld8
                                return r0
                            Ld8:
                                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                return r9
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr2 = flowArr;
                        Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$1.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr2.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            } else if (i2 == 2) {
                flow = FlowKt.combine(FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticated), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isAuthenticating), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.isPendingConfirmation), FlowKt.distinctUntilChanged(this.$promptViewModel$inlined.showingError), new PromptIconViewModel$iconAsset$1$2(this.this$0, null));
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                final PromptIconViewModel promptIconViewModel2 = this.this$0;
                DisplayStateInteractorImpl displayStateInteractorImpl2 = (DisplayStateInteractorImpl) promptIconViewModel2.displayStateInteractor;
                ReadonlyStateFlow readonlyStateFlow3 = displayStateInteractorImpl2.currentRotation;
                ReadonlyStateFlow readonlyStateFlow4 = displayStateInteractorImpl2.isInRearDisplayMode;
                StateFlow stateFlow2 = ((PromptSelectorInteractorImpl) this.$promptSelectorInteractor$inlined).fingerprintSensorType;
                PromptViewModel promptViewModel2 = this.$promptViewModel$inlined;
                final Flow[] flowArr2 = {readonlyStateFlow3, readonlyStateFlow4, stateFlow2, promptViewModel2.isAuthenticated, promptViewModel2.isAuthenticating, promptViewModel2.isPendingConfirmation, promptViewModel2.showingError};
                flow = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2$3, reason: invalid class name */
                    public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                        private /* synthetic */ Object L$0;
                        /* synthetic */ Object L$1;
                        int label;
                        final /* synthetic */ PromptIconViewModel this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        public AnonymousClass3(Continuation continuation, PromptIconViewModel promptIconViewModel) {
                            super(3, continuation);
                            this.this$0 = promptIconViewModel;
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.this$0);
                            anonymousClass3.L$0 = (FlowCollector) obj;
                            anonymousClass3.L$1 = (Object[]) obj2;
                            return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                        }

                        /* JADX WARN: Code restructure failed: missing block: B:82:0x016b, code lost:
                        
                            if (r1 != false) goto L77;
                         */
                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object invokeSuspend(java.lang.Object r15) {
                            /*
                                Method dump skipped, instructions count: 385
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2.AnonymousClass3.invokeSuspend(java.lang.Object):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        final Flow[] flowArr3 = flowArr2;
                        Object combineInternal = CombineKt.combineInternal(flowArr3, new Function0() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptIconViewModel$iconAsset$lambda$2$$inlined$combine$2.2
                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return new Object[flowArr3.length];
                            }
                        }, new AnonymousClass3(null, promptIconViewModel2), flowCollector2, continuation);
                        return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
