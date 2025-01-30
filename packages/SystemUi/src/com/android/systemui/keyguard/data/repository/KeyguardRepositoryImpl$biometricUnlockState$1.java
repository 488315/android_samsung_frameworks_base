package com.android.systemui.keyguard.data.repository;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.HashSet;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1", m277f = "KeyguardRepository.kt", m278l = {VolteConstants.ErrorCode.BUSY_HERE}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardRepositoryImpl$biometricUnlockState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BiometricUnlockController $biometricUnlockController;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$biometricUnlockState$1(BiometricUnlockController biometricUnlockController, KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$biometricUnlockState$1> continuation) {
        super(2, continuation);
        this.$biometricUnlockController = biometricUnlockController;
        this.this$0 = keyguardRepositoryImpl;
    }

    public static final void invokeSuspend$dispatchUpdate(ProducerScope producerScope, KeyguardRepositoryImpl keyguardRepositoryImpl, BiometricUnlockController biometricUnlockController) {
        BiometricUnlockModel biometricUnlockModel;
        ChannelExt channelExt = ChannelExt.INSTANCE;
        int i = biometricUnlockController.mMode;
        int i2 = KeyguardRepositoryImpl.$r8$clinit;
        keyguardRepositoryImpl.getClass();
        switch (i) {
            case 0:
                biometricUnlockModel = BiometricUnlockModel.NONE;
                break;
            case 1:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK;
                break;
            case 2:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK_PULSING;
                break;
            case 3:
                biometricUnlockModel = BiometricUnlockModel.SHOW_BOUNCER;
                break;
            case 4:
                biometricUnlockModel = BiometricUnlockModel.ONLY_WAKE;
                break;
            case 5:
                biometricUnlockModel = BiometricUnlockModel.UNLOCK_COLLAPSING;
                break;
            case 6:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK_FROM_DREAM;
                break;
            case 7:
                biometricUnlockModel = BiometricUnlockModel.DISMISS_BOUNCER;
                break;
            default:
                throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid BiometricUnlockModel value: ", i));
        }
        channelExt.getClass();
        ChannelExt.trySendWithFailureLogging(producerScope, biometricUnlockModel, "KeyguardRepositoryImpl", "biometric mode");
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardRepositoryImpl$biometricUnlockState$1 keyguardRepositoryImpl$biometricUnlockState$1 = new KeyguardRepositoryImpl$biometricUnlockState$1(this.$biometricUnlockController, this.this$0, continuation);
        keyguardRepositoryImpl$biometricUnlockState$1.L$0 = obj;
        return keyguardRepositoryImpl$biometricUnlockState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRepositoryImpl$biometricUnlockState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final BiometricUnlockController biometricUnlockController = this.$biometricUnlockController;
            final ?? r1 = new BiometricUnlockController.BiometricUnlockEventsListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1
                @Override // com.android.systemui.statusbar.phone.BiometricUnlockController.BiometricUnlockEventsListener
                public final void onModeChanged(int i2) {
                    KeyguardRepositoryImpl$biometricUnlockState$1.invokeSuspend$dispatchUpdate(ProducerScope.this, keyguardRepositoryImpl, biometricUnlockController);
                }

                @Override // com.android.systemui.statusbar.phone.BiometricUnlockController.BiometricUnlockEventsListener
                public final void onResetMode() {
                    KeyguardRepositoryImpl$biometricUnlockState$1.invokeSuspend$dispatchUpdate(ProducerScope.this, keyguardRepositoryImpl, biometricUnlockController);
                }
            };
            ((HashSet) this.$biometricUnlockController.mBiometricUnlockEventsListeners).add(r1);
            invokeSuspend$dispatchUpdate(producerScope, this.this$0, this.$biometricUnlockController);
            final BiometricUnlockController biometricUnlockController2 = this.$biometricUnlockController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BiometricUnlockController biometricUnlockController3 = BiometricUnlockController.this;
                    ((HashSet) biometricUnlockController3.mBiometricUnlockEventsListeners).remove(r1);
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
