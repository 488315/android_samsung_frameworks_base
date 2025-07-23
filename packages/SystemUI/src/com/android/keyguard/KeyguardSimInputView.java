package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyguardSimInputView extends KeyguardSecPinBasedInputView {
    public KeyguardEsimArea disableESimButton;
    public ImageView simImageView;

    public KeyguardSimInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, com.android.keyguard.KeyguardInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.simImageView = (ImageView) findViewById(R.id.keyguard_sim);
        this.disableESimButton = (KeyguardEsimArea) findViewById(R.id.keyguard_esim_area);
        super.onFinishInflate();
    }
}
