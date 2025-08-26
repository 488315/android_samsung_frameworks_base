package com.google.android.setupdesign.util;

import com.android.settingslib.avatarpicker.AvatarPickerActivity;
import com.google.android.setupcompat.util.Logger;

/* loaded from: classes4.dex */
public final class ThemeHelper {
    public static final Logger LOG = new Logger("ThemeHelper");

    private ThemeHelper() {
    }

    public static String colorIntToHex(AvatarPickerActivity avatarPickerActivity, int i) {
        return String.format("#%06X", Integer.valueOf(avatarPickerActivity.getResources().getColor(i) & 16777215));
    }
}
