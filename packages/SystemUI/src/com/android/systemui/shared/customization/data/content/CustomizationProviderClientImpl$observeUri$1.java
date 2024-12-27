package com.android.systemui.shared.customization.data.content;

import android.database.ContentObserver;
import android.net.Uri;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class CustomizationProviderClientImpl$observeUri$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $uri;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomizationProviderClientImpl this$0;

    public CustomizationProviderClientImpl$observeUri$1(CustomizationProviderClientImpl customizationProviderClientImpl, Uri uri, Continuation continuation) {
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
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.context.getContentResolver().registerContentObserver(this.$uri, true, r1);
            final CustomizationProviderClientImpl customizationProviderClientImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeUri$1.1
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
