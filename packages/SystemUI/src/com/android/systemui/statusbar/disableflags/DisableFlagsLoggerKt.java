package com.android.systemui.statusbar.disableflags;

import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;

public abstract class DisableFlagsLoggerKt {
    public static final List defaultDisable1FlagsList = CollectionsKt__CollectionsKt.listOf(new DisableFlagsLogger.DisableFlag(65536, 'E', 'e'), new DisableFlagsLogger.DisableFlag(131072, 'N', 'n'), new DisableFlagsLogger.DisableFlag(262144, 'A', 'a'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING, 'I', 'i'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_DEVICE_DOZING, 'H', 'h'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_BACK_DISABLED, 'B', 'b'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED, 'C', 'c'), new DisableFlagsLogger.DisableFlag(16777216, 'R', 'r'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_GAME_TOOLS_SHOWING, 'S', 's'), new DisableFlagsLogger.DisableFlag(QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, 'O', 'o'));
    public static final List defaultDisable2FlagsList = CollectionsKt__CollectionsKt.listOf(new DisableFlagsLogger.DisableFlag(1, 'Q', 'q'), new DisableFlagsLogger.DisableFlag(2, 'I', 'i'), new DisableFlagsLogger.DisableFlag(4, 'N', 'n'), new DisableFlagsLogger.DisableFlag(8, 'G', 'g'), new DisableFlagsLogger.DisableFlag(16, 'R', 'r'));
}
