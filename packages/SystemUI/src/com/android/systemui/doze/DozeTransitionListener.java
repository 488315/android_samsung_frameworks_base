package com.android.systemui.doze;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DozeTransitionListener implements DozeMachine.Part, CallbackController {
    public final Set callbacks = new LinkedHashSet();
    public DozeMachine.State newState;
    public DozeMachine.State oldState;

    public DozeTransitionListener() {
        DozeMachine.State state = DozeMachine.State.UNINITIALIZED;
        this.oldState = state;
        this.newState = state;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.callbacks.add((KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1) obj);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.callbacks.remove((KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1) obj);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.oldState = state;
        this.newState = state2;
        for (KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 : this.callbacks) {
            keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.getClass();
            ChannelExt channelExt = ChannelExt.INSTANCE;
            KeyguardRepositoryImpl keyguardRepositoryImpl = keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.this$0;
            DozeTransitionModel dozeTransitionModel = new DozeTransitionModel(KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl, state), KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl, state2));
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.$$this$conflatedCallbackFlow, dozeTransitionModel, "KeyguardRepositoryImpl", "doze transition model");
        }
    }
}
