package com.google.android.setupdesign.util;

import com.android.settingslib.avatarpicker.AvatarPickerActivity;
import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class ThemeHelper {
    public static final Logger LOG = new Logger("ThemeHelper");

    private ThemeHelper() {
    }

    public static String colorIntToHex(AvatarPickerActivity avatarPickerActivity, int i) {
        return String.format("#%06X", Integer.valueOf(avatarPickerActivity.getResources().getColor(i) & 16777215));
    }
}
