package com.samsung.android.biometrics.app.setting.fingerprint;

import androidx.recyclerview.widget.RecyclerView;

import com.samsung.android.biometrics.app.setting.SysUiUdfpsManager;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final /* synthetic */ class UdfpsFodHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UdfpsFodHandler f$0;

    public /* synthetic */ UdfpsFodHandler$$ExternalSyntheticLambda0(
            UdfpsFodHandler udfpsFodHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = udfpsFodHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UdfpsFodHandler udfpsFodHandler = this.f$0;
        switch (i) {
            case 0:
                udfpsFodHandler.handleTouchUp(
                        RecyclerView.DECELERATION_RATE, RecyclerView.DECELERATION_RATE);
                break;
            default:
                if (!udfpsFodHandler.mIsTouchDown
                        && !udfpsFodHandler.mShouldIgnoreSingleTap.getAsBoolean()) {
                    Iterator it = ((CopyOnWriteArrayList) udfpsFodHandler.mFodConsumers).iterator();
                    while (it.hasNext()) {
                        SysUiUdfpsManager.this.handleOnFodSingleTap();
                    }
                    break;
                }
                break;
        }
    }
}
