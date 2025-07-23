package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LegacyMediaDataManagerImpl$addResumptionControls$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Runnable $action;
    final /* synthetic */ PendingIntent $appIntent;
    final /* synthetic */ String $appName;
    final /* synthetic */ MediaDescription $desc;
    final /* synthetic */ String $packageName;
    final /* synthetic */ MediaSession.Token $token;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ LegacyMediaDataManagerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyMediaDataManagerImpl$addResumptionControls$1(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = legacyMediaDataManagerImpl;
        this.$userId = i;
        this.$desc = mediaDescription;
        this.$action = runnable;
        this.$token = token;
        this.$appName = str;
        this.$appIntent = pendingIntent;
        this.$packageName = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LegacyMediaDataManagerImpl$addResumptionControls$1(this.this$0, this.$userId, this.$desc, this.$action, this.$token, this.$appName, this.$appIntent, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LegacyMediaDataManagerImpl$addResumptionControls$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
            int i2 = this.$userId;
            MediaDescription mediaDescription = this.$desc;
            Runnable runnable = this.$action;
            MediaSession.Token token = this.$token;
            String str = this.$appName;
            PendingIntent pendingIntent = this.$appIntent;
            String str2 = this.$packageName;
            this.label = 1;
            int i3 = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
            legacyMediaDataManagerImpl.getClass();
            Object withContext = BuildersKt.withContext(legacyMediaDataManagerImpl.backgroundDispatcher, new LegacyMediaDataManagerImpl$loadMediaDataForResumption$2(legacyMediaDataManagerImpl, str2, i2, mediaDescription, runnable, token, str, pendingIntent, null), this);
            if (withContext != obj2) {
                withContext = Unit.INSTANCE;
            }
            if (withContext == obj2) {
                return obj2;
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
