package com.android.systemui.keyboard.stickykeys.ui;

import android.app.Dialog;
import com.android.systemui.keyboard.stickykeys.StickyKeysLogger;
import com.android.systemui.keyboard.stickykeys.ui.viewmodel.StickyKeysIndicatorViewModel;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class StickyKeysIndicatorCoordinator {
    public final CoroutineScope applicationScope;
    public Dialog dialog;
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
        BuildersKt.launch$default(this.applicationScope, null, null, new StickyKeysIndicatorCoordinator$startListening$1(this, null), 3);
    }
}
