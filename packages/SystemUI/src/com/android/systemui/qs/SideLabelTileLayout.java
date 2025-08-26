package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.RefactorFlag;
import com.android.systemui.flags.RefactorFlag$$ExternalSyntheticLambda0;
import com.android.systemui.flags.UnreleasedFlag;

/* loaded from: classes2.dex */
public class SideLabelTileLayout extends TileLayout {
    public final boolean isSmallLandscapeLockscreenEnabled;

    public SideLabelTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        RefactorFlag.Companion companion = RefactorFlag.Companion;
        UnreleasedFlag unreleasedFlag = Flags.LOCKSCREEN_ENABLE_LANDSCAPE;
        companion.getClass();
        this.isSmallLandscapeLockscreenEnabled = ((Boolean) new RefactorFlag(null, unreleasedFlag, new RefactorFlag$$ExternalSyntheticLambda0(1), null).isEnabled$delegate.getValue()).booleanValue();
    }

    @Override // com.android.systemui.qs.TileLayout
    public final boolean updateMaxRows(int i, int i2) {
        int i3 = this.mRows;
        int i4 = this.mMaxAllowedRows;
        this.mRows = i4;
        int i5 = this.mColumns;
        if (i4 > ((i2 + i5) - 1) / i5) {
            this.mRows = ((i2 + i5) - 1) / i5;
        }
        return i3 != this.mRows;
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.SecQSPanel.QSTileLayout
    public final boolean updateResources() throws Resources.NotFoundException {
        boolean zUpdateResources = super.updateResources();
        this.mMaxAllowedRows = (this.isSmallLandscapeLockscreenEnabled && ((ViewGroup) this).mContext.getResources().getBoolean(R.bool.is_small_screen_landscape)) ? getContext().getResources().getInteger(R.integer.small_land_lockscreen_quick_settings_max_rows) : getContext().getResources().getInteger(R.integer.quick_settings_max_rows);
        return zUpdateResources;
    }
}
