package com.android.systemui.dreams;

import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarViewController$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class DreamOverlayStatusBarItemsProvider implements CallbackController {
    public final Executor mExecutor;
    public final List mItems = new ArrayList();
    public final List mCallbacks = new ArrayList();

    public DreamOverlayStatusBarItemsProvider(Executor executor) {
        this.mExecutor = executor;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(this, (AmbientStatusBarViewController$$ExternalSyntheticLambda3) obj, 0));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(this, (AmbientStatusBarViewController$$ExternalSyntheticLambda3) obj, 1));
    }
}
