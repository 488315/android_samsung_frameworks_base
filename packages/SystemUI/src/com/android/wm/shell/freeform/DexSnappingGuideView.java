package com.android.wm.shell.freeform;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class DexSnappingGuideView extends FrameLayout {
    public DexSnappingGuideView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.dex_snapping_guide_view_margin);
    }

    public DexSnappingGuideView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
