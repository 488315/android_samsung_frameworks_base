package com.google.android.setupcompat.util;

import android.content.Intent;

/* loaded from: classes4.dex */
public final class WizardManagerHelper {
    private WizardManagerHelper() {
    }

    public static boolean isAnySetupWizard(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra("isSetupFlow", false);
    }
}
