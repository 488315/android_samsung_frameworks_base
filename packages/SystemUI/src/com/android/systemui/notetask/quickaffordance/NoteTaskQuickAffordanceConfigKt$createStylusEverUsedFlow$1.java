package com.android.systemui.notetask.quickaffordance;

import android.content.Context;
import android.hardware.input.InputSettings;
import com.android.systemui.stylus.StylusManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ StylusManager $this_createStylusEverUsedFlow;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1(Context context, StylusManager stylusManager, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$this_createStylusEverUsedFlow = stylusManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1 noteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1 = new NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1(this.$context, this.$this_createStylusEverUsedFlow, continuation);
        noteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1.L$0 = obj;
        return noteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ChannelsKt.trySendBlocking(producerScope, Boolean.valueOf(InputSettings.isStylusEverUsed(this.$context)));
            final Context context = this.$context;
            final ?? r1 = new StylusManager.StylusCallback() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1$callback$1
                @Override // com.android.systemui.stylus.StylusManager.StylusCallback
                public final void onStylusFirstUsed() {
                    ChannelsKt.trySendBlocking(ProducerScope.this, Boolean.valueOf(InputSettings.isStylusEverUsed(context)));
                }
            };
            this.$this_createStylusEverUsedFlow.stylusCallbacks.add(r1);
            final StylusManager stylusManager = this.$this_createStylusEverUsedFlow;
            Function0 function0 = new Function0() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    StylusManager stylusManager2 = StylusManager.this;
                    stylusManager2.stylusCallbacks.remove(r1);
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
