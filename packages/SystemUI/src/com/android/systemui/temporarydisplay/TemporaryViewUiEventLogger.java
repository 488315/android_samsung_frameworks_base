package com.android.systemui.temporarydisplay;

import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;

/* loaded from: classes3.dex */
public final class TemporaryViewUiEventLogger {
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final UiEventLogger logger;

    public TemporaryViewUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }
}
