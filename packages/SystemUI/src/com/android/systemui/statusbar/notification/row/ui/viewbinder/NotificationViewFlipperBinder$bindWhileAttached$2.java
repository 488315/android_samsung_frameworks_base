package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import android.widget.ViewFlipper;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.NotificationViewFlipperViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationViewFlipperBinder$bindWhileAttached$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ ViewFlipper $viewFlipper;
    final /* synthetic */ NotificationViewFlipperViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public NotificationViewFlipperBinder$bindWhileAttached$2(ViewFlipper viewFlipper, NotificationViewFlipperViewModel notificationViewFlipperViewModel, Continuation continuation) {
        super(3, continuation);
        this.$viewFlipper = viewFlipper;
        this.$viewModel = notificationViewFlipperViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationViewFlipperBinder$bindWhileAttached$2 notificationViewFlipperBinder$bindWhileAttached$2 = new NotificationViewFlipperBinder$bindWhileAttached$2(this.$viewFlipper, this.$viewModel, (Continuation) obj3);
        notificationViewFlipperBinder$bindWhileAttached$2.L$0 = (LifecycleOwner) obj;
        return notificationViewFlipperBinder$bindWhileAttached$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$viewFlipper, this.$viewModel, null), 3);
        return Unit.INSTANCE;
    }
}
