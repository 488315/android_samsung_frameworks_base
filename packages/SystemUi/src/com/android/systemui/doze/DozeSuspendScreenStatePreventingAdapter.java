package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeSuspendScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeSuspendScreenStatePreventingAdapter(DozeMachine.Service service, Executor executor) {
        super(service, executor);
    }
}
