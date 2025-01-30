package com.android.systemui.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyboardShortcutsReceiver extends BroadcastReceiver {
    public KeyboardShortcutsReceiver(FeatureFlags featureFlags) {
        ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.SHORTCUT_LIST_SEARCH_LAYOUT);
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if ("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.show(-1, context);
        } else if ("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.dismiss();
        }
    }
}
