package com.android.systemui.statusbar.chips.notification.domain.interactor;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.chips.notification.domain.model.NotificationChipModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class StatusBarNotificationChipsInteractor$logSort$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StatusBarNotificationChipsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarNotificationChipsInteractor$logSort$1(StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarNotificationChipsInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarNotificationChipsInteractor$logSort$1 statusBarNotificationChipsInteractor$logSort$1 = new StatusBarNotificationChipsInteractor$logSort$1(this.this$0, continuation);
        statusBarNotificationChipsInteractor$logSort$1.L$0 = obj;
        return statusBarNotificationChipsInteractor$logSort$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarNotificationChipsInteractor$logSort$1) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final int i = 0;
        String joinToString$default = CollectionsKt___CollectionsKt.joinToString$default((List) this.L$0, null, null, null, new Function1() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$logSort$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                switch (i) {
                    case 0:
                        NotificationChipModel notificationChipModel = (NotificationChipModel) obj2;
                        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("{key=", notificationChipModel.key, ". lastVisibleAppTime=");
                        m.append(notificationChipModel.lastAppVisibleTime);
                        m.append(". creationTime=");
                        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(notificationChipModel.creationTime, "}", m);
                    default:
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Sorted notif chips: ", ((LogMessage) obj2).getStr1());
                }
            }
        }, 31);
        Logger logger = this.this$0.logger;
        final int i2 = 1;
        Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor$logSort$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                switch (i2) {
                    case 0:
                        NotificationChipModel notificationChipModel = (NotificationChipModel) obj2;
                        StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("{key=", notificationChipModel.key, ". lastVisibleAppTime=");
                        m.append(notificationChipModel.lastAppVisibleTime);
                        m.append(". creationTime=");
                        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(notificationChipModel.creationTime, "}", m);
                    default:
                        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Sorted notif chips: ", ((LogMessage) obj2).getStr1());
                }
            }
        };
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, function1, null);
        obtain.setStr1(joinToString$default);
        logger.getBuffer().commit(obtain);
        return Unit.INSTANCE;
    }
}
