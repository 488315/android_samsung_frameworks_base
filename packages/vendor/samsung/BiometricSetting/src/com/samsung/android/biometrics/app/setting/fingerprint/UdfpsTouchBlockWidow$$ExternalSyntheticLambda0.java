package com.samsung.android.biometrics.app.setting.fingerprint;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public final /* synthetic */ class UdfpsTouchBlockWidow$$ExternalSyntheticLambda0
        implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("BSS_SysUiWindow.TB", "Touch: " + motionEvent.getAction());
        return false;
    }
}
