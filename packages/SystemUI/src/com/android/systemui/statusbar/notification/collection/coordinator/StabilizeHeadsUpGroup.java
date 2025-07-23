package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.flags.FlagToken;
import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StabilizeHeadsUpGroup {
    public static final int $stable = 0;
    public static final String FLAG_NAME = "com.android.systemui.stabilize_heads_up_group_v2";
    public static final StabilizeHeadsUpGroup INSTANCE = new StabilizeHeadsUpGroup();

    private StabilizeHeadsUpGroup() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("Legacy code path not supported when com.android.systemui.stabilize_heads_up_group_v2 is enabled.".toString());
    }

    public static final boolean isEnabled() {
        return true;
    }

    public static final boolean isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        return false;
    }

    public static final void unsafeAssertInNewMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
    }

    public final FlagToken getToken() {
        return new FlagToken(FLAG_NAME, true);
    }

    public static /* synthetic */ void isEnabled$annotations() {
    }
}
