package com.android.settingslib;

import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes.dex */
public final /* synthetic */ class PrimarySwitchPreference$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 2;
    }
}
