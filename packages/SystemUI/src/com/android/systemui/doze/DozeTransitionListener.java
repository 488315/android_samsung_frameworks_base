package com.android.systemui.doze;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
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
        KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 = (KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1) obj;
        synchronized (this) {
            this.callbacks.add(keyguardRepositoryImpl$dozeTransitionModel$1$callback$1);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 = (KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1) obj;
        synchronized (this) {
            this.callbacks.remove(keyguardRepositoryImpl$dozeTransitionModel$1$callback$1);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        Set<KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1> set;
        this.oldState = state;
        this.newState = state2;
        synchronized (this) {
            set = CollectionsKt___CollectionsKt.toSet(this.callbacks);
        }
        for (KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1 keyguardRepositoryImpl$dozeTransitionModel$1$callback$1 : set) {
            keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.getClass();
            ChannelExt channelExt = ChannelExt.INSTANCE;
            KeyguardRepositoryImpl keyguardRepositoryImpl = keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.this$0;
            DozeTransitionModel dozeTransitionModel = new DozeTransitionModel(KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl, state), KeyguardRepositoryImpl.access$dozeMachineStateToModel(keyguardRepositoryImpl, state2));
            ProducerScope producerScope = keyguardRepositoryImpl$dozeTransitionModel$1$callback$1.$$this$conflatedCallbackFlow;
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, dozeTransitionModel, "KeyguardRepositoryImpl", "doze transition model");
        }
    }
}
