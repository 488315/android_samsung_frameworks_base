package com.android.systemui.accessibility;

import com.android.systemui.accessibility.MagnificationModeSwitch;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MagnificationImpl$$ExternalSyntheticLambda2 implements MagnificationModeSwitch.ClickListener {
    public final /* synthetic */ MagnificationImpl f$0;

    public /* synthetic */ MagnificationImpl$$ExternalSyntheticLambda2(MagnificationImpl magnificationImpl) {
        this.f$0 = magnificationImpl;
    }

    @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
    public final void onClick(int i) {
        MagnificationImpl magnificationImpl = this.f$0;
        magnificationImpl.mHandler.post(new MagnificationImpl$$ExternalSyntheticLambda4(magnificationImpl, i, 0));
    }
}
