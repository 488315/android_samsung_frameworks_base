package com.android.systemui.statusbar.core;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.data.repository.LightBarControllerStore;
import com.android.systemui.statusbar.data.repository.PrivacyDotWindowControllerStore;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarStarter implements CoreStartable {
    public final CoroutineScope applicationScope;
    public final DisplayRepository displayRepository;
    public final LightBarControllerStore lightBarControllerStore;
    public final MultiDisplayStatusBarOrchestratorStore multiDisplayStatusBarOrchestratorStore;
    public final PrivacyDotWindowControllerStore privacyDotWindowControllerStore;
    public final StatusBarInitializerStore statusBarInitializerStore;

    public MultiDisplayStatusBarStarter(CoroutineScope coroutineScope, MultiDisplayStatusBarOrchestratorStore multiDisplayStatusBarOrchestratorStore, DisplayRepository displayRepository, StatusBarInitializerStore statusBarInitializerStore, PrivacyDotWindowControllerStore privacyDotWindowControllerStore, LightBarControllerStore lightBarControllerStore) {
        this.applicationScope = coroutineScope;
        this.multiDisplayStatusBarOrchestratorStore = multiDisplayStatusBarOrchestratorStore;
        this.displayRepository = displayRepository;
        this.statusBarInitializerStore = statusBarInitializerStore;
        this.privacyDotWindowControllerStore = privacyDotWindowControllerStore;
        this.lightBarControllerStore = lightBarControllerStore;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new MultiDisplayStatusBarStarter$start$1(this, null), 7);
    }
}
