package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import com.android.systemui.pluginlock.PluginLockBottomAreaCallback;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class LockStarShortcutController {
    public PluginLockBottomAreaCallback bottomAreaCallback;
    public final Context context;
    public final ArrayList taskList = new ArrayList();
    public final ZenModeController zenModeController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public LockStarShortcutController(Context context, ZenModeController zenModeController) {
        this.context = context;
        this.zenModeController = zenModeController;
    }
}
