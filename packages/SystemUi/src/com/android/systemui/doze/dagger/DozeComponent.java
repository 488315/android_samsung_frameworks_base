package com.android.systemui.doze.dagger;

import com.android.systemui.doze.AODMachine;
import com.android.systemui.doze.DozeMachine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface DozeComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Builder {
        DozeComponent build(DozeMachine.Service service);
    }

    AODMachine getAODMachine();
}
