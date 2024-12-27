package com.android.systemui.dreams;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class DreamOverlayContainerViewController$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        KeyguardState keyguardState = (KeyguardState) obj;
        KeyguardState.Companion.getClass();
        return Boolean.valueOf(keyguardState == KeyguardState.PRIMARY_BOUNCER || keyguardState == KeyguardState.ALTERNATE_BOUNCER);
    }
}
