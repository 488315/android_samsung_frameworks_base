package com.android.systemui.statusbar.chips.notification.domain.interactor;

import com.android.internal.logging.InstanceId;
import com.android.systemui.activity.data.model.AppVisibilityModel;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModels;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
final class SingleNotificationChipInteractor$notificationChip$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SingleNotificationChipInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SingleNotificationChipInteractor$notificationChip$1(SingleNotificationChipInteractor singleNotificationChipInteractor, Continuation continuation) {
        super(3, continuation);
        this.this$0 = singleNotificationChipInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SingleNotificationChipInteractor$notificationChip$1 singleNotificationChipInteractor$notificationChip$1 = new SingleNotificationChipInteractor$notificationChip$1(this.this$0, (Continuation) obj3);
        singleNotificationChipInteractor$notificationChip$1.L$0 = (ActiveNotificationModel) obj;
        singleNotificationChipInteractor$notificationChip$1.L$1 = (AppVisibilityModel) obj2;
        return singleNotificationChipInteractor$notificationChip$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) this.L$0;
        AppVisibilityModel appVisibilityModel = (AppVisibilityModel) this.L$1;
        SingleNotificationChipInteractor singleNotificationChipInteractor = this.this$0;
        singleNotificationChipInteractor.getClass();
        PromotedNotificationContentModels promotedNotificationContentModels = activeNotificationModel.promotedContent;
        Logger logger = singleNotificationChipInteractor.logger;
        String str = singleNotificationChipInteractor.extraLogTag;
        if (promotedNotificationContentModels == null) {
            SingleNotificationChipInteractor$$ExternalSyntheticLambda0 singleNotificationChipInteractor$$ExternalSyntheticLambda0 = new SingleNotificationChipInteractor$$ExternalSyntheticLambda0(1);
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, singleNotificationChipInteractor$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(str);
            logger.getBuffer().commit(logMessageObtain);
            return null;
        }
        StatusBarIconView statusBarIconView = activeNotificationModel.statusBarChipIconView;
        if (statusBarIconView != null) {
            boolean z = appVisibilityModel.isAppCurrentlyVisible;
            InstanceId instanceId = activeNotificationModel.instanceId;
            return new NotificationChipModel(activeNotificationModel.key, activeNotificationModel.appName, statusBarIconView, promotedNotificationContentModels, singleNotificationChipInteractor.creationTime, z, appVisibilityModel.lastAppVisibleTime, instanceId);
        }
        SingleNotificationChipInteractor$$ExternalSyntheticLambda0 singleNotificationChipInteractor$$ExternalSyntheticLambda02 = new SingleNotificationChipInteractor$$ExternalSyntheticLambda0(2);
        LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, singleNotificationChipInteractor$$ExternalSyntheticLambda02, null);
        logMessageObtain2.setStr1(str);
        logger.getBuffer().commit(logMessageObtain2);
        return null;
    }
}
