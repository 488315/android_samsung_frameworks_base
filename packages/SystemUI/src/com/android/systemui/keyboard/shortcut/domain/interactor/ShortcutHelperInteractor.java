package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperRepository;
import com.android.systemui.model.SysUiState;
import com.android.systemui.settings.DisplayTracker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ShortcutHelperInteractor {
    public final CoroutineScope backgroundScope;
    public final DisplayTracker displayTracker;
    public final ShortcutHelperRepository repository;
    public final StateFlowImpl state;
    public final SysUiState sysUiState;

    public ShortcutHelperInteractor(DisplayTracker displayTracker, CoroutineScope coroutineScope, SysUiState sysUiState, ShortcutHelperRepository shortcutHelperRepository) {
        this.displayTracker = displayTracker;
        this.backgroundScope = coroutineScope;
        this.sysUiState = sysUiState;
        this.repository = shortcutHelperRepository;
        this.state = shortcutHelperRepository.state;
    }

    public final void setSysUiStateFlagEnabled(boolean z) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new ShortcutHelperInteractor$setSysUiStateFlagEnabled$1(this, z, null), 3);
    }
}
