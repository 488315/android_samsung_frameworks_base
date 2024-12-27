package com.android.systemui.navigationbar;

import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBarView f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda4(NavigationBarView navigationBarView, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBarView;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        NavigationBarView navigationBarView = this.f$0;
        switch (i) {
            case 0:
                NavigationBarView$$ExternalSyntheticLambda0 navigationBarView$$ExternalSyntheticLambda0 = navigationBarView.mPipListener;
                PipController.PipImpl pipImpl = (PipController.PipImpl) ((Pip) obj);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(2, pipImpl, navigationBarView$$ExternalSyntheticLambda0));
                break;
            case 1:
                navigationBarView.mEdgeBackGestureHandler.setBackAnimation((BackAnimationController.BackAnimationImpl) obj);
                break;
            default:
                NavigationBarView$$ExternalSyntheticLambda0 navigationBarView$$ExternalSyntheticLambda02 = navigationBarView.mPipListener;
                PipController.PipImpl pipImpl2 = (PipController.PipImpl) ((Pip) obj);
                ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(1, pipImpl2, navigationBarView$$ExternalSyntheticLambda02));
                break;
        }
    }
}
