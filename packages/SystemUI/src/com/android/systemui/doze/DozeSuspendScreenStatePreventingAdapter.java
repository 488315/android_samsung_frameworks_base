package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import java.util.concurrent.Executor;

public final class DozeSuspendScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeSuspendScreenStatePreventingAdapter(DozeMachine.Service service, Executor executor) {
        super(service, executor);
    }
}
