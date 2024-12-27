package com.android.systemui.dreams;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class DreamOverlayContainerViewController$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        KeyguardState keyguardState = (KeyguardState) obj;
        KeyguardState.Companion.getClass();
        return Boolean.valueOf(keyguardState == KeyguardState.PRIMARY_BOUNCER || keyguardState == KeyguardState.ALTERNATE_BOUNCER);
    }
}
