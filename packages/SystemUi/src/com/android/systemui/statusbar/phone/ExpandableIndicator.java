package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ExpandableIndicator extends ImageView {
    public boolean mExpanded;
    public final boolean mIsDefaultDirection;

    public ExpandableIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsDefaultDirection = true;
    }

    public final String getContentDescription(boolean z) {
        return z ? ((ImageView) this).mContext.getString(R.string.accessibility_quick_settings_collapse) : ((ImageView) this).mContext.getString(R.string.accessibility_quick_settings_expand);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        boolean z = this.mExpanded;
        boolean z2 = this.mIsDefaultDirection;
        int i = R.drawable.ic_volume_expand_animation;
        if (!z2 ? !z : z) {
            i = R.drawable.ic_volume_collapse_animation;
        }
        setImageResource(i);
        setContentDescription(getContentDescription(this.mExpanded));
    }
}
