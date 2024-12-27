package com.android.systemui.statusbar.notification.footer.shared;

import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
