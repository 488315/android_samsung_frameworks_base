package com.android.systemui.statusbar.policy;

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
/* loaded from: classes3.dex */
final class ConfigurationControllerExtKt$onThemeChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConfigurationController $this_onThemeChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationControllerExtKt$onThemeChanged$1(ConfigurationController configurationController, Continuation continuation) {
        super(2, continuation);
        this.$this_onThemeChanged = configurationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConfigurationControllerExtKt$onThemeChanged$1 configurationControllerExtKt$onThemeChanged$1 = new ConfigurationControllerExtKt$onThemeChanged$1(this.$this_onThemeChanged, continuation);
        configurationControllerExtKt$onThemeChanged$1.L$0 = obj;
        return configurationControllerExtKt$onThemeChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationControllerExtKt$onThemeChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onThemeChanged$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onThemeChanged$1$listener$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onThemeChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            ((ConfigurationControllerImpl) this.$this_onThemeChanged).addCallback(r1);
            final ConfigurationController configurationController = this.$this_onThemeChanged;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onThemeChanged$1.1
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
