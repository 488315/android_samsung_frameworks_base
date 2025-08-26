package com.android.systemui.doze.dagger;

import com.android.systemui.doze.DozeMachine;

/* loaded from: classes2.dex */
public interface DozeComponent {

    public interface Builder {
        DozeComponent build(DozeMachine.Service service);
    }
}
