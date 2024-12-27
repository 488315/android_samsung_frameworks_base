package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class NotificationShelfViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ NotificationIconContainerShelfViewBinder $nicBinder;
    final /* synthetic */ NotificationIconAreaController $notificationIconAreaController;
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bind$2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, FalsingManager falsingManager, NotificationIconAreaController notificationIconAreaController, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Continuation continuation) {
        super(2, continuation);
        this.$viewModel = notificationShelfViewModel;
        this.$shelf = notificationShelf;
        this.$falsingManager = falsingManager;
        this.$notificationIconAreaController = notificationIconAreaController;
        this.$nicBinder = notificationIconContainerShelfViewBinder;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NotificationShelfViewBinder$bind$2 notificationShelfViewBinder$bind$2 = new NotificationShelfViewBinder$bind$2(this.$viewModel, this.$shelf, this.$falsingManager, this.$notificationIconAreaController, this.$nicBinder, continuation);
        notificationShelfViewBinder$bind$2.L$0 = obj;
        return notificationShelfViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationShelfViewBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            ActivatableNotificationViewBinder activatableNotificationViewBinder = ActivatableNotificationViewBinder.INSTANCE;
            NotificationShelfViewModel notificationShelfViewModel = this.$viewModel;
            NotificationShelf notificationShelf = this.$shelf;
            FalsingManager falsingManager = this.$falsingManager;
            activatableNotificationViewBinder.getClass();
            ActivatableNotificationViewBinder.bind(notificationShelfViewModel, notificationShelf, falsingManager);
            NotificationShelf notificationShelf2 = this.$shelf;
            NotificationShelfViewModel notificationShelfViewModel2 = this.$viewModel;
            NotificationIconAreaController notificationIconAreaController = this.$notificationIconAreaController;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("NotifShelf#bindShelfIcons");
            }
            try {
                notificationIconAreaController.setShelfIcons(notificationShelf2.mShelfIcons);
                Unit unit = Unit.INSTANCE;
                BuildersKt.launch$default(coroutineScope, null, null, new NotificationShelfViewBinder$bind$2$1$2(notificationShelfViewModel2, notificationShelf2, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new NotificationShelfViewBinder$bind$2$1$3(notificationShelfViewModel2, notificationShelf2, null), 3);
                NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                this.L$0 = notificationShelf2;
                this.label = 1;
                if (NotificationShelfViewBinder.access$registerViewListenersWhileAttached(notificationShelfViewBinder, notificationShelf2, notificationShelfViewModel2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } finally {
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
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
