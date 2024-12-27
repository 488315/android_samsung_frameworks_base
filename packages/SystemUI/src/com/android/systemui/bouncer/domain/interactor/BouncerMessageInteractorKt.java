package com.android.systemui.bouncer.domain.interactor;

import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.bouncer.shared.model.BouncerMessageStrings;
import com.android.systemui.bouncer.shared.model.Message;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class BouncerMessageInteractorKt {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Invalid.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.None.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SimPerso.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SmartcardPIN.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.AdminLock.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Permanent.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.FMM.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.RMM.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.KNOXGUARD.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierLock.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.SKTCarrierPassword.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Swipe.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[KeyguardSecurityModel.SecurityMode.ForgotPassword.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final BouncerMessageModel access$defaultMessage(KeyguardSecurityModel.SecurityMode securityMode, String str, boolean z) {
        BouncerMessageStrings bouncerMessageStrings = BouncerMessageStrings.INSTANCE;
        AuthenticationMethodModel authModel = toAuthModel(securityMode);
        bouncerMessageStrings.getClass();
        Message message = toMessage(BouncerMessageStrings.defaultMessage(authModel, z)).message;
        return new BouncerMessageModel(new Message(null, message != null ? message.messageResId : null, null, null, false, 13, null), new Message(str, null, null, null, false, 14, null));
    }

    public static final AuthenticationMethodModel toAuthModel(KeyguardSecurityModel.SecurityMode securityMode) {
        switch (WhenMappings.$EnumSwitchMapping$0[securityMode.ordinal()]) {
            case 1:
                return AuthenticationMethodModel.None.INSTANCE;
            case 2:
                return AuthenticationMethodModel.None.INSTANCE;
            case 3:
                return AuthenticationMethodModel.Pattern.INSTANCE;
            case 4:
                return AuthenticationMethodModel.Password.INSTANCE;
            case 5:
                return AuthenticationMethodModel.Pin.INSTANCE;
            case 6:
                return AuthenticationMethodModel.Sim.INSTANCE;
            case 7:
                return AuthenticationMethodModel.Sim.INSTANCE;
            case 8:
                return AuthenticationMethodModel.Sim.INSTANCE;
            case 9:
                return AuthenticationMethodModel.SmartcardPIN.INSTANCE;
            case 10:
                return AuthenticationMethodModel.AdminLock.INSTANCE;
            case 11:
                return AuthenticationMethodModel.Permanent.INSTANCE;
            case 12:
                return AuthenticationMethodModel.FMM.INSTANCE;
            case 13:
                return AuthenticationMethodModel.RMM.INSTANCE;
            case 14:
                return AuthenticationMethodModel.KNOXGUARD.INSTANCE;
            case 15:
                return AuthenticationMethodModel.SKTCarrierLock.INSTANCE;
            case 16:
                return AuthenticationMethodModel.SKTCarrierLock.INSTANCE;
            case 17:
                return AuthenticationMethodModel.Swipe.INSTANCE;
            case 18:
                return AuthenticationMethodModel.ForgotPassword.INSTANCE;
            default:
                throw new IllegalStateException("Invalid security mode!".toString());
        }
    }

    public static final BouncerMessageModel toMessage(Pair pair) {
        return new BouncerMessageModel(new Message(null, (Integer) pair.getFirst(), null, null, false, 13, null), new Message(null, (Integer) pair.getSecond(), null, null, false, 13, null));
    }
}
