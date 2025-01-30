package com.android.systemui.shared.customization.data.content;

import android.database.ContentObserver;
import android.net.Uri;
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
@DebugMetadata(m276c = "com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeUri$1", m277f = "CustomizationProviderClient.kt", m278l = {518}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CustomizationProviderClientImpl$observeUri$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $uri;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomizationProviderClientImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProviderClientImpl$observeUri$1(CustomizationProviderClientImpl customizationProviderClientImpl, Uri uri, Continuation<? super CustomizationProviderClientImpl$observeUri$1> continuation) {
        super(2, continuation);
        this.this$0 = customizationProviderClientImpl;
        this.$uri = uri;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomizationProviderClientImpl$observeUri$1 customizationProviderClientImpl$observeUri$1 = new CustomizationProviderClientImpl$observeUri$1(this.this$0, this.$uri, continuation);
        customizationProviderClientImpl$observeUri$1.L$0 = obj;
        return customizationProviderClientImpl$observeUri$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomizationProviderClientImpl$observeUri$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeUri$1$observer$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeUri$1$observer$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.context.getContentResolver().registerContentObserver(this.$uri, true, r1);
            final CustomizationProviderClientImpl customizationProviderClientImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeUri$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CustomizationProviderClientImpl.this.context.getContentResolver().unregisterContentObserver(r1);
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
