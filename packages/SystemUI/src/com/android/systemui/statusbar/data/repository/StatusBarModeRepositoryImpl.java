package com.android.systemui.statusbar.data.repository;

import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.phone.fragment.dagger.HomeStatusBarComponent;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class StatusBarModeRepositoryImpl implements StatusBarModeRepositoryStore, CoreStartable, StatusBarInitializer.OnStatusBarViewInitializedListener {
    public final StatusBarModePerDisplayRepositoryImpl defaultDisplay;

    public StatusBarModeRepositoryImpl(int i, StatusBarModePerDisplayRepositoryFactory statusBarModePerDisplayRepositoryFactory) {
        this.defaultDisplay = statusBarModePerDisplayRepositoryFactory.create(i);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        this.defaultDisplay.dump(printWriter, strArr);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        return this.defaultDisplay;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        return this.defaultDisplay;
    }

    @Override // com.android.systemui.statusbar.core.StatusBarInitializer.OnStatusBarViewInitializedListener
    public final void onStatusBarViewInitialized(HomeStatusBarComponent homeStatusBarComponent) {
        this.defaultDisplay.onStatusBarViewInitialized(homeStatusBarComponent);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.defaultDisplay.start();
    }
}
