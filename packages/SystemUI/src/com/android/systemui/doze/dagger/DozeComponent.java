package com.android.systemui.doze.dagger;

import com.android.systemui.doze.AODMachine;
import com.android.systemui.doze.DozeMachine;

public interface DozeComponent {

    public interface Builder {
        DozeComponent build(DozeMachine.Service service);
    }

    AODMachine getAODMachine();
}
