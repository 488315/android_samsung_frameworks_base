package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

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
