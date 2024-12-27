package com.android.systemui.keyguard;

import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface KeyguardSecUnlockAnimationController {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
