package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import java.util.concurrent.Executor;

public final class DozeScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeScreenStatePreventingAdapter(DozeMachine.Service service, Executor executor) {
        super(service, executor);
    }
}
