package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.ui.view.SideFpsProgressBar;
import com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel;
import com.android.systemui.log.SideFpsLogger;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SideFpsProgressBarViewBinder implements CoreStartable {
    public final SideFpsLogger logger;
    public final SideFpsProgressBar view;
    public final SideFpsProgressBarViewModel viewModel;

    public SideFpsProgressBarViewBinder(SideFpsProgressBarViewModel sideFpsProgressBarViewModel, SideFpsProgressBar sideFpsProgressBar, CoroutineScope coroutineScope, SideFpsLogger sideFpsLogger, CommandRegistry commandRegistry) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.FEATURE_FLAGS.getClass();
    }
}
