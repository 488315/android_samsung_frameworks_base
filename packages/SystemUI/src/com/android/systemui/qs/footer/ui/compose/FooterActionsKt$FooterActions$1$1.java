package com.android.systemui.qs.footer.ui.compose;

import android.content.Context;
import androidx.compose.runtime.MutableState;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsForegroundServicesButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsSecurityButtonViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class FooterActionsKt$FooterActions$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ MutableState<FooterActionsForegroundServicesButtonViewModel> $foregroundServices$delegate;
    final /* synthetic */ MutableState<FooterActionsButtonViewModel> $power$delegate;
    final /* synthetic */ LifecycleOwner $qsVisibilityLifecycleOwner;
    final /* synthetic */ MutableState<FooterActionsSecurityButtonViewModel> $security$delegate;
    final /* synthetic */ MutableState<FooterActionsButtonViewModel> $userSwitcher$delegate;
    final /* synthetic */ FooterActionsViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        final /* synthetic */ FooterActionsViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FooterActionsViewModel footerActionsViewModel, Context context, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerActionsViewModel;
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$viewModel, this.$context, continuation);
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
                Function2 function2 = this.$viewModel.observeDeviceMonitoringDialogRequests;
                Context context = this.$context;
                this.label = 1;
                if (function2.invoke(context, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ MutableState<FooterActionsForegroundServicesButtonViewModel> $foregroundServices$delegate;
        final /* synthetic */ MutableState<FooterActionsButtonViewModel> $power$delegate;
        final /* synthetic */ MutableState<FooterActionsSecurityButtonViewModel> $security$delegate;
        final /* synthetic */ MutableState<FooterActionsButtonViewModel> $userSwitcher$delegate;
        final /* synthetic */ FooterActionsViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableState<FooterActionsSecurityButtonViewModel> $security$delegate;
            final /* synthetic */ FooterActionsViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(FooterActionsViewModel footerActionsViewModel, MutableState<FooterActionsSecurityButtonViewModel> mutableState, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$security$delegate = mutableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$security$delegate, continuation);
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
                    Flow flow = this.$viewModel.security;
                    final MutableState<FooterActionsSecurityButtonViewModel> mutableState = this.$security$delegate;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.FooterActions.1.1.2.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            mutableState.setValue((FooterActionsSecurityButtonViewModel) obj2);
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

        /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$2$2, reason: invalid class name and collision with other inner class name */
        final class C03912 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableState<FooterActionsForegroundServicesButtonViewModel> $foregroundServices$delegate;
            final /* synthetic */ FooterActionsViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03912(FooterActionsViewModel footerActionsViewModel, MutableState<FooterActionsForegroundServicesButtonViewModel> mutableState, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$foregroundServices$delegate = mutableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03912(this.$viewModel, this.$foregroundServices$delegate, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03912) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = this.$viewModel.foregroundServices;
                    final MutableState<FooterActionsForegroundServicesButtonViewModel> mutableState = this.$foregroundServices$delegate;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.FooterActions.1.1.2.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            mutableState.setValue((FooterActionsForegroundServicesButtonViewModel) obj2);
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

        /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableState<FooterActionsButtonViewModel> $userSwitcher$delegate;
            final /* synthetic */ FooterActionsViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(FooterActionsViewModel footerActionsViewModel, MutableState<FooterActionsButtonViewModel> mutableState, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$userSwitcher$delegate = mutableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$userSwitcher$delegate, continuation);
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
                    Flow flow = this.$viewModel.userSwitcher;
                    final MutableState<FooterActionsButtonViewModel> mutableState = this.$userSwitcher$delegate;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.FooterActions.1.1.2.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            mutableState.setValue((FooterActionsButtonViewModel) obj2);
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

        /* renamed from: com.android.systemui.qs.footer.ui.compose.FooterActionsKt$FooterActions$1$1$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableState<FooterActionsButtonViewModel> $power$delegate;
            final /* synthetic */ FooterActionsViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(FooterActionsViewModel footerActionsViewModel, MutableState<FooterActionsButtonViewModel> mutableState, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = footerActionsViewModel;
                this.$power$delegate = mutableState;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.$viewModel, this.$power$delegate, continuation);
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
                    Flow flow = this.$viewModel.power;
                    final MutableState<FooterActionsButtonViewModel> mutableState = this.$power$delegate;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.footer.ui.compose.FooterActionsKt.FooterActions.1.1.2.4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            mutableState.setValue((FooterActionsButtonViewModel) obj2);
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
        public AnonymousClass2(FooterActionsViewModel footerActionsViewModel, MutableState<FooterActionsSecurityButtonViewModel> mutableState, MutableState<FooterActionsForegroundServicesButtonViewModel> mutableState2, MutableState<FooterActionsButtonViewModel> mutableState3, MutableState<FooterActionsButtonViewModel> mutableState4, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = footerActionsViewModel;
            this.$security$delegate = mutableState;
            this.$foregroundServices$delegate = mutableState2;
            this.$userSwitcher$delegate = mutableState3;
            this.$power$delegate = mutableState4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$security$delegate, this.$foregroundServices$delegate, this.$userSwitcher$delegate, this.$power$delegate, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$security$delegate, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new C03912(this.$viewModel, this.$foregroundServices$delegate, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$userSwitcher$delegate, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(this.$viewModel, this.$power$delegate, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FooterActionsKt$FooterActions$1$1(LifecycleOwner lifecycleOwner, FooterActionsViewModel footerActionsViewModel, Context context, MutableState<FooterActionsSecurityButtonViewModel> mutableState, MutableState<FooterActionsForegroundServicesButtonViewModel> mutableState2, MutableState<FooterActionsButtonViewModel> mutableState3, MutableState<FooterActionsButtonViewModel> mutableState4, Continuation continuation) {
        super(2, continuation);
        this.$qsVisibilityLifecycleOwner = lifecycleOwner;
        this.$viewModel = footerActionsViewModel;
        this.$context = context;
        this.$security$delegate = mutableState;
        this.$foregroundServices$delegate = mutableState2;
        this.$userSwitcher$delegate = mutableState3;
        this.$power$delegate = mutableState4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FooterActionsKt$FooterActions$1$1 footerActionsKt$FooterActions$1$1 = new FooterActionsKt$FooterActions$1$1(this.$qsVisibilityLifecycleOwner, this.$viewModel, this.$context, this.$security$delegate, this.$foregroundServices$delegate, this.$userSwitcher$delegate, this.$power$delegate, continuation);
        footerActionsKt$FooterActions$1$1.L$0 = obj;
        return footerActionsKt$FooterActions$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FooterActionsKt$FooterActions$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new AnonymousClass1(this.$viewModel, this.$context, null), 3);
            LifecycleOwner lifecycleOwner = this.$qsVisibilityLifecycleOwner;
            Lifecycle.State state = Lifecycle.State.RESUMED;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$security$delegate, this.$foregroundServices$delegate, this.$userSwitcher$delegate, this.$power$delegate, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass2, this) == coroutineSingletons) {
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
