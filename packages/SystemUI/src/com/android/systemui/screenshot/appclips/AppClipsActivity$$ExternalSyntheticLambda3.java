package com.android.systemui.screenshot.appclips;

import android.widget.CompoundButton;

/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsActivity$$ExternalSyntheticLambda3 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ AppClipsActivity f$0;

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.f$0.mBacklinksDataTextView.setVisibility(z ? 0 : 8);
    }
}
