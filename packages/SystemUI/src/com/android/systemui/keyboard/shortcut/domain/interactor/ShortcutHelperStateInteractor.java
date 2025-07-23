package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperStateRepository;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutHelperState;
import com.android.systemui.model.SysUiState;
import com.android.systemui.settings.DisplayTracker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperStateInteractor {
    public final CoroutineScope backgroundScope;
    public final DisplayTracker displayTracker;
    public final ShortcutHelperStateRepository repository;
    public final SysUiState sysUiState;

    public ShortcutHelperStateInteractor(DisplayTracker displayTracker, CoroutineScope coroutineScope, SysUiState sysUiState, ShortcutHelperStateRepository shortcutHelperStateRepository) {
        this.displayTracker = displayTracker;
        this.backgroundScope = coroutineScope;
        this.sysUiState = sysUiState;
        this.repository = shortcutHelperStateRepository;
        ReadonlyStateFlow readonlyStateFlow = shortcutHelperStateRepository.state;
    }

    public final void onViewClosed() {
        this.repository._state.setValue(ShortcutHelperState.Inactive.INSTANCE);
        CoroutineTracingKt.launchTraced$default(this.backgroundScope, null, null, new ShortcutHelperStateInteractor$setSysUiStateFlagEnabled$1(this, false, null), 7);
    }
}
