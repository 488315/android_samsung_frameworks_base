package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

public final class TemporaryViewUiEventLogger {
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING);
    public final UiEventLogger logger;

    public TemporaryViewUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
