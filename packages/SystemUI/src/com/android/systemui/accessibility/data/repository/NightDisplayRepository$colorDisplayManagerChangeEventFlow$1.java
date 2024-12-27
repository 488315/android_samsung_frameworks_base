package com.android.systemui.accessibility.data.repository;

import android.hardware.display.NightDisplayListener;
import android.os.UserHandle;
import com.android.systemui.accessibility.data.model.NightDisplayChangeEvent;
import com.android.systemui.dagger.NightDisplayListenerModule$Builder;
import java.time.LocalTime;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final class NightDisplayRepository$colorDisplayManagerChangeEventFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NightDisplayRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NightDisplayRepository$colorDisplayManagerChangeEventFlow$1(NightDisplayRepository nightDisplayRepository, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nightDisplayRepository;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NightDisplayRepository$colorDisplayManagerChangeEventFlow$1 nightDisplayRepository$colorDisplayManagerChangeEventFlow$1 = new NightDisplayRepository$colorDisplayManagerChangeEventFlow$1(this.this$0, this.$user, continuation);
        nightDisplayRepository$colorDisplayManagerChangeEventFlow$1.L$0 = obj;
        return nightDisplayRepository$colorDisplayManagerChangeEventFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NightDisplayRepository$colorDisplayManagerChangeEventFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            NightDisplayListenerModule$Builder nightDisplayListenerModule$Builder = this.this$0.nightDisplayListenerBuilder;
            nightDisplayListenerModule$Builder.mUserId = this.$user.getIdentifier();
            final NightDisplayListener nightDisplayListener = new NightDisplayListener(nightDisplayListenerModule$Builder.mContext, nightDisplayListenerModule$Builder.mUserId, nightDisplayListenerModule$Builder.mBgHandler);
            nightDisplayListener.setCallback(new NightDisplayListener.Callback() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$colorDisplayManagerChangeEventFlow$1$nightDisplayCallback$1
                public final void onActivated(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new NightDisplayChangeEvent.OnActivatedChanged(z));
                }

                public final void onAutoModeChanged(int i2) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new NightDisplayChangeEvent.OnAutoModeChanged(i2));
                }

                public final void onCustomEndTimeChanged(LocalTime localTime) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new NightDisplayChangeEvent.OnCustomEndTimeChanged(localTime));
                }

                public final void onCustomStartTimeChanged(LocalTime localTime) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(new NightDisplayChangeEvent.OnCustomStartTimeChanged(localTime));
                }
            });
            Function0 function0 = new Function0() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$colorDisplayManagerChangeEventFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    nightDisplayListener.setCallback((NightDisplayListener.Callback) null);
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
