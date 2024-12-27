package com.android.systemui.statusbar.pipeline.mobile.ui.binder;

import android.view.ViewGroup;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileViewLogger;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.LocationBasedMobileViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.StateFlow;

public final class MobileIconBinder$bind$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ MobileViewLogger $logger;
    final /* synthetic */ ViewGroup $view;
    final /* synthetic */ LocationBasedMobileViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ MobileViewLogger $logger;
        final /* synthetic */ ViewGroup $view;
        final /* synthetic */ LocationBasedMobileViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$2$1$1, reason: invalid class name and collision with other inner class name */
        final class C02331 extends SuspendLambda implements Function2 {
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            public C02331(LocationBasedMobileViewModel locationBasedMobileViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02331(this.$viewModel, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02331) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow dexStatusBarIcon = this.$viewModel.commonImpl.getDexStatusBarIcon();
                    this.label = 1;
                    if (FlowKt.collect(dexStatusBarIcon, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.statusbar.pipeline.mobile.ui.binder.MobileIconBinder$bind$2$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ LocationBasedMobileViewModel $viewModel;
            int label;

            public AnonymousClass2(LocationBasedMobileViewModel locationBasedMobileViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = locationBasedMobileViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, continuation);
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
                    StateFlow updateDeXStatusBarIconModel = this.$viewModel.commonImpl.getUpdateDeXStatusBarIconModel();
                    if (updateDeXStatusBarIconModel != null) {
                        this.label = 1;
                        if (FlowKt.collect(updateDeXStatusBarIconModel, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
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

        public AnonymousClass1(MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$logger = mobileViewLogger;
            this.$view = viewGroup;
            this.$viewModel = locationBasedMobileViewModel;
            this.$isCollecting = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$logger, this.$view, this.$viewModel, this.$isCollecting, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    this.$logger.logCollectionStarted(this.$view, this.$viewModel);
                    this.$isCollecting.element = true;
                    BuildersKt.launch$default(coroutineScope, null, null, new C02331(this.$viewModel, null), 3);
                    BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, null), 3);
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
            } catch (Throwable th) {
                this.$isCollecting.element = false;
                this.$logger.logCollectionStopped(this.$view, this.$viewModel);
                throw th;
            }
        }
    }

    public MobileIconBinder$bind$2(MobileViewLogger mobileViewLogger, ViewGroup viewGroup, LocationBasedMobileViewModel locationBasedMobileViewModel, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
        super(3, continuation);
        this.$logger = mobileViewLogger;
        this.$view = viewGroup;
        this.$viewModel = locationBasedMobileViewModel;
        this.$isCollecting = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MobileIconBinder$bind$2 mobileIconBinder$bind$2 = new MobileIconBinder$bind$2(this.$logger, this.$view, this.$viewModel, this.$isCollecting, (Continuation) obj3);
        mobileIconBinder$bind$2.L$0 = (LifecycleOwner) obj;
        return mobileIconBinder$bind$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.CREATED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$logger, this.$view, this.$viewModel, this.$isCollecting, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
