package com.android.systemui.statusbar.data.repository;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import kotlin.Unit;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class MultiDisplayStatusBarModeRepositoryStore extends StatusBarPerDisplayStoreImpl implements StatusBarModeRepositoryStore {
    public final StatusBarModePerDisplayRepositoryFactory factory;

    public MultiDisplayStatusBarModeRepositoryStore(CoroutineScope coroutineScope, StatusBarModePerDisplayRepositoryFactory statusBarModePerDisplayRepositoryFactory, DisplayRepository displayRepository) {
        super(coroutineScope, displayRepository);
        this.factory = statusBarModePerDisplayRepositoryFactory;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object createInstanceForDisplay(int i) {
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImplCreate = this.factory.create(i);
        statusBarModePerDisplayRepositoryImplCreate.start();
        return statusBarModePerDisplayRepositoryImplCreate;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Class getInstanceClass() {
        return null;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl
    public final Object onDisplayRemovalAction(Object obj) {
        StatusBarModePerDisplayRepositoryImpl statusBarModePerDisplayRepositoryImpl = (StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) obj);
        statusBarModePerDisplayRepositoryImpl.commandQueue.removeCallback((CommandQueue.Callbacks) statusBarModePerDisplayRepositoryImpl.commandQueueCallback);
        return Unit.INSTANCE;
    }
}
