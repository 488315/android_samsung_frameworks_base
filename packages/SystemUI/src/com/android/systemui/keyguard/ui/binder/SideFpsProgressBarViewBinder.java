package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.ui.view.SideFpsProgressBar;
import com.android.systemui.keyguard.ui.viewmodel.SideFpsProgressBarViewModel;
import com.android.systemui.log.SideFpsLogger;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SideFpsProgressBarViewBinder implements CoreStartable {
    public final SideFpsLogger logger;
    public final SideFpsProgressBar view;
    public final SideFpsProgressBarViewModel viewModel;

    public SideFpsProgressBarViewBinder(SideFpsProgressBarViewModel sideFpsProgressBarViewModel, SideFpsProgressBar sideFpsProgressBar, CoroutineScope coroutineScope, SideFpsLogger sideFpsLogger, CommandRegistry commandRegistry) {
        this.viewModel = sideFpsProgressBarViewModel;
        this.view = sideFpsProgressBar;
        this.logger = sideFpsLogger;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
