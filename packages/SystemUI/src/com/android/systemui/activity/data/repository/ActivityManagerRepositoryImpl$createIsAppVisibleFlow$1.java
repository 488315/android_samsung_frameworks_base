package com.android.systemui.activity.data.repository;

import android.app.ActivityManager;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $creationUid;
    final /* synthetic */ String $identifyingLogTag;
    final /* synthetic */ Logger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ActivityManagerRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1(ActivityManagerRepositoryImpl activityManagerRepositoryImpl, Logger logger, int i, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = activityManagerRepositoryImpl;
        this.$logger = logger;
        this.$creationUid = i;
        this.$identifyingLogTag = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1 activityManagerRepositoryImpl$createIsAppVisibleFlow$1 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1(this.this$0, this.$logger, this.$creationUid, this.$identifyingLogTag, continuation);
        activityManagerRepositoryImpl$createIsAppVisibleFlow$1.L$0 = obj;
        return activityManagerRepositoryImpl$createIsAppVisibleFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.app.ActivityManager$OnUidImportanceListener, com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$listener$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final int i2 = this.$creationUid;
            final ActivityManagerRepositoryImpl activityManagerRepositoryImpl = this.this$0;
            final Logger logger = this.$logger;
            final String str = this.$identifyingLogTag;
            final ?? r3 = new ActivityManager.OnUidImportanceListener() { // from class: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$listener$1
                public final void onUidImportance(int i3, int i4) {
                    if (i3 != i2) {
                        return;
                    }
                    ActivityManagerRepositoryImpl activityManagerRepositoryImpl2 = activityManagerRepositoryImpl;
                    int i5 = ActivityManagerRepositoryImpl.$r8$clinit;
                    activityManagerRepositoryImpl2.getClass();
                    boolean z = i4 <= 100;
                    Logger logger2 = logger;
                    ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(2);
                    String str2 = str;
                    LogMessage obtain = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.DEBUG, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, null);
                    obtain.setStr1(str2);
                    obtain.setInt1(i4);
                    obtain.setBool1(z);
                    logger2.getBuffer().commit(obtain);
                    ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            try {
                this.this$0.activityManager.addOnUidImportanceListener(r3, 100);
            } catch (SecurityException e) {
                Logger logger2 = this.$logger;
                ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(0);
                String str2 = this.$identifyingLogTag;
                LogMessage obtain = logger2.getBuffer().obtain(logger2.getTag(), LogLevel.ERROR, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, e);
                obtain.setStr1(str2);
                logger2.getBuffer().commit(obtain);
            }
            final ActivityManagerRepositoryImpl activityManagerRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ActivityManagerRepositoryImpl.this.activityManager.removeOnUidImportanceListener(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
