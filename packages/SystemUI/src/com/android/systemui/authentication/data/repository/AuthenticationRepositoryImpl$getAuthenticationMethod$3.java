package com.android.systemui.authentication.data.repository;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AuthenticationRepositoryImpl$getAuthenticationMethod$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Swipe.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.ForgotPassword.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$getAuthenticationMethod$3(AuthenticationRepositoryImpl authenticationRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$getAuthenticationMethod$3(this.this$0, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$getAuthenticationMethod$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        switch (WhenMappings.$EnumSwitchMapping$0[((KeyguardSecurityModel.SecurityMode) this.this$0.getSecurityMode.apply(new Integer(this.$userId))).ordinal()]) {
            case 1:
                return AuthenticationMethodModel.Pin.INSTANCE;
            case 2:
            case 3:
            case 4:
                return AuthenticationMethodModel.Sim.INSTANCE;
            case 5:
                return AuthenticationMethodModel.Password.INSTANCE;
            case 6:
                return AuthenticationMethodModel.Pattern.INSTANCE;
            case 7:
                return AuthenticationMethodModel.None.INSTANCE;
            case 8:
                return AuthenticationMethodModel.SmartcardPIN.INSTANCE;
            case 9:
                return AuthenticationMethodModel.AdminLock.INSTANCE;
            case 10:
                return AuthenticationMethodModel.Permanent.INSTANCE;
            case 11:
                return AuthenticationMethodModel.FMM.INSTANCE;
            case 12:
                return AuthenticationMethodModel.RMM.INSTANCE;
            case 13:
                return AuthenticationMethodModel.KNOXGUARD.INSTANCE;
            case 14:
            case 15:
                return AuthenticationMethodModel.SKTCarrierLock.INSTANCE;
            case 16:
                return AuthenticationMethodModel.Swipe.INSTANCE;
            case 17:
                return AuthenticationMethodModel.ForgotPassword.INSTANCE;
            case 18:
                throw new IllegalStateException("Invalid security mode!".toString());
            default:
                throw new IllegalStateException("Invalid security mode!".toString());
        }
    }
}
