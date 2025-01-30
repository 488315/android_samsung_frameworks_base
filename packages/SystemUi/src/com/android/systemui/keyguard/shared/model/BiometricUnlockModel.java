package com.android.systemui.keyguard.shared.model;

import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum BiometricUnlockModel {
    NONE,
    WAKE_AND_UNLOCK,
    WAKE_AND_UNLOCK_PULSING,
    SHOW_BOUNCER,
    ONLY_WAKE,
    UNLOCK_COLLAPSING,
    DISMISS_BOUNCER,
    WAKE_AND_UNLOCK_FROM_DREAM;

    public static final Companion Companion;
    public static final Set wakeAndUnlockModes;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        BiometricUnlockModel biometricUnlockModel = WAKE_AND_UNLOCK;
        BiometricUnlockModel biometricUnlockModel2 = WAKE_AND_UNLOCK_PULSING;
        BiometricUnlockModel biometricUnlockModel3 = WAKE_AND_UNLOCK_FROM_DREAM;
        Companion = new Companion(null);
        wakeAndUnlockModes = SetsKt__SetsKt.setOf(biometricUnlockModel, biometricUnlockModel3, biometricUnlockModel2);
    }
}
