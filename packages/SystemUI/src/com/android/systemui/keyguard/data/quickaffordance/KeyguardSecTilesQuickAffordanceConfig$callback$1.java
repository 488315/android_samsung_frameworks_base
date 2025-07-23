package com.android.systemui.keyguard.data.quickaffordance;

import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.statusbar.KeyguardShortcutManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSecTilesQuickAffordanceConfig$callback$1 implements QSTile.Callback {
    public static final KeyguardSecTilesQuickAffordanceConfig$callback$1 INSTANCE = new KeyguardSecTilesQuickAffordanceConfig$callback$1();

    @Override // com.android.systemui.plugins.qs.QSTile.Callback
    public final void onStateChanged(QSTile.State state) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(state.state, "spec name: ", state.spec, ", tile state: ", "KeyguardSecTilesQuickAffordanceConfig");
        ((KeyguardShortcutManager) Dependency.sDependency.getDependencyInner(KeyguardShortcutManager.class)).updateShortcutIcons();
    }
}
