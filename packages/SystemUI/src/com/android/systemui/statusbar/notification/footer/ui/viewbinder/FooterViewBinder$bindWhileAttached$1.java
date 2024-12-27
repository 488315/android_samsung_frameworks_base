package com.android.systemui.statusbar.notification.footer.ui.viewbinder;

import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
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
final class FooterViewBinder$bindWhileAttached$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ View.OnClickListener $clearAllNotifications;
    final /* synthetic */ FooterView $footer;
    final /* synthetic */ View.OnClickListener $launchNotificationHistory;
    final /* synthetic */ View.OnClickListener $launchNotificationSettings;
    final /* synthetic */ FooterViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.notification.footer.ui.viewbinder.FooterViewBinder$bindWhileAttached$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ View.OnClickListener $clearAllNotifications;
        final /* synthetic */ FooterView $footer;
        final /* synthetic */ View.OnClickListener $launchNotificationHistory;
        final /* synthetic */ View.OnClickListener $launchNotificationSettings;
        final /* synthetic */ FooterViewModel $viewModel;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, View.OnClickListener onClickListener3, Continuation continuation) {
            super(2, continuation);
            this.$footer = footerView;
            this.$viewModel = footerViewModel;
            this.$clearAllNotifications = onClickListener;
            this.$launchNotificationSettings = onClickListener2;
            this.$launchNotificationHistory = onClickListener3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$footer, this.$viewModel, this.$clearAllNotifications, this.$launchNotificationSettings, this.$launchNotificationHistory, continuation);
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
                FooterViewBinder footerViewBinder = FooterViewBinder.INSTANCE;
                FooterView footerView = this.$footer;
                FooterViewModel footerViewModel = this.$viewModel;
                View.OnClickListener onClickListener = this.$clearAllNotifications;
                View.OnClickListener onClickListener2 = this.$launchNotificationSettings;
                View.OnClickListener onClickListener3 = this.$launchNotificationHistory;
                this.label = 1;
                footerViewBinder.getClass();
                if (CoroutineScopeKt.coroutineScope(new FooterViewBinder$bind$2(footerView, footerViewModel, onClickListener, onClickListener2, onClickListener3, null), this) == coroutineSingletons) {
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
    public FooterViewBinder$bindWhileAttached$1(FooterView footerView, FooterViewModel footerViewModel, View.OnClickListener onClickListener, View.OnClickListener onClickListener2, View.OnClickListener onClickListener3, Continuation continuation) {
        super(3, continuation);
        this.$footer = footerView;
        this.$viewModel = footerViewModel;
        this.$clearAllNotifications = onClickListener;
        this.$launchNotificationSettings = onClickListener2;
        this.$launchNotificationHistory = onClickListener3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FooterViewBinder$bindWhileAttached$1 footerViewBinder$bindWhileAttached$1 = new FooterViewBinder$bindWhileAttached$1(this.$footer, this.$viewModel, this.$clearAllNotifications, this.$launchNotificationSettings, this.$launchNotificationHistory, (Continuation) obj3);
        footerViewBinder$bindWhileAttached$1.L$0 = (LifecycleOwner) obj;
        return footerViewBinder$bindWhileAttached$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) this.L$0), null, null, new AnonymousClass1(this.$footer, this.$viewModel, this.$clearAllNotifications, this.$launchNotificationSettings, this.$launchNotificationHistory, null), 3);
        return Unit.INSTANCE;
    }
}
