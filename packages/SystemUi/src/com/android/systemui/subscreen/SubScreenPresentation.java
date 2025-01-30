package com.android.systemui.subscreen;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubScreenPresentation extends Presentation {
    public SubScreenPresentation(Context context, Display display) {
        super(context, display, 2132018536, 2620);
        setCancelable(false);
        getWindow().getDecorView().semSetRoundedCorners(0);
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.subscreen_presentation);
        getWindow().getDecorView().setSystemUiVisibility(1792);
        getWindow().setNavigationBarContrastEnforced(false);
        getWindow().setNavigationBarColor(0);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (attributes != null) {
            attributes.setTitle("SubScreen");
            attributes.semSetScreenTimeout(5000L);
            attributes.semSetScreenDimDuration(0L);
            attributes.privateFlags |= 16;
            attributes.format = -3;
            getWindow().setAttributes(attributes);
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onDetachedFromWindow() {
    }
}
