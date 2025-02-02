package com.android.systemui.shade.carrier;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SwitchableDoubleShadowTextView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ShadeCarrier extends LinearLayout {
    public SwitchableDoubleShadowTextView mCarrierText;
    public boolean mIsSingleCarrier;
    public CellSignalState mLastSignalState;
    public View mMobileGroup;
    public View mSpacer;

    public ShadeCarrier(Context context) {
        super(context);
    }

    public View getRSSIView() {
        return this.mMobileGroup;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mCarrierText.setMaxEms(getResources().getInteger(R.integer.shade_carrier_single_line_max_ems));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mMobileGroup = findViewById(R.id.mobile_combo);
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = (SwitchableDoubleShadowTextView) findViewById(R.id.shade_carrier_text);
        this.mCarrierText = switchableDoubleShadowTextView;
        switchableDoubleShadowTextView.shadowEnabled = true;
        this.mSpacer = findViewById(R.id.spacer);
        this.mCarrierText.setMaxEms(getResources().getInteger(R.integer.shade_carrier_single_line_max_ems));
    }

    public final void setCarrierText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.toString().contains("&")) {
            this.mCarrierText.setTextDirection(5);
            this.mCarrierText.setTextAlignment(0);
        } else {
            this.mCarrierText.setTextDirection(6);
            this.mCarrierText.setTextAlignment(5);
        }
        this.mCarrierText.setText(charSequence);
    }

    public ShadeCarrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ShadeCarrier(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public ShadeCarrier(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
