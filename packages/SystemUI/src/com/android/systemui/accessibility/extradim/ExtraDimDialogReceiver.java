package com.android.systemui.accessibility.extradim;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class ExtraDimDialogReceiver extends BroadcastReceiver {
    public final ExtraDimDialogManager extraDimDialogManager;

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
