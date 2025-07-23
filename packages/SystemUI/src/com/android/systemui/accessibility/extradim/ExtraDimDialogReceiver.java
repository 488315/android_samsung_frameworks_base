package com.android.systemui.accessibility.extradim;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtraDimDialogReceiver extends BroadcastReceiver {
    public final ExtraDimDialogManager extraDimDialogManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ExtraDimDialogReceiver(ExtraDimDialogManager extraDimDialogManager) {
        this.extraDimDialogManager = extraDimDialogManager;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (context.getResources().getBoolean(R.bool.config_isDesktopModeSupported) && "com.android.systemui.action.LAUNCH_REMOVE_EXTRA_DIM_DIALOG".equals(intent.getAction())) {
            this.extraDimDialogManager.dismissKeyguardIfNeededAndShowDialog(null);
        }
    }
}
