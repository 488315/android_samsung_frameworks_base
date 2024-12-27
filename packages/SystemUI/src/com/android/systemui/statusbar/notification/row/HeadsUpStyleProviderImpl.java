package com.android.systemui.statusbar.notification.row;

import android.app.Flags;
import android.os.SystemProperties;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryImpl;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;

public final class HeadsUpStyleProviderImpl implements HeadsUpStyleProvider {
    public final StatusBarModeRepositoryStore statusBarModeRepositoryStore;

    public HeadsUpStyleProviderImpl(StatusBarModeRepositoryStore statusBarModeRepositoryStore) {
        this.statusBarModeRepositoryStore = statusBarModeRepositoryStore;
    }

    public final boolean shouldApplyCompactStyle() {
        if (Flags.compactHeadsUpNotification()) {
            return ((Boolean) ((StatusBarModeRepositoryImpl) this.statusBarModeRepositoryStore).defaultDisplay.isInFullscreenMode.$$delegate_0.getValue()).booleanValue() || SystemProperties.getBoolean("persist.compact_heads_up_notification.always_show", false);
        }
        return false;
    }
}
