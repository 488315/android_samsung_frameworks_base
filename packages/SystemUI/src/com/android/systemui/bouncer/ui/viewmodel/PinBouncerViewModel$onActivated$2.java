package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.EntryToken;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PinBouncerViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PinBouncerViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PinBouncerViewModel pinBouncerViewModel = this.this$0;
                this.label = 1;
                pinBouncerViewModel.getClass();
                if (AuthMethodBouncerViewModel.onActivated$suspendImpl(pinBouncerViewModel, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$10, reason: invalid class name */
    final class AnonymousClass10 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass10(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass10(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass10) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0._isWhiteBg.updateState(null, Boolean.valueOf(this.this$0.wallpaperManager.semGetWallpaperColors(10).get(512L).getFontColor() == 1));
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ PinBouncerViewModel this$0;

            public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel) {
                this.this$0 = pinBouncerViewModel;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.Request r12, kotlin.coroutines.Continuation r13) {
                /*
                    r11 = this;
                    boolean r0 = r13 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1$emit$1
                    if (r0 == 0) goto L13
                    r0 = r13
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1$emit$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1$emit$1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L13
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L18
                L13:
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1$emit$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1$emit$1
                    r0.<init>(r11, r13)
                L18:
                    java.lang.Object r13 = r0.result
                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                    int r2 = r0.label
                    r3 = 1
                    r4 = 0
                    if (r2 == 0) goto L34
                    if (r2 != r3) goto L2c
                    java.lang.Object r11 = r0.L$0
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$2$1 r11 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2.AnonymousClass2.AnonymousClass1) r11
                    kotlin.ResultKt.throwOnFailure(r13)
                    goto L96
                L2c:
                    java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                    java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                    r11.<init>(r12)
                    throw r11
                L34:
                    kotlin.ResultKt.throwOnFailure(r13)
                    boolean r13 = r12 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.OnErrorDialogDismissed
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r2 = r11.this$0
                    if (r13 == 0) goto L49
                    com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor r11 = r2.simBouncerInteractor
                    com.android.systemui.bouncer.data.repository.SimBouncerRepository r11 = r11.repository
                    com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl r11 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl) r11
                    kotlinx.coroutines.flow.StateFlowImpl r11 = r11.simVerificationErrorMessage
                    r11.setValue(r4)
                    goto La4
                L49:
                    boolean r12 = r12 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.OnAuthenticateButtonClickedForSim
                    if (r12 == 0) goto La7
                    kotlinx.coroutines.flow.StateFlowImpl r12 = r2.isSimUnlockingDialogVisible
                    java.lang.Boolean r13 = java.lang.Boolean.TRUE
                    r12.updateState(r4, r13)
                    java.util.List r5 = r2.getInput()
                    r0.L$0 = r11
                    r0.label = r3
                    com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor r12 = r2.simBouncerInteractor
                    r12.getClass()
                    r8 = 0
                    r10 = 62
                    java.lang.String r6 = ""
                    r7 = 0
                    r9 = 0
                    java.lang.String r13 = kotlin.collections.CollectionsKt___CollectionsKt.joinToString$default(r5, r6, r7, r8, r9, r10)
                    com.android.systemui.bouncer.data.repository.SimBouncerRepository r2 = r12.repository
                    com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl r2 = (com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl) r2
                    kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.isSimPukLocked
                    kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                    java.lang.Object r2 = r2.getValue()
                    java.lang.Boolean r2 = (java.lang.Boolean) r2
                    boolean r2 = r2.booleanValue()
                    if (r2 == 0) goto L8a
                    java.lang.Object r12 = r12.verifySimPuk(r13, r0)
                    if (r12 != r1) goto L87
                    goto L93
                L87:
                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                    goto L93
                L8a:
                    java.lang.Object r12 = r12.verifySimPin(r13, r0)
                    if (r12 != r1) goto L91
                    goto L93
                L91:
                    kotlin.Unit r12 = kotlin.Unit.INSTANCE
                L93:
                    if (r12 != r1) goto L96
                    return r1
                L96:
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r12 = r11.this$0
                    kotlinx.coroutines.flow.StateFlowImpl r12 = r12.isSimUnlockingDialogVisible
                    java.lang.Boolean r13 = java.lang.Boolean.FALSE
                    r12.updateState(r4, r13)
                    com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r11 = r11.this$0
                    r11.clearInput()
                La4:
                    kotlin.Unit r11 = kotlin.Unit.INSTANCE
                    return r11
                La7:
                    kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
                    r11.<init>()
                    throw r11
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2.AnonymousClass2.AnonymousClass1.emit(com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$Request, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ChannelAsFlow receiveAsFlow = FlowKt.receiveAsFlow(this.this$0.requests);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0);
                this.label = 1;
                if (receiveAsFlow.collect(anonymousClass1, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = pinBouncerViewModel.simBouncerInteractor.subId;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Number) obj2).intValue();
                        PinBouncerViewModel pinBouncerViewModel2 = PinBouncerViewModel.this;
                        pinBouncerViewModel2.simBouncerInteractor.resetSimPukUserInput();
                        pinBouncerViewModel2.clearInput();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PinBouncerViewModel pinBouncerViewModel = this.this$0;
                Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = pinBouncerViewModel.isSimAreaVisible ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null) : pinBouncerViewModel.interactor.hintedPinLength;
                final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PinBouncerViewModel.this._hintedPinLength.setValue((Integer) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$5$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = pinBouncerViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                anonymousClass1.L$0 = (PinInputViewModel) obj;
                anonymousClass1.Z$0 = booleanValue;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                PinInputViewModel pinInputViewModel = (PinInputViewModel) this.L$0;
                this.this$0.getClass();
                boolean z = CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll;
                return ActionButtonAppearance.Shown;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass5(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PinBouncerViewModel pinBouncerViewModel = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(pinBouncerViewModel.mutablePinInput, pinBouncerViewModel.interactor.isAutoConfirmEnabled, new AnonymousClass1(pinBouncerViewModel, null));
                final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.5.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PinBouncerViewModel.this._backspaceButtonAppearance.setValue((ActionButtonAppearance) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$6$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ PinBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = pinBouncerViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean booleanValue = ((Boolean) obj2).booleanValue();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                anonymousClass1.L$0 = (PinInputViewModel) obj;
                anonymousClass1.Z$0 = booleanValue;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                PinInputViewModel pinInputViewModel = (PinInputViewModel) this.L$0;
                boolean z = this.Z$0;
                this.this$0.getClass();
                return ((CollectionsKt___CollectionsKt.last(pinInputViewModel.input) instanceof EntryToken.ClearAll) || z) ? ActionButtonAppearance.Disable : ActionButtonAppearance.Shown;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass6(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PinBouncerViewModel pinBouncerViewModel = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(pinBouncerViewModel.mutablePinInput, pinBouncerViewModel.interactor.isAutoConfirmEnabled, new AnonymousClass1(pinBouncerViewModel, null));
                final PinBouncerViewModel pinBouncerViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.6.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PinBouncerViewModel.this._confirmButtonAppearance.setValue((ActionButtonAppearance) obj2);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass8(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ReadonlyStateFlow readonlyStateFlow = this.this$0.interactor.isPinEnhancedPrivacyEnabled;
                Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L48
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                java.lang.Boolean r5 = (java.lang.Boolean) r5
                                boolean r5 = r5.booleanValue()
                                r5 = r5 ^ r3
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L48
                                return r1
                            L48:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$8$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.8.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        PinBouncerViewModel.this._isDigitButtonAnimationEnabled.updateState(null, bool);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$onActivated$2$9, reason: invalid class name */
    final class AnonymousClass9 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PinBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = pinBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass9(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final PinBouncerViewModel pinBouncerViewModel = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = pinBouncerViewModel.isWhiteBg;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel.onActivated.2.9.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        PinBouncerViewModel pinBouncerViewModel2 = PinBouncerViewModel.this;
                        boolean booleanValue = ((Boolean) pinBouncerViewModel2.isWhiteBg.$$delegate_0.getValue()).booleanValue();
                        StateFlowImpl stateFlowImpl = pinBouncerViewModel2._dotColor;
                        StateFlowImpl stateFlowImpl2 = pinBouncerViewModel2._digitButtonContentColor;
                        StateFlowImpl stateFlowImpl3 = pinBouncerViewModel2._digitButtonBackgroundColorAlpha;
                        StateFlowImpl stateFlowImpl4 = pinBouncerViewModel2._digitButtonBackgroundColor;
                        if (booleanValue) {
                            stateFlowImpl4.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_whitebg_color))));
                            stateFlowImpl3.updateState(null, Float.valueOf(0.1f));
                            stateFlowImpl2.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_whitebg_color))));
                            stateFlowImpl.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_whitebg_color))));
                        } else {
                            stateFlowImpl4.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_color))));
                            stateFlowImpl3.updateState(null, Float.valueOf(0.2f));
                            stateFlowImpl2.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pin_background_color))));
                            stateFlowImpl.updateState(null, Color.m454boximpl(ColorKt.Color(pinBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_pattern_dot_color))));
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinBouncerViewModel$onActivated$2(PinBouncerViewModel pinBouncerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pinBouncerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PinBouncerViewModel$onActivated$2 pinBouncerViewModel$onActivated$2 = new PinBouncerViewModel$onActivated$2(this.this$0, continuation);
        pinBouncerViewModel$onActivated$2.L$0 = obj;
        return pinBouncerViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PinBouncerViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.this$0, null), 7);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
