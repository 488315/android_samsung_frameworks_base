package com.google.android.setupcompat.util;

import android.content.Intent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WizardManagerHelper {
    public static final String ACTION_NEXT = "com.android.wizard.NEXT";
    static final String EXTRA_ACTION_ID = "actionId";
    static final String EXTRA_SCRIPT_URI = "scriptUri";
    static final String EXTRA_WIZARD_BUNDLE = "wizardBundle";

    private WizardManagerHelper() {
    }

    public static boolean isAnySetupWizard(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra("isSetupFlow", false);
    }
}
