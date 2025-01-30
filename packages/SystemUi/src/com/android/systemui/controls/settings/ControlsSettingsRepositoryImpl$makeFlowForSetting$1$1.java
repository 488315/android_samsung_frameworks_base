package com.android.systemui.controls.settings;

import android.content.pm.UserInfo;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.util.settings.SecureSettings;
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
@DebugMetadata(m276c = "com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1", m277f = "ControlsSettingsRepositoryImpl.kt", m278l = {77}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $setting;
    final /* synthetic */ UserInfo $userInfo;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ControlsSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(ControlsSettingsRepositoryImpl controlsSettingsRepositoryImpl, UserInfo userInfo, String str, Continuation<? super ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1> continuation) {
        super(2, continuation);
        this.this$0 = controlsSettingsRepositoryImpl;
        this.$userInfo = userInfo;
        this.$setting = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1 controlsSettingsRepositoryImpl$makeFlowForSetting$1$1 = new ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(this.this$0, this.$userInfo, this.$setting, continuation);
        controlsSettingsRepositoryImpl$makeFlowForSetting$1$1.L$0 = obj;
        return controlsSettingsRepositoryImpl$makeFlowForSetting$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1, com.android.systemui.qs.SettingObserver] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SecureSettings secureSettings = this.this$0.secureSettings;
            final int i2 = this.$userInfo.id;
            final String str = this.$setting;
            final ?? r4 = new SettingObserver(str, secureSettings, i2) { // from class: com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1
                @Override // com.android.systemui.p016qs.SettingObserver
                public final void handleValueChanged(int i3, boolean z) {
                    ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(Boolean.valueOf(i3 == 1));
                }
            };
            r4.setListening(true);
            ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(Boolean.valueOf(r4.getValue() == 1));
            Function0 function0 = new Function0() { // from class: com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    setListening(false);
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
