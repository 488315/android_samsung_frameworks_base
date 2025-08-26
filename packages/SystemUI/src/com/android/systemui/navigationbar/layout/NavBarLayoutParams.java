package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* loaded from: classes2.dex */
public final class NavBarLayoutParams implements BarLayoutParams {
    public final Context context;

    public NavBarLayoutParams(Context context, NavBarStateManager navBarStateManager) {
        this.context = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarGravity(boolean z, int i) {
        if (!z) {
            return 80;
        }
        if (i != 1) {
            return i != 3 ? 80 : 3;
        }
        return 5;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarHeight(boolean z, int i) {
        if (!z) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        if (!z) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.select_dialog_drawable_padding_start_material);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.select_dialog_drawable_padding_start_material);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        if (!z || i == -1 || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(R.dimen.snooze_and_bubble_gone_padding_end);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!z) {
            return -1;
        }
        if (i == 1 || i == 3) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_right_edge_size);
        }
        return -1;
    }
}
