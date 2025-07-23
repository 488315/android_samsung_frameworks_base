package com.android.systemui.statusbar.phone.ongoingcall;

import android.app.PendingIntent;
import com.android.internal.logging.InstanceId;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.notification.shared.CallType;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingCallController$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ OngoingCallController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallController$start$1(OngoingCallController ongoingCallController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ongoingCallController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new OngoingCallController$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OngoingCallController$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final OngoingCallController ongoingCallController = this.this$0;
            Flow flow = ongoingCallController.activeNotificationsInteractor.ongoingCallNotification;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) obj2;
                    int i2 = OngoingCallController.$r8$clinit;
                    OngoingCallController ongoingCallController2 = OngoingCallController.this;
                    ongoingCallController2.getClass();
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    int i3 = StatusBarChipsModernization.$r8$clinit;
                    LogBuffer logBuffer = ongoingCallController2.logger;
                    if (activeNotificationModel == null) {
                        logBuffer.commit(logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(2), null));
                        ongoingCallController2.removeChipInfo();
                    } else {
                        CallType callType = CallType.Ongoing;
                        CallType callType2 = activeNotificationModel.callType;
                        if (callType2 != callType) {
                            LogMessage obtain = logBuffer.obtain("OngoingCall", LogLevel.ERROR, new OngoingCallController$$ExternalSyntheticLambda2(3), null);
                            ((LogMessageImpl) obtain).str1 = callType2.name();
                            logBuffer.commit(obtain);
                            ongoingCallController2.removeChipInfo();
                        } else {
                            LogMessage obtain2 = logBuffer.obtain("OngoingCall", LogLevel.DEBUG, new OngoingCallController$$ExternalSyntheticLambda2(4), null);
                            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
                            logMessageImpl.str1 = activeNotificationModel.key;
                            logMessageImpl.long1 = activeNotificationModel.whenTime;
                            logMessageImpl.str1 = callType2.name();
                            logMessageImpl.bool1 = activeNotificationModel.statusBarChipIconView != null;
                            logBuffer.commit(obtain2);
                            PendingIntent pendingIntent = activeNotificationModel.contentIntent;
                            InstanceId instanceId = activeNotificationModel.instanceId;
                            ActiveNotificationsInteractor.Companion.getClass();
                            boolean isOngoingCallNotification = ActiveNotificationsInteractor.Companion.isOngoingCallNotification(activeNotificationModel);
                            OngoingCallController.CallNotificationInfo callNotificationInfo = ongoingCallController2.callNotificationInfo;
                            boolean z = callNotificationInfo != null ? callNotificationInfo.statusBarSwipedAway : false;
                            OngoingCallController.CallNotificationInfo callNotificationInfo2 = new OngoingCallController.CallNotificationInfo(activeNotificationModel.key, activeNotificationModel.whenTime, activeNotificationModel.statusBarChipIconView, pendingIntent, activeNotificationModel.uid, activeNotificationModel.appName, instanceId, activeNotificationModel.promotedContent, isOngoingCallNotification, z, activeNotificationModel.callChipColor, activeNotificationModel.extraVisibleFlag);
                            if (!callNotificationInfo2.equals(ongoingCallController2.callNotificationInfo)) {
                                ongoingCallController2.callNotificationInfo = callNotificationInfo2;
                                ongoingCallController2.updateChip();
                                if (ongoingCallController2.callNotificationInfo == null) {
                                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_CALL_CHIP_GENERATED);
                                }
                            }
                        }
                    }
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
