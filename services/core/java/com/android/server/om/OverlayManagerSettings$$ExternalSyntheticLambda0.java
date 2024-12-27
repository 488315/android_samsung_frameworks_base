package com.android.server.om;

import android.util.Slog;

import java.util.function.Predicate;

public final /* synthetic */ class OverlayManagerSettings$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ OverlayManagerSettings$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        OverlayManagerSettings.SettingsItem settingsItem =
                (OverlayManagerSettings.SettingsItem) obj;
        switch (i) {
            case 0:
                if (settingsItem.mUserId != i2) {
                    return false;
                }
                Slog.d(
                        "OverlayManager",
                        "Removing overlay "
                                + settingsItem.mOverlay
                                + " for user "
                                + i2
                                + " from settings because user was removed");
                return true;
            default:
                return settingsItem.mUserId == i2;
        }
    }
}
