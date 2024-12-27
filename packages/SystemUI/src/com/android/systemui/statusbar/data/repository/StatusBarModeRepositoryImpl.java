package com.android.systemui.statusbar.data.repository;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
