package com.android.systemui.bouncer.shared.model;

import com.android.systemui.R;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.Pair;

public final class BouncerMessageStrings {
    public static final BouncerMessageStrings INSTANCE = new BouncerMessageStrings();
    public static final Pair EmptyMessage = new Pair(0, 0);

    private BouncerMessageStrings() {
    }

    public static Pair class3AuthLockedOut(AuthenticationMethodModel authenticationMethodModel) {
        return authenticationMethodModel.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(R.string.keyguard_enter_pattern), Integer.valueOf(R.string.kg_bio_too_many_attempts_pattern)) : authenticationMethodModel.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(R.string.keyguard_enter_password), Integer.valueOf(R.string.kg_bio_too_many_attempts_password)) : authenticationMethodModel.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(R.string.keyguard_enter_pin), Integer.valueOf(R.string.kg_bio_too_many_attempts_pin)) : EmptyMessage;
    }

    public static Pair defaultMessage(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        return authenticationMethodModel.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(patternDefaultMessage(z)), 0) : authenticationMethodModel.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(passwordDefaultMessage(z)), 0) : authenticationMethodModel.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(pinDefaultMessage(z)), 0) : EmptyMessage;
    }

    public static int passwordDefaultMessage(boolean z) {
        return z ? R.string.kg_unlock_with_password_or_fp : R.string.keyguard_enter_password;
    }

    public static int patternDefaultMessage(boolean z) {
        return z ? R.string.kg_unlock_with_pattern_or_fp : R.string.keyguard_enter_pattern;
    }

    public static int pinDefaultMessage(boolean z) {
        return z ? R.string.kg_unlock_with_pin_or_fp : R.string.keyguard_enter_pin;
    }

    public static Pair trustAgentDisabled(AuthenticationMethodModel authenticationMethodModel, boolean z) {
        return authenticationMethodModel.equals(AuthenticationMethodModel.Pattern.INSTANCE) ? new Pair(Integer.valueOf(patternDefaultMessage(z)), Integer.valueOf(R.string.kg_trust_agent_disabled)) : authenticationMethodModel.equals(AuthenticationMethodModel.Password.INSTANCE) ? new Pair(Integer.valueOf(passwordDefaultMessage(z)), Integer.valueOf(R.string.kg_trust_agent_disabled)) : authenticationMethodModel.equals(AuthenticationMethodModel.Pin.INSTANCE) ? new Pair(Integer.valueOf(pinDefaultMessage(z)), Integer.valueOf(R.string.kg_trust_agent_disabled)) : EmptyMessage;
    }
}
