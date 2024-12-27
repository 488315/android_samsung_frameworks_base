package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlag;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SideLabelTileLayout extends TileLayout {
    public final boolean isSmallLandscapeLockscreenEnabled;

    public SideLabelTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isSmallLandscapeLockscreenEnabled = ((Boolean) RefactorFlag.Companion.forView$default(RefactorFlag.Companion, Flags.LOCKSCREEN_ENABLE_LANDSCAPE).isEnabled$delegate.getValue()).booleanValue();
    }

    @Override // com.android.systemui.qs.TileLayout
    public final boolean updateMaxRows(int i, int i2) {
        int i3 = this.mRows;
        int i4 = this.mMaxAllowedRows;
        this.mRows = i4;
        int i5 = this.mColumns;
        int i6 = ((i2 + i5) - 1) / i5;
        if (i4 > i6) {
            this.mRows = i6;
        }
        return i3 != this.mRows;
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() {
        boolean updateResources = super.updateResources();
        this.mMaxAllowedRows = (this.isSmallLandscapeLockscreenEnabled && ((ViewGroup) this).mContext.getResources().getBoolean(R.bool.is_small_screen_landscape)) ? getContext().getResources().getInteger(R.integer.small_land_lockscreen_quick_settings_max_rows) : getContext().getResources().getInteger(R.integer.quick_settings_max_rows);
        return updateResources;
    }
}
