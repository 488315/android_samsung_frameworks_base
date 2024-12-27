package com.android.systemui.keyguard;

import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public interface KeyguardSecUnlockAnimationController {

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
            LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardSecUnlockAnimationController$Companion$DEBUG$2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return Boolean.valueOf(android.util.Log.isLoggable("KeyguardUnlock", 3));
                }
            });
        }

        private Companion() {
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    default long getUnlockAnimationDuration() {
        return 0L;
    }

    default void setCallback(KeyguardViewMediatorHelperImpl$setupLocked$5 keyguardViewMediatorHelperImpl$setupLocked$5) {
    }
}
