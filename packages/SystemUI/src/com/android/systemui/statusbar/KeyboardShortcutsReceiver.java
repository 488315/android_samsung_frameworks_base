package com.android.systemui.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyboardShortcutsReceiver extends BroadcastReceiver {
    public final FeatureFlags mFeatureFlags;
    public final WindowManagerProvider mWindowManagerProvider;

    public KeyboardShortcutsReceiver(FeatureFlags featureFlags, WindowManagerProvider windowManagerProvider) {
        this.mFeatureFlags = featureFlags;
        this.mWindowManagerProvider = windowManagerProvider;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.SHORTCUT_LIST_SEARCH_LAYOUT)) {
            Utilities.isLargeScreen(context);
        }
        if ("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.show(context, -1, this.mWindowManagerProvider);
        } else if ("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.dismiss();
        }
    }
}
