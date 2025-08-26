package com.android.systemui.media.controls.domain.pipeline;

import android.app.PendingIntent;
import android.graphics.drawable.Icon;
import android.media.MediaDescription;
import android.media.session.MediaSession;
import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.domain.pipeline.MediaDataLoader;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class LegacyMediaDataManagerImpl$loadMediaDataForResumption$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ PendingIntent $appIntent;
    final /* synthetic */ String $appName;
    final /* synthetic */ MediaDescription $desc;
    final /* synthetic */ String $packageName;
    final /* synthetic */ Runnable $resumeAction;
    final /* synthetic */ MediaSession.Token $token;
    final /* synthetic */ int $userId;
    long J$0;
    long J$1;
    Object L$0;
    int label;
    final /* synthetic */ LegacyMediaDataManagerImpl this$0;

    /* renamed from: com.android.systemui.media.controls.domain.pipeline.LegacyMediaDataManagerImpl$loadMediaDataForResumption$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ long $createdTimestampMillis;
        final /* synthetic */ InstanceId $instanceId;
        final /* synthetic */ long $lastActive;
        final /* synthetic */ String $packageName;
        final /* synthetic */ MediaDataLoader.MediaDataLoaderResult $result;
        final /* synthetic */ Runnable $resumeAction;
        final /* synthetic */ int $userId;
        int label;
        final /* synthetic */ LegacyMediaDataManagerImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, int i, MediaDataLoader.MediaDataLoaderResult mediaDataLoaderResult, Runnable runnable, long j, long j2, InstanceId instanceId, Continuation continuation) {
            super(2, continuation);
            this.this$0 = legacyMediaDataManagerImpl;
            this.$packageName = str;
            this.$userId = i;
            this.$result = mediaDataLoaderResult;
            this.$resumeAction = runnable;
            this.$lastActive = j;
            this.$createdTimestampMillis = j2;
            this.$instanceId = instanceId;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$packageName, this.$userId, this.$result, this.$resumeAction, this.$lastActive, this.$createdTimestampMillis, this.$instanceId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
            String str = this.$packageName;
            int i = this.$userId;
            MediaDataLoader.MediaDataLoaderResult mediaDataLoaderResult = this.$result;
            String str2 = mediaDataLoaderResult.appName;
            CharSequence charSequence = mediaDataLoaderResult.artist;
            CharSequence charSequence2 = mediaDataLoaderResult.song;
            Icon icon = mediaDataLoaderResult.artworkIcon;
            List list = mediaDataLoaderResult.actionIcons;
            List list2 = mediaDataLoaderResult.actionsToShowInCompact;
            MediaButton mediaButton = mediaDataLoaderResult.semanticActions;
            String str3 = this.$packageName;
            legacyMediaDataManagerImpl.onMediaDataLoaded(str, null, new MediaData(i, true, str2, null, charSequence, charSequence2, icon, list, list2, mediaButton, str3, mediaDataLoaderResult.token, mediaDataLoaderResult.clickIntent, mediaDataLoaderResult.device, false, this.$resumeAction, 0, true, str3, true, null, false, this.$lastActive, this.$createdTimestampMillis, this.$instanceId, mediaDataLoaderResult.appUid, mediaDataLoaderResult.isExplicit, mediaDataLoaderResult.resumeProgress, 3211264, null));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LegacyMediaDataManagerImpl$loadMediaDataForResumption$2(LegacyMediaDataManagerImpl legacyMediaDataManagerImpl, String str, int i, MediaDescription mediaDescription, Runnable runnable, MediaSession.Token token, String str2, PendingIntent pendingIntent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = legacyMediaDataManagerImpl;
        this.$packageName = str;
        this.$userId = i;
        this.$desc = mediaDescription;
        this.$resumeAction = runnable;
        this.$token = token;
        this.$appName = str2;
        this.$appIntent = pendingIntent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LegacyMediaDataManagerImpl$loadMediaDataForResumption$2(this.this$0, this.$packageName, this.$userId, this.$desc, this.$resumeAction, this.$token, this.$appName, this.$appIntent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LegacyMediaDataManagerImpl$loadMediaDataForResumption$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d5, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r2, r5, r20) == r1) goto L34;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long jElapsedRealtime;
        Object objAwaitInternal;
        MediaData mediaData;
        long j;
        CharSequence title;
        InstanceId instanceIdNewInstanceId;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            jElapsedRealtime = this.this$0.systemClock.elapsedRealtime();
            MediaData mediaData2 = (MediaData) this.this$0.mediaEntries.get(this.$packageName);
            long j2 = mediaData2 != null ? mediaData2.createdTimestampMillis : 0L;
            MediaDataLoader mediaDataLoader = (MediaDataLoader) this.this$0.mediaDataLoader.get();
            int i2 = this.$userId;
            MediaDescription mediaDescription = this.$desc;
            Runnable runnable = this.$resumeAction;
            MediaSession.Token token = this.$token;
            String str = this.$appName;
            PendingIntent pendingIntent = this.$appIntent;
            String str2 = this.$packageName;
            this.L$0 = mediaData2;
            this.J$0 = jElapsedRealtime;
            this.J$1 = j2;
            this.label = 1;
            mediaDataLoader.getClass();
            long j3 = j2;
            objAwaitInternal = CoroutineTracingKt.asyncTraced$default(mediaDataLoader.backgroundScope, null, null, new MediaDataLoader$loadMediaDataForResumption$mediaData$1(mediaDataLoader, i2, mediaDescription, runnable, mediaData2, token, str, pendingIntent, str2, null), 7).awaitInternal(this);
            if (objAwaitInternal != coroutineSingletons) {
                mediaData = mediaData2;
                j = j3;
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        long j4 = this.J$1;
        jElapsedRealtime = this.J$0;
        mediaData = (MediaData) this.L$0;
        ResultKt.throwOnFailure(obj);
        objAwaitInternal = obj;
        j = j4;
        long j5 = jElapsedRealtime;
        MediaDataLoader.MediaDataLoaderResult mediaDataLoaderResult = (MediaDataLoader.MediaDataLoaderResult) objAwaitInternal;
        if (mediaDataLoaderResult == null || (title = this.$desc.getTitle()) == null || StringsKt__StringsKt.isBlank(title)) {
            Log.d("MediaDataManager", "No MediaData result for resumption");
            this.this$0.mediaEntries.remove(this.$packageName);
            return Unit.INSTANCE;
        }
        if (mediaData == null || (instanceIdNewInstanceId = mediaData.instanceId) == null) {
            instanceIdNewInstanceId = this.this$0.logger.instanceIdSequence.newInstanceId();
        }
        InstanceId instanceId = instanceIdNewInstanceId;
        LegacyMediaDataManagerImpl legacyMediaDataManagerImpl = this.this$0;
        CoroutineDispatcher coroutineDispatcher = legacyMediaDataManagerImpl.mainDispatcher;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(legacyMediaDataManagerImpl, this.$packageName, this.$userId, mediaDataLoaderResult, this.$resumeAction, j5, j, instanceId, null);
        this.L$0 = null;
        this.label = 2;
    }
}
