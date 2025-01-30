package com.android.systemui.p016qs.pipeline.data.repository;

import android.database.ContentObserver;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$1", m277f = "TileSpecRepository.kt", m278l = {142}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class TileSpecSettingsRepository$settingsTiles$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TileSpecSettingsRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileSpecSettingsRepository$settingsTiles$1(TileSpecSettingsRepository tileSpecSettingsRepository, int i, Continuation<? super TileSpecSettingsRepository$settingsTiles$1> continuation) {
        super(2, continuation);
        this.this$0 = tileSpecSettingsRepository;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TileSpecSettingsRepository$settingsTiles$1 tileSpecSettingsRepository$settingsTiles$1 = new TileSpecSettingsRepository$settingsTiles$1(this.this$0, this.$userId, continuation);
        tileSpecSettingsRepository$settingsTiles$1.L$0 = obj;
        return tileSpecSettingsRepository$settingsTiles$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileSpecSettingsRepository$settingsTiles$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$1$observer$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$1$observer$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.secureSettings.registerContentObserverForUser("sysui_qs_tiles", r1, this.$userId);
            final TileSpecSettingsRepository tileSpecSettingsRepository = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$settingsTiles$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    TileSpecSettingsRepository.this.secureSettings.unregisterContentObserver(r1);
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
