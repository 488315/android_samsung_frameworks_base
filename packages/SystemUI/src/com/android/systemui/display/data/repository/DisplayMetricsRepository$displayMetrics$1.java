package com.android.systemui.display.data.repository;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
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
final class DisplayMetricsRepository$displayMetrics$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConfigurationController $configurationController;
    final /* synthetic */ Context $context;
    final /* synthetic */ DisplayMetrics $displayMetricsHolder;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisplayMetricsRepository$displayMetrics$1(ConfigurationController configurationController, Context context, DisplayMetrics displayMetrics, Continuation continuation) {
        super(2, continuation);
        this.$configurationController = configurationController;
        this.$context = context;
        this.$displayMetricsHolder = displayMetrics;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisplayMetricsRepository$displayMetrics$1 displayMetricsRepository$displayMetrics$1 = new DisplayMetricsRepository$displayMetrics$1(this.$configurationController, this.$context, this.$displayMetricsHolder, continuation);
        displayMetricsRepository$displayMetrics$1.L$0 = obj;
        return displayMetricsRepository$displayMetrics$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisplayMetricsRepository$displayMetrics$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.display.data.repository.DisplayMetricsRepository$displayMetrics$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Context context = this.$context;
            final DisplayMetrics displayMetrics = this.$displayMetricsHolder;
            final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.display.data.repository.DisplayMetricsRepository$displayMetrics$1$callback$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onConfigChanged(Configuration configuration) {
                    Display display = context.getDisplay();
                    if (display != null) {
                        display.getMetrics(displayMetrics);
                    }
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(displayMetrics);
                }
            };
            ((ConfigurationControllerImpl) this.$configurationController).addCallback(r1);
            final ConfigurationController configurationController = this.$configurationController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.display.data.repository.DisplayMetricsRepository$displayMetrics$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((ConfigurationControllerImpl) ConfigurationController.this).removeCallback(r1);
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
