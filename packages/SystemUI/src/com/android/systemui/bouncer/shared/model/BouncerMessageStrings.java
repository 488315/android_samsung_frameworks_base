package com.android.systemui.bouncer.shared.model;

import android.os.Bundle;
import com.android.systemui.CscRune;
import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class BouncerMessageStrings {
    public static final BouncerMessageStrings INSTANCE = new BouncerMessageStrings();
    public static final Pair EmptyMessage = new Pair(0, 0);

    private BouncerMessageStrings() {
    }

    public static Pair authRequiredAfterAdaptiveAuthRequest(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_prompt_after_adaptive_auth_lock);
        if (zAreEqual) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pattern_or_fp : R.string.kg_none_pattern_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_password_or_fp : R.string.kg_none_password_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pin_or_fp : R.string.kg_none_pin_none_instructions), numValueOf);
        }
        return EmptyMessage;
    }

    public static Pair authRequiredAfterAdminLockdown(AuthenticationMethodModel authenticationMethodModel) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_prompt_after_dpm_lock);
        return zAreEqual ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), numValueOf) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), numValueOf) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), numValueOf) : EmptyMessage;
    }

    public static Pair authRequiredAfterPrimaryAuthTimeout(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_prompt_pattern_auth_timeout)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_prompt_password_auth_timeout)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_prompt_pin_auth_timeout)) : EmptyMessage;
    }

    public static Pair authRequiredAfterReboot(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_prompt_reason_restart_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_prompt_reason_restart_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_prompt_reason_restart_pin)) : EmptyMessage;
    }

    public static Pair authRequiredAfterUserLockdown(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_prompt_after_user_lockdown_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_prompt_after_user_lockdown_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_prompt_after_user_lockdown_pin)) : EmptyMessage;
    }

    public static Pair authRequiredForMainlineUpdate(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_prompt_after_update_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_prompt_after_update_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_prompt_after_update_pin)) : EmptyMessage;
    }

    public static Pair authRequiredForUnattendedUpdate(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_prompt_added_security_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_prompt_added_security_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_prompt_added_security_pin)) : EmptyMessage;
    }

    public static Pair class3AuthLockedOut(AuthenticationMethodModel authenticationMethodModel) {
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pattern_none_instructions), Integer.valueOf(R.string.kg_bio_too_many_attempts_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_password_none_instructions), Integer.valueOf(R.string.kg_bio_too_many_attempts_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_none_pin_none_instructions), Integer.valueOf(R.string.kg_bio_too_many_attempts_pin)) : EmptyMessage;
    }

    public static Pair defaultMessage(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pattern_or_fp : R.string.kg_none_pattern_none_instructions), 0);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_password_or_fp : R.string.kg_none_password_none_instructions), displayDefaultSubMessage(authenticationMethodModel));
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pin_or_fp : R.string.kg_none_pin_none_instructions), displayDefaultSubMessage(authenticationMethodModel));
        }
        return EmptyMessage;
    }

    public static Object displayDefaultSubMessage(AuthenticationMethodModel authenticationMethodModel) {
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return CscRune.SECURITY_VZW_INSTRUCTION ? setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw) : setSubSecurityMessage(R.string.kg_password_sub_instructions);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return CscRune.SECURITY_VZW_INSTRUCTION ? setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw) : setSubSecurityMessage(R.string.kg_pin_sub_instructions);
        }
        return 0;
    }

    public static Pair faceLockedOut(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_face_locked_out);
        if (zAreEqual) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pattern_or_fp : R.string.kg_none_pattern_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_password_or_fp : R.string.kg_none_password_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pin_or_fp : R.string.kg_none_pin_none_instructions), numValueOf);
        }
        return EmptyMessage;
    }

    public static Pair incorrectFaceInput(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        Integer numValueOf = Integer.valueOf(R.string.bouncer_face_not_recognized);
        if (z) {
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE)) {
                return new Pair(Integer.valueOf(R.string.kg_unlock_with_pattern_or_fp), numValueOf);
            }
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
                return new Pair(Integer.valueOf(R.string.kg_unlock_with_password_or_fp), numValueOf);
            }
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
                return new Pair(Integer.valueOf(R.string.kg_unlock_with_pin_or_fp), numValueOf);
            }
        } else {
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE)) {
                return new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pattern));
            }
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
                return new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_password));
            }
            if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
                return new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pin));
            }
        }
        return EmptyMessage;
    }

    public static Pair incorrectFingerprintInput(AuthenticationMethodModel authenticationMethodModel) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_fp_not_recognized);
        return zAreEqual ? new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(numValueOf, Integer.valueOf(R.string.kg_bio_try_again_or_pin)) : EmptyMessage;
    }

    public static Pair incorrectSecurityInput(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        int i = z ? R.string.kg_wrong_input_try_fp_suggestion : 0;
        return Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_incorrect_pattern), Integer.valueOf(i)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_incorrect_password), Integer.valueOf(i)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.kg_incorrect_pin), Integer.valueOf(i)) : EmptyMessage;
    }

    public static Pair nonStrongAuthTimeout(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_prompt_auth_timeout);
        if (zAreEqual) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pattern_or_fp : R.string.kg_none_pattern_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_password_or_fp : R.string.kg_none_password_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pin_or_fp : R.string.kg_none_pin_none_instructions), numValueOf);
        }
        return EmptyMessage;
    }

    public static Pair primaryAuthLockedOut(AuthenticationMethodModel authenticationMethodModel) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_too_many_failed_attempts_countdown);
        return zAreEqual ? new Pair(numValueOf, Integer.valueOf(R.string.kg_primary_auth_locked_out_pattern)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE) ? new Pair(numValueOf, Integer.valueOf(R.string.kg_primary_auth_locked_out_password)) : Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(numValueOf, Integer.valueOf(R.string.kg_primary_auth_locked_out_pin)) : EmptyMessage;
    }

    public static Bundle setSubSecurityMessage(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("SubMessageResId", i);
        if (i != 0) {
            bundle.putInt("SubMessageFormatArgs", 4);
        }
        return bundle;
    }

    public static Pair trustAgentDisabled(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        boolean zAreEqual = Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pattern.INSTANCE);
        Integer numValueOf = Integer.valueOf(R.string.kg_trust_agent_disabled);
        if (zAreEqual) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pattern_or_fp : R.string.kg_none_pattern_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Password.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_password_or_fp : R.string.kg_none_password_none_instructions), numValueOf);
        }
        if (Intrinsics.areEqual(authenticationMethodModel, AuthenticationMethodModel.Pin.INSTANCE)) {
            return new Pair(Integer.valueOf(z ? R.string.kg_unlock_with_pin_or_fp : R.string.kg_none_pin_none_instructions), numValueOf);
        }
        return EmptyMessage;
    }
}
