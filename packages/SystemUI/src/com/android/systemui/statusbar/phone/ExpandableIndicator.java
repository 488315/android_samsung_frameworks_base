package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class ExpandableIndicator extends ImageView {
    public final boolean mIsDefaultDirection;

    public ExpandableIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDefaultDirection = true;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setImageResource(this.mIsDefaultDirection ? R.drawable.ic_volume_expand_animation : R.drawable.ic_volume_collapse_animation);
        setContentDescription(((ImageView) this).mContext.getString(R.string.accessibility_quick_settings_expand));
    }
}
