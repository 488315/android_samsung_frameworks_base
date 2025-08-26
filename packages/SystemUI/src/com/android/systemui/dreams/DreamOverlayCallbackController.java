package com.android.systemui.dreams;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DreamOverlayCallbackController implements CallbackController {
    public final Set callbacks = new LinkedHashSet();
    public boolean isDreaming;

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.callbacks.add((KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1) obj);
    }

    public final void onWakeUp() {
        if (this.isDreaming) {
            this.isDreaming = false;
            for (KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1 : this.callbacks) {
                keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.getClass();
                ChannelExt channelExt = ChannelExt.INSTANCE;
                Boolean bool = Boolean.FALSE;
                channelExt.getClass();
                ChannelExt.trySendWithFailureLogging(keyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1.$$this$conflatedCallbackFlow, bool, "KeyguardRepositoryImpl", "updated isDreamingWithOverlay");
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.callbacks.remove((KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1) obj);
    }
}
