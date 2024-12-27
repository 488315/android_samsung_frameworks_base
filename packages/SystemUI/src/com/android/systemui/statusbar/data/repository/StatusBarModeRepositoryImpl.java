package com.android.systemui.statusbar.data.repository;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import java.io.PrintWriter;

public final class StatusBarModeRepositoryImpl implements StatusBarModeRepositoryStore, CoreStartable, StatusBarInitializer.OnStatusBarViewInitializedListener {
    public final StatusBarModePerDisplayRepositoryImpl defaultDisplay;

    public StatusBarModeRepositoryImpl(int i, StatusBarModePerDisplayRepositoryFactory statusBarModePerDisplayRepositoryFactory) {
        this.defaultDisplay = statusBarModePerDisplayRepositoryFactory.create(i);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.defaultDisplay.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.statusbar.core.StatusBarInitializer.OnStatusBarViewInitializedListener
    public final void onStatusBarViewInitialized(StatusBarFragmentComponent statusBarFragmentComponent) {
        this.defaultDisplay.onStatusBarViewInitialized(statusBarFragmentComponent);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = this.defaultDisplay;
        statusBarModePerDisplayRepositoryImpl.commandQueue.addCallback((CommandQueue.Callbacks) statusBarModePerDisplayRepositoryImpl.commandQueueCallback);
    }
}
