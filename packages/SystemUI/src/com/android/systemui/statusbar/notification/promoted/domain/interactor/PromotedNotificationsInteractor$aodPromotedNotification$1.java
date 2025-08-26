package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class PromotedNotificationsInteractor$aodPromotedNotification$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromotedNotificationsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromotedNotificationsInteractor$aodPromotedNotification$1(PromotedNotificationsInteractor promotedNotificationsInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = promotedNotificationsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromotedNotificationsInteractor$aodPromotedNotification$1 promotedNotificationsInteractor$aodPromotedNotification$1 = new PromotedNotificationsInteractor$aodPromotedNotification$1(this.this$0, (Continuation) obj3);
        promotedNotificationsInteractor$aodPromotedNotification$1.L$0 = (PromotedNotificationContentModels) obj;
        promotedNotificationsInteractor$aodPromotedNotification$1.L$1 = (List) obj2;
        return promotedNotificationsInteractor$aodPromotedNotification$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PromotedNotificationContentModels promotedNotificationContentModels = (PromotedNotificationContentModels) this.L$0;
        List list = (List) this.L$1;
        if (promotedNotificationContentModels != null) {
            this.this$0.getClass();
            if (promotedNotificationContentModels.privateVersion.style == PromotedNotificationContentModel.Style.Ineligible) {
                promotedNotificationContentModels = null;
            }
            if (promotedNotificationContentModels != null) {
                return promotedNotificationContentModels;
            }
        }
        this.this$0.getClass();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PromotedNotificationContentModels promotedNotificationContentModels2 = ((ActiveNotificationModel) it.next()).promotedContent;
            if (promotedNotificationContentModels2 == null || promotedNotificationContentModels2.privateVersion.style == PromotedNotificationContentModel.Style.Ineligible) {
                promotedNotificationContentModels2 = null;
            }
            if (promotedNotificationContentModels2 != null) {
                return promotedNotificationContentModels2;
            }
        }
        return null;
    }
}
