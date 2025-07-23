package com.android.settingslib;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class PrimarySwitchPreference$$ExternalSyntheticLambda1 implements View.OnTouchListener {
    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return motionEvent.getActionMasked() == 2;
    }
}
