package com.android.systemui.navigationbar.gestural;

import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class EdgeBackGestureHandler$$ExternalSyntheticLambda9 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PipController.PipImpl pipImpl = (PipController.PipImpl) ((Pip) obj);
        HandlerExecutor handlerExecutor = (HandlerExecutor) PipController.this.mMainExecutor;
        handlerExecutor.execute(new PipController$$ExternalSyntheticLambda0(3, pipImpl, null));
    }
}
