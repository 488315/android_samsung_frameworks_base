package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class StubQSTileViewModel implements QSTileViewModel {
    public static final StubQSTileViewModel INSTANCE = new StubQSTileViewModel();

    private StubQSTileViewModel() {
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void destroy() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void forceUpdate() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final QSTileConfig getConfig() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final int getCurrentTileUser() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final StateFlow getState() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final StateFlow isAvailable() {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void onActionPerformed(QSTileUserAction qSTileUserAction) {
        throw new IllegalStateException("Don't call stubs");
    }

    @Override // com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModel
    public final void onUserChanged(UserHandle userHandle) {
        throw new IllegalStateException("Don't call stubs");
    }
}
