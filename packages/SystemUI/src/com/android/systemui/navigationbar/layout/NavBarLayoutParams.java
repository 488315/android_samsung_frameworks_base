package com.android.systemui.navigationbar.layout;

import android.R;
import android.content.Context;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.samsung.systemui.splugins.navigationbar.BarLayoutParams;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_left_edge_size);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_left_edge_size);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetHeight(boolean z, int i) {
        if (!z) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.seekbar_track_progress_height_material);
        }
        if (i == -1 || i == 0 || i == 2) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.seekbar_track_progress_height_material);
        }
        return -1;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarInsetWidth(boolean z, int i) {
        if (!z || i == -1 || i == 0 || i == 2) {
            return -1;
        }
        return this.context.getResources().getDimensionPixelSize(R.dimen.slice_shortcut_size);
    }

    @Override // com.samsung.systemui.splugins.navigationbar.BarLayoutParams
    public final int getBarWidth(boolean z, int i) {
        if (!z) {
            return -1;
        }
        if (i == 1 || i == 3) {
            return this.context.getResources().getDimensionPixelSize(R.dimen.secondary_waterfall_display_left_edge_size);
        }
        return -1;
    }
}
