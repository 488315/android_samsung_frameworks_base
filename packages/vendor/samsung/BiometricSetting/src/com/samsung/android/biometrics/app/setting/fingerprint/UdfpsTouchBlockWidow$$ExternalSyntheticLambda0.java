package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public final /* synthetic */ class UdfpsTouchBlockWidow$$ExternalSyntheticLambda0
        implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("BSS_SysUiWindow.TB", "Touch: " + motionEvent.getAction());
        return false;
    }
}
