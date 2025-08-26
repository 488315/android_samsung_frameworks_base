package com.android.settingslib.volume.data.repository;

import android.database.ContentObserver;
import android.net.Uri;
import androidx.concurrent.futures.DirectExecutor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class AudioRepositoryImpl$volumeSettingChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $uri;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$volumeSettingChanges$1(AudioRepositoryImpl audioRepositoryImpl, Uri uri, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioRepositoryImpl$volumeSettingChanges$1 audioRepositoryImpl$volumeSettingChanges$1 = new AudioRepositoryImpl$volumeSettingChanges$1(this.this$0, this.$uri, continuation);
        audioRepositoryImpl$volumeSettingChanges$1.L$0 = obj;
        return audioRepositoryImpl$volumeSettingChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$volumeSettingChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final DirectExecutor directExecutor = DirectExecutor.INSTANCE;
            ContentObserver contentObserver = new ContentObserver(directExecutor) { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$volumeSettingChanges$1$observer$1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ProducerScope producerScope2 = producerScope;
                    BuildersKt.launch$default(producerScope2, null, null, new AudioRepositoryImpl$volumeSettingChanges$1$observer$1$onChange$1(producerScope2, null), 3);
                }
            };
            this.this$0.contentResolver.registerContentObserver(this.$uri, false, contentObserver);
            AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0 audioRepositoryImpl$mode$1$$ExternalSyntheticLambda0 = new AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0(this.this$0, contentObserver, 2);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, audioRepositoryImpl$mode$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
