package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardDismissUtil implements KeyguardDismissHandler {
    public volatile KeyguardDismissHandler mDismissHandler;

    @Override // com.android.systemui.statusbar.phone.KeyguardDismissHandler
    public final void executeWhenUnlocked(ActivityStarter.OnDismissAction onDismissAction, boolean z, boolean z2) {
        KeyguardDismissHandler keyguardDismissHandler = this.mDismissHandler;
        if (keyguardDismissHandler != null) {
            keyguardDismissHandler.executeWhenUnlocked(onDismissAction, z, z2);
        } else {
            Log.wtf("KeyguardDismissUtil", "KeyguardDismissHandler not set.");
            onDismissAction.onDismiss();
        }
    }
}
