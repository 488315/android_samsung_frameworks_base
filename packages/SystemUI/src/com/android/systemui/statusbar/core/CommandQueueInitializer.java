package com.android.systemui.statusbar.core;

import android.content.Context;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.InitController;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryStore;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CommandQueueInitializer implements CoreStartable {
    public final IStatusBarService barService;
    public final CommandQueue commandQueue;
    public final Context context;

    public CommandQueueInitializer(Context context, CommandQueue commandQueue, Lazy lazy, StatusBarModeRepositoryStore statusBarModeRepositoryStore, InitController initController, IStatusBarService iStatusBarService, NavigationBarController navigationBarController) {
        this.context = context;
        this.commandQueue = commandQueue;
        this.barService = iStatusBarService;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }
}
