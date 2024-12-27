package com.android.systemui.qs.tiles.viewmodel;

import android.os.UserHandle;
import kotlinx.coroutines.flow.SharedFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class StubQSTileViewModel implements QSTileViewModel {
    public static final StubQSTileViewModel INSTANCE = new StubQSTileViewModel();

    private StubQSTileViewModel() {
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void destroy() {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void forceUpdate() {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final QSTileConfig getConfig() {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final SharedFlow getState() {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final StateFlow isAvailable() {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void onActionPerformed(QSTileUserAction qSTileUserAction) {
        throw new IllegalStateException("Don't call stubs".toString());
    }

    @Override // com.android.systemui.qs.tiles.viewmodel.QSTileViewModel
    public final void onUserChanged(UserHandle userHandle) {
        throw new IllegalStateException("Don't call stubs".toString());
    }
}
