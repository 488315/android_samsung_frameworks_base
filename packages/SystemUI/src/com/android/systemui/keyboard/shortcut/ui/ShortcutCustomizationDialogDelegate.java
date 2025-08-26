package com.android.systemui.keyboard.shortcut.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import com.android.systemui.statusbar.phone.DialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: classes2.dex */
public final class ShortcutCustomizationDialogDelegate implements DialogDelegate {
    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final /* bridge */ /* synthetic */ int getHeight(SystemUIDialog systemUIDialog) {
        return -2;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final /* bridge */ /* synthetic */ int getWidth(SystemUIDialog systemUIDialog) {
        return -2;
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        Window window = ((SystemUIDialog) dialog).getWindow();
        if (window != null) {
            window.setGravity(17);
        }
    }
}
