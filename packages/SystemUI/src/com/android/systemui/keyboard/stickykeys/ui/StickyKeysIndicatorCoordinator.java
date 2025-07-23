package com.android.systemui.keyboard.stickykeys.ui;

import androidx.activity.ComponentDialog;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.keyboard.stickykeys.ui.viewmodel.StickyKeysIndicatorViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StickyKeysIndicatorCoordinator {
    public final CoroutineScope applicationScope;
    public ComponentDialog dialog;
    public final StickyKeyDialogFactory stickyKeyDialogFactory;
    public final StickyKeysLogger stickyKeysLogger;
    public final StickyKeysIndicatorViewModel viewModel;

    public StickyKeysIndicatorCoordinator(CoroutineScope coroutineScope, StickyKeyDialogFactory stickyKeyDialogFactory, StickyKeysIndicatorViewModel stickyKeysIndicatorViewModel, StickyKeysLogger stickyKeysLogger) {
        this.applicationScope = coroutineScope;
        this.stickyKeyDialogFactory = stickyKeyDialogFactory;
        this.viewModel = stickyKeysIndicatorViewModel;
        this.stickyKeysLogger = stickyKeysLogger;
    }

    public final void startListening() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new StickyKeysIndicatorCoordinator$startListening$1(this, null), 7);
    }
}
