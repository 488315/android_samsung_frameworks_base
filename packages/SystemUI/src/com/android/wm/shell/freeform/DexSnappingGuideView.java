package com.android.wm.shell.freeform;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DexSnappingGuideView extends FrameLayout {
    public DexSnappingGuideView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.dex_snapping_guide_view_margin);
    }

    public DexSnappingGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
