package com.google.android.setupcompat.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* loaded from: classes4.dex */
public final class KeyboardHelper {
    private KeyboardHelper() {
    }

    public static boolean isKeyboardFocusEnhancementEnabled(Context context) {
        Bundle bundle = PartnerConfigHelper.keyboardFocusEnhancementBundle;
        if (bundle == null || bundle.isEmpty()) {
            try {
                PartnerConfigHelper.keyboardFocusEnhancementBundle = context.getContentResolver().call(PartnerConfigHelper.getContentUri(context), PartnerConfigHelper.IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD, (String) null, (Bundle) null);
            } catch (IllegalArgumentException | SecurityException unused) {
                Log.w("PartnerConfigHelper", "SetupWizard keyboard focus enhancement status unknown; return as false.");
                PartnerConfigHelper.keyboardFocusEnhancementBundle = null;
                return false;
            }
        }
        Bundle bundle2 = PartnerConfigHelper.keyboardFocusEnhancementBundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return false;
        }
        return PartnerConfigHelper.keyboardFocusEnhancementBundle.getBoolean(PartnerConfigHelper.IS_KEYBOARD_FOCUS_ENHANCEMENT_ENABLED_METHOD);
    }
}
