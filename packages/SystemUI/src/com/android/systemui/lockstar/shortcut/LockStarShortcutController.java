package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import com.android.systemui.pluginlock.PluginLockBottomAreaCallback;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockStarShortcutController {
    public PluginLockBottomAreaCallback bottomAreaCallback;
    public final Context context;
    public final ArrayList taskList = new ArrayList();
    public final ZenModeController zenModeController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
