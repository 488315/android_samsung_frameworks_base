package com.android.p038wm.shell.onehanded;

import android.R;
import android.content.Context;
import android.content.res.Resources;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneHandedSurfaceTransactionHelper {
    public final float mCornerRadius;
    public final float mCornerRadiusAdjustment;
    public final boolean mEnableCornerRadius;

    public OneHandedSurfaceTransactionHelper(Context context) {
        Resources resources = context.getResources();
        float dimension = resources.getDimension(R.dimen.text_size_subtitle_material_toolbar);
        this.mCornerRadiusAdjustment = dimension;
        this.mCornerRadius = resources.getDimension(R.dimen.text_size_subhead_material) - dimension;
        this.mEnableCornerRadius = resources.getBoolean(com.android.systemui.R.bool.config_one_handed_enable_round_corner);
    }
}
