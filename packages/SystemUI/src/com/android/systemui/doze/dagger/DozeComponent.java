package com.android.systemui.doze.dagger;

import com.android.systemui.doze.AODMachine;
import com.android.systemui.doze.DozeMachine;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface DozeComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Builder {
        DozeComponent build(DozeMachine.Service service);
    }

    AODMachine getAODMachine();
}
