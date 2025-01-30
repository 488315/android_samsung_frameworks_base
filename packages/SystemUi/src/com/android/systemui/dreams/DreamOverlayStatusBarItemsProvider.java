package com.android.systemui.dreams;

import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DreamOverlayStatusBarItemsProvider implements CallbackController {
    public final Executor mExecutor;
    public final List mItems = new ArrayList();
    public final List mCallbacks = new ArrayList();

    public DreamOverlayStatusBarItemsProvider(Executor executor) {
        this.mExecutor = executor;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(this, (DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4) obj, 1));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mExecutor.execute(new DreamOverlayStatusBarItemsProvider$$ExternalSyntheticLambda0(this, (DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4) obj, 0));
    }
}
