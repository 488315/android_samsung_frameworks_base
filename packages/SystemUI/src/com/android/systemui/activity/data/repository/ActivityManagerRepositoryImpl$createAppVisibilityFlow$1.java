package com.android.systemui.activity.data.repository;

import com.android.systemui.activity.data.model.AppVisibilityModel;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ActivityManagerRepositoryImpl$createAppVisibilityFlow$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ String $identifyingLogTag;
    final /* synthetic */ Logger $logger;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ ActivityManagerRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityManagerRepositoryImpl$createAppVisibilityFlow$1(ActivityManagerRepositoryImpl activityManagerRepositoryImpl, Logger logger, String str, Continuation continuation) {
        super(3, continuation);
        this.this$0 = activityManagerRepositoryImpl;
        this.$logger = logger;
        this.$identifyingLogTag = str;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        ActivityManagerRepositoryImpl$createAppVisibilityFlow$1 activityManagerRepositoryImpl$createAppVisibilityFlow$1 = new ActivityManagerRepositoryImpl$createAppVisibilityFlow$1(this.this$0, this.$logger, this.$identifyingLogTag, (Continuation) obj3);
        activityManagerRepositoryImpl$createAppVisibilityFlow$1.L$0 = (AppVisibilityModel) obj;
        activityManagerRepositoryImpl$createAppVisibilityFlow$1.Z$0 = booleanValue;
        return activityManagerRepositoryImpl$createAppVisibilityFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AppVisibilityModel appVisibilityModel = (AppVisibilityModel) this.L$0;
        if (!this.Z$0) {
            Long l = appVisibilityModel.lastAppVisibleTime;
            appVisibilityModel.getClass();
            return new AppVisibilityModel(false, l);
        }
        long currentTimeMillis = this.this$0.systemClock.currentTimeMillis();
        Logger logger = this.$logger;
        ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0 = new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0(1);
        String str = this.$identifyingLogTag;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.DEBUG, activityManagerRepositoryImpl$createIsAppVisibleFlow$1$$ExternalSyntheticLambda0, null);
        obtain.setStr1(str);
        obtain.setLong1(currentTimeMillis);
        logger.getBuffer().commit(obtain);
        return new AppVisibilityModel(true, new Long(currentTimeMillis));
    }
}
