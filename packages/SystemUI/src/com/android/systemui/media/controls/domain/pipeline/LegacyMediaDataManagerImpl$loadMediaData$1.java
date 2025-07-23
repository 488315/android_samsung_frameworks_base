package com.android.systemui.media.controls.domain.pipeline;

import android.service.notification.StatusBarNotification;
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
final class LegacyMediaDataManagerImpl$loadMediaData$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isConvertingToActive;
    final /* synthetic */ boolean $isNewlyActiveEntry;
    final /* synthetic */ String $key;
    final /* synthetic */ String $oldKey;
    final /* synthetic */ StatusBarNotification $sbn;
    int label;
    final /* synthetic */ LegacyMediaDataManagerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyMediaDataManagerImpl$loadMediaData$1(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, StatusBarNotification statusBarNotification, String str2, boolean z, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = legacyMediaDataManagerImpl;
        this.$key = str;
        this.$sbn = statusBarNotification;
        this.$oldKey = str2;
        this.$isNewlyActiveEntry = z;
        this.$isConvertingToActive = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LegacyMediaDataManagerImpl$loadMediaData$1(this.this$0, this.$key, this.$sbn, this.$oldKey, this.$isNewlyActiveEntry, this.$isConvertingToActive, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LegacyMediaDataManagerImpl$loadMediaData$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
            String str = this.$key;
            StatusBarNotification statusBarNotification = this.$sbn;
            String str2 = this.$oldKey;
            boolean z = this.$isNewlyActiveEntry;
            boolean z2 = this.$isConvertingToActive;
            this.label = 1;
            int i2 = LegacyMediaDataManagerImpl.MAX_COMPACT_ACTIONS;
            legacyMediaDataManagerImpl.getClass();
            Object withContext = BuildersKt.withContext(legacyMediaDataManagerImpl.backgroundDispatcher, new LegacyMediaDataManagerImpl$loadMediaDataWithLoader$2(legacyMediaDataManagerImpl, str, statusBarNotification, z2, z, str2, null), this);
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
