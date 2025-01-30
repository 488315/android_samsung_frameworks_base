package com.android.systemui.hdmi;

import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HdmiCecSetMenuLanguageHelper {
    public final Executor mBackgroundExecutor;
    public final HashSet mDenylist;
    public Locale mLocale;
    public final SecureSettings mSecureSettings;

    public HdmiCecSetMenuLanguageHelper(Executor executor, SecureSettings secureSettings) {
        this.mBackgroundExecutor = executor;
        this.mSecureSettings = secureSettings;
        String stringForUser = ((SecureSettingsImpl) secureSettings).getStringForUser(-2, "hdmi_cec_set_menu_language_denylist");
        this.mDenylist = new HashSet(stringForUser == null ? Collections.EMPTY_SET : Arrays.asList(stringForUser.split(",")));
    }
}
