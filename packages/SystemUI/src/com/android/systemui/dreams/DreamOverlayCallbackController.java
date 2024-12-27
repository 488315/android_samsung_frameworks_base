package com.android.systemui.dreams;

import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;

public final class DreamOverlayCallbackController implements CallbackController {
    public final Set callbacks = new LinkedHashSet();
    public boolean isDreaming;

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.callbacks.add((KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1) obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.callbacks.remove((KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1) obj);
    }
}
