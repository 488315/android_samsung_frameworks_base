package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import com.android.systemui.media.controls.shared.model.MediaData;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaDataLoader$loadMediaDataForResumption$mediaData$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PendingIntent $appIntent;
    final /* synthetic */ String $appName;
    final /* synthetic */ MediaData $currentEntry;
    final /* synthetic */ MediaDescription $desc;
    final /* synthetic */ String $packageName;
    final /* synthetic */ Runnable $resumeAction;
    final /* synthetic */ MediaSession.Token $token;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ MediaDataLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDataLoader$loadMediaDataForResumption$mediaData$1(MediaDataLoader mediaDataLoader, int i, MediaDescription mediaDescription, Runnable runnable, MediaData mediaData, MediaSession.Token token, String str, PendingIntent pendingIntent, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaDataLoader;
        this.$userId = i;
        this.$desc = mediaDescription;
        this.$resumeAction = runnable;
        this.$currentEntry = mediaData;
        this.$token = token;
        this.$appName = str;
        this.$appIntent = pendingIntent;
        this.$packageName = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaDataLoader$loadMediaDataForResumption$mediaData$1(this.this$0, this.$userId, this.$desc, this.$resumeAction, this.$currentEntry, this.$token, this.$appName, this.$appIntent, this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDataLoader$loadMediaDataForResumption$mediaData$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        MediaDataLoader mediaDataLoader = this.this$0;
        int i2 = this.$userId;
        MediaDescription mediaDescription = this.$desc;
        Runnable runnable = this.$resumeAction;
        MediaData mediaData = this.$currentEntry;
        MediaSession.Token token = this.$token;
        String str = this.$appName;
        PendingIntent pendingIntent = this.$appIntent;
        String str2 = this.$packageName;
        this.label = 1;
        Object objAccess$loadMediaDataForResumptionInBackground = MediaDataLoader.access$loadMediaDataForResumptionInBackground(mediaDataLoader, i2, mediaDescription, runnable, mediaData, token, str, pendingIntent, str2, this);
        return objAccess$loadMediaDataForResumptionInBackground == coroutineSingletons ? coroutineSingletons : objAccess$loadMediaDataForResumptionInBackground;
    }
}
