package com.android.systemui.statusbar.policy;

import android.content.res.Configuration;
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

final class ConfigurationControllerExtKt$onConfigChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConfigurationController $this_onConfigChanged;
    private /* synthetic */ Object L$0;
    int label;

    public ConfigurationControllerExtKt$onConfigChanged$1(ConfigurationController configurationController, Continuation continuation) {
        super(2, continuation);
        this.$this_onConfigChanged = configurationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConfigurationControllerExtKt$onConfigChanged$1 configurationControllerExtKt$onConfigChanged$1 = new ConfigurationControllerExtKt$onConfigChanged$1(this.$this_onConfigChanged, continuation);
        configurationControllerExtKt$onConfigChanged$1.L$0 = obj;
        return configurationControllerExtKt$onConfigChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationControllerExtKt$onConfigChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onConfigChanged$1$listener$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onConfigChanged(Configuration configuration) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(configuration);
                }
            };
            ((ConfigurationControllerImpl) this.$this_onConfigChanged).addCallback(r1);
            final ConfigurationController configurationController = this.$this_onConfigChanged;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onConfigChanged$1.1
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
