package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.inputmethod.data.repository.InputMethodRepositoryImpl;
import com.android.systemui.inputmethod.domain.interactor.InputMethodInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.ChannelAsFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PasswordBouncerViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PasswordBouncerViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                this.label = 1;
                PasswordBouncerViewModel.Companion companion = PasswordBouncerViewModel.Companion;
                passwordBouncerViewModel.getClass();
                if (AuthMethodBouncerViewModel.onActivated$suspendImpl(passwordBouncerViewModel, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PasswordBouncerViewModel.Request request = (PasswordBouncerViewModel.Request) obj2;
                        boolean z = request instanceof PasswordBouncerViewModel.OnImeSwitcherButtonClicked;
                        PasswordBouncerViewModel passwordBouncerViewModel2 = PasswordBouncerViewModel.this;
                        if (z) {
                            Object showInputMethodPicker = ((InputMethodRepositoryImpl) passwordBouncerViewModel2.inputMethodInteractor.repository).showInputMethodPicker(((PasswordBouncerViewModel.OnImeSwitcherButtonClicked) request).displayId, continuation);
                            CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                            if (showInputMethodPicker != coroutineSingletons2) {
                                showInputMethodPicker = Unit.INSTANCE;
                            }
                            return showInputMethodPicker == coroutineSingletons2 ? showInputMethodPicker : Unit.INSTANCE;
                        }
                        if (!(request instanceof PasswordBouncerViewModel.OnImeDismissed)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        SharedFlowImpl sharedFlowImpl = passwordBouncerViewModel2.interactor._onImeHiddenByUser;
                        Unit unit = Unit.INSTANCE;
                        Object emit = sharedFlowImpl.emit(unit, continuation);
                        CoroutineSingletons coroutineSingletons3 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (emit != coroutineSingletons3) {
                            emit = unit;
                        }
                        return emit == coroutineSingletons3 ? emit : unit;
                    }
                };
                this.label = 1;
                if (receiveAsFlow.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ boolean Z$0;
            /* synthetic */ boolean Z$1;
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = passwordBouncerViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                anonymousClass1.Z$0 = booleanValue;
                anonymousClass1.Z$1 = booleanValue2;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf((!this.Z$0 || this.Z$1 || this.this$0.wasSuccessfullyAuthenticated) ? false : true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(passwordBouncerViewModel.isInputEnabled, passwordBouncerViewModel.isTextFieldFocused, new AnonymousClass1(passwordBouncerViewModel, null));
                final PasswordBouncerViewModel passwordBouncerViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        PasswordBouncerViewModel.this._isTextFieldFocusRequested.updateState(null, bool);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                Flow flow = passwordBouncerViewModel.selectedUserInteractor.selectedUser;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PasswordBouncerViewModel.this._selectedUserId.updateState(null, new Integer(((Number) obj2).intValue()));
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            int label;

            public AnonymousClass1(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Number) obj).intValue();
                return new AnonymousClass1((Continuation) obj2).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    PasswordBouncerViewModel.Companion.getClass();
                    long j = PasswordBouncerViewModel.DELAY_TO_FETCH_IMES;
                    this.label = 1;
                    if (DelayKt.m3449delayVtjQ1oo(j, this) == coroutineSingletons) {
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
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$5$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function3 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ PasswordBouncerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = passwordBouncerViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                int intValue = ((Number) obj).intValue();
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, (Continuation) obj3);
                anonymousClass2.I$0 = intValue;
                return anonymousClass2.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                ResultKt.throwOnFailure(obj);
                int i2 = this.I$0;
                InputMethodInteractor inputMethodInteractor = this.this$0.inputMethodInteractor;
                this.label = 1;
                Object hasMultipleEnabledImesOrSubtypes = inputMethodInteractor.hasMultipleEnabledImesOrSubtypes(i2, this);
                return hasMultipleEnabledImesOrSubtypes == coroutineSingletons ? coroutineSingletons : hasMultipleEnabledImesOrSubtypes;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.selectedUserInteractor.selectedUser, new AnonymousClass1(null)), com.android.systemui.util.kotlin.FlowKt.onSubscriberAdded(this.this$0._isImeSwitcherButtonVisible), new AnonymousClass2(this.this$0, null));
                final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.5.3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        PasswordBouncerViewModel.this._isImeSwitcherButtonVisible.updateState(null, bool);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                ReadonlyStateFlow readonlyStateFlow = passwordBouncerViewModel.isWhiteBg;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.6.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        PasswordBouncerViewModel passwordBouncerViewModel2 = PasswordBouncerViewModel.this;
                        StateFlowImpl stateFlowImpl = passwordBouncerViewModel2._textColor;
                        StateFlowImpl stateFlowImpl2 = passwordBouncerViewModel2._entryBackgroundColor;
                        if (booleanValue) {
                            stateFlowImpl2.updateState(null, Color.m454boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_entry_background_whitebg_color))));
                            stateFlowImpl.updateState(null, Color.m454boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_text_whitebg_color))));
                        } else {
                            stateFlowImpl2.updateState(null, Color.m454boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_entry_background_color))));
                            stateFlowImpl.updateState(null, Color.m454boximpl(ColorKt.Color(passwordBouncerViewModel2.applicationContext.getColor(R.color.kg_compose_password_text_color))));
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass7(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$onActivated$2$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ PasswordBouncerViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = passwordBouncerViewModel;
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
                final PasswordBouncerViewModel passwordBouncerViewModel = this.this$0;
                Flow flow = ((ConnectedDisplayInteractorImpl) passwordBouncerViewModel.connectedDisplayInteractor).isExternalDesktopWindowing;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel.onActivated.2.8.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        PasswordBouncerViewModel.this.isExternalDesktopWindowing = ((Boolean) obj2).booleanValue();
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PasswordBouncerViewModel$onActivated$2(PasswordBouncerViewModel passwordBouncerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = passwordBouncerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PasswordBouncerViewModel$onActivated$2 passwordBouncerViewModel$onActivated$2 = new PasswordBouncerViewModel$onActivated$2(this.this$0, continuation);
        passwordBouncerViewModel$onActivated$2.L$0 = obj;
        return passwordBouncerViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PasswordBouncerViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.this$0, null), 7);
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
