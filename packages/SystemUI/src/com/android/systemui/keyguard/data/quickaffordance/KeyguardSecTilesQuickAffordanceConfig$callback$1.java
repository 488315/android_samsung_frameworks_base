package com.android.systemui.keyguard.data.quickaffordance;

import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.statusbar.KeyguardShortcutManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardSecTilesQuickAffordanceConfig$callback$1 implements QSTile.Callback {
    public static final KeyguardSecTilesQuickAffordanceConfig$callback$1 INSTANCE = new KeyguardSecTilesQuickAffordanceConfig$callback$1();

    @Override // com.android.systemui.plugins.qs.QSTile.Callback
    public final void onStateChanged(QSTile.State state) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(state.state, "spec name: ", state.spec, ", tile state: ", "KeyguardSecTilesQuickAffordanceConfig");
        ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
    }
}
