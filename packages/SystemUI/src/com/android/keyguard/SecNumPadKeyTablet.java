package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
