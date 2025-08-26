package com.android.systemui.keyguard;

import kotlin.LazyKt__LazyJVMKt;

/* loaded from: classes2.dex */
public interface KeyguardSecUnlockAnimationController {

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
            LazyKt__LazyJVMKt.lazy(new KeyguardSecUnlockAnimationController$Companion$$ExternalSyntheticLambda0());
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
