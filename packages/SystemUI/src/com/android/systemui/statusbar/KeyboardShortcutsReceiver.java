package com.android.systemui.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.Flags;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.shared.recents.utilities.Utilities;

public class KeyboardShortcutsReceiver extends BroadcastReceiver {
    public final FeatureFlags mFeatureFlags;

    public KeyboardShortcutsReceiver(FeatureFlags featureFlags) {
        this.mFeatureFlags = featureFlags;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Flags.FEATURE_FLAGS.getClass();
        if (((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.SHORTCUT_LIST_SEARCH_LAYOUT)) {
            Utilities.isLargeScreen(context);
        }
        if ("com.android.intent.action.SHOW_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.show(-1, context);
        } else if ("com.android.intent.action.DISMISS_KEYBOARD_SHORTCUTS".equals(intent.getAction())) {
            KeyboardShortcuts.dismiss();
        }
    }
}
