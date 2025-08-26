package com.android.systemui.lockstar.shortcut;

import android.content.Context;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class LockStarShortcutController {
    public KeyguardSecBottomAreaViewController bottomAreaCallback;
    public final Context context;
    public final ArrayList taskList = new ArrayList();
    public final ZenModeController zenModeController;

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

    public LockStarShortcutController(Context context, ZenModeController zenModeController) {
        this.context = context;
        this.zenModeController = zenModeController;
    }
}
