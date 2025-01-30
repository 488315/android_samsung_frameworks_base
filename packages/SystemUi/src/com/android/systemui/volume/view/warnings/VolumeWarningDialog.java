package com.android.systemui.volume.view.warnings;

import android.content.Context;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class VolumeWarningDialog extends AlertDialog {
    public VolumeWarningDialog(Context context) {
        super(context, 2132018534);
    }

    @Override // android.app.Dialog
    public Window getWindow() {
        Window window = super.getWindow();
        Intrinsics.checkNotNull(window);
        return window;
    }

    public final void initButtons() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 17;
        layoutParams.weight = 1.0f;
        Button button = getButton(-1);
        if (button != null) {
            button.setLayoutParams(layoutParams);
        }
        Button button2 = getButton(-2);
        if (button2 == null) {
            return;
        }
        button2.setLayoutParams(layoutParams);
    }

    public void initDialog() {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.semAddPrivateFlags(16);
        attributes.setTitle(attributes.getClass().getSimpleName());
        attributes.setFitInsetsTypes(getWindow().getAttributes().getFitInsetsTypes() & (~WindowInsets.Type.statusBars()));
        window.setAttributes(attributes);
        getWindow().setGravity(80);
        getWindow().setType(2020);
        setCancelable(false);
    }
}
