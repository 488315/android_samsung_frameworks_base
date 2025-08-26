package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DisposableHandle;

/* loaded from: classes3.dex */
public final class NotificationViewFlipperBinder {
    public static final NotificationViewFlipperBinder INSTANCE = new NotificationViewFlipperBinder();

    /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bindWhileAttached$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function3 {
        final /* synthetic */ ViewFlipper $viewFlipper;
        final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bindWhileAttached$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ ViewFlipper $viewFlipper;
            final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
                super(2, continuation);
                this.$viewFlipper = viewFlipper;
                this.$viewModel = notificationViewFlipperViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewFlipper, this.$viewModel, continuation);
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
                    NotificationViewFlipperBinder notificationViewFlipperBinder = NotificationViewFlipperBinder.INSTANCE;
                    ViewFlipper viewFlipper = this.$viewFlipper;
                    NotificationViewFlipperViewModel notificationViewFlipperViewModel = this.$viewModel;
                    this.label = 1;
                    notificationViewFlipperBinder.getClass();
                    if (CoroutineScopeKt.coroutineScope(new NotificationViewFlipperBinder$bind$2(notificationViewFlipperViewModel, viewFlipper, null), this) == coroutineSingletons) {
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
        public AnonymousClass2(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
            super(3, continuation);
            this.$viewFlipper = viewFlipper;
            this.$viewModel = notificationViewFlipperViewModel;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewFlipper, this.$viewModel, (Continuation) obj3);
            anonymousClass2.L$0 = (LifecycleOwner) obj;
            return anonymousClass2.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineTracingKt.launchTraced$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$viewFlipper, this.$viewModel, null), 7);
            return Unit.INSTANCE;
        }
    }

    private NotificationViewFlipperBinder() {
    }

    public static void bindWhileAttached(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel) {
        if (viewFlipper.isAutoStart()) {
            RepeatWhenAttachedKt.repeatWhenAttached(viewFlipper, EmptyCoroutineContext.INSTANCE, new AnonymousClass2(viewFlipper, notificationViewFlipperViewModel, null));
        } else {
            AnonymousClass1 anonymousClass1 = AnonymousClass1.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.row.ui.viewbinder.NotificationViewFlipperBinder$bindWhileAttached$1, reason: invalid class name */
    public final class AnonymousClass1 implements DisposableHandle {
        public static final AnonymousClass1 INSTANCE = null;

        static {
            new AnonymousClass1();
        }

        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
        }
    }
}
