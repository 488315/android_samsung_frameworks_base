package com.android.systemui.bouncer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.keyguard.BouncerKeyguardMessageArea;
import com.android.keyguard.KeyguardSecMessageAreaController;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageView extends LinearLayout {
    public KeyguardSecMessageAreaController primaryMessage;
    public BouncerKeyguardMessageArea primaryMessageView;
    public KeyguardSecMessageAreaController secondaryMessage;
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
    }

    public BouncerMessageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LinearLayout.inflate(getContext(), R.layout.bouncer_message_view, this);
    }
}
