package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.plugins.DarkIconDispatcher;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TwoPhoneModeIconView extends ImageView implements DarkIconDispatcher.DarkReceiver {
    public TwoPhoneModeIconView(Context context) {
        super(context);
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public final void onDarkChanged(ArrayList arrayList, float f, int i) {
        setImageTintList(ColorStateList.valueOf(DarkIconDispatcher.getTint(arrayList, this, i)));
    }

    public TwoPhoneModeIconView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TwoPhoneModeIconView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
