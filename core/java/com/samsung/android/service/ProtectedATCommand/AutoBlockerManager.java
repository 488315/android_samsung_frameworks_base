package com.samsung.android.service.ProtectedATCommand;

import android.content.Context;
import android.provider.Settings;
import android.util.Slog;

class AutoBlockerManager {
    AutoBlockerManager() {}

    static boolean isAutoBlockerOn(Context context) {
        if (Settings.Secure.getInt(
                        context.getContentResolver(), Settings.Secure.RAMPART_BLOCKED_AT_CMD, 0)
                != 1) {
            return false;
        }
        Slog.d("PACMClassifier", "Auto Blocker is on");
        return true;
    }
}
