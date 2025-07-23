package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecNumPadKeyTablet extends SecNumPadKey {
    public SecNumPadKeyTablet(Context context) {
        this(context, null);
    }

    @Override // com.android.keyguard.SecNumPadKey
    public final void updateDigitTextSize() {
        this.mDigitText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_num_pad_font_size_tablet));
    }

    @Override // com.android.keyguard.SecNumPadKey
    public final void updateKlondikeTextSize() {
        this.mKlondikeText.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_pin_klondike_font_size_tablet));
    }

    public SecNumPadKeyTablet(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecNumPadKeyTablet(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.layout.keyguard_sec_num_pad_key_tablet);
    }

    private SecNumPadKeyTablet(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
    }
}
