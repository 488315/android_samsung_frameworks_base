package com.android.systemui.accessibility;

import com.android.systemui.accessibility.MagnificationModeSwitch;

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
