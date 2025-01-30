package com.android.systemui.keyguard.bouncer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.keyguard.BouncerKeyguardMessageArea;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageView extends LinearLayout {
    public BouncerKeyguardMessageArea primaryMessageView;
    public BouncerKeyguardMessageArea secondaryMessageView;

    public BouncerMessageView(Context context) {
        super(context);
        LinearLayout.inflate(getContext(), R.layout.bouncer_message_view, this);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.primaryMessageView = (BouncerKeyguardMessageArea) findViewById(R.id.bouncer_primary_message_area);
        this.secondaryMessageView = (BouncerKeyguardMessageArea) findViewById(R.id.bouncer_secondary_message_area);
        BouncerKeyguardMessageArea bouncerKeyguardMessageArea = this.primaryMessageView;
        if (bouncerKeyguardMessageArea != null) {
            bouncerKeyguardMessageArea.mIsDisabled = true;
            bouncerKeyguardMessageArea.update();
        }
        BouncerKeyguardMessageArea bouncerKeyguardMessageArea2 = this.secondaryMessageView;
        if (bouncerKeyguardMessageArea2 != null) {
            bouncerKeyguardMessageArea2.mIsDisabled = true;
            bouncerKeyguardMessageArea2.update();
        }
    }

    public BouncerMessageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LinearLayout.inflate(getContext(), R.layout.bouncer_message_view, this);
    }
}
