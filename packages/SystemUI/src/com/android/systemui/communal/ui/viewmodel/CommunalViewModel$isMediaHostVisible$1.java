package com.android.systemui.communal.ui.viewmodel;

import com.android.systemui.media.controls.ui.view.MediaHost;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class CommunalViewModel$isMediaHostVisible$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaHost $mediaHost;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalViewModel$isMediaHostVisible$1(MediaHost mediaHost, Continuation continuation) {
        super(2, continuation);
        this.$mediaHost = mediaHost;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CommunalViewModel$isMediaHostVisible$1 communalViewModel$isMediaHostVisible$1 = new CommunalViewModel$isMediaHostVisible$1(this.$mediaHost, continuation);
        communalViewModel$isMediaHostVisible$1.L$0 = obj;
        return communalViewModel$isMediaHostVisible$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalViewModel$isMediaHostVisible$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.communal.ui.viewmodel.CommunalViewModel$isMediaHostVisible$1$$ExternalSyntheticLambda0, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new Function1() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$isMediaHostVisible$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    Boolean bool = (Boolean) obj2;
                    bool.booleanValue();
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(bool);
                    return Unit.INSTANCE;
                }
            };
            this.$mediaHost.visibleChangedListeners.add(r1);
            final MediaHost mediaHost = this.$mediaHost;
            Function0 function0 = new Function0() { // from class: com.android.systemui.communal.ui.viewmodel.CommunalViewModel$isMediaHostVisible$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    mediaHost.visibleChangedListeners.remove(r1);
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
