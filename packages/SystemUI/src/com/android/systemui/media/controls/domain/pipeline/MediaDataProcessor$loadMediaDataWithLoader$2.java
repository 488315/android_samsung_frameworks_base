package com.android.systemui.media.controls.domain.pipeline;

import android.os.Trace;
import android.service.notification.StatusBarNotification;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.data.repository.MediaDataRepository;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.util.Assert;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
                TraceUtilsKt.beginSlice("MediaDataProcessor#onMediaDataLoaded");
            }
            try {
                Assert.isMainThread();
                if (((Map) mediaDataRepository.mediaEntries.$$delegate_0.getValue()).containsKey(str)) {
                    mediaDataRepository.addMediaEntry(mediaData, str);
                    mediaDataProcessor.notifyMediaDataLoaded(str, str2, mediaData);
                }
                Unit unit = Unit.INSTANCE;
                if (isEnabled) {
                    TraceUtilsKt.endSlice();
                }
                return Unit.INSTANCE;
            } catch (Throwable th) {
                if (isEnabled) {
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

    /* JADX WARN: Code restructure failed: missing block: B:46:0x01b0, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r2, r7, r38) == r1) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01b2, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0048, code lost:
    
        if (r2 == r1) goto L62;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r39) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor$loadMediaDataWithLoader$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
