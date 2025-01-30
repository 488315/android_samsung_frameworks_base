package com.samsung.android.desktopsystemui.sharedlib.system;

import android.graphics.drawable.Icon;
import android.view.KeyboardShortcutInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyboardShortcutInfoWrapper {
    public static Icon getIcon(KeyboardShortcutInfo keyboardShortcutInfo) {
        return keyboardShortcutInfo.getIcon();
    }

    public static KeyboardShortcutInfo getKeyboardShortcutInfo(CharSequence charSequence, Icon icon, int i, int i2) {
        return new KeyboardShortcutInfo(charSequence, icon, i, i2);
    }
}
