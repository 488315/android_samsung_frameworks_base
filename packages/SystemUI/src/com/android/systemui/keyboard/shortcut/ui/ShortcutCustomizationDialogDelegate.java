package com.android.systemui.keyboard.shortcut.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import com.android.systemui.statusbar.phone.DialogDelegate;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
