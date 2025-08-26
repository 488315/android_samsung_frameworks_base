package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public final class ActivatableNotificationViewBinder {
    public static final ActivatableNotificationViewBinder INSTANCE = new ActivatableNotificationViewBinder();

    /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ TouchHandler $touchHandler;
        final /* synthetic */ ActivatableNotificationView $view;
        final /* synthetic */ ActivatableNotificationViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C05011 extends SuspendLambda implements Function2 {
            final /* synthetic */ TouchHandler $touchHandler;
            final /* synthetic */ ActivatableNotificationView $view;
            final /* synthetic */ ActivatableNotificationViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05021 extends SuspendLambda implements Function2 {
                final /* synthetic */ TouchHandler $touchHandler;
                final /* synthetic */ ActivatableNotificationViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05021(ActivatableNotificationViewModel activatableNotificationViewModel, TouchHandler touchHandler, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = activatableNotificationViewModel;
                    this.$touchHandler = touchHandler;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05021(this.$viewModel, this.$touchHandler, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05021) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Flow flowIsTouchable = this.$viewModel.isTouchable();
                        final TouchHandler touchHandler = this.$touchHandler;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder.bind.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                touchHandler.isTouchEnabled = ((Boolean) obj2).booleanValue();
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (flowIsTouchable.collect(flowCollector, this) == coroutineSingletons) {
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
            public C05011(ActivatableNotificationView activatableNotificationView, TouchHandler touchHandler, ActivatableNotificationViewModel activatableNotificationViewModel, Continuation continuation) {
                super(2, continuation);
                this.$view = activatableNotificationView;
                this.$touchHandler = touchHandler;
                this.$viewModel = activatableNotificationViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C05011 c05011 = new C05011(this.$view, this.$touchHandler, this.$viewModel, continuation);
                c05011.L$0 = obj;
                return c05011;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05011) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineTracingKt.launchTraced$default((CoroutineScope) this.L$0, null, null, new C05021(this.$viewModel, this.$touchHandler, null), 7);
                    ActivatableNotificationViewBinder activatableNotificationViewBinder = ActivatableNotificationViewBinder.INSTANCE;
                    ActivatableNotificationView activatableNotificationView = this.$view;
                    TouchHandler touchHandler = this.$touchHandler;
                    this.label = 1;
                    if (ActivatableNotificationViewBinder.access$registerListenersWhileAttached(activatableNotificationViewBinder, activatableNotificationView, touchHandler, this) == coroutineSingletons) {
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
        public AnonymousClass1(ActivatableNotificationView activatableNotificationView, TouchHandler touchHandler, ActivatableNotificationViewModel activatableNotificationViewModel, Continuation continuation) {
            super(3, continuation);
            this.$view = activatableNotificationView;
            this.$touchHandler = touchHandler;
            this.$viewModel = activatableNotificationViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$touchHandler, this.$viewModel, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C05011 c05011 = new C05011(this.$view, this.$touchHandler, this.$viewModel, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05011, this) == coroutineSingletons) {
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

    private ActivatableNotificationViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons access$registerListenersWhileAttached(ActivatableNotificationViewBinder activatableNotificationViewBinder, ActivatableNotificationView activatableNotificationView, TouchHandler touchHandler, ContinuationImpl continuationImpl) {
        ActivatableNotificationViewBinder$registerListenersWhileAttached$1 activatableNotificationViewBinder$registerListenersWhileAttached$1;
        activatableNotificationViewBinder.getClass();
        if (continuationImpl instanceof ActivatableNotificationViewBinder$registerListenersWhileAttached$1) {
            activatableNotificationViewBinder$registerListenersWhileAttached$1 = (ActivatableNotificationViewBinder$registerListenersWhileAttached$1) continuationImpl;
            int i = activatableNotificationViewBinder$registerListenersWhileAttached$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                activatableNotificationViewBinder$registerListenersWhileAttached$1.label = i - Integer.MIN_VALUE;
            } else {
                activatableNotificationViewBinder$registerListenersWhileAttached$1 = new ActivatableNotificationViewBinder$registerListenersWhileAttached$1(activatableNotificationViewBinder, continuationImpl);
            }
        }
        Object obj = activatableNotificationViewBinder$registerListenersWhileAttached$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = activatableNotificationViewBinder$registerListenersWhileAttached$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                activatableNotificationView.setOnTouchListener(touchHandler);
                activatableNotificationView.mTouchHandler = touchHandler;
                activatableNotificationViewBinder$registerListenersWhileAttached$1.L$0 = activatableNotificationView;
                activatableNotificationViewBinder$registerListenersWhileAttached$1.label = 1;
                if (DelayKt.awaitCancellation(activatableNotificationViewBinder$registerListenersWhileAttached$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                activatableNotificationView = (ActivatableNotificationView) activatableNotificationViewBinder$registerListenersWhileAttached$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            activatableNotificationView.mTouchHandler = null;
            activatableNotificationView.setOnTouchListener(null);
            throw th;
        }
    }

    public static void bind(ActivatableNotificationViewModel activatableNotificationViewModel, ActivatableNotificationView activatableNotificationView, FalsingManager falsingManager) {
        ExpandableOutlineViewBinder.INSTANCE.getClass();
        ExpandableViewBinder.INSTANCE.getClass();
        RepeatWhenAttachedKt.repeatWhenAttached(activatableNotificationView, EmptyCoroutineContext.INSTANCE, new AnonymousClass1(activatableNotificationView, new TouchHandler(activatableNotificationView, falsingManager), activatableNotificationViewModel, null));
    }
}
