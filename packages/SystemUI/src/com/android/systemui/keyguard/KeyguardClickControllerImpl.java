package com.android.systemui.keyguard;

import kotlin.jvm.functions.Function2;

public final class KeyguardClickControllerImpl implements KeyguardClickController {
    public final String tag = "KeyguardClickControllerImpl";
    public Function2 isClockContainerArea = new Function2() { // from class: com.android.systemui.keyguard.KeyguardClickControllerImpl$isClockContainerArea$1
        @Override // kotlin.jvm.functions.Function2
        public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            ((Number) obj).intValue();
            ((Number) obj2).intValue();
            return Boolean.FALSE;
        }
    };
}
