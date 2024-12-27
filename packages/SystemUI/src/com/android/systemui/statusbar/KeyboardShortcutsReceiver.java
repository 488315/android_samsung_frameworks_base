package com.android.systemui.statusbar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.android.systemui.Flags;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.shared.recents.utilities.Utilities;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
