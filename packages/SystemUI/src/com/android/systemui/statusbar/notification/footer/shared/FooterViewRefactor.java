package com.android.systemui.statusbar.notification.footer.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

public final class FooterViewRefactor {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new FooterViewRefactor();
    }

    private FooterViewRefactor() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.notificationsFooterViewRefactor();
    }

    public static final void isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.notificationsFooterViewRefactor();
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
    }
}
