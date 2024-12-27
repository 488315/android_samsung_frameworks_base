package com.android.systemui.keyguard.ui.composable.section;

import com.android.compose.animation.scene.ElementKey;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;

public abstract class LockSectionKt {
    public static final ElementKey LockIconElementKey = new ElementKey(PluginLockStar.LOCK_ICON_TYPE, null, null, 6, null);
}
