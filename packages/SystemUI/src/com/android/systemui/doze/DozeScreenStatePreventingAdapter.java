package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DozeScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeScreenStatePreventingAdapter(DozeMachine.Service service, Executor executor) {
        super(service, executor);
    }
}
