package com.android.systemui.media.controls.domain.pipeline;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataLoader;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.util.Assert;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaDataProcessor$loadMediaDataWithLoader$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isConvertingToActive;
    final /* synthetic */ boolean $isNewlyActiveEntry;
    final /* synthetic */ String $key;
    final /* synthetic */ String $oldKey;
    final /* synthetic */ StatusBarNotification $sbn;
    long J$0;
    int label;
    final /* synthetic */ MediaDataProcessor this$0;

    /* renamed from: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadMediaDataWithLoader$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $key;
        final /* synthetic */ MediaData $mediaData;
        final /* synthetic */ String $oldKey;
        int label;
        final /* synthetic */ MediaDataProcessor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MediaDataProcessor mediaDataProcessor, String str, String str2, MediaData mediaData, Continuation continuation) {
            super(2, continuation);
            this.this$0 = mediaDataProcessor;
            this.$key = str;
            this.$oldKey = str2;
            this.$mediaData = mediaData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$key, this.$oldKey, this.$mediaData, continuation);
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
            MediaDataProcessor mediaDataProcessor = this.this$0;
            String str = this.$key;
            String str2 = this.$oldKey;
            MediaData mediaData = this.$mediaData;
            MediaDataRepository mediaDataRepository = mediaDataProcessor.mediaDataRepository;
            boolean zIsEnabled = Trace.isEnabled();
            if (zIsEnabled) {
                TraceUtilsKt.beginSlice("MediaDataProcessor#onMediaDataLoaded");
            }
            try {
                Assert.isMainThread();
                if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).containsKey(str)) {
                    mediaDataRepository.addMediaEntry(mediaData, str);
                    mediaDataProcessor.notifyMediaDataLoaded(str, str2, mediaData);
                }
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return Unit.INSTANCE;
            } catch (Throwable th) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
                throw th;
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDataProcessor$loadMediaDataWithLoader$2(MediaDataProcessor mediaDataProcessor, String str, StatusBarNotification statusBarNotification, boolean z, boolean z2, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaDataProcessor;
        this.$key = str;
        this.$sbn = statusBarNotification;
        this.$isConvertingToActive = z;
        this.$isNewlyActiveEntry = z2;
        this.$oldKey = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaDataProcessor$loadMediaDataWithLoader$2(this.this$0, this.$key, this.$sbn, this.$isConvertingToActive, this.$isNewlyActiveEntry, this.$oldKey, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDataProcessor$loadMediaDataWithLoader$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x01b0, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r2, r7, r38) == r1) goto L62;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        long jElapsedRealtime;
        Object objLoadMediaData;
        InstanceId instanceIdNewInstanceId;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            jElapsedRealtime = this.this$0.systemClock.elapsedRealtime();
            MediaDataLoader mediaDataLoader = (MediaDataLoader) this.this$0.mediaDataLoader.get();
            String str = this.$key;
            StatusBarNotification statusBarNotification = this.$sbn;
            boolean z = this.$isConvertingToActive;
            this.J$0 = jElapsedRealtime;
            this.label = 1;
            objLoadMediaData = mediaDataLoader.loadMediaData(str, statusBarNotification, z, this);
            if (objLoadMediaData != coroutineSingletons) {
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
        jElapsedRealtime = this.J$0;
        ResultKt.throwOnFailure(obj);
        objLoadMediaData = obj;
        long j = jElapsedRealtime;
        MediaDataLoader.MediaDataLoaderResult mediaDataLoaderResult = (MediaDataLoader.MediaDataLoaderResult) objLoadMediaData;
        if (mediaDataLoaderResult == null) {
            Log.d("MediaDataProcessor", "No result from loadMediaData");
            return Unit.INSTANCE;
        }
        MediaControllerFactory mediaControllerFactory = this.this$0.mediaControllerFactory;
        MediaSession.Token token = mediaDataLoaderResult.token;
        token.getClass();
        MediaController mediaControllerCreate = mediaControllerFactory.create(token);
        MediaData mediaData = (MediaData) ((Map) this.this$0.mediaDataRepository.mediaEntries.$$delegate_0.getValue()).get(this.$key);
        if (mediaData == null || (instanceIdNewInstanceId = mediaData.instanceId) == null) {
            instanceIdNewInstanceId = this.this$0.logger.instanceIdSequence.newInstanceId();
        }
        InstanceId instanceId = instanceIdNewInstanceId;
        MediaData mediaData2 = new MediaData(this.$sbn.getNormalizedUserId(), true, mediaDataLoaderResult.appName, mediaDataLoaderResult.appIcon, mediaDataLoaderResult.artist, mediaDataLoaderResult.song, mediaDataLoaderResult.artworkIcon, mediaDataLoaderResult.actionIcons, mediaDataLoaderResult.actionsToShowInCompact, mediaDataLoaderResult.semanticActions, this.$sbn.getPackageName(), mediaDataLoaderResult.token, mediaDataLoaderResult.clickIntent, mediaDataLoaderResult.device, mediaData != null ? mediaData.active : true, mediaData != null ? mediaData.resumeAction : null, mediaDataLoaderResult.playbackLocation, false, this.$key, mediaData != null && mediaData.hasCheckedForResume, mediaDataLoaderResult.isPlaying, !this.$sbn.isOngoing(), j, mediaData != null ? mediaData.createdTimestampMillis : 0L, instanceId, mediaDataLoaderResult.appUid, mediaDataLoaderResult.isExplicit, null, 134348800, null);
        if (MediaProcessingHelperKt.isSameMediaData(this.this$0.context, mediaControllerCreate, mediaData2, mediaData)) {
            this.this$0.mediaLogger.logDuplicateMediaNotification(this.$key);
            return Unit.INSTANCE;
        }
        boolean z2 = this.$isNewlyActiveEntry;
        int i2 = mediaDataLoaderResult.playbackLocation;
        int i3 = mediaDataLoaderResult.appUid;
        if (z2) {
            MediaDataProcessor mediaDataProcessor = this.this$0;
            String packageName = this.$sbn.getPackageName();
            MediaDataRepository mediaDataRepository = mediaDataProcessor.mediaDataRepository;
            int size = ((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).size();
            MediaUiEventLogger mediaUiEventLogger = mediaDataProcessor.logger;
            if (size == 1) {
                mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_SINGLE_PLAYER, i3, packageName, instanceId);
            } else if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).size() == 2) {
                mediaUiEventLogger.logger.logWithInstanceId(MediaUiEvent.MEDIA_CAROUSEL_MULTIPLE_PLAYERS, i3, packageName, instanceId);
            }
            this.this$0.logger.logActiveMediaAdded(i3, i2, instanceId, this.$sbn.getPackageName());
        } else if (mediaData == null || i2 != mediaData.playbackLocation) {
            this.this$0.logger.logPlaybackLocationChange(i3, i2, instanceId, this.$sbn.getPackageName());
        }
        MediaDataProcessor mediaDataProcessor2 = this.this$0;
        CoroutineDispatcher coroutineDispatcher = mediaDataProcessor2.mainDispatcher;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(mediaDataProcessor2, this.$key, this.$oldKey, mediaData2, null);
        this.label = 2;
    }
}
