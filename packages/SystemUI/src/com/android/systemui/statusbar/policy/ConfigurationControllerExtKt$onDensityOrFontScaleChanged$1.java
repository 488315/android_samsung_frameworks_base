package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConfigurationController $this_onDensityOrFontScaleChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1(ConfigurationController configurationController, Continuation continuation) {
        super(2, continuation);
        this.$this_onDensityOrFontScaleChanged = configurationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1 configurationControllerExtKt$onDensityOrFontScaleChanged$1 = new ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1(this.$this_onDensityOrFontScaleChanged, continuation);
        configurationControllerExtKt$onDensityOrFontScaleChanged$1.L$0 = obj;
        return configurationControllerExtKt$onDensityOrFontScaleChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ConfigurationController.ConfigurationListener configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.policy.ConfigurationControllerExtKt$onDensityOrFontScaleChanged$1$listener$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public final void onDensityOrFontScaleChanged() {
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            ((ConfigurationControllerImpl) this.$this_onDensityOrFontScaleChanged).addCallback(configurationListener);
            ConfigurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0 configurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0 = new ConfigurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0(this.$this_onDensityOrFontScaleChanged, configurationListener, 1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, configurationControllerExtKt$onConfigChanged$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
