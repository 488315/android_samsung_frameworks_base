package com.android.systemui.bouncer.ui.viewmodel;

import android.graphics.Bitmap;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$special$$inlined$map$2;
import com.android.systemui.authentication.shared.model.BouncerInputSide;
import com.android.systemui.bouncer.data.repository.BouncerRepository;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor$special$$inlined$map$1;
import com.android.systemui.bouncer.shared.model.BouncerActionButtonModel;
import com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel$special$$inlined$map$1;
import com.android.systemui.user.ui.viewmodel.UserViewModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class BouncerOverlayContentViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BouncerOverlayContentViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                BouncerMessageViewModel message = this.this$0.getMessage();
                this.label = 1;
                if (message.activate(this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$10, reason: invalid class name */
    final class AnonymousClass10 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass10(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = bouncerOverlayContentViewModel.bouncerInteractor.preferredBouncerInputSide;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.10.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BouncerOverlayContentViewModel.this._isInputPreferredOnLeftSide.updateState(null, Boolean.valueOf(((BouncerInputSide) obj2) == BouncerInputSide.LEFT));
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11, reason: invalid class name */
    final class AnonymousClass11 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass11(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass11(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass11) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                final ReadonlyStateFlow readonlyStateFlow = bouncerOverlayContentViewModel.authMethodViewModel;
                Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ BouncerOverlayContentViewModel this$0;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = bouncerOverlayContentViewModel;
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
                                boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L4b
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel) r5
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel r6 = r4.this$0
                                r6.getClass()
                                boolean r5 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel
                                r5 = r5 ^ r3
                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L4b
                                return r1
                            L4b:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$11$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, bouncerOverlayContentViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.11.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        BouncerOverlayContentViewModel.this._isFoldSplitRequired.updateState(null, bool);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12, reason: invalid class name */
    final class AnonymousClass12 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass12(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass12(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass12) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final BouncerMessageViewModel$special$$inlined$map$1 bouncerMessageViewModel$special$$inlined$map$1 = this.this$0.getMessage().isLockoutMessagePresent;
                Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1$2$1
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
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$12$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.12.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        BouncerOverlayContentViewModel.this._isInputEnabled.updateState(null, bool);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$2, reason: invalid class name and collision with other inner class name */
        final class C00852 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$launch;
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00852(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
                this.$$this$launch = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C00852 c00852 = new C00852(this.this$0, this.$$this$launch, continuation);
                c00852.L$0 = obj;
                return c00852;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00852) create((AuthMethodBouncerViewModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AuthMethodBouncerViewModel authMethodBouncerViewModel = (AuthMethodBouncerViewModel) this.L$0;
                    this.this$0._authMethodViewModel.setValue(authMethodBouncerViewModel);
                    if (authMethodBouncerViewModel == null) {
                        return Unit.INSTANCE;
                    }
                    this.label = 1;
                    if (authMethodBouncerViewModel.activate(this) == coroutineSingletons) {
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
        public AnonymousClass2(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                final Flow flow = bouncerOverlayContentViewModel.authenticationInteractor.authenticationMethod;
                Flow flow2 = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ BouncerOverlayContentViewModel receiver$inlined;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, BouncerOverlayContentViewModel bouncerOverlayContentViewModel) {
                            this.$this_unsafeFlow = flowCollector;
                            this.receiver$inlined = bouncerOverlayContentViewModel;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                            /*
                                r8 = this;
                                boolean r0 = r10 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r10
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r10)
                            L18:
                                java.lang.Object r10 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L30
                                if (r2 != r3) goto L28
                                kotlin.ResultKt.throwOnFailure(r10)
                                goto La3
                            L28:
                                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                                r8.<init>(r9)
                                throw r8
                            L30:
                                kotlin.ResultKt.throwOnFailure(r10)
                                com.android.systemui.authentication.shared.model.AuthenticationMethodModel r9 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r9
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel r10 = r8.receiver$inlined
                                kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r10.authMethodViewModel
                                kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
                                java.lang.Object r2 = r2.getValue()
                                com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel r2 = (com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel) r2
                                r4 = 0
                                if (r2 == 0) goto L49
                                com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = r2.getAuthenticationMethod()
                                goto L4a
                            L49:
                                r5 = r4
                            L4a:
                                boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r9, r5)
                                if (r5 == 0) goto L51
                                goto L98
                            L51:
                                boolean r2 = r9 instanceof com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pin
                                kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r10.isInputEnabled
                                com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel$Factory r6 = r10.pinViewModelFactory
                                com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer r7 = r10.bouncerHapticPlayer
                                if (r2 == 0) goto L67
                                com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Pin r9 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pin) r9
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$1 r2 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$1
                                r2.<init>(r10)
                                com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r2 = r6.create(r5, r2, r9, r7)
                                goto L98
                            L67:
                                boolean r2 = r9 instanceof com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Sim
                                if (r2 == 0) goto L77
                                com.android.systemui.authentication.shared.model.AuthenticationMethodModel$Sim r9 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Sim) r9
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$2 r2 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$2
                                r2.<init>(r10)
                                com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r2 = r6.create(r5, r2, r9, r7)
                                goto L98
                            L77:
                                boolean r2 = r9 instanceof com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Password
                                if (r2 == 0) goto L87
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$3 r9 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$3
                                r9.<init>(r10)
                                com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel$Factory r10 = r10.passwordViewModelFactory
                                com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel r2 = r10.create(r5, r9)
                                goto L98
                            L87:
                                boolean r9 = r9 instanceof com.android.systemui.authentication.shared.model.AuthenticationMethodModel.Pattern
                                if (r9 == 0) goto L97
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$4 r9 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getChildViewModel$4
                                r9.<init>(r10)
                                com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel$Factory r10 = r10.patternViewModelFactory
                                com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel r2 = r10.create(r7, r5, r9)
                                goto L98
                            L97:
                                r2 = r4
                            L98:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                                java.lang.Object r8 = r8.emit(r2, r0)
                                if (r8 != r1) goto La3
                                return r1
                            La3:
                                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                                return r8
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$2$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, bouncerOverlayContentViewModel), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                C00852 c00852 = new C00852(this.this$0, coroutineScope, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow2, c00852, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                AuthenticationInteractor$special$$inlined$map$2 authenticationInteractor$special$$inlined$map$2 = bouncerOverlayContentViewModel.authenticationInteractor.upcomingWipe;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.3.1
                    /* JADX WARN: Code restructure failed: missing block: B:16:0x007f, code lost:
                    
                        if (r6 != null) goto L11;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:8:0x0049, code lost:
                    
                        if (r6 != null) goto L11;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:9:0x004c, code lost:
                    
                        r2 = r6;
                     */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            com.android.systemui.authentication.shared.model.AuthenticationWipeModel r7 = (com.android.systemui.authentication.shared.model.AuthenticationWipeModel) r7
                            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel r6 = com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.this
                            kotlinx.coroutines.flow.StateFlowImpl r8 = r6.wipeDialogMessage
                            if (r7 == 0) goto L82
                            int r0 = r7.remainingAttempts
                            int r1 = r7.failedAttempts
                            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget r7 = r7.wipeTarget
                            if (r0 <= 0) goto L4e
                            android.content.Context r2 = r6.applicationContext
                            int r3 = r7.messageIdForAlmostWipe
                            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
                            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
                            java.lang.Object[] r4 = new java.lang.Object[]{r4, r5}
                            java.lang.String r2 = r2.getString(r3, r4)
                            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$ManagedProfile r3 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE
                            boolean r7 = r7.equals(r3)
                            if (r7 == 0) goto L83
                            android.app.admin.DevicePolicyManager r6 = r6.devicePolicyManager
                            android.app.admin.DevicePolicyResourcesManager r6 = r6.getResources()
                            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getAlmostAtWipeMessage$1 r7 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getAlmostAtWipeMessage$1
                            r7.<init>()
                            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
                            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                            java.lang.Object[] r0 = new java.lang.Object[]{r1, r0}
                            java.lang.String r1 = "SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ALMOST_ERASING_PROFILE"
                            java.lang.String r6 = r6.getString(r1, r7, r0)
                            if (r6 != 0) goto L4c
                            goto L83
                        L4c:
                            r2 = r6
                            goto L83
                        L4e:
                            android.content.Context r0 = r6.applicationContext
                            int r2 = r7.messageIdForWipe
                            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
                            java.lang.Object[] r3 = new java.lang.Object[]{r3}
                            java.lang.String r2 = r0.getString(r2, r3)
                            com.android.systemui.authentication.shared.model.AuthenticationWipeModel$WipeTarget$ManagedProfile r0 = com.android.systemui.authentication.shared.model.AuthenticationWipeModel.WipeTarget.ManagedProfile.INSTANCE
                            boolean r7 = r7.equals(r0)
                            if (r7 == 0) goto L83
                            android.app.admin.DevicePolicyManager r6 = r6.devicePolicyManager
                            android.app.admin.DevicePolicyResourcesManager r6 = r6.getResources()
                            com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getWipeMessage$1 r7 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$getWipeMessage$1
                            r7.<init>()
                            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
                            java.lang.Object[] r0 = new java.lang.Object[]{r0}
                            java.lang.String r1 = "SystemUi.KEYGUARD_DIALOG_FAILED_ATTEMPTS_ERASING_PROFILE"
                            java.lang.String r6 = r6.getString(r1, r7, r0)
                            if (r6 != 0) goto L4c
                            goto L83
                        L82:
                            r2 = 0
                        L83:
                            r8.setValue(r2)
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2.AnonymousClass3.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                };
                this.label = 1;
                if (authenticationInteractor$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                final UserSwitcherViewModel$special$$inlined$map$1 userSwitcherViewModel$special$$inlined$map$1 = this.this$0.userSwitcher.selectedUser;
                Flow flow = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L45
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.user.ui.viewmodel.UserViewModel r5 = (com.android.systemui.user.ui.viewmodel.UserViewModel) r5
                                android.graphics.drawable.Drawable r5 = r5.image
                                android.graphics.Bitmap r5 = androidx.core.graphics.drawable.DrawableKt.toBitmap$default(r5)
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L45
                                return r1
                            L45:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$4$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.4.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BouncerOverlayContentViewModel.this._selectedUserImage.setValue((Bitmap) obj2);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$5$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj3);
                anonymousClass1.L$0 = (List) obj;
                anonymousClass1.L$1 = (List) obj2;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                List list = (List) this.L$0;
                List list2 = (List) this.L$1;
                List<UserViewModel> list3 = list;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                for (UserViewModel userViewModel : list3) {
                    Icon.Loaded loaded = new Icon.Loaded(userViewModel.image, null, null, 4, null);
                    Function0 function0 = userViewModel.onClicked;
                    if (function0 == null) {
                        function0 = new BouncerOverlayContentViewModel$onActivated$2$5$1$$ExternalSyntheticLambda0();
                    }
                    arrayList.add(new BouncerOverlayContentViewModel.UserSwitcherDropdownItemViewModel(loaded, userViewModel.name, function0));
                }
                List<UserActionViewModel> list4 = list2;
                BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                for (UserActionViewModel userActionViewModel : list4) {
                    arrayList2.add(new BouncerOverlayContentViewModel.UserSwitcherDropdownItemViewModel(new Icon.Loaded(bouncerOverlayContentViewModel.applicationContext.getResources().getDrawable(userActionViewModel.iconResourceId), null, null, 4, null), new Text.Resource(userActionViewModel.textResourceId), userActionViewModel.onClicked));
                }
                return CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                UserSwitcherViewModel userSwitcherViewModel = bouncerOverlayContentViewModel.userSwitcher;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(userSwitcherViewModel.users, userSwitcherViewModel.menu, new AnonymousClass1(bouncerOverlayContentViewModel, null));
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.5.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BouncerOverlayContentViewModel.this._userSwitcherDropdown.setValue((List) obj2);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$6$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            int label;
            final /* synthetic */ BouncerOverlayContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
                super(3, continuation);
                this.this$0 = bouncerOverlayContentViewModel;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return new AnonymousClass1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.this$0.createDialogViewModel();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(bouncerOverlayContentViewModel.wipeDialogMessage, bouncerOverlayContentViewModel.lockoutDialogMessage, new AnonymousClass1(bouncerOverlayContentViewModel, null));
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.6.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BouncerOverlayContentViewModel.this._dialogViewModel.setValue((BouncerOverlayContentViewModel.DialogViewModel) obj2);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                Flow flow = bouncerOverlayContentViewModel.actionButtonInteractor.actionButton;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.7.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        BouncerOverlayContentViewModel.this._actionButton.setValue((BouncerActionButtonModel) obj2);
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$8$3, reason: invalid class name */
        final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3 {
            public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

            public AnonymousClass3() {
                super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return new Pair(bool, (Float) obj2);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                BouncerInteractor bouncerInteractor = this.this$0.bouncerInteractor;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(bouncerInteractor.isOneHandedModeSupported, bouncerInteractor.lastRecordedLockscreenTouchPosition, AnonymousClass3.INSTANCE);
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.8.4
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Pair pair = (Pair) obj2;
                        Boolean bool = (Boolean) pair.component1();
                        boolean booleanValue = bool.booleanValue();
                        Float f = (Float) pair.component2();
                        BouncerOverlayContentViewModel bouncerOverlayContentViewModel2 = BouncerOverlayContentViewModel.this;
                        bouncerOverlayContentViewModel2._isOneHandedModeSupported.updateState(null, bool);
                        if (booleanValue && f != null) {
                            BouncerInputSide bouncerInputSide = f.floatValue() < ((float) (bouncerOverlayContentViewModel2.applicationContext.getResources().getDisplayMetrics().widthPixels / 2)) ? BouncerInputSide.LEFT : BouncerInputSide.RIGHT;
                            BouncerRepository bouncerRepository = bouncerOverlayContentViewModel2.bouncerInteractor.repository;
                            bouncerRepository.getClass();
                            bouncerRepository.globalSettings.putInt("one_handed_keyguard_side", bouncerInputSide.getSettingValue());
                            bouncerRepository.preferredBouncerInputSide.updateState(null, bouncerInputSide);
                        }
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
    /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel$onActivated$2$9, reason: invalid class name */
    final class AnonymousClass9 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ BouncerOverlayContentViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = bouncerOverlayContentViewModel;
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
                final BouncerOverlayContentViewModel bouncerOverlayContentViewModel = this.this$0;
                BouncerInteractor$special$$inlined$map$1 bouncerInteractor$special$$inlined$map$1 = bouncerOverlayContentViewModel.bouncerInteractor.isUserSwitcherVisible;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerOverlayContentViewModel.onActivated.2.9.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Boolean bool = (Boolean) obj2;
                        bool.getClass();
                        BouncerOverlayContentViewModel.this._isUserSwitcherVisible.updateState(null, bool);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (bouncerInteractor$special$$inlined$map$1.collect(flowCollector, this) == coroutineSingletons) {
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
    public BouncerOverlayContentViewModel$onActivated$2(BouncerOverlayContentViewModel bouncerOverlayContentViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerOverlayContentViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerOverlayContentViewModel$onActivated$2 bouncerOverlayContentViewModel$onActivated$2 = new BouncerOverlayContentViewModel$onActivated$2(this.this$0, continuation);
        bouncerOverlayContentViewModel$onActivated$2.L$0 = obj;
        return bouncerOverlayContentViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerOverlayContentViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass10(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass11(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass12(this.this$0, null), 7);
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
